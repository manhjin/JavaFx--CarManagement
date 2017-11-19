package com.CarManagement.DBO;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderTable {
    private final SimpleIntegerProperty ordid;
    private final SimpleIntegerProperty ordCus;
    private final SimpleDoubleProperty ordPrice;
    private final SimpleIntegerProperty ordCar;
    private final SimpleStringProperty ordDate;
    private final SimpleStringProperty ordPay;
    private final SimpleStringProperty ordStatus;

    public OrderTable(int ordid, int ordCus, Double ordPrice, int ordCar, String ordDate, String ordPay, String ordStatus) {
        this.ordid = new SimpleIntegerProperty(ordid);
        this.ordCus = new SimpleIntegerProperty(ordCus);
        this.ordPrice = new SimpleDoubleProperty(ordPrice);
        this.ordCar = new SimpleIntegerProperty(ordCar);
        this.ordDate = new SimpleStringProperty(ordDate);
        this.ordPay = new SimpleStringProperty(ordPay);
        this.ordStatus = new SimpleStringProperty(ordStatus);
    }

    public int getOrdid() {
        return ordid.get();
    }

    public SimpleIntegerProperty ordidProperty() {
        return ordid;
    }

    public void setOrdid(int ordid) {
        this.ordid.set(ordid);
    }

    public int getOrdCus() {
        return ordCus.get();
    }

    public SimpleIntegerProperty ordCusProperty() {
        return ordCus;
    }

    public void setOrdCus(int ordCus) {
        this.ordCus.set(ordCus);
    }

    public double getOrdPrice() {
        return ordPrice.get();
    }

    public SimpleDoubleProperty ordPriceProperty() {
        return ordPrice;
    }

    public void setOrdPrice(double ordPrice) {
        this.ordPrice.set(ordPrice);
    }

    public int getOrdCar() {
        return ordCar.get();
    }

    public SimpleIntegerProperty ordCarProperty() {
        return ordCar;
    }

    public void setOrdCar(int ordCar) {
        this.ordCar.set(ordCar);
    }

    public String getOrdDate() {
        return ordDate.get();
    }

    public SimpleStringProperty ordDateProperty() {
        return ordDate;
    }

    public void setOrdDate(String ordDate) {
        this.ordDate.set(ordDate);
    }

    public String getOrdPay() {
        return ordPay.get();
    }

    public SimpleStringProperty ordPayProperty() {
        return ordPay;
    }

    public void setOrdPay(String ordPay) {
        this.ordPay.set(ordPay);
    }

    public String getOrdStatus() {
        return ordStatus.get();
    }

    public SimpleStringProperty ordStatusProperty() {
        return ordStatus;
    }

    public void setOrdStatus(String ordStatus) {
        this.ordStatus.set(ordStatus);
    }
}
