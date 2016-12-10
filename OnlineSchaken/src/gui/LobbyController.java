/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Server.ClientApp;
import Shared.IGameLobby;
import database.Database;
import onlineschaken.*;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author redxice
 */
public class LobbyController implements Initializable
{

    private Database db = new Database();
    private IGameLobby lobby;
    private ClientApp client = new ClientApp();
    private ObservableList gameList = FXCollections.observableArrayList();
    private List<String> test = new ArrayList<String>();
    private Player player;
    private static final Logger LOGGER = Logger.getLogger(LobbyController.class.getName());
    @FXML
    private Button Btn_Join;
    @FXML
    private Button Btn_Profile;
    @FXML
    private Button Btn_AddFriend;
    @FXML
    private TextField Tb_Friend;
    @FXML
    private TextField Tb_GameName;
    @FXML
    private Button Btn_CreateGame;
    @FXML
    private ListView Lv_GameList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        if (client.GetGameLobbys() != null)
        {
            gameList.setAll(client.GetGameLobbys());
            Lv_GameList.setItems(gameList);
        }
    }

    @FXML
    private void HandleProfileBTN(ActionEvent event)
    {
        try
        {
            Stage CurrentStage = (Stage) Btn_Profile.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            ProfileController controller = fxmlLoader.<ProfileController>getController();
            controller.setPlayer(this.player);
            CurrentStage.close();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex)
        {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void joinGame(ActionEvent event)
    {
        String selectedLobby =(String)Lv_GameList.getSelectionModel().getSelectedItem();
        if (selectedLobby!=null)
        {
           IGameLobby lobby = client.GetGameLobby(selectedLobby);
        }
    }

    @FXML
    private void addFriend(ActionEvent event)
    {
        if (Tb_Friend.getText() != null)
        {
            db.init();
            db.addFriend(player.getUsername(), Tb_Friend.getText());
            db.closeConnection();
        }
    }

    @FXML
    private void HandleCreateGame(ActionEvent event)
    {
        try
        {
            if (!Tb_GameName.getText().isEmpty())
            {
                IGameLobby lobby = TryToCreateGameLobby(Tb_GameName.getText(), this.player);
                if (lobby != null)
                {
                    Stage CurrentStage = (Stage) Btn_Profile.getScene().getWindow();
                    CurrentStage.close();
                    Stage stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Gamelobby.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    GamelobbyController controller = fxmlLoader.<GamelobbyController>getController();
                    controller.createGameLobby(lobby);
                    Scene scene = new Scene(root, Color.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                }

            } else
            {
                JOptionPane.showMessageDialog(null, "Geen gamenaam ingevoerd");
            }
        } catch (IOException ex)
        {
            LOGGER.log(Level.FINE, ex.getMessage());
        }
        //db.insertLobby(player.getUsername(),Tb_GameName.getText());
    }

    public void setPlayer(Player p_player)
    {
        this.player = p_player;
    }

    public Player getPlayer()
    {
        return player;
    }

    public IGameLobby TryToCreateGameLobby(String lobbyName, Player p_player)
    {
        if (client.createGameLobby(lobbyName, p_player))
        {
            lobby = client.GetGameLobby(lobbyName);
        } else
        {
          JOptionPane.showMessageDialog(null, "Lobby naam moet uniek zijn");
        }
     return lobby;
    }

}
