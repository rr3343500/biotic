package com.example.bioticclasses.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.bioticclasses.Adapter.MytestAdapter;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.ActivityMyTestsBinding;
import com.example.bioticclasses.global.GlobalList;
import com.example.bioticclasses.modal.mytest.MyTest;
import com.example.bioticclasses.other.SessionManage;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTestsActivity extends AppCompatActivity {
    ActivityMyTestsBinding  binding;
    SessionManage sessionManage;
    BiotechInterface  biotechInterface;
    List<MyTest> myTests= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMyTestsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sessionManage= new SessionManage(this);
        biotechInterface = ApiClient.getClient().create(BiotechInterface.class);
        SetActivityData();

    }

    void SetActivityData(){
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("user_id",sessionManage.getUserDetails().get("userid"));
        Log.e("dds", jsonObject.toString());
        biotechInterface.TEST_CALL(jsonObject).enqueue(new Callback<ArrayList<MyTest>>() {
            @Override
            public void onResponse(Call<ArrayList<MyTest>> call, Response<ArrayList<MyTest>> response) {
                if (response.isSuccessful()) {
                      myTests= response.body();
                    ((GlobalList)getApplicationContext()).myTest = myTests;
                    binding.recycle.setAdapter(new MytestAdapter(MyTestsActivity.this, myTests));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MyTest>> call, Throwable t) {
              Log.e("dds",t.getMessage());
            }
        });
    }
}