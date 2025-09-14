package com.example.chatboxapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.chatboxapp.Util.FirebaseUtil;
import com.example.chatboxapp.Util.Utils;
import com.example.chatboxapp.model.UserModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ProfileFragment extends Fragment {
EditText usernameInput,phone;
ImageView profilePic;
TextView logoutBtn;
ProgressBar progressBar;
Button updateBtn;
UserModel currentUserModel;
ActivityResultLauncher<Intent> imgPickerLauncher;
Uri selectedImgUri;
private static  String TAG = "uploag img";
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgPickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data != null && data.getData() != null){
                            selectedImgUri = data.getData();
                            Utils.setProfilePic(getContext(),selectedImgUri,profilePic);
                        }
                    }
                }
                );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        updateBtn = view.findViewById(R.id.btn_update);
        progressBar = view.findViewById(R.id.profile_progressBar);
        logoutBtn = view.findViewById(R.id.logout_btn);
        profilePic = view.findViewById(R.id.profile_img_view);
        usernameInput = view.findViewById(R.id.profile_username);
        phone = view.findViewById(R.id.profile_phone);

        getUserData();
        updateBtn.setOnClickListener(v -> updateBtnClick());
        logoutBtn.setOnClickListener(v ->{
           logoutBtnClick();
        });
        profilePic.setOnClickListener(v ->{
            ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512,512)
                    .createIntent(new Function1<Intent, Unit>() {
                        @Override
                        public Unit invoke(Intent intent) {
                            imgPickerLauncher.launch(intent);
                            return null;
                        }
                    });
        });

        initConfig();

        return view;
    }

    private void logoutBtnClick() {
        FirebaseMessaging.getInstance().deleteToken().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUtil.logout();
                Intent intent = new Intent(getContext(),SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void initConfig() {
        Map config = new HashMap();
        config.put("cloud_name", "dq4p0a6x1");
        config.put("api_key", "823294593735767");
        config.put("api_secret", "AKI_bfjnKU-QCycywACcKAsrpIQ");
     //   config.put("secure", true);
        MediaManager.init(requireContext().getApplicationContext(), config);

    }

    void updateBtnClick(){

        String newUsername = usernameInput.getText().toString();
        if(newUsername.isEmpty() || newUsername.length() < 3){
            usernameInput.setError("username length should be at least 3 letters");
            return;
        }
        currentUserModel.setUsername(newUsername);
        setInprogress(true);
        if(selectedImgUri != null){
            updateImg();
        }else{
            updateToFirebase();
        }

    }

    private void updateImg() {
        MediaManager.get().upload(selectedImgUri).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                Log.d(TAG, "onStart: "+"started");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                Log.d(TAG, "onStart: "+"uploading");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {

                Log.d(TAG, "onStart: "+"usuccess");

                String profileImageUrl = (String) resultData.get("secure_url");
                currentUserModel.setProfileImageUrl(profileImageUrl);
                updateToFirebase();
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                Log.d(TAG, "onStart: "+"error");
                Utils.ShowToast(getContext(), "Image upload failed");
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                Log.d(TAG, "onStart: "+"error");
            }
        }).dispatch();
    }

    void updateToFirebase(){
        FirebaseUtil.currentUserDetails().set(currentUserModel)
                .addOnCompleteListener(task -> {
                    setInprogress(false);
                    if(task.isSuccessful()){
                        Utils.ShowToast(getContext(),"updated successfully");
                    }else{
                        Utils.ShowToast(getContext(),"updated failed");
                    }
                });
    }
    void getUserData(){
        setInprogress(true);
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            setInprogress(false);
           currentUserModel = task.getResult().toObject(UserModel.class);
           usernameInput.setText(currentUserModel.getUsername());
           phone.setText(currentUserModel.getPhone());
            String imageUrl = currentUserModel.getProfileImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {

                Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.default_profile)
                        .error(R.drawable.error_profile)
                        .circleCrop()
                        .into(profilePic);
            }
        });

    }
    void setInprogress(boolean isInProgress){
        if(isInProgress){
            progressBar.setVisibility(View.VISIBLE);
            updateBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            updateBtn.setVisibility(View.VISIBLE);
        }
    }
}