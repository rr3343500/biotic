package com.example.bioticclasses.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioticclasses.Activity.TakeTestActivity;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.databinding.RowCourcesLayoutBinding;
import com.example.bioticclasses.databinding.RowHomeRecycleLayoutBinding;
import com.example.bioticclasses.modal.category.CatDatum;
import com.example.bioticclasses.modal.notes.DataNotes;
import com.example.bioticclasses.other.SessionManage;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    RowCourcesLayoutBinding binding;
    Context context;
    SessionManage sessionManage;
    NavController navController;
    List<DataNotes> dataNotes;

    public NotesAdapter(Context context, List<DataNotes> dataNotes) {
        this.context = context;
        this.dataNotes = dataNotes;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = RowCourcesLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotesAdapter.ViewHolder holder, int position) {

        holder.name.setText(dataNotes.get(position).getName());
        holder.desc.setText(dataNotes.get(position).getDes());
        holder.eye.setVisibility(View.VISIBLE);
        binding.start.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ApiClient.Image_URL+dataNotes.get(position).getFileName()));
            context.startActivity(browserIntent);
        });
    }

    @Override
    public int getItemCount() {
        return dataNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView arrow,eye;
        RelativeLayout mainview;
        TextView name,desc;
        MaterialCardView start;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mainview= binding.mainview;
            name= binding.subjectName;
            desc= binding.desc;
            start= binding.start;
            arrow=binding.arrow;
            eye= binding.eye;
        }
    }
}
