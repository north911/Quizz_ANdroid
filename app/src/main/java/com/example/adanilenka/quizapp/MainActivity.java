package com.example.adanilenka.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String INCORRECT = "INCORRECT";
    public static final String CORRECT = "CORRECT";
    private DatabaseReference myRef;
    FirebaseDatabase database;
    List<Question> questionList;
    int score = 0;
    int quid = 0;
    Question currentQuestion;

    TextView txtQuestion;
    RadioButton rda, rdb, rdc;
    Button butNext;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DbHelper(this);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        dbHelper.onCreate(db);

        txtQuestion = (TextView) findViewById(R.id.question);
        rda = (RadioButton) findViewById(R.id.radio0);
        rdb = (RadioButton) findViewById(R.id.radio1);
        rdc = (RadioButton) findViewById(R.id.radio2);
        butNext = (Button) findViewById(R.id.button1);

        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("players").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Long>> listPlayer = new GenericTypeIndicator<HashMap<String, Long>>() {
                };
                HashMap<String, Long> listOfPlayers = dataSnapshot.getValue(listPlayer);
                dbHelper.UpdatePlayers(dbHelper.getWritableDatabase(), listOfPlayers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        myRef.child("questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Question>> listOption = new GenericTypeIndicator<HashMap<String, Question>>() {
                };
                HashMap<String, Question> questionMap = dataSnapshot.getValue(listOption);
//                myRef = FirebaseDatabase.getInstance().getReference("questions");
                dbHelper.UpdateQuestions(dbHelper.getWritableDatabase(), questionMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        myRef.child("answers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Answer>> listQuestion = new GenericTypeIndicator<List<Answer>>() {
                };
                List<Answer> answerHashMap = dataSnapshot.getValue(listQuestion);
                dbHelper.UpdateAnswers(dbHelper.getWritableDatabase(), answerHashMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        questionList = dbHelper.getAllQuestions();
        Collections.shuffle(questionList);
        currentQuestion = questionList.get(quid);
        setQuestionView();
    }

    private void setQuestionView() {
        txtQuestion.setText(currentQuestion.getQuestion());
        rda.setText(dbHelper.getAnswersByIdQuestion(currentQuestion.getId()).get(0).getAnswer());
        rdb.setText(dbHelper.getAnswersByIdQuestion(currentQuestion.getId()).get(1).getAnswer());
        rdc.setText(dbHelper.getAnswersByIdQuestion(currentQuestion.getId()).get(2).getAnswer());
        quid++;
    }

    public void btClick(View view) {
        RadioGroup grp = (RadioGroup) findViewById(R.id.radioGroup1);
        RadioButton answer = (RadioButton) findViewById(grp.getCheckedRadioButtonId());
//        setCorrectColour(grp);

        handleAnswer(answer);
        postProcessAnswer();
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
        return super.onOptionsItemSelected(item);
    }
}
