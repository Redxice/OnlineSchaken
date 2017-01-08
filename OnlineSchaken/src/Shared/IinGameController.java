/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Server.ClientApp;
import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import onlineschaken.Chatline;
import onlineschaken.Game;
import onlineschaken.Pawn;
import onlineschaken.Piece;
import onlineschaken.Player;
import onlineschaken.Section;

/**
 *
 * @author Sander
 */
public interface IinGameController extends Remote
{

    public void move(Point section1, Point section2, double time) throws RemoteException;

    public IrmiClient getIClient() throws RemoteException;

    public IrmiClient getClient() throws RemoteException;

    public void setIClient(IrmiClient iClient) throws RemoteException;

    public void setClient(ClientApp client) throws RemoteException;

    public String getPlayer1() throws RemoteException;

    public String getPlayer2() throws RemoteException;

    public boolean getMyTurn() throws RemoteException;

    public void setMyturn() throws RemoteException;

    public void updateChat(Chatline message) throws RemoteException;

    public boolean isWhite() throws RemoteException;

    public ArrayList<Point> getLocalLastMove() throws RemoteException;

    public void setLocalLastMove(Point p1, Point p2) throws RemoteException;

    public boolean getRealTurn() throws RemoteException;

    public void SetRealTurn(boolean MyRealTurn) throws RemoteException;

    public void setisPromoting(boolean bool) throws RemoteException;

    public boolean isPromoting() throws RemoteException;

    public boolean isIsWaitingForPromotion() throws RemoteException;

    public void setIsWaitingForPromotion(boolean bool) throws RemoteException;

    public void PromotePawn(Piece piece,Pawn pawn) throws RemoteException;
    
    public void addToMoveHistory(Point prev, Point current,Piece piece)throws RemoteException;
    
    public ArrayList<String> GetMyMoveHisotry()throws RemoteException;
    
    public void ReceiveSurrender(String loser)throws RemoteException;
    
    public void recieveDraw()throws RemoteException;
    
    public boolean isSpectator() throws RemoteException;
    
    public List<Player> getSpectators()throws RemoteException;
}
