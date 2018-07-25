/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail.sales;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import goretail.DatabaseCheck;
import goretail.Post;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
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
public class FXMLInventoryController implements Initializable {

     @FXML
    private ScrollPane displayscroll;

    @FXML
    private VBox adddisplay;

    @FXML
    private JFXButton receive;

    @FXML
    private Label datetime;

    @FXML
    private Label namelog;

    @FXML
    private ScrollPane oga;

    @FXML
    private Label info;

    @FXML
    void onReceiveClick(ActionEvent event) {
        info.setVisible(true);
        info.setText("Sync.....");
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLSupplies.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Supplies");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                    ((Node)event.getSource()).getScene().getWindow() );
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLInventoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void onSaleClicked(ActionEvent event) {
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
            Logger.getLogger(FXMLInventoryController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FXMLInventoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    
    @FXML
    void onInventoryClicked(ActionEvent event) {
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
    
    List<String> lista = new ArrayList<>();
    List<String> listb = new ArrayList<>();
    List<String> listc = new ArrayList<>();
    List<String> listd = new ArrayList<>();
    List<String> liste = new ArrayList<>();
    List<String> listf = new ArrayList<>();
    List<String> listg = new ArrayList<>();
    List<String> listh = new ArrayList<>();
    
    class DeleteHandler implements EventHandler<MouseEvent> {
        private final String number;
        DeleteHandler(String number) {
            this.number = number ;
        }
        @Override
        public void handle(MouseEvent event) {
            Platform.runLater(() -> {
                int index = lista.indexOf(number);
                lista.remove(index);listb.remove(index);listc.remove(index);listd.remove(index);
                liste.remove(index);listf.remove(index);listg.remove(index);listh.remove(index);
                FlowPane flowall = new FlowPane();
                box.getChildren().clear();
                for (int n = 1; n<lista.size() + 1; n++) {
                    FlowPane pane = createPane(lista.get(n - 1));
                    pane.setPrefWidth(600);
                    pane.setPrefHeight(30);
                    pane.setStyle("-fx-background-color: #8c8a8a;");
                    pane.setStyle("-fx-padding: 10 0 0 0;");
                    
                    FlowPane pane2 = new FlowPane();
                    pane2.setPrefWidth(600);
                    pane2.setPrefHeight(5);
                    pane2.setStyle("-fx-background-color: #000000;");
                    pane2.setStyle("-fx-padding: 10 0 0 0;");
                    
                    Label sn = new Label("    " + String.valueOf(n));
                    sn.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: WHITE;");
                    sn.setStyle("-fx-padding: 10 0 0 0;");
                    sn.setPrefWidth(70);
                    sn.setPrefHeight(15);
                    Label date = new Label(listd.get(n - 1));
                    date.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: BLACK;");
                    date.setStyle("-fx-padding: 10 0 0 0;");
                    date.setPrefWidth(250);
                    date.setPrefHeight(15);
                    Label user = new Label(liste.get(n - 1));
                    user.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: WHITE;");
                    user.setStyle("-fx-padding: 10 0 0 0;");
                    user.setPrefWidth(180);
                    user.setPrefHeight(15);
                    Label amt = new Label(listb.get(n - 1));
                    amt.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: WHITE;");
                    amt.setStyle("-fx-padding: 10 0 0 0;");
                    amt.setPrefWidth(60);
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
        image.setOnMouseClicked(new FXMLInventoryController.DeleteHandler(number));
        return image;
    }

    static String orderid = "";
    static String supplierid = "";
    static String quan = "";
    
    private FlowPane createFlowPane() {
        FlowPane flow = new FlowPane();
        return flow;
    }
    
    class BackHandler implements EventHandler<MouseEvent> {
        private final String number;
        BackHandler(String number) {
            this.number = number ;
        }
        @Override
        public void handle(MouseEvent event) {
            int index = lista.indexOf(number);
            try
            {
                if((Integer.valueOf(qty.getText()) + 1) <= 0)
                {
                    displayOrder(index, number, Integer.valueOf(listb.get(index)), listc.get(index));
                }else
                {
                    try
                    {
                        String val = qty.getText();
                        displayOrder(index, number, Integer.valueOf(val) - 1, listc.get(index));
                    }catch(Exception e)
                    {
                        displayOrder(index, number, 1, listc.get(index));
                    }
                }
            }catch(Exception e)
            {
                displayOrder(index, number, 1, listc.get(index));
            }
        }
    }
    class NextHandler implements EventHandler<MouseEvent> {
        private final String number;
        NextHandler(String number) {
            this.number = number ;
        }
        @Override
        public void handle(MouseEvent event) {
            int index = lista.indexOf(number);
            try
            {
                if((Integer.valueOf(qty.getText()) + 1) > Integer.valueOf(listb.get(index)))
                {
                    displayOrder(index, number, Integer.valueOf(listb.get(index)), listc.get(index));
                }else
                {
                    try
                    {
                        String val = qty.getText();
                        displayOrder(index, number, Integer.valueOf(val) + 1, listc.get(index));
                    }catch(NumberFormatException e)
                    {
                        displayOrder(index, number, 1, listc.get(index));
                    }
                }
            }catch(NumberFormatException e)
            {
                displayOrder(index, number, 1, listc.get(index));
            }
        }
    }
    
    private ImageView createBackImage() {
        Image image = new Image(getClass().getResource("previous_blue.png").toExternalForm());
        ImageView cancel = new ImageView();
        cancel.setImage(image);
        return cancel;
    }
    private ImageView createNextImage() {
        Image image = new Image(getClass().getResource("next_blue.png").toExternalForm());
        ImageView cancel = new ImageView();
        cancel.setImage(image);
        return cancel;
    }
    
    private ImageView createBack(String number) {
        ImageView image = createBackImage();
        image.setOnMouseClicked(new FXMLInventoryController.BackHandler(number));
        return image;
    }
    private ImageView createNext(String number) {
        ImageView image = createNextImage();
        image.setOnMouseClicked(new FXMLInventoryController.NextHandler(number));
        return image;
    }
    
    class MouseExitEdit implements EventHandler<MouseEvent> {
        private final String number;
        MouseExitEdit(String number) {
            this.number = number ;
        }
        @Override
        public void handle(MouseEvent event) {
            JFXTextField field = (JFXTextField)event.getSource();
            int index = lista.indexOf(number);
            if(field.getText() == null)
            {
                displayOrder(index, number, 1, listc.get(index));
            }else
            {
                try
                {
                    if(Integer.valueOf(field.getText()) > Integer.valueOf(listb.get(index)))
                    {
                        displayOrder(index, number, Integer.valueOf(listb.get(index)), listc.get(index));
                    }else
                    {
                        try
                        {
                            String val = field.getText();
                            displayOrder(index, number, Integer.valueOf(val), listc.get(index));
                        }catch(Exception e)
                        {
                            displayOrder(index, number, 1, listc.get(index));
                        }
                    }
                }catch(Exception e)
                {
                    displayOrder(index, number, 1, listc.get(index));
                }
            }
        }
    }
    
    class EditQuantityHandler implements EventHandler<ActionEvent> {
        private final String number ;
        EditQuantityHandler(String number) {
            this.number = number ;
        }
        @Override
        public void handle(ActionEvent event) {
            
        }
    }  
    
    public String amountPop(String amount)
    {
        StringBuilder bd = new StringBuilder();
        int size = amount.length();
        switch(size)
        {
            case 1:
            case 2:
            case 3:
                bd.append(amount);
                break;
            case 4:
                bd.append(amount.charAt(0));
                bd.append(",");
                bd.append(amount.charAt(1));
                bd.append(amount.charAt(2));
                bd.append(amount.charAt(3));
                break;
            case 5:
                bd.append(amount.charAt(0));
                bd.append(amount.charAt(1));
                bd.append(",");
                bd.append(amount.charAt(2));
                bd.append(amount.charAt(3));
                bd.append(amount.charAt(4));
                break;
            case 6:
                bd.append(amount.charAt(0));
                bd.append(amount.charAt(1));
                bd.append(amount.charAt(2));
                bd.append(",");
                bd.append(amount.charAt(3));
                bd.append(amount.charAt(4));
                bd.append(amount.charAt(5));
                break;
            case 7:
                bd.append(amount.charAt(0));
                bd.append(",");
                bd.append(amount.charAt(1));
                bd.append(amount.charAt(2));
                bd.append(amount.charAt(3));
                bd.append(",");
                bd.append(amount.charAt(4));
                bd.append(amount.charAt(5));
                bd.append(amount.charAt(6));
                break;
            case 8:
                bd.append(amount.charAt(0));
                bd.append(amount.charAt(1));
                bd.append(",");
                bd.append(amount.charAt(2));
                bd.append(amount.charAt(3));
                bd.append(amount.charAt(4));
                bd.append(",");
                bd.append(amount.charAt(5));
                bd.append(amount.charAt(6));
                bd.append(amount.charAt(7));
                break;
            default:
                bd.append(amount);
                break;
        }
        bd.append(".00");
        return bd.toString();
    }
    
    private ImageView createCancelImage() {
        Image image = new Image(getClass().getResource("cancel.png").toExternalForm());
        ImageView cancel = new ImageView();
        cancel.setImage(image);
        return cancel;
    }
    
    class CloseHandler implements EventHandler<MouseEvent> {
        private final String number;
        CloseHandler(String number) {
            this.number = number ;
        }
        @Override
        public void handle(MouseEvent event) {
            adddisplay.getChildren().clear();
            receive.setVisible(false);
        }
    }
    
    private ImageView createCancel(String number) {
        ImageView image = createCancelImage();
        image.setOnMouseClicked(new FXMLInventoryController.CloseHandler(number));
        return image;
    }
    JFXTextField qty;
    
    private void displayOrder(int index, String number, int quantity, String supplier)
    {
        orderid = number;
        supplierid = supplier;
        quan = String.valueOf(quantity);
        FlowPane flowall = createFlowPane();
        adddisplay.getChildren().clear();
        FlowPane flow = createFlowPane();
        FlowPane flow2 = createFlowPane();
        flow.getStyleClass().add("color-back");
        flow2.getStyleClass().add("color-back");
        flowall.getStyleClass().add("color-back");
        BorderPane bPane = new BorderPane(); 
        ImageView dec = createBack(number);
        dec.setCursor(Cursor.HAND); 
        qty = new JFXTextField();
        qty.setOnMouseExited(new FXMLInventoryController.MouseExitEdit(number));
        qty.setPrefWidth(50);
        qty.setId(number);
        qty.setText(String.valueOf(quantity));
        qty.setOnAction(new FXMLInventoryController.EditQuantityHandler(listb.get(index)));
        ImageView inc = createNext(number);
        inc.setCursor(Cursor.HAND); 
        flow.getChildren().addAll(dec, qty, inc);
        StackPane left = new StackPane();
        left.setPrefWidth(150);
        left.getChildren().add(flow);
        bPane.setLeft(left);
        Label name2 = new Label(listg.get(index));
        name2.getStyleClass().add("text-raised");
        Label amt = new Label("₦" + amountPop(listh.get(index)));
        amt.getStyleClass().add("text-raised");
        VBox b = new VBox();
        b.getStyleClass().add("color-back");
        b.getChildren().addAll(name2, amt);
        StackPane center = new StackPane();
        center.setPrefWidth(180);
        center.getChildren().add(b);
        bPane.setCenter(center);
        ImageView img = createCancel(number);
        img.setCursor(Cursor.HAND); 
        bPane.setRight(img);
        StackPane right = new StackPane();
        right.setPrefWidth(10);
        right.getChildren().add(img);
        bPane.setRight(right);
        flowall.getChildren().add(bPane);
        ScrollPane sp1 = new ScrollPane(flowall);
        sp1.setFitToWidth(true);
        sp1.setFitToHeight(true);
        sp1.setStyle("-fx-background-color: transparent; -fx-background-color: transparent; -fx-padding: 0; -fx-background-insets: 0; -fx-border-color: #000000; -fx-border-width:0; -fx-border-insets:0;");
        sp1.getStyleClass().add("scroll-pane");
        displayscroll.setStyle("-fx-background-color: #000000; -fx-background-color: #000000; -fx-padding: 0; -fx-background-insets: 0; -fx-border-color: #000000; -fx-border-width:0; -fx-border-insets:0;");
        adddisplay.getChildren().add(sp1);
        receive.setVisible(true);
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
                        int index = lista.indexOf(number);
                        displayOrder(index, number, Integer.valueOf(listb.get(index)), listc.get(index));
                    });
                }
            }
        }
    }
    
    private FlowPane createPane(String number) {
        FlowPane pane = new FlowPane();
        pane.setOnMouseClicked(new FXMLInventoryController.ViewHandler(number));
        pane.setCursor(Cursor.HAND);
        return pane;
    }
    
    @FXML
    private VBox box;
    
    String dateParse(String time)
    {
        String yr = time.substring(21, time.length());
        String date = time.substring(0, 11);
        String tm = time.substring(11, 20);
        StringBuilder bd = new StringBuilder();
        bd.append(date);bd.append(yr);bd.append("  ");bd.append(tm);
        return bd.toString();
    }
    
    private void displayOrders(String frmdb)
    {
        lista.clear();
        listb.clear();
        listc.clear();
        listd.clear();
        liste.clear();
        listf.clear();
        listg.clear();
        listh.clear();
        JSONObject obj = new JSONObject(frmdb);
        JSONArray arr = obj.getJSONArray("data");
        JSONObject sup;
        JSONObject pro;
        for(int i = 0; i < arr.length(); i++){
            if(arr.getJSONObject(i).getString("status").equals("PENDING"))
            {
                lista.add(arr.getJSONObject(i).getString("orderId"));
                listb.add(String.valueOf(arr.getJSONObject(i).getInt("quantity")));
                pro = new JSONObject();
                pro = arr.getJSONObject(i).getJSONObject("product");
                sup = new JSONObject();
                sup = arr.getJSONObject(i).getJSONObject("supplier");
                listc.add(sup.getString("supplierId"));
                liste.add(sup.getString("name"));
                listf.add(sup.getString("phone"));
                listg.add(pro.getString("name"));
                listh.add(String.valueOf(pro.getInt("salePrice")));
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(arr.getJSONObject(i).getLong("date"));
                String tmp = String.valueOf(c.getTime());
                listd.add(dateParse(tmp.replace("WAT", "")));
            }
        }
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
            Label date = new Label(listd.get(n - 1));
            date.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: BLACK;");
            date.setPrefWidth(250);
            date.setPrefHeight(15);
            Label user = new Label(liste.get(n - 1));
            user.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: WHITE;");
            user.setPrefWidth(180);
            user.setPrefHeight(15);
            Label amt = new Label(listb.get(n - 1));
            amt.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: WHITE;");
            amt.setPrefWidth(60);
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
    
    @FXML
    void onPaneEntered(MouseEvent event) {
        if(FXMLInventoryController.supplie.length() > 10)
        {
            info.setVisible(false);
            adddisplay.getChildren().clear();
            receive.setVisible(false);
            FXMLInventoryController.supplie = "";
            String orders = Post.getOrders();
            //display order
            System.out.print(orders);
            if(orders.length() < 5)
            {
                System.out.println("Error. No data");
            }else
            {
                displayOrders(orders);
                info.setVisible(false);
            }
        }
    }
    
    static String supplie = "";
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                while (true) {
                    Platform.runLater(() -> {
                        Date date = new Date();
                        String dt = sdf.format(date);
                        datetime.setText(dt);
                    });
                    Thread.sleep(1000);
                }
            }
        };
        
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        
        Platform.runLater(() -> {
            namelog.setText(DatabaseCheck.getName());
        });
        
        Platform.runLater(() -> {
            String orders = Post.getOrders();
            if(orders.length() < 5)
            {
                System.out.println("Error. No data");
            }else
            {
                displayOrders(orders);
                info.setVisible(false);
            }
        });
    }    
    
}
