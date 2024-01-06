package com.example.mtseminar;

public class UserScore {
    private String user;
    private String score;

    public UserScore() {
    }

    public UserScore(String user, String score) {
        this.user = user;
        this.score = score;
    }

    public String getUser() {
        return user;
    }

    public String getScore() {
        return score;
    }

    public void setUser(String user){
        this.user = user;
    }

    public void setScore(String score){
        this.score = score;
    }
}

