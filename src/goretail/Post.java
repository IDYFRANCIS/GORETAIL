/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WISDOM IBANGA
 */

public class Post {
    
    public static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }
    
    public static String forgot(String number)
    {
        try {
            HttpURLConnection connection = null;
            String targetURL = "http://5.77.43.22:8093/bzaccount/api/v1/account/passwordRecovery/1";
            URL url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("stage", "1");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            //Send request
            DataOutputStream wr = new DataOutputStream (
            connection.getOutputStream());
            wr.writeBytes("{\"password\": \"" + "string" + "\"," + "\"token\": \"" + "string" + "\"," + "\"user\": \"" + number + "\"}");
            wr.close();
            //Get Response  
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            int code = connection.getResponseCode();
            if((code == 200) || (code == 202))
            {
                return response.toString();
            }else{
                return "Invalid";
            }
        } catch (IOException ex) {
            Logger.getLogger(Post.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Invalid";
    }
    
    public static String pass = "poleoplrilakokdmvnkdpsolskpkpdjpdopjpduriojosndojsiojdiosjjonosjiosjoijios";
    public static String loginToken(String username, String password)
    {
        try {
            String postData = "grant_type=password&" + "username=" + username + "&password=" + password;
            String plain = "bzaccount" + ":" + "bzaccountSignKey";
            String base64encodedString = Base64.getEncoder().encodeToString(plain.getBytes("utf-8"));
            HttpURLConnection connection = null;
            String targetURL = "http://5.77.43.22:8093/bzaccount/api/v1/oauth/token";
            URL url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", "Basic " + base64encodedString);
            //connection.setRequestProperty("Content-Length", Integer.toString(postData.length()));
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            //Send request
            DataOutputStream wr = new DataOutputStream (
            connection.getOutputStream());
            wr.writeBytes(postData);
            wr.close();
            //Get Response  
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            int code = connection.getResponseCode();
            if(code == 200)
            {
                pass = password;
                //System.out.println(response.toString());
                return response.toString();
            }else{
                return "Invalid";
            }
        } catch (IOException ex) {
            Logger.getLogger(Post.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Invalid";
    }
    
    public static String login(String auth)
    {
        try {
            HttpURLConnection connection = null;
            String targetURL = GORETAIL.baseurl + "/v2/user/auth";
            URL url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + auth);
            connection.setRequestProperty("platform", "web");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            //Send request
            DataOutputStream wr = new DataOutputStream (
            connection.getOutputStream());
            wr.writeBytes("");
            wr.close();
            //Get Response  
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            
            int code = connection.getResponseCode();
            if((code == 200) || (code == 201))
            {
                //System.out.println(response.toString());
                return response.toString();
            }else{
                return "Invalid";
            }
        } catch (IOException ex) {
            Logger.getLogger(Post.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Invalid";
    }
    
    public static String subscribe(String voucher)
    {
        try {
            HttpURLConnection connection = null;
            String targetURL = GORETAIL.baseurl + "/v2/business/" + DatabaseCheck.getBusId() + "/subscription";
            URL url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + GORETAIL.access_token);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("platform", "web");
            connection.setRequestProperty("id", DatabaseCheck.getBusId());
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            //Send request
            DataOutputStream wr = new DataOutputStream (
            connection.getOutputStream());
            wr.writeBytes("{\"voucher\": \"" + voucher + "\"}");
            wr.close();
            //Get Response  
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            int code = connection.getResponseCode();
            if((code == 200) || (code == 201) || (code == 202))
            {
                if(response.toString().contains("Invalid voucher"))
                    return "Invalid";
                else
                    return response.toString();
            }else{
                return "Invalid";
            }
        } catch (IOException ex) {
            Logger.getLogger(Post.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Invalid";
    }
    
    public static String getGoods()
    {
        try {
            URL oracle = new URL(GORETAIL.baseurl + "/v2/business/" + DatabaseCheck.getBusId() + "/products");
            URLConnection yc = oracle.openConnection();
            yc.setRequestProperty("Authorization", "Bearer " + GORETAIL.access_token);
            yc.setRequestProperty("Content-Type", "application/json");
            yc.setRequestProperty("platform", "web");
            yc.setRequestProperty("id", DatabaseCheck.getBusId());
            BufferedReader in = new BufferedReader(
		        new InputStreamReader(yc.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (IOException ex) {
            Logger.getLogger(Post.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Invalid";
    }

    public static String getServices()
    {
        try {
            URL oracle = new URL(GORETAIL.baseurl + "/v2/business/" + DatabaseCheck.getBusId() + "/services");
            URLConnection yc = oracle.openConnection();
            yc.setRequestProperty("Authorization", "Bearer " + GORETAIL.access_token);
            yc.setRequestProperty("Content-Type", "application/json");
            yc.setRequestProperty("platform", "web");
            yc.setRequestProperty("id", DatabaseCheck.getBusId());
            BufferedReader in = new BufferedReader(
		        new InputStreamReader(yc.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (IOException ex) {
            Logger.getLogger(Post.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Invalid";
    }
    
    public static String getOrders()
    {
        try {
            URL oracle = new URL(GORETAIL.baseurl + "/v2/business/" + DatabaseCheck.getBusId() + "/orders");
            URLConnection yc = oracle.openConnection();
            yc.setRequestProperty("Authorization", "Bearer " + GORETAIL.access_token);
            yc.setRequestProperty("Content-Type", "application/json");
            yc.setRequestProperty("platform", "web");
            yc.setRequestProperty("id", DatabaseCheck.getBusId());
            BufferedReader in = new BufferedReader(
		        new InputStreamReader(yc.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (IOException ex) {
            Logger.getLogger(Post.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Invalid";
    }
    
    public static String postSupplies(String orderid, String quantity, String supplierId)
    {
        try {
            HttpURLConnection connection = null;
            String targetURL = GORETAIL.baseurl + "/v2/business/" + DatabaseCheck.getBusId() + "/supplies";
            URL url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + GORETAIL.access_token);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("platform", "web");
            connection.setRequestProperty("id", DatabaseCheck.getBusId());
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            //Send request
            DataOutputStream wr = new DataOutputStream (
            connection.getOutputStream());
            wr.writeBytes("{\"orderId\": \"" + orderid + "\"," + "\"quantity\": " + quantity + "," + "\"supplierId\": \"" + supplierId + "\"}");
            wr.close();
            //Get Response  
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            int code = connection.getResponseCode();
            if((code == 200) || (code == 201) || (code == 202))
            {
                return response.toString();
            }else{
                return "Invalid";
            }
        } catch (IOException ex) {
            Logger.getLogger(Post.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Invalid";
    }
    
    public static String salesSync(String json)
    {
        try {
            HttpURLConnection connection = null;
            String targetURL = GORETAIL.baseurl + "/v2/business/" + DatabaseCheck.getBusId() + "/sales/sync";
            URL url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + GORETAIL.access_token);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("platform", "web");
            connection.setRequestProperty("id", DatabaseCheck.getBusId());
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            //Send request
            DataOutputStream wr = new DataOutputStream (
            connection.getOutputStream());
            wr.writeBytes(json);
            wr.close();
            //Get Response  
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            int code = connection.getResponseCode();
            if((code == 200) || (code == 201) || (code == 202))
            {
                return response.toString();
            }else{
                return "Invalid";
            }
        } catch (IOException ex) {
            Logger.getLogger(Post.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Invalid";
    }
    
}