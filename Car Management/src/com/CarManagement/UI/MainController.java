package com.CarManagement.UI;

import com.CarManagement.CarUI.CarDA;
import com.CarManagement.CarUI.CarInformation;
import com.CarManagement.CarUI.CarsTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable{
    CarDA carDA = new CarDA();
    Stage stage = new Stage();
    @FXML
    TableView<CarsTable> carTableView;
    @FXML
    TableColumn<CarsTable,Integer> carId,carCate,carTotalStock,carAvailableStock;
    @FXML
    TableColumn<CarsTable,String>carName,carColor;
    @FXML
    TableColumn<CarsTable,Double>carPrice;
    @FXML
    ComboBox cbFindBywhat;
    @FXML
    TextField txtWhatToFind;
    @FXML
    public void txtFindbyName(){
        carTableView.setItems(carDA.getDataCars("SELECT * FROM car where `carName` LIKE '%"+txtWhatToFind.getText()+"%'"));
    }
    @FXML
    public void btnViewHandler(javafx.event.ActionEvent actionEvent){
        CarsTable getSelectedRow = carTableView.getSelectionModel().getSelectedItem();
        CarInformation carInformation = new CarInformation(getSelectedRow.getCarId());
        carDA.selectCar("SELECT * FROM CAR WHERE CARID =" + getSelectedRow.getCarId() + "");
        System.out.println(getSelectedRow.getCarId());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../CarUI/carInformation.FXML"));
            Parent CarInformation = fxmlLoader.load();
            stage.setTitle("Car Information");
            stage.setScene(new Scene(CarInformation));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
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
    public void btnProfile(ActionEvent actionEvent){
        Parent informationParent = null;
        try {
            informationParent = FXMLLoader.load(getClass().getResource("UserInformation.FXML"));
            Scene informationScene = new Scene(informationParent);
            Stage informationStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            informationStage.hide();
            informationStage.setScene(informationScene);
            informationStage.setTitle("Profile");
            informationStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carId.setCellValueFactory(new PropertyValueFactory<CarsTable,Integer>("carId"));
        carCate.setCellValueFactory(new PropertyValueFactory<CarsTable,Integer>("cateId"));
        carTotalStock.setCellValueFactory(new PropertyValueFactory<CarsTable,Integer>("totalStock"));
        carAvailableStock.setCellValueFactory(new PropertyValueFactory<CarsTable,Integer>("availablestock"));
        carName.setCellValueFactory(new PropertyValueFactory<CarsTable,String>("carName"));
        carColor.setCellValueFactory(new PropertyValueFactory<CarsTable,String>("color"));
        carPrice.setCellValueFactory(new PropertyValueFactory<CarsTable,Double>("price"));
        carTableView.setItems(carDA.getDataCars("SELECT * FROM car"));
    }
}
