/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail.sales;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import goretail.DatabaseCheck;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author WISDOM IBANGA
 */
public class FXMLCheckController implements Initializable {
    List<String> list2a = new ArrayList<String>();
    List<String> list2b = new ArrayList<String>();
    List<String> list2c = new ArrayList<String>();
    List<String> list2d = new ArrayList<String>();
    
    @FXML
    private JFXTextField amtcollected;

    @FXML
    private Label amt;

    @FXML
    private JFXTreeTableView<User2> treeView;

    @FXML
    void onCancelClicked(ActionEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    static int amount = 0;
    @FXML
    void onPrintClicked(ActionEvent event) {
        if(amtcollected.getText() == null)
        {
            amtcollected.requestFocus();
        }else if(amtcollected.getText().length() < 1)
        {
            amtcollected.requestFocus();
        }else if(Integer.valueOf(amtcollected.getText()) < sum)
        {
            amtcollected.requestFocus();
        }else
        {
            amount = Integer.valueOf(amtcollected.getText());
            ((Node)event.getSource()).getScene().getWindow().hide();
            try {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("FXMLPrint.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Print");
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

    /**
     * Initializes the controller class.
     */
    static int sum = 0;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            DatabaseCheck sql = new DatabaseCheck();
            String preg = sql.getpregoods();
            if(preg.equals("") || preg.length() < 6)
            {
                
            }else
            {
                JSONObject obj2a = new JSONObject(preg);
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
                }
                Platform.runLater(() -> {
                    int i = 0;
                    sum = 0;
                    while(i < list2c.size())
                    {
                        sum += Integer.valueOf(list2c.get(i)) * Integer.valueOf(list2d.get(i));
                        i++;
                    }
                    FXMLSalesController jx = new FXMLSalesController();
                    amt.setText(jx.amountPop(String.valueOf(sum)));
                });
            }
            table2();
        });
    }   
    
    class User2 extends RecursiveTreeObject<User2>{
        StringProperty NAME;
        StringProperty PRICE;
        StringProperty QUANTITY;
        public User2(String NAME, String PRICE, String QUANTITY) {
            this.NAME = new SimpleStringProperty(NAME);
            this.PRICE = new SimpleStringProperty(PRICE);
            this.QUANTITY = new SimpleStringProperty(QUANTITY);
        }
    }
    
    public void table2()
    {
        JFXTreeTableColumn<User2, String> name = new JFXTreeTableColumn<>("NAME");
        name.setPrefWidth(130);
        name.setCellValueFactory((TreeTableColumn.CellDataFeatures<User2, String> param) -> param.getValue().getValue().NAME);
        name.setStyle( "-fx-alignment: CENTER;");
        
        JFXTreeTableColumn<User2, String> price = new JFXTreeTableColumn<>("PRICE");
        price.setPrefWidth(70);
        price.setCellValueFactory((TreeTableColumn.CellDataFeatures<User2, String> param) -> param.getValue().getValue().PRICE);
        price.setStyle( "-fx-alignment: CENTER;");
        
        JFXTreeTableColumn<User2, String> quantity = new JFXTreeTableColumn<>("QUANTITY");
        quantity.setPrefWidth(100);
        quantity.setCellValueFactory((TreeTableColumn.CellDataFeatures<User2, String> param) -> param.getValue().getValue().QUANTITY);
        quantity.setStyle( "-fx-alignment: CENTER;");
        
        ObservableList<User2> users = FXCollections.observableArrayList();
        
        for(int i=0;i<list2a.size();i++)
        {
            users.add(new User2(list2b.get(i), list2c.get(i), list2d.get(i)));
        }
        final TreeItem<User2> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(name, price, quantity);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }
    
}
