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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author WISDOM IBANGA
 */
public class FXMLForgotController implements Initializable {
    
      @FXML
    private AnchorPane indicator;

    @FXML
    private JFXTextField number;

    @FXML
    private JFXButton reset;

    @FXML
    private Label loading;

    @FXML
    private ProgressIndicator indicator1;

    @FXML
    private Label error;

    @FXML
    private Hyperlink gologin;



    @FXML
    void backhome(ActionEvent event) throws IOException {
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

    @FXML
    void clickhome(ActionEvent event) throws IOException {
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
   
    @FXML
    void onResetPressed(ActionEvent event) {
        reset.setVisible(false);
        String num = number.getText();
        error.setVisible(false);
         ErrorHelper.showLoadingIndicator(indicator1);
        loading.setVisible(true);
         final int TIMEOUT=100000;
        if(num.length() < 1|| num.isEmpty()){
           // ErrorHelper.showJFXTextFieldError(number, " This field Can not be empty ");
            error.setText("Empty value not allwoed");  
            error.setVisible(true);
            loading.setVisible(false);
            number.requestFocus();
            reset.setVisible(true);
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
                            error.setText("Not a valid Email address!!");
                            error.setVisible(true);
                             reset.setVisible(true);
                        }else
                        {   reset.setDisable(false);
                            indicator.setDisable(false);
                            JSONObject obj = new JSONObject(response);
                            String data = obj.getString("data");
                            System.out.println(":Json object:   "+data);
                            if(data.contains("Account not found"))
                            {
                                number.requestFocus();
                                loading.setVisible(false);
                                reset.setVisible(true);
                                error.setText(" Account not found");
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


//    @FXML
//    void onResetPressed(ActionEvent event) throws IOException {
//        error.setVisible(false);
//        loading.setVisible(true);
//        String num = number.getText();
//        if(num.length() < 1)
//        {
//            loading.setVisible(false);
//            number.requestFocus();
//        }else
//        {
//            Platform.runLater(() -> {
//                if(Post.netIsAvailable())
//                {
//                    Platform.runLater(() -> {
//                        String response = Post.forgot(num);
//                        if(response.equals("Invalid"))
//                        {
//                            number.requestFocus();
//                            loading.setVisible(false);
//                            error.setVisible(true);
//                        }else
//                        {
//                            JSONObject obj = new JSONObject(response);
//                            String data = obj.getString("data");
//                            if(data.contains("Account not found"))
//                            {
//                                number.requestFocus();
//                                loading.setVisible(false);
//                                error.setVisible(true);
//                            }else
//                            {
//                                Stage stage = new Stage();
//                                Parent root;
//                                try {
//                                    root = FXMLLoader.load(getClass().getResource("FXMLForgotSuccess.fxml"));
//                                    Scene scene = new Scene(root);
//                                    stage.setScene(scene);
//                                    stage.setMaximized(true);
//                                    stage.setTitle("Success");
//                                    stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
//                                    stage.show();
//                                    ((Node)event.getSource()).getScene().getWindow().hide();
//                                } catch (IOException ex) {
//                                    Logger.getLogger(FXMLForgotController.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//                            }
//                        }
//                    });
//                }else
//                {
//                    number.requestFocus();
//                    error.setVisible(true);
//                    loading.setVisible(false);
//                }
//            });
//        }
//    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
