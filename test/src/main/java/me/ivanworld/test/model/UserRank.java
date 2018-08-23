package me.ivanworld.test.model;

public class UserRank {

    private int userId;
    private String userName;
    private int score;

    public UserRank(int userId, String userName, int score) {
        this.userId = userId;
        this.userName = userName;
        this.score = score;
    }

    public UserRank() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "UserRank{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", score=" + score +
                '}';
    }
}
