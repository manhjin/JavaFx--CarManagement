package com.CarManagement.UI;

import com.CarManagement.DA.AdminDA;
import com.CarManagement.DBO.Admin;
import com.CarManagement.DBO.User;
import com.CarManagement.DBO.checkPattern;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Controller implements Initializable {

    private boolean isRegisterClicked = false;
    UserDA userDA = new UserDA();
    AdminDA adminDA = new AdminDA();
    /**
     * declare the variable of javafx scene into controller
     */
    @FXML
    JFXButton btnSignin, btnSignup,btnLogin,btnRegister;
    @FXML
    Pane pnLogin,pnRegister,leftMenuPane;
    @FXML
    TextField txtUserName,txtEmail, txtPhone,txtsignUsername,txtsignPassword;
    @FXML
    JFXDatePicker txtDob;
    @FXML
    PasswordField txtPassword, txtConfirmpassword;
    checkPattern cp = new checkPattern();
    User user = new User();
    Admin admin = new Admin();

    /**
     * Function control the selection between Sign in and Sign up.
     * @param actionEvent
     */
    @FXML
    private void actionButtonHandler(ActionEvent actionEvent){
        if (actionEvent.getSource() == btnSignin){
            pnLogin.toFront();

        }else if (actionEvent.getSource() == btnSignup){
            pnRegister.toFront();
        }
    }

    /**
     * To handle register button when ever click con register the program will insert values into database.
     * @param actionEvent
     */
    @FXML
    public void actionRegisterButton1(ActionEvent actionEvent){
        Stage stage = new Stage();
       try {
                   if (txtDob.getValue().toString().isEmpty()){
                       btnRegister.setDisable(true);
                   }else {btnRegister.setDisable(false);
                       if (txtPassword.getText().equals(txtConfirmpassword.getText()) && actionEvent.getSource().equals(btnRegister)){
                           try {
                               userDA.insertData(txtUserName.getText(), txtEmail.getText(), txtPassword.getText(), txtPhone.getText(), txtDob.getValue().toString());
                               try {
                                   FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Complete.fxml"));
                                   Parent ConfirmInformation = fxmlLoader.load();
                                   stage.setTitle("Confirm");
                                   stage.setScene(new Scene(ConfirmInformation));
                                   stage.showAndWait();

                               } catch (IOException e) {
                                   e.printStackTrace();
                               }
                               System.out.println(txtPassword.getText());
                           }
                           catch (Exception e){
                               System.out.println("hhaa");
                           }

                       }
                   }
               }catch (Exception e){
                   System.out.println("E");
               }



           }
    @FXML
    private void actionRegisterButton() {

        try {
            if (txtUserName.getText().isEmpty() || txtEmail.getText().isEmpty() || !cp.isValidEmail(txtEmail.getText()) ||
                    txtPassword.getText().isEmpty() || txtConfirmpassword.getText().isEmpty() || txtPhone.getText().isEmpty() || !cp.isValidPhone(txtPhone.getText())) {
                btnRegister.setDisable(true);
            } else {
                btnRegister.setDisable(false);


            }
        } catch (Exception e) {

        }
    }

    /**
     * handle the login button by connect into database and check valid for user
     * @param actionEvent
     * @throws SQLException
     */
    @FXML
    private void actionLoginButton() throws SQLException {
        try {

            if ( !txtsignUsername.getText().isEmpty() && !txtsignPassword.getText().isEmpty()){
                    btnLogin.setDisable(false);

                System.out.println("ok");
            } else btnLogin.setDisable(true);
        }catch (Exception e){
            System.out.println("e");
        }


    }
    @FXML
    private void actionLoginButton1(ActionEvent actionEvent) throws SQLException {
            try {
                String username = txtsignUsername.getText();
                String password = txtsignPassword.getText();
                if(userDA.isAuthUser(username,password)){
                    try {
                        userDA.getCustomerInfor(txtsignUsername.getText());
                        user.setUserName(username);
                        Parent informationParent = FXMLLoader.load(getClass().getResource("UserInformation.FXML"));
                        Scene informationScene = new Scene(informationParent);
                        Stage informationStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        informationStage.setScene(informationScene);
                        informationStage.setTitle("Information Panel");
                        informationStage.show();



                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (adminDA.isAdmin(username,password)){
                    try{
                        Parent adminParent = FXMLLoader.load(getClass().getResource("../AdminUI/Admin.FXML"));
                        Scene adminScene = new Scene(adminParent);
                        Stage adminStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        adminStage.hide();
                        adminStage.setScene(adminScene);
                        adminStage.setTitle("Admin Panel");
                        adminStage.show();
                        admin.setUserName(username);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){

            }
        }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnLogin.setDisable(true);
        btnRegister.setDisable(true);
    }
}
