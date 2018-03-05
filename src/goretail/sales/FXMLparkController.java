/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail.sales;

import goretail.DatabaseCheck;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author WISDOM IBANGA
 */
public class FXMLparkController implements Initializable {

    static boolean clearpage = false; 
    
    private String getMonth(int month)
    {
        if(month == 1)
            return "Jan";
        else if(month == 2)
            return "Feb";
        else if(month == 3)
            return "Mar";
        else if(month == 4)
            return "Apr";
        else if(month == 5)
            return "May";
        else if(month == 6)
            return "Jun";    
        else if(month == 7)
            return "Jul";    
        else if(month == 8)
            return "Aug";
        else if(month == 9)
            return "Sep";
        else if(month == 10)
            return "Oct";
        else if(month == 11)
            return "Nov";
        else
            return "Dec";
    }
    @FXML
    void onCancelClicked(ActionEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void onYesClicked(ActionEvent event) {
        DatabaseCheck sql = new DatabaseCheck();
        sql.createParkSale();
        sql.createParkSaleDisplay();
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        String goods = sql.getpregoods();
        if(goods.equals("") || goods.length() < 6)
        {
            
        }else
        {
            JSONObject obj2a = new JSONObject(goods);
            JSONArray arr2a = new JSONArray();
            arr2a = obj2a.getJSONArray("data");
            int tot = 0;
            for(int i = 0; i < arr2a.length(); i++){
                sql.insertparksale(time, arr2a.getJSONObject(i).getString("id"), 
                    arr2a.getJSONObject(i).getString("price"), 
                    arr2a.getJSONObject(i).getString("quantity"), 
                    arr2a.getJSONObject(i).getString("name"));
                tot += Integer.valueOf(arr2a.getJSONObject(i).getString("quantity")) * Integer.valueOf(arr2a.getJSONObject(i).getString("price"));
            }
            
            Date date = new Date(); 
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int min = cal.get(Calendar.MINUTE);
            int sec = cal.get(Calendar.SECOND);
            sql.createParkSaleDisplay();
            String dayStr = String.valueOf(day) + " " + getMonth(month) + " " + String.valueOf(year) + " " + String.valueOf(hour) + ":" + String.valueOf(min) + ":" + String.valueOf(sec);
            sql.insertparksaleDisplay(time, dayStr, DatabaseCheck.getName(), String.valueOf(tot));
            sql.droptable("pregoods");
            sql.createpregoods();
            clearpage = true;
        }
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
