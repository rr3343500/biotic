package com.example.bioticclasses.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.ActivityLoginBinding;
import com.example.bioticclasses.modal.login.Signin;
import com.example.bioticclasses.modal.signup.Signup;
import com.example.bioticclasses.other.SessionManage;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
     ActivityLoginBinding binding;
     SessionManage sessionManage;
     BiotechInterface biotechInterface ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sessionManage= new SessionManage(this);
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
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("mobile",binding.mobile.getText().toString());
        jsonObject.addProperty("password",binding.password.getText().toString());

        Log.e("row  json ", jsonObject.toString());
        biotechInterface.LOGIN_CALL(jsonObject).enqueue(new Callback<Signin>() {
            @Override
            public void onResponse(Call<Signin> call, Response<Signin> response) {
                if (response.isSuccessful()){
                    Log.e("sadsfs",response.body().getResult().getMessage());
                    Log.e("sadsfs", String.valueOf(response.body().getResult().getErrorCode()));
                    Log.e("sadsfs", String.valueOf(response.body().getResult().getError()));
                    if (!response.body().getResult().getError()  && response.body().getResult().getErrorCode()==200){
                        sessionManage.createLoginSession(response.body().getResult().getData().get(0).getNameEn(),
                                response.body().getResult().getData().get(0).getEmail(),
                                response.body().getResult().getData().get(0).getMobile(),
                                response.body().getResult().getData().get(0).getMedium(),
                                response.body().getResult().getData().get(0).getStuSub().toString(),
                                response.body().getResult().getData().get(0).getMedium(),
                                response.body().getResult().getData().get(0).getId()
                        );
                        if (sessionManage.Checkingcredential()){
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        }

                    }
                    else {
                        Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Signin> call, Throwable t) {
                Log.e("sadsfs",t.getMessage());
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