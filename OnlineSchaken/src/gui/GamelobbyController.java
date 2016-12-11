/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Server.ClientApp;
import Shared.IGameLobby;
import Shared.IGameLobbyController;
import Shared.IrmiClient;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
import javafx.stage.Stage;
import onlineschaken.Chatline;
import onlineschaken.Gamelobby;
import onlineschaken.Player;

/**
 * FXML Controller class
 *
 * @author redxice
 */
public class GamelobbyController extends UnicastRemoteObject implements Initializable, IGameLobbyController
{

    private IGameLobby GameLobby = null;
    private String lobbyName;
    private Player LoggedInUser;
    private ClientApp client;
    private boolean player1Ready;
    private boolean player2Ready;
    private IrmiClient IClient;
    private ObservableList playerList = FXCollections.observableArrayList();
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

    public GamelobbyController() throws RemoteException
    {

    }

    @FXML
    public void HandleReadyBtn(ActionEvent event)
    {        
        try
        {
            if(GameLobby.GetPlayer1().getUsername().equals(LoggedInUser.getUsername()))
            {
                player1Ready = true;
            }
            else if(GameLobby.GetPlayer2().getUsername().equals(LoggedInUser.getUsername()))
            {
                player2Ready = true;
            }
            System.out.println("Local player ready player1 = " + player1Ready + " player2 = "+ player2Ready);
            try
            {    client.playerReady(true, lobbyName, LoggedInUser.getUsername());               
                if(player1Ready == true && player2Ready == true)
                {
                    Stage LoginStage = (Stage) Btn_Ready.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ingame.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    IngameController controller = fxmlLoader.<IngameController>getController();
                    controller.setClient(client);
                    controller.setIClient(IClient);
                    LoginStage.close();
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    controller.DrawBoard();
                }
            } catch (IOException ex)
            {
                Logger.getLogger(GamelobbyController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (RemoteException ex)
        {
            Logger.getLogger(GamelobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void HandleLeaveBtn(ActionEvent event)
    {
        try
        {
            if (GameLobby.leaveGameLobby(LoggedInUser))
            {
                if (GameLobby.GetPlayer1() == null && GameLobby.GetPlayer2() == null)
                {   System.out.println("Gandalf was here");
                    client.unBindLobby(lobbyName);
                    
                }
            }
            Stage CurrentStage = (Stage) Btn_Leave.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lobby.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            LobbyController controller = fxmlLoader.<LobbyController>getController();
            controller.setPlayer(this.LoggedInUser);
            controller.setClient(client);
            controller.setIClient(IClient);
            if (GameLobby.GetPlayer1() != null && GameLobby.GetPlayer2() != null){
            IClient.RefreshGameLobby();
            IClient.setGameLobbyController(null);
            }
            CurrentStage.close();
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
     * moet worden aangeroepen wanneer een player wilt joinen op een bestaande
     * GameLobby .
     */
    public void JoinGameLobby(IGameLobby lobby)
    {
        try
        {
            lobbyName = lobby.getName();
            GameLobby = lobby;
            LoggedInUser = lobby.GetPlayer2();
            this.GameLobby = lobby;
        } catch (RemoteException ex)
        {
            Logger.getLogger(GamelobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Deze methode moet worden aangeroepen voor dat je een gameLobby aanmaakt.
     * Hier wordt de IGameLobby vast gezet voor verder gebruik in deze classen
     * in de variabel GameLobby.
     *
     * @param p_GameLobby
     * @param p_player
     */
    public void createGameLobby(IGameLobby lobby)
    {
        try
        {
            lobbyName = lobby.getName();
            LoggedInUser = lobby.GetPlayer1();
            this.GameLobby = lobby;
        } catch (RemoteException ex)
        {
            Logger.getLogger(GamelobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    @FXML
    public void HandleSendBtn(ActionEvent event)
    {
        Chatline chatLine = new Chatline(LoggedInUser.getUsername(), Chatline_TxtField.getText());
        client.SendMessage(chatLine, lobbyName);
    }

    public void setClient(ClientApp client)
    {
        this.client = client;

    }

    public IrmiClient getIClient()
    {
        return IClient;
    }

    public void setIClient(IrmiClient IClient)
    {
        this.IClient = IClient;
        try
        {
            System.out.println("De GameLobby die mee wordt gegeven aan de IClient " + (IGameLobbyController) this);
            this.IClient.setGameLobbyController((IGameLobbyController) this);
        } catch (RemoteException ex)
        {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateChat() throws RemoteException
    {  
     final ObservableList chatList = FXCollections.observableArrayList();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            for (Chatline c : GameLobby.getChatLines())
                            {
                               chatList.add(c);
                            }
                            ChatBox.setItems(chatList);
                        } catch (RemoteException ex)
                        {
                            Logger.getLogger(GamelobbyController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        }).start();

    }

    @Override
    public void updatePlayerList() throws RemoteException
    {        
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                           playerList.setAll(GameLobby.GetPlayerNames());
                           SpelerBox.setItems(playerList);
                        } catch (RemoteException ex)
                        {
                            Logger.getLogger(GamelobbyController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public IGameLobby getIGameLobby() throws RemoteException
    {
      return this.GameLobby;
    }

    @Override
    public void ready() throws RemoteException {
        if(GameLobby.checkPlayer1Exists() && GameLobby.checkPlayer2Exists())
        {
        if(GameLobby.GetPlayer1().getUsername().equals(LoggedInUser.getUsername()))
            {
                player2Ready = true;
            }
            else if(GameLobby.GetPlayer2().getUsername().equals(LoggedInUser.getUsername()))
            {
                player1Ready = true;
            }
        }
        System.out.println("Remote player ready player1 = " + player1Ready + "player2 = " + player2Ready);
        if(player1Ready && player2Ready)
        {
        drawBoard();
        }
    }
    
    
    public void drawBoard()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {                        
            try
            {                
                if(player1Ready == true && player2Ready == true)
                {
                    System.out.println("Board tekenen probeersel");
                    Stage LoginStage = (Stage) Btn_Ready.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ingame.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    IngameController controller = fxmlLoader.<IngameController>getController();
                    controller.setClient(client);
                    controller.setIClient(IClient);
                    LoginStage.close();
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    controller.DrawBoard();
                }
            } catch (IOException ex)
            {
                Logger.getLogger(GamelobbyController.class.getName()).log(Level.SEVERE, null, ex);
            }
                    }
                });
            }
        }).start();
    }
}
