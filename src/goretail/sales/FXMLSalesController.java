/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail.sales;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import goretail.DatabaseCheck;
import goretail.GORETAIL;
import goretail.Post;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author WISDOM IBANGA
 */
public class FXMLSalesController implements Initializable {

    static String closeRef = "";
    
    class CloseHandler implements EventHandler<MouseEvent> {
        private final String number;
        CloseHandler(String number) {
            this.number = number ;
        }
        @Override
        public void handle(MouseEvent event) {
            try {
                closeRef = number;
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("FXMLCancel.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Delete");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
                stage.resizableProperty().setValue(Boolean.FALSE);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(
                        ((Node)event.getSource()).getScene().getWindow() );
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLSalesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private String preDis(String init, int count)
    {
        StringBuilder bd = new StringBuilder();
        switch(count)
        {
            case 1:
                bd.append(init);
                bd.append("            ");
                break;
            case 2:
                bd.append(init);
                bd.append("           ");
                break;
            case 3:
                bd.append(init);
                bd.append("          ");
                break;
            case 4:
                bd.append(init);
                bd.append("         ");
                break;
            case 5:
                bd.append(init);
                bd.append("        ");
                break;
            case 6:
                bd.append(init);
                bd.append("       ");
                break;
            case 7:
                bd.append(init);
                bd.append("      ");
                break;
            case 8:
                bd.append(init);
                bd.append("     ");
                break;
            case 9:
                bd.append(init);
                bd.append("    ");
                break;
            case 10:
                bd.append(init);
                bd.append("   ");
                break;
            case 11:
                bd.append(init);
                bd.append("  ");
                break;
            case 12:
                bd.append(init);
                bd.append(" ");
                break;
            case 13:
                bd.append(init);
                break;
        }
        return bd.toString();
    }
    int serve = 0;
    private String Dis(String init, int val)
    {
        serve = val - init.length();
        StringBuilder bd = new StringBuilder();
        bd.append("  ");
        if(init.length() <= val)
        {
            String ret = preDis(init, init.length());
            bd.append(ret);
        }else
        {
            serve = 0;
            int me = 0;
            while(me < val)
            {
                bd.append(init.charAt(me));
                me++;
            }
        }
        return bd.toString();
    }
    private void displaySales(String frmDb, boolean check)
    {
        DatabaseCheck sql = new DatabaseCheck();
        JSONObject obj2a = new JSONObject(frmDb);
        JSONArray arr2a = new JSONArray();
        arr2a = obj2a.getJSONArray("data");
        list2a.clear();
        list2b.clear();
        list2c.clear();
        list2d.clear();
        for(int i = 0; i < arr2a.length(); i++){
            list2a.add(arr2a.getJSONObject(i).getString("id"));
            list2b.add(arr2a.getJSONObject(i).getString("name"));    
            list2c.add(arr2a.getJSONObject(i).getString("price"));
            list2d.add(arr2a.getJSONObject(i).getString("quantity"));
            if(check)
            {
                if(i == 0)
                {
                    sql.droptable("pregoods");
                    sql.createpregoods();
                }
                sql.insertpregoods(list2a.get(i), Integer.valueOf(list2d.get(i)), 
                        Integer.valueOf(list2c.get(i)), list2b.get(i));
            }
        }
                
        Platform.runLater(() -> {
            int i = 0;
            int sum = 0;
            while(i < list2c.size())
            {
                sum += Integer.valueOf(list2c.get(i)) * Integer.valueOf(list2d.get(i));
                i++;
            }
            sumSave = String.valueOf(sum);
            amount.setText(amountPop(String.valueOf(sum)));
        });
        
        FlowPane flowall = createFlowPane();
        
        adddisplay.getChildren().clear();
        
        for (int n = 1; n<list2a.size() + 1; n++) {
            FlowPane flow = createFlowPane();
            FlowPane flow2 = createFlowPane();
            flow.getStyleClass().add("color-back");
            flow2.getStyleClass().add("color-back");
            flowall.getStyleClass().add("color-back");
        
            BorderPane bPane = new BorderPane(); 
            ImageView dec = createBack(list2a.get(n-1));
            dec.setCursor(Cursor.HAND); 
            //dec.setStyle("-fx-padding: 10 0 0 0;");
            JFXTextField qty = new JFXTextField();
            qty.setOnMouseExited(new MouseExitEdit(list2a.get(n-1)));
            qty.setPrefWidth(50);
            qty.setId(list2a.get(n-1));
            qty.setText(list2d.get(n-1));
            qty.setStyle("-fx-alignment: center;");
            //qty.setStyle("-fx-padding: 10 0 0 0;");
            ImageView inc = createNext(list2a.get(n-1));
            inc.setCursor(Cursor.HAND); 
            //inc.setStyle("-fx-padding: 10 0 0 0;");
            flow.getChildren().addAll(dec, qty, inc);
            StackPane left = new StackPane();
            left.setPrefWidth(150);
            left.getChildren().add(flow);
            left.setStyle("-fx-padding: 10 0 0 0;");
            bPane.setLeft(left);
            
            //Label name2 = new Label(Dis(list2b.get(n-1), 13));
            Label name2 = new Label(list2b.get(n-1));
            name2.getStyleClass().add("text-raised");
            name2.setStyle("-fx-padding: 10 0 0 0;");
            Label amt = new Label("₦" + amountPop(list2c.get(n-1)));
            amt.getStyleClass().add("text-raised");
            VBox b = new VBox();
            b.getStyleClass().add("color-back");
            b.getChildren().addAll(name2, amt);
            StackPane center = new StackPane();
            center.setPrefWidth(220);
            center.getChildren().add(b);
            center.setStyle("-fx-padding: 10 0 0 0;");
            bPane.setCenter(center);
            
            ImageView img = createCancel(list2a.get(n-1));
            img.setCursor(Cursor.HAND); 
            img.setStyle("-fx-padding: 30 0 0 0;");
            //bPane.setRight(img);
            StackPane right = new StackPane();
            right.setPrefWidth(5);
            right.getChildren().add(img);
            left.setStyle("-fx-padding: 30 0 0 0;");
            bPane.setRight(right);
            
            flowall.getChildren().add(bPane);
        }

        ScrollPane sp1 = new ScrollPane(flowall);
        sp1.setFitToWidth(true);
        sp1.setFitToHeight(true);
        sp1.setStyle("-fx-background-color: transparent; -fx-background-color: transparent; -fx-padding: 0; -fx-background-insets: 0; -fx-border-color: #000000; -fx-border-width:0; -fx-border-insets:0;");
        sp1.getStyleClass().add("scroll-pane");
        
        
        
        sp1.setVvalue(1.0);
        
        displayscroll.setStyle("-fx-background-color: #000000; -fx-background-color: #000000; -fx-padding: 0; -fx-background-insets: 0; -fx-border-color: #000000; -fx-border-width:0; -fx-border-insets:0;");
        adddisplay.getChildren().add(sp1);
        
        displayscroll.setStyle("-fx-background: #000000; -fx-control-inner-background: #000000;");
        ram.setStyle("-fx-background: #000000; -fx-control-inner-background: #000000;");
      
    }
    
    @FXML
    private AnchorPane ram;
    
    class BoxClick implements EventHandler<MouseEvent> {
        private final String price;
        private final String name;
        BoxClick(String name, String price) {
            this.price = price;
            this.name = name;
        }
        @Override
        public void handle(MouseEvent event) {
            supername = name;
            superprice = price.replaceAll("₦", "");
            int nameIndex = listb.indexOf(name);
            String id = lista.get(nameIndex);
            superquantity = listd.get(nameIndex);
            parksal.setVisible(true);
            DatabaseCheck sql = new DatabaseCheck();
            if(sql.getpregoodsbyName(name).length() < 2)
            {
                displayscroll.setVisible(true);
                sql.insertpregoods(id, 1, Integer.valueOf(price), name);
                String preg = sql.getpregoods();
                if(preg.equals("") || preg.length() < 6)
                {
                    checkout.setVisible(false);
                    salescancel.setVisible(false);
                    parksal.setVisible(false);
                    displayscroll.setVisible(false);
                }else
                {
                    checkout.setVisible(true);
                    salescancel.setVisible(true);
                    displayscroll.setVisible(true);
                    displaySales(preg, false);
                }
            }else
            {
                int qty = Integer.valueOf(sql.getpregoodsQtybyId(id));
                int qtyint = Integer.valueOf(listd.get(nameIndex));
                if(qty <= qtyint)
                {
                    qty++;
                }
                sql.updatepregoods(id, qty);
                String preg = sql.getpregoods();
                displaySales(preg, false);
            }
        }
    }
    
    
    private VBox createNumberVBox(String name, String price) {
        VBox enclose = new VBox();
        Label nm = new Label(name);
        nm.getStyleClass().add("label-name");
        Label pr = new Label("₦" + price);
        pr.getStyleClass().add("button-raised");
        enclose.getChildren().addAll(nm, pr);
        GridPane.setFillHeight(enclose, true);
        GridPane.setFillWidth(enclose, true);
        GridPane.setHgrow(enclose, Priority.ALWAYS);
        GridPane.setVgrow(enclose, Priority.ALWAYS);
        enclose.setOnMouseClicked((new BoxClick(name, price)));
        return enclose;
    }
    
    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10));
        return grid;
    }
    private FlowPane createFlowPane() {
        FlowPane flow = new FlowPane();
        return flow;
    }
    private ImageView createCancelImage() {
        Image image = new Image(getClass().getResource("cancel.png").toExternalForm());
        ImageView cancel = new ImageView();
        cancel.setImage(image);
        return cancel;
    }
    private ImageView createCancel(String number) {
        ImageView image = createCancelImage();
        image.setOnMouseClicked(new CloseHandler(number));
        return image;
    }
    class BackHandler implements EventHandler<MouseEvent> {
        private final String number;
        BackHandler(String number) {
            this.number = number ;
        }
        @Override
        public void handle(MouseEvent event) {
            DatabaseCheck sql = new DatabaseCheck();
            int qty = Integer.valueOf(sql.getpregoodsQtybyId(number));
            if(qty <= 1)
                qty = 1;
            else
                qty--;
            sql.updatepregoods(number, qty);
            String preg = sql.getpregoods();
            displaySales(preg, false);
        }
    }
    class NextHandler implements EventHandler<MouseEvent> {
        private final String number;
        NextHandler(String number) {
            this.number = number ;
        }
        @Override
        public void handle(MouseEvent event) {
            DatabaseCheck sql = new DatabaseCheck();
            int qty = Integer.valueOf(sql.getpregoodsQtybyId(number));
            int nameIndex = lista.indexOf(number);
            int qtyint = Integer.valueOf(listd.get(nameIndex));
            if(qty <= qtyint)
            {
                qty++;
            }
            sql.updatepregoods(number, qty);
            String preg = sql.getpregoods();
            displaySales(preg, false);
        }
    }
    private ImageView createBackImage() {
        Image image = new Image(getClass().getResource("arrow-decrement.png").toExternalForm());
        ImageView cancel = new ImageView();
        cancel.setImage(image);
        return cancel;
    }
    private ImageView createNextImage() {
        Image image = new Image(getClass().getResource("arrow-incremeant.png").toExternalForm());
        ImageView cancel = new ImageView();
        cancel.setImage(image);
        return cancel;
    }
    private ImageView createBack(String number) {
        ImageView image = createBackImage();
        image.setOnMouseClicked(new BackHandler(number));
        return image;
    }
    private ImageView createNext(String number) {
        ImageView image = createNextImage();
        image.setOnMouseClicked(new NextHandler(number));
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
            if(field.getText() == null)
            {
                
            }else
            {
                DatabaseCheck sql = new DatabaseCheck();
                try
                {
                    int qty = Integer.valueOf(sql.getpregoodsQtybyId(number));
                    int nameIndex = lista.indexOf(number);
                    int qtyint = Integer.valueOf(listd.get(nameIndex));
                    if(Integer.valueOf(field.getText()) <= qtyint)
                        sql.updatepregoods(number, Integer.valueOf(field.getText()));
                    else
                        sql.updatepregoods(number, qty);
                    String preg = sql.getpregoods();
                    displaySales(preg, false);
                }catch(NumberFormatException e)
                {
                    sql.updatepregoods(number, 1);
                    String preg = sql.getpregoods();
                    displaySales(preg, false);
                    System.out.println(e);
                }
            }
        }
    }
    
    @FXML
    private ScrollPane displayscroll;
    @FXML
    private AnchorPane pane;
    @FXML
    private AnchorPane meall;
    @FXML
    private ScrollPane har;
    @FXML
    private Label datetime;
    @FXML
    private Label namelog;
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    List<String> lista = new ArrayList<>();
    List<String> listb = new ArrayList<>();
    List<String> listc = new ArrayList<>();
    List<String> listd = new ArrayList<>();
    
    List<String> list2a = new ArrayList<String>();
    List<String> list2b = new ArrayList<String>();
    List<String> list2c = new ArrayList<String>();
    List<String> list2d = new ArrayList<String>();
    
    @FXML
    private JFXTextField searchBox;
     
    @FXML
    private JFXButton parksal;
    
    private void populate(List<String> lista, List<String> listb, List<String> listc)
    {
        ali.getChildren().clear();
        GridPane grid = createGrid();
        grid.getStyleClass().add("color-grid");

        for (int n = 1; n<lista.size() + 1; n++) {
            VBox box = createNumberVBox(listb.get(n-1), listc.get(n-1));
            int row = (n-1) / 3;
            int col = (n-1) % 3;
            grid.add(box, col, row);
        }

        ScrollPane sp = new ScrollPane(grid);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        sp.setStyle("-fx-background-color: transparent; -fx-background-color: transparent; -fx-padding: 0; -fx-background-insets: 0; -fx-border-color: #000000; -fx-border-width:0; -fx-border-insets:0;");
        oga.getStyleClass().add("scroll-pane");
        sp.getStyleClass().add("scroll-pane");
        ali.getChildren().add(sp);
    }
    
    @FXML
    void onSearchKeyReleased(KeyEvent event) 
    {
        if(searchBox.getText() == null || "".equals(searchBox.getText()))
        {
            lista.clear();
            listb.clear();
            listc.clear();
            listd.clear();
            DatabaseCheck sql = new DatabaseCheck();
            String gd = sql.getgoods();
            JSONObject obj = new JSONObject(gd);
            JSONArray arr = obj.getJSONArray("data");
            for(int i = 0; i < arr.length(); i++){
                if(arr.getJSONObject(i).getInt("quantity") > 0)
                {
                    lista.add(String.valueOf(arr.getJSONObject(i).getString("id")));
                    listb.add(arr.getJSONObject(i).getString("name"));    
                    listc.add(String.valueOf(arr.getJSONObject(i).getString("price")));
                    listd.add(String.valueOf(arr.getJSONObject(i).getInt("quantity")));
                }
            }
            populate(listd, listb, listc);
        }else
        {
            lista.clear();
            listb.clear();
            listc.clear();
            listd.clear();
            DatabaseCheck sql = new DatabaseCheck();
            String gd = sql.getgoods();
            JSONObject obj = new JSONObject(gd);
            JSONArray arr = obj.getJSONArray("data");
            for(int i = 0; i < arr.length(); i++){
                if(arr.getJSONObject(i).getInt("quantity") > 0)
                {
                    lista.add(String.valueOf(arr.getJSONObject(i).getString("id")));
                    listb.add(arr.getJSONObject(i).getString("name"));    
                    listc.add(String.valueOf(arr.getJSONObject(i).getString("price")));
                    listd.add(String.valueOf(arr.getJSONObject(i).getInt("quantity")));
                }
            }
            
            String search = searchBox.getText();
            List<String> listtempa = new ArrayList<>();
            List<String> listtempb = new ArrayList<>();
            List<String> listtempc = new ArrayList<>();
            List<String> listtempd = new ArrayList<>();
            
            for(int n = 0; n < listd.size(); n++)
            {
                if(listd.get(n).toLowerCase().toLowerCase().contains(search.toLowerCase()))
                {
                    listtempa.add(lista.get(n));
                    listtempb.add(listb.get(n));
                    listtempc.add(listc.get(n));
                    listtempd.add(listd.get(n));
                }else if(listb.get(n).toLowerCase().toLowerCase().contains(search.toLowerCase()))
                {
                    listtempa.add(lista.get(n));
                    listtempb.add(listb.get(n));
                    listtempc.add(listc.get(n));
                    listtempd.add(listd.get(n));
                }else if(listc.get(n).toLowerCase().toLowerCase().contains(search.toLowerCase()))
                {
                    listtempa.add(lista.get(n));
                    listtempb.add(listb.get(n));
                    listtempc.add(listc.get(n));
                    listtempd.add(listd.get(n));
                }
            }
            lista.clear();
            listb.clear();
            listc.clear();
            listd.clear();
            listtempa.forEach((item) -> {
                lista.add(item);
            });
            listtempb.forEach((item) -> {
                listb.add(item);
            });
            listtempc.forEach((item) -> {
                listc.add(item);
            });
            listtempd.forEach((item) -> {
                listd.add(item);
            });
            populate(listd, listb, listc);
        }
    }
    
    String superquantity = "";
    String superprice = "";
    String supername = "";
    static String sumSave = "";
    static String removeName = "";
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
    
    @FXML
    void omInventory(ActionEvent event) {
        
     
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
    void onParkViewClicked(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLAllPark.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(FXMLSalesController.class.getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle("Park");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
            stage.show();
            ((Node)event.getSource()).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(FXMLSalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static String remove = "";
    
    @FXML
    private JFXButton checkout;
    
    @FXML
    private JFXButton salescancel;
    
    @FXML
    void onSalesCancel(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLCancelSale.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Cancel");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                    ((Node)event.getSource()).getScene().getWindow() );
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLSalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void onCheckOutClick(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLCheck.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Checkout");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                    ((Node)event.getSource()).getScene().getWindow() );
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLSalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void signout(MouseEvent event) throws IOException {
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
    }

    @FXML
    private Label amount;
    
    @FXML
    void onParkClicked(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXMLpark.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Park");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
        ((Node)event.getSource()).getScene().getWindow() );
        stage.show();
    }
    
    /**
     * Initializes the controller class.
     */
    static boolean me = false;
    
    @FXML
    private VBox ali;
    
    @FXML
    private VBox adddisplay;

    @FXML
    private ScrollPane oga;

    @FXML
    void onPaneEntered(MouseEvent event) {
        if(FXMLCancelSaleController.deleteSale)
        {
            FXMLCancelSaleController.deleteSale = false;
            FXMLparkController.clearpage = true;
            DatabaseCheck sql = new DatabaseCheck();
            sql.droptable("pregoods");
            sql.createpregoods();
            amount.setText("0.00");
            salescancel.setVisible(false);
            checkout.setVisible(false);
            parksal.setVisible(false);
            displayscroll.setVisible(false);
        }
        
        if(FXMLCancelController.deleteItem)
        {
            FXMLCancelController.deleteItem = false;
            DatabaseCheck sql = new DatabaseCheck();
            List<String> dbcheck = sql.getpregoodsIdList();
            if(dbcheck.size() <= 1)
            {
                FXMLparkController.clearpage = true;
                sql.droptable("pregoods");
                sql.createpregoods();
                amount.setText("0.00");
                salescancel.setVisible(false);
                checkout.setVisible(false);
                parksal.setVisible(false);
                displayscroll.setVisible(false);
            }else
            {
                sql.removepregoods(closeRef);
                String preg = sql.getpregoods();
                displaySales(preg, false);
            }
        }
        
        if(FXMLparkController.clearpage)
        {
            amount.setText("0.00");
            checkout.setVisible(false);
            salescancel.setVisible(false);
            parksal.setVisible(false);
            displayscroll.setVisible(false);
            FXMLparkController.clearpage = false;
            
            searchBox.clear();
            lista.clear();listb.clear();listc.clear();listd.clear();
            DatabaseCheck sql = new DatabaseCheck();
            String gd = sql.getgoods();
            JSONObject obj = new JSONObject(gd);
            JSONArray arr = obj.getJSONArray("data");
            for(int i = 0; i < arr.length(); i++){
                if(arr.getJSONObject(i).getInt("quantity") > 0)
                {
                    lista.add(String.valueOf(arr.getJSONObject(i).getString("id")));
                    listb.add(arr.getJSONObject(i).getString("name"));    
                    listc.add(String.valueOf(arr.getJSONObject(i).getString("price")));
                    listd.add(String.valueOf(arr.getJSONObject(i).getString("quantity")));
                }
            }
            populate(listd, listb, listc);
        }
        
    }
    
    @FXML
    private Label wiseman;
    
    @FXML
    private Label synclabel;
    
    @FXML
    private JFXButton syncbutton;
    
    @FXML
    void onSyncSales(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLSync.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Sync");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("gr-transparent.png")));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.initOwner(
                    ((Node)event.getSource()).getScene().getWindow() );
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLSalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lista.clear();
        listb.clear();
        listc.clear();
        listd.clear();
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
        
        DatabaseCheck sql = new DatabaseCheck();
        sql.droptable("pregoods");
        sql.createpregoods();
        
        displayscroll.setStyle("-fx-background: #000000; -fx-control-inner-background: #000000;");
        ram.setStyle("-fx-background: #000000; -fx-control-inner-background: #000000;");
        
        Platform.runLater(() -> {
            String gd = sql.getgoods();
            System.out.println( "products:"+ gd);
            if(gd.length() > 20)
            {
                wiseman.setVisible(false);
                JSONObject obj = new JSONObject(gd);
                JSONArray arr = obj.getJSONArray("data");
                for(int i = 0; i < arr.length(); i++){
                    if(arr.getJSONObject(i).getInt("quantity") > 0)
                    {
                        lista.add(String.valueOf(arr.getJSONObject(i).getString("id")));
                        listb.add(arr.getJSONObject(i).getString("name"));    
                        listc.add(String.valueOf(arr.getJSONObject(i).getString("price")));
                        listd.add(String.valueOf(arr.getJSONObject(i).getString("quantity")));
                    }
                }
            }else
            {
                String goods = Post.getGoods();
                String services = Post.getServices();
                wiseman.setVisible(false);
                sql.creategoods();
                JSONObject obj = new JSONObject(goods);
                JSONArray arr = obj.getJSONArray("data");
                int i = 0;
                for(i = 0; i < arr.length(); i++){
                    if(arr.getJSONObject(i).getInt("quantity") > 0)
                    {
                        lista.add(String.valueOf(arr.getJSONObject(i).getString("productId")));
                        listb.add(arr.getJSONObject(i).getString("name"));    
                        listc.add(String.valueOf(arr.getJSONObject(i).getInt("salePrice")));
                        listd.add(String.valueOf(arr.getJSONObject(i).getInt("quantity")));
                        sql.insertgoods(lista.get(i), Integer.valueOf(listc.get(i)), Integer.valueOf(listd.get(i)), listb.get(i));
                    }
                }
                
                JSONObject obj2 = new JSONObject(services);
                JSONArray arr2 = obj2.getJSONArray("data");
                for(int k = 0; k < arr2.length(); k++){
                    lista.add(String.valueOf(arr2.getJSONObject(k).getString("serviceId")));
                    listb.add(arr2.getJSONObject(k).getString("name"));    
                    listc.add(String.valueOf(arr2.getJSONObject(k).getInt("salePrice")));
                    listd.add(String.valueOf(5000));
                    sql.insertgoods(lista.get(i + k), Integer.valueOf(listc.get(i + k)), Integer.valueOf(listd.get(i + k)), listb.get(i + k));
                }
            }
            
            GridPane grid = createGrid();
            grid.getStyleClass().add("color-grid");

            for (int n = 1; n<lista.size() + 1; n++) {
                VBox box = createNumberVBox(listb.get(n-1), listc.get(n-1));
                int row = (n-1) / 3;
                int col = (n-1) % 3;
                grid.add(box, col, row);
            }

            ScrollPane sp = new ScrollPane(grid);
            sp.setFitToWidth(true);
            sp.setFitToHeight(true);
            sp.setStyle("-fx-background-color: transparent; -fx-background-color: transparent; -fx-padding: 0; -fx-background-insets: 0; -fx-border-color: #000000; -fx-border-width:0; -fx-border-insets:0;");
            oga.getStyleClass().add("scroll-pane");
            sp.getStyleClass().add("scroll-pane");
            ali.getChildren().add(sp);
        });
        
        displayscroll.setVisible(false);
                
        //debug from here
        if(FXMLAllParkController.idfromPacked.length() > 2)
        {
            displayscroll.setVisible(true);
            Platform.runLater(() -> {
                String goods = sql.getparksaleId(FXMLAllParkController.idfromPacked);
                if(goods.length() < 5)
                {
                    System.out.println("Error. No data");
                }else
                {
                    sql.removeparksale(FXMLAllParkController.idfromPacked);
                    sql.removeparksaleDisplay(FXMLAllParkController.idfromPacked);
                    checkout.setVisible(true);
                    salescancel.setVisible(true);
                    parksal.setVisible(true);
                    displaySales(goods, true);
                }
            });
        }
    }
   
}
