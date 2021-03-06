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
import onlineschaken.Pawn;
import onlineschaken.Piece;
import onlineschaken.Player;

/**
 *
 * @author Sander
 */
public interface IinGameController extends Remote
{

    /**
     *
     * @param section1
     * @param section2
     * @param time
     * @throws RemoteException
     */
    public void move(Point section1, Point section2, double time) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public IrmiClient getIClient() throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public IrmiClient getClient() throws RemoteException;

    /**
     *
     * @param iClient
     * @throws RemoteException
     */
    public void setIClient(IrmiClient iClient) throws RemoteException;

    /**
     *
     * @param client
     * @throws RemoteException
     */
    public void setClient(ClientApp client) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public String getPlayer1() throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public String getPlayer2() throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public boolean getMyTurn() throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void setMyturn() throws RemoteException;

    /**
     *
     * @param message
     * @throws RemoteException
     */
    public void updateChat(Chatline message) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public boolean isWhite() throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public ArrayList<Point> getLocalLastMove() throws RemoteException;

    /**
     *
     * @param p1
     * @param p2
     * @throws RemoteException
     */
    public void setLocalLastMove(Point p1, Point p2) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public boolean getRealTurn() throws RemoteException;

    /**
     *
     * @param MyRealTurn
     * @throws RemoteException
     */
    public void SetRealTurn(boolean MyRealTurn) throws RemoteException;

    /**
     *
     * @param bool
     * @throws RemoteException
     */
    public void setisPromoting(boolean bool) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public boolean isPromoting() throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public boolean isIsWaitForPromotion() throws RemoteException;

    /**
     *
     * @param bool
     * @throws RemoteException
     */
    public void setIsWaitForPromotion(boolean bool) throws RemoteException;

    /**
     *
     * @param piece
     * @param pawn
     * @throws RemoteException
     */
    public void PromotePawn(Piece piece,Pawn pawn) throws RemoteException;
    
    /**
     *
     * @param prev
     * @param current
     * @param piece
     * @throws RemoteException
     */
    public void addToMoveHistory(Point prev, Point current,Piece piece)throws RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public ArrayList<String> GetMyMoveHisotry()throws RemoteException;
    
    /**
     *
     * @param loser
     * @throws RemoteException
     */
    public void ReceiveSurrender(String loser)throws RemoteException;
    
    /**
     *
     * @throws RemoteException
     */
    public void recieveDraw()throws RemoteException;
    
    /**
     *
     * @throws RemoteException
     */
    public void recieveGameover()throws RemoteException;
    
    /**
     *
     * @throws RemoteException
     */
    public void gameover()throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public boolean isSpectator() throws RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public List<Player> getSpectators()throws RemoteException;
    
    /**
     *
     * @throws RemoteException
     */
    public void leaveGame()throws RemoteException;
}
