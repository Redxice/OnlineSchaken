/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import onlineschaken.Chatline;
import onlineschaken.Game;
import onlineschaken.Pawn;
import onlineschaken.Piece;
import onlineschaken.Player;

/**
 *
 * @author Sander
 */
public interface IrmiClient extends Remote {

    /**
     *
     * @param prev
     * @param next
     * @param time
     * @throws RemoteException
     */
    public void sendTurn(Point prev, Point next, double time) throws RemoteException;
    
    /**
     *
     * @param section1
     * @param section2
     * @param time
     * @throws RemoteException
     */
    public void getTurn(Point section1, Point section2, double time) throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void UpdateLobbyController() throws RemoteException;

    /**
     *
     * @param controller
     * @throws RemoteException
     */
    public void setLobbyController(ILobbyController controller) throws RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public ILobbyController getLobbyController()throws RemoteException;

    /**
     *
     * @param controller
     * @throws RemoteException
     */
    public void setGameLobbyController(IGameLobbyController controller) throws RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public String getUserName() throws RemoteException;

    /**
     *
     * @param userName
     * @throws RemoteException
     */
    public void setUserName(String userName) throws RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public IGameLobbyController getGameLobbyController()throws RemoteException;
    
    /**
     *
     * @throws RemoteException
     */
    public void updateChat() throws RemoteException;
    
    /**
     *
     * @throws RemoteException
     */
    public void updatePlayerList() throws RemoteException;
    
    /**
     *
     * @param controller
     * @throws RemoteException
     */
    public void setIinGameController(IinGameController controller) throws RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public IinGameController GetGameController() throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void RefreshGameLobby()throws RemoteException;

    /**
     *
     * @param playerName
     * @throws RemoteException
     */
    public void updateReady(String playerName) throws RemoteException;
    
    /**
     *
     * @param message
     * @throws RemoteException
     */
    public void UpdateInGameChat(Chatline message)throws RemoteException;
    
    /**
     *
     * @param message
     * @throws RemoteException
     */
    public void sendInGameMessage(Chatline message)throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public ArrayList<Point> getLastMove() throws RemoteException;
    /**
     * wordt gebruikt door de player die mag promoten
     * @param piece
     * @param pawn
     * @throws RemoteException 
     */
    public void castPiece(Piece piece,Pawn pawn)throws RemoteException;
    /**
     * wordt aangeroepen om bij de client die wacht op een promotie
     * @param piece
     * @param pawn
     * @throws RemoteException 
     */
    public void PromotePawn(Piece piece,Pawn pawn)throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void isPromoting()throws RemoteException;
    
    /**
     *
     * @param bool
     * @throws RemoteException
     */
    public void isWaitinPromotion(boolean bool)throws RemoteException;
    
    /**
     *
     * @param Player
     * @param Friend
     * @return
     * @throws RemoteException
     */
    public boolean addFriend(String Player,String Friend)throws RemoteException;
    
    /**
     *
     * @param username
     * @return
     * @throws RemoteException
     */
    public Player selectPlayer(String username)throws RemoteException;
    
    /**
     *
     * @param username
     * @param password
     * @param email
     * @return
     * @throws RemoteException
     */
    public boolean insertPlayer(String username,String password,String email)throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void surrender()throws RemoteException;
    
    /**
     *
     * @param userNameOtherPlayer
     * @throws RemoteException
     */
    public void draw(String userNameOtherPlayer)throws RemoteException;
    
    /**
     *
     * @param userNameOtherPlayer
     * @throws RemoteException
     */
    public void sendGameOver(String userNameOtherPlayer)throws RemoteException;

    /**
     *
     * @param username
     * @return
     * @throws RemoteException
     */
    public ArrayList<Game> GetGames(String username)throws RemoteException;

    /**
     *
     * @param game
     * @throws RemoteException
     */
    public void SaveGame(Game game)throws RemoteException;
    
    /**
     *
     * @throws RemoteException
     */
    public void leaveGame() throws RemoteException;

    /**
     *
     * @param SelectedGame
     * @return
     * @throws RemoteException
     */
    public boolean RestartGame(Game SelectedGame)throws RemoteException;
}
