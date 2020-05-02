package com.example.drzavnamatura_endgame;

public class User {
    private String username;
    private String email;
    private String userID;
    private String score;

    public User(String username, String email, String userID, String score) {
        this.username = username;
        this.email = email;
        this.userID = userID;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

}
