package com.CarManagement.socketConnection;

import com.mysql.fabric.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ChatController {
    @FXML
    TextField txtInput;
    @FXML
    TextArea txtDisplay;
    @FXML
    Button btnConnect;
    public static boolean isServer = false;



    private NetworkConnection networkConnection = isServer ? createSupporter() : createUserChat();
    @FXML
    public void btnConnectHandle(ActionEvent actionEvent){
        networkConnection.startConnection();
        if (isServer == true) {
            btnConnect.setText("Connecting to customer");
        }else btnConnect.setText("Connecting to server");
    }

    @FXML
    public void sendMessageHandle() throws Exception {
            String message = isServer ? "Server : " : "Client : ";
            message += txtInput.getText();
            txtInput.clear();
            txtDisplay.appendText(message+ "\n");
            try {
                networkConnection.send(message);
            } catch (Exception e) {
                txtDisplay.appendText("Failed to send \n");

        }
        }


    public Supporter createSupporter(){

        return new Supporter(55555, data ->{
            Platform.runLater(()-> {
                txtDisplay.appendText(data.toString()+ "\n");
                System.out.println("support");
            });
        });
    }
    private UserChat createUserChat(){
        return  new UserChat("127.0.0.1",55555,data ->{
            Platform.runLater(()-> {
                txtDisplay.appendText(data.toString()+ "\n");
                System.out.println("userchat");
            });

        });
    }
}
