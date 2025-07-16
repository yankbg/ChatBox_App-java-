package com.example.chatboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.hbb20.CountryCodePicker;

public class LoginMobile extends AppCompatActivity {


    Button sendOtp_btn;
    EditText mobile;
    ProgressBar progressBar;
    CountryCodePicker countryCodePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mobile);

        sendOtp_btn=findViewById(R.id.btn_send_otp);
        mobile=findViewById(R.id.login_mobile);
        countryCodePicker=findViewById(R.id.login_ccp);
        progressBar=findViewById(R.id.login_progressBar01);

        progressBar.setVisibility(View.GONE);
        countryCodePicker.registerCarrierNumberEditText(mobile);
        sendOtp_btn.setOnClickListener((v) ->{
            if(!countryCodePicker.isValidFullNumber()){
                mobile.setError("phone number not valid");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this,LoginOtp.class);
            intent.putExtra("phone",countryCodePicker.getFullNumberWithPlus());
            startActivity(intent);
        });
    }
}