package com.example.bioticclasses.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioticclasses.databinding.RowCourcesLayoutBinding;
import com.example.bioticclasses.modal.testlist.Datum;
import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LactureAdapter extends RecyclerView.Adapter<LactureAdapter.ViewHolder> {
    Context context;
    RowCourcesLayoutBinding binding;
    List<Datum> testLists;
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = RowCourcesLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LactureAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        RelativeLayout mainview;
        TextView name,desc;
        MaterialCardView start;

        public ViewHolder(@NonNull @NotNull RowCourcesLayoutBinding binding) {
            super(binding.getRoot());
            mainview= binding.mainview;
            name= binding.subjectName;
            desc= binding.desc;
            start= binding.start;
        }
    }
}
