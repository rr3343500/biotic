package com.example.bioticclasses.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioticclasses.Activity.MyTestsActivity;
import com.example.bioticclasses.Activity.ViewTestActivity;
import com.example.bioticclasses.List.CourseList;
import com.example.bioticclasses.databinding.RowMytestlistLayoutBinding;
import com.example.bioticclasses.modal.show_test_list.Datum;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MytestAdapter extends RecyclerView.Adapter<MytestAdapter.Viewholder> {
    Context context;
    RowMytestlistLayoutBinding binding;
    List<CourseList> courseLists;
    List<Datum> tests;
    int subPosition;


    public MytestAdapter(MyTestsActivity context, List<Datum> tests) {
        this.context = context;
        this.tests = tests;
    }



    @NonNull
    @NotNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = RowMytestlistLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Viewholder holder, int position) {
        Datum list = tests.get(position);
        binding.totalmarks.setText("Total Marks : "+list.getTotalMarks().toString());
        binding.obtainedmarks.setText("Marks Obtained : " + list.getMarksObtain().toString());
        if (list.getTestName() != null) {binding.name.setText(list.getTestName().trim());}
        binding.viewtest.setOnClickListener(v -> {
            context.startActivity(new Intent(context, ViewTestActivity.class).putExtra("position",String.valueOf(position)));
        });
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        MaterialButton start;
        public Viewholder(@NonNull RowMytestlistLayoutBinding binding) {
            super(binding.getRoot());
        }
    }
}
