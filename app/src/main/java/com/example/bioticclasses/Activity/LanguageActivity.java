package com.example.bioticclasses.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bioticclasses.R;
import com.example.bioticclasses.databinding.ActivityLanguageBinding;

public class   LanguageActivity extends AppCompatActivity {
    ActivityLanguageBinding binding;
    String[] spinnerValue = {"Hindi","English"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLanguageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        LanguageAdapter adapter = new LanguageAdapter(LanguageActivity.this, android.R.layout.simple_list_item_1);
        adapter.addAll(spinnerValue);
        adapter.add("Select Language");
        binding.language.setAdapter(adapter);
        binding.language.setSelection(adapter.getCount());
        binding.next.setOnClickListener(v -> {startActivity(new Intent(this,MainActivity.class));finish();});

        binding.language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    public class LanguageAdapter extends ArrayAdapter<String> {
        public LanguageAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }
    }
}