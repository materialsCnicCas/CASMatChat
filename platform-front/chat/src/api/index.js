import request from "../utils/request";

// 添加对话
export function addChatRecord(data) {
  return request({
    url: "/materialsgpt/chat/addChatRecord",
    headers: {
      isToken: true,
    },
    method: "post",
    data: data,
  });
}

// 查询历史列表
export function chatList() {
  return request({
    url: "/materialsgpt/chat/chatList",
    headers: {
      isToken: true,
    },
    method: "get",
  });
}

// 查询某一项的对话信息
export function chatRecordList(data) {
  return request({
    url: "/materialsgpt/chat/chatRecordList",
    headers: {
      isToken: true,
    },
    method: "get",
    params: data,
  });
}

// 删除
export function deleteChat(data) {
  return request({
    url: "/materialsgpt/chat/deleteChat",
    headers: {
      isToken: true,
    },
    method: "get",
    params: data,
  });
}

// 获取回复消息
export function interference(data){
  return request({
    url:'/interference',
    headers: {
      isToken: true,
    },
    method: "post",
    data: data
  })
}