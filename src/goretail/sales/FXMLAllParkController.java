/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail.sales;

import goretail.DatabaseCheck;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author WISDOM IBANGA
 */
public class FXMLAllParkController implements Initializable {

    @FXML
    private Label total;

    @FXML
    private Label totalnum;
    
    @FXML
    private Label name;

    @FXML
    private Label time;

    static String idfromPacked = "";
    
    @FXML
    void onBackClicked(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLSales.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(FXMLAllParkController.class.getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle("Sales");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
            stage.show();
            ((Node)event.getSource()).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAllParkController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void onSalesClicked(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLSales.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(FXMLAllParkController.class.getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle("Sales");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
            stage.show();
            ((Node)event.getSource()).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAllParkController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void onInventory(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLInventory.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(FXMLSalesController.class.getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle("Inventory");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
            stage.show();
            ((Node)event.getSource()).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(FXMLSalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void signout(MouseEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLLogout.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Logout");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                    ((Node)event.getSource()).getScene().getWindow() );
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAllParkController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private ScrollPane oga;
    
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    
    /**
     * Initializes the controller class.
     */
    List<String> lista = new ArrayList<>();
    List<String> listb = new ArrayList<>();
    List<String> listc = new ArrayList<>();
    List<String> listd = new ArrayList<>();
    
    @FXML
    private VBox box;
     
    private void displayPark(String frmdb)
    {
        lista.clear();
        listb.clear();
        listc.clear();
        listd.clear();
        JSONObject obj = new JSONObject(frmdb);
        JSONArray arr = obj.getJSONArray("data");
        int totalamt = 0;
        for(int i = 0; i < arr.length(); i++){
            lista.add(arr.getJSONObject(i).getString("id"));
            listb.add(arr.getJSONObject(i).getString("amount"));
            listc.add(arr.getJSONObject(i).getString("date"));
            listd.add(arr.getJSONObject(i).getString("user"));
            totalamt += Integer.valueOf(arr.getJSONObject(i).getString("amount"));
        }
        totalnum.setText(String.valueOf(lista.size()));
        FXMLSalesController jx = new FXMLSalesController();
        total.setText(jx.amountPop(String.valueOf(totalamt)));
        FlowPane flowall = new FlowPane();
        box.getChildren().clear();
        for (int n = 1; n<lista.size() + 1; n++) {
            FlowPane pane = createPane(lista.get(n - 1));
            pane.setPrefWidth(600);
            pane.setPrefHeight(30);
            pane.setStyle("-fx-background-color: #8c8a8a;");
            FlowPane pane2 = new FlowPane();
            pane2.setPrefWidth(600);
            pane2.setPrefHeight(5);
            pane2.setStyle("-fx-background-color: #000000;");
            Label sn = new Label("    " + String.valueOf(n));
            sn.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: WHITE;");
            sn.setPrefWidth(70);
            sn.setPrefHeight(15);
            Label date = new Label(listc.get(n - 1));
            date.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: BLACK;");
            date.setPrefWidth(170);
            date.setPrefHeight(15);
            Label user = new Label(listd.get(n - 1));
            user.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: WHITE;");
            user.setPrefWidth(180);
            user.setPrefHeight(15);
            Label amt = new Label("NGN " + jx.amountPop(listb.get(n - 1)));
            amt.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: WHITE;");
            amt.setPrefWidth(140);
            amt.setPrefHeight(15);
            ImageView rem = createDelete(lista.get(n-1));
            rem.setCursor(Cursor.HAND);
            pane.getChildren().addAll(sn, date, user, amt, rem);
            flowall.getChildren().addAll(pane, pane2);
        }
        ScrollPane sp1 = new ScrollPane(flowall);
        sp1.setFitToWidth(true);
        sp1.setFitToHeight(true);
        sp1.setStyle("-fx-background-color: transparent; -fx-background-color: transparent; -fx-padding: 0; -fx-background-insets: 0; -fx-border-color: #000000; -fx-border-width:0; -fx-border-insets:0;");
        sp1.getStyleClass().add("scroll-pane");
        box.getChildren().add(sp1);
    }
    
    class DeleteHandler implements EventHandler<MouseEvent> {
        private final String number;
        DeleteHandler(String number) {
            this.number = number ;
        }
        @Override
        public void handle(MouseEvent event) {
            Platform.runLater(() -> {
                DatabaseCheck sql = new DatabaseCheck();
                sql.removeparksale(number);
                sql.removeparksaleDisplay(number);
                String goods = sql.getparksaleDisplay();
                if(goods.length() < 5)
                {
                    System.out.println("Error. No data");
                }else
                {
                    displayPark(goods);
                }
            });
        }
    }
    
    private ImageView createDeleteImage() {
        Image image = new Image(getClass().getResource("cancel.png").toExternalForm());
        ImageView cancel = new ImageView();
        cancel.setImage(image);
        return cancel;
    }
    private ImageView createDelete(String number) {
        ImageView image = createDeleteImage();
        image.setOnMouseClicked(new FXMLAllParkController.DeleteHandler(number));
        return image;
    }

    class ViewHandler implements EventHandler<MouseEvent> {
        private final String number;
        ViewHandler(String number) {
            this.number = number ;
        }
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    Platform.runLater(() -> {
                        idfromPacked = "";
                        idfromPacked = number;
                        try {
                            Stage stage = new Stage();
                            Parent root = FXMLLoader.load(getClass().getResource("FXMLSales.fxml"));
                            Scene scene = new Scene(root);
                            scene.getStylesheets().add(FXMLAllParkController.class.getResource("style.css").toExternalForm());
                            stage.setScene(scene);
                            stage.setMaximized(true);
                            stage.setTitle("Sales");
                            stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
                            stage.show();
                            ((Node)mouseEvent.getSource()).getScene().getWindow().hide();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLAllParkController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            }
        }
    }
    
    private FlowPane createPane(String number) {
        FlowPane pane = new FlowPane();
        pane.setOnMouseClicked(new FXMLAllParkController.ViewHandler(number));
        pane.setCursor(Cursor.HAND);
        return pane;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DatabaseCheck sql = new DatabaseCheck();
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                while (true) {
                    Platform.runLater(() -> {
                        Date date = new Date();
                        String dt = sdf.format(date);
                        time.setText(dt);
                    });
                    Thread.sleep(1000);
                }
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        
        Platform.runLater(() -> {
            name.setText(DatabaseCheck.getName());
        });
        
        Platform.runLater(() -> {
            String goods = sql.getparksaleDisplay();
            if(goods.length() < 5)
            {
                System.out.println("Error. No data");
            }else
            {
                displayPark(goods);
            }
        });
    }    
}
