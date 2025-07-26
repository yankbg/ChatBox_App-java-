package com.example.chatboxapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chatboxapp.Util.Utils;
import com.example.chatboxapp.model.UserModel;

public class ChatActivicty extends AppCompatActivity {

    UserModel otherUser;
    ImageButton sendBtn,backBtn;
    TextView otherUserName;
    EditText chat_msg_send;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        otherUser = Utils.getUserModelFromIntent(getIntent());
        sendBtn = findViewById(R.id.img_send_btn);
        backBtn = findViewById(R.id.back_btn);
        otherUserName = findViewById(R.id.other_user);
        chat_msg_send = findViewById(R.id.chat_message);
        recyclerView = findViewById(R.id.recycler_view_chat);

        backBtn.setOnClickListener(v -> onBackPressed());
        otherUserName.setText(otherUser.getUsername());
    }
}