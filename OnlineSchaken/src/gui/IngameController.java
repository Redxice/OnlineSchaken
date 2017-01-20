/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Server.ClientApp;
import Shared.IinGameController;
import Shared.IrmiClient;
import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import onlineschaken.Bishop;
import onlineschaken.Chatline;

import onlineschaken.*;

/**
 * FXML Controller class
 *
 * @author redxice
 */
public class IngameController extends UnicastRemoteObject implements Initializable, IinGameController
{

    private boolean MyRealTurn;
    private boolean isMyTurn;
    private boolean IsPromoting;
    private boolean IsWaitingForPromotion;
    private ClientApp client;
    private IrmiClient Iclient;
    private ArrayList<String> listMoveHistory = new ArrayList<>();
    private ObservableList moveHistory = FXCollections.observableArrayList();
    private ArrayList<Chatline> chatlines = new ArrayList<>();
    private ObservableList chatList = FXCollections.observableArrayList();
    @FXML
    private Button Btn_Surrender;
    @FXML
    private Button Btn_Draw;
    @FXML
    private TextField Txt_Message;
    @FXML
    private Button Btn_Send;
    @FXML
    private ListView Chatbox;
    @FXML
    private SubScene GameBoard;
    @FXML
    private ListView MoveHistory;
    @FXML
    private Button Btn_Leave;
    @FXML
    private TextField timerWhite;
    @FXML
    private TextField timerBlack;
    private List<Player> spectators = new ArrayList<>();
    private Game game;
    private String player1;
    private String player2;
    private boolean white;
    private Point localStart;
    private Point localEnd;
    private boolean spectator;
    private Player LoggedInUser;

    public IngameController() throws RemoteException
    {

    }

    /**
     * moet nog verder worden uitgewerkt. De players moeten worden geadd in de
     * game.
     */
    public void DrawBoard(Game game, List<Player> spectators, boolean newGame) throws RemoteException
    {
        Group root = new Group();
        client.setGame(this);
        System.out.println(client.GetGameController());
        
        if (newGame)
        {
            this.game = game;
        } else
        {
            this.game = new Game(game,this.Iclient,this);
        }
        GameBoard.setRoot(root);
        game.getBoard().createContent();
        game.setPieces();
        game.getBoard().createContent2();
        game.setSpectators(spectators);
        this.spectators = spectators;
        root.getChildren().add(game.getBoard().getRoot());
        if (Iclient.getUserName().equals(game.getPlayer1().getUsername()))
        {
            this.isMyTurn = true;
            this.MyRealTurn = true;
            white = true;
            spectator = false;
        } else if (Iclient.getUserName().equals(game.getPlayer2().getUsername()))
        {
            this.isMyTurn = false;
            this.MyRealTurn = false;
            white = false;
            spectator = false;
        } else
        {
            spectator = true;
        }
        runTimer();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }

    @Override
    public void setIClient(IrmiClient iClient) throws RemoteException
    {
        this.Iclient = iClient;
        this.Iclient.setIinGameController(this);

    }

    @Override
    public void setClient(ClientApp client) throws RemoteException
    {
        this.client = client;
    }

    @Override
    public IrmiClient getClient() throws RemoteException
    {
        return (IrmiClient) client;
    }

    @Override
    public IrmiClient getIClient() throws RemoteException
    {
        return Iclient;
    }

    @Override
    public void move(Point section1, Point section2, double time) throws RemoteException

    {
        System.out.println("Start move methode");
        int xValue = (int) section1.getX();
        int yValue = (int) section1.getY();
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
                        isMyTurn = true;
                        if (game.getBoard().getSections(xValue, yValue).getPiece() != null)
                        {
                            Piece piece = game.getBoard().getSections(xValue, yValue).getPiece();
                            if (game.getBoard().getSections(xValue, yValue).getPiece().move(game.getBoard().getSections((int) section2.getX(), (int) section2.getY())))
                            {
                                try
                                {
                                    addToMoveHistory(section1, section2, piece);
                                } catch (RemoteException ex)
                                {
                                    Logger.getLogger(IngameController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if (!IsWaitingForPromotion)
                                {
                                    isMyTurn = true;
                                }
                            } else
                            {

                            }
                            game.draw();
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public String getPlayer1() throws RemoteException
    {
        return player1;
    }

    public void setPlayer1(String player1)
    {
        this.player1 = player1;
    }

    @Override
    public String getPlayer2() throws RemoteException
    {
        return player2;
    }

    public void setPlayer2(String player2)
    {
        this.player2 = player2;
    }

    @Override
    public boolean getMyTurn() throws RemoteException
    {
        return this.isMyTurn;
    }

    @Override
    public void setMyturn() throws RemoteException
    {
        this.isMyTurn = false;
    }

    @FXML
    public void sendMessage(ActionEvent event)
    {
        Chatfilter filter = new Chatfilter();
        String message = Txt_Message.getText();
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

    @FXML
    public void handleLeaveBtn(ActionEvent event)
    {
        try
        {
            System.out.println(this.game);
            Iclient.SaveGame(this.game);
            System.out.println("Hij heeft een game gesaved");
            leaveGame();
        } catch (RemoteException ex)
        {
            Logger.getLogger(IngameController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void leaveGame() throws RemoteException
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Stage CurrentStage = (Stage) Btn_Leave.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lobby.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    LobbyController controller = fxmlLoader.<LobbyController>getController();
                    controller.setPlayer(LoggedInUser);
                    System.out.println("LoggedinUser "+LoggedInUser);
                    controller.setClient(client);
                    controller.setIClient(Iclient);
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
        });

    }

    @Override
    public void updateChat(Chatline message) throws RemoteException
    {
        this.chatlines.add(message);
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
                        chatList.setAll(chatlines);
                        Chatbox.setItems(chatList);

                        chatList.setAll(chatlines);
                        Chatbox.setItems(chatList);
                        Txt_Message.setText("");

                    }
                });
            }
        }).start();
    }

    @Override
    public boolean isWhite() throws RemoteException
    {
        return white;
    }

    public boolean isSpectator() throws RemoteException
    {
        return spectator;
    }

    @Override
    public boolean getRealTurn() throws RemoteException
    {
        return this.MyRealTurn;
    }

    @Override
    public void SetRealTurn(boolean MyRealTurn) throws RemoteException
    {
        this.MyRealTurn = MyRealTurn;
    }

    @Override
    public ArrayList<Point> getLocalLastMove() throws RemoteException
    {
        ArrayList<Point> list = new ArrayList<Point>();
        list.add(localStart);
        list.add(localEnd);
        return list;
    }

    @Override
    public void setLocalLastMove(Point p1, Point p2) throws RemoteException
    {
        localStart = p1;
        localEnd = p2;
    }

    @Override
    public void PromotePawn(Piece piece, Pawn pawn) throws RemoteException
    {
        this.game.PromotePawn(piece, pawn);
        this.IsWaitingForPromotion = false;
    }

    public void runTimer()
    {
        Timer timer = new Timer();
        timer.schedule(new TurnTimer(this, client), 0, 5000);
    }

    @Override
    public void setisPromoting(boolean bool) throws RemoteException
    {
        this.IsPromoting = bool;
        if (bool)
        {
            Iclient.isPromoting();
        }
    }

    @Override
    public boolean isPromoting() throws RemoteException
    {
        return IsPromoting;
    }

    @Override
    public boolean isIsWaitingForPromotion() throws RemoteException
    {
        return IsWaitingForPromotion;
    }

    @Override
    public void setIsWaitingForPromotion(boolean bool) throws RemoteException
    {
        this.IsWaitingForPromotion = bool;
    }

    @Override
    public void addToMoveHistory(Point prev, Point current, Piece piece) throws RemoteException
    {
        String type = null;
        if (piece instanceof Pawn)
        {
            type = "Pawn";
        } else if (piece instanceof Bishop)
        {
            type = "Bishop";
        } else if (piece instanceof King)
        {
            type = "King";
        } else if (piece instanceof Knight)
        {
            type = "Knight";
        } else if (piece instanceof Queen)
        {
            type = "Queen";
        } else if (piece instanceof Rook)
        {
            type = "Rook";
        }
        String move = "Piece : " + type + " From :" + getCharForNumber((int) prev.getX()) + prev.y + " To :" + getCharForNumber((int) current.getX()) + current.y;
        this.listMoveHistory.add(move);
        Platform.runLater(() -> addTheListToView());
    }

    private String getCharForNumber(int i)
    {
        i++;
        return i > 0 && i < 27 ? String.valueOf((char) (i + 64)) : null;
    }

    /**
     * dit moet worden uitgevoert worden in een runlater
     */
    private void addTheListToView()
    {
        moveHistory.setAll(listMoveHistory);
        MoveHistory.setItems(moveHistory);
    }

    @Override
    public ArrayList<String> GetMyMoveHisotry() throws RemoteException
    {
        return this.listMoveHistory;
    }

    @Override
    public void ReceiveSurrender(String loser) throws RemoteException
    {
        game.Surrender(loser);
    }

    @FXML
    private void surrender()
    {
        try
        {
            Iclient.surrender();
            this.game.Surrender(this.Iclient.getUserName());
        } catch (RemoteException ex)
        {
            Logger.getLogger(IngameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void draw()
    {
        System.out.println("@#@#@$@$@$@ in draw");
        try
        {
            Chatline chatLine;
            if (this.Iclient.getUserName().equals(this.player1))
            {
                if (game.isPlayer1Draw())
                {
                    chatLine = new Chatline("--System--", "Player1 heeft zijn gelijkspel aanvraag ingetrokken");
                } else
                {
                    chatLine = new Chatline("--System--", "Player1 heeft gelijkspel aangevraagd");
                }
                Iclient.draw(player2);
                game.setPlayer1Draw();
                client.sendInGameMessage(chatLine);
            } else if (this.Iclient.getUserName().equals(this.player2))
            {
                if (game.isPlayer2Draw())
                {
                    chatLine = new Chatline("--System--", "Player2 heeft zijn gelijkspel aanvraag ingetrokken");
                } else
                {
                    chatLine = new Chatline("--System--", "Player2 heeft gelijkspel aangevraagd");
                }
                Iclient.draw(player1);
                game.setPlayer2Draw();
                client.sendInGameMessage(chatLine);
            }
            game.checkDraw();
        } catch (RemoteException ex)
        {
            Logger.getLogger(IngameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void recieveDraw() throws RemoteException
    {
        if (this.Iclient.getUserName().equals(this.player2))
        {
            game.setPlayer1Draw();
        } else if (this.Iclient.getUserName().equals(this.player1))
        {
            game.setPlayer2Draw();
        }
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
                        game.checkDraw();
                    }
                });
            }
        }).start();
    }

    @Override
    public List<Player> getSpectators() throws RemoteException
    {
        return spectators;
    }

    @Override
    public void gameover()
    {
        try
        {
            if (this.Iclient.getUserName().equals(this.player1))
            {
                Iclient.sendGameOver(player2);
            } else
            {
                Iclient.sendGameOver(player1);
            }
        } catch (RemoteException ex)
        {
            Logger.getLogger(IngameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void recieveGameover() throws RemoteException
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
                        game.setFinished(true);
                    }
                });
            }
        }).start();
    }
    
    public void updateTimers()
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
                        System.out.println("Game : "+game+" resterend 1 :"+game.resterend(1)+"Game resterend 2 :"+game.resterend(2));
                        timerWhite.setText(String.valueOf(game.resterend(1)));
                        timerBlack.setText(String.valueOf(game.resterend(2)));
                    }
                });
            }
        }).start();
    }

  public void setLoggedInUser(Player LoggedInUser)
    {
        this.LoggedInUser = LoggedInUser;
    }
    
    public void GoToLobby(){
        try
        {
             if (game.getPlayer1().getUsername().equals(this.Iclient.getUserName()))
            {
                this.Iclient.SaveGame(game);
            }
            Stage CurrentStage = (Stage) Btn_Leave.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lobby.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            LobbyController controller = fxmlLoader.<LobbyController>getController();
            controller.setPlayer(this.LoggedInUser);
            controller.setClient(client);
            controller.setIClient(Iclient);
           
            CurrentStage.close();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex)
        {
            Logger.getLogger(IngameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
