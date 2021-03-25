package com.example.bioticclasses.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.ActivitySignUpBinding;
import com.example.bioticclasses.modal.signup.Signup;
import com.example.bioticclasses.modal.subjectclass.Subject;
import com.example.bioticclasses.modal.subjectclass.SubjectClass;
import com.example.bioticclasses.other.SessionManage;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity  {
    ActivitySignUpBinding binding;
    String[] spinnerValue = {"Male","Female"};
    String[] classes={};
    String[] subjects={};
    ArrayList<String>finalsubjects = new ArrayList<String>();
    BiotechInterface biotechInterface ;
    List<Subject>subjectsList= new ArrayList<>();
    String SubjectName="", ClassName="", Gender="";
    SubjectAdapter subjectAdapter;
    ClassAdapter classadapter;
    Boolean first=false, second=false, third=false;
    JsonArray jsonArray;
    SessionManage sessionManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sessionManage= new SessionManage(this);
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



    public void showDialog( ArrayList<String> stringArrayList){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.multiselect_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LinearLayout linearLayout= dialog.findViewById(R.id.mainview);
        for(int i=0 ;i< stringArrayList.size();i++){
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(stringArrayList.get(i));
            cb.setOnClickListener(v -> {
                finalsubjects.add( cb.getText().toString());
            });
        }
        dialog.show();
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
                if (third){
                    JsonObject jsonObject= new JsonObject();
                    jsonArray= new JsonArray();
                    jsonObject.addProperty("id","6059cbb443eb7c29568afc8b");
                    jsonObject.addProperty("subj","BIOLOGY");
                    jsonArray.add(jsonObject);

                    SubjectName=jsonArray.toString();
                }else {
                    third= true;
                };
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
            binding.numberTextInputLayout.setError("Invalid Mobile Number ");
            binding.numberTextInputLayout.setErrorEnabled(true);
            responce = false;
        }else {
            binding.numberTextInputLayout.setError(null);
            binding.numberTextInputLayout.setErrorEnabled(false);
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
            binding.nameTextInputLayout.setError("name is empty");
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

        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("name_en", binding.name.getText().toString().toUpperCase());
        jsonObject1.addProperty("email", binding.email.getText().toString().toUpperCase());
        jsonObject1.addProperty("mobile", binding.mobile.getText().toString().toUpperCase());
        jsonObject1.addProperty("medium", "ENGLISH");
        jsonObject1.addProperty("password", binding.password.getText().toString().toUpperCase());
        jsonObject1.add("subjects", jsonArray);

        Log.e("row  json ", jsonObject1.toString());

        biotechInterface.SIGNUP_CALL(jsonObject1).enqueue(new Callback<Signup>() {
            @Override
            public void onResponse(Call<Signup> call, Response<Signup> response) {
                if (response.isSuccessful()){
                    Log.e("sadsfs",response.body().getResult().getMessage());
                    Log.e("sadsfs", String.valueOf(response.body().getResult().getErrorCode()));
                    Log.e("sadsfs", String.valueOf(response.body().getResult().getError()));
                    if (!response.body().getResult().getError()  && response.body().getResult().getErrorCode()==200){
                        sessionManage.createLoginSession(response.body().getResult().getData().getNameEn(),
                                response.body().getResult().getData().getEmail(),
                                response.body().getResult().getData().getMobile(),
                                response.body().getResult().getData().getMedium(),
                                response.body().getResult().getData().getStuSub().toString(),
                                response.body().getResult().getData().getMedium(),
                                response.body().getResult().getData().getId()
                               );
                        if (sessionManage.Checkingcredential()){
                            startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                        }

                    }
                    else {
                        Toast.makeText(SignUpActivity.this, response.body().getResult().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(SignUpActivity.this, "hyjkhygbjh,vgjyh", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Signup> call, Throwable t) {
                Log.e("sadsfs",t.getMessage());
                Toast.makeText(SignUpActivity.this, "user  Already Registered!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}