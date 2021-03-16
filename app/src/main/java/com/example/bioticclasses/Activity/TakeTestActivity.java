package com.example.bioticclasses.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.example.bioticclasses.List.QuestionList;
import com.example.bioticclasses.R;
import com.example.bioticclasses.databinding.ActivityTakeTestBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TakeTestActivity extends AppCompatActivity {
    ActivityTakeTestBinding binding;
    List<QuestionList>questionLists= new ArrayList<>();
    int question= 0;
    JSONObject answersheet;
    Boolean action= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityTakeTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        answersheet= new JSONObject();
        timer();
        ActivityAction();
        for (int i =0 ;i<=10;i++){
            questionLists.add(new QuestionList("your question no is  "+i, "patato"+i,"tomato"+i,"onion"+i,"chilli"+i  ));
        }
        binding.question.setText("Q."+question+" "+questionLists.get(question).getQues());
        binding.option1.setText(questionLists.get(question).getOption1()); binding.option1.setTag(questionLists.get(question).getOption1());
        binding.option2.setText(questionLists.get(question).getOption2()); binding.option2.setTag(questionLists.get(question).getOption2());
        binding.option3.setText(questionLists.get(question).getOption3()); binding.option3.setTag(questionLists.get(question).getOption3());
        binding.option4.setText(questionLists.get(question).getOption4()); binding.option4.setTag(questionLists.get(question).getOption4());



    }

    private void ActivityAction(){
        binding.next.setOnClickListener(v -> {question++;Next();});
        binding.prev.setOnClickListener(v -> {question--;Prev();});
        binding.option.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton= findViewById(checkedId);
            try {
                if(radioButton!=null && action){
                    answersheet.put(String.valueOf(question),radioButton.getTag().toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("json ", String.valueOf(answersheet));
        });
        binding.clear.setOnClickListener(v -> {binding.option.clearCheck();deletejson(String.valueOf(question));});
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
      action=false;
      if(question==questionLists.size()-1){binding.next.setEnabled(false); }else {binding.prev.setEnabled(true);Loading();}
      binding.question.setText("Q"+question+"  " +questionLists.get(question).getQues());
      binding.option1.setText(questionLists.get(question).getOption1()); binding.option1.setTag(questionLists.get(question).getOption1());
      binding.option2.setText(questionLists.get(question).getOption2()); binding.option2.setTag(questionLists.get(question).getOption2());
      binding.option3.setText(questionLists.get(question).getOption3()); binding.option3.setTag(questionLists.get(question).getOption3());
      binding.option4.setText(questionLists.get(question).getOption4()); binding.option4.setTag(questionLists.get(question).getOption4());
            if(!searchJson(String.valueOf(question)).isEmpty()){
                RadioButton button = binding.option.findViewWithTag(searchJson(String.valueOf(question)));
                if(button!=null){button.setChecked(true);}
            }else
                {
                    binding.option.clearCheck();
                }
      action=true;
    }

    private void Prev(){
        action=false;
        if(question==0){binding.prev.setEnabled(false);}else {binding.next.setEnabled(true);Loading();}
        binding.question.setText("Q"+question+  "  " +questionLists.get(question).getQues());
        binding.option1.setText(questionLists.get(question).getOption1()); binding.option1.setTag(questionLists.get(question).getOption1());
        binding.option2.setText(questionLists.get(question).getOption2()); binding.option2.setTag(questionLists.get(question).getOption2());
        binding.option3.setText(questionLists.get(question).getOption3()); binding.option3.setTag(questionLists.get(question).getOption3());
        binding.option4.setText(questionLists.get(question).getOption4());  binding.option4.setTag(questionLists.get(question).getOption4());
            if(!searchJson(String.valueOf(question)).isEmpty()){
                String s=searchJson(String.valueOf(question));
                RadioButton button = binding.option.findViewWithTag(s);
                if(button!=null){button.setChecked(true);}
            }else
            {
                binding.option.clearCheck();
            }
       action= true;
    }

    private void Loading() {
        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
                binding.mainview.setAlpha((float) 0.5);
                binding.progress.setVisibility(View.VISIBLE);
            }
            public void onFinish() {
                binding.progress.setVisibility(View.GONE);
                binding.mainview.setAlpha(1);
            }
        }.start();
    }


    private String searchJson(String id){
        try {
            return answersheet.getString(id);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void deletejson(String id){
        answersheet.remove(id);
        Log.e("dsfsd",answersheet.toString());
    }
}