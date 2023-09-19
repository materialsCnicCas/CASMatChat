import os
import sys

import fire
import gradio as gr
import torch
import transformers
from peft import PeftModel
from transformers import GenerationConfig, LlamaForCausalLM, LlamaTokenizer

from utils.callbacks import Iteratorize, Stream
from utils.prompter import Prompter


from flask import Flask, render_template, request, jsonify
from flask_socketio import SocketIO
import redis
import queue
import threading
import time


if torch.cuda.is_available():
    device = "cuda"
else:
    device = "cpu"

try:
    if torch.backends.mps.is_available():
        device = "mps"
except:  # noqa: E722
    pass


# 初始化 Flask 和 SocketIO
app = Flask(__name__)

# 创建一个锁
lock = threading.Lock()
condition = threading.Condition(lock)
waiting_queue = []


# 初始化 Redis
r = redis.Redis(host='localhost', port=6379, db=0)


def evaluate(
    instruction,
    input=None,
    temperature=0.1,
    top_p=0.75,
    top_k=40,
    num_beams=4,
    max_new_tokens=1024,
    stream_output=True,
    **kwargs,
):
    prompt = prompter.generate_prompt(instruction, input)
    inputs = tokenizer(prompt, return_tensors="pt")
    input_ids = inputs["input_ids"].to(device)
    generation_config = GenerationConfig(
        temperature=temperature,
        top_p=top_p,
        top_k=top_k,
        num_beams=num_beams,
        **kwargs,
    )

    generate_params = {
        "input_ids": input_ids,
        "generation_config": generation_config,
        "return_dict_in_generate": True,
        "output_scores": True,
        "max_new_tokens": max_new_tokens,
    }

    if stream_output:
        # Stream the reply 1 token at a time.
        # This is based on the trick of using 'stopping_criteria' to create an iterator,
        # from https://github.com/oobabooga/text-generation-webui/blob/ad37f396fc8bcbab90e11ecf17c56c97bfbd4a9c/modules/text_generation.py#L216-L243.

        def generate_with_callback(callback=None, **kwargs):
            kwargs.setdefault(
                "stopping_criteria", transformers.StoppingCriteriaList()
            )
            kwargs["stopping_criteria"].append(
                Stream(callback_func=callback)
            )
            with torch.no_grad():
                model.generate(**kwargs)

        def generate_with_streaming(**kwargs):
            return Iteratorize(
                generate_with_callback, kwargs, callback=None
            )

        with generate_with_streaming(**generate_params) as generator:
            for output in generator:
                # new_tokens = len(output) - len(input_ids[0])
                decoded_output = tokenizer.decode(output)

                if output[-1] in [tokenizer.eos_token_id]:
                    break

                yield prompter.get_response(decoded_output)
        return  # early return for stream_output

    # Without streaming
    with torch.no_grad():
        generation_output = model.generate(
            input_ids=input_ids,
            generation_config=generation_config,
            return_dict_in_generate=True,
            output_scores=True,
            max_new_tokens=max_new_tokens,
        )
    s = generation_output.sequences[0]
    output = tokenizer.decode(s)
    yield prompter.get_response(output)

    for i in evaluate(instruction='please tell me how to synesis LiPSO4'):
        msgQueue.put(i)
    msgQueue.put("end441")

@app.route('/interference', methods=['POST'])
def handle_connect():
    print("enter")
    # 获取传过来的 token 参数
    token = request.json.get('token')
    print(token)
    # 检查 token 是否存在于 Redis 中
    if r.exists(token):
        with condition:
            if waiting_queue:  # 如果队列不为空，将当前请求放入队列
                event = threading.Event()
                waiting_queue.append(event)
                condition.wait_for(event.set)

            # 在这里处理模型或其他资源
            print('Client connected')
            instruction = request.json.get('instruction')
            print(instruction)
            res = ""
            for i in evaluate(instruction=instruction):
                res = i
                #socketio.emit('model_result', {'data': i})
            print(res)
            # 唤醒队列中的下一个请求（如果有的话）
            if waiting_queue:
                next_event = waiting_queue.pop(0)
                next_event.set()
            return jsonify({"message": res}), 200

    else:
        print('Unauthorized')
        return jsonify({"error": "Token not found in Redis"}), 403

if __name__ == "__main__":
    #初始化参数
    load_8bit: bool = False
   # base_model: str = ""
   # lora_weights: str = "tloen/alpaca-lora-7b"
    base_model: str = "../Llama2-7b-hf/"
    lora_weights: str = "../output_weights"
    prompt_template: str = ""  # The prompt template to use, will default to alpaca.
    server_name: str = "0.0.0.0"  # Allows to listen on all interfaces by providing '0.
    share_gradio: bool = False

    # 初始化模型
    base_model = base_model or os.environ.get("BASE_MODEL", "")
    assert (
        base_model
    ), "Please specify a --base_model, e.g. --base_model='huggyllama/llama-7b'"

    prompter = Prompter(prompt_template)
    tokenizer = LlamaTokenizer.from_pretrained(base_model)
    if device == "cuda":
        model = LlamaForCausalLM.from_pretrained(
            base_model,
            load_in_8bit=load_8bit,
            torch_dtype=torch.float16,
            device_map="auto",
        )
        model = PeftModel.from_pretrained(
            model,
            lora_weights,
            torch_dtype=torch.float16,
        )
    elif device == "mps":
        model = LlamaForCausalLM.from_pretrained(
            base_model,
            device_map={"": device},
            torch_dtype=torch.float16,
        )
        model = PeftModel.from_pretrained(
            model,
            lora_weights,
            device_map={"": device},
            torch_dtype=torch.float16,
        )
    else:
        model = LlamaForCausalLM.from_pretrained(
            base_model, device_map={"": device}, low_cpu_mem_usage=True
        )
        model = PeftModel.from_pretrained(
            model,
            lora_weights,
            device_map={"": device},
        )

    # unwind broken decapoda-research config
    model.config.pad_token_id = tokenizer.pad_token_id = 0  # unk
    model.config.bos_token_id = 1
    model.config.eos_token_id = 2

    if not load_8bit:
        model.half()  # seems to fix bugs for some users.

    model.eval()
    if torch.__version__ >= "2" and sys.platform != "win32":
        model = torch.compile(model)

    app.run(host='0.0.0.0', port=5000)





