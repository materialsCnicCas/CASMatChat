package com.materials.gpt.controller.req;

import java.io.Serializable;

public class AddChatRecordReq implements Serializable {

    private Integer chatId;

    private String sendMessage;

    private String receiveMessage;

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage;
    }

    public String getReceiveMessage() {
        return receiveMessage;
    }

    public void setReceiveMessage(String receiveMessage) {
        this.receiveMessage = receiveMessage;
    }

    @Override
    public String toString() {
        return "AddChatRecordReq{" +
                "chatId=" + chatId +
                ", sendMessage='" + sendMessage + '\'' +
                ", receiveMessage='" + receiveMessage + '\'' +
                '}';
    }
}
