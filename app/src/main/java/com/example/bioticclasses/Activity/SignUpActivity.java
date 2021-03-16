package com.example.bioticclasses.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.example.bioticclasses.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
    }
}