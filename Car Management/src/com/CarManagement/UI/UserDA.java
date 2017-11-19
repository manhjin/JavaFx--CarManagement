package com.CarManagement.UI;

import com.CarManagement.DA.DBconnection;
import com.CarManagement.DBO.User;
import javafx.fxml.FXML;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class UserDA extends DBconnection {
    private DBconnection database = new DBconnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement stm;
    private User user;

    /**
     * I will call this function in login class to insert data from register pane to database
     *
     * @param userName userName from text
     * @param email email from text
     * @param password
     * @param phone
     * @param dob
     */
    public void insertData(String userName, String email, String password, String phone, String dob){
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            String sql = "INSERT INTO `carmanagement`.`user` (`username`, `email`, `password`, `phone`, `dob`) VALUES (?,?,?,?,?);";
            stm= connection.prepareStatement(sql);
            stm.setString(1,userName);
            stm.setString(2,email);
            stm.setString(3,password);
            stm.setString(4,phone);
            stm.setString(5,dob);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateData(String username,String email, String full, String address, String phone,FileInputStream image ){
        try {
            connection = database.getConnection();
            stm = connection.prepareStatement("UPDATE `carmanagement`.`user` SET `image` = ? ,`email` = '?', `fullname` = '?',\" +\n" +
                    "                    \"`address` ='address',`phone` = '?' WHERE `username`='?'");
            stm.setBinaryStream(1,image);
            stm.setString(2,email);
            stm.setString(3,full);
            stm.setString(4,address);
            stm.setString(5,phone);
            stm.setString(6,username);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void updateImage(String image) throws SQLException {

        try {
            connection = database.getConnection();
            stm = connection.prepareStatement("UPDATE `carmanagement`.`user` SET `uImage` = ?");
            InputStream is = new FileInputStream(new File(image));
            stm.setBlob(1,is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void updateDate(String username){
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            String sql = "UPDATE `carmanagement`.`user` SET `uImage`=? WHERE `username`='"+username+"';\n";
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to check username and password of user.
     *
     * @param username
     * @param Password
     * @return true if correct both of user name and password
     * @throws SQLException
     */
    public boolean isAuthUser(String username, String Password) throws SQLException{
         boolean userauth = false;
        connection = database.getConnection();
        statement = connection.createStatement();
        String sql = "SELECT * FROM user WHERE username = '"+username+"'";
        resultSet = statement.executeQuery(sql);

        while (resultSet.next()){
            if (resultSet.getString("username") != null && resultSet.getString("password")
                    != null && resultSet.getString("password").equals(Password)){
                        userauth = true;
            }
        }
        if (!userauth){
            System.out.println(false);
        }
        return userauth;
    }
    public ArrayList<User> getAllCustomer() throws SQLException, IOException {
        String selectSQL = "SELECT * FROM User";
        Statement stm = connection.createStatement();
        ResultSet res = stm.executeQuery(selectSQL);
        ArrayList<User> customers = new ArrayList<>();
        while(res.next()){
            int id = res.getInt("userID");
            String name = res.getString("username");
            String email = res.getString("email");
            String password = res.getString("password");
            String phone = res.getString("phone");
            String dob = res.getString("dob");
            InputStream image = res.getBinaryStream("uImage");
            String fullname = res.getString("fullname");
            String address = res.getString("address");
            User cust = new User(id,fullname,name,email,password,dob,phone,image,address,"free");
            customers.add(cust);
            image.close();
        }
        return customers;
    }
    public ArrayList<User> getCustomerInfor(String username) throws SQLException, IOException {
        System.out.println("get" +username+"information");
         String sql = "SELECT * FROM user WHERE user.username = '"+username+ "'";
         Statement stm = connection.createStatement();
         ResultSet res = stm.executeQuery(sql);
        ArrayList<User> logCustomer = new ArrayList<>();
        while (res.next()){
            int id = res.getInt("userID");
            String name = res.getString("username");
            String email = res.getString("email");
            String password = res.getString("password");
            String phone = res.getString("phone");
            String dob = res.getString("dob");
            InputStream image = res.getBinaryStream("uImage");
            String fullname = res.getString("fullname");
            String address = res.getString("address");
            User cust = new User(id,fullname,name,email,password,dob,phone,image,address,"free");
            logCustomer.add(cust);
            cust.setUserName(username);
            image.close();
        }
        return logCustomer;
    }
}
