package com.example.bioticclasses.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioticclasses.Activity.ViewTestActivity;
import com.example.bioticclasses.databinding.RowMyTestQuestionLayoutBinding;
import com.example.bioticclasses.modal.mytest.Response;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyTestViewAdapter extends RecyclerView.Adapter<MyTestViewAdapter.Viewholder> {
        Context context;
        RowMyTestQuestionLayoutBinding binding;
        List<Response> responses;
        int subPosition;
        private static final String TAG = "MyTestViewAdapter";

        public MyTestViewAdapter(ViewTestActivity context, List<Response> responses) {
                this.context = context;
                this.responses = responses;
                }



        @NonNull
        @NotNull
        @Override
        public Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                binding = RowMyTestQuestionLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new Viewholder(binding);
                }

        @Override
        public void onBindViewHolder(@NonNull @NotNull Viewholder holder, int position) {

                Response list = responses.get(position);
                Log.e(TAG, "onBindViewHolder: " + list.getQuestion() );

  /*              switch (responses.get(position).getType().toUpperCase()) {
                        case "TEXT":
                                binding.question.setText(responses.get(position).getQuestion());
                                binding.t1.setText(responses.get(position).getOp1());
                                binding.t2.setText(responses.get(position).getOp2());
                                binding.t3.setText(responses.get(position).getOp3());
                                binding.t4.setText(responses.get(position).getOp4());
                                binding.accepted.setText("Accepted answer - " + responses.get(position).getCop());
                                int q = position + 1;
                                binding.ques.setText("Q." + q);
                              switch (responses.get(position).getUop()){
                                      case "op1" :
                                              binding.ans1.setChecked(true);
                                              break;
                                      case "op2" :
                                              binding.ans2.setChecked(true);
                                              break;
                                      case "op3" :
                                              binding.ans3.setChecked(true);
                                              break;
                                      case "op4" :
                                              binding.ans3.setChecked(true);
                                              break;
                              }
                              switch (responses.get(position).getCop()){
                                      case "op1" :
                                              binding.correct1.setVisibility(View.VISIBLE);
                                              break;
                                      case "op2" :
                                              binding.correct2.setVisibility(View.VISIBLE);
                                              break;
                                      case "op3" :
                                              binding.correct3.setVisibility(View.VISIBLE);
                                              break;
                                      case "op4" :
                                              binding.correct4.setVisibility(View.VISIBLE);
                                              break;
                              }

                              break;
                      case "IMAGE" :
                              break;
              }
*/

        }

        @Override
        public int getItemCount() {
                return responses.size();
                }

        public class Viewholder extends RecyclerView.ViewHolder {
            MaterialButton start;

            public Viewholder(@NonNull RowMyTestQuestionLayoutBinding binding) {
                super(binding.getRoot());
            }
        }
}
