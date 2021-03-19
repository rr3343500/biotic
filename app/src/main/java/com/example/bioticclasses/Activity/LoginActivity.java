package com.example.bioticclasses.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.bioticclasses.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
     ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        ActivityAction();
    }


    private void ActivityAction(){
        binding.SigupBtn.setOnClickListener(v -> {startActivity(new Intent(this,SignUpActivity.class));});
        binding.forgotpsw.setOnClickListener(v -> {startActivity(new Intent(this,ForgetPassword.class));});
        binding.SignInBtn.setOnClickListener(v -> {startActivity(new Intent(this,MainActivity.class));});
    }
}