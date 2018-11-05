package com.example.adanilenka.quizapp;

public class Answer {

    private int idQuestion;
    private String answer;

    public Answer() {
    }

    public Answer(int idQuestion, String answer) {
        this.idQuestion = idQuestion;
        this.answer = answer;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
