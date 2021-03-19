package com.example.bioticclasses.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.bioticclasses.Adapter.TestAdapter;
import com.example.bioticclasses.R;
import com.example.bioticclasses.databinding.ActivityTestListBinding;

public class TestListActivity extends AppCompatActivity {
    ActivityTestListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Python Tests");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.recycle.setAdapter(new TestAdapter(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}