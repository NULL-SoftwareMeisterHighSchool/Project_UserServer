package com.project.backend.article.entity;

public class UserRanking {
    private int userID;
    private int totalScore;

    public UserRanking(int userID, int totalScore) {
        this.userID = userID;
        this.totalScore = totalScore;
    }

    public int getUserID() {
        return userID;
    }

    public int getTotalScore() {
        return totalScore;
    }
}
