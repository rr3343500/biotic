package com.example.bioticclasses.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity  {
    ActivitySignUpBinding binding;
    String[] spinnerValue = {"Male","Female"};
    String[] classes={};
    String[] subjects={};
    HashMap<String,String>subjectlist= new HashMap<>();
    ArrayList<String>finalsubjects = new ArrayList<String>();
    BiotechInterface biotechInterface ;
    List<Subject>subjectsList= new ArrayList<>();
    String SubjectName="", ClassName="", Gender="" , Language="";
    SubjectAdapter subjectAdapter;
    ClassAdapter classadapter;
    Boolean first=false, second=false, third=false,fourth=false;
    JsonArray jsonArray;
    JSONObject jsonObject= new JSONObject();
    SessionManage sessionManage;
    String[] language = {"Hindi","English"};

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


        LanguageAdapter languageAdapter = new LanguageAdapter(SignUpActivity.this, android.R.layout.simple_list_item_1);
        languageAdapter.addAll(language);
        languageAdapter.add("Select Medium");
        binding.language.setAdapter(languageAdapter);
        binding.language.setSelection(languageAdapter.getCount());
    }



    public void showDialog(HashMap<String,String> stringArrayList){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.multiselect_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
        LinearLayout linearLayout= dialog.findViewById(R.id.mainview);
        for (Map.Entry me : stringArrayList.entrySet()) {
            System.out.println("Key: "+me.getKey() + " & Value: " + me.getValue());
            System.out.println("Key: "+me.getKey() + " & Value: " + me.getValue());
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(me.getValue().toString());
            cb.setTag(me.getKey().toString());
            cb.setTextColor(getResources().getColor(R.color.white));
            LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,10,0,0);
            cb.setLayoutParams(params);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cb.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            }
            linearLayout.addView(cb);
            cb.setOnClickListener(v -> {
                if(!cb.isChecked()){
                  jsonObject.remove(cb.getTag().toString());
                }else {
                    try {
                        jsonObject.put(cb.getTag().toString(),cb.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.e("TAG", "showDialog: " + jsonObject.toString());
            });
        }

        MaterialButton close= dialog.findViewById(R.id.close);
        close.setOnClickListener(v -> {dialog.cancel();});
        dialog.show();
    }

    private void ActivityAction(){

        binding.signupBtn.setOnClickListener(v -> {
            if(Validate()){
                Signup();
            }
        });

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
                    for(int i=0 ;i< subjectsList.size();i++){
                        if(subjectsList.get(i).getStuClass().trim().toUpperCase().equals(ClassName.trim().toUpperCase())){
                            subjectlist.put(subjectsList.get(i).getId(),subjectsList.get(i).getNameEn());
                        }
                    }
                    subjects= null;
//                    subjects=stringArrayList.toArray(new String[stringArrayList.size()]);
//                    subjectAdapter.clear();
//                    subjectAdapter.addAll(subjects);
//                    subjectAdapter.add("Select Subject");
//                    binding.subject.setAdapter(subjectAdapter);
//                    binding.subject.setSelection(subjectAdapter.getCount());
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

        binding.subject.setOnClickListener(v -> {showDialog(subjectlist);});

        binding.language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                if(fourth){
                    Language= language[position];
                }else {
                    fourth=true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
              Language="";
            }
        });
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
        if(jsonObject.toString().isEmpty()){
            Toast.makeText(this, "Select Subject", Toast.LENGTH_SHORT).show();
            responce = false;
        }else {
            responce = true;
        }
        if(Language.isEmpty()){
            Toast.makeText(this, "Select Medium", Toast.LENGTH_SHORT).show();
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
        jsonObject1.addProperty("mobile", binding.mobile.getText().toString());
        jsonObject1.addProperty("medium", Language.toUpperCase());
        jsonObject1.addProperty("password", binding.password.getText().toString());
        jsonObject1.addProperty("stu_class", ClassName);
        jsonArray= new JsonArray();

        Iterator<?>keys = jsonObject.keys();
        while (keys.hasNext()) {
            try {
                String key = String.valueOf(keys.next());
                JsonObject finalsubject= new JsonObject();
                finalsubject.addProperty("id",key);
                finalsubject.addProperty("subj",jsonObject.getString(key));
                jsonArray.add(finalsubject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
                                String.valueOf(response.body().getResult().getData().getClass()),
                                response.body().getResult().getData().getStuSub().toString(),
                                response.body().getResult().getData().getMedium(),
                                response.body().getResult().getData().getId(),
                                response.body().getResult().getData().getActive()
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