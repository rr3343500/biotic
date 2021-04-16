package com.example.bioticclasses.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioticclasses.databinding.RowTransactionsBinding;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.Viewholder> {

    RowTransactionsBinding binding;

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowTransactionsBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false);
        return new Viewholder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public Viewholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
