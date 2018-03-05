/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail.sales;

import com.jfoenix.controls.JFXComboBox;
import goretail.DatabaseCheck;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author WISDOM IBANGA
 */
public class FXMLPrintController implements Initializable {

    @FXML
    private Label change;
    
    @FXML
    private Label error;
    
    @FXML
    void onCancelClicked(ActionEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void onYesClicked(ActionEvent event) {
        error.setVisible(false);
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        DatabaseCheck sql = new DatabaseCheck();
        sql.createSalesUpload();
        sql.createSalesStore();
        sql.insertsalesstore(time, String.valueOf(FXMLCheckController.sum));
        String goods = sql.getpregoods();
        if(goods.equals("") || goods.length() < 6)
        {
            
        }else
        {
            if(printers.getValue() == null)
            {
                error.setVisible(true);
            }else if(printers.getValue().length() < 1)
            {
                error.setVisible(true);
            }else
            {
                error.setVisible(false);
                JSONObject obj2a = new JSONObject(goods);
                JSONArray arr2a = new JSONArray();
                arr2a = obj2a.getJSONArray("data");
                for(int i = 0; i < arr2a.length(); i++){
                    sql.insertsalesupload(time, arr2a.getJSONObject(i).getString("id"), 
                        arr2a.getJSONObject(i).getString("name"), 
                        arr2a.getJSONObject(i).getString("quantity"), arr2a.getJSONObject(i).getString("price"));
                }
                Print pt = new Print();
                pt.init(goods, printers.getValue());
                List<String> qty = new ArrayList<String>();
                List<String> id = new ArrayList<String>();
                qty = sql.getpregoodsQtyList();
                id = sql.getpregoodsIdList();
                for(int i = 0; i < id.size(); i++)
                {
                    int quan = Integer.valueOf(sql.getgoodsbyId(id.get(i)));
                    int quanMod = quan - Integer.valueOf(qty.get(i));
                    sql.updategoods(id.get(i), quanMod);
                }
                sql.droptable("pregoods");//delete pregoods table
                sql.createpregoods();
                FXMLparkController.clearpage = true;//cause a refresh
            }
        }
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
    /**
     * Initializes the controller class.
     */
    
    List<String> pr = new ArrayList<>();
    @FXML
    private JFXComboBox<String> printers;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        change.setText(String.valueOf(FXMLCheckController.amount - FXMLCheckController.sum));
        
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        
        for (PrintService printer : printServices)
            pr.add(printer.getName());
        
        printers.getItems().clear();
        printers.getItems().addAll(pr);
        if(pr.size() > 0)
            printers.setValue(pr.get(0));
    }    
    
}
