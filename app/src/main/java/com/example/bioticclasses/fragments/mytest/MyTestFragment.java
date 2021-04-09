package com.example.bioticclasses.fragments.mytest;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bioticclasses.Activity.MyTestsActivity;
import com.example.bioticclasses.Adapter.MytestAdapter;
import com.example.bioticclasses.R;
import com.example.bioticclasses.databinding.MyTestFragmentBinding;

public class MyTestFragment extends Fragment {

    private MyTestViewModel mViewModel;
    MyTestFragmentBinding binding;
    TextView title;

    public static MyTestFragment newInstance() {
        return new MyTestFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= MyTestFragmentBinding.inflate(inflater,container,false);
        title=getActivity().findViewById(R.id.title);
        title.setText("My Tests");
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MyTestViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getTestlist().observe(getActivity(),data -> {
            if(data.isEmpty()){
                binding.meg.setVisibility(View.VISIBLE);
            }else {
                binding.recycle.setAdapter(new MytestAdapter(getActivity(), data));
            }

        });
    }

}