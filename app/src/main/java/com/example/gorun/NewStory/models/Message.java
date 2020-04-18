package com.example.gorun.NewStory.models;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

public class Message {

    private String messageUserId;
    private String messageText;
    private String messageUser;
    private long messageTime;



    public Message(String messageUser,  String messageUserId, String textMessage){
        this.messageUserId = messageUserId;
        this.messageText = textMessage;
        this.messageUser = messageUser;
        this.messageTime = new Date().getTime();
    }

    public Message(){}

    public String getMessageUserId() {
        return messageUserId;
    }

    public void setMessageUserId(String messageUserId) {
        this.messageUserId = messageUserId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
