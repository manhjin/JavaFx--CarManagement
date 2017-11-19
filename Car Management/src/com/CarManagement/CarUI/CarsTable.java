package com.CarManagement.CarUI;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.InputStream;

public class CarsTable {
    private final SimpleIntegerProperty carId;
    private final SimpleStringProperty carName;
    private final SimpleDoubleProperty price;
    private final SimpleIntegerProperty cateId;
    private final SimpleIntegerProperty totalStock;
    private final SimpleIntegerProperty availablestock;
    private final SimpleStringProperty color;
    private static InputStream image;


    public CarsTable(int carId, String carName, Double price, int cateId, int totalStock, int availablestock, String color) {
        this.carId = new SimpleIntegerProperty(carId);
        this.carName = new SimpleStringProperty(carName);
        this.price = new SimpleDoubleProperty(price);
        this.cateId = new SimpleIntegerProperty(cateId);
        this.totalStock = new SimpleIntegerProperty(totalStock);
        this.availablestock = new SimpleIntegerProperty(availablestock);
        this.color = new SimpleStringProperty(color);
    }
    public CarsTable(int carId, String carName, Double price, int cateId, int totalStock, int availablestock, String color,InputStream image) {
        this.carId = new SimpleIntegerProperty(carId);
        this.carName = new SimpleStringProperty(carName);
        this.price = new SimpleDoubleProperty(price);
        this.cateId = new SimpleIntegerProperty(cateId);
        this.totalStock = new SimpleIntegerProperty(totalStock);
        this.availablestock = new SimpleIntegerProperty(availablestock);
        this.color = new SimpleStringProperty(color);
        this.image = image;
    }


    public int getCarId() {
        return carId.get();
    }


    public SimpleIntegerProperty carIdProperty() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId.set(carId);
    }

    public String getCarName() {
        return carName.get();
    }

    public SimpleStringProperty carNameProperty() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName.set(carName);
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public int getCateId() {
        return cateId.get();
    }

    public SimpleIntegerProperty cateIdProperty() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId.set(cateId);
    }

    public int getTotalStock() {
        return totalStock.get();
    }

    public SimpleIntegerProperty totalStockProperty() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock.set(totalStock);
    }

    public int getAvailablestock() {
        return availablestock.get();
    }

    public SimpleIntegerProperty availablestockProperty() {
        return availablestock;
    }

    public void setAvailablestock(int availablestock) {
        this.availablestock.set(availablestock);
    }

    public String getColor() {
        return color.get();
    }

    public SimpleStringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }


}
