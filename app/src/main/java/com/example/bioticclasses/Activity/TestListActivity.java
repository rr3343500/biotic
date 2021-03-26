package com.example.bioticclasses.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.bioticclasses.Adapter.MytestAdapter;
import com.example.bioticclasses.Adapter.TestAdapter;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.ActivityTestListBinding;
import com.example.bioticclasses.modal.mainList.Datum;
import com.example.bioticclasses.modal.mainList.MainList;
import com.example.bioticclasses.modal.show_test_list.TestShowList;
import com.example.bioticclasses.other.SessionManage;
import com.example.bioticclasses.viewModel.MainActivityViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestListActivity extends AppCompatActivity {

    ActivityTestListBinding binding;
    int position;
    SessionManage sessionManage;
    MainActivityViewModel mainActivityViewModel;
    List<Datum> datumList;
    BiotechInterface biotechInterface;
    private static final String TAG = "TestListActivity";

   MainList mainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Tests");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sessionManage = new SessionManage(this);
        biotechInterface = ApiClient.getClient().create(BiotechInterface.class);


//        Log.e(TAG, "onCreate: " + mainList.getResult().getData().size() );




        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        position = Integer.parseInt(getIntent().getStringExtra("pos"));

        try {
            TestData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    private void TestData() throws Exception {

        biotechInterface.getTestList(sessionManage.getUserDetails().get("userid") , sessionManage.getUserDetails().get("Class")).enqueue(new Callback<TestShowList>() {
            @Override
            public void onResponse(Call<TestShowList> call, Response<TestShowList> response) {
                mainActivityViewModel.getMainList().observe(TestListActivity.this, data -> {


                    binding.recycle.setAdapter(new TestAdapter(TestListActivity.this, data.get(position).getTests() , position,  response.body().getResult().getData()));
                });
            }

            @Override
            public void onFailure(Call<TestShowList> call, Throwable t) {

            }
        });

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));finish();
    }
}



































