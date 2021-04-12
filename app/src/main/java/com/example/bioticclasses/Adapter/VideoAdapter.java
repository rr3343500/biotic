package com.example.bioticclasses.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioticclasses.Activity.YoutubePlayerActivity;
import com.example.bioticclasses.databinding.RowCourcesLayoutBinding;
import com.example.bioticclasses.modal.video.Datum;
import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.Viewbinding> {

    Context context;
    RowCourcesLayoutBinding binding;
    List<Datum> data;

    public VideoAdapter(List<Datum> data) {
        this.data = data;
    }


    @NonNull
    @NotNull
    @Override
    public Viewbinding onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        binding = RowCourcesLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new Viewbinding(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VideoAdapter.Viewbinding holder, int position) {
        Datum list = data.get(position);
        holder.name.setText(list.getName());
        holder.desc.setText(list.getDes());
        binding.start.setOnClickListener(v -> {
            context.startActivity(new Intent(context, YoutubePlayerActivity.class).putExtra("id", String.valueOf(list.getVedioLink())));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Viewbinding extends RecyclerView.ViewHolder {

        ImageView imageView;
        RelativeLayout mainview;
        TextView name, desc;
        MaterialCardView start;

        public Viewbinding(@NonNull @NotNull RowCourcesLayoutBinding itemView) {
            super(itemView.getRoot());

            mainview = binding.mainview;
            name = binding.subjectName;
            desc = binding.desc;
            start = binding.start;
        }
    }
}
