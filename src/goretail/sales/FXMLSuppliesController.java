/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail.sales;

import com.jfoenix.controls.JFXPasswordField;
import goretail.Post;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author WISDOM IBANGA
 */
public class FXMLSuppliesController implements Initializable {

    @FXML
    private JFXPasswordField password;

    @FXML
    void onCancelClicked(ActionEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private Label info;
    
    @FXML
    void onYesClicked(ActionEvent event) {
        info.setVisible(false);
        if(password.getText().equals(Post.pass))
        {
            FXMLInventoryController.supplie = Post.postSupplies(FXMLInventoryController.orderid, FXMLInventoryController.quan, FXMLInventoryController.supplierid);
            ((Node)event.getSource()).getScene().getWindow().hide();
        }else
        {
            password.requestFocus();
            info.setVisible(true);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        password.requestFocus();
    }    
    
}
