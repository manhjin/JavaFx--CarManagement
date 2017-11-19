package com.CarManagement.CarUI;

import com.CarManagement.DA.DBconnection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CarInformation {
    private static int carid;
    private static String carName;
    private static String color;
    private static int stock;
    private static double price;
    private static InputStream image;

    DBconnection database = new DBconnection();
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    public void getCurrentInformation(){

        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM car where carId = '"+carid+"';");
            while (resultSet.next()){
                carid = resultSet.getInt("carID");
                carName = resultSet.getString("carName");
                price = resultSet.getDouble("price");
                color = resultSet.getString("color");
                stock = resultSet.getInt("availablestock");
                image = resultSet.getBinaryStream("image");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public CarInformation(int carid, String carName, String color, int stock, double price, InputStream image) {
        this.carid = carid;
        this.carName = carName;
        this.color = color;
        this.stock = stock;
        this.price = price;
        this.image = image;
    }
    public CarInformation(int id){
        this.carid=id;
        getCurrentInformation();

    }

    public int getCarid() {
        return carid;
    }

    public void setCarid(int carid) {
        this.carid = carid;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    public CarInformation(){

    }
}
