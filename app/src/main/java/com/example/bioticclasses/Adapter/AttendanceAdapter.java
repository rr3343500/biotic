package com.example.bioticclasses.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bioticclasses.databinding.RowAttendanceBinding;
import com.example.bioticclasses.modal.attendence.Datum;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.Viewholder> {

    RowAttendanceBinding binding;
    List<com.example.bioticclasses.modal.attendence.Datum> data;


    public AttendanceAdapter(List<com.example.bioticclasses.modal.attendence.Datum> data) {
        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = RowAttendanceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AttendanceAdapter.Viewholder holder, int position) {
        Datum list = data.get(position);
        if(!list.getStudents().isEmpty()){
        if(list.getStudents().get(0).getStatus().toUpperCase().equals("PRESENT")){
          holder.checkBox.setChecked(true);
        }}
        if(list.getDate()!=null){
            holder.date.setText(list.getDate().substring(0,10));
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        MaterialCheckBox checkBox;
        MaterialTextView date;
        public Viewholder(@NonNull @NotNull RowAttendanceBinding itemView) {
            super(itemView.getRoot());
            checkBox = binding.materialCheck;
            date = binding.stuname;

        }
    }
}




