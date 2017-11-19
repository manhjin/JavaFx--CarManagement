package com.CarManagement.CarUI;

import com.CarManagement.Confirm;
import com.CarManagement.DBO.Admin;
import com.CarManagement.DBO.Order;
import com.CarManagement.DBO.User;
import com.CarManagement.DBO.checkPattern;
import com.CarManagement.UI.UserDA;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OrderCar implements Initializable {
    checkPattern cp = new checkPattern();

    User user = new User();
    Admin admin = new Admin();
    OrderCarDA orderCarDA = new OrderCarDA();
    CarInformation currentInformation = new CarInformation();
    Order order = new Order();
    @FXML
    TextField txtOrderID,txtCarName,txtCustomername,txtHiredays,txtTotalmoney;
    @FXML
    TextField txtCardNum,txtCVV,txtCardDate;
    @FXML
    JFXDatePicker txtDate;
    @FXML
    JFXCheckBox cbPay;
    @FXML
    JFXButton btnOrder;
    Stage stage = new Stage();
    @FXML
    public void txtPayHandle(){
        try {
            int days = Integer.parseInt(txtHiredays.getText());
            double price = currentInformation.getPrice();
            double money = days*price;
            txtTotalmoney.setText(String.valueOf(money));
            btnOrder.setDisable(false);
        }catch (Exception e){
            btnOrder.setDisable(true);
        }
    }
    @FXML
    public void txtValidate(){
        try {
            if (cp.isValidCarDNum(txtCardNum.getText()) && cp.isValidCVV(txtCVV.getText()) && cp.isValidDate(txtCardDate.getText())){
                btnOrder.setDisable(false);
            }else btnOrder.setDisable(true);
        }catch (Exception e){
            btnOrder.setDisable(true);
        }
    }
    @FXML
    public void cbPayHandler(ActionEvent actionEvent){
        if (cbPay.isSelected()){
            System.out.println("selected");
            txtCardNum.setDisable(false);
            txtCVV.setDisable(false);
            txtCardDate.setDisable(false);
            btnOrder.setDisable(true);
        }else {
            btnOrder.setDisable(false);
            txtCardNum.setDisable(true);
            txtCVV.setDisable(true);
            txtCardDate.setDisable(true);
            txtCardNum.clear();
            txtCVV.clear();
            txtCardDate.clear();
        }
    }
    @FXML
    public void btnChatHandle(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../socketConnection/ChatScene.FXML"));
        Parent ConfirmInformation = fxmlLoader.load();
        stage.setTitle("Chat");
        stage.setScene(new Scene(ConfirmInformation));
        stage.showAndWait();
    }
    @FXML
    public void btnOrderHandler(ActionEvent actionEvent){
        Confirm confirm = new Confirm();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Confirm.fxml"));
            Parent ConfirmInformation = fxmlLoader.load();
            stage.setTitle("Confirm Action");
            stage.setScene(new Scene(ConfirmInformation));
            stage.showAndWait();
            System.out.println(confirm.getIsok() + "status1");

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (confirm.getIsok() == 1) {
            int days = Integer.parseInt(txtHiredays.getText());
            double price = currentInformation.getPrice();
            double money = days * price;
            String orderdate = txtDate.getValue().toString();
            orderCarDA.insertOrder(user.getUserID(), orderdate, orderdate, user.getAddress());
            orderCarDA.selectOrderid(user.getUserID());
            if (cbPay.isSelected() && !txtCardNum.getText().isEmpty() && !txtCVV.getText().isEmpty() && !txtCardDate.getText().isEmpty()) {
                String cardNum = txtCardNum.getText();
                int cvv = Integer.parseInt(txtCVV.getText());
                String date = txtCardDate.getText();
                orderCarDA.insertOrderDetail(order.getOrderid(), "paid", currentInformation.getCarid(), money, cardNum, cvv, date);

            } else if (!cbPay.isSelected()) {
                orderCarDA.insertOrderDetail(order.getOrderid(), "not paid", currentInformation.getCarid(), money, "null", 1, "null");
            }
            NotificationType notificationNotValid = NotificationType.SUCCESS;
            TrayNotification notValid = new TrayNotification();
            notValid.setTitle("Insert Success");
            notValid.setNotificationType(notificationNotValid);
            notValid.showAndDismiss(Duration.millis(4000));
            orderCarDA.updateStock(currentInformation.getCarid(), currentInformation.getStock() - 1);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txtCarName.setText(currentInformation.getCarName());
        String username = user.getUserName();
        txtCustomername.setText(username);
        btnOrder.setDisable(true);
    }
}
