/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author WISDOM IBANGA
 */
public class DatabaseCheck{
    
    public boolean droptable(String tablename)
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            stmt = c.createStatement();
            String sql = "DROP TABLE IF EXISTS " + tablename + ";"; 
            stmt.execute(sql);
            stmt.close();
            c.close();
            return true;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }
    
    public boolean createlogin()
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE login " +
                "(ID INT PRIMARY KEY NOT NULL, " +
                "userid CHAR(248) NOT NULL, " + 
                "businessid CHAR(248) NOT NULL, " + 
                "name CHAR(248) NOT NULL, " + 
                "businessname CHAR(248) NOT NULL, " + 
                "businesscode CHAR(248) NOT NULL, " + 
                "phone CHAR(248) NOT NULL, " +
                "email CHAR(248) NOT NULL, " + 
                "contactname CHAR(248) NOT NULL, " + 
                "address CHAR(248) NOT NULL, " + 
                "datejoined CHAR(248) NOT NULL, " +
                "dateupdated CHAR(248) NOT NULL)"; 
            stmt.execute(sql);
            stmt.close();
            c.close();
            return true;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }
    public boolean insertlogin(String userid, String businessid, String name, String businessname, 
            String businesscode, String phone, String email, String contactname, String address, 
            String datejoined, String dateupdated) {
        Connection c = null;
        Statement stmt = null;
      
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String val = "(1, " + "\'" + userid + "\'," + "\'" + businessid + "\',"
                    + "\'" + name + "\'," + "\'" + businessname + "\'," + "\'" + businesscode + "\',"
                    + "\'" + phone + "\'," + "\'" + email + "\'," + "\'" + contactname + "\',"
                    + "\'" + address + "\'," + "\'" + datejoined + "\'," + "\'" + dateupdated + "\');";
            String sql = "INSERT INTO login (ID, userid, businessid, name, businessname, businesscode,"
                    + " phone, email, contactname, address, datejoined, dateupdated) " +
                    "VALUES " + val; 
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
            return true;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return false;
    }
    public static String getName()
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM login;" );
            String name = "";
            while ( rs.next() ) {
                name = rs.getString("contactname");
            }
            rs.close();
            stmt.close();
            c.close();
            return name;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return "NA";
    }
    public static String getBusId()
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM login;" );
            String bid = "";
            while ( rs.next() ) {
                bid = rs.getString("businessid");
            }
            rs.close();
            stmt.close();
            c.close();
            return bid;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return "NA";
    }

    public void creategoods()
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE goods " +
                "(ID CHAR(248) NOT NULL, " +
                "price INT NOT NULL, " +
                "quantity INT NOT NULL, " +
                "name CHAR(248) NOT NULL)"; 
            stmt.execute(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }
    }
    public void insertgoods(String id, int price, int quantity, String name) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String val = "(\'" + id + "\', " + String.valueOf(price) + ", " 
                    + String.valueOf(quantity) + ", " + "\'" + name + "\');";
            String sql = "INSERT INTO goods (ID, price, quantity, name) " +
                    "VALUES " + val; 
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    public String getgoods()
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM goods;");
            JSONArray arr = new JSONArray();
            JSONObject obj;
            
            while ( rs.next() ) {
                obj = new JSONObject();
                obj.put("id", String.valueOf(rs.getString("ID")));
                obj.put("price", String.valueOf(rs.getInt("price")));
                obj.put("quantity", String.valueOf(rs.getInt("quantity")));
                obj.put("name", rs.getString("name"));
                arr.put(obj);
            }
            rs.close();
            stmt.close();
            c.close();
            JSONObject sen = new JSONObject();
            sen.put("data", arr);
            return sen.toString();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return "";
    }
    public String getgoodsbyId(String id)
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM goods WHERE ID=\'" + id + "\';");
            String qty = "";
            while ( rs.next() ) {
                qty = rs.getString("quantity");
            }
            rs.close();
            stmt.close();
            c.close();
            return qty;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return "";
    }
    public void updategoods(String id, int quantity)
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "UPDATE goods set quantity = " + String.valueOf(quantity)
                    + " where ID=\'" + id + "\';";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    
    
    public void createpregoods()
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE pregoods " +
                "(ID CHAR(248) NOT NULL, " +
                "quantity INT NOT NULL, " +
                "price INT NOT NULL, " +
                "name CHAR(248) NOT NULL)"; 
            stmt.execute(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }
    }
    public void updatepregoods(String id, int quantity)
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "UPDATE pregoods set quantity = " + String.valueOf(quantity)
                    + " where ID=\'" + id + "\';";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    public void insertpregoods(String id, int quantity, int price, String name) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String val = "(\'" + id + "\', " + String.valueOf(quantity) 
                    + ", " + String.valueOf(price) + ", " 
                    + "\'" + name + "\');";
            String sql = "INSERT INTO pregoods (ID, quantity, price, name) " +
                    "VALUES " + val; 
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    public String getpregoodsbyName(String name)
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM pregoods WHERE name=\'" + name + "\';");
            String nameRet = "";
            while ( rs.next() ) {
                nameRet = rs.getString("name");
            }
            rs.close();
            stmt.close();
            c.close();
            return nameRet;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return "";
    }
    public String getpregoodsQtybyId(String id)
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pregoods WHERE ID=\'" + id + "\';");
            String qty = "";
            while ( rs.next() ) {
                qty = rs.getString("quantity");
            }
            rs.close();
            stmt.close();
            c.close();
            return qty;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return "";
    }   
    public List<String> getpregoodsIdList()
    {
        Connection c = null;
        Statement stmt = null;
        List<String> id = new ArrayList<String>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pregoods;");
            while ( rs.next() ) {
                id.add(rs.getString("ID"));
            }
            rs.close();
            stmt.close();
            c.close();
            return id;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return id;
    }
    public List<String> getpregoodsQtyList()
    {
        Connection c = null;
        Statement stmt = null;
        List<String> qty = new ArrayList<String>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pregoods;");
            while ( rs.next() ) {
                qty.add(rs.getString("quantity"));
            }
            rs.close();
            stmt.close();
            c.close();
            return qty;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return qty;
    }
    public String getpregoods() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM pregoods;" );
            
            JSONArray arr = new JSONArray();
            JSONObject obj;
            
            while ( rs.next() ) {
                obj = new JSONObject();
                obj.put("id", String.valueOf(rs.getString("id")));
                obj.put("quantity", String.valueOf(rs.getString("quantity")));
                obj.put("price", String.valueOf(rs.getString("price")));
                obj.put("name", rs.getString("name"));
                arr.put(obj);
            }
            rs.close();
            stmt.close();
            c.close();
            JSONObject sen = new JSONObject();
            sen.put("data", arr);
            return sen.toString();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return "";
    }
    public void removepregoods(String id) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "DELETE from pregoods where ID=\'" + id +"\';";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
  
    
    
    public boolean createSalesUpload()
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS salesupload " +
                "(time CHAR(248) NOT NULL, " +
                "id CHAR(248) NOT NULL, " + 
                "name CHAR(248) NOT NULL, " +
                "quantity CHAR(248) NOT NULL, " +
                "price CHAR(248) NOT NULL)"; 
            stmt.execute(sql);
            stmt.close();
            c.close();
            return true;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

    public void insertsalesupload(String time, String id, String price, String quantity, String name) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String val = "(\'" + time + "\' , \'" + id + "\', \'" + price + "\', \'" 
                    + quantity + "\', \'" + name + "\');";
            String sql = "INSERT INTO salesupload (time, id, name, quantity, price) " +
                    "VALUES " + val; 
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    
    public String getsalesupload() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM salesupload;" );
            
            JSONArray arr = new JSONArray();
            JSONObject obj;
            
            while ( rs.next() ) {
                obj = new JSONObject();
                obj.put("time", String.valueOf(rs.getString("time")));
                obj.put("id", String.valueOf(rs.getString("id")));
                obj.put("quantity", String.valueOf(rs.getString("quantity")));
                obj.put("price", String.valueOf(rs.getString("price")));
                obj.put("name", rs.getString("name"));
                arr.put(obj);
            }
            rs.close();
            stmt.close();
            c.close();
            JSONObject sen = new JSONObject();
            sen.put("data", arr);
            return sen.toString();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return "";
    }
    
    public void removesalesupload(String time) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "DELETE from salesupload where time=\'" + time +"\';";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    
    public boolean createSalesStore()
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS salesstore " +
                "(time CHAR(248) NOT NULL, " +
                "amount CHAR(248) NOT NULL)"; 
            stmt.execute(sql);
            stmt.close();
            c.close();
            return true;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }
    public void insertsalesstore(String time, String amount) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String val = "(\'" + time + "\' , \'" + amount + "\');";
            String sql = "INSERT INTO salesstore (time, amount) " +
                    "VALUES " + val; 
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    
    public String getsalesstore(String time)
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM salesstore WHERE time=\'" + time + "\';");
            String amt = "";
            while ( rs.next() ) {
                amt = rs.getString("amount");
            }
            rs.close();
            stmt.close();
            c.close();
            return amt;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return "";
    }
    
    public void salesstore(String time) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "DELETE from salesstore where time=\'" + time +"\';";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    
    public boolean createParkSale()
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS parksales " +
                "(time CHAR(248) NOT NULL, " +
                "id CHAR(248) NOT NULL, " +
                "price CHAR(248) NOT NULL, " +
                "quantity CHAR(248) NOT NULL, " +
                "name CHAR(248) NOT NULL)"; 
            stmt.execute(sql);
            stmt.close();
            c.close();
            return true;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }
    public void insertparksale(String time, String id, String price, String quantity, String name) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String val = "(\'" + time + "\' , \'" + id + "\' , \'" + price + "\', \'" 
                    + quantity + "\', \'" + name + "\');";
            String sql = "INSERT INTO parksales (time, id, price, quantity, name) " +
                    "VALUES " + val; 
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    public String getparksaleId(String id)
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM parksales WHERE time=" + id + ";");
            JSONArray arr = new JSONArray();
            JSONObject obj;
            
            while ( rs.next() ) {
                obj = new JSONObject();
                obj.put("id", String.valueOf(rs.getString("id")));
                obj.put("quantity", String.valueOf(rs.getString("quantity")));
                obj.put("price", String.valueOf(rs.getString("price")));
                obj.put("name", rs.getString("name"));
                arr.put(obj);
            }
            rs.close();
            stmt.close();
            c.close();
            JSONObject sen = new JSONObject();
            sen.put("data", arr);
            return sen.toString();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return "";
    }
    public void removeparksale(String id) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "DELETE from parksales where time=" + id +";";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    
    public boolean createParkSaleDisplay()
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS parksalesdisplay " +
                "(time CHAR(248) NOT NULL, " +
                "date CHAR(248) NOT NULL, " +
                "user CHAR(248) NOT NULL, " +
                "amount CHAR(248) NOT NULL)"; 
            stmt.execute(sql);
            stmt.close();
            c.close();
            return true;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }
    public void insertparksaleDisplay(String time, String date, String user, String amount) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String val = "(\'" + time + "\' , \'" + date + "\' , \'" + user + "\' , \'" + amount + "\');";
            String sql = "INSERT INTO parksalesdisplay (time, date, user, amount) " +
                    "VALUES " + val; 
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    public String getparksaleDisplay() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM parksalesdisplay;" );
            
            JSONArray arr = new JSONArray();
            JSONObject obj;
            
            while ( rs.next() ) {
                obj = new JSONObject();
                obj.put("id", String.valueOf(rs.getString("time")));
                obj.put("date", String.valueOf(rs.getString("date")));
                obj.put("user", String.valueOf(rs.getString("user")));
                obj.put("amount", String.valueOf(rs.getString("amount")));
                arr.put(obj);
            }
            rs.close();
            stmt.close();
            c.close();
            JSONObject sen = new JSONObject();
            sen.put("data", arr);
            return sen.toString();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return "";
    }
    public void removeparksaleDisplay(String id) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:goretail.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "DELETE from parksalesdisplay where time=" + id +";";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    
}
