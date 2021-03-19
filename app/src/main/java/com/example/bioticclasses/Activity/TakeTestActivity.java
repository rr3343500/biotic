package com.example.bioticclasses.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bioticclasses.Adapter.QuestionAdapter;
import com.example.bioticclasses.List.QuestionList;
import com.example.bioticclasses.R;
import com.example.bioticclasses.databinding.ActivityTakeTestBinding;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TakeTestActivity extends AppCompatActivity implements QuestionAdapter.ChangeQuestion {
    ActivityTakeTestBinding binding;
    List<QuestionList>questionLists= new ArrayList<>();
    int question= 0;
    JSONObject answersheet;
    Boolean action= true;
    Boolean Test= false;
    Boolean Warning= false;
    CountDownTimer countDownTimer;
    String msg;
    QuestionAdapter.ChangeQuestion changeQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityTakeTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Python Tests");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        answersheet= new JSONObject();
        ActivityAction();
        for (int i =0 ;i<10;i++){
            questionLists.add(new QuestionList("your question no is  "+i, "patato"+i,"tomato"+i,"onion"+i,"chilli"+i  ));
        }

        binding.recycle.setAdapter(new QuestionAdapter(this,this));

        binding.question.setText("Q."+question+" "+questionLists.get(question).getQues());
        binding.t1.setText(questionLists.get(question).getOption1()); binding.ans1.setTag(questionLists.get(question).getOption1());
        binding.t2.setText(questionLists.get(question).getOption2()); binding.ans2.setTag(questionLists.get(question).getOption2());
        binding.t3.setText(questionLists.get(question).getOption3()); binding.ans3.setTag(questionLists.get(question).getOption3());
        binding.t4.setText(questionLists.get(question).getOption4()); binding.ans4.setTag(questionLists.get(question).getOption4());

        StartTest();

    }





    private void ActivityAction(){
        binding.next.setOnClickListener(v -> {question++;Next();});
        binding.prev.setOnClickListener(v -> {question--;Prev();});
        binding.clear.setOnClickListener(v -> {clearResponse();;deletejson(String.valueOf(question));});
        binding.submit.setOnClickListener(v -> { Submit(binding.getRoot());});
    }

    private void timer() {
      countDownTimer =  new CountDownTimer(15000, 1000) {

            public void onTick(long millisUntilFinished) {
                binding.time.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                TimeUp(binding.getRoot());
            }

        }.start();
    }


    private void Next(){
      action=false;
      clearResponse();
      if(question==questionLists.size()-1){binding.next.setEnabled(false);binding.submit.setVisibility(View.VISIBLE);}else {binding.prev.setEnabled(true);Loading();binding.submit.setVisibility(View.GONE);}
      binding.question.setText("Q"+question+"  " +questionLists.get(question).getQues());
      binding.t1.setText(questionLists.get(question).getOption1()); binding.ans1.setTag(questionLists.get(question).getOption1());
      binding.t2.setText(questionLists.get(question).getOption2()); binding.ans2.setTag(questionLists.get(question).getOption2());
      binding.t3.setText(questionLists.get(question).getOption3()); binding.ans3.setTag(questionLists.get(question).getOption3());
      binding.t4.setText(questionLists.get(question).getOption4()); binding.ans4.setTag(questionLists.get(question).getOption4());
            if(!searchJson(String.valueOf(question)).isEmpty()){
                RadioButton button = binding.newoption.findViewWithTag(searchJson(String.valueOf(question)));
                if(button!=null){button.setChecked(true);}
            }else
                {
                    binding.newoption.clearCheck();
                }
      action=true;
    }

    private void Prev(){
        action=false;
        clearResponse();
        if(question==0){binding.prev.setEnabled(false);binding.submit.setVisibility(View.GONE);}else {binding.next.setEnabled(true);Loading();binding.submit.setVisibility(View.GONE);}
        binding.question.setText("Q"+question+  "  " +questionLists.get(question).getQues());
        binding.t1.setText(questionLists.get(question).getOption1()); binding.ans1.setTag(questionLists.get(question).getOption1());
        binding.t2.setText(questionLists.get(question).getOption2()); binding.ans2.setTag(questionLists.get(question).getOption2());
        binding.t3.setText(questionLists.get(question).getOption3()); binding.ans3.setTag(questionLists.get(question).getOption3());
        binding.t4.setText(questionLists.get(question).getOption4()); binding.ans4.setTag(questionLists.get(question).getOption4());
            if(!searchJson(String.valueOf(question)).isEmpty()){
                String s=searchJson(String.valueOf(question));
                RadioButton button = binding.newoption.findViewWithTag(s);
                if(button!=null){button.setChecked(true);}
            }else
            {
                binding.newoption.clearCheck();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void TimeUp(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Time Up Please Submit");
                alertDialogBuilder.setPositiveButton("Submit",
                        (arg0, arg1) ->{startActivity(new Intent(this,ScoreActivity.class));});
                AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public void Submit(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to submit.");
        alertDialogBuilder.setPositiveButton("Submit",
                (arg0, arg1) ->{startActivity(new Intent(this,ScoreActivity.class));});
        alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure that you want to exit Test. if you click yes your test auto submitted");
        alertDialogBuilder.setPositiveButton("yes",
                (arg0, arg1) ->{countDownTimer.cancel();startActivity(new Intent(this,TestListActivity.class));finish();});
        alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(Test) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("If you change the screen you test is auto submit and exit");
            alertDialogBuilder.setPositiveButton("exit",
                    (arg0, arg1) -> {
                    });
            alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    protected void onResume() {

        if(Test) {
            if(Warning) {
                 msg="we Have already give warning.";
            }else {
                 msg="If you another time change the screen you test is auto submit and exit.";
            }
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(msg);
            if(Warning) {
                alertDialogBuilder.setPositiveButton("exit",
                        (arg0, arg1) -> {
                            countDownTimer.cancel();
                            startActivity(new Intent(this, TestListActivity.class));
                            finish();
                        });
            }else {
                alertDialogBuilder.setNegativeButton("close", (dialog, which) -> {
                    Warning= true;
                    dialog.cancel();

                });
            }
            AlertDialog alertDialog = alertDialogBuilder.create();
            if(Warning){ alertDialog.setCanceledOnTouchOutside(false);}
            alertDialog.show();
        }

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(Test) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("If you change the screen you test is auto submit and exit");
            alertDialogBuilder.setPositiveButton("exit",
                    (arg0, arg1) -> {
                        super.onDestroy();
                    });
            alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private void StartTest(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(" Start Test");
        alertDialogBuilder.setNegativeButton("Start", (dialog, which) -> {
            Test=true;
            timer();
            dialog.cancel();
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public void OnOptionChecked(View view) {
        RadioButton radioButton= findViewById(view.getId());
        switch (view.getId()) {
            case R.id.ans1:
                if (binding.ans1.isChecked()) {
                    try {
                        if(radioButton!=null && action){
                            answersheet.put(String.valueOf(question),radioButton.getTag().toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("json ", String.valueOf(answersheet));

                    binding.ans1.setChecked(true);
                    binding.ans2.setChecked(false);
                    binding.ans3.setChecked(false);
                    binding.ans4.setChecked(false);
                    onSelectOption(question);

                }
                break;
            case R.id.ans2:
                if (binding.ans2.isChecked()) {
                    try {
                        if(radioButton!=null && action){
                            answersheet.put(String.valueOf(question),radioButton.getTag().toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("json ", String.valueOf(answersheet));
                    binding.ans1.setChecked(false);
                    binding.ans2.setChecked(true);
                    binding.ans3.setChecked(false);
                    binding.ans4.setChecked(false);
                    onSelectOption(question);
                }
                break;
            case R.id.ans3:
                if (binding.ans3.isChecked()) {
                    try {
                        if(radioButton!=null && action){
                            answersheet.put(String.valueOf(question),radioButton.getTag().toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("json ", String.valueOf(answersheet));
                    binding.ans1.setChecked(false);
                    binding.ans2.setChecked(false);
                    binding.ans3.setChecked(true);
                    binding.ans4.setChecked(false);
                    onSelectOption(question);
                }
                break;
            case R.id.ans4:
                if (binding.ans4.isChecked()) {
                    try {
                        if(radioButton!=null && action){
                            answersheet.put(String.valueOf(question),radioButton.getTag().toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("json ", String.valueOf(answersheet));
                    binding.ans1.setChecked(false);
                    binding.ans2.setChecked(false);
                    binding.ans3.setChecked(false);
                    binding.ans4.setChecked(true);
                    onSelectOption(question);
                }
                break;
        }
    }

    private void clearResponse(){
        binding.ans1.setChecked(false);
        binding.ans2.setChecked(false);
        binding.ans3.setChecked(false);
        binding.ans4.setChecked(false);
        onClearOption(question);
    }


    @Override
    public void onitemClick(int position, MaterialCardView cardView) {
//        cardView.setCardBackgroundColor(Color.GREEN);
        this.question=position;
        action=false;
        clearResponse();
        if(question==questionLists.size()-1){
            binding.next.setEnabled(false);binding.prev.setEnabled(true);binding.submit.setVisibility(View.VISIBLE);
        } else if(question == 0){
            binding.prev.setEnabled(false);binding.next.setEnabled(true);binding.submit.setVisibility(View.GONE);
        }else {
                binding.prev.setEnabled(true); binding.next.setEnabled(true);Loading();binding.submit.setVisibility(View.GONE);
            }
        binding.question.setText("Q"+question+"  " +questionLists.get(question).getQues());
        binding.t1.setText(questionLists.get(question).getOption1()); binding.ans1.setTag(questionLists.get(question).getOption1());
        binding.t2.setText(questionLists.get(question).getOption2()); binding.ans2.setTag(questionLists.get(question).getOption2());
        binding.t3.setText(questionLists.get(question).getOption3()); binding.ans3.setTag(questionLists.get(question).getOption3());
        binding.t4.setText(questionLists.get(question).getOption4()); binding.ans4.setTag(questionLists.get(question).getOption4());
        if(!searchJson(String.valueOf(question)).isEmpty()){
            RadioButton button = binding.newoption.findViewWithTag(searchJson(String.valueOf(question)));
            if(button!=null){button.setChecked(true);}
        }else
        {
            binding.newoption.clearCheck();
        }
        action=true;
    }

    private void onSelectOption(int position){
       QuestionAdapter.Viewholder  viewholder= (QuestionAdapter.Viewholder) binding.recycle.findViewHolderForAdapterPosition(position);
       MaterialCardView cardView = viewholder.itemView.findViewById(R.id.mainview);
        cardView.setCardBackgroundColor(Color.GREEN);

    }

    private void onClearOption(int position){
        QuestionAdapter.Viewholder  viewholder= (QuestionAdapter.Viewholder) binding.recycle.findViewHolderForAdapterPosition(position);
        MaterialCardView cardView = viewholder.itemView.findViewById(R.id.mainview);
        cardView.setCardBackgroundColor(Color.GRAY);

    }
}