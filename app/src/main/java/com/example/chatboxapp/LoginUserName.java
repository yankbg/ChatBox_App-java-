package com.example.chatboxapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.chatboxapp.Util.FirebaseUtil;
import com.example.chatboxapp.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

public class LoginUserName extends AppCompatActivity {
    EditText usernameInput;
    Button btnNext;
    ProgressBar progressBar;
    String phonenumber;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user_name);

        usernameInput=findViewById(R.id.login_username);
        btnNext = findViewById(R.id.btn_next02);
        progressBar= findViewById(R.id.login_progressBar03);

        phonenumber= getIntent().getExtras().getString("phone");
        getUserName();
        btnNext.setOnClickListener(v -> setUsername());
    }
    void setUsername(){

        String username = usernameInput.getText().toString();
        if(username.isEmpty() || username.length() < 3){
            usernameInput.setError("username length should be at least 3 letters");
            return;
        }
        setInprogress(true);
        if(userModel != null){
            userModel.setUsername(username);
        }else{
            userModel = new UserModel(phonenumber,username, Timestamp.now());
        }
        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                setInprogress(false);
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginUserName.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }
    void getUserName(){
        setInprogress(true);
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setInprogress(false);
                if(task.isSuccessful()){
                     userModel = task.getResult().toObject(UserModel.class);
                    if(userModel != null){
                        usernameInput.setText(userModel.getUsername());
                    }
                }
            }
        });
    }
    void setInprogress(boolean isInProgress){
        if(isInProgress){
            progressBar.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            btnNext.setVisibility(View.VISIBLE);
        }
    }
}