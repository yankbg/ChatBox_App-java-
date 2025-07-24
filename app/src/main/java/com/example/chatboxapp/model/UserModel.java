package com.example.chatboxapp.model;

import com.google.firebase.Timestamp;

public class UserModel {
    private String phone,username;
    private Timestamp createdtimestamp;

    public UserModel() {
    }

    public UserModel(String phone, String username, Timestamp createdtimestamp) {
        this.phone = phone;
        this.username = username;
        this.createdtimestamp = createdtimestamp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreatedtimestamp() {
        return createdtimestamp;
    }

    public void setCreatedtimestamp(Timestamp createdtimestamp) {
        this.createdtimestamp = createdtimestamp;
    }
}
