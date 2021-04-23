package com.example.bioticclasses.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioticclasses.R;
import com.example.bioticclasses.databinding.RowTransactionsBinding;
import com.example.bioticclasses.modal.transaction.Transtion;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.Viewholder> {

    RowTransactionsBinding binding;
    List<Transtion>transtions;
    Context context;

    public TransactionAdapter(List<Transtion> transtions, Context context) {
        this.transtions = transtions;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowTransactionsBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.title.setText(transtions.get(position).getSubjectName());
        holder.date.setText("Date : "+transtions.get(position).getDate().substring(0,10));
        holder.amt.setText(context.getString(R.string.Rs)+transtions.get(position).getAmount());
        holder.type.setText("Transaction id : "+transtions.get(position).getTranstionId());


    }

    @Override
    public int getItemCount() {
        return transtions.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        MaterialTextView amt,title,date,type;
        public Viewholder(@NonNull RowTransactionsBinding itemView) {
            super(itemView.getRoot());
            amt= binding.AmtUnr;
            title= binding.transTitle;
            date= binding.date;
            type= binding.paidby;
        }
    }
}
