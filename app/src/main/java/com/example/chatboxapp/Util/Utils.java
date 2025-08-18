package com.example.chatboxapp.Util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.chatboxapp.model.UserModel;

public class Utils {

    public static void ShowToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static void passUserModelAsIntent(Intent intent, UserModel model){
        intent.putExtra("username",model.getUsername());
        intent.putExtra("phone", model.getPhone());
        intent.putExtra("userId", model.getUserId());
        intent.putExtra("profileImageUrl", model.getProfileImageUrl());
    }
    public static UserModel getUserModelFromIntent(Intent intent){
        UserModel userModel = new UserModel();
        userModel.setUsername(intent.getStringExtra("username"));
        userModel.setPhone(intent.getStringExtra("phone"));
        userModel.setUserId(intent.getStringExtra("userId"));
        userModel.setProfileImageUrl(intent.getStringExtra("profileImageUrl"));
        return userModel;
    }
    public static void setProfilePic(Context context, Uri imgUri, ImageView imgView){
        Glide.with(context).load(imgUri).apply(RequestOptions.circleCropTransform()).into(imgView);
    }
}
