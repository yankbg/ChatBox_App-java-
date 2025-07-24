package com.example.chatboxapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.chatboxapp.Util.FirebaseUtil;
import com.example.chatboxapp.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class LoginUserName extends AppCompatActivity {
    EditText usernameInput;
    Button btnNext;
    ProgressBar progressBar;
    String phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user_name);

        usernameInput=findViewById(R.id.login_username);
        btnNext = findViewById(R.id.btn_next02);
        progressBar= findViewById(R.id.login_progressBar03);

        phonenumber= getIntent().getExtras().getString("phone");
        getUserName();
    }
    void getUserName(){
        setInprogress(true);
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setInprogress(false);
                if(task.isSuccessful()){
                    UserModel UserModel = task.getResult().toObject(UserModel.class);
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