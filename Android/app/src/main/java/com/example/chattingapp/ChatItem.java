package com.example.chattingapp;

public class ChatItem {
    String userName;
    String content;
    String sendTime;
    int viewType;

    public ChatItem(String userName, String content, String sendTime, int viewType) {
        this.userName = userName;
        this.content = content;
        this.sendTime = sendTime;
        this.viewType = viewType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
