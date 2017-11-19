package com.CarManagement.CarUI;

import com.CarManagement.AdminUI.AdminController;
import com.CarManagement.CategoryUI.CategoryTable;
import com.CarManagement.DA.DBconnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class carController implements Initializable {
    AdminController adminController = new AdminController();
    Connection connection;
    DBconnection database = new DBconnection();
    Statement statement;
    ResultSet rs;
    private Stage stage;
    private CarInformation currentInformation = new CarInformation();
    @FXML
    Label lbCarname;
    @FXML
    JFXButton btnOrder;
    @FXML
    Label lbStock, lbPrice;
    @FXML
    JFXColorPicker txtColorCarDetail;
    @FXML
    ImageView imgCardetail;
    @FXML
    LineChart<String, Number> reportChart;
    @FXML
    TableColumn<Comments, String> cusname;
    @FXML
    TableColumn<Comments, String> comment;
    @FXML
    TableView commentTableView;
    private Image image;

    public void btnOrderHandler(ActionEvent actionEvent) throws IOException {
        Parent OrderParent = FXMLLoader.load(getClass().getResource("OrderCar.FXML"));
        Scene OrderScene = new Scene(OrderParent);
        Stage orderStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        orderStage.hide();
        orderStage.setScene(OrderScene);
        orderStage.setTitle("Order Panel");
        orderStage.show();
    }

    @FXML
    public void btnChatHandle(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../socketConnection/ChatScene.FXML"));
        Parent ConfirmInformation = fxmlLoader.load();
        stage.setTitle("Confirm Action");
        stage.setScene(new Scene(ConfirmInformation));
        stage.showAndWait();
    }
    public ObservableList getComment(String query){
        ObservableList<Comments> comments = FXCollections.observableArrayList();
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);//"SELECT * FROM category;"
            while(rs.next()){
                comments.add(new Comments(
                        rs.getString("username"),
                        rs.getString("comment")
                ));

            }
            connection.close();
            statement.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //comment from here
        cusname.setCellValueFactory(new PropertyValueFactory<Comments,String>("cusname"));
        comment.setCellValueFactory(new PropertyValueFactory<Comments,String>("comment"));
        commentTableView.setItems(getComment("select user.username,cmt.comment from comments as cmt, user as user where user.userid = cmt.customerid and carid = "+currentInformation.getCarid()+""));




        //set Report
        XYChart.Series<String, Number> reportValue = new XYChart.Series<String, Number>();
        System.out.println("aaaaaa");
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            for (int i = 1; i < 12; i++) {
                rs = statement.executeQuery("Select count(*) as count from `order` as o, orderdetail as od where MONTH(o.orderdate) ="+i+" and o.id = od.orderid and od.carid = "+currentInformation.getCarid()+"");
                while (rs.next()) {

                    reportValue.getData().add(new XYChart.Data<String, Number>(i + "", rs.getInt("count")));


                }
            }
            reportValue.setName("Order Trend");
            reportChart.getData().add(reportValue);


            //G
            String carName = currentInformation.getCarName();
            int stock = currentInformation.getStock();
            String color = currentInformation.getColor();
            int carid = currentInformation.getCarid();
            Double price = currentInformation.getPrice();
            InputStream getimage = currentInformation.getImage();
            lbCarname.setText("Car Name :" + carName);
            lbPrice.setText("Price per Day: " + price);

            if (stock > 0) {
                lbStock.setText("Stock: " + stock);
            } else {
                lbStock.setText("Out of stock");
                btnOrder.setDisable(true);
            }

            txtColorCarDetail.setValue(Color.valueOf(color));
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
                while ((size = getimage.read(content)) != -1) {
                    os.write(content, 0, size);

                }
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            image = new Image("file:photo.jpg", 350, 300, true, true);
            imgCardetail.setImage(image);
        } catch (Exception e) {

        }
    }
}
