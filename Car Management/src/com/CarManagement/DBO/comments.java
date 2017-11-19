package com.CarManagement.DBO;

public class comments {
    private int commentId;
    private int carId;
    private String comments;

    public comments(int commentId, int carId, String comments) {
        this.commentId = commentId;
        this.carId = carId;
        this.comments = comments;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
