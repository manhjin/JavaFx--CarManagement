package com.CarManagement.DA;

import com.CarManagement.DBO.Admin;
import com.CarManagement.DBO.checkPattern;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminDA {
    private DBconnection database = new DBconnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement stm;
    private Admin admin;
    private checkPattern cp;

    public boolean isAdmin(String username, String password) throws SQLException {
        boolean isAdmin = false;
        String patternAdmin = "^[ADMIN]+:[A-Za-z]{1,10}"; //write again
        Pattern pattern = Pattern.compile(patternAdmin);
        Matcher matcher = pattern.matcher(username);
        if (matcher.find()){
            isAdmin = true;
        }
        else isAdmin = false;
        if (isAdmin) {
            connection = database.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM admin WHERE email = '" + username + "'";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                if (resultSet.getString("email") != null && resultSet.getString("password")
                        != null && resultSet.getString("password").equals(password)) {
                    isAdmin = true;
                }
            }
            if (!isAdmin) {
                System.out.println(false);
            }
        }
            return isAdmin;
        }



}
