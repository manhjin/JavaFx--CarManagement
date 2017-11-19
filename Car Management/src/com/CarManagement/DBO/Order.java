package com.CarManagement.DBO;

public class Order {
    private static int orderid;
    private int cusid;
    private String orderdate;
    private String deliverdate;
    private String address;
    public Order(){

    }
    public Order(int orderid){
        this.orderid = orderid;
    }
    public Order(int orderid, int cusid, String orderdate, String deliverdate, String address) {
        this.orderid = orderid;
        this.cusid = cusid;
        this.orderdate = orderdate;
        this.deliverdate = deliverdate;
        this.address = address;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getCusid() {
        return cusid;
    }

    public void setCusid(int cusid) {
        this.cusid = cusid;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getDeliverdate() {
        return deliverdate;
    }

    public void setDeliverdate(String deliverdate) {
        this.deliverdate = deliverdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
