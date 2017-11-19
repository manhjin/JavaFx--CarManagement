package com.CarManagement.DBO;

import com.CarManagement.DA.DBconnection;

import java.io.InputStream;

public class Admin  {
    private static int userID;
    private static String userName;
    private static String fullName;
    private static String email;
    private static String passWord;
    private static String dob;
    private static String phone;
    private static String address;
    private static InputStream image;

    public Admin(){

    }
    public Admin(int ID, String userName, String fullName, String email, String passWord, String dob ,String phone, String address, InputStream image){
        this.userID = ID;
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.passWord = passWord;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        this.image = image;
    }

    public static int getUserID() {

        return userID;
    }

    public static void setUserID(int userID) {
        Admin.userID = userID;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        Admin.userName = userName;
    }

    public static String getFullName() {
        return fullName;
    }

    public static void setFullName(String fullName) {
        Admin.fullName = fullName;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Admin.email = email;
    }

    public static String getPassWord() {
        return passWord;
    }

    public static void setPassWord(String passWord) {
        Admin.passWord = passWord;
    }

    public static String getDob() {
        return dob;
    }

    public static void setDob(String dob) {
        Admin.dob = dob;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        Admin.phone = phone;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        Admin.address = address;
    }

    public static InputStream getImage() {
        return image;
    }

    public static void setImage(InputStream image) {
        Admin.image = image;
    }
}
