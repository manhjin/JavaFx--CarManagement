package com.CarManagement;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;


public class Confirm {
    @FXML
    JFXButton btnOK,btnCancel;

    private static int isok =-1;

    public void btnHandler(ActionEvent actionEvent){
        if (actionEvent.getSource() == btnOK){
            setIsok(1);
            isok =1;
            Stage stage = (Stage) btnOK.getScene().getWindow();
            stage.close();
        }else if (actionEvent.getSource() == btnCancel){
            isok = 0;
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        }else isok = 0;



    }

    public static int getIsok() {
        return isok;
    }

    public static void setIsok(int isok) {
        Confirm.isok = isok;
    }
}
