package com.CarManagement.UI;

import java.util.ArrayList;

public class Status {
    int statusid;
    int statusname;
    public Status (){

    }
    public Status(int statusid, int statusname) {
        this.statusid = statusid;
        this.statusname = statusname;
    }

    public int getStatusid() {
        return statusid;
    }

    public void setStatusid(int statusid) {
        this.statusid = statusid;
    }

    public int getStatusname() {
        return statusname;
    }

    public void setStatusname(int statusname) {
        this.statusname = statusname;
    }
}
