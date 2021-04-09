package com.example.bioticclasses.fragments.account;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.AccountFragmentBinding;
import com.example.bioticclasses.modal.subjectclass.Subject;
import com.example.bioticclasses.other.SessionManage;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AccountFragment extends Fragment {

    private AccountViewModel mViewModel;
    AccountFragmentBinding binding;
    String[] spinnerValue = {"Male","Female"};
    String[] classes={};
    String[] subjects={};
    HashMap<String,String> subjectlist= new HashMap<>();
    ArrayList<String> finalsubjects = new ArrayList<String>();
    BiotechInterface biotechInterface ;
    List<Subject> subjectsList= new ArrayList<>();
    List<Subject>newlist= new ArrayList<>();
    String SubjectName="", ClassName="", Gender="" , Language="";
    SubjectAdapter subjectAdapter;
    ClassAdapter classadapter;
    Boolean first=false, second=false, third=false,fourth=false;
    JsonArray jsonArray;
    JSONObject jsonObject= new JSONObject();
    SessionManage sessionManage;
    String[] language = {"Hindi","English"};
    Typeface face;
    private Uri fileUri;
    TextView title;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AccountFragmentBinding.inflate(inflater,container,false);
        sessionManage= new SessionManage(getActivity());
        face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/montserrat_regular.ttf");
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        mViewModel.getTestlist().observe(getActivity(),data -> {
            ArrayList<String> stringArrayList = new ArrayList<String>();
            for(int i=0 ;i<data.getClass_().size();i++){
                stringArrayList.add(data.getClass_().get(i).getNameEn());
            }
            classes=stringArrayList.toArray(new String[stringArrayList.size()]);
            subjectsList= data.getSubject();
            classadapter.addAll(classes);
            classadapter.add("Select Class");
            binding.classes2.setAdapter(classadapter);
            binding.classes2.setSelection(classadapter.getCount());
        });
        SetFragmentData();
        FragmentAction();
    }



    private  void SetFragmentData(){
        title=getActivity().findViewById(R.id.title);
        title.setText("Account");
        binding.inputname.setText(sessionManage.getUserDetails().get("Name"));
        binding.inputemail.setText(sessionManage.getUserDetails().get("Email"));
        binding.inputmobile.setText(sessionManage.getUserDetails().get("Mobile"));
        binding.inputmobile.setText(sessionManage.getUserDetails().get("Mobile"));
//        binding.inputpassword.setText(sessionManage.getUserDetails().get("Password"));




        GenderAdapter adapter = new GenderAdapter(getActivity(), android.R.layout.simple_list_item_1);
        adapter.addAll(spinnerValue);
        adapter.add(sessionManage.getUserDetails().get("Gender"));
        binding.gender2.setAdapter(adapter);
        binding.gender2.setSelection(adapter.getCount());
        classadapter = new ClassAdapter(getActivity(), android.R.layout.simple_list_item_1);
        subjectAdapter = new SubjectAdapter(getActivity(), android.R.layout.simple_list_item_1);
        LanguageAdapter languageAdapter = new LanguageAdapter(getActivity(), android.R.layout.simple_list_item_1);
        languageAdapter.addAll(language);
        languageAdapter.add(sessionManage.getUserDetails().get("Medium"));
        binding.meduim1.setAdapter(languageAdapter);
        binding.meduim1.setSelection(languageAdapter.getCount());
    }

    private void FragmentAction(){
        binding.upload.setOnClickListener(v -> {
            ImagePicker.Companion.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start();
        });

        binding.gender2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.hint_gray));
                ((TextView) parent.getChildAt(0)).setTextSize(14);
                ((TextView) parent.getChildAt(0)).setTypeface(face);
                ((TextView) parent.getChildAt(0)).setPadding(0,0,0,0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((TextView) parent.getChildAt(0)).setLetterSpacing(0);
                }
                if (first){Gender= spinnerValue[position];}else {first= true;};

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Gender= "";
            }
        });

        binding.classes2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.hint_gray));
                ((TextView) parent.getChildAt(0)).setTextSize(14);
                ((TextView) parent.getChildAt(0)).setTypeface(face);
                ((TextView) parent.getChildAt(0)).setPadding(0,0,0,0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((TextView) parent.getChildAt(0)).setLetterSpacing(0);
                }

                jsonObject = new JSONObject();
                binding.multisubject.setText("Select Subject");
                if (second){
                    ClassName= classes[position];


                    subjectlist.clear();
                    for(int i=0 ;i< subjectsList.size();i++){
                        if(subjectsList.get(i).getStuClass().trim().toUpperCase().equals(ClassName.trim().toUpperCase())){
                            subjectlist.put(subjectsList.get(i).getId(),subjectsList.get(i).getNameEn());
                        }
                    }

                    Log.e("main ", String.valueOf(subjectsList.size()));
                    subjects= null;
//                    subjects=stringArrayList.toArray(new String[stringArrayList.size()]);
//                    subjectAdapter.clear();
//                    subjectAdapter.addAll(subjects);
//                    subjectAdapter.add("Select Subject");
//                    binding.subject.setAdapter(subjectAdapter);
//                    binding.subject.setSelection(subjectAdapter.getCount());
                    binding.subjectview.setVisibility(View.VISIBLE);

                }else {
                    second=true;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ClassName= "";
            }
        });

        binding.subjectview.setOnClickListener(v -> {
            showDialog(subjectlist);
        });

        binding.meduim1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.hint_gray));
                ((TextView) parent.getChildAt(0)).setTextSize(14);
                ((TextView) parent.getChildAt(0)).setTypeface(face);
                ((TextView) parent.getChildAt(0)).setPadding(0,0,0,0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((TextView) parent.getChildAt(0)).setLetterSpacing(0);
                }
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
             fileUri = data.getData();
             binding.image.setImageURI(fileUri);
             File file =  ImagePicker.Companion.getFile(data);


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(getActivity(), ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    public void showDialog(HashMap<String,String> stringArrayList){
        Log.e("sdfsd", String.valueOf(stringArrayList.size()));
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.multiselect_dialog_layout);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getColor(R.color.theme_pink)));

        LinearLayout linearLayout= dialog.findViewById(R.id.mainview);
        for (Map.Entry me : stringArrayList.entrySet()) {
            System.out.println("Key: "+me.getKey() + " & Value: " + me.getValue());
            System.out.println("Key: "+me.getKey() + " & Value: " + me.getValue());
            CheckBox cb = new CheckBox(getActivity());
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
                        if (!findSubjects(cb.getTag().toString())){
                            jsonObject.put(cb.getTag().toString(),cb.getText().toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.e("TAG", "showDialog: " + jsonObject.toString());
                setSubjects();
            });
        }

        MaterialButton close= dialog.findViewById(R.id.close);
        close.setOnClickListener(v -> {dialog.cancel();});
        dialog.show();
    }

    Boolean findSubjects(String s){
        Boolean finder;
        try {
            jsonObject.getString(s);
            finder=true;
        } catch (JSONException e) {
            e.printStackTrace();
            finder=false;
        }

        return finder;
    }

    private void setSubjects(){
        String text="";
        Iterator<?> keys = jsonObject.keys();
        while (keys.hasNext()) {
            try {
                String key = String.valueOf(keys.next());
                JsonObject finalsubject= new JsonObject();
                finalsubject.addProperty("subj",jsonObject.getString(key));
                text+= jsonObject.getString(key)+" ";
                binding.multisubject.setText(text);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private boolean Validate(){

        Boolean responce =true;

        if(binding.inputmobile.getText().toString().isEmpty() || binding.inputmobile.getText().toString().length()< 10){
            binding.inputmobile.setError("Invalid Mobile Number ");
//            binding.numberTextInputLayout.setErrorEnabled(true);
            responce = false;
        }else {
            binding.inputmobile.setError(null);
//            binding.numberTextInputLayout.setErrorEnabled(false);
            responce = true;
        }
        if(binding.inputpassword.getText().toString().isEmpty()){
            binding.inputpassword.setError("password is empty");
//            binding.passwordTextInputLayout.setErrorEnabled(true);
            responce = false;
        }else {
            binding.inputpassword.setError(null);
//            binding.passwordTextInputLayout.setErrorEnabled(false);
            responce = true;
        }
        if(binding.inputname.getText().toString().isEmpty()){
            binding.inputname.setError("name is empty");
//            binding.nameTextInputLayout.setErrorEnabled(true);
            responce = false;
        }else {
            binding.inputname.setError(null);
//            binding.nameTextInputLayout.setErrorEnabled(false);
            responce = true;
        }
        if(Gender.isEmpty()){
            Toast.makeText(getActivity(), "Select Gender", Toast.LENGTH_SHORT).show();
            responce = false;
        }else {
            responce = true;
        }

        if(ClassName.isEmpty()){
            Toast.makeText(getActivity(), "Select Classs", Toast.LENGTH_SHORT).show();
            responce = false;
        }else {
            responce = true;
        }
        if(jsonObject.toString().isEmpty()){
            Toast.makeText(getActivity(), "Select Subject", Toast.LENGTH_SHORT).show();
            responce = false;
        }else {
            responce = true;
        }
        if(Language.isEmpty()){
            Toast.makeText(getActivity(), "Select Medium", Toast.LENGTH_SHORT).show();
            responce = false;
        }else {
            responce = true;
        }

        return responce;
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