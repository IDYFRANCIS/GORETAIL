/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author WISDOM IBANGA
 */
public class FXMLSubscriptionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private JFXTextField number;

    @FXML
    private JFXButton subscribe;

    @FXML
    private Label loading;
    
     @FXML
    private ProgressIndicator indicatorpro;

    @FXML
    private Label error;

    @FXML
    void onsubscribePressed(ActionEvent event) {
        subscribe.setVisible(false);
        error.setVisible(false);
        ErrorHelper.showLoadingIndicator(indicatorpro);
        loading.setVisible(true);
        String num = number.getText();
        if(num.length() < 1|| num.isEmpty())
        {
             loading.setVisible(false);
              number.requestFocus();
              subscribe.setVisible(true);
        }else
        {
            Task task = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    Platform.runLater(() -> {
                    if(Post.netIsAvailable())
                    {
                        Platform.runLater(() -> {
                            String response = Post.subscribe(num);
                            if(response.equals("Invalid"))
                            {
                                number.requestFocus();
                                loading.setVisible(false);
                                error.setText(response + " Voucher number");
                                subscribe.setVisible(true);
                                error.setVisible(true);
                            }else
                            {
                                Path path = Paths.get("C:\\sys\\windows.ini");
                                File file = new File("C:\\sys\\windows.ini");
                                if(file.exists())
                                    file.delete();
                                File file2 = new File("C:\\sys");
                                    file2.delete();
                                File file3 = new File("C:\\sys");
                                file3.mkdir();
                                try {
                                    Files.createFile(path);
                                    Files.write(path, (Base64.getEncoder().encodeToString(response.getBytes("utf-8"))).getBytes());
                                } catch (IOException ex) {
                                    Logger.getLogger(FXMLSubscriptionController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Stage stage = new Stage();
                                Parent root;
                                try {
                                    root = FXMLLoader.load(getClass().getResource("sales/FXMLSales.fxml"));
                                    Scene scene = new Scene(root);
                                    scene.getStylesheets().add(FXMLHOMEController.class.getResource("style.css").toExternalForm());
                                    stage.setScene(scene);
                                    stage.setMaximized(true);
                                    stage.setTitle("Sales");
                                    stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
                                    stage.show();
                                    ((Node)event.getSource()).getScene().getWindow().hide();
                                } catch (IOException ex) {
                                    Logger.getLogger(FXMLHOMEController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                    }else
                    {
                        number.requestFocus();
                        error.setVisible(true);
                        loading.setVisible(false);
                         subscribe.setVisible(true);
                    }
                });
                    return null;
                }
            };
            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();
        }
    }
    @FXML
    private Label details;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        File file = new File("C:\\sys\\windows.ini");
        if(file.exists())
            details.setText("Voucher Expire. Subscribe");
        else
            details.setText("New user. Please Subscribe");
    }    
    
}
