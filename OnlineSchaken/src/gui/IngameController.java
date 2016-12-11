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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.SubScene;
import onlineschaken.Chatline;

import onlineschaken.Game;
import onlineschaken.Player;

/**
 * FXML Controller class
 *
 * @author redxice
 */
public class IngameController extends UnicastRemoteObject implements Initializable, IinGameController
{

    private ClientApp client;
    private IrmiClient Iclient;
    @FXML
    private SubScene GameBoard;
    private Game game;
    private String player1;
    private String player2;

    public IngameController() throws RemoteException
    {

    }

    /**
     * moet nog verder worden uitgewerkt. De players moeten worden geadd in de
     * game.
     */
    public void DrawBoard() throws RemoteException
    {
        Player p1 = new Player("White", "ww", 0);
        Player p2 = new Player("Black", "ww", 0);
        Group root = new Group();
        client.setGame(this);
        System.out.println(client.GetGameController());
        game = new Game(p1, p2, Iclient);
        System.out.println("IngameController: " + client);
        GameBoard.setRoot(root);
        game.getBoard().createContent();
        game.setPieces();
        game.getBoard().createContent2();
        root.getChildren().add(game.getBoard().getRoot());
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
        System.out.println(game.getBoard().getSections(xValue, yValue).getPiece().toString());
        System.out.println(game.getBoard().getSections((int) section2.getX(), (int) section2.getY()).getID());
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
                        if (game.getBoard().getSections(xValue, yValue).getPiece().move(game.getBoard().getSections((int) section2.getX(), (int) section2.getY())) == false)
                        {

                            if (game.getBoard().getTurn().equals("white"))
                        {
                            game.getBoard().setTurn("black");
                        } else if (game.getBoard().getTurn().equals("black"))
                        {
                            game.getBoard().setTurn("black");
                        }
                        }
  
                            System.out.println("Hij mag daar niet heen bewegen/er gaat iets fout");
                        }
                        if (game.getBoard().getSections(xValue, yValue).getPiece().move(game.getBoard().getSections((int) section2.getX(), (int) section2.getY())) == true)

                        {
                            if (game.getBoard().getTurn().equals("white"))
                            {
                                game.getBoard().setTurn("black");
                            } else if (game.getBoard().getTurn().equals("black"))
                            {
                                game.getBoard().setTurn("black");
                            }
                        } else
                        {
                            System.out.println("Hij mag daar niet heen bewegen/er gaat iets fout");
                        }

                    }
                });
            }
        }).start();

        System.out.println("gelukt");
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

}
