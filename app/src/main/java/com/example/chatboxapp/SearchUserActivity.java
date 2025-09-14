package com.example.chatboxapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.chatboxapp.Util.FirebaseUtil;
import com.example.chatboxapp.adapter.SearchUserRecyclerAdapter;
import com.example.chatboxapp.model.UserModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class SearchUserActivity extends AppCompatActivity {
    ImageButton searchbtn,backbtn;
    EditText searchInput;
    RecyclerView recyclerView;
    SearchUserRecyclerAdapter adapter;

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
        Query query= FirebaseUtil.allUserCollectionReference()
                .whereGreaterThanOrEqualTo("username",searchname)
                .whereLessThanOrEqualTo("username",searchname+'\uf8ff');

        FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>()
                .setQuery(query,UserModel.class).build();

        adapter = new SearchUserRecyclerAdapter(options,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
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
}