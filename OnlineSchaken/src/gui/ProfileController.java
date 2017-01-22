/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Server.ClientApp;
import Shared.IrmiClient;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import onlineschaken.Game;
import onlineschaken.Player;

/**
 * FXML Controller class
 *
 * @author redxice
 */
public class ProfileController extends UnicastRemoteObject implements Initializable
{

    private static final Logger LOGGER = Logger.getLogger(LobbyController.class.getName());
    private ClientApp client;
    private IrmiClient iClient;
    private final ObservableList activeGames = FXCollections.observableArrayList();
    private final ObservableList gameHistory = FXCollections.observableArrayList();
    @FXML
    private Button Btn_Restart;
    @FXML
    private Button Btn_Back;
    @FXML
    private Label lbNaam;
    @FXML
    private ListView Lv_ActiveGames;
    @FXML
    private ListView Lv_GameHistory;
    private Player player;

    /**
     *
     * @throws RemoteException
     */
    public ProfileController() throws RemoteException
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
    private void HandleBack(ActionEvent event)
    {
        try
        {
            Stage CurrentStage = (Stage) Btn_Back.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Lobby.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            LobbyController controller = fxmlLoader.<LobbyController>getController();
            controller.setPlayer(player);
            controller.setClient(client);
            controller.setiClient(iClient);
            CurrentStage.close();
            Stage stage = new Stage();
            Scene scene = new Scene(root, Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex)
        {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void HandleRestartGame(ActionEvent event)
    {

        Game SelectedGame = (Game) this.Lv_ActiveGames.getSelectionModel().getSelectedItem();
        try
        {

            if (iClient.RestartGame(SelectedGame))
            {
                Stage LoginStage = (Stage) Btn_Restart.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ingame.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                IngameController controller = fxmlLoader.<IngameController>getController();
                controller.setLoggedInUser(client.getPlayer());
                controller.setClient(client);
                controller.setIClient(iClient);
                controller.setPlayer1(SelectedGame.getPlayer1().getUsername());
                controller.setPlayer2(SelectedGame.getPlayer2().getUsername());
                LoginStage.close();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                controller.DrawBoard(SelectedGame, SelectedGame.getSpectators(), false);

            }
        } catch (RemoteException ex)
        {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * show all new games
     */
    public void UpdateGames()
    {
        Platform.runLater(() ->
        {
            activeGames.setAll(player.getActiveGames());
            gameHistory.setAll(player.getHistory());
            Lv_ActiveGames.setItems(activeGames);
            gameHistory.setAll(player.getHistory());
        });
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
     * @param player
     */
    public void setPlayer(Player player)
    {
        this.player = player;
        lbNaam.setText(player.getUsername());
    }

    /**
     *
     * @param client
     */
    public void setClient(ClientApp client)
    {
        this.client = client;
    }

    /**
     *
     * @param client
     */
    public void setiClient(IrmiClient client)
    {
        this.iClient = client;
    }

}
