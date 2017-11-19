package com.CarManagement;

import com.CarManagement.DA.DBconnection;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Comment implements Initializable {
    public Comment(){

    }
    @FXML
    JFXButton btnSubmit;
    @FXML
    TextField txtCarID;
    @FXML
    TextArea txtComment;
    private DBconnection database = new DBconnection();
    private PreparedStatement stm;
    Statement statement;
    Connection connection;
    private static int userID;
    private static int carId;

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        Comment.userID = userID;
    }

    public static int getCarId() {
        return carId;
    }

    public static void setCarId(int carId) {
        Comment.carId = carId;
    }

    @FXML
    public void txtCommentValid(){
        if (txtComment.getText().isEmpty()){
            btnSubmit.setDisable(true);
        }else btnSubmit.setDisable(false);
    }
    @FXML
    public void btnSubmitHandler(){
        String carid = txtCarID.getText();
        String comment = txtComment.getText();
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            String sql = "INSERT INTO `carmanagement`.`comments` (`carid`, `comment`, `customerid`) VALUES (?,?,?);";
            stm= connection.prepareStatement(sql);
            stm.setString(1,carid);
            stm.setString(2,comment);
            stm.setInt(3,getUserID());
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) btnSubmit.getScene().getWindow();
        stage.close();
        NotificationType notificationNotValid = NotificationType.SUCCESS;
        TrayNotification notValid = new TrayNotification();
        notValid.setTitle("Submitted");
        notValid.setNotificationType(notificationNotValid);
        notValid.showAndDismiss(Duration.millis(4000));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtCarID.setText(String.valueOf(getCarId()));
        btnSubmit.setDisable(true);
    }
}
