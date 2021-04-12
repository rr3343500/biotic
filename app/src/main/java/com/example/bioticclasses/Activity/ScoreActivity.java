package com.example.bioticclasses.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.bioticclasses.Adapter.RankAdapter;
import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.ActivityScoreBinding;
import com.example.bioticclasses.global.GlobalList;
import com.example.bioticclasses.modal.testlist.Question;
import com.example.bioticclasses.modal.testlist.Result;
import com.example.bioticclasses.modal.testlist.Question;
import com.example.bioticclasses.modal.test_submit_data.TestSubmitData;
import com.example.bioticclasses.modal.testresult.TestResult;
import com.example.bioticclasses.piechart.Piechart;
import com.example.bioticclasses.piechart.PiechartHelper;
import com.example.bioticclasses.other.SessionManage;
import com.example.bioticclasses.viewModel.MainActivityViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import im.dacer.androidcharts.PieHelper;
import im.dacer.androidcharts.PieView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScoreActivity extends AppCompatActivity {

    private TextView textView;
    int pos, TestPos;
    JSONObject answersheet;
    private static final String TAG = "ScoreActivity";
    int correctAnswer = 0, wrongAnser = 0;
    MainActivityViewModel mainActivityViewModel;
    ActivityScoreBinding binding;
    JsonObject passjsonObject = new JsonObject();
    JsonArray jsonElements = new JsonArray();
    BiotechInterface biotechInterface;
    SessionManage sessionManage;
    Result result;
    Piechart pieView;
    double totaltime,timetaking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        result = ((GlobalList) getApplicationContext()).testlist;


        pos = Integer.parseInt(getIntent().getStringExtra("pos"));
        TestPos = Integer.parseInt(getIntent().getStringExtra("TestPos"));
//        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        biotechInterface = ApiClient.getClient().create(BiotechInterface.class);
        sessionManage = new SessionManage(this);

        try {
            answersheet = new JSONObject(getIntent().getStringExtra("answersheet"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        calculateTest();

//        Log.e("dfdsfs", getIntent().getStringExtra("totaltime"));
//        Log.e("dfdsfs", getIntent().getStringExtra("remainnig"));
//        totaltime = Double.parseDouble(getIntent().getStringExtra("totaltime"));
//        timetaking = Double.parseDouble(getIntent().getStringExtra("remainnig"));
//
//        binding.totaltime.setText(String.valueOf(totaltime/60000));
//        binding.takingtime.setText(String.valueOf(timetaking/60000));
//
//        double timeper =  (timetaking/totaltime)*100;
//
//        binding.timeprogress.setProgress(Float.parseFloat(String.valueOf(timeper)),true);

    }


    private void calculateTest() {


        Iterator iterator = answersheet.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            try {
                Question Copt = result.getData().get(TestPos).getQuestions().get(Integer.parseInt(key));
                if (answersheet.getString(key).trim().toUpperCase().equals(Copt.getCop().trim().toUpperCase())) {
                    correctAnswer++;
                } else {
                    wrongAnser++;
                }
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("question", Copt.getQuestion());
//                jsonObject.addProperty("op1", Copt.getOp1());
//                jsonObject.addProperty("op2", Copt.getOp2());
//                jsonObject.addProperty("op3", Copt.getOp3());
//                jsonObject.addProperty("op4", Copt.getOp4());
//                jsonObject.addProperty("cop", Copt.getCop());
//                jsonObject.addProperty("uop", answersheet.getString(key).trim());
//                jsonObject.addProperty("des", "");
//                jsonObject.addProperty("type", Copt.getType());
//                jsonElements.add(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        AnswerSheet(result.getData().get(TestPos).getQuestions().size(), result.getData().get(TestPos).getCoorectMarks(), result.getData().get(TestPos).getWrongMarks(), result.getData().get(pos).getQuestions());



/*
        mainActivityViewModel.getMainList().observe(this, data -> {

            Iterator iterator = answersheet.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                try {
                    Question Copt = data.get(pos).getTests().get(TestPos).getQuestions().get(Integer.parseInt(key));
                    Log.e(TAG, "calculateTest: " + answersheet.getString(key));
                    Log.e(TAG, "calculateTest: " + Copt.getCop());
                    if (answersheet.getString(key).trim().toUpperCase().equals(Copt.getCop().trim().toUpperCase())) {
                        correctAnswer++;
                    } else {
                        wrongAnser++;
                    }
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("question", Copt.getQuestion());
                    jsonObject.addProperty("op1", Copt.getOp1());
                    jsonObject.addProperty("op2", Copt.getOp2());
                    jsonObject.addProperty("op3", Copt.getOp3());
                    jsonObject.addProperty("op4", Copt.getOp4());
                    jsonObject.addProperty("cop", Copt.getCop());
                    jsonObject.addProperty("uop", answersheet.getString(key).trim());
                    jsonObject.addProperty("des", "");
                    jsonObject.addProperty("type", Copt.getType());
                    jsonElements.add(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            AnswerSheet(data.get(pos).getTests().get(TestPos).getQuestions().size(), data.get(pos).getTests().get(TestPos).getCoorectMarks(), data.get(pos).getTests().get(TestPos).getWrongMarks(), data.get(pos).getTests());
        });*/
    }

    private void AnswerSheet(int totalQuestion, int coorect_marks_1_question, int wrong_marks_1_question, List<Question> test) {
//        Log.e(TAG, "calculateTest: correctAnswer" + correctAnswer);
//        Log.e(TAG, "calculateTest: wrongAnser" + wrongAnser);
//
//        Log.e(TAG, "calculateTest: totalQuestion" + totalQuestion);
//        Log.e(TAG, "calculateTest: coorect_marks_1_question" + coorect_marks_1_question);
//        Log.e(TAG, "calculateTest: wrong_marks_1_question" + wrong_marks_1_question);
        List<Question> questionList = result.getData().get(TestPos).getQuestions();
        for (int i = 0; i < questionList.size(); i++) {
            Question Copt = questionList.get(i);
            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("question", questionList.get(i).getQuestion());
                jsonObject.addProperty("op1", Copt.getOp1());
                jsonObject.addProperty("op2", Copt.getOp2());
                jsonObject.addProperty("op3", Copt.getOp3());
                jsonObject.addProperty("op4", Copt.getOp4());
                jsonObject.addProperty("cop", Copt.getCop());
                jsonObject.addProperty("uop", answersheet.getString(String.valueOf(i)).trim());
                jsonObject.addProperty("des", "");
                jsonObject.addProperty("type", Copt.getType());
                jsonElements.add(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("question", questionList.get(i).getQuestion());
                jsonObject.addProperty("op1", Copt.getOp1());
                jsonObject.addProperty("op2", Copt.getOp2());
                jsonObject.addProperty("op3", Copt.getOp3());
                jsonObject.addProperty("op4", Copt.getOp4());
                jsonObject.addProperty("cop", Copt.getCop());
                jsonObject.addProperty("uop", "");
                jsonObject.addProperty("des", "");
                jsonObject.addProperty("type", Copt.getType());
                jsonElements.add(jsonObject);
            }

//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("question", Copt.getQuestion());
//            jsonObject.addProperty("op1", Copt.getOp1());
//            jsonObject.addProperty("op2", Copt.getOp2());
//            jsonObject.addProperty("op3", Copt.getOp3());
//            jsonObject.addProperty("op4", Copt.getOp4());
//            jsonObject.addProperty("cop", Copt.getCop());
//            jsonObject.addProperty("uop", answersheet.getString(key).trim());
//            jsonObject.addProperty("des", "");
//            jsonObject.addProperty("type", Copt.getType());
//            jsonElements.add(jsonObject);

        }

        double attemptques= correctAnswer + wrongAnser;
        double totalques= totalQuestion;

        double attper= (attemptques/totalques)*100;
        binding.attemptprogress.setProgress(Float.valueOf(String.valueOf(attper)),true);

        binding.attques.setText(String.valueOf(correctAnswer + wrongAnser));
        binding.totques.setText(String.valueOf(totalQuestion));
        binding.incorrect.setText(String.valueOf(wrongAnser)+" Incorrect");
        binding.correct.setText(String.valueOf(correctAnswer)+" Correct");
        double obtained= (correctAnswer * coorect_marks_1_question) - (wrongAnser * wrong_marks_1_question);
        binding.marksobtained.setText(String.valueOf(obtained));





        int totalMarks = totalQuestion * coorect_marks_1_question;
        int leaveQue = totalQuestion - (correctAnswer + wrongAnser);


        double obper= (obtained /totalMarks)*100;
        if(obper<0){
            binding.scoreprogress.setProgress(0,true);
        }else{
            binding.scoreprogress.setProgress(Float.valueOf(String.valueOf(obper)),true);
        }


        binding.totmark.setText(String.valueOf(totalMarks));

        binding.unanswered.setText(String.valueOf(leaveQue)+" UnAnswered");

        passjsonObject.addProperty("test_name",result.getData().get(pos).getHeading());
        passjsonObject.addProperty("user_id", sessionManage.getUserDetails().get("userid"));
        passjsonObject.addProperty("user_name", sessionManage.getUserDetails().get("Name"));
        passjsonObject.addProperty("test_id", result.getData().get(TestPos).getId());
        passjsonObject.addProperty("total_ques", totalQuestion);
        passjsonObject.addProperty("total_marks", String.valueOf(totalMarks));
        passjsonObject.addProperty("marks_obtain", String.valueOf((correctAnswer * coorect_marks_1_question) - (wrongAnser * wrong_marks_1_question)));
        passjsonObject.addProperty("correct_ques", correctAnswer);
        passjsonObject.addProperty("wrong_ques", wrongAnser);
        passjsonObject.addProperty("leave_ques", String.valueOf(leaveQue));
        passjsonObject.add("response", jsonElements);

        Log.e(TAG, "AnswerSheet: " + passjsonObject.toString());
        TestSubmit( result.getData().get(TestPos).getId());

        set(totalQuestion,correctAnswer,wrongAnser);
    }

    private void TestSubmit(String id) {
        biotechInterface.testSubmit(passjsonObject).enqueue(new Callback<TestSubmitData>() {
            @Override
            public void onResponse(Call<TestSubmitData> call, Response<TestSubmitData> response) {
                Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    if (!response.body().getResult().getError() && response.body().getResult().getErrorCode() == 200) {
                        SetRank(id);
                    }
                }
            }

            @Override
            public void onFailure(Call<TestSubmitData> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }



    private void set(double total1,int attempt, int notvisited ) {
         double unvisited=   total1-(correctAnswer+wrongAnser);

         double per1=   unvisited / total1;
         double perun=   per1 * 100;

         double percor=    (correctAnswer / total1) * 100;

         double perwro = (wrongAnser / total1) * 100;





//        ArrayList<PiechartHelper> pieHelperArrayList = new ArrayList<>();
//        pieHelperArrayList.add(new PiechartHelper((float) percor, "Attempt", Color.GREEN));
//        pieHelperArrayList.add(new PiechartHelper((float) perwro, "Wrong", Color.RED));
//        pieHelperArrayList.add(new PiechartHelper((float) perun, "Unvisited",Color.GRAY));
////        pieHelperArrayList.add(new PieHelper(12));
////        pieHelperArrayList.add(new PieHelper(32));
//        pieView.showPercentLabel(false);
//        pieView.setDate(pieHelperArrayList);
//        pieView.setOnPieClickListener(index -> {
//            if (index != PieView.NO_SELECTED_INDEX) {
//                textView.setText(index + " selected");
//            } else {
//                textView.setText("No selected pie");
//            }
//        });
//        pieView.selectedPie(2);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ScoreActivity.this, HomeActivity.class).putExtra("pos", String.valueOf(pos)).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }


    public void SetRank(String id){
       JsonObject jsonObject= new JsonObject();
       jsonObject.addProperty("test_id",id);
      biotechInterface.TEST_RESULT_CALL(jsonObject).enqueue(new Callback<TestResult>() {
          @Override
          public void onResponse(Call<TestResult> call, Response<TestResult> response) {
              if (response.isSuccessful()) {
                  if (!response.body().getResult().getError() && response.body().getResult().getErrorCode() == 200) {
                   binding.renkrecycle.setAdapter(new RankAdapter(ScoreActivity.this,response.body().getResult().getData()));
                   binding.rankview.setVisibility(View.VISIBLE);

                   double totalstu= response.body().getResult().getData().size();
                   double stupos =0;
                   for (int i=1; i<= response.body().getResult().getData().size();i++){
                       if(sessionManage.getUserDetails().get("userid").equals(response.body().getResult().getData().get(i).getUserId())){
                           stupos = i;
                           break;
                       }
                   }
                   double rankper =  (stupos/totalstu)*100;
                      binding.scoreprogress.setProgress(Float.valueOf(String.valueOf(rankper)),true);
                      binding.totalstu.setText(String.valueOf((int)totalstu));
                      binding.stupos.setText(String.valueOf((int)stupos));


                  }
              }
          }

          @Override
          public void onFailure(Call<TestResult> call, Throwable t) {

          }
      });
    }
}