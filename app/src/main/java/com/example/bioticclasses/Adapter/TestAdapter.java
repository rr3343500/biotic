package com.example.bioticclasses.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioticclasses.Activity.TakeTestActivity;
import com.example.bioticclasses.Activity.TestListActivity;
import com.example.bioticclasses.List.CourseList;
import com.example.bioticclasses.databinding.RowTestListLayoutBinding;
import com.example.bioticclasses.modal.mainList.Test;
import com.example.bioticclasses.modal.show_test_list.Datum;
import com.example.bioticclasses.modal.show_test_list.TestShowList;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.Viewholder> {
    private final List<Datum> data;
    Context context;
    RowTestListLayoutBinding binding;
    List<CourseList> courseLists;
    List<TestShowList> testShowLists;
    List<Test> tests;
    int subPosition;


//    public TestAdapter(TestListActivity context, List<Test> tests, int subPosition,List<TestShowList> testShowLists) {
//        this.context = context;
//        this.tests = tests;
//        this.subPosition = subPosition;
//        this.testShowLists=testShowLists;
//    }

    public TestAdapter(TestListActivity context, List<Test> tests, int position, List<Datum> data) {
        this.context = context;
        this.tests = tests;
        this.subPosition = subPosition;
        this.data = data;
    }


    @NonNull
    @NotNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = RowTestListLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Viewholder holder, int position) {
        Test list = tests.get(position);
//        data.get(position).getTestId();


        if (list.getActive().equals("YES")) {
             Boolean State= false;
            for(int i=0 ; i< data.size(); i++) {
                if(data.get(i).getTestId().trim().toUpperCase().equals(list.getId().trim().toUpperCase())){State=true;}
            }
                if(State){
                    binding.name.setText(list.getHeading().trim());
                    binding.marks.setText("Marks " + String.valueOf(list.getTotalQuestion() * list.getCoorectMarks()));
                    holder.start.setOnClickListener(v -> {
                        Toast.makeText(context, "You Have Already Given Test", Toast.LENGTH_SHORT).show();
                    });
                    holder.start.setText("Already Given");
                }else {
                    binding.name.setText(list.getHeading().trim());
                    binding.marks.setText("Marks " + String.valueOf(list.getTotalQuestion() * list.getCoorectMarks()));
                    holder.start.setOnClickListener(v -> {
                        context.startActivity(new Intent(context, TakeTestActivity.class).putExtra("subPosition", String.valueOf(subPosition)).putExtra("testPos", String.valueOf(position)));
                    });


            }

        }
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        MaterialButton start;
        public Viewholder(@NonNull @NotNull RowTestListLayoutBinding binding) {
            super(binding.getRoot());
            start= binding.start;
        }
    }
}














