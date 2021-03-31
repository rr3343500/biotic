package com.example.bioticclasses.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.ProfileActivityLayoutBinding;
import com.example.bioticclasses.modal.subjectclass.Subject;
import com.example.bioticclasses.modal.subjectclass.SubjectClass;
import com.example.bioticclasses.other.SessionManage;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    ProfileActivityLayoutBinding binding;
    DrawerLayout drawer;
    SessionManage sessionManage;
    String[] gender = {"Male","Female"};
    String[] classes={};
    String[] subjects={};
    HashMap<String,String> subjectlist= new HashMap<>();
    ArrayList<String> finalsubjects = new ArrayList<String>();
    BiotechInterface biotechInterface ;
    List<Subject> subjectsList= new ArrayList<>();
    String SubjectName="", ClassName="", Gender="" , Language="";
    SignUpActivity.SubjectAdapter subjectAdapter;
    ClassAdapter classadapter;
    Boolean first=false, second=false, third=false,fourth=false;
    JsonArray jsonArray;
    JSONObject jsonObject= new JSONObject();
    String[] language = {"Hindi","English"};
    Vector<String> strings= new Vector<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ProfileActivityLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sessionManage = new SessionManage(this);
        binding.navView.setNavigationItemSelectedListener(ProfileActivity.this);
        getSupportActionBar().hide();
        SetActivityData();
        ActivityAction();





    }

    private void SetActivityData() {
        getSupportActionBar().hide();
        drawer = binding.drawer;
        binding.home.name.setText(sessionManage.getUserDetails().get("Name"));
        binding.home.name1.setText(sessionManage.getUserDetails().get("Name"));
        binding.home.mobile.setText(sessionManage.getUserDetails().get("Mobile"));
        binding.home.email.setText(sessionManage.getUserDetails().get("Email"));

        GenderAdapter genderadapter = new GenderAdapter(ProfileActivity.this, android.R.layout.simple_list_item_1);
        genderadapter.addAll(gender);
        genderadapter.add("Select Gender");
        binding.home.gender.setAdapter(genderadapter);
        binding.home.gender.setSelection(genderadapter.getCount());

        LanguageAdapter mediumAdapter = new LanguageAdapter(ProfileActivity.this, android.R.layout.simple_list_item_1);
        mediumAdapter.addAll(language);
        mediumAdapter.add("Select Medium");
        binding.home.language.setAdapter(mediumAdapter);
        binding.home.language.setSelection(mediumAdapter.getCount());


        classadapter = new ClassAdapter(ProfileActivity.this, android.R.layout.simple_list_item_1);
//        classadapter.addAll(gender);
//        classadapter.add("Select Class");
//        binding.home.gender.setAdapter(classadapter);
//        binding.home.gender.setSelection(classadapter.getCount());





    }


    public void showDialog(HashMap<String,String> stringArrayList){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.multispinner_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        LinearLayout linearLayout= dialog.findViewById(R.id.mainview);
        for (Map.Entry me : stringArrayList.entrySet()) {
            System.out.println("Key: "+me.getKey() + " & Value: " + me.getValue());
            System.out.println("Key: "+me.getKey() + " & Value: " + me.getValue());
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(me.getValue().toString());
            cb.setTag(me.getKey().toString());
            cb.setTextColor(getResources().getColor(R.color.black));
            LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,10,0,0);
            cb.setLayoutParams(params);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cb.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
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
            Log.e("next", String.valueOf(keys.hasNext()));
            try {
                String key = String.valueOf(keys.next());
                JsonObject finalsubject= new JsonObject();
                finalsubject.addProperty("subj",jsonObject.getString(key));
                text+= jsonObject.getString(key)+" ";
                binding.home.multisubject.setText(text);

            } catch (JSONException e) {
                e.printStackTrace();
            }
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
                        binding.home.classes.setAdapter(classadapter);
                        binding.home.classes.setSelection(classadapter.getCount());

                    }

                }
            }

            @Override
            public void onFailure(Call<SubjectClass> call, Throwable t) {

            }
        });

    }

    private void ActivityAction() {
        binding.home.sidebarMenu.setOnClickListener(v -> {
            openDrawer();
        });

        binding.home.bottom.course.setOnClickListener(v -> {
            startActivity( new Intent(this, MainActivity.class));finish();
        });


        binding.home.gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                if (first){Gender= gender[position];}else {first= true;};

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Gender= "";
            }
        });
        binding.home.classes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                jsonObject = new JSONObject();
                binding.home.multisubject.setText("Select Subject");
                if (second){
                    subjectlist.clear();
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
                    binding.home.subject.setVisibility(View.VISIBLE);

                }else {
                    second=true;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ClassName= "";
            }
        });

        binding.home.subject.setOnClickListener(v -> {showDialog(subjectlist);});

        binding.home.language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
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

    private void openDrawer() {

        if (binding.drawer.isDrawerVisible(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START);
        } else binding.drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(this, MainActivity.class));finish();
                break;
            case R.id.test:
                startActivity(new Intent(this, MyTestsActivity.class));
                break;
            case R.id.logout:
                sessionManage.logoutUser();
                finish();
                break;
            case R.id.lacture:
                startActivity(new Intent(this, VedioLactureActivity.class));
                break;
            case R.id.notes:
                startActivity(new Intent(this, NotesActivity.class));
                break;

        }
        return false;
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