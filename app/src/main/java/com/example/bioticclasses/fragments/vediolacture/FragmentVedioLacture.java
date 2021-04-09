package com.example.bioticclasses.fragments.vediolacture;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bioticclasses.R;
import com.example.bioticclasses.databinding.FragmentVedioLactureFragmentBinding;

public class FragmentVedioLacture extends Fragment {
    FragmentVedioLactureFragmentBinding binding;
    private FragmentVedioLactureViewModel mViewModel;
    TextView title;

    public static FragmentVedioLacture newInstance() {
        return new FragmentVedioLacture();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentVedioLactureFragmentBinding.inflate(inflater,container, false);
        title=getActivity().findViewById(R.id.title);
        title.setText("Lactures");
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentVedioLactureViewModel.class);
        // TODO: Use the ViewModel
    }

}