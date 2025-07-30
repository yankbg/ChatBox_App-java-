package com.example.chatboxapp.model;

import com.google.firebase.Timestamp;

import java.util.List;

public class chatroomModel {
    String chatroomId;
    List<String> userId;
    Timestamp timestamp;
    String lastmessage;

    public chatroomModel() {
    }

    public chatroomModel(String chatroomId, List<String> userId, Timestamp timestamp, String lastmessage) {
        this.chatroomId = chatroomId;
        this.userId = userId;
        this.timestamp = timestamp;
        this.lastmessage = lastmessage;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public List<String> getUserId() {
        return userId;
    }

    public void setUserId(List<String> userId) {
        this.userId = userId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }
}
