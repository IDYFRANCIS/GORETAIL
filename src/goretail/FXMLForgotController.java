/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author WISDOM IBANGA
 */
public class FXMLForgotController implements Initializable {

    @FXML
    private JFXTextField number;

    @FXML
    private Label error;

    @FXML
    private JFXButton reset;

    @FXML
    private Label loading;

    @FXML
    void onResetPressed(ActionEvent event) throws IOException {
        error.setVisible(false);
        loading.setVisible(true);
        String num = number.getText();
        if(num.length() < 1)
        {
            loading.setVisible(false);
            number.requestFocus();
        }else
        {
            Platform.runLater(() -> {
                if(Post.netIsAvailable())
                {
                    Platform.runLater(() -> {
                        String response = Post.forgot(num);
                        if(response.equals("Invalid"))
                        {
                            number.requestFocus();
                            loading.setVisible(false);
                            error.setVisible(true);
                        }else
                        {
                            JSONObject obj = new JSONObject(response);
                            String data = obj.getString("data");
                            if(data.contains("Account not found"))
                            {
                                number.requestFocus();
                                loading.setVisible(false);
                                error.setVisible(true);
                            }else
                            {
                                Stage stage = new Stage();
                                Parent root;
                                try {
                                    root = FXMLLoader.load(getClass().getResource("FXMLForgotSuccess.fxml"));
                                    Scene scene = new Scene(root);
                                    stage.setScene(scene);
                                    stage.setMaximized(true);
                                    stage.setTitle("Success");
                                    stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
                                    stage.show();
                                    ((Node)event.getSource()).getScene().getWindow().hide();
                                } catch (IOException ex) {
                                    Logger.getLogger(FXMLForgotController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    });
                }else
                {
                    number.requestFocus();
                    error.setVisible(true);
                    loading.setVisible(false);
                }
            });
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
