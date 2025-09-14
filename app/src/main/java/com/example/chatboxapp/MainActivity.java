package com.example.chatboxapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.chatboxapp.Util.FirebaseUtil;
import com.example.chatboxapp.Util.Utils;
import com.example.chatboxapp.model.UserModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ImageButton searchBtn, aiAssistantBtn;
    charFragment CharFragment;
    ProfileFragment profileFragment;
    // Define AI Assistant User Details
    private static final String AI_ASSISTANT_USER_ID = "ai_assistant";
    private static final String AI_ASSISTANT_USERNAME = "AI Assistant";
    private static final String AI_ASSISTANT_PHONE = "+256793456789";
    private static final String AI_ASSISTANT_PROFILE = "https://www.electropages.com/storage/temp/public/6b7/98a/5a4/neural-networks-take-inspiration-human-brain__750.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CharFragment = new charFragment();
        profileFragment = new ProfileFragment();
        searchBtn = findViewById(R.id.search_icon);
        bottomNavigationView = findViewById(R.id.bottomnavigation);
        aiAssistantBtn = findViewById(R.id.ai_assistant_button);


        ensureAiAssistantUserExists();
        searchBtn.setOnClickListener(v -> startActivity(new Intent(this,SearchUserActivity.class)));

        aiAssistantBtn.setOnClickListener(v -> {
            // Start a chat with the AI Assistant
            Intent intent = new Intent(this, ChatActivicty.class);
            UserModel aiModel = new UserModel(AI_ASSISTANT_PHONE, AI_ASSISTANT_USERNAME, null, AI_ASSISTANT_USER_ID);
            Utils.passUserModelAsIntent(intent, aiModel);
            startActivity(intent);
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.menu_chat){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,CharFragment).commit();
                }
                if(item.getItemId() == R.id.menu_profile){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,profileFragment).commit();
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_chat);
        getFCMToken();
    }

    private void getFCMToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String token = task.getResult();
                FirebaseUtil.currentUserDetails().update("fcmToken",token);
            }
        });
    }
    private void ensureAiAssistantUserExists() {
        DocumentReference aiUserDoc = FirebaseUtil.allUserCollectionReference().document(AI_ASSISTANT_USER_ID);
        aiUserDoc.get().addOnSuccessListener(documentSnapshot -> {
            if (!documentSnapshot.exists()) {
                // AI user does not exist, so create it
                UserModel aiUser = new UserModel(AI_ASSISTANT_PHONE, AI_ASSISTANT_USERNAME, null, AI_ASSISTANT_USER_ID, AI_ASSISTANT_PROFILE);
                aiUserDoc.set(aiUser);
            }
        });
    }
}
