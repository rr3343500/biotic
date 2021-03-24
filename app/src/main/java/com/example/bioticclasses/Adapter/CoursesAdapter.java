package com.example.bioticclasses.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioticclasses.Activity.MainActivity;
import com.example.bioticclasses.Activity.TestListActivity;
import com.example.bioticclasses.databinding.RowCourcesLayoutBinding;
import com.example.bioticclasses.modal.mainList.Datum;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.Viewholder> {
    Context context;
    RowCourcesLayoutBinding binding;
    List<com.example.bioticclasses.modal.mainList.Datum> courseLists;


    public CoursesAdapter(MainActivity context, List<com.example.bioticclasses.modal.mainList.Datum> class_) {
        this.context = context;
        this.courseLists = class_;
    }

    @NonNull
    @NotNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = RowCourcesLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CoursesAdapter.Viewholder holder, int position) {

        Datum list = courseLists.get(position);
        holder.mainview.setOnClickListener(v -> {
            if (list.getTests().isEmpty())
                Toast.makeText(context, "No Tests", Toast.LENGTH_SHORT).show();
            else
                context.startActivity(new Intent(context, TestListActivity.class).putExtra("pos", String.valueOf(position)));
        });
        binding.subjectName.setText(list.getNameEn());
        if (position >= 9) binding.counting.setText(String.valueOf(position + 1));
        else binding.counting.setText("0" + String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return courseLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ConstraintLayout mainview;

        public Viewholder(@NonNull @NotNull RowCourcesLayoutBinding binding) {
            super(binding.getRoot());
            mainview= binding.mainview;
        }
    }
}
