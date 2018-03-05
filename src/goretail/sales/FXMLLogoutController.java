/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail.sales;

import goretail.DatabaseCheck;
import goretail.Post;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

/**
 * FXML Controller class
 *
 * @author WISDOM IBANGA
 */
public class FXMLLogoutController implements Initializable {

    @FXML
    void onCancelClicked(ActionEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void onYesClicked(ActionEvent event) {
        DatabaseCheck sql = new DatabaseCheck();
        if(sql.droptable("login"))
        {
            sql.droptable("goods");
            sql.droptable("pregoods");
            Platform.exit();
        }else
        {
            ((Node)event.getSource()).getScene().getWindow().hide();
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
