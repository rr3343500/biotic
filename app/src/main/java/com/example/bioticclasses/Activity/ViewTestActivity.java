package com.example.bioticclasses.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.bioticclasses.Adapter.MyTestViewAdapter;
import com.example.bioticclasses.Adapter.MytestAdapter;
import com.example.bioticclasses.R;
import com.example.bioticclasses.databinding.ActivityViewTestBinding;
import com.example.bioticclasses.global.GlobalList;

public class ViewTestActivity extends AppCompatActivity {
     ActivityViewTestBinding  binding;
     int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityViewTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.e("dsfdgd","cfgxf  "+getIntent().getStringExtra("position"));
        pos= Integer.parseInt(getIntent().getStringExtra("position"));
        binding.recycle.setAdapter(new MyTestViewAdapter(this, ((GlobalList)getApplicationContext()).myTest.get(pos).getResponse()));
    }
}