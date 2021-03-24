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
import com.example.bioticclasses.databinding.ActivityScoreBinding;
import com.example.bioticclasses.modal.mainList.Question;
import com.example.bioticclasses.viewModel.MainActivityViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import im.dacer.androidcharts.PieHelper;
import im.dacer.androidcharts.PieView;

public class ScoreActivity extends AppCompatActivity {

    private TextView textView;
    int pos, TestPos;
    JSONObject answersheet;
    private static final String TAG = "ScoreActivity";
    int correctAnswer = 0, wrongAnser = 0;
    MainActivityViewModel mainActivityViewModel;
    ActivityScoreBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Score Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = (TextView) findViewById(R.id.textView);
        final PieView pieView = (PieView) findViewById(R.id.pie_view);
        pos = Integer.parseInt(getIntent().getStringExtra("pos"));
        TestPos = Integer.parseInt(getIntent().getStringExtra("TestPos"));
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        try {
            answersheet = new JSONObject(getIntent().getStringExtra("answersheet"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        calculateTest();
        set(pieView);

    }


    private void calculateTest() {

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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            AnswerSheet(data.get(pos).getTests().get(TestPos).getQuestions().size(), data.get(pos).getTests().get(TestPos).getCoorectMarks(), data.get(pos).getTests().get(TestPos).getWrongMarks());
        });
    }

    private void AnswerSheet(int totalQuestion, int coorect_marks_1_question, int wrong_marks_1_question) {
        Log.e(TAG, "calculateTest: correctAnswer" + correctAnswer);
        Log.e(TAG, "calculateTest: wrongAnser" + wrongAnser);

        Log.e(TAG, "calculateTest: totalQuestion" + totalQuestion);
        Log.e(TAG, "calculateTest: coorect_marks_1_question" + coorect_marks_1_question);
        Log.e(TAG, "calculateTest: wrong_marks_1_question" + wrong_marks_1_question);

        binding.attemQuestion.setText(String.valueOf(correctAnswer + wrongAnser));
        binding.NoQuestion.setText(String.valueOf(totalQuestion));
        binding.wrongAnswer.setText(String.valueOf(wrongAnser));
        binding.correctAnswer.setText(String.valueOf(correctAnswer));
        binding.totalMarks.setText(String.valueOf((correctAnswer * coorect_marks_1_question) - (wrongAnser * wrong_marks_1_question)));
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