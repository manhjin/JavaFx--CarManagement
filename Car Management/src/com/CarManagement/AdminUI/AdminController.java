package com.CarManagement.AdminUI;

import com.CarManagement.CarUI.*;
import com.CarManagement.CategoryUI.CategoryDA;
import com.CarManagement.CategoryUI.CategoryName;
import com.CarManagement.CategoryUI.CategoryTable;
import com.CarManagement.Confirm;
import com.CarManagement.DA.DBconnection;
import com.CarManagement.DBO.OrderTable;
import com.CarManagement.UI.Status;
import com.CarManagement.socketConnection.ChatController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class AdminController extends CategoryDA implements Initializable{
    private static int count = 0;
    Confirm confirm = new Confirm();
    Status status = new Status();
    private File file;
    private FileInputStream fis;
    private Image image;
    private FileChooser fileChooser = new FileChooser();
    private Connection connection;
    private Statement statement;
    private DBconnection database = new DBconnection();
    private PreparedStatement stm;
    private ResultSet rs;
    Stage stage = new Stage();
    CarDA carDA = new CarDA();
    @FXML
    TableView<CategoryTable> categoryTableView;
    @FXML
    TableColumn<CategoryTable,Integer> categoryId;
    @FXML
    TableColumn<CategoryTable,String> categoryName;
    @FXML
    TableColumn<CategoryTable,String>categoryDescription;
    //Table for Cars
    @FXML
    TableView<CarsTable>carTableView;
    @FXML
    TableColumn<CarsTable,Integer> carId,carCate,carTotalStock,carAvailableStock;
    @FXML
    TableColumn<CarsTable,String>carName,carColor;
    @FXML
    TableColumn<CarsTable,Double>carPrice;
    private boolean isButtonAddNew;
    private boolean isButtonEdit;
    private boolean isButtonAddCar;
    private boolean isButtonEditcar;
    @FXML
    TextField txtCateName,txtCarname,txtPrice,txtStock;
    @FXML
    JFXComboBox cbCategory,cbCategory1;
    @FXML
    JFXColorPicker txtColor;
    @FXML
    ImageView lbImage;
    @FXML
    TextArea txtDes;
    @FXML
    JFXButton btnCate, btnCar,btnComments,btnSave,btnClear,btnAddnewcar,btnEditcar,btnSavecar,btnClearcar;
    @FXML
    AnchorPane pnCate,pnNewcar,pnCars,pnOrderMan;
    @FXML
    TextField txtavailablestock;
    @FXML
    JFXButton btnOrderMan,btnHiring,btnFinish,btnCanceled,btnEditOrder;

    /**
     * declare for order Manager
     */
    @FXML
    TableColumn<OrderTable,String> ordDate,ordPay,ordStatus;
    @FXML
    TableColumn<OrderTable,Integer>ordID,ordCus,ordCar;
    @FXML
    TableColumn<OrderTable,Double>ordPrice;
    @FXML
    TableView
            OrderManTableView;
    @FXML
    JFXComboBox type;
    @FXML
    JFXDatePicker txtStartDate, txtEndDate;
    @FXML
    LineChart<String,Number> reportChart;
    @FXML
    Label lbPrice,lbMostCust,lbMostCar;

    @FXML
    public void actionButtonDeleteHandler(){
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
        if (confirm.getIsok() == 1){
            CarsTable getSelectedRow = carTableView.getSelectionModel().getSelectedItem();

            carDA.selectCar("SELECT * FROM CAR WHERE CARID ="+getSelectedRow.getCarId()+"");
            int totalstock = getSelectedRow.getTotalStock();
            int avalablestock = getSelectedRow.getAvailablestock();
            if (avalablestock<totalstock){
                NotificationType notificationNotValid = NotificationType.ERROR;
                TrayNotification notValid = new TrayNotification();
                notValid.setTitle("Car's hiring");
                notValid.setNotificationType(notificationNotValid);
                notValid.showAndDismiss(Duration.millis(4000));
            } else if (avalablestock == totalstock){
                carDA.deleteCar(getSelectedRow.getCarId());
            }
        }
        confirm.setIsok(-1);
    }
    @FXML
    public void btnViewHandler() {
        CarsTable getSelectedRow = carTableView.getSelectionModel().getSelectedItem();
        CarInformation carInformation = new CarInformation(getSelectedRow.getCarId());
        carDA.selectCar("SELECT  * FROM CAR WHERE CARID =" + getSelectedRow.getCarId() + "");
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
    public void btnBrowseImage(){
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("File Image","*.png", "*.jpg","*.gif"));
        file = fileChooser.showOpenDialog(stage);

        if (file != null){
            System.out.println(file.getAbsolutePath());
            image = new Image(file.toURI().toString(),300,350,true,true);

            lbImage.setImage(image);


        }

    }
    @FXML
    public void btnSaveCarHandler() {

        if (isButtonAddCar) {
            String carName = txtCarname.getText();
            Double Price = Double.parseDouble(txtPrice.getText());
            int stock = Integer.parseInt(txtStock.getText());
            txtavailablestock.setText(String.valueOf(stock));
            String name = cbCategory.getValue().toString();
            String color = txtColor.getValue().toString();
            int available = Integer.parseInt(txtavailablestock.getText());
            lbImage.setImage(image);
            try {

                fis = new FileInputStream(file);
                connection = database.getConnection();
                statement = connection.createStatement();
                rs = statement.executeQuery("SELECT cateid from category where `cateName` = '" + name + "'");
                while (rs.next()) {
                    int cateid = rs.getInt("cateid");
                    if (file != null) {
                        stm = connection.prepareStatement("INSERT INTO `carmanagement`.`car` (`carName`, `price`, `cateid`, `totalstock`, `color`, `availablestock`,`Image`) VALUES (?,?,?,?,?,?,?)");
                        stm.setBinaryStream(7, (InputStream) fis, (int) file.length());
                    } else {
                        stm = connection.prepareStatement("INSERT INTO `carmanagement`.`car` (`carName`, `price`, `cateid`, `totalstock`, `color`, `availablestock`,) VALUES (?,?,?,?,?,?)");
                    }


                    stm.setString(1, carName);
                    stm.setInt(3, cateid);
                    stm.setInt(4, stock);
                    stm.setString(5, color);
                    stm.setInt(6, available);
                    stm.setDouble(2, Price);
                    stm.executeUpdate();
                }
                lbImage.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (isButtonEditcar) {
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
                String carName = txtCarname.getText();
                Double Price = Double.parseDouble(txtPrice.getText());
                int stocknew = Integer.parseInt(txtStock.getText());
                String name = cbCategory.getValue().toString();
                String color = txtColor.getValue().toString();
                CarsTable getSelectedRow = carTableView.getSelectionModel().getSelectedItem();

                //dang lam do
                int available = getSelectedRow.getAvailablestock();
                int oldstock = getSelectedRow.getAvailablestock();
                int hired = oldstock - available;
                int newavailable = stocknew - hired;
                try {

                    fis = new FileInputStream(file);
                    connection = database.getConnection();
                    statement = connection.createStatement();
                    rs = statement.executeQuery("SELECT cateid from category where `cateName` = '" + name + "'");
                    while (rs.next()) {
                        int cateid = rs.getInt("cateid");
                        stm = connection.prepareStatement("UPDATE `carmanagement`.`car` SET carname = ?,price = ?,cateid = ?,totalstock = ?,color = ?,availablestock = ?,image=? where carid = ?");

                        stm.setString(1, carName);
                        stm.setInt(3, cateid);
                        stm.setInt(4, stocknew);
                        stm.setString(5, color);
                        stm.setInt(6, newavailable);
                        stm.setDouble(2, Price);
                        stm.setBinaryStream(7, (InputStream) fis, (int) file.length());
                        stm.setInt(8, getSelectedRow.getCarId());
                        stm.executeUpdate();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            isButtonAddCar = false;
            isButtonEditcar = false;
        }
        confirm.setIsok(-1);
    }
    @FXML
    public void actionButtonHandler(ActionEvent actionEvent) throws SQLException {
        System.out.println(actionEvent);
        if (actionEvent.getSource() == btnCate){
            pnCate.toFront();
        }else if (actionEvent.getSource() == btnCar){
            carTableView.setItems(carDA.getDataCars("SELECT * FROM car"));
            pnCars.toFront();
        }else if (actionEvent.getSource() == btnAddnewcar){
            clearField();
            pnNewcar.toFront();
            isButtonAddCar = true;
        }else if (actionEvent.getSource() == btnEditcar) {
            pnNewcar.toFront();

                CarsTable getSelectedRow = carTableView.getSelectionModel().getSelectedItem();
                carDA.selectCar("SELECT * FROM CAR WHERE CARID =" + getSelectedRow.getCarId() + "");
                System.out.println(getSelectedRow.getImage());
                txtCarname.setText(getSelectedRow.getCarName());
                txtPrice.setText(String.valueOf(getSelectedRow.getPrice()));
                txtColor.setValue(Color.valueOf(getSelectedRow.getColor()));
                txtStock.setText(String.valueOf(getSelectedRow.getTotalStock()));
                int available = getSelectedRow.getAvailablestock();
                int stock = getSelectedRow.getTotalStock();
                int hired = stock - available;
                System.out.println("avai" + available);
                System.out.println("hired:" + hired);
                txtavailablestock.setText("Hired :" + String.valueOf(hired) + " Available:" + available);
                OutputStream os = null;
                try {
                    os = new FileOutputStream(new File("photo.jpg"));
                    System.out.println("file output");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                byte[] content = new byte[1024];
                int size = 0;
                try {
                    while ((size = getSelectedRow.getImage().read(content)) != -1) {
                        os.write(content, 0, size);

                    }
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                image = new Image("file:photo.jpg", 350, 300, true, true);
                lbImage.setImage(image);

                isButtonEditcar = true;
            }

    }

    @FXML
    public void btnAddNewHandle(ActionEvent actionEvent){
        setAllEnable();
        txtavailablestock.clear();
        txtStock.clear();
        txtPrice.clear();
        txtCateName.clear();
        isButtonAddNew = true;
    }
    @FXML
    public void btnServerChatHandle(ActionEvent actionEvent) throws IOException {
        ChatController chatController = new ChatController();
        chatController.isServer= true;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../socketConnection/ChatScene.FXML"));
            Parent ConfirmInformation = fxmlLoader.load();
            stage.setTitle("Chat");
            stage.setScene(new Scene(ConfirmInformation));
            stage.showAndWait();
    }
    public void setAllEnable(){
        txtDes.setDisable(false);
        txtCateName.setDisable(false);
        btnSave.setDisable(false);
        btnClear.setDisable(false);
    }
    @FXML
    public void btnEditHandle(ActionEvent actionEvent){
        try{
            CategoryTable getSelectedRow = categoryTableView.getSelectionModel().getSelectedItem();
            selectCate("SELECT * FROM category WHERE cateId = "+getSelectedRow.getCategoryID()+"");
            setAllEnable();
            txtCateName.setText(getSelectedRow.getCategoryName());
            txtDes.setText(getSelectedRow.getCateDescription());
            isButtonEdit = true;
        }catch (NullPointerException e){
            System.out.println("null");
        }
    }
    public void btnClearHandle(){
        clearField();
    }
    @FXML
    public void btnSaveHandle(){
        String name = txtCateName.getText();
        String des = txtDes.getText();
        if (isButtonAddNew){

            if (!name.isEmpty()){
                insertCate(name,des);
            }
            else System.out.println("please input category name");

        }
        else if (isButtonEdit) {
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
                CategoryTable getSelectedRow = categoryTableView.getSelectionModel().getSelectedItem();
                System.out.println(getSelectedRow.getCategoryID());
                updateCate(getSelectedRow.getCategoryID(), name, des);

            }
        }
        categoryTableView.setItems(getData("SELECT * FROM category"));
        isButtonEdit = false;
        isButtonAddNew = false;
        clearField();
        setDisable();
        confirm.setIsok(-1);
    }
    @FXML
    public void btnDeleteHandle(ActionEvent actionEvent){
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
        if (confirm.getIsok() == 1){
            CategoryTable getSelectedRow = categoryTableView.getSelectionModel().getSelectedItem();
            int id = getSelectedRow.getCategoryID();
            deleteCate(id);
            categoryTableView.setItems(getData("SELECT * FROM category"));
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            categoryId.setCellValueFactory(new PropertyValueFactory<CategoryTable,Integer>("categoryID"));
            categoryName.setCellValueFactory(new PropertyValueFactory<CategoryTable,String>("categoryName"));
            categoryDescription.setCellValueFactory(new PropertyValueFactory<CategoryTable,String>("cateDescription"));
            categoryTableView.setItems(getData("SELECT * FROM category"));
            //cars
            carId.setCellValueFactory(new PropertyValueFactory<CarsTable,Integer>("carId"));
            carCate.setCellValueFactory(new PropertyValueFactory<CarsTable,Integer>("cateId"));
            carTotalStock.setCellValueFactory(new PropertyValueFactory<CarsTable,Integer>("totalStock"));
            carAvailableStock.setCellValueFactory(new PropertyValueFactory<CarsTable,Integer>("availablestock"));
            carName.setCellValueFactory(new PropertyValueFactory<CarsTable,String>("carName"));
            carColor.setCellValueFactory(new PropertyValueFactory<CarsTable,String>("color"));
            carPrice.setCellValueFactory(new PropertyValueFactory<CarsTable,Double>("price"));
            carTableView.setItems(carDA.getDataCars("SELECT * FROM car"));
            cbCategory.getItems().addAll(getName());
            cbCategory1.getItems().addAll("Finished","Hiring","Canceled");

            //Order
            ordID.setCellValueFactory(new PropertyValueFactory<OrderTable,Integer>("ordid"));
            ordCar.setCellValueFactory(new PropertyValueFactory<OrderTable,Integer>("ordCar"));
            ordCus.setCellValueFactory(new PropertyValueFactory<OrderTable,Integer>("ordCus"));
            ordDate.setCellValueFactory(new PropertyValueFactory<OrderTable,String>("ordDate"));
            ordPay.setCellValueFactory(new PropertyValueFactory<OrderTable,String>("ordPay"));
            ordPrice.setCellValueFactory(new PropertyValueFactory<OrderTable,Double>("ordPrice"));
            ordStatus.setCellValueFactory(new PropertyValueFactory<OrderTable,String>("ordStatus"));
            //


        }catch (NullPointerException ex) {
            System.out.println("Null Pointer");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //ORDER MANAGE FROM HERE
    @FXML
    public void actionOrderMan(ActionEvent actionEvent) throws SQLException {
        OrderCarDA odCar = new OrderCarDA();
            pnOrderMan.toFront();

            OrderManTableView.setItems(odCar.getOrderInfor("Select distinct o.id,o.cusid,o.orderdate,od.pay,od.price,od.carid,o.status from carmanagement.order as o,orderdetail as od where o.id = od.orderid group by o.id "));
    }
    @FXML
    public void actionViewTable(ActionEvent actionEvent) throws SQLException {

        OrderCarDA odCar = new OrderCarDA();
        if (actionEvent.getSource()==btnHiring){
            OrderManTableView.setItems(odCar.getOrderInfor("Select distinct o.id,o.cusid,o.orderdate,od.pay,od.price,od." +
                    "carid,o.status from carmanagement.order as o,orderdetail as od where o.id = od.orderid group by o.id "));


        }else if (actionEvent.getSource() == btnCanceled){
            OrderManTableView.setItems(odCar.getOrderInfor("Select distinct o.id,o.cusid,o.orderdate,od.pay,od.price,od.car" +
                    "id,o.status from carmanagement.order as o,orderdetail as od where o.id = od.orderid and o.status = 'hiring' group by o.id "));

        }else if (actionEvent.getSource() == btnFinish){
            OrderManTableView.setItems(odCar.getOrderInfor("Select distinct o.id,o.cusid,o.orderdate,od.pay,od.price,od.carid," +
                    "o.status from carmanagement.order as o,orderdetail as od where o.id = od.orderid and o.status = 'finished' group by o.id "));
        }
    }
    @FXML
    public void mouseClickedHandler(){
        OrderTable getSelectedRow = (OrderTable) OrderManTableView.getSelectionModel().getSelectedItem();
        if (getSelectedRow.getOrdStatus().equals("Finished") || getSelectedRow.getOrdStatus().equals("Canceled")){
            btnEditOrder.setDisable(true);
        }else btnEditOrder.setDisable(false);
    }
    @FXML
    public void btnEditOrderHandler(ActionEvent actionEvent) {
        OrderCarDA ord = new OrderCarDA();
        OrderTable getSelectedRow = (OrderTable) OrderManTableView.getSelectionModel().getSelectedItem();
        try {
            String status = cbCategory1.getValue().toString();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Confirm.fxml"));
                Parent ConfirmInformation = fxmlLoader.load();
                stage.setTitle("Confirm");
                stage.setScene(new Scene(ConfirmInformation));
                stage.showAndWait();
                System.out.println(confirm.getIsok() + "status1");

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (confirm.getIsok() == 1)     {
                int id = getSelectedRow.getOrdid();
                int carid = getSelectedRow.getOrdCar();
                if (status.equals("Finished")){
                    ord.updateFinishedCar(status, id);
                    ord.updateAvaiFinishedCar(carid);

                }else {
                    ord.updateOrderCar(status, id);}
            }
        }catch (NullPointerException e){
            NotificationType notificationNotValid = NotificationType.ERROR;
            TrayNotification notValid = new TrayNotification();
            notValid.setTitle("Please Select Status First");
            notValid.setNotificationType(notificationNotValid);
            notValid.showAndDismiss(Duration.millis(4000));
        }


        confirm.setIsok(-1);
    }
    //REPORT FROM HERE
    @FXML
    public void actionReportByDate(ActionEvent actionEvent){
        //reportChart
        count ++;
       String sDate =  txtStartDate.getValue().toString();
       String eDate = txtEndDate.getValue().toString();
        XYChart.Series<String,Number> reportValue = new XYChart.Series<String,Number>();
        System.out.println("aaaaaa");
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            for (int i = 1; i <12 ; i++) {
            rs = statement.executeQuery("Select count(*) as count from `order` as o where MONTH(o.orderdate) ="+i+" and o.orderdate between '"+sDate+"' and '"+eDate+"'");
            while (rs.next()){

                        reportValue.getData().add(new XYChart.Data<String,Number>(i+ "", rs.getInt("count")));


                }
            }
                reportValue.setName("All Order" + count);
                reportChart.getData().add(reportValue);
            rs = statement.executeQuery("Select sum(price) as totalprice from orderdetail as od, `order` as o where o.id = od.orderid and o.orderdate between '"+sDate+"' and '"+eDate+"'");
            while (rs.next()){
                double price = rs.getDouble("totalprice");
                lbPrice.setText("Total Income: "+String.valueOf(price));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    //NONE FXML FROM HERE
    public void clearField(){
        txtDes.clear();
        txtCateName.clear();
        txtCarname.clear();
        txtPrice.clear();
        txtStock.clear();
        txtavailablestock.clear();
        lbImage.setImage(null);
        txtColor.setValue(Color.WHITE);
    }
    public void setDisable(){
        txtCateName.setDisable(true);
        txtDes.setDisable(true);
        btnClear.setDisable(true);
        btnSave.setDisable(true);

    }

}
