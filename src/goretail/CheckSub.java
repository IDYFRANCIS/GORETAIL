/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author WISDOM IBANGA
 */
public class CheckSub {
    static String dir = "C:\\sys";
    
    static boolean checkDirectory(String dir)
    {
        File f = new File(dir);
        return (f.exists() && f.isDirectory());
    }
    
    static boolean checkFile(String dir)
    {
        File f = new File(dir + "\\windows.ini");
        return (f.exists() && !f.isDirectory());
    }
    
    static boolean readAndDecryptAndParse()
    {
        try {
            byte[] decoded = Base64.getDecoder().decode(new String(Files.readAllBytes(Paths.get(dir + "\\windows.ini"))));
            String deStr = new String(decoded, StandardCharsets.UTF_8);
            if(deStr.length() < 20)
                return false;
            JSONObject json = new JSONObject(deStr);
            JSONObject jsondata = json.getJSONObject("data");
            Long dateexp = jsondata.getLong("expirationDate");
            Date d = new Date(FXMLHOMEController.USEtime);
            Date d2 = new Date(dateexp);
            int diffInDays = (int)( (d2.getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
            //System.out.println("SUBSCRIPTION DIFFERENCE: " + diffInDays);
            return diffInDays >= 1;
        } catch (IOException ex) {
            Logger.getLogger(CheckSub.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    static boolean finalCheck()
    {
        if(checkDirectory(dir))
        {
            System.out.println("Check 1a");
            if(checkFile(dir))
            {
                return readAndDecryptAndParse();
            }else
            {
                return false;
            }
        }else
        {
            return false;
        }
    }
}
