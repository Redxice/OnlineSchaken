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

    private boolean myRealTurn;
    private boolean isMyTurn;
    private boolean isPromoting;
    private boolean isWaitForPromotion;
    private ClientApp client;
    private IrmiClient iclient;
    private final ArrayList<String> listMoveHistory = new ArrayList<>();
    private final ObservableList moveHistory = FXCollections.observableArrayList();
    private final ArrayList<Chatline> chatlines = new ArrayList<>();
    private final ObservableList chatList = FXCollections.observableArrayList();
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

    /**
     *
     * @throws RemoteException
     */
    public IngameController() throws RemoteException
    {

    }

    /**
     * moet nog verder worden uitgewerkt. De players moeten worden geadd in de
     * game.
     * @param gameData
     * @param spectators
     * @param newGame
     * @throws java.rmi.RemoteException
     */
    public void DrawBoard(Game gameData, List<Player> spectators, boolean newGame) throws RemoteException
    {
        Group root = new Group();
        client.setGame(this);
        if (newGame)
        {
            this.game = gameData;
        } else
        {
            this.game = new Game(gameData,this.iclient,this);
        }
        
        GameBoard.setRoot(root);
        game.getBoard().createContent();
        if (newGame)
        {
           game.setPieces(); 
        }
        else{
          game.SetPiecesAgain();
        }
        game.getBoard().createContent2();
        game.setSpectators(spectators);
        this.spectators = spectators;
        root.getChildren().add(game.getBoard().getRoot());
        if (iclient.getUserName().equals(game.getPlayer1().getUsername()))
        {
            this.isMyTurn = true;
            this.myRealTurn = true;
            white = true;
            spectator = false;
        } else if (iclient.getUserName().equals(game.getPlayer2().getUsername()))
        {
            this.isMyTurn = false;
            this.myRealTurn = false;
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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }

    /**
     *
     * @param iClient
     * @throws RemoteException
     */
    @Override
    public void setIClient(IrmiClient iClient) throws RemoteException
    {
        this.iclient = iClient;
        this.iclient.setIinGameController(this);

    }

    /**
     *
     * @param client
     * @throws RemoteException
     */
    @Override
    public void setClient(ClientApp client) throws RemoteException
    {
        this.client = client;
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public IrmiClient getClient() throws RemoteException
    {
        return (IrmiClient) client;
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public IrmiClient getIClient() throws RemoteException
    {
        return iclient;
    }

    /**
     *
     * @param section1
     * @param section2
     * @param time
     * @throws RemoteException
     */
    @Override
    public void move(Point section1, Point section2, double time) throws RemoteException

    {
        int xValue = (int) section1.getX();
        int yValue = (int) section1.getY();
        new Thread(() ->
        {
            Platform.runLater(() ->
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
                        if (!isWaitForPromotion)
                        {
                            isMyTurn = true;
                        }
                    } else
                    {
                        
                    }
                    game.draw();
                }
            });
        }).start();
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public String getPlayer1() throws RemoteException
    {
        return player1;
    }

    /**
     *
     * @param player1
     */
    public void setPlayer1(String player1)
    {
        this.player1 = player1;
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public String getPlayer2() throws RemoteException
    {
        return player2;
    }

    /**
     *
     * @param player2
     */
    public void setPlayer2(String player2)
    {
        this.player2 = player2;
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean getMyTurn() throws RemoteException
    {
        return this.isMyTurn;
    }

    /**
     *
     * @throws RemoteException
     */
    @Override
    public void setMyturn() throws RemoteException
    {
        this.isMyTurn = false;
    }

    /**
     *
     * @param event
     */
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

    /**
     *
     * @param event
     */
    @FXML
    public void handleLeaveBtn(ActionEvent event)
    {
        try
        {
            System.out.println(this.game);
            iclient.SaveGame(this.game);
            leaveGame();
        } catch (RemoteException ex)
        {
            Logger.getLogger(IngameController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @throws RemoteException
     */
    @Override
    public void leaveGame() throws RemoteException
    {
        Platform.runLater(() ->
        {
            try
            {
                Stage CurrentStage = (Stage) Btn_Leave.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lobby.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                LobbyController controller = fxmlLoader.<LobbyController>getController();
                controller.setPlayer(LoggedInUser);
                controller.setClient(client);
                controller.setiClient(iclient);
                CurrentStage.close();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex)
            {
                Logger.getLogger(GamelobbyController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    /**
     *
     * @param message
     * @throws RemoteException
     */
    @Override
    public void updateChat(Chatline message) throws RemoteException
    {
        this.chatlines.add(message);
        new Thread(() ->
        {
            Platform.runLater(() ->
            {
                chatList.setAll(chatlines);
                Chatbox.setItems(chatList);
                
                chatList.setAll(chatlines);
                Chatbox.setItems(chatList);
                Txt_Message.setText("");
            });
        }).start();
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean isWhite() throws RemoteException
    {
        return white;
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean isSpectator() throws RemoteException
    {
        return spectator;
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean getRealTurn() throws RemoteException
    {
        return this.myRealTurn;
    }

    /**
     *
     * @param MyRealTurn
     * @throws RemoteException
     */
    @Override
    public void SetRealTurn(boolean MyRealTurn) throws RemoteException
    {
        this.myRealTurn = MyRealTurn;
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<Point> getLocalLastMove() throws RemoteException
    {
        ArrayList<Point> list = new ArrayList<>();
        list.add(localStart);
        list.add(localEnd);
        return list;
    }

    /**
     *
     * @param p1
     * @param p2
     * @throws RemoteException
     */
    @Override
    public void setLocalLastMove(Point p1, Point p2) throws RemoteException
    {
        localStart = p1;
        localEnd = p2;
    }

    /**
     *
     * @param piece
     * @param pawn
     * @throws RemoteException
     */
    @Override
    public void PromotePawn(Piece piece, Pawn pawn) throws RemoteException
    {
        this.game.PromotePawn(piece, pawn);
        this.isWaitForPromotion = false;
    }

    /**
     * start timers
     */
    public void runTimer()
    {
        Timer timer = new Timer();
        timer.schedule(new TurnTimer(this, client), 0, 5000);
    }

    /**
     *
     * @param bool
     * @throws RemoteException
     */
    @Override
    public void setisPromoting(boolean bool) throws RemoteException
    {
        this.isPromoting = bool;
        if (bool)
        {
            iclient.isPromoting();
        }
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean isPromoting() throws RemoteException
    {
        return isPromoting;
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean isIsWaitForPromotion() throws RemoteException
    {
        return isWaitForPromotion;
    }

    /**
     *
     * @param bool
     * @throws RemoteException
     */
    @Override
    public void setIsWaitForPromotion(boolean bool) throws RemoteException
    {
        this.isWaitForPromotion = bool;
    }

    /**
     *
     * @param prev
     * @param current
     * @param piece
     * @throws RemoteException
     */
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

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<String> GetMyMoveHisotry() throws RemoteException
    {
        return this.listMoveHistory;
    }

    /**
     *
     * @param loser
     * @throws RemoteException
     */
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
            iclient.surrender();
            this.game.Surrender(this.iclient.getUserName());
        } catch (RemoteException ex)
        {
            Logger.getLogger(IngameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * handle a game that is a draw
     */
    @FXML
    public void draw()
    {
        try
        {
            Chatline chatLine;
            if (this.iclient.getUserName().equals(this.player1))
            {
                if (game.isPlayer1Draw())
                {
                    chatLine = new Chatline("--System--", "Player1 heeft zijn gelijkspel aanvraag ingetrokken");
                } else
                {
                    chatLine = new Chatline("--System--", "Player1 heeft gelijkspel aangevraagd");
                }
                iclient.draw(player2);
                game.setPlayer1Draw();
                client.sendInGameMessage(chatLine);
            } else if (this.iclient.getUserName().equals(this.player2))
            {
                if (game.isPlayer2Draw())
                {
                    chatLine = new Chatline("--System--", "Player2 heeft zijn gelijkspel aanvraag ingetrokken");
                } else
                {
                    chatLine = new Chatline("--System--", "Player2 heeft gelijkspel aangevraagd");
                }
                iclient.draw(player1);
                game.setPlayer2Draw();
                client.sendInGameMessage(chatLine);
            }
            game.checkDraw();
        } catch (RemoteException ex)
        {
            Logger.getLogger(IngameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @throws RemoteException
     */
    @Override
    public void recieveDraw() throws RemoteException
    {
        if (this.iclient.getUserName().equals(this.player2))
        {
            game.setPlayer1Draw();
        } else if (this.iclient.getUserName().equals(this.player1))
        {
            game.setPlayer2Draw();
        }
        new Thread(() ->
        {
            Platform.runLater(() ->
            {
                game.checkDraw();
            });
        }).start();
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public List<Player> getSpectators() throws RemoteException
    {
        return spectators;
    }

    /**
     * called when game is finished and sends this to both players
     */
    @Override
    public void gameover()
    {
        try
        {
            if (this.iclient.getUserName().equals(this.player1))
            {
                iclient.sendGameOver(player2);
            } else
            {
                iclient.sendGameOver(player1);
            }
        } catch (RemoteException ex)
        {
            Logger.getLogger(IngameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @throws RemoteException
     */
    @Override
    public void recieveGameover() throws RemoteException
    {
        new Thread(() ->
        {
            Platform.runLater(() ->
            {
                game.setFinished(true);
            });
        }).start();
    }
    
    /**
     * update the labels with new values
     */
    public void updateTimers()
    {
        new Thread(() ->
        {
            Platform.runLater(() ->
            {
                timerWhite.setText(String.valueOf(game.resterend(1)));
                timerBlack.setText(String.valueOf(game.resterend(2)));
            });
        }).start();
    }

    /**
     *
     * @param LoggedInUser
     */
    public void setLoggedInUser(Player LoggedInUser)
    {
        this.LoggedInUser = LoggedInUser;
    }
    
    /**
     * join a lobby
     */
    public void GoToLobby(){
        try
        {
             if (game.getPlayer1().getUsername().equals(this.iclient.getUserName()))
            {
                this.iclient.SaveGame(game);
            }
            Stage CurrentStage = (Stage) Btn_Leave.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lobby.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            LobbyController controller = fxmlLoader.<LobbyController>getController();
            controller.setPlayer(this.LoggedInUser);
            controller.setClient(client);
            controller.setiClient(iclient);
           
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
