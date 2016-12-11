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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.SubScene;


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
    
    public IngameController()throws RemoteException
    {
        
    }
   /**
    * moet nog verder worden uitgewerkt. De players moeten worden geadd in de game.
    */
    public void DrawBoard() throws RemoteException{
        Player p1 = new Player("White", "ww", 0);
        Player p2 = new Player("Black", "ww", 0);
        Group root = new Group();
        client.setGame(this);
        System.out.println(client.getGame());
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
    public void setIClient(IrmiClient iClient)
    {
       this.Iclient = iClient;
    }
    
    @Override
    public void setClient(ClientApp client)
    {
       this.client = client;
    }
    
    @Override
    public IrmiClient getClient()
    {
       return (IrmiClient)client;
    }
    
    @Override
    public IrmiClient getIClient()
    {
        return Iclient;
    }
    
    @Override
    public void move(Point section1, Point section2, double time)
    {
        int xValue = (int) section1.getX();
        int yValue = (int) section1.getY();
        game.getBoard().getSections(xValue, yValue).getPiece().move(game.getBoard().getSections((int)section2.getX(), (int)section2.getY()));
        System.out.println("gelukt");
    }
}
