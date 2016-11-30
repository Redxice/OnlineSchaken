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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author redxice
 */
public class ProfileController implements Initializable
{
    private static final Logger LOGGER = Logger.getLogger( LobbyController.class.getName() );
    @FXML
    private Button Btn_Back;

    private int i = 5;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
    @FXML
    private void HandleBack(ActionEvent event)
    {      
        System.out.println(i);
        try
        {  
            Stage CurrentStage = (Stage) Btn_Back.getScene().getWindow();
            CurrentStage.close();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("lobby.fxml"));
            Scene scene = new Scene(root,Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();      
        } catch (IOException ex)
        {
           LOGGER.log(Level.FINE,ex.getMessage());
        }
        
    } 

    public void setI(int i)
    {
        this.i = i;
    }
    
}
