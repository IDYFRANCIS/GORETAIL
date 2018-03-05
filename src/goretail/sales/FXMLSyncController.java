/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail.sales;

import com.jfoenix.controls.JFXButton;
import static com.sun.deploy.security.ruleset.DRSHelper.check;
import goretail.DatabaseCheck;
import goretail.FXMLHOMEController;
import goretail.Post;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author WISDOM IBANGA
 */
public class FXMLSyncController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label status;
    
    List<String> lista = new ArrayList<>();
    List<String> listb = new ArrayList<>();
    List<String> listc = new ArrayList<>();
    List<String> listd = new ArrayList<>();
    List<String> liste = new ArrayList<>();
    
    @FXML
    private JFXButton done;

    @FXML
    void onDoneClicked(ActionEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
    
    void sendSales(String json, String time, int init, int size)
    {
        status.setText("Uploading " + String.valueOf(init) + " of " + String.valueOf(size));
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                Platform.runLater(() -> {
                    String res = Post.salesSync(json);
                    if(res.contains("Sale records have been added"))
                    {
                        DatabaseCheck sql = new DatabaseCheck();
                        sql.removesalesupload(time);
                        sql.salesstore(time);
                        if((init + 1) == size)
                        {
                            status.setText("All Sales Uploaded");
                            done.setVisible(true);
                        }
                    }
                });
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }
    
    List<String> total = new ArrayList<>();
    JSONArray array;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        DatabaseCheck sql = new DatabaseCheck();
        String sync = sql.getsalesupload();
        JSONObject obj2a = new JSONObject(sync);
        JSONArray arr2a = new JSONArray();
        arr2a = obj2a.getJSONArray("data");
        lista.clear();
        listb.clear();
        listc.clear();
        listd.clear();
        liste.clear();
        for(int i = 0; i < arr2a.length(); i++){
            lista.add(arr2a.getJSONObject(i).getString("time"));
            listb.add(arr2a.getJSONObject(i).getString("id"));    
            listc.add(arr2a.getJSONObject(i).getString("quantity"));
            listd.add(arr2a.getJSONObject(i).getString("price"));
            liste.add(arr2a.getJSONObject(i).getString("name"));
        }
        
        for(int i = 0; i < lista.size(); i++){
            if(total.contains(lista.get(i)))
            {
                //ignore
            }else
            {
                total.add(lista.get(i));
            }
        }
        
        int size = total.size();
        if(size < 1)
        {
            status.setText("No Sales Record");
            done.setVisible(true);
        }else
        {
            Date d = new Date();
            Long dVal = d.getTime();

            JSONObject obj;

            for(int i = 0; i < total.size(); i++)
            {
                array = new JSONArray();
                obj = new JSONObject();
                obj.put("date", dVal);
                obj.put("discount", 0);
                JSONArray arr = new JSONArray();
                JSONObject temp;
                for(int k = 0; k < lista.size(); k++)
                {
                    if(total.get(i).equals(lista.get(k)))
                    {
                        temp = new JSONObject();
                        temp.put("productId", listb.get(k));
                        temp.put("quantity", Integer.valueOf(listc.get(k)));
                        arr.put(temp);
                    }
                }
                obj.put("items", arr);
                obj.put("operatorId", FXMLHOMEController.USEuserid);
                obj.put("paymentMethod", "Cash");
                obj.put("total", Long.valueOf(sql.getsalesstore(total.get(i))));
                obj.put("transactionId", total.get(i));
                array.put(obj);
                sendSales(array.toString(), total.get(i), i, size);
            }
        }
    }    
    
}
