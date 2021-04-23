package com.example.bioticclasses.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.databinding.RowRemainderBinding;
import com.example.bioticclasses.modal.reminder.Datum;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.Viewholder> {

    RowRemainderBinding binding;
    List<Datum> data;
    Context context;

    public ReminderAdapter(List<Datum> data, Context context) {
        this.data = data;
        this.context=context;
    }

    @NonNull
    @NotNull
    @Override
    public ReminderAdapter.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = RowRemainderBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false );
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReminderAdapter.Viewholder holder, int position) {
        Datum list = data.get(position);
        holder.heading.setText(list.getHeading());
        holder.desc.setText(list.getDescription());
        if(list.getBanner()!=null){
            Glide.with(context)
                    .load(ApiClient.Image_URL + "list.getBanner()")
                    .fitCenter()
                    .placeholder(context.getResources().getDrawable(R.drawable.placeholder))
                    .into(holder.imageView);
        }else {holder.imageView.setVisibility(View.GONE);}
        if(list.getCreatedAt()!=null){
            holder.date.setText(list.getCreatedAt().substring(0,10));
        }else {holder.date.setVisibility(View.GONE);}
        if(list.getSubject()!=null){
            holder.sub.setText(list.getSubject());
        }else {holder.sub.setVisibility(View.GONE);}
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView heading, desc,sub,date;
        ImageView imageView;
        public Viewholder(@NonNull @NotNull RowRemainderBinding itemView) {
            super(itemView.getRoot());
            heading= binding.subjectName;
            desc= binding.desc;
            sub= binding.sub;
            date= binding.date;
            imageView= binding.img;
        }
    }
}
