/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Server.ClientApp;
import Shared.IrmiClient;
import Shared.IrmiServer;
import database.Database;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import onlineschaken.Player;

/**
 * FXML Controller class
 *
 * @author redxice
 */
public class LoginController implements Initializable
{

    private Player player;
    private ClientApp client = new ClientApp();
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    /**
     * Initializes the controller class.
     */
    @FXML
    private Button BtnRegister;
    @FXML
    private Button BtnLogin;
    @FXML
    private TextField TxtField_Username;
    @FXML
    private PasswordField TxtField_Password;
    @FXML
    private Label Warning_Login;
    private IrmiClient IClient;

    @FXML
    private void HandleLoginBTN(ActionEvent event)
    {
        Warning_Login.setText(null);
        if (CheckIfValidUser())//(true))
        {
            try
            {           
                client.setUserName(TxtField_Username.getText());
                player = new Player((String)TxtField_Username.getText(),(String)TxtField_Password.getText(),10);
                Stage LoginStage = (Stage) BtnLogin.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lobby.fxml"));

                Parent root = (Parent) fxmlLoader.load();
                LobbyController controller = fxmlLoader.<LobbyController>getController();
                controller.setPlayer(this.player);
                controller.setClient(client);
                controller.setIClient((IrmiClient)client);
                LoginStage.close();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex)
            {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else
        {
            Warning_Login.setText("Invalid Login");
        }

    }

    @FXML
    private void HandleRegisterBTN(ActionEvent event)
    {
        try
        {
            Stage LoginStage = (Stage) BtnRegister.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Register.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            RegisterController controller = fxmlLoader.<RegisterController>getController();
            controller.setClient(client);
            controller.setIClient((IrmiClient)client);
            LoginStage.close();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex)
        {
            LOGGER.log(Level.FINE, ex.getMessage());
        }
    }

    /**
     * haalt een user uit database op om te bekijken
     *dit zou ook op de server kunnen gebeuren.
     * @return
     */

    private boolean CheckIfValidUser()
    {
        
        try
        {
            player = ((IrmiClient)client).selectPlayer(TxtField_Username.getText());
        } catch (RemoteException ex)
        {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (player != null)
        {
            if (player.getUsername().equals(TxtField_Username.getText()))
            {
                if (CheckUserPassword(player))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean CheckUserPassword(Player player)
    {

        if (!TxtField_Password.getText().isEmpty())
        {
            return player.getPassword().equals(TxtField_Password.getText());
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {   
        
         
        
    }
         
   public void setClient(ClientApp client){
       this.client = client;
   }
   public void setIClient(IrmiClient client){
       this.IClient = client;
   }
}
