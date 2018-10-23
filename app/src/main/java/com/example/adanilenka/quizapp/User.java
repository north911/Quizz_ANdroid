package com.example.adanilenka.quizapp;

public class User {
    private int id;
    private String username;
    private int score;

    public User() {
        id = 0;
        username = "";
        score = 0;
    }

    public User(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
