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
import onlineschaken.Chatfilter;
import onlineschaken.Chatline;
import onlineschaken.Game;
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
    private ObservableList spectatorList = FXCollections.observableArrayList();
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
    private ListView SpectatorListView;
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
            if (GameLobby.GetPlayer1().getUsername().equals(getLoggedInUser().getUsername()))
            {
                player1Ready = true;
                Chatline chatLine = new Chatline("--System--","Player1 is ready"); 
                client.SendMessage(chatLine, lobbyName);
            } else if (GameLobby.GetPlayer2().getUsername().equals(getLoggedInUser().getUsername()))
            {
                player2Ready = true;
                Chatline chatLine = new Chatline("--System--","Player2 is ready"); 
                client.SendMessage(chatLine, lobbyName);
            }            
            try
            {
                client.playerReady(true, lobbyName, getLoggedInUser().getUsername());
                if (player1Ready == true && player2Ready == true)
                {
                    Stage LoginStage = (Stage) Btn_Ready.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ingame.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    IngameController controller = fxmlLoader.<IngameController>getController();
                    controller.setLoggedInUser(this.LoggedInUser);
                    controller.setClient(client);
                    controller.setIClient(IClient);
                    controller.setPlayer1(GameLobby.GetPlayer1().getUsername());
                    controller.setPlayer2(GameLobby.GetPlayer2().getUsername());
                    LoginStage.close();
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    Game game = new Game(GameLobby.GetPlayer1(), GameLobby.GetPlayer2(),this.IClient,controller);
                    controller.DrawBoard(game,GameLobby.getSpectators(),true);
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
            chatBerichtLeave();
            if (GameLobby.leaveGameLobby(getLoggedInUser()))
            {                
                if (GameLobby.GetPlayer1() == null && GameLobby.GetPlayer2() == null)
                {
                    client.unBindLobby(lobbyName);
                } else //if (GameLobby.GetPlayer1() == null || GameLobby.GetPlayer2() == null)
                {
                    IClient.RefreshGameLobby();
                }
            }
            Stage CurrentStage = (Stage) Btn_Leave.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lobby.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            LobbyController controller = fxmlLoader.<LobbyController>getController();
            controller.setPlayer(this.getLoggedInUser());
            controller.setClient(client);
            controller.setIClient(IClient);

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
    
    public void chatBerichtLeave()
    {
        
        try {
            if(getLoggedInUser().getUsername().equals(GameLobby.GetPlayer1().getUsername()))
            {
                Chatline chatLine = new Chatline("--System--","Player1 heeft de gamelobby verlaten");
                client.SendMessage(chatLine, lobbyName);
            }
            else if(getLoggedInUser().getUsername().equals(GameLobby.GetPlayer2().getUsername()))
            {
                Chatline chatLine = new Chatline("--System--","Player2 heeft de gamelobby verlaten");
                client.SendMessage(chatLine, lobbyName);
            }
            else
            {
                Chatline chatLine = new Chatline("--System--","Een spectator heeft de gamelobby verlaten");
                client.SendMessage(chatLine, lobbyName);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GamelobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * moet worden aangeroepen wanneer een player wilt joinen op een bestaande
     * GameLobby .
     */
    public void JoinGameLobby(IGameLobby lobby,Player player)
    {
        try
        {
            lobbyName = lobby.getName();
            GameLobby = lobby;
            LoggedInUser = player;
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
        Chatfilter filter = new Chatfilter();
        String message = Chatline_TxtField.getText();
        message = filter.checkMessage(message);
        if (message != null)
        {
            Chatline chatLine = new Chatline(client.getUserName(), message);
            try
            {
                client.sendInGameMessage(chatLine);
            } catch (RemoteException ex)
            {
                Logger.getLogger(IngameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
                            spectatorList.setAll(GameLobby.getSpectators());
                            SpectatorListView.setItems(spectatorList);
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
    public void ready(String userName) throws RemoteException
    {
        if (GameLobby.checkPlayer1Exists() && GameLobby.checkPlayer2Exists())
        {
            if (GameLobby.GetPlayer1().getUsername().equals(userName))
            {
                player1Ready = true;
            } else if (GameLobby.GetPlayer2().getUsername().equals(userName))
            {
                player2Ready = true;
            }
        }
        if (player1Ready && player2Ready)
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
                            if (player1Ready == true && player2Ready == true)
                            {
                                Stage LoginStage = (Stage) Btn_Ready.getScene().getWindow();
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ingame.fxml"));
                                Parent root = (Parent) fxmlLoader.load();
                                IngameController controller = fxmlLoader.<IngameController>getController();
                                controller.setClient(client);
                                controller.setIClient(IClient);
                                controller.setPlayer1(GameLobby.GetPlayer1().getUsername());
                                controller.setPlayer2(GameLobby.GetPlayer2().getUsername());
                                controller.setLoggedInUser(LoggedInUser);
                                LoginStage.close();
                                Stage stage = new Stage();
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                                Game game = new Game(GameLobby.GetPlayer1(), GameLobby.GetPlayer2(),IClient,controller);
                                controller.DrawBoard(game,GameLobby.getSpectators(),true);
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

    public void setPlayerAndTurn()
    {

    }

    /**
     * @return the LoggedInUser
     */
    @Override
    public Player getLoggedInUser()
    {
        return LoggedInUser;
    }
}
