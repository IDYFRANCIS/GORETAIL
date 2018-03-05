/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.JSONObject;

/**
 *
 * @author WISDOM IBANGA
 */
public class FXMLHOMEController implements Initializable {
    
    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private Label error;

    @FXML
    private JFXButton login;

    @FXML
    private Hyperlink forgot;

    @FXML
    private Label loading;

    static String user = "";
    static String pass = "";
    
    @FXML
    void onForgotPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXMLForgot.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Reset");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
        stage.show();
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    public static String USEuserid = "";
    public static String USEname = "";
    public static String USEaddress = "";
    public static Long USEtime = null;
    
    @FXML
    void onLoginPressed(ActionEvent event) throws IOException {
        login.setDisable(true);
        user = username.getText();
        pass = password.getText();
        if(user.length() < 1)
        {
            login.setDisable(false);
            username.requestFocus();
        }else if(pass.length() < 1)
        {
            login.setDisable(false);
            password.requestFocus();
        }else
        {
            loading.setVisible(true);
            error.setVisible(false);
            Task task = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    Platform.runLater(() -> {
                        if(Post.netIsAvailable())
                        {
                            Platform.runLater(() -> {
                                String response = Post.loginToken(user, pass);
                                if(response.equals("Invalid"))
                                {
                                    login.setDisable(false);
                                    error.setVisible(true);
                                    loading.setVisible(false);
                                    username.requestFocus();
                                }else
                                {
                                    JSONObject j = new JSONObject(response);
                                    GORETAIL.access_token = j.getString("access_token");
                                    GORETAIL.refresh_token = j.getString("refresh_token");
                                    String res = Post.login(GORETAIL.access_token);
                                    JSONObject json = new JSONObject(res);
                                    JSONObject jsondata = json.getJSONObject("data");
                                    String userid = jsondata.getString("userId");
                                    USEuserid = userid;
                                    String businessid = jsondata.getString("businessId");
                                    String name = user;
                                    String businessname = jsondata.getString("businessName");
                                    USEname = businessname;
                                    String businesscode = jsondata.getString("businessCode");
                                    String phone = jsondata.getString("phone");
                                    String email = jsondata.getString("email");
                                    String contactname = jsondata.getString("contactName");
                                    String address = jsondata.getString("address");
                                    USEaddress = address;
                                    String datejoined =  String.valueOf(jsondata.getDouble("dateJoined"));
                                    String dateupdated = String.valueOf(jsondata.getDouble("dateJoined"));
                                    USEtime = jsondata.getLong("time");
                                    DatabaseCheck sql = new DatabaseCheck();
                                    sql.droptable("login");
                                    if(sql.createlogin())
                                    {
                                        if(sql.insertlogin(
                                            userid, businessid, name,
                                            businessname, businesscode, phone, email,
                                            contactname, address, datejoined, dateupdated))
                                        {
                                            if(CheckSub.finalCheck() == false)
                                            {
                                                Stage stage = new Stage();
                                                Parent root;
                                                try {
                                                    root = FXMLLoader.load(getClass().getResource("FXMLSubscription.fxml"));
                                                    Scene scene = new Scene(root);
                                                    stage.setScene(scene);
                                                    stage.setMaximized(true);
                                                    stage.setTitle("Subscribe");
                                                    stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
                                                    stage.show();
                                                    ((Node)event.getSource()).getScene().getWindow().hide();
                                                } catch (IOException ex) {
                                                    Logger.getLogger(FXMLHOMEController.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            }else
                                            {
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
                                        }else
                                        {
                                            login.setDisable(false);
                                            error.setVisible(true);
                                            loading.setVisible(false);
                                            username.requestFocus();
                                        }
                                    }else
                                    {
                                        if(sql.droptable("login"))
                                        {
                                            if(sql.createlogin())
                                            {
                                                if(sql.insertlogin(
                                                    userid, businessid, name,
                                                    businessname, businesscode, phone, email,
                                                    contactname, address, datejoined, dateupdated))
                                                {
                                                    if(CheckSub.finalCheck() == false)
                                                    {
                                                        Stage stage = new Stage();
                                                        Parent root;
                                                        try {
                                                            root = FXMLLoader.load(getClass().getResource("FXMLSubscription.fxml"));
                                                            Scene scene = new Scene(root);
                                                            stage.setScene(scene);
                                                            stage.setMaximized(true);
                                                            stage.setTitle("Subscribe");
                                                            stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
                                                            stage.show();
                                                            ((Node)event.getSource()).getScene().getWindow().hide();
                                                        } catch (IOException ex) {
                                                            Logger.getLogger(FXMLHOMEController.class.getName()).log(Level.SEVERE, null, ex);
                                                        }
                                                    }else
                                                    {
                                                        Stage stage = new Stage();
                                                        Parent root;
                                                        try {
                                                            root = FXMLLoader.load(getClass().getResource("sales/FXMLSales.fxml"));
                                                            Scene scene = new Scene(root);
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
                                                }else
                                                {
                                                    login.setDisable(false);
                                                    error.setVisible(true);
                                                    loading.setVisible(false);
                                                    username.requestFocus();
                                                }
                                            }else
                                            {
                                                login.setDisable(false);
                                                error.setVisible(true);
                                                loading.setVisible(false);
                                                username.requestFocus();
                                            }
                                        }else
                                        {
                                            login.setDisable(false);
                                            error.setVisible(true);
                                            loading.setVisible(false);
                                            username.requestFocus();
                                        }
                                    }
                                }
                            });
                        }else
                        {
                            login.setDisable(false);
                            error.setVisible(true);
                            loading.setVisible(false);
                            username.requestFocus();
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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
