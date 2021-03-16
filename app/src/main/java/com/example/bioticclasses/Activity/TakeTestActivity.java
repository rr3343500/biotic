package com.example.bioticclasses.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;

import com.example.bioticclasses.List.QuestionList;
import com.example.bioticclasses.databinding.ActivityTakeTestBinding;

import java.util.ArrayList;
import java.util.List;

public class TakeTestActivity extends AppCompatActivity {
    ActivityTakeTestBinding binding;
    List<QuestionList>questionLists= new ArrayList<>();
    int question= 0;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityTakeTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("loading");
        timer();
        ActivityAction();

        for (int i =0 ;i<=10;i++){
            questionLists.add(new QuestionList("your question no is  "+i, "patato"+i,"tomato"+i,"onion"+i,"chilli"+i  ));
        }

    }

    private void ActivityAction(){
        binding.next.setOnClickListener(v -> {question++;Next();});
        binding.prev.setOnClickListener(v -> {question--;Prev();});
    }

    private void timer() {
        new CountDownTimer(90000, 1000) {

            public void onTick(long millisUntilFinished) {
                binding.time.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                binding.time.setText("over");
            }

        }.start();
    }


    private void Next(){
      if(question==questionLists.size()-1){binding.next.setEnabled(false); }else {binding.prev.setEnabled(true);progressDialog.show();}
      binding.question.setText("Q. "+question+"  " +questionLists.get(question).getQues());
      binding.option1.setText("Q. "+question+"  " +questionLists.get(question).getOption1());
      binding.option2.setText("Q. "+question+"  " +questionLists.get(question).getOption2());
      binding.option3.setText("Q. "+question+"  " +questionLists.get(question).getOption3());
      binding.option4.setText("Q. "+question+"  " +questionLists.get(question).getOption4());
      progressDialog.cancel();
    }

    private void Prev(){
        if(question==0){binding.prev.setEnabled(false);}else {binding.next.setEnabled(true);progressDialog.show();}
        binding.question.setText("Q. "+question+"  " +questionLists.get(question).getQues());
        binding.option1.setText("Q. "+question+"  " +questionLists.get(question).getOption1());
        binding.option2.setText("Q. "+question+"  " +questionLists.get(question).getOption2());
        binding.option3.setText("Q. "+question+"  " +questionLists.get(question).getOption3());
        binding.option4.setText("Q. "+question+"  " +questionLists.get(question).getOption4());
        progressDialog.cancel();
    }
}