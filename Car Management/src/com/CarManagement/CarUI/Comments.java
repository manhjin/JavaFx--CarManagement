package com.CarManagement.CarUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Comments {
    private final SimpleStringProperty cusname;
    private final SimpleStringProperty comment;

    public Comments(String cusname, String comment) {

        this.cusname = new SimpleStringProperty(cusname);
        this.comment = new SimpleStringProperty(comment);

    }

    public String getCusname() {
        return cusname.get();
    }

    public SimpleStringProperty cusnameProperty() {
        return cusname;
    }

    public void setCusname(String cusname) {
        this.cusname.set(cusname);
    }

    public String getComment() {
        return comment.get();
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }
}