package com.CarManagement.DA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/carmanagement?autoReconnect=true&useSSL=false";
    private String user = "CarManagement";
    private String password = "Manhdaik123";

    public Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(url,user,password);
        return connection;
    }
    public static void closeConnection(Connection con){
        try{
            if(con != null) con.close();
        }catch(SQLException e){
            System.out.println("Error closing database");
        }
    }
}
