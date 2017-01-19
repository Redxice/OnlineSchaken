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
public class ProfileController implements Initializable
{

    private static final Logger LOGGER = Logger.getLogger(LobbyController.class.getName());
    private ClientApp client;
    private IrmiClient IClient;
    private ObservableList ActiveGames = FXCollections.observableArrayList();
    private ObservableList GameHistory = FXCollections.observableArrayList();
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
