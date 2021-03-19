package com.example.bioticclasses.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bioticclasses.Activity.TestListActivity;
import com.example.bioticclasses.List.CourseList;
import com.example.bioticclasses.R;
import com.example.bioticclasses.databinding.RowCourcesLayoutBinding;
import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.Viewholder> {
    Context context;
    RowCourcesLayoutBinding binding;
    List<CourseList> courseLists;

    public CoursesAdapter(Context context, List<CourseList> courseLists) {
        this.context = context;
        this.courseLists = courseLists;
    }

    @NonNull
    @NotNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = RowCourcesLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewholder(binding) ;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CoursesAdapter.Viewholder holder, int position) {
        Glide.with(context).load("https://d2wvmrjymyrujw.cloudfront.net/media/images/tutorial/tutorials/bites-of-python/Patreon_PYTHON_Course_titleImage_v02.jpg").placeholder(R.drawable.logo).into(holder.imageView);
        holder.mainview.setOnClickListener(v -> {
            context.startActivity(new Intent(context, TestListActivity.class).putExtra("id","1"));
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ConstraintLayout mainview;
        public Viewholder(@NonNull @NotNull RowCourcesLayoutBinding binding) {
            super(binding.getRoot());
            imageView= binding.image;
            mainview= binding.mainview;
        }
    }
}
