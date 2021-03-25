package com.example.bioticclasses.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.example.bioticclasses.Adapter.QuestionAdapter;
import com.example.bioticclasses.List.QuestionList;
import com.example.bioticclasses.R;
import com.example.bioticclasses.databinding.TakeTestLayoutBinding;
import com.example.bioticclasses.global.GlobalList;
import com.example.bioticclasses.modal.mainList.Question;
import com.example.bioticclasses.modal.mainList.Result;
import com.example.bioticclasses.viewModel.MainActivityViewModel;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TakeTestActivity extends AppCompatActivity implements QuestionAdapter.ChangeQuestion, View.OnClickListener {
    TakeTestLayoutBinding binding;
    List<QuestionList> questionLists = new ArrayList<>();
    int question = 0;
    JSONObject answersheet;
    Boolean action = true;
    Boolean Test = false;
    Boolean Warning = false;
    CountDownTimer countDownTimer;
    String msg;
    MainActivityViewModel mainActivityViewModel;
    List<Question> list;
    int SubPos, TestPos;
    private static final String TAG = "TakeTestActivity";
    com.example.bioticclasses.modal.mainList.Test timecheck;
    AlertDialog alertDialog;
    Calendar todayDate = Calendar.getInstance();
    Calendar nextDate = Calendar.getInstance();
    long todatTimeMili, NextTimeMili;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    QuestionAdapter.ChangeQuestion changeQuestion;
    private static long START_TIME_IN_MILLIS = 600000;
    AlertDialog alertDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = TakeTestLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        answersheet = new JSONObject();

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        SubPos = Integer.parseInt(getIntent().getStringExtra("subPosition"));
        TestPos = Integer.parseInt(getIntent().getStringExtra("testPos"));

        Result result = ((GlobalList) getApplicationContext()).result;
        list = result.getData().get(SubPos).getTests().get(TestPos).getQuestions();
        timecheck = result.getData().get(SubPos).getTests().get(TestPos);

        binding.mainview.Ans1Layout.setOnClickListener(this);
        binding.mainview.Ans2Layout.setOnClickListener(this);
        binding.mainview.Ans3Layout.setOnClickListener(this);
        binding.mainview.Ans4Layout.setOnClickListener(this);

        setActivityData();
        ActivityAction();
        StartTest();


    }

    private void setActivityData() {

        for (int i = 1; i <= list.size(); i++) {
            questionLists.add(new QuestionList("your question no is  " + i, "patato" + i, "tomato" + i, "onion" + i, "chilli" + i));
        }
        binding.recycle.setAdapter(new QuestionAdapter(this, this, list));

        if (list.get(question).getType().trim().toUpperCase().equals("TEXT")) {
            binding.mainview.question.setText(list.get(question).getQuestion());
            int snoCount = question + 1;
            binding.mainview.ques.setText("Q." + snoCount);
            binding.mainview.t1.setText(list.get(question).getOp1());
//                binding.mainview.ans1.setTag(questionLists.get(question).getOption1());
            binding.mainview.ans1.setTag("op1");
            binding.mainview.t2.setText(list.get(question).getOp2());
//                binding.mainview.ans2.setTag(questionLists.get(question).getOption2());
            binding.mainview.ans2.setTag("op2");
            binding.mainview.t3.setText(list.get(question).getOp3());
//                binding.mainview.ans3.setTag(questionLists.get(question).getOption3());
            binding.mainview.ans3.setTag("op3");
            binding.mainview.t4.setText(list.get(question).getOp4());
//                binding.mainview.ans4.setTag(questionLists.get(question).getOption4());
            binding.mainview.ans4.setTag("op4");
            binding.total.setText(String.valueOf(questionLists.size()));
        }


 /*       mainActivityViewModel.getMainList().observe(this, data -> {


//            list = data.get(SubPos).getTests().get(TestPos).getQuestions();
//            timecheck = data.get(SubPos).getTests().get(TestPos);

//            for (int i = 1; i <= list.size(); i++) {
//                questionLists.add(new QuestionList("your question no is  " + i, "patato" + i, "tomato" + i, "onion" + i, "chilli" + i));
//            }
//            binding.recycle.setAdapter(new QuestionAdapter(this, this, list));
//
//            if (list.get(question).getType().trim().toUpperCase().equals("TEXT")) {
//                binding.mainview.question.setText(list.get(question).getQuestion());
//                int snoCount = question + 1;
//                binding.mainview.ques.setText("Q." + snoCount);
//                binding.mainview.t1.setText(list.get(question).getOp1());
////                binding.mainview.ans1.setTag(questionLists.get(question).getOption1());
//                binding.mainview.ans1.setTag("op1");
//                binding.mainview.t2.setText(list.get(question).getOp2());
////                binding.mainview.ans2.setTag(questionLists.get(question).getOption2());
//                binding.mainview.ans2.setTag("op2");
//                binding.mainview.t3.setText(list.get(question).getOp3());
////                binding.mainview.ans3.setTag(questionLists.get(question).getOption3());
//                binding.mainview.ans3.setTag("op3");
//                binding.mainview.t4.setText(list.get(question).getOp4());
////                binding.mainview.ans4.setTag(questionLists.get(question).getOption4());
//                binding.mainview.ans4.setTag("op4");
//                binding.total.setText(String.valueOf(questionLists.size()));
//            }
        });

*/
    }

    private void SetDrawerLayout() {

    }


    private void openDrawer() {

        if (binding.drawer.isDrawerVisible(GravityCompat.END)) {
            binding.drawer.closeDrawer(GravityCompat.END);
        } else binding.drawer.openDrawer(GravityCompat.END);
    }


    private void ActivityAction() {
        binding.mainview.next.setOnClickListener(v -> {
            question++;
            Next();
        });
        binding.mainview.prev.setOnClickListener(v -> {
            question--;
            Prev();
        });
        binding.mainview.clear.setOnClickListener(v -> {
            clearResponse();
            deletejson(String.valueOf(question));
        });
        binding.mainview.submit.setOnClickListener(v -> {
            Submit(binding.getRoot());
        });
        binding.mainview.sidebarMenu.setOnClickListener(v -> {
            openDrawer();
        });
        binding.finalsubmit.setOnClickListener(v -> {

            if (countDownTimer != null) countDownTimer.cancel();
            Test = false;
            startActivity(new Intent(this, ScoreActivity.class).putExtra("pos", String.valueOf(SubPos)).putExtra("answersheet", answersheet.toString()).putExtra("TestPos", String.valueOf(TestPos)));
            finish();
        });
    }


    private String modifyDateLayout(String inputDate) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(inputDate);
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }

    public String getTimeStamp(long timeinMillies) {
        String date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // modify format
        date = formatter.format(new Date(timeinMillies));
        System.out.println("Today is " + date);

        return date;
    }


    private void timer() {
        if (timecheck.getTimeLimit().trim().equals("NO"))
            binding.mainview.time.setText("Unlimited");
        else {

            String Date = timecheck.getDuration();
            String[] SpliteTime = Date.split(":");
            Log.e(TAG, "timer: " + getTimeStamp(todayDate.getTimeInMillis()));
            todatTimeMili = todayDate.getTimeInMillis();
            nextDate = todayDate;
            nextDate.add(Calendar.MINUTE, Integer.parseInt(Date));
            Log.e(TAG, "timer: " + getTimeStamp(nextDate.getTimeInMillis()));
            NextTimeMili = nextDate.getTimeInMillis();
            START_TIME_IN_MILLIS = NextTimeMili - todatTimeMili;
            Log.e(TAG, "timer: mili sec" + mTimeLeftInMillis);

            countDownTimer = new CountDownTimer(7200000, 1000) {

                public void onTick(long millisUntilFinished) {
                    mTimeLeftInMillis = millisUntilFinished;
                    updateCountDownText();
//                    binding.mainview.time.setText("00:" + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    TimeUp(binding.getRoot());
                }

            }.start();
        }
    }

    private void updateCountDownText() {
        int minutes = (int) ((mTimeLeftInMillis / (1000 * 60)) % 60);
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        int hours = (int) ((mTimeLeftInMillis / (1000 * 60 * 60)) % 24);

//        Log.e(TAG, "updateCountDownText: " + hours );
//        Log.e(TAG, "updateCountDownText: " + minutes );
//        Log.e(TAG, "updateCountDownText: " + seconds );

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        binding.mainview.time.setText(timeLeftFormatted);
    }

    private void Next() {

        action = false;
        clearResponse();

        if (question == list.size() - 1) {
            binding.mainview.next.setEnabled(false);
            binding.mainview.submit.setVisibility(View.VISIBLE);
            binding.mainview.next.setVisibility(View.GONE);
        } else {
            binding.mainview.prev.setEnabled(true);
            Loading();
            binding.mainview.submit.setVisibility(View.GONE);

        }
        binding.mainview.question.setText(list.get(question).getQuestion());
        int snoCount = question + 1;
        binding.mainview.ques.setText("Q." + snoCount);
        binding.mainview.t1.setText(list.get(question).getOp1());
//        binding.mainview.ans1.setTag(questionLists.get(question).getOption1());
        binding.mainview.ans1.setTag("op1");
        binding.mainview.t2.setText(list.get(question).getOp2());
//        binding.mainview.ans2.setTag(questionLists.get(question).getOption2());
        binding.mainview.ans2.setTag("op2");
        binding.mainview.t3.setText(list.get(question).getOp3());
//        binding.mainview.ans3.setTag(questionLists.get(question).getOption3());
        binding.mainview.ans3.setTag("op3");
        binding.mainview.t4.setText(list.get(question).getOp4());
//        binding.mainview.ans4.setTag(questionLists.get(question).getOption4());
        binding.mainview.ans4.setTag("op4");
        if (!searchJson(String.valueOf(question)).isEmpty()) {
            RadioButton button = binding.mainview.newoption.findViewWithTag(searchJson(String.valueOf(question)));
            if (button != null) {
                button.setChecked(true);
            }
        } else {
            binding.mainview.newoption.clearCheck();
        }


        action = true;
    }

    private void Prev() {

        Log.e(TAG, "Prev: " + question);

        action = false;
        clearResponse();
        if (question == 0) {
            binding.mainview.prev.setEnabled(false);
            binding.mainview.submit.setVisibility(View.GONE);
        } else {
            binding.mainview.next.setEnabled(true);
            Loading();
            binding.mainview.submit.setVisibility(View.GONE);
            binding.mainview.next.setVisibility(View.VISIBLE);
        }
        binding.mainview.question.setText(list.get(question).getQuestion());
        int snoCount = question + 1;
        binding.mainview.ques.setText("Q." + snoCount);

        binding.mainview.t1.setText(list.get(question).getOp1());
//        binding.mainview.ans1.setTag(questionLists.get(question).getOption1());
        binding.mainview.ans1.setTag("op1");
        binding.mainview.t2.setText(list.get(question).getOp2());
//        binding.mainview.ans2.setTag(questionLists.get(question).getOption2());
        binding.mainview.ans2.setTag("op2");
        binding.mainview.t3.setText(list.get(question).getOp3());
//        binding.mainview.ans3.setTag(questionLists.get(question).getOption3());
        binding.mainview.ans3.setTag("op3");
        binding.mainview.t4.setText(list.get(question).getOp4());
//        binding.mainview.ans4.setTag(questionLists.get(question).getOption4());
        binding.mainview.ans4.setTag("op4");
        if (!searchJson(String.valueOf(question)).isEmpty()) {
            String s = searchJson(String.valueOf(question));
            RadioButton button = binding.mainview.newoption.findViewWithTag(s);
            if (button != null) {
                button.setChecked(true);
            }
        } else {
            binding.mainview.newoption.clearCheck();
        }
        action = true;
    }

    private void Loading() {
        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
//                binding.mainview.setAlpha((float) 0.5);
//                binding.progress.setVisibility(View.VISIBLE);
            }
            public void onFinish() {
//                binding.progress.setVisibility(View.GONE);
//                binding.mainview.setAlpha(1);
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

    public void TimeUp(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Time Up Please Submit");
        alertDialogBuilder.setPositiveButton("Submit",
                (arg0, arg1) -> {
                    startActivity(new Intent(this, ScoreActivity.class).putExtra("pos", String.valueOf(SubPos)).putExtra("answersheet", answersheet.toString()).putExtra("TestPos", String.valueOf(TestPos)));
                    finish();
                });
        alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public void Submit(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to submit.");
        alertDialogBuilder.setPositiveButton("Submit",
                (arg0, arg1) -> {
                    if (countDownTimer != null) countDownTimer.cancel();
                    Test = false;
                    startActivity(new Intent(this, ScoreActivity.class).putExtra("pos", String.valueOf(SubPos)).putExtra("answersheet", answersheet.toString()).putExtra("TestPos", String.valueOf(TestPos)));
                });
        alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
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
                if (binding.mainview.ans1.isChecked()) {
                    try {
                        if(radioButton!=null && action){
                            answersheet.put(String.valueOf(question),radioButton.getTag().toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("json ", String.valueOf(answersheet));

                    binding.mainview.ans1.setChecked(true);
                    binding.mainview.ans2.setChecked(false);
                    binding.mainview.ans3.setChecked(false);
                    binding.mainview.ans4.setChecked(false);
                    onSelectOption(question);
                }
                break;
            case R.id.ans2:
                if (binding.mainview.ans2.isChecked()) {
                    try {
                        if(radioButton!=null && action){
                            answersheet.put(String.valueOf(question),radioButton.getTag().toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("json ", String.valueOf(answersheet));
                    binding.mainview.ans1.setChecked(false);
                    binding.mainview.ans2.setChecked(true);
                    binding.mainview.ans3.setChecked(false);
                    binding.mainview.ans4.setChecked(false);
                    onSelectOption(question);
                }
                break;
            case R.id.ans3:
                if (binding.mainview.ans3.isChecked()) {
                    try {
                        if(radioButton!=null && action){
                            answersheet.put(String.valueOf(question),radioButton.getTag().toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("json ", String.valueOf(answersheet));
                    binding.mainview.ans1.setChecked(false);
                    binding.mainview.ans2.setChecked(false);
                    binding.mainview.ans3.setChecked(true);
                    binding.mainview.ans4.setChecked(false);
                    onSelectOption(question);
                }
                break;
            case R.id.ans4:
                if (binding.mainview.ans4.isChecked()) {
                    try {
                        if(radioButton!=null && action){
                            answersheet.put(String.valueOf(question),radioButton.getTag().toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("json ", String.valueOf(answersheet));
                    binding.mainview.ans1.setChecked(false);
                    binding.mainview.ans2.setChecked(false);
                    binding.mainview.ans3.setChecked(false);
                    binding.mainview.ans4.setChecked(true);
                    onSelectOption(question);
                }
                break;

        }
    }

    private void clearResponse(){
        binding.mainview.ans1.setChecked(false);
        binding.mainview.ans2.setChecked(false);
        binding.mainview.ans3.setChecked(false);
        binding.mainview.ans4.setChecked(false);
        onClearOption(question);
    }


    @Override
    public void onitemClick(int position, MaterialCardView cardView) {
//        cardView.setCardBackgroundColor(Color.GREEN);
        this.question = position;
        action = false;
        clearResponse();
        if (question == questionLists.size() - 1) {
            binding.mainview.next.setEnabled(false);
            binding.mainview.prev.setEnabled(true);
            binding.mainview.submit.setVisibility(View.VISIBLE);
        } else if (question == 0) {
            binding.mainview.prev.setEnabled(false);
            binding.mainview.next.setEnabled(true);
            binding.mainview.submit.setVisibility(View.GONE);
        } else {
            binding.mainview.prev.setEnabled(true);
            binding.mainview.next.setEnabled(true);
            Loading();
            binding.mainview.submit.setVisibility(View.GONE);
        }
        binding.mainview.question.setText(questionLists.get(question).getQues());
        int snoCount = question + 1;
        binding.mainview.ques.setText("Q." + snoCount);
        binding.mainview.t1.setText(questionLists.get(question).getOption1());
//        binding.mainview.ans1.setTag(questionLists.get(question).getOption1());
        binding.mainview.ans1.setTag("op1");
        binding.mainview.t2.setText(questionLists.get(question).getOption2());
//        binding.mainview.ans2.setTag(questionLists.get(question).getOption2());
        binding.mainview.ans2.setTag("op2");
        binding.mainview.t3.setText(questionLists.get(question).getOption3());
//        binding.mainview.ans3.setTag(questionLists.get(question).getOption3());
        binding.mainview.ans3.setTag("op3");
        binding.mainview.t4.setText(questionLists.get(question).getOption4());
//        binding.mainview.ans4.setTag(questionLists.get(question).getOption4());
        binding.mainview.ans4.setTag("op4");
        if (!searchJson(String.valueOf(question)).isEmpty()) {
            RadioButton button = binding.mainview.newoption.findViewWithTag(searchJson(String.valueOf(question)));
            if (button != null) {
                button.setChecked(true);
            }
        } else {
            binding.mainview.newoption.clearCheck();
        }
        action = true;
        binding.drawer.closeDrawer(GravityCompat.END);

    }

    private void onSelectOption(int position) {
        QuestionAdapter.Viewholder viewholder = (QuestionAdapter.Viewholder) binding.recycle.findViewHolderForAdapterPosition(position);
        MaterialCardView cardView = viewholder.itemView.findViewById(R.id.mainview);
        cardView.setCardBackgroundColor(getResources().getColor(R.color.dark_green));

    }

    private void onClearOption(int position) {
//        QuestionAdapter.Viewholder  viewholder= (QuestionAdapter.Viewholder) binding.recycle.findViewHolderForAdapterPosition(position);
//        MaterialCardView cardView = viewholder.itemView.findViewById(R.id.mainview);
//        cardView.setCardBackgroundColor(getResources().getColor(R.color.gray));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.Ans1Layout:
                if (binding.mainview.ans1.isChecked()) {
                    try {
                        if(binding.mainview.ans1 !=null && action){
                            answersheet.put(String.valueOf(question),binding.mainview.ans1.getTag().toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("json ", String.valueOf(answersheet));

                    binding.mainview.ans1.setChecked(true);
                    binding.mainview.ans2.setChecked(false);
                    binding.mainview.ans3.setChecked(false);
                    binding.mainview.ans4.setChecked(false);
                    onSelectOption(question);
                }
                break;
            case R.id.Ans2Layout:
                if (binding.mainview.ans2.isChecked()) {
                    try {
                        if(binding.mainview.ans2!=null && action){
                            answersheet.put(String.valueOf(question),binding.mainview.ans2.getTag().toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("json ", String.valueOf(answersheet));
                    binding.mainview.ans1.setChecked(false);
                    binding.mainview.ans2.setChecked(true);
                    binding.mainview.ans3.setChecked(false);
                    binding.mainview.ans4.setChecked(false);
                    onSelectOption(question);
                }
                break;
            case R.id.Ans3Layout:
                if (binding.mainview.ans3.isChecked()) {
                    try {
                        if(binding.mainview.ans3!=null && action){
                            answersheet.put(String.valueOf(question),binding.mainview.ans3.getTag().toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("json ", String.valueOf(answersheet));
                    binding.mainview.ans1.setChecked(false);
                    binding.mainview.ans2.setChecked(false);
                    binding.mainview.ans3.setChecked(true);
                    binding.mainview.ans4.setChecked(false);
                    onSelectOption(question);
                }
                break;
            case R.id.Ans4Layout:
                if (binding.mainview.ans4.isChecked()) {
                    try {
                        if(binding.mainview.ans4!=null && action){
                            answersheet.put(String.valueOf(question),binding.mainview.ans4.getTag().toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("json ", String.valueOf(answersheet));
                    binding.mainview.ans1.setChecked(false);
                    binding.mainview.ans2.setChecked(false);
                    binding.mainview.ans3.setChecked(false);
                    binding.mainview.ans4.setChecked(true);
                    onSelectOption(question);
                }
                break;
        }
    }
}