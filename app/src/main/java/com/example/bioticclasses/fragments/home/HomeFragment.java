package com.example.bioticclasses.fragments.home;

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
import com.example.bioticclasses.Adapter.HomeAdapter;
import com.example.bioticclasses.Adapter.SliderAdapter;
import com.example.bioticclasses.List.SliderList;
import com.example.bioticclasses.R;
import com.example.bioticclasses.databinding.FragmentHomeBinding;
import com.example.bioticclasses.viewModel.MainActivityViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    MainActivityViewModel mainActivityViewModel;
    List<SliderList> sliderLists = new ArrayList<>();
    TextView title;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SetFragmentData();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void SetFragmentData(){
        title=getActivity().findViewById(R.id.title);
        title.setText("Home");

        homeViewModel.getMainList().observe(getActivity(), data -> {
            for(int i=0; i< data.size();i++){
                sliderLists.add(new SliderList("", data.get(i).getName()));
            }
            if(!sliderLists.isEmpty()){
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
         if(!data.isEmpty()){
             binding.recycle.setAdapter(new HomeAdapter(getActivity(),data));
         }
        });


    }
}