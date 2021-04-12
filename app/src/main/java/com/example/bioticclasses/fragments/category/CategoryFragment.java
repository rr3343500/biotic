package com.example.bioticclasses.fragments.category;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.bioticclasses.Activity.HomeActivity;
import com.example.bioticclasses.Adapter.CoursesAdapter;
import com.example.bioticclasses.Adapter.NotesAdapter;
import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.CategoryFragmentBinding;
import com.example.bioticclasses.fragments.mytest.MyTestViewModel;
import com.example.bioticclasses.modal.testlist.TestList;
import com.example.bioticclasses.other.SessionManage;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

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



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CategoryFragmentBinding.inflate(inflater, container, false);
        bundle=  getArguments();
        View root = binding.getRoot();
        SetFragmentData();
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
                title.setText("Tests");
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
                if(categoryViewModel!=null) {
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
                }
                break;

            case "Test Paper":
                title.setText("Tests");
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


    @Override
    public void onStart() {
        super.onStart();

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