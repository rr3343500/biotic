package com.example.bioticclasses.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.ActivitySignUpBinding;
import com.example.bioticclasses.modal.signup.Signup;
import com.example.bioticclasses.modal.subjectclass.Subject;
import com.example.bioticclasses.modal.subjectclass.SubjectClass;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    String[] spinnerValue = {"Male","Female"};
    String[] classes={};
    String[] subjects={};
    BiotechInterface biotechInterface ;
    List<Subject>subjectsList= new ArrayList<>();
    String SubjectName, ClassName, Gender;
    SubjectAdapter subjectAdapter;
    ClassAdapter classadapter;
    Boolean first=false, second=false, third=false;

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

        classadapter = new ClassAdapter(SignUpActivity.this, android.R.layout.simple_list_item_1);
//        classadapter.addAll(classes);
//        classadapter.add("Select Class");
//        binding.classes.setAdapter(classadapter);
//        binding.classes.setSelection(classadapter.getCount());

         subjectAdapter = new SubjectAdapter(SignUpActivity.this, android.R.layout.simple_list_item_1);

    }

    private void ActivityAction(){

        binding.signupBtn.setOnClickListener(v -> {startActivity(new Intent(SignUpActivity.this , LanguageActivity.class));});

        binding.gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                if (first){Gender= spinnerValue[position];}else {first= true;};

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Gender= "";
            }
        });
        binding.classes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                 if (second){
                     ClassName= classes[position];
                     ArrayList<String> stringArrayList = new ArrayList<String>();

                     for(int i=0 ;i< subjectsList.size();i++){
                         if(subjectsList.get(i).getStuClass().trim().toUpperCase().equals(ClassName.trim().toUpperCase())){
                             stringArrayList.add(subjectsList.get(i).getNameEn());
                         }
                     }
                     subjects= null;
                     subjects=stringArrayList.toArray(new String[stringArrayList.size()]);
                     subjectAdapter.clear();
                     subjectAdapter.addAll(subjects);
                     subjectAdapter.add("Select Subject");
                     binding.subject.setAdapter(subjectAdapter);
                     binding.subject.setSelection(subjectAdapter.getCount());
                     binding.subject.setVisibility(View.VISIBLE);
                 }else {
                     second=true;
                 }




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ClassName= "";
            }
        });
        binding.subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                if (third){SubjectName= subjects[position];}else {first= true;};
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SubjectName= "";
            }
        });

        binding.signupBtn.setOnClickListener(v -> {
            if(Validate()){
                Signup();
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

    public class SubjectAdapter extends ArrayAdapter<String> {
        public SubjectAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        biotechInterface = ApiClient.getClient().create(BiotechInterface.class);
        biotechInterface.Fetch_sub_class("").enqueue(new Callback<SubjectClass>() {
            @Override
            public void onResponse(Call<SubjectClass> call, Response<SubjectClass> response) {
                if (response.isSuccessful()){
                    if (!response.body().getResult().getError()  && response.body().getResult().getErrorCode()==200){
                        ArrayList<String> stringArrayList = new ArrayList<String>();
                        for(int i=0 ;i< response.body().getResult().getClass_().size();i++){
                            stringArrayList.add(response.body().getResult().getClass_().get(i).getNameEn());
                        }
                        classes=stringArrayList.toArray(new String[stringArrayList.size()]);
                        subjectsList= response.body().getResult().getSubject();
                        classadapter.addAll(classes);
                        classadapter.add("Select Class");
                        binding.classes.setAdapter(classadapter);
                        binding.classes.setSelection(classadapter.getCount());

                    }

                }
            }

            @Override
            public void onFailure(Call<SubjectClass> call, Throwable t) {

            }
        });

    }

    private boolean Validate(){

        Boolean responce =true;

        if(binding.mobile.getText().toString().isEmpty() || binding.mobile.getText().toString().length()< 10){
            binding.emailTextInputLayout.setError("Invalid Mobile Number ");
            binding.emailTextInputLayout.setErrorEnabled(true);
            responce = false;
        }else {
            binding.emailTextInputLayout.setError(null);
            binding.emailTextInputLayout.setErrorEnabled(false);
            responce = true;
        }
        if(binding.password.getText().toString().isEmpty()){
            binding.passwordTextInputLayout.setError("password is empty");
            binding.passwordTextInputLayout.setErrorEnabled(true);
            responce = false;
        }else {
            binding.passwordTextInputLayout.setError(null);
            binding.passwordTextInputLayout.setErrorEnabled(false);
            responce = true;
        }
        if(binding.name.getText().toString().isEmpty()){
            binding.nameTextInputLayout.setError("password is empty");
            binding.nameTextInputLayout.setErrorEnabled(true);
            responce = false;
        }else {
            binding.nameTextInputLayout.setError(null);
            binding.nameTextInputLayout.setErrorEnabled(false);
            responce = true;
        }
        if(Gender.isEmpty()){
            Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show();
            responce = false;
        }else {
            responce = true;
        }

        if(ClassName.isEmpty()){
            Toast.makeText(this, "Select Classs", Toast.LENGTH_SHORT).show();
            responce = false;
        }else {
            responce = true;
        }
        if(SubjectName.isEmpty()){
            Toast.makeText(this, "Select Subject", Toast.LENGTH_SHORT).show();
            responce = false;
        }else {
            responce = true;
        }

        return responce;
    }

    private void Signup(){
        biotechInterface.Signup()
    }
}