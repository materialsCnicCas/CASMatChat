package com.materials.gpt.utils;


import com.materials.gpt.enums.HttpCodeEnum;

public class ResponseInfoUtil {
    public ResponseInfoUtil() {
    }
    public static ResponseInfo createResponseInfo(HttpCodeEnum state, String msg) {
        return new ResponseInfo(state.getValue(),msg);
    }

    public static ResponseInfo createResponseInfo(HttpCodeEnum state, String msg, Object data) {
        return new ResponseInfo(state.getValue(),msg,data);
    }

    public static ResponseInfo createResponseInfo(HttpCodeEnum state, String msg, Long total, Object data) {
        return new ResponseInfo(state.getValue(), msg, total, data);
    }

    public static ResponseInfo createResult(Exception e){
        ResponseInfo responeData = new ResponseInfo();
        responeData.setStatus(HttpCodeEnum.failed.getValue());
        responeData.setMsg(String.format("错误信息为: %s",e.getMessage()));
        return responeData;
    }
}
