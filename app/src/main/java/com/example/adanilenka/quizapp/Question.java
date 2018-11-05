package com.example.adanilenka.quizapp;

/**
 * Created by adanilenka
 */
public class Question {
    private int id;
    private String question;
    private String answer;

    public Question(){
    }

    public Question(String question, String answer, int id){
        this.question = question;
        this.answer = answer;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
