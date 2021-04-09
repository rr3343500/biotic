package com.example.bioticclasses.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioticclasses.databinding.RowRankLayoutBinding;
import com.example.bioticclasses.modal.testresult.Datum;
import com.example.bioticclasses.modal.testresult.TestResult;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    RowRankLayoutBinding binding;
    Context context;
    List<Datum> resultList;


    public RankAdapter(Context context, List<Datum> resultList) {
        this.context = context;
        this.resultList= resultList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = RowRankLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RankAdapter.ViewHolder holder, int position) {
           holder.score.setText(resultList.get(position).getMarksObtain()+"/"+resultList.get(position).getTotalMarks());
           int Rank= position+1;
           holder.rank.setText(String.valueOf(Rank));
           holder.materialTextView.setText(resultList.get(position).getUserName());

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView materialTextView,rank,score ;
        ImageView imageView;
        public ViewHolder(@NonNull @NotNull RowRankLayoutBinding itemView) {
            super(itemView.getRoot());
            materialTextView= itemView.materialTextView;
            rank= itemView.index;
            score= itemView.score;
        }
    }
}
