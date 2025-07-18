package com.example.chatboxapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatboxapp.Util.Utils;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class LoginOtp extends AppCompatActivity {

    String phoneNumber;
    Long timeoutSeconds = 60L; // 60 seconds for OTP resend
    ProgressBar progressBar;
    EditText otpEditText;
    Button OtpButton;
    TextView etvresendotp;
    String verification;
    PhoneAuthProvider.ForceResendingToken token;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);

        progressBar= findViewById(R.id.login_progressBar02);
        otpEditText = findViewById(R.id.login_otp);
        OtpButton = findViewById(R.id.btn_next01);
        etvresendotp = findViewById(R.id.login_resend_otp);

        phoneNumber = getIntent().getExtras().getString("phone");
        progressBar.setVisibility(View.GONE);
        sendOtp(phoneNumber,false);

        OtpButton.setOnClickListener(v -> {
            String otp = otpEditText.getText().toString();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification, otp);
            signIn(credential);
            setInprogress(true);
        });

    }

    void sendOtp(String phoneNumber,boolean isResend){
        startResendTimer();
        setInprogress(true);
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding

                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signIn(phoneAuthCredential);
                                setInprogress(false);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Utils.ShowToast(getApplicationContext(),"OTP verification failed");
                                setInprogress(false);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verification = s;
                                token = forceResendingToken;
                                Utils.ShowToast(getApplicationContext(),"OTP verification sent successfully");
                                setInprogress(false);
                            }
                        });
        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(
                    builder.setForceResendingToken(token).build()
            );
        }
        else {
            PhoneAuthProvider.verifyPhoneNumber(
                    builder.build()
            );
        }
    }
    void setInprogress(boolean isInProgress){
        if(isInProgress){
            progressBar.setVisibility(View.VISIBLE);
            OtpButton.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            OtpButton.setVisibility(View.VISIBLE);
        }
    }
    void signIn(PhoneAuthCredential phoneAuthCredential) {
        setInprogress(true);
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Utils.ShowToast(getApplicationContext(), "Login successful");
                        // Navigate to the next activity
                        Intent intent = new Intent(LoginOtp.this, LoginUserName.class);
                        intent.putExtra("phone", phoneNumber);
                        startActivity(intent);

                    } else {
                        Utils.ShowToast(getApplicationContext(), "OTP verification failed:");
                    }
                    setInprogress(false);
                });
    }
    void startResendTimer(){
        etvresendotp.setEnabled(false);
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeoutSeconds--;


            }
        },0, 1000);
    }
}