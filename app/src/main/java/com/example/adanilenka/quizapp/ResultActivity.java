package com.example.adanilenka.quizapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    TextView scoreTxtView, scoreText, resultTextView, resultList;
    RatingBar ratingBar1, ratingBar2;
    ImageView img;
    Button tryButton, resultButton;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar1);
        scoreTxtView = (TextView) findViewById(R.id.score);
        scoreText = (TextView) findViewById(R.id.scoreText);
        resultTextView = (TextView) findViewById(R.id.RresultTextView);
        resultList = (TextView) findViewById(R.id.resultList);
        ratingBar1 = (RatingBar) findViewById(R.id.ratingBar1);
        ratingBar2 = (RatingBar) findViewById(R.id.ratingBar2);
        img = (ImageView) findViewById(R.id.img1);
        tryButton = (Button) findViewById(R.id.buttonTry);
        resultButton = (Button) findViewById(R.id.buttonResult);

        b = getIntent().getExtras();
        int score = b.getInt("score");
        ratingBar.setRating(score);
        scoreTxtView.setText(String.valueOf(score));
        int numbQuest = b.getInt("numbQuest");
        if (numbQuest <= 5) {
            ratingBar1.setNumStars(numbQuest);
            ratingBar1.setRating(score);
        } else {
            //ratingBar1.setNumStars(numbQuest);
            ratingBar1.setRating(score);
            ratingBar2.setVisibility(View.VISIBLE);
            //ratingBar2.setNumStars(5);
            ratingBar2.setRating(score - 5);
        }
        scoreTxtView.setText(String.valueOf(score));

        if (numbQuest == 5) {

            if (score == 0) {
                img.setImageResource(R.drawable.score_0);
            } else if (score == 1) {
                img.setImageResource(R.drawable.score_1);
            } else if (score == 2) {
                img.setImageResource(R.drawable.score_2);
            } else if (score == 3) {
                img.setImageResource(R.drawable.score_3);
            } else if (score == 4) {
                img.setImageResource(R.drawable.score_4);
            } else if (score == 5) {
                img.setImageResource(R.drawable.score_5);
            }
        } else {
            if (score == 0 || score == 1) {
                img.setImageResource(R.drawable.score_0);
            } else if (score == 2 || score == 3) {
                img.setImageResource(R.drawable.score_1);
            } else if (score == 4 || score == 5) {
                img.setImageResource(R.drawable.score_2);
            } else if (score == 6 || score == 7) {
                img.setImageResource(R.drawable.score_3);
            } else if (score == 8 || score == 9) {
                img.setImageResource(R.drawable.score_4);
            } else if (score == 10) {
                img.setImageResource(R.drawable.score_5);
            }
        }
    }

    public void onClickTryAgain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickResult(View view) {
        scoreTxtView.setVisibility(View.INVISIBLE);
        scoreText.setVisibility(View.INVISIBLE);
        ratingBar1.setVisibility(View.INVISIBLE);
        ratingBar2.setVisibility(View.INVISIBLE);
        img.setVisibility(View.INVISIBLE);
        resultTextView.setVisibility(View.VISIBLE);
        resultButton.setVisibility(View.INVISIBLE);
        resultList.setVisibility(View.VISIBLE);
        DbHelper dbHelper = new DbHelper(this);
        List<Player> players = dbHelper.getAllPlayers();

        StringBuilder resultsString = new StringBuilder();

        for (Player player : players) {
            resultsString.append(player.getName()).append(": ").append(player.getScore()).append("\n");
        }
        resultList.setText(resultsString);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void onClickSubmit(View view){
        Objects.requireNonNull(findViewById(R.id.fr_result)).setVisibility(View.VISIBLE);
        Objects.requireNonNull(findViewById(R.id.fr_allResult)).setVisibility(View.VISIBLE);
        Objects.requireNonNull(findViewById(R.id.buttonTry)).setVisibility(View.VISIBLE);
        Objects.requireNonNull(findViewById(R.id.buttonSubmit)).setVisibility(View.INVISIBLE);
        Objects.requireNonNull(findViewById(R.id.fr_register)).setVisibility(View.INVISIBLE);
        EditText name = (EditText) findViewById(R.id.username);
        new DbHelper(this).addPlayerToDB(createPlayer(name.getText().toString(), b.getInt("score")));
        Objects.requireNonNull(findViewById(R.id.buttonResult)).setVisibility(View.VISIBLE);

    }

    public Player createPlayer(String name, int score){
        Player player = new Player();
        player.setName(name);
        player.setScore(score);
        return player;
    }
}
