/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Server.ClientApp;
import Shared.IGameLobby;
import Shared.ILobbyController;
import Shared.IrmiClient;
import onlineschaken.*;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
public class LobbyController extends UnicastRemoteObject implements Initializable, ILobbyController
{

    private IGameLobby lobby;
    private ClientApp client;
    private final ObservableList gameList = FXCollections.observableArrayList();
    private Player player;
    private IrmiClient iClient;
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
     *
     * @throws RemoteException
     */
    public LobbyController() throws RemoteException
    {

    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

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
            controller.setClient(client);
            controller.setiClient(iClient);
            CurrentStage.close();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            controller.UpdateGames();
        } catch (IOException ex)
        {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void joinGame(ActionEvent event)
    {
        String selectedLobby = (String) Lv_GameList.getSelectionModel().getSelectedItem();
        if (selectedLobby != null)
        {
            try
            {
                this.player.setColor("black");
                IGameLobby lobby = client.GetGameLobby(selectedLobby);
                if (lobby.joinGameLobby(player))
                {
                    Stage CurrentStage = (Stage) Btn_Profile.getScene().getWindow();
                    CurrentStage.close();
                    Stage stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Gamelobby.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    GamelobbyController controller = fxmlLoader.<GamelobbyController>getController();
                    controller.JoinGameLobby(lobby, player);
                    controller.setiClient(iClient);
                    controller.setClient(client);
                    iClient.updatePlayerList();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else
                {
                    JOptionPane.showMessageDialog(null, "Failed to join the lobby");
                }

            } catch (RemoteException ex)
            {
                Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex)
            {
                Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else
        {
            JOptionPane.showMessageDialog(null, "Select a Lobby");
        }
    }

    @FXML
    private void addFriend(ActionEvent event)
    {
        if (Tb_Friend.getText() != null)
        {
            try
            {
                iClient.addFriend(player.getUsername(), Tb_Friend.getText());
            } catch (RemoteException ex)
            {
                Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void HandleCreateGame(ActionEvent event)
    {
        try
        {
            if (!Tb_GameName.getText().isEmpty())
            {
                this.player.setColor("white");
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
                    controller.setClient(client);
                    controller.setiClient(iClient);
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
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param p_player
     */
    public void setPlayer(Player p_player)
    {
        this.player = p_player;

    }

    /**
     *
     * @return
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     *
     * @param lobbyName
     * @param p_player
     * @return
     */
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

    @Override
    public void UpdateGameLobbys() throws RemoteException
    {

        new Thread(() ->
        {
            Platform.runLater(() ->
            {
                if (!gameList.isEmpty())
                {
                    gameList.clear();
                }
                gameList.setAll(client.GetGameLobbys());
                Lv_GameList.setItems(gameList);
            });
        }).start();
    }

    /**
     *
     * @param client
     */
    public void setClient(ClientApp client)
    {
        this.client = client;
        if (client.GetGameLobbys() != null)
        {
            gameList.setAll(client.GetGameLobbys());
            Lv_GameList.setItems(gameList);

        }

    }

    /**
     *
     * @param iClient
     */
    public void setiClient(IrmiClient iClient)
    {
        this.iClient = iClient;
        try
        {
            iClient.setLobbyController((ILobbyController) this);
            player.setGames(this.iClient.GetGames(player.getUsername()));
        } catch (RemoteException ex)
        {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void RestartGame(Game game) throws RemoteException
    {
        Platform.runLater(() -> 
                {
                    try
                    {
                        Stage LoginStage = (Stage) Btn_Join.getScene().getWindow();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ingame.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        IngameController controller = fxmlLoader.<IngameController>getController();
                        controller.setClient(client);
                        controller.setIClient(iClient);
                        controller.setPlayer1(game.getPlayer1().getUsername());
                        controller.setPlayer2(game.getPlayer2().getUsername());
                        controller.setLoggedInUser(client.getPlayer());
                        LoginStage.close();
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        controller.DrawBoard(game, game.getSpectators(), false);
                    } catch (IOException ex)
                    {
                        Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
                    }

        });
    }
}