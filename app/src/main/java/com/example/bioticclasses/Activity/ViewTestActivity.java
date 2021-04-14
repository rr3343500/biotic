package com.example.bioticclasses.Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bioticclasses.Adapter.TestShowAdapter;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.ActivityViewTestBinding;
import com.example.bioticclasses.modal.mytest.MyTest;
import com.example.bioticclasses.modal.show_test_list.ShowTestDatum;
import com.example.bioticclasses.modal.show_test_list.TestShowList;
import com.example.bioticclasses.other.SessionManage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTestActivity extends AppCompatActivity {
    ActivityViewTestBinding binding;
    int pos;
    private static final String TAG = "ViewTestActivity";
    MyTest myTest;
    SessionManage sessionManage;
    BiotechInterface biotechInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        biotechInterface = ApiClient.getClient().create(BiotechInterface.class);
        sessionManage = new SessionManage(this);
        pos = Integer.parseInt(getIntent().getStringExtra("position"));
//        myTest = ((GlobalList) getApplicationContext()).myTest.get(pos);

        test();
//        binding.recycle.setAdapter(new MyTestViewAdapter(this, myTest.getResponse()));
    }


    void test() {

   /*     JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("user_id", sessionManage.getUserDetails().get("userid"));
//        jsonObject.addProperty("user_id", "605c1c4553a5880b41762566");
        Log.e("dds", jsonObject.toString());*/

        biotechInterface.getTestList(sessionManage.getUserDetails().get("userid"), sessionManage.getUserDetails().get("Class")).enqueue(new Callback<TestShowList>() {
            @Override
            public void onResponse(Call<TestShowList> call, Response<TestShowList> response) {

                List<ShowTestDatum> list = response.body().getResult().getData();
                Log.e("position",list.get(pos).toString());
                binding.recycle.setAdapter(new TestShowAdapter(list.get(pos).getResponse()));
            }

            @Override
            public void onFailure(Call<TestShowList> call, Throwable t) {

            }
        });

    }


}

























