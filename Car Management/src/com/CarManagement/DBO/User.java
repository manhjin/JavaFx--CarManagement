package com.CarManagement.DBO;

import java.io.InputStream;

public class User {



    private static int userID;
    private static String userName;
    private static String fullName;
    private static String email;
    private static String passWord;
    private static String dob;
    private static String phone;
    private static String address;
    private static InputStream image;
    private static String userType;
    public User(){

    }
    public User(int userID, String userName, String email, String passWord, String dob, String phone, InputStream image, String userType) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.passWord = passWord;
        this.dob = dob;
        this.phone = phone;
        this.image = image;
        this.userType = userType;
    }
    public User(int userID, String userName,String fullName, String email, String passWord, String dob, String phone, InputStream image,String address, String userType) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.passWord = passWord;
        this.dob = dob;
        this.phone = phone;
        this.image = image;
        this.userType = userType;
        this.fullName = fullName;
        this.address = address;
    }

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        User.userID = userID;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        User.userName = userName;
    }

    public static String getFullName() {
        return fullName;
    }

    public static void setFullName(String fullName) {
        User.fullName = fullName;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getPassWord() {
        return passWord;
    }

    public static void setPassWord(String passWord) {
        User.passWord = passWord;
    }

    public static String getDob() {
        return dob;
    }

    public static void setDob(String dob) {
        User.dob = dob;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        User.phone = phone;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        User.address = address;
    }

    public static InputStream getImage() {
        return image;
    }

    public static void setImage(InputStream image) {
        User.image = image;
    }

    public static String getUserType() {
        return userType;
    }

    public static void setUserType(String userType) {
        User.userType = userType;
    }

    @Override
    public String toString() {
        return userName;
    }
}
