package com.materials.gpt.service;

import com.materials.gpt.controller.req.AddChatRecordReq;
import com.materials.gpt.entity.User;
import com.materials.gpt.utils.ResponseInfo;
import org.apache.ibatis.annotations.Param;

public interface ChatService {


    /**
     * 新增会话记录
     * @param req
     * @return
     */
    ResponseInfo addChatRecord(User userInfo, AddChatRecordReq req);


    /**
     * 查询会话列表
     */
    ResponseInfo getChatList(User user);


    /**
     * 查询会话记录记录列表
     */
    ResponseInfo getChatRecordList(Integer chatId);

    /**
     * 删除会话记录
     * @param chatId
     * @return
     */
    ResponseInfo deleteChat(Integer chatId);
}
