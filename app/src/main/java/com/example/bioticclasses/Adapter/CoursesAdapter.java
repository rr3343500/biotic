package com.example.bioticclasses.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioticclasses.Activity.TakeTestActivity;
import com.example.bioticclasses.databinding.RowCourcesLayoutBinding;
import com.example.bioticclasses.modal.testlist.Datum;
import com.example.bioticclasses.other.SessionManage;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.Viewholder> {
    Context context;
    RowCourcesLayoutBinding binding;
    List<Datum> testLists;



    public CoursesAdapter(Context context,  List<Datum> testLists ) {
        this.context = context;
//        this.courseLists = class_;
        this.testLists= testLists;
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

        holder.name.setText(testLists.get(position).getHeading());
        holder.desc.setText(testLists.get(position).getDescription());
        binding.start.setOnClickListener(v -> {
            if(testLists.get(position).getQuestions().isEmpty()){
                Snackbar snackbar = Snackbar
                        .make(v, "No Question Add Yet", Snackbar.LENGTH_LONG);
                snackbar.show();
            }else {
                context.startActivity(new Intent(context, TakeTestActivity.class).putExtra("subPosition", String.valueOf(position)).putExtra("testPos", String.valueOf(position)));
            }

        });

//        Datum list = courseLists.get(position);
//        holder.mainview.setOnClickListener(v -> {
//            if (list.getTests().isEmpty())
//                Toast.makeText(context, "No Tests", Toast.LENGTH_SHORT).show();
//            else
//                if(sessionManage.active()){
//                    context.startActivity(new Intent(context, TestListActivity.class).putExtra("pos", String.valueOf(position)));
//                }else {
//                    Toast.makeText(context, "Your Account is Not Active", Toast.LENGTH_SHORT).show();
//                }
//
//
//        });
//        binding.subjectName.setText(list.getNameEn());
//        if (position >= 9) binding.counting.setText(String.valueOf(position + 1));
//        else binding.counting.setText("0" + String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return testLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        RelativeLayout mainview;
        TextView name,desc;
        MaterialCardView start;

        public Viewholder(@NonNull @NotNull RowCourcesLayoutBinding binding) {
            super(binding.getRoot());
            mainview= binding.mainview;
            name= binding.subjectName;
            desc= binding.desc;
            start= binding.start;
        }
    }
}
