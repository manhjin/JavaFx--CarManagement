package com.CarManagement.UI;

import com.CarManagement.CarUI.CarsTable;
import com.CarManagement.CarUI.Comments;
import com.CarManagement.CarUI.OrderCarDA;
import com.CarManagement.CategoryUI.CategoryTable;
import com.CarManagement.Comment;
import com.CarManagement.DA.DBconnection;
import com.CarManagement.DBO.OrderTable;
import com.CarManagement.DBO.User;
import com.CarManagement.DBO.checkPattern;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.Window;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class UserInformation extends User implements Initializable {
    OrderCarDA orCar = new OrderCarDA();
    checkPattern cp = new checkPattern();
    @FXML
    TextField txtUsername,txtFullname,txtEmail,txtAddress, txtPhonenumber;
    @FXML
    Label lbImage;
    @FXML
    ImageView imgUser;
    @FXML
    Label lbusername;
    private DBconnection database = new DBconnection();
    private PreparedStatement stm;
    Statement statement;
    Connection connection;
    UserDA userDA = new UserDA();
    private File file;
    private FileInputStream fis;
    private Image image;
    private FileChooser fileChooser = new FileChooser();
    Parent informationParent;
    Stage stage = new Stage();
    @FXML
    JFXButton btnUpdate,btnInfor,btnHistory;
    @FXML
    TableColumn<OrderTable,String> ordDate,ordPay,ordStatus;
    @FXML
    TableColumn<OrderTable,Integer>ordID,ordCar;
    @FXML
    TableColumn<OrderTable,Double>ordPrice;
    @FXML
    TableView
            OrderManTableView;
    @FXML
    AnchorPane pnHistory,pnInfor;
    Comment comment = new Comment();


    @FXML
    public void actionAddComment(ActionEvent actionEvent) throws SQLException, IOException {
        OrderTable getSelectedRow = (OrderTable) OrderManTableView.getSelectionModel().getSelectedItem();
        OrderManTableView.setItems(orCar.getOrderInfor("Select distinct o.id,o.cusid,o.orderdate,od.pay,od.price,od.carid,o.status from" +
                " carmanagement.order as o,orderdetail as od where o.id = od.orderid and o.cusid = '"+getUserID()+"' group by o.id"));
        comment.setCarId(getSelectedRow.getOrdCar());
        comment.setUserID(getSelectedRow.getOrdCus());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Comment.FXML"));
        Parent CommentScene = fxmlLoader.load();
        stage.setTitle("Submit Comment");
        stage.setScene(new Scene(CommentScene));
        stage.showAndWait();

    }


    @FXML
    public void btnActionHandler(ActionEvent actionEvent){
        if (actionEvent.getSource() == btnInfor){
            pnInfor.toFront();
        }else if (actionEvent.getSource() == btnHistory){
            pnHistory.toFront();
        }
    }
    @FXML
    public void btnHomeHandle(ActionEvent actionEvent){


        try {
            informationParent = FXMLLoader.load(getClass().getResource("MainCustomer.FXML"));
            Scene informationScene = new Scene(informationParent);
            Stage informationStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            informationStage.hide();
            informationStage.setScene(informationScene);
            informationStage.setTitle("Main Menu");
            informationStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void handlePane() throws FileNotFoundException {



    }

    @FXML
    public void btnBrowseimage(ActionEvent actionEvent) throws FileNotFoundException {


        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("File Image","*.png", "*.jpg","*.gif"));
        file = fileChooser.showOpenDialog(stage);

        if (file != null){
            System.out.println(file.getAbsolutePath());
            image = new Image(file.toURI().toString(),300,350,true,true);
            imgUser.setImage(image);

        }

    }
    @FXML
    public void txtField(){

        if (!cp.isValidPhone(txtPhonenumber.getText()) || !cp.isValidEmail(txtEmail.getText())){
            btnUpdate.setDisable(true);
        }else btnUpdate.setDisable(false);
    }
    @FXML
    public void btnUpdateHandle(ActionEvent actionEvent) throws FileNotFoundException {
        try {
            fis = new FileInputStream(file);
            Connection connection;
            connection = database.getConnection();
            if (file == null){
                stm = connection.prepareStatement("UPDATE `carmanagement`.`user` SET `email` = ?, `fullname` = ?,`address` =?,`phone` = ? WHERE `username`=?");
                stm.setString(1,txtEmail.getText());
                stm.setString(2,txtFullname.getText());
                stm.setString(3,txtAddress.getText());
                stm.setString(4,txtPhonenumber.getText());
                stm.setString(5,getUserName());
                stm.executeUpdate();
            }else {
            stm = connection.prepareStatement("UPDATE `carmanagement`.`user` SET `uimage` = ? ,`email` = ?, `fullname` = ?,`address` =?,`phone` = ? WHERE `username`=?");
            stm.setBinaryStream(1,(InputStream)fis, (int)file.length());
            stm.setString(2,txtEmail.getText());
            stm.setString(3,txtFullname.getText());
            stm.setString(4,txtAddress.getText());
            stm.setString(5,txtPhonenumber.getText());
            stm.setString(6,getUserName());
            stm.executeUpdate();}
        } catch (Exception e) {
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Set History Information
        ordID.setCellValueFactory(new PropertyValueFactory<OrderTable,Integer>("ordid"));
        ordCar.setCellValueFactory(new PropertyValueFactory<OrderTable,Integer>("ordCar"));
        ordDate.setCellValueFactory(new PropertyValueFactory<OrderTable,String>("ordDate"));
        ordPay.setCellValueFactory(new PropertyValueFactory<OrderTable,String>("ordPay"));
        ordPrice.setCellValueFactory(new PropertyValueFactory<OrderTable,Double>("ordPrice"));
        ordStatus.setCellValueFactory(new PropertyValueFactory<OrderTable,String>("ordStatus"));
        try {
            OrderManTableView.setItems(orCar.getOrderInfor("Select distinct o.id,o.cusid,o.orderdate,od.pay,od.price,od.carid,o.status from carmanagement.order as o,orderdetail as od where o.id = od.orderid and o.cusid = '"+getUserID()+"' group by o.id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //Set User Information
        lbusername.setText(getUserName());
        System.out.println(getUserName());
        txtFullname.setText(getFullName());
        txtEmail.setText(getEmail());
        txtAddress.setText(getAddress());
        txtPhonenumber.setText(getPhone());
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File("photo.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] content = new byte[1024];
        int size = 0;
        try {
            while ((size= getImage().read(content)) != -1){
                os.write(content,0,size);

            }
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        image = new Image("file:photo.jpg",350,300,true,true);
        imgUser.setImage(image);


    }
}
