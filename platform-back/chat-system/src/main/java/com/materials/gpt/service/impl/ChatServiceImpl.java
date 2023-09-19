package com.materials.gpt.service.impl;

import com.materials.gpt.controller.req.AddChatRecordReq;
import com.materials.gpt.entity.Chat;
import com.materials.gpt.entity.ChatRecord;
import com.materials.gpt.entity.User;
import com.materials.gpt.enums.HttpCodeEnum;
import com.materials.gpt.mapper.ChatMapper;
import com.materials.gpt.mapper.ChatRecordMapper;
import com.materials.gpt.service.ChatService;
import com.materials.gpt.utils.ResponseInfo;
import com.materials.gpt.utils.ResponseInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private ChatRecordMapper chatRecordMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseInfo addChatRecord(User userInfo, AddChatRecordReq req) {
        //首次添加会话记录，新增一个会话
        if(req.getChatId()==null){
            Chat chat=new Chat();
            chat.setChatName(req.getSendMessage());
            chat.setUserId(userInfo.getUserId());
            chat.setCreateDate(new Timestamp(System.currentTimeMillis()));
            Integer insert = chatMapper.insert(chat);
            req.setChatId(chat.getId());
        }
        ChatRecord chatRecord=new ChatRecord();
        chatRecord.setChatId(req.getChatId());
        chatRecord.setUserId(userInfo.getUserId());
        chatRecord.setCreateDate(new Timestamp(System.currentTimeMillis()));
        chatRecord.setSendMessage(req.getSendMessage());
        chatRecord.setReceiveMessage(req.getReceiveMessage());
        chatRecordMapper.insert(chatRecord);
        return ResponseInfoUtil.createResponseInfo(HttpCodeEnum.success,"添加成功",chatRecord.getChatId());
    }

    @Override
    public ResponseInfo getChatList(User user) {
        List<Chat> chats = chatMapper.selectList(user.getUserId());
        return ResponseInfoUtil.createResponseInfo(HttpCodeEnum.success,"查询成功",chats);
    }


    @Override
    public ResponseInfo getChatRecordList(Integer chatId) {
        List<ChatRecord> chats = chatRecordMapper.selectList(chatId);
        return ResponseInfoUtil.createResponseInfo(HttpCodeEnum.success,"查询成功",chats);
    }

    @Override
    public ResponseInfo deleteChat(Integer chatId) {
        int delete = chatMapper.delete(chatId);
        int delete1 = chatRecordMapper.delete(chatId);
        if(delete!=0){
            return ResponseInfoUtil.createResponseInfo(HttpCodeEnum.success,"删除成功");
        }
        return ResponseInfoUtil.createResponseInfo(HttpCodeEnum.failed,"删除失败");
    }
}
