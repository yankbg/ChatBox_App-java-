package com.example.chatboxapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chatboxapp.Util.FirebaseUtil;
import com.example.chatboxapp.Util.Utils;
import com.example.chatboxapp.adapter.ChatRecyclerAdapter;
import com.example.chatboxapp.adapter.SearchUserRecyclerAdapter;
import com.example.chatboxapp.model.ChatMessageModel;
import com.example.chatboxapp.model.UserModel;
import com.example.chatboxapp.model.chatroomModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Arrays;

public class ChatActivicty extends AppCompatActivity {

    UserModel otherUser;
    String chatroomId;
    chatroomModel chatroommodel;
    ChatRecyclerAdapter adapter;
    ImageButton sendBtn,backBtn;
    TextView otherUserName;
    EditText chat_msg_send;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        otherUser = Utils.getUserModelFromIntent(getIntent());
        chatroomId = FirebaseUtil.getchatroomId(FirebaseUtil.currentUserid(), otherUser.getUserId());
        sendBtn = findViewById(R.id.img_send_btn);
        backBtn = findViewById(R.id.back_btn);
        otherUserName = findViewById(R.id.other_user);
        chat_msg_send = findViewById(R.id.chat_message);
        recyclerView = findViewById(R.id.recycler_view_chat);

        backBtn.setOnClickListener(v -> onBackPressed());
        otherUserName.setText(otherUser.getUsername());
        sendBtn.setOnClickListener(v ->{
            String msg = chat_msg_send.getText().toString().trim();
            if(msg.isEmpty()){
                return;
            }
            sendMessageToUser(msg);
        });
        getOnCreateChatroomModel();
        setUprecyclerView();
    }
    void setUprecyclerView(){
        Query query= FirebaseUtil.getChatMessageReference(chatroomId)
                .orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatMessageModel> options = new FirestoreRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(query,ChatMessageModel.class).build();

        adapter = new ChatRecyclerAdapter(options,getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        if(adapter != null){
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null){
            adapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter != null){
            adapter.startListening();
        }
    }
    void sendMessageToUser(String msg){

        chatroommodel.setLastmessage(FirebaseUtil.currentUserid());
        chatroommodel.setTimestamp(Timestamp.now());
        chatroommodel.setLastMsg(msg);
        FirebaseUtil.getChatroomReference(chatroomId).set(chatroommodel);

        ChatMessageModel chatMessageModel = new ChatMessageModel(msg,FirebaseUtil.currentUserid(),Timestamp.now());
        FirebaseUtil.getChatMessageReference(chatroomId).add(chatMessageModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    chat_msg_send.setText(" ");
                }
            }
        });
    }
    void getOnCreateChatroomModel(){
        FirebaseUtil.getChatroomReference(chatroomId).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                chatroommodel = task.getResult().toObject(chatroomModel.class);
                if (chatroommodel == null){
                    chatroommodel = new chatroomModel(
                            chatroomId,
                            Arrays.asList(FirebaseUtil.currentUserid(),otherUser.getUserId()),
                            Timestamp.now(),
                            ""
                    );
                    FirebaseUtil.getChatroomReference(chatroomId).set(chatroommodel);
                }
            }
        });

    }
}