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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import onlineschaken.Gamelobby;
import onlineschaken.Player;

/**
 * FXML Controller class
 *
 * @author redxice
 */
public class GamelobbyController implements Initializable
{
    private Gamelobby gameLobby;
    
    private Player LoggedInUser;
    @FXML
    private Button Btn_Send;
    @FXML
    private Button Btn_Ready;
    @FXML 
    private Button Btn_Leave;
    @FXML
    private Button Btn_FriendList;
    @FXML
    private ListView ChatBox;
    @FXML 
    private ListView SpelerBox;
    @FXML 
    private ListView GameInstellingBox;
    @FXML
    private TextField Chatline_TxtField;
   
    
    @FXML
    public void HandleLeaveBtn(ActionEvent event){
        try
        {
            Stage LoginStage = (Stage) Btn_Leave.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lobby.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            LobbyController controller= fxmlLoader.<LobbyController>getController();
            controller.setPlayer(this.LoggedInUser);
            LoginStage.close();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex)
        {
            Logger.getLogger(GamelobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   /**
    * moet worden aangeroepen wanneer een player wilt joinen
    * op een bestaande GameLobby .
    */
    public void JoinGameLobby(Player p_player){
      gameLobby.setPlayer2(p_player);
    }
    
    /**
     * Deze methode moet worden aangeroepen voor dat je een gameLobby aanmaakt.
     * @param p_GameLobby 
     * @param p_player 
     */
    public void createGameLobby(Gamelobby p_GameLobby,Player p_player){
       this.gameLobby = p_GameLobby;
       this.LoggedInUser = p_player;
       
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
}
