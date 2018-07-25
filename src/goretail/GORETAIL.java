/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author WISDOM IBANGA
 */
public class GORETAIL extends Application {
  
    public static String baseurl = "http://5.77.43.22:8093/goretail/api";
    public static String access_token = "";
    public static String refresh_token = "";
    
    @Override
    public void start(Stage stage) throws Exception {
        DatabaseCheck sql = new DatabaseCheck();
        sql.droptable("goods");
       
        Parent root = FXMLLoader.load(getClass().getResource("FXMLHOME.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Login");
        scene.getStylesheets().add(GORETAIL.class.getResource("style.css").toExternalForm());
        stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
