package com.example.chatboxapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class SearchUserActivity extends AppCompatActivity {
    ImageButton searchbtn,backbtn;
    EditText searchInput;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        searchbtn= findViewById(R.id.search_btn);
        backbtn = findViewById(R.id.back_btn);
        searchInput = findViewById(R.id.search_username_input);
        recyclerView = findViewById(R.id.search_user_recycler_view);

        searchInput.requestFocus();

        backbtn.setOnClickListener(v -> onBackPressed());

        searchbtn.setOnClickListener((v) ->{
            String searchname = searchInput.getText().toString();
            if(searchname.isEmpty() || searchname.length() < 3){
                searchInput.setError("Invalid username");
                return;
            }
            setupSearchRecyclerView(searchname);
        });
    }
    void setupSearchRecyclerView(String searchname){

    }
}