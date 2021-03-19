package com.example.bioticclasses.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bioticclasses.Activity.TakeTestActivity;
import com.example.bioticclasses.List.CourseList;
import com.example.bioticclasses.databinding.RowTestListLayoutBinding;
import com.google.android.material.button.MaterialButton;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TestAdapter  extends RecyclerView.Adapter<TestAdapter.Viewholder> {
    Context context;
    RowTestListLayoutBinding binding;
    List<CourseList> courseLists;

    public TestAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = RowTestListLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewholder(binding) ;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Viewholder holder, int position) {
       holder.start.setOnClickListener(v -> {context.startActivity(new Intent(context, TakeTestActivity.class));});
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        MaterialButton start;
        public Viewholder(@NonNull @NotNull RowTestListLayoutBinding binding) {
            super(binding.getRoot());
            start= binding.start;
        }
    }
}
