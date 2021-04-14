package com.example.bioticclasses.fragments.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.bioticclasses.Activity.MainActivity;
import com.example.bioticclasses.Activity.ScoreActivity;
import com.example.bioticclasses.Adapter.HomeAdapter;
import com.example.bioticclasses.Adapter.SliderAdapter;
import com.example.bioticclasses.List.SliderList;
import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.FragmentHomeBinding;
import com.example.bioticclasses.modal.userprofile.UserProfile;
import com.example.bioticclasses.other.SessionManage;
import com.example.bioticclasses.viewModel.MainActivityViewModel;
import com.google.gson.JsonObject;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    MainActivityViewModel mainActivityViewModel;
    List<SliderList> sliderLists = new ArrayList<>();
    TextView title;
    SessionManage sessionManage;
    AlertDialog alertDialog;
    private  JSONObject jsonObject1;
    private  JsonObject finalsubject1;
    static BiotechInterface biotechInterface;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sessionManage= new SessionManage(getActivity());
        biotechInterface = ApiClient.getClient().create(BiotechInterface.class);
        SetFragmentData();
        Requestprofile();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void SetFragmentData() {
        title = getActivity().findViewById(R.id.title);
        title.setText("Home");

        homeViewModel.getMainList().observe(getActivity(), data -> {
            for (int i = 0; i < data.size(); i++) {
                sliderLists.add(new SliderList("", data.get(i).getName()));
            }
            if (!sliderLists.isEmpty()) {
                SliderAdapter adapter = new SliderAdapter(getActivity(), sliderLists);
                binding.imageSlider.setSliderAdapter(adapter);
                binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
                binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                binding.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                binding.imageSlider.setIndicatorSelectedColor(Color.WHITE);
                binding.imageSlider.setIndicatorUnselectedColor(Color.GRAY);
                binding.imageSlider.setScrollTimeInSec(4);
                binding.imageSlider.startAutoCycle();
            }
        });

        homeViewModel.getCatList().observe(getActivity(), data -> {
            if (!data.isEmpty()) {
                binding.recycle.setAdapter(new HomeAdapter(getActivity(), data));
            }
        });


//        homeViewModel.data().observe(getActivity(), data -> {
//            if (data.getActive().toUpperCase().equals("YES")) {
//
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//                alertDialogBuilder.setMessage("You are under varification.");
//                alertDialog = alertDialogBuilder.create();
//                alertDialog.setCanceledOnTouchOutside(false);
//                alertDialog.setCancelable(false);
//                alertDialog.show();
//
//            }
//
//        });
    }
    
    private  void Requestprofile(){
        finalsubject1 = new JsonObject();
            finalsubject1.addProperty("user_id",sessionManage.getUserDetails().get("userid") );
        biotechInterface.USER_PROFILE_CALL(finalsubject1).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful()){
                    if (!response.body().getResult().getError() && response.body().getResult().getErrorCode() == 200) {
                        if (response.body().getResult().getData() != null) {
                            if (response.body().getResult().getData().getActive().toUpperCase().equals("NO")) {

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                alertDialogBuilder.setMessage("Your are under Varification");
                                alertDialogBuilder.setPositiveButton("Exit",
                                        (arg0, arg1) -> {
                                            getActivity().moveTaskToBack(true);
                                            getActivity().finish();
                                        });
                                alertDialog = alertDialogBuilder.create();
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.setCancelable(false);
                                alertDialog.show();
                            }

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {

            }
        });
    }
}