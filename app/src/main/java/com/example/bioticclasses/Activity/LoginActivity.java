package com.example.bioticclasses.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.ActivityLoginBinding;
import com.example.bioticclasses.modal.login.Login;
import com.example.bioticclasses.modal.signup.Signup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
     ActivityLoginBinding binding;

     BiotechInterface biotechInterface ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        biotechInterface = ApiClient.getClient().create(BiotechInterface.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.redColor));
        }

        getSupportActionBar().hide();
        ActivityAction();
    }


    private void ActivityAction(){
        binding.SinnupBtn.setOnClickListener(v -> { startActivity(new Intent(LoginActivity.this , SignUpActivity.class)); });
        binding.signinBtn.setOnClickListener(v -> {
          if(Validate()){
              SigIn();
          }
        });
    }


    void SigIn(){
        biotechInterface.LOGIN_CALL(binding.mobile.getText().toString(),binding.password.getText().toString()).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Log.e("responce", String.valueOf(response.body().getResult()));
                if (response.isSuccessful()){
                    Log.e("sadsfs", String.valueOf(response.body().getResult().getError()));
                    if (!response.body().getResult().getError()  && response.body().getResult().getErrorCode()==200){

                    }
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }

    private boolean Validate(){
        Log.e("length", String.valueOf(binding.mobile.getText().toString().length()));
        Boolean responce =true;

        if(binding.mobile.getText().toString().isEmpty() || binding.mobile.getText().toString().length()< 10){
            binding.emailTextInputLayout.setError("Invalid Mobile Number ");
            binding.emailTextInputLayout.setErrorEnabled(true);
            responce = false;
        }else {
            binding.emailTextInputLayout.setError(null);
            binding.emailTextInputLayout.setErrorEnabled(false);
            responce = true;
        }
        if(binding.password.getText().toString().isEmpty()){
            binding.passwordTextInputLayout.setError("password is empty");
            binding.passwordTextInputLayout.setErrorEnabled(true);
            responce = false;
        }else {
            binding.passwordTextInputLayout.setError(null);
            binding.passwordTextInputLayout.setErrorEnabled(false);
            responce = true;
        }

        return responce;
    }
}