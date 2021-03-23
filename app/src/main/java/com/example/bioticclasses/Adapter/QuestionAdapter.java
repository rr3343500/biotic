package com.example.bioticclasses.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioticclasses.Activity.TakeTestActivity;
import com.example.bioticclasses.List.CourseList;
import com.example.bioticclasses.List.QuestionList;
import com.example.bioticclasses.databinding.RowQuestionLayoutBinding;
import com.google.android.material.card.MaterialCardView;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.Viewholder> {
    TakeTestActivity context;
    RowQuestionLayoutBinding binding;
    List<QuestionList> questionLists;
    ChangeQuestion changeQuestion;

    public QuestionAdapter(TakeTestActivity context,ChangeQuestion changeQuestion) {
        this.context = context;
        this.changeQuestion=changeQuestion;

    }

    @NonNull
    @NotNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = RowQuestionLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Viewholder holder, int position) {
        holder.textView.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        MaterialCardView cardView;
        public Viewholder(@NonNull @NotNull RowQuestionLayoutBinding binding) {
            super(binding.getRoot());
             textView= binding.number;
             cardView= binding.mainview;
             binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            changeQuestion.onitemClick(getAdapterPosition(),cardView);
        }
    }


    public interface ChangeQuestion{
        void onitemClick(int position,MaterialCardView cardView);
    }

}