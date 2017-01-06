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
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    private TextField Txt_Message;
    @FXML
    private Button Btn_Send;
    @FXML
    private ListView Chatbox;
    @FXML
    private SubScene GameBoard;
    @FXML
    private ListView MoveHistory;

    private Game game;
    private String player1;
    private String player2;
    private boolean white;
    private Point localStart;
    private Point localEnd;

    public IngameController() throws RemoteException
    {

    }

    /**
     * moet nog verder worden uitgewerkt. De players moeten worden geadd in de
     * game.
     */
    public void DrawBoard(Player p1, Player p2) throws RemoteException
    {
        Group root = new Group();
        client.setGame(this);
        System.out.println(client.GetGameController());
        game = new Game(p1, p2, Iclient);
        GameBoard.setRoot(root);
        game.getBoard().createContent();
        game.setPieces();
        game.getBoard().createContent2();
        root.getChildren().add(game.getBoard().getRoot());
        if (Iclient.getUserName().equals(game.getPlayer1().getUsername()))
        {
            this.isMyTurn = true;
            this.MyRealTurn = true;
            white = true;
        } else
        {
            this.isMyTurn = false;
            this.MyRealTurn = false;
            white = false;
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
                        {  Piece piece = game.getBoard().getSections(xValue, yValue).getPiece();
                            if (game.getBoard().getSections(xValue, yValue).getPiece().move(game.getBoard().getSections((int) section2.getX(), (int) section2.getY())))
                            {   try
                            {
                                addToMoveHistory(section1,section2,piece);
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
        String message = Txt_Message.getText();
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
    public void addToMoveHistory(Point prev, Point current,Piece piece)throws RemoteException
    { String type = null;
        if (piece instanceof Pawn)
        {
            type ="Pawn";
        }
        else if (piece instanceof Bishop)
        {
           type="Bishop";
        }
        else if (piece instanceof King)
        {
            type="King"; 
        }
        else if (piece instanceof Knight)
        {
             type="Knight"; 
        }
        else if (piece instanceof Queen)
        {
            type="Queen"; 
        }
         else if (piece instanceof Rook)
        {
           type="Rook";  
        }
        String move ="Piece : "+type+" From :" + getCharForNumber((int) prev.getX()) + prev.y + " To :" + getCharForNumber((int) current.getX()) + current.y;
        this.listMoveHistory.add(move);
        Platform.runLater(() ->addTheListToView());
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

   
}
