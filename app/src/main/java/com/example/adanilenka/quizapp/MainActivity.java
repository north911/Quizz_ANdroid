package com.example.adanilenka.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String INCORRECT = "INCORRECT";
    public static final String CORRECT = "CORRECT";
    List<Question> questionList;
    int score = 0;
    int quid = 0;
    Question currentQuestion;

    TextView txtQuestion;
    RadioButton rda, rdb, rdc;
    Button butNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DbHelper dbHelper = new DbHelper(this);
        questionList = dbHelper.getAllQuestions();
        Collections.shuffle(questionList);
        currentQuestion = questionList.get(quid);

        txtQuestion = (TextView) findViewById(R.id.question);
        rda = (RadioButton) findViewById(R.id.radio0);
        rdb = (RadioButton) findViewById(R.id.radio1);
        rdc = (RadioButton) findViewById(R.id.radio2);
        butNext = (Button) findViewById(R.id.button1);
        setQuestionView();
    }

    private void setQuestionView() {
        txtQuestion.setText(currentQuestion.getQuestion());
        rda.setText(currentQuestion.getOptA());
        rdb.setText(currentQuestion.getOptB());
        rdc.setText(currentQuestion.getOptC());
        quid++;
    }

    public void btClick(View view) {
        RadioGroup grp = (RadioGroup) findViewById(R.id.radioGroup1);
        RadioButton answer = (RadioButton) findViewById(grp.getCheckedRadioButtonId());
//        setCorrectColour(grp);

        handleAnswer(answer);
        postProcessAnswer();
    }

    private void setCorrectColour(RadioGroup grp) {
        for (int i = 0; i < grp.getChildCount(); i++) {
            if (((RadioButton) findViewById(grp.getCheckedRadioButtonId())).getText().equals(currentQuestion.getAnswer())){
                RadioButton correctAnswer = (RadioButton) findViewById(grp.getChildAt(i).getId());
                correctAnswer.setTextColor(Color.GREEN);
            }
        }
    }

    private void postProcessAnswer() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (quid < 5) {
                    currentQuestion = questionList.get(quid);
                    setQuestionView();
                    TextView resultTextView = (TextView) findViewById(R.id.correct);
                    Objects.requireNonNull(resultTextView).setText("");
                } else {
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("score", score);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }

    private void handleAnswer(RadioButton answer) {
        if (currentQuestion.getAnswer().equals(answer.getText())) {
            score++;
            TextView resultTextView = (TextView) findViewById(R.id.correct);
            Objects.requireNonNull(resultTextView).setText(CORRECT);
            resultTextView.setTextColor(Color.GREEN);
            Log.d("Score", "Your score: " + score);
        } else {
            TextView resultTextView = (TextView) findViewById(R.id.correct);
            Objects.requireNonNull(resultTextView).setText(INCORRECT + "\nCorrect answer is " + currentQuestion.getAnswer());
            resultTextView.setTextColor(Color.RED);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
