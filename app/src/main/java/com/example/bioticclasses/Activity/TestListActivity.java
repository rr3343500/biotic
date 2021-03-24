package com.example.bioticclasses.Activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.bioticclasses.Adapter.TestAdapter;
import com.example.bioticclasses.databinding.ActivityTestListBinding;
import com.example.bioticclasses.viewModel.MainActivityViewModel;

public class TestListActivity extends AppCompatActivity {

    ActivityTestListBinding binding;
    int position;
    MainActivityViewModel mainActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Python Tests");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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



































