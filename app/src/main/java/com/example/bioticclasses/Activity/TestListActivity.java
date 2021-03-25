package com.example.bioticclasses.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.bioticclasses.Adapter.TestAdapter;
import com.example.bioticclasses.databinding.ActivityTestListBinding;
import com.example.bioticclasses.modal.mainList.Datum;
import com.example.bioticclasses.modal.mainList.MainList;
import com.example.bioticclasses.viewModel.MainActivityViewModel;

import java.util.List;

public class TestListActivity extends AppCompatActivity {

    ActivityTestListBinding binding;
    int position;
    MainActivityViewModel mainActivityViewModel;
    List<Datum> datumList;
    private static final String TAG = "TestListActivity";

   MainList mainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Tests");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
        mainActivityViewModel.getMainList().observe(this, data -> {
            binding.recycle.setAdapter(new TestAdapter(this, data.get(position).getTests() , position));
        });
    }


}



































