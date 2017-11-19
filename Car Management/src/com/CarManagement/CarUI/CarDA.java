package com.CarManagement.CarUI;

import com.CarManagement.CategoryUI.CategoryTable;
import com.CarManagement.DA.DBconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;

public class CarDA {
    FileInputStream fis;
    private DBconnection database = new DBconnection();
    private Connection connection;
    private Statement statement;
    private PreparedStatement stm;
    private ResultSet resultSet;
    public ObservableList getDataCars(String query){
        ObservableList<CarsTable> carsTables = FXCollections.observableArrayList();
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);//"SELECT * FROM car;"
            while(resultSet.next()){
                carsTables.add(new CarsTable(
                        resultSet.getInt("carId"),
                        resultSet.getString("carName"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("cateid"),
                        resultSet.getInt("totalStock"),
                        resultSet.getInt("availablestock"),
                        resultSet.getString("color")
                ));

            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carsTables;

    }
    public void selectCar(String sqlQuery){
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()){
                int CarID = resultSet.getInt("cateid");
                String carname = resultSet.getString("carname");
                Double carprice = resultSet.getDouble("price");
                int cateID = resultSet.getInt("cateid");
                String color = resultSet.getString("color");
                int stock = resultSet.getInt("totalstock");
                int availablestock = resultSet.getInt("availablestock");
                InputStream image = resultSet.getBinaryStream("image");
                System.out.println(image);
                CarsTable carsTable = new CarsTable(CarID,carname,carprice,cateID,stock,availablestock,color,image);
            }
            connection.close();
            statement.close();
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void deleteCar(int carid){
        try {
            String sql = "delete from carmanagement.car where carID = "+carid+"";
            connection = database.getConnection();
            statement = connection.createStatement();
            stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateCar(int carid, String carName, Double price, String cateName, int totalStock, String color,int availableStock, InputStream Image ){

    }
}
