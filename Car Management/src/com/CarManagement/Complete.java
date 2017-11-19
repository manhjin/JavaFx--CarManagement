package com.CarManagement;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class Complete {
    @FXML
    JFXButton btnOK;
    public void btnOkHandler(){
        Stage stage = (Stage) btnOK.getScene().getWindow();
        stage.close();
    }
}
