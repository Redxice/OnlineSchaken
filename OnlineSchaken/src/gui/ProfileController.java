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
    private IrmiClient IClient;
    private ObservableList ActiveGames = FXCollections.observableArrayList();
    private ObservableList GameHistory = FXCollections.observableArrayList();
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

    public ProfileController() throws RemoteException
    {

    }

    /**
     * Initializes the controller class.
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
            controller.setIClient(IClient);
            CurrentStage.close();
            Stage stage = new Stage();
            Scene scene = new Scene(root, Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex)
        {
            LOGGER.log(Level.FINE, ex.getMessage());
        }

    }

    @FXML
    private void HandleRestartGame(ActionEvent event)
    {

        Game SelectedGame = (Game) this.Lv_ActiveGames.getSelectionModel().getSelectedItem();
        try
        {

            if (IClient.RestartGame(SelectedGame))
            {
                Stage LoginStage = (Stage) Btn_Restart.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ingame.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                IngameController controller = fxmlLoader.<IngameController>getController();
                controller.setLoggedInUser(client.getPlayer());
                controller.setClient(client);
                controller.setIClient(IClient);
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

    public void UpdateGames()
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                ActiveGames.setAll(player.getActiveGames());
                GameHistory.setAll(player.getHistory());
                Lv_ActiveGames.setItems(ActiveGames);
                GameHistory.setAll(player.getHistory());

            }
        });
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
        lbNaam.setText(player.getUsername());
    }

    public void setClient(ClientApp client)
    {
        this.client = client;
    }

    public void setIClient(IrmiClient client)
    {
        this.IClient = client;
    }

}
