package com.example.bioticclasses.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioticclasses.R;
import com.example.bioticclasses.databinding.RowMyTestQuestionLayoutBinding;
import com.example.bioticclasses.modal.show_test_list.Response;
import com.google.android.material.radiobutton.MaterialRadioButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TestShowAdapter extends RecyclerView.Adapter<TestShowAdapter.Viewholder> {
    List<com.example.bioticclasses.modal.show_test_list.Response> response;
    RowMyTestQuestionLayoutBinding binding;
    Context context;
    private static final String TAG = "TestShowAdapter";

    public TestShowAdapter(List<Response> response) {
        this.response = response;
        setHasStableIds(true);
    }

    @NonNull
    @NotNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new Viewholder(LayoutInflater.from(context).inflate(R.layout.row_my_test_question_layout, parent, false));
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TestShowAdapter.Viewholder holder, int position) {
        Response list = response.get(position);
        int pos = (position + 1);
        holder.ques.setText("Q." + pos);

        Log.e("assdg",list.getUop());
        switch (list.getType().toUpperCase()) {
            case "TEXT":
                holder.question.setText(list.getQuestion());
                holder.t1.setText(list.getOp1());
                holder.t2.setText(list.getOp2());
                holder.t3.setText(list.getOp3());
                holder.t4.setText(list.getOp4());
                int q = position + 1;
                holder.ques.setText("Q." + q);
                switch (list.getUop()) {
                    case "op1":
                        holder.ans1.setChecked(true);
                        if(list.getCop().toUpperCase().trim().equals("OP1")){
                            holder.correct1.setVisibility(View.VISIBLE);
                        }else {
                            holder.correct1.setImageResource(R.drawable.wrong_icon);
                            holder.correct1.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "op2":
                        holder.ans2.setChecked(true);
                        if(list.getCop().toUpperCase().trim().equals("OP2")){
                            holder.correct2.setVisibility(View.VISIBLE);
                        }else {
                            holder.correct2.setImageResource(R.drawable.wrong_icon);
                            holder.correct2.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "op3":
                        holder.ans3.setChecked(true);
                        if(list.getCop().toUpperCase().trim().equals("OP3")){
                            holder.correct3.setVisibility(View.VISIBLE);
                        }else {
                            holder.correct3.setImageResource(R.drawable.wrong_icon);
                            holder.correct3.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "op4":
                        holder.ans4.setChecked(true);
                        if(list.getCop().toUpperCase().trim().equals("OP4")){
                            holder.correct4.setVisibility(View.VISIBLE);
                        }else {
                            holder.correct4.setImageResource(R.drawable.wrong_icon);
                            holder.correct4.setVisibility(View.VISIBLE);
                        }
                        break;
                }


                switch (list.getCop()){
                    case "op1":
                        holder.accepted.setText("Correct answer - " + list.getOp1());
                        break;
                    case "op2":
                        holder.accepted.setText("Correct answer - " + list.getOp2());
                        break;
                    case "op3":
                        holder.accepted.setText("Correct answer - " + list.getOp3());
                        break;
                    case "op4":
                        holder.accepted.setText("Correct answer - " + list.getOp4());
                        break;

                }

//                if (list.getCop().toUpperCase().trim().equals("OP1"))
//                    holder.correct1.setVisibility(View.VISIBLE);
//                else holder.correct1.setVisibility(View.GONE);
//
//                if (list.getCop().toUpperCase().trim().equals("OP2"))
//                    holder.correct2.setVisibility(View.VISIBLE);
//                else holder.correct2.setVisibility(View.GONE);
//
//                if (list.getCop().toUpperCase().trim().equals("OP3"))
//                    holder.correct3.setVisibility(View.VISIBLE);
//                else holder.correct3.setVisibility(View.GONE);
//
//                if (list.getCop().toUpperCase().trim().equals("OP4"))
//                    holder.correct4.setVisibility(View.VISIBLE);
//                else holder.correct4.setVisibility(View.GONE);



                break;
            case "IMAGE":
                break;
        }

    }

    @Override
    public int getItemCount() {
        return response.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView ques, question, t1, t2, t3, t4, accepted;
        MaterialRadioButton ans1, ans2, ans3, ans4;
        ImageView correct1, correct2, correct3, correct4;

        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            ques = itemView.findViewById(R.id.ques);
            question = itemView.findViewById(R.id.question);
            ans1 = itemView.findViewById(R.id.ans1);
            ans2 = itemView.findViewById(R.id.ans2);
            ans3 = itemView.findViewById(R.id.ans3);
            ans4 = itemView.findViewById(R.id.ans4);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            t4 = itemView.findViewById(R.id.t4);
            correct1 = itemView.findViewById(R.id.correct1);
            correct2 = itemView.findViewById(R.id.correct2);
            correct3 = itemView.findViewById(R.id.correct3);
            correct4 = itemView.findViewById(R.id.correct4);
            accepted = itemView.findViewById(R.id.accepted);
        }
    }
}




































