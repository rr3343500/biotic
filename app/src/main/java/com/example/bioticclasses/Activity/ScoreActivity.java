package com.example.bioticclasses.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.ActivityScoreBinding;
import com.example.bioticclasses.global.GlobalList;
import com.example.bioticclasses.modal.mainList.Question;
import com.example.bioticclasses.modal.mainList.Result;
import com.example.bioticclasses.modal.mainList.Test;
import com.example.bioticclasses.modal.test_submit_data.TestSubmitData;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Score Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        result = ((GlobalList) getApplicationContext()).result;


        textView = (TextView) findViewById(R.id.textView);
        final PieView pieView = (PieView) findViewById(R.id.pie_view);
        pos = Integer.parseInt(getIntent().getStringExtra("pos"));
        TestPos = Integer.parseInt(getIntent().getStringExtra("TestPos"));
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        biotechInterface = ApiClient.getClient().create(BiotechInterface.class);
        sessionManage = new SessionManage(this);

        try {
            answersheet = new JSONObject(getIntent().getStringExtra("answersheet"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        calculateTest();
        set(pieView);

    }


    private void calculateTest() {


        Iterator iterator = answersheet.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            try {
                Question Copt = result.getData().get(pos).getTests().get(TestPos).getQuestions().get(Integer.parseInt(key));
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
        AnswerSheet(result.getData().get(pos).getTests().get(TestPos).getQuestions().size(), result.getData().get(pos).getTests().get(TestPos).getCoorectMarks(), result.getData().get(pos).getTests().get(TestPos).getWrongMarks(), result.getData().get(pos).getTests());



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

    private void AnswerSheet(int totalQuestion, int coorect_marks_1_question, int wrong_marks_1_question, List<Test> test) {
//        Log.e(TAG, "calculateTest: correctAnswer" + correctAnswer);
//        Log.e(TAG, "calculateTest: wrongAnser" + wrongAnser);
//
//        Log.e(TAG, "calculateTest: totalQuestion" + totalQuestion);
//        Log.e(TAG, "calculateTest: coorect_marks_1_question" + coorect_marks_1_question);
//        Log.e(TAG, "calculateTest: wrong_marks_1_question" + wrong_marks_1_question);
        List<Question> questionList = result.getData().get(pos).getTests().get(TestPos).getQuestions();
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


        binding.attemQuestion.setText(String.valueOf(correctAnswer + wrongAnser));
        binding.NoQuestion.setText(String.valueOf(totalQuestion));
        binding.wrongAnswer.setText(String.valueOf(wrongAnser));
        binding.correctAnswer.setText(String.valueOf(correctAnswer));
        binding.totalMarks.setText(String.valueOf((correctAnswer * coorect_marks_1_question) - (wrongAnser * wrong_marks_1_question)));


        int totalMarks = totalQuestion * coorect_marks_1_question;
        int leaveQue = totalQuestion - (correctAnswer + wrongAnser);


        passjsonObject.addProperty("test_name", test.get(TestPos).getHeading());
        passjsonObject.addProperty("user_id", sessionManage.getUserDetails().get("userid"));
        passjsonObject.addProperty("test_id", test.get(TestPos).getId());
        passjsonObject.addProperty("total_ques", totalQuestion);
        passjsonObject.addProperty("total_marks", String.valueOf(totalMarks));
        passjsonObject.addProperty("marks_obtain", String.valueOf((correctAnswer * coorect_marks_1_question) - (wrongAnser * wrong_marks_1_question)));
        passjsonObject.addProperty("correct_ques", correctAnswer);
        passjsonObject.addProperty("wrong_ques", wrongAnser);
        passjsonObject.addProperty("leave_ques", String.valueOf(leaveQue));
        passjsonObject.add("response", jsonElements);

        Log.e(TAG, "AnswerSheet: " + passjsonObject.toString());
        TestSubmit();
    }

    private void TestSubmit() {
        biotechInterface.testSubmit(passjsonObject).enqueue(new Callback<TestSubmitData>() {
            @Override
            public void onResponse(Call<TestSubmitData> call, Response<TestSubmitData> response) {
                Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    if (!response.body().getResult().getError() && response.body().getResult().getErrorCode() == 200) {

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

    private void randomSet(PieView pieView) {
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
        ArrayList<Integer> intList = new ArrayList<Integer>();
        int totalNum = (int) (5 * Math.random()) + 5;

        int totalInt = 0;
        for (int i = 0; i < totalNum; i++) {
            int ranInt = (int) (Math.random() * 10) + 1;
            intList.add(ranInt);
            totalInt += ranInt;
        }
        for (int i = 0; i < totalNum; i++) {
            pieHelperArrayList.add(new PieHelper(100f * intList.get(i) / totalInt));
        }

        pieView.selectedPie(PieView.NO_SELECTED_INDEX);
        pieView.showPercentLabel(true);
        pieView.setDate(pieHelperArrayList);
    }

    private void set(PieView pieView) {
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<>();
        pieHelperArrayList.add(new PieHelper(20, Color.BLACK));
        pieHelperArrayList.add(new PieHelper(50));
        pieHelperArrayList.add(new PieHelper(30));
//        pieHelperArrayList.add(new PieHelper(12));
//        pieHelperArrayList.add(new PieHelper(32));

        pieView.setDate(pieHelperArrayList);
        pieView.setOnPieClickListener(index -> {
            if (index != PieView.NO_SELECTED_INDEX) {
                textView.setText(index + " selected");
            } else {
                textView.setText("No selected pie");
            }
        });
        pieView.selectedPie(2);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ScoreActivity.this, TestListActivity.class).putExtra("pos", String.valueOf(pos)));
        finish();
    }
}