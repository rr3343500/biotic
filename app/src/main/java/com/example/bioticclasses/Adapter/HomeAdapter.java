package com.example.bioticclasses.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bioticclasses.Activity.MainActivity;
import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.databinding.RowCourcesLayoutBinding;
import com.example.bioticclasses.databinding.RowHomeRecycleLayoutBinding;
import com.example.bioticclasses.modal.category.CatDatum;
import com.example.bioticclasses.modal.category.Category;
import com.example.bioticclasses.other.SessionManage;
import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeAdapter  extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    RowHomeRecycleLayoutBinding binding;
    Context context;
    SessionManage sessionManage;
    NavController navController;
    List<CatDatum> categoryList;

    public HomeAdapter(Context mainActivity, List<CatDatum> categoryList) {
        this.context=mainActivity;
        this.categoryList= categoryList;

    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = RowHomeRecycleLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HomeAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(ApiClient.Image_URL+categoryList.get(position).getImgPath())
                .fitCenter()
                .placeholder(context.getResources().getDrawable(R.drawable.placeholder))
                .into(holder.imageView);

        holder.name.setText(categoryList.get(position).getName());
        holder.desc.setText(categoryList.get(position).getDes());

        if(categoryList.get(position).getName().equals("Online Class")){
            holder.cardView.setOnClickListener(v -> {
                Toast.makeText(context, "This feature coming soon!", Toast.LENGTH_SHORT).show();
            });
        }else {
            if(categoryList.get(position).getName().equals("Videos")){
                holder.cardView.setOnClickListener(v -> {
                    navController= Navigation.findNavController(v);
                    navController.navigate(R.id.navigation_vedio_lacture);
                });
            }else {
                holder.cardView.setOnClickListener(v -> {
                    navController= Navigation.findNavController(v);
                    Bundle bundle= new Bundle();
                    bundle.putString("type",categoryList.get(position).getName());
                    navController.navigate(R.id.navigation_category,bundle);
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        ImageView imageView;
        TextView name,desc;
        public ViewHolder(@NonNull @NotNull RowHomeRecycleLayoutBinding binding) {
            super(binding.getRoot());
            cardView = binding.mainview;
            imageView= binding.image;
            name= binding.name;
            desc= binding.desc;
        }
    }
}
