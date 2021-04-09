package com.example.bioticclasses.fragments.notes;

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
import com.example.bioticclasses.databinding.FragmentNotesFragmentBinding;

public class FragmentNotes extends Fragment {
    FragmentNotesFragmentBinding binding;
    private FragmentNotesViewModel mViewModel;
    TextView title;

    public static FragmentNotes newInstance() {
        return new FragmentNotes();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentNotesFragmentBinding.inflate(inflater,container,false);
        title=getActivity().findViewById(R.id.title);
        title.setText("Notes");
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentNotesViewModel.class);
        // TODO: Use the ViewModel
    }

}