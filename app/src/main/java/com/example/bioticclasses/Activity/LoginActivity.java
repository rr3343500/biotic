package com.example.bioticclasses.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.ActivityLoginBinding;
import com.example.bioticclasses.modal.login.Login;
import com.example.bioticclasses.other.NetworkCheck;
import com.example.bioticclasses.other.SessionManage;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
     ActivityLoginBinding binding;
     SessionManage sessionManage;
     BiotechInterface biotechInterface ;
     boolean doubleBackToExitPressedOnce = false;
    private boolean showPsw= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        sessionManage= new SessionManage(this);
        biotechInterface = ApiClient.getClient().create(BiotechInterface.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.redColor));
        }

        getSupportActionBar().hide();
        ActivityAction();
    }


    private void ActivityAction(){
        binding.showpass.setOnClickListener(v -> {
            if (showPsw) {
                binding.inputpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                showPsw = false;
            } else {
                binding.inputpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                showPsw= true;
            }
        });

        binding.forget.setOnClickListener(v -> { startActivity(new Intent(LoginActivity.this , ForgetPassword.class)); });
        binding.signup.setOnClickListener(v -> { startActivity(new Intent(LoginActivity.this , SignUpActivity.class)); });
        binding.button.setOnClickListener(v -> {
          if(Validate()){
              if(new NetworkCheck().haveNetworkConnection(LoginActivity.this)){
                  SigIn();
              }else {
                  Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
              }

          }
        });
    }


    void SigIn(){
        binding.constraintLayout.setAlpha((float) 0.2);
        binding.progress.setVisibility(View.VISIBLE);
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("mobile",binding.inputmobile.getText().toString());
        jsonObject.addProperty("password",binding.inputpassword.getText().toString());

        Log.e("row  json ", jsonObject.toString());
        biotechInterface.LOGIN_CALL(jsonObject).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()){
                    Log.e("sadsfs",response.body().getResult().getMessage());
                    Log.e("sadsfs", String.valueOf(response.body().getResult().getErrorCode()));
                    Log.e("sadsfs", String.valueOf(response.body().getResult().getError()));
                    if (!response.body().getResult().getError()  && response.body().getResult().getErrorCode()==200){

                        JSONObject jsonObject= new JSONObject();
                        Log.e("sfsdf", String.valueOf(response.body().getResult().getData().get(0).getStuSub().size()));
                        for(int i=0; i<response.body().getResult().getData().get(0).getStuSub().size();i++){
                            Log.e("sfsdf",response.body().getResult().getData().get(0).getStuSub().get(i).getId());
                            try {
                                jsonObject.put(response.body().getResult().getData().get(0).getStuSub().get(i).getId(),response.body().getResult().getData().get(0).getStuSub().get(i).getSubj());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Log.e("dfsdsd",  response.body().getResult().getData().get(0).getStuClas());
                        sessionManage.createLoginSession(response.body().getResult().getData().get(0).getNameEn(),
                                response.body().getResult().getData().get(0).getEmail(),
                                response.body().getResult().getData().get(0).getMobile(),
                                response.body().getResult().getData().get(0).getStuClas(),
                                jsonObject.toString(),
                                response.body().getResult().getData().get(0).getMedium(),
                                response.body().getResult().getData().get(0).getId(),
                                response.body().getResult().getData().get(0).getActive(),
                                response.body().getResult().getData().get(0).getImgName(),
                                response.body().getResult().getData().get(0).getPassword(),
                                "MALE"


                        );

                        Log.e("xzfsdf",  response.body().getResult().getData().get(0).getActive());
                        if (sessionManage.Checkingcredential() ){
                            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                            binding.constraintLayout.setAlpha(1);
                            binding.progress.setVisibility(View.GONE);
                        }

                    }
                    else {
                        Toast.makeText(LoginActivity.this, response.body().getResult().getMessage(), Toast.LENGTH_SHORT).show();
                        binding.constraintLayout.setAlpha(1);
                        binding.progress.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.e("sadsfs",t.getMessage());
                binding.constraintLayout.setAlpha(1);
                binding.progress.setVisibility(View.GONE);
            }
        });
    }

    private boolean Validate(){
        Log.e("length", String.valueOf(binding.inputmobile.getText().toString().length()));
        Boolean responce =true;

        if(binding.inputmobile.getText().toString().isEmpty() || binding.inputmobile.getText().toString().length()< 10){
            binding.inputmobile.setError("Invalid Mobile Number ");
//            binding.inputmobile.setErrorEnabled(true);
            responce = false;
        }else {
            binding.inputmobile.setError(null);
//            binding.emailTextInputLayout.setErrorEnabled(false);
            responce = true;
        }
        if(binding.inputpassword.getText().toString().isEmpty()){
            binding.inputpassword.setError("password is empty");
//            binding.passwordTextInputLayout.setErrorEnabled(true);
            responce = false;
        }else {
            binding.inputpassword.setError(null);
//            binding.passwordTextInputLayout.setErrorEnabled(false);
            responce = true;
        }

        return responce;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();

            System.exit(0);

            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit",
                Toast.LENGTH_SHORT).show();

    }
}