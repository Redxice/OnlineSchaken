/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import gui.IngameController;
import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import onlineschaken.Chatline;
import onlineschaken.Pawn;
import onlineschaken.Piece;
import onlineschaken.Player;

/**
 *
 * @author Sander
 */
public interface IrmiClient extends Remote {

    public void sendTurn(Point prev, Point next, double time) throws RemoteException;
    
    public void getTurn(Point section1, Point section2, double time) throws RemoteException;

    public void addController(ILobbyController controller) throws RemoteException;

    public ArrayList<ILobbyController> GetLobbyControllers() throws RemoteException;

    public void UpdateLobbyController() throws RemoteException;

    public void setLobbyController(ILobbyController controller) throws RemoteException;
    
    public ILobbyController getLobbyController()throws RemoteException;

    public void setGameLobbyController(IGameLobbyController controller) throws RemoteException;
    
    public String getUserName() throws RemoteException;

    public void setUserName(String userName) throws RemoteException;
    
    public IGameLobbyController getGameLobbyController()throws RemoteException;
    
    public void updateChat() throws RemoteException;
    
    public void updatePlayerList() throws RemoteException;
    
    public void setIinGameController(IinGameController controller) throws RemoteException;
    
    public IinGameController GetGameController() throws RemoteException;

    public void RefreshGameLobby()throws RemoteException;

    public void updateReady() throws RemoteException;
    
    public void UpdateInGameChat(Chatline message)throws RemoteException;
    
    public void sendInGameMessage(Chatline message)throws RemoteException;

    public ArrayList<Point> getLastMove() throws RemoteException;
    /**
     * wordt gebruikt door de player die mag promoten
     * @param piece
     * @throws RemoteException 
     */
    public void castPiece(Piece piece,Pawn pawn)throws RemoteException;
    /**
     * wordt aangeroepen om bij de client die wacht op een promotie
     * @param piece
     * @throws RemoteException 
     */
    public void PromotePawn(Piece piece,Pawn pawn)throws RemoteException;

    public void isPromoting()throws RemoteException;
    
    public void isWaitinPromotion(boolean bool)throws RemoteException;
    
    public boolean addFriend(String Player,String Friend)throws RemoteException;
    
    public Player selectPlayer(String username)throws RemoteException;
    
    public boolean insertPlayer(String username,String password,String email)throws RemoteException;
}
