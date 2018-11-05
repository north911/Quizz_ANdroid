package com.example.adanilenka.quizapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adanilenka on 10/22/2018.
 */
public class DbHelper extends SQLiteOpenHelper {

    //DB version, table and database name
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "quizdb";
    private static final String DB_TABLE = "quiztable";
    private static final String ANSWERS = "answers";
    private static final String PLAYER_NAMES = "player_names";


    //table column names
    private static final String KEY_ID = "id";
    private static final String KEY_QUES = "question";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_NAME = "name";
    private static final String KEY_SCORE = "score";


    private SQLiteDatabase dbase;
    private int rowCount = 0;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase = db;
   /*     String sqlQuery = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)",
                DB_TABLE, KEY_ID, KEY_QUES, KEY_ANSWER);
        String sqlQuery2 = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INT)",
                PLAYER_NAMES, KEY_ID, KEY_NAME, KEY_SCORE);
        String sqlQuery3 = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY, %s TEXT)",
                ANSWERS, KEY_ID, KEY_ANSWER);
        db.execSQL(sqlQuery);
        db.execSQL(sqlQuery2);
        db.execSQL(sqlQuery3);
        addQuestions();*/
    }

    private void addQuestions() {
        Question q1 = new Question("Which company is the largest manufacturer of network equipment ?", "HP", 1);
        this.addQuestionToDB(q1);
        /*
        Question q2 = new Question("Which of the following is NOT an operating system ?", "Linux", "BIOS", "DOS", "BIOS");
        this.addQuestionToDB(q2);
        Question q3 = new Question("Who is the founder of Apple Inc. ?", "Jose Thomas", "Bill Gates", "Steve Jobs", "Steve Jobs");
        this.addQuestionToDB(q3);
        Question q4 = new Question("Who is the first cricketer to score an international double century in 50-over match ?", "Ricky Ponting", "Sachin Tendulkar", "Brian Lara", "Sachin Tendulkar");
        this.addQuestionToDB(q4);
        Question q5 = new Question("Which is the biggest largest city in the world ?", "Vienna", "Reno", "Delhi", "Reno");
        this.addQuestionToDB(q5);
        Question q6 = new Question("Which is the biggest desert in in the world ?", "Thar", "Sahara", "Mohave", "Sahara");
        this.addQuestionToDB(q6);
        Question q7 = new Question("Which is the the largest coffee growing country in the world ?", "Brazil", "India", "Myanmar", "Brazil");
        this.addQuestionToDB(q7);
        Question q8 = new Question("Which is the longest river in the world ?", "Ganga", "Amazon", "Nile", "Nile");
        this.addQuestionToDB(q8);
        Question q9 = new Question("Which country is known as the country of copper ?", "Somalia", "Cameroon", "Zambia", "Zambia");
        this.addQuestionToDB(q9);
        Question q10 = new Question("Which is the world's oldest known city ?", "Rome", "Damascus", "Jerusalem", "Damascus");
        this.addQuestionToDB(q10);
        Question q11 = new Question("Who is the first Prime minister of India ?", "Jawaharlal Nehru", "Dr.Rajendra Prasad", "Lal Bahadur Shasthri", "Jawaharlal Nehru");
        this.addQuestionToDB(q11);
        Question q12 = new Question("Which city is known as the city of canals ?", "Paris", "Venice", "London", "Venice");
        this.addQuestionToDB(q12);
        Question q13 = new Question("Australia was discovered by ?", "James Cook", "Columbus", "Magallan", "James Cook");
        this.addQuestionToDB(q13);
        Question q14 = new Question("The national flower of Britain is ?", "Lily", "Rose", "Lotus", "Rose");
        this.addQuestionToDB(q14);
        Question q15 = new Question("The red cross was founded by ?", "Gullivar Crossby", "Alexandra Maria Lara", "Jean Henri Durant", "Jean Henri Durant");
        this.addQuestionToDB(q15);
        Question q16 = new Question("Which place is known as the roof of the world ?", "Alphs", "Tibet", "Nepal", "Tibet");
        this.addQuestionToDB(q16);
        Question q17 = new Question("Who invented washing machine ?", "James King", "Alfred Vincor", "Christopher Marcossi", "James King");
        this.addQuestionToDB(q17);
        Question q18 = new Question("Who won the Football world Cup in 2014 ?", "Italy", "Argentina", "Germany", "Germany");
        this.addQuestionToDB(q18);
        Question q19 = new Question("Who won the Cricket World cup in 2011 ?", "Australia", "India", "England", "India");
        this.addQuestionToDB(q19);
        Question q20 = new Question("The number regarded as the lucky number in Italy is ?", "13", "7", "9", "13");
        this.addQuestionToDB(q20);*/
    }

    public void addPlayerToDB(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        this.onCreate(db);
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, player.getName());
        values.put(KEY_SCORE, player.getScore());
        dbase.insert(PLAYER_NAMES, null, values);
    }


    public void addQuestionToDB(Question q) {
        ContentValues values = new ContentValues();
        values.put(KEY_QUES, q.getQuestion());
        values.put(KEY_ANSWER, q.getAnswer());
        values.put(KEY_ID, q.getId());
      /*  values.put(KEY_OPTA, q.getOptA());
        values.put(KEY_OPTB, q.getOptB());
        values.put(KEY_OPTC, q.getOptC());*/
        dbase.insert(DB_TABLE, null, values);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void UpdatePlayers(SQLiteDatabase db, HashMap<String, Long> userList) {
        db.execSQL("DROP TABLE IF EXISTS " + PLAYER_NAMES);
        String sqlQuery2 = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INT)",
                PLAYER_NAMES, KEY_ID, KEY_NAME, KEY_SCORE);
        db.execSQL(sqlQuery2);
        for (Map.Entry<String, Long> entry : userList.entrySet()) {
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, entry.getKey());
            values.put(KEY_SCORE, entry.getValue());
            db.insert(PLAYER_NAMES, null, values);
        }
    }


    public void UpdateQuestions(SQLiteDatabase db, HashMap<String, Question> questionMap) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        String sqlQuery2 = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)",
                DB_TABLE, KEY_ID, KEY_QUES, KEY_ANSWER);
        db.execSQL(sqlQuery2);
        for (Map.Entry<String, Question> q : questionMap.entrySet()) {
            ContentValues values = new ContentValues();
            values.put(KEY_QUES, q.getValue().getQuestion());
            values.put(KEY_ANSWER, q.getValue().getAnswer());
            values.put(KEY_ID, q.getValue().getId());
            db.insert(DB_TABLE, null, values);
        }
    }

    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();

        dbase = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + PLAYER_NAMES;
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        rowCount = cursor.getCount();

        if (cursor.moveToFirst()) {
            do {
                Player p = new Player();
                p.setName(cursor.getString(1));
                p.setScore(cursor.getInt(2));
                players.add(p);

            } while (cursor.moveToNext());
        }
        return players;
    }

    public void UpdateAnswers(SQLiteDatabase db, List<Answer> answerHashMap) {
        db.execSQL("DROP TABLE IF EXISTS " + ANSWERS);
        String sqlQuery2 = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s INT, %s TEXT)",
                ANSWERS, KEY_ID, KEY_QUES, KEY_ANSWER);
        db.execSQL(sqlQuery2);
        for (int i = 1; i < answerHashMap.size(); i++) {
            ContentValues values = new ContentValues();
//            values.put(KEY_ID, q.getKey());
            values.put(KEY_QUES, answerHashMap.get(i).getIdQuestion());
            values.put(KEY_ANSWER, answerHashMap.get(i).getAnswer());
            db.insert(ANSWERS, null, values);
        }
    }

    public List<Answer> getAnswersByIdQuestion(int id) {
        List<Answer> answerList = new ArrayList<>();
        dbase = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + ANSWERS + " WHERE " + KEY_QUES + "=" + id;
//        String selectQuery = "SELECT * FROM " + ANSWERS;
        @SuppressLint("Recycle") Cursor cursor = dbase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Answer answer = new Answer();
                answer.setAnswer(cursor.getString(2));
                answerList.add(answer);

            } while (cursor.moveToNext());
        }
        return answerList;
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();

        dbase = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + DB_TABLE;
        @SuppressLint("Recycle") Cursor cursor = dbase.rawQuery(selectQuery, null);
        rowCount = cursor.getCount();

        if (cursor.moveToFirst()) {
            do {
                Question q = new Question();
                q.setId(cursor.getInt(0));
                q.setQuestion(cursor.getString(1));
                q.setAnswer(cursor.getString(2));

                questionList.add(q);

            } while (cursor.moveToNext());
        }
        return questionList;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PLAYER_NAMES);
        onCreate(db);
    }

    public int getRowCount() {
        return rowCount;
    }
}
