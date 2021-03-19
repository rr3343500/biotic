package com.example.bioticclasses.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.bioticclasses.R;
import com.example.bioticclasses.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
     ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.redColor));
        }

        getSupportActionBar().hide();
        ActivityAction();
    }

//
    private void ActivityAction(){

        binding.SinnupBtn.setOnClickListener(v -> {startActivity(new Intent(LoginActivity.this , SignUpActivity.class));});

//        binding.SigupBtn.setOnClickListener(v -> {startActivity(new Intent(this,SignUpActivity.class));});
//        binding.forgotpsw.setOnClickListener(v -> {startActivity(new Intent(this,ForgetPassword.class));});
//        binding.SignInBtn.setOnClickListener(v -> {startActivity(new Intent(this,MainActivity.class));});
    }
}