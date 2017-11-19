package com.CarManagement.CategoryUI;

import com.CarManagement.DA.DBconnection;
import com.CarManagement.DBO.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;


import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryDA implements Initializable{
    private DBconnection database = new DBconnection();
    private Connection connection;
    private Statement statement;
    private PreparedStatement stm;
    private ResultSet resultSet;

    public ObservableList getData(String query){
        ObservableList<CategoryTable> categoryTables = FXCollections.observableArrayList();
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);//"SELECT * FROM category;"
            while(resultSet.next()){
                categoryTables.add(new CategoryTable(
                        resultSet.getInt("cateId"),
                        resultSet.getString("cateName"),
                        resultSet.getString("description")
                ));

            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryTables;

    }
    public ArrayList<CategoryName> getName() throws SQLException {
        String sql = "SELECT cateName,cateID FROM category";
        connection = database.getConnection();
        Statement stm = connection.createStatement();
        ResultSet res = stm.executeQuery(sql);
        ArrayList<CategoryName> categoryNames = new ArrayList<>();
        while (res.next()){
            int id = res.getInt("cateId");
            String name = res.getString("cateName");

            CategoryName categoryName = new CategoryName(name,id);
            categoryNames.add(categoryName);

        }
        connection.close();
        res.close();
        stm.close();
        return categoryNames;
    }
    public ArrayList<CategoryName> getName1(int cateid) throws SQLException {
        String sql = "SELECT cateName,cateID FROM category where cateid = "+cateid+"";
        connection = database.getConnection();
        Statement stm = connection.createStatement();
        ResultSet res = stm.executeQuery(sql);
        ArrayList<CategoryName> categoryNames = new ArrayList<>();
        while (res.next()){
            int cateId = res.getInt("cateId");
            String cateName = res.getString("cateName");
            CategoryName categoryName = new CategoryName(cateName,cateId);
            categoryNames.add(categoryName);

        }
        connection.close();
        res.close();
        stm.close();
        return categoryNames;
    }
    public void selectCate(String sqlQuery){
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()){
                int cateID = resultSet.getInt("cateid");
                String cateName = resultSet.getString("cateName");
                String description = resultSet.getString("description");
                CategoryTable cateTable = new CategoryTable(cateID,cateName,description);
            }
            connection.close();
            statement.close();
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void insertCate( String cateName, String description){
        try {
            String sql = "INSERT INTO `carmanagement`.`category` (`cateName`, `description`) VALUES ('"+cateName+"','"+description+"')";
            connection = database.getConnection();
            stm = connection.prepareStatement(sql);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error when insert category");
        }
    }
    public void updateCate(int cateid ,String cateName,String description){
        try {
            String sql = "UPDATE carmanagement.category SET cateName = '"+cateName+"', description = '"+description+"' where cateId = '"+cateid+"'";
            connection = database.getConnection();
            stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void deleteCate(int cateid){
        try{
            String sql ="DELETE FROM carmanagement.category WHERE Cateid = '"+cateid+"'";
            connection = database.getConnection();
            stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
