/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goretail;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

/**
 *
 * @author Aniwange.TA
 */
public class ErrorHelper {
   
     
    public static void showEditTextError(
     JFXTextField name, String message){
             name.setFocusTraversable(false);
             name.setStyle("-fx-prompt-text-fill: red; -fx-font-size: 16px;");
             name.setPromptText(message);
             
             
             
    }
     public static void showJFXTextFieldError(
     JFXTextField name, String message){
             name.setFocusTraversable(false);
             name.setStyle("-fx-prompt-text-fill: red; -fx-font-size: 16px;");
             name.setPromptText(message);        
    }
     public static void showJFXPasswordFielddError(
     JFXPasswordField name, String message){
             name.setFocusTraversable(false);
             name.setStyle("-fx-prompt-text-fill: red; -fx-font-size: 16px;");
             name.setPromptText(message);
              
             
    }
     //JFXPasswordField
    public static void showLabelError(
     Label name, String message){
             name.setText(message);
             //name.setUnFocusColor();
             
    }
    
    public static void showLoadingIndicator(ProgressIndicator  indicator){
        indicator.setRotate(5.00);
        indicator.setVisible(true);
    }
    
}
