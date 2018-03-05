/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author WISDOM IBANGA
 */
public class FXMLForgotSuccessController implements Initializable {

    @FXML
    private JFXButton ret;

    @FXML
    void onReturnPressed(ActionEvent event) throws IOException {
        
        System.out.println("Okay sir 2");
            
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXMLHOME.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Login");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
        stage.show();
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
