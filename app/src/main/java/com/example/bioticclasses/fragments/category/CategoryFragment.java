package com.example.bioticclasses.fragments.category;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bioticclasses.Activity.HomeActivity;
import com.example.bioticclasses.Adapter.CoursesAdapter;
import com.example.bioticclasses.Adapter.NotesAdapter;
import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.CategoryFragmentBinding;
import com.example.bioticclasses.fragments.mytest.MyTestViewModel;
import com.example.bioticclasses.modal.testlist.Datum;
import com.example.bioticclasses.modal.testlist.TestList;
import com.example.bioticclasses.other.SessionManage;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    CategoryViewModel categoryViewModel;
    MyTestViewModel myTestViewModel;
    CategoryFragmentBinding binding;
    TextView title;
    SessionManage  sessionManage;
    private static final String TAG = "CategoryFragment";
    Bundle bundle;
    List<Datum> list= new ArrayList<>();



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CategoryFragmentBinding.inflate(inflater, container, false);
        bundle=  getArguments();
        View root = binding.getRoot();
        SetFragmentData();

        binding.time.setOnClickListener(v -> {
            showDialog();
        });

        return root;

    }





    private void SetFragmentData(){
       title=getActivity().findViewById(R.id.title);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
//        myTestViewModel = new ViewModelProvider(this).get(MyTestViewModel.class);

        myTestViewModel  = ViewModelProviders.of(getActivity()).get(MyTestViewModel.class);
        categoryViewModel  = ViewModelProviders.of(getActivity()).get(CategoryViewModel.class);


        switch (bundle.get("type").toString())
        {
            case "Dpt":
                title.setText("Dpt");
                binding.time.setVisibility(View.VISIBLE);
                if(categoryViewModel!=null){


                    myTestViewModel.getTestlist().observe(getActivity(), showTestData -> {
                        categoryViewModel.getCatList(bundle.get("type").toString()).observe(getActivity(),data -> {
                            Log.e(TAG, "onActivityCreated: " + data.size() );
                            if(data.isEmpty()){
                                binding.msg.setVisibility(View.VISIBLE);
                                binding.recycle.setVisibility(View.GONE);
                            }else {
                                binding.msg.setVisibility(View.GONE);
                                binding.recycle.setVisibility(View.VISIBLE);
                                binding.recycle.setAdapter(new CoursesAdapter(getActivity(),data ,showTestData));
                            }

                        });

                    });



                }

                break;

            case "Notes":
                title.setText("Notes");
                binding.time.setVisibility(View.GONE);
                    categoryViewModel.getNotesList().observe(getActivity(), data -> {
                        Log.e(TAG, "onActivityCreated: " + data.size());
                        if (data.isEmpty()) {
                            binding.msg.setVisibility(View.VISIBLE);
                            binding.recycle.setVisibility(View.GONE);
                        } else {
                            binding.msg.setVisibility(View.GONE);
                            binding.recycle.setVisibility(View.VISIBLE);
                            binding.recycle.setAdapter(new NotesAdapter(getActivity(), data));
                        }
                    });
                break;

            case "Test Paper":
                binding.time.setVisibility(View.VISIBLE);
                title.setText("Test Paper");
                if(categoryViewModel!=null) {

                    myTestViewModel.getTestlist().observe(getActivity(), showTestData -> {

                    categoryViewModel.getCatList(bundle.get("type").toString()).observe(getActivity(), data -> {

                            Log.e(TAG, "onActivityCreated: " + data.size());
                            if (data.isEmpty()) {
                                binding.msg.setVisibility(View.VISIBLE);
                                binding.recycle.setVisibility(View.GONE);
                            } else {
                                binding.msg.setVisibility(View.GONE);
                                binding.recycle.setVisibility(View.VISIBLE);
                                binding.recycle.setAdapter(new CoursesAdapter(getActivity(), data, showTestData));
                            }
                        });
                    });


                }
                break;

        }

    }



    public void showDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.time_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/montserrat_regular.ttf");
        LinearLayout linearLayout= dialog.findViewById(R.id.mainview);
        ImageView close= dialog.findViewById(R.id.close);

                MaterialTextView textView = new MaterialTextView(getActivity());
                textView.setText("Time Limited");
                textView.setTypeface(face);
                textView.setTextSize(14);
                textView.setTag("YES");
                textView.setTextColor(getResources().getColor(R.color.very_light_black));
                textView.setMaxLines(1);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10,20,10,10);
                textView.setLayoutParams(params);
                linearLayout.addView(textView);

                MaterialTextView textView1 = new MaterialTextView(getActivity());
                textView1.setText("Time Unlimited");
                textView1.setTypeface(face);
                textView1.setTextSize(14);
                textView1.setTextColor(getResources().getColor(R.color.very_light_black));
                textView1.setMaxLines(1);
                textView1.setTag("NO");
                textView1.setEllipsize(TextUtils.TruncateAt.END);
                LinearLayout.LayoutParams params1 =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params1.setMargins(10,20,10,10);
                textView1.setLayoutParams(params1);
                linearLayout.addView(textView1);

                textView.setOnClickListener(v -> {
                    filter(textView.getTag().toString());
                    dialog.cancel();

                });
                textView1.setOnClickListener(v -> {
                    Log.e(TAG, "showDialog: "+ textView1.getTag().toString());
                    filter(textView1.getTag().toString());
                    dialog.cancel();

                });


        close.setOnClickListener(v -> {dialog.cancel();});
        dialog.show();
    }


    @Override
    public void onStart() {
        super.onStart();

    }

      public void filter( String s){

          switch (bundle.get("type").toString())
          {
              case "Dpt":
                  title.setText("Tests");
                  if(categoryViewModel!=null){
                      myTestViewModel.getTestlist().observe(getActivity(), showTestData -> {
                          categoryViewModel.getCatList(bundle.get("type").toString()).observe(getActivity(),data -> {
                              list.clear();
                             for(int i=0;i<data.size();i++){
                                 Log.e(TAG, "filter: "+ s+"==" + data.get(i).getTimeLimit() );
                                 if(s.toUpperCase().equals(data.get(i).getTimeLimit().toUpperCase())){
                                     list.add(data.get(i));
                                 }
                             }

                              if(list.isEmpty()){
                                  binding.msg.setVisibility(View.VISIBLE);
                                  binding.recycle.setVisibility(View.GONE);
                              }else {
                                  binding.msg.setVisibility(View.GONE);
                                  binding.recycle.setVisibility(View.VISIBLE);
                                  binding.recycle.setAdapter(new CoursesAdapter(getActivity(),list ,showTestData));
                              }

                          });

                      });
                  }

                  break;
                  case "Test Paper":
                  title.setText("Tests");
                  if(categoryViewModel!=null) {

                      myTestViewModel.getTestlist().observe(getActivity(), showTestData -> {

                          categoryViewModel.getCatList(bundle.get("type").toString()).observe(getActivity(), data -> {

                              list.clear();
                              for(int i=0;i<data.size();i++){
                                  if(s.equals(data.get(i).getTimeLimit())){
                                      list.add(data.get(i));
                                  }
                              }
                              if (list.isEmpty()) {
                                  binding.msg.setVisibility(View.VISIBLE);
                                  binding.recycle.setVisibility(View.GONE);
                              } else {
                                  binding.msg.setVisibility(View.GONE);
                                  binding.recycle.setVisibility(View.VISIBLE);
                                  binding.recycle.setAdapter(new CoursesAdapter(getActivity(), list, showTestData));
                              }
                          });
                      });


                  }
                  break;

          }

      }

//    @Override
//    public void onSubjectChanged() {
//        Log.e("dfdsss","sdfdssdg");
//        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
//        categoryViewModel.getCatList().observe(getActivity(),data -> {
//            Log.e(TAG, "onActivityCreated: " + data.size() );
//            binding.recycle.setAdapter(new CoursesAdapter(getActivity(),data));
//        });
//    }
}