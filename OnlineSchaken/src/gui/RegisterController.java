/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author redxice
 */
public class RegisterController implements Initializable
{
private static final Logger LOGGER = Logger.getLogger( RegisterController.class.getName() );
    /**
     * Initializes the controller class.
     */
    @FXML
    private Button Btn_Register;
    @FXML
    private TextField TxtField_Username;
    @FXML
    private TextField TxtField_Email;
    @FXML
    private PasswordField TxtField_Password;
    @FXML
    private PasswordField TxtField_RePassword;
    
    
    @FXML
    private void RegisterAccount(ActionEvent event)
    {
        try
        {  
            Stage CurrentStage = (Stage) Btn_Register.getScene().getWindow();
            CurrentStage.close();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root,Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();      
        } catch (IOException ex)
        {
           LOGGER.log(Level.FINE,ex.getMessage());
        }
    } 
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
}
