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

    private IGameLobby gameLobby = null;
    private String lobbyName;
    private Player logInUser;
    private ClientApp client;
    private boolean player1Ready;
    private boolean player2Ready;
    private IrmiClient iClient;
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

    /**
     *
     * @throws RemoteException
     */
    public GamelobbyController() throws RemoteException
    {

    }

    /**
     *
     * @param event
     */
    @FXML
    public void HandleReadyBtn(ActionEvent event)
    {
        try
        {
            if (gameLobby.GetPlayer1().getUsername().equals(getLogInUser().getUsername()))
            {
                player1Ready = true;
                Chatline chatLine = new Chatline("--System--","Player1 is ready"); 
                client.SendMessage(chatLine, lobbyName);
            } else if (gameLobby.GetPlayer2().getUsername().equals(getLogInUser().getUsername()))
            {
                player2Ready = true;
                Chatline chatLine = new Chatline("--System--","Player2 is ready"); 
                client.SendMessage(chatLine, lobbyName);
            }            
            try
            {
                client.playerReady(true, lobbyName, getLogInUser().getUsername());
                if (player1Ready == true && player2Ready == true)
                {
                    Stage LoginStage = (Stage) Btn_Ready.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ingame.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    IngameController controller = fxmlLoader.<IngameController>getController();
                    controller.setLoggedInUser(this.logInUser);
                    controller.setClient(client);
                    controller.setIClient(iClient);
                    controller.setPlayer1(gameLobby.GetPlayer1().getUsername());
                    controller.setPlayer2(gameLobby.GetPlayer2().getUsername());
                    LoginStage.close();
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    Game game = new Game(gameLobby.GetPlayer1(), gameLobby.GetPlayer2(),this.iClient,controller);
                    controller.DrawBoard(game,gameLobby.getSpectators(),true);
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

    /**
     *
     * @param event
     */
    @FXML
    public void HandleLeaveBtn(ActionEvent event)
    {
        try
        {
            chatBerichtLeave();
            if (gameLobby.leaveGameLobby(getLogInUser()))
            {                
                if (gameLobby.GetPlayer1() == null && gameLobby.GetPlayer2() == null)
                {
                    client.unBindLobby(lobbyName);
                } else //if (gameLobby.GetPlayer1() == null || gameLobby.GetPlayer2() == null)
                {
                    iClient.RefreshGameLobby();
                }
            }
            Stage CurrentStage = (Stage) Btn_Leave.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lobby.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            LobbyController controller = fxmlLoader.<LobbyController>getController();
            controller.setPlayer(this.getLogInUser());
            controller.setClient(client);
            controller.setiClient(iClient);

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
     * send message to other player that this player has left
     */
    public void chatBerichtLeave()
    {
        
        try {
            if(getLogInUser().getUsername().equals(gameLobby.GetPlayer1().getUsername()))
            {
                Chatline chatLine = new Chatline("--System--","Player1 heeft de gamelobby verlaten");
                client.SendMessage(chatLine, lobbyName);
            }
            else if(getLogInUser().getUsername().equals(gameLobby.GetPlayer2().getUsername()))
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
 gameLobby .
     * @param lobby
     * @param player
     */
    public void JoinGameLobby(IGameLobby lobby,Player player)
    {
        try
        {
            lobbyName = lobby.getName();
            gameLobby = lobby;
            logInUser = player;
            this.gameLobby = lobby;
        } catch (RemoteException ex)
        {
            Logger.getLogger(GamelobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Deze methode moet worden aangeroepen voor dat je een gameLobby aanmaakt.
     * Hier wordt de IGameLobby vast gezet voor verder gebruik in deze classen
 in de variabel gameLobby.
     *
     * @param lobby
     */
    public void createGameLobby(IGameLobby lobby)
    {
        try
        {
            lobbyName = lobby.getName();
            logInUser = lobby.GetPlayer1();
            this.gameLobby = lobby;
        } catch (RemoteException ex)
        {
            Logger.getLogger(GamelobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    /**
     *
     * @param event
     */
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
     * @return
     */
    public IrmiClient getiClient()
    {
        return iClient;
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
            this.iClient.setGameLobbyController((IGameLobbyController) this);
        } catch (RemoteException ex)
        {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @throws RemoteException
     */
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
                            for (Chatline c : gameLobby.getChatLines())
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

    /**
     *
     * @throws RemoteException
     */
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
                            playerList.setAll(gameLobby.GetPlayerNames());
                            SpelerBox.setItems(playerList);
                            spectatorList.setAll(gameLobby.getSpectators());
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

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public IGameLobby getIGameLobby() throws RemoteException
    {
        return this.gameLobby;
    }

    /**
     *
     * @param userName
     * @throws RemoteException
     */
    @Override
    public void ready(String userName) throws RemoteException
    {
        if (gameLobby.checkPlayer1Exists() && gameLobby.checkPlayer2Exists())
        {
            if (gameLobby.GetPlayer1().getUsername().equals(userName))
            {
                player1Ready = true;
            } else if (gameLobby.GetPlayer2().getUsername().equals(userName))
            {
                player2Ready = true;
            }
        }
        if (player1Ready && player2Ready)
        {
            drawBoard();
        }
    }

    /**
     * method to fill the board with sections
     */
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
                                controller.setIClient(iClient);
                                controller.setPlayer1(gameLobby.GetPlayer1().getUsername());
                                controller.setPlayer2(gameLobby.GetPlayer2().getUsername());
                                controller.setLoggedInUser(logInUser);
                                LoginStage.close();
                                Stage stage = new Stage();
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                                Game game = new Game(gameLobby.GetPlayer1(), gameLobby.GetPlayer2(),iClient,controller);
                                controller.DrawBoard(game,gameLobby.getSpectators(),true);
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

    /**
     * @return the logInUser
     */
    @Override
    public Player getLogInUser()
    {
        return logInUser;
    }
}
