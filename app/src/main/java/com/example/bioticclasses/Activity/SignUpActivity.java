package com.example.bioticclasses.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bioticclasses.databinding.ActivitySignUpBinding;

import static android.os.Build.VERSION_CODES.R;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    String[] spinnerValue = {"Male","Female"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        SetActivityData();
        ActivityAction();

    }

    private void SetActivityData(){
        GenderAdapter adapter = new GenderAdapter(SignUpActivity.this, android.R.layout.simple_list_item_1);
        adapter.addAll(spinnerValue);
        adapter.add("Select Gender");
        binding.gender.setAdapter(adapter);
        binding.gender.setSelection(adapter.getCount());

        ClassAdapter classadapter = new ClassAdapter(SignUpActivity.this, android.R.layout.simple_list_item_1);
        classadapter.addAll(spinnerValue);
        classadapter.add("Select Class");
        binding.classes.setAdapter(classadapter);
        binding.classes.setSelection(classadapter.getCount());

    }


    private void ActivityAction(){
        binding.gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(SignUpActivity.this, spinnerValue[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    public class GenderAdapter extends ArrayAdapter<String> {
        public GenderAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }
    }

    public class ClassAdapter extends ArrayAdapter<String> {
        public ClassAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }
    }
}