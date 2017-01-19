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
import java.util.List;
import javafx.collections.ObservableList;
import onlineschaken.Chatline;
import onlineschaken.Game;
import onlineschaken.Gamelobby;
import onlineschaken.Pawn;
import onlineschaken.Piece;
import onlineschaken.Player;
import onlineschaken.Section;

/**
 *
 * @author Sander
 */
public interface IrmiServer extends Remote
{

    /**
     * Een methoden om een zet door te sturen
     *
     * @param section1 de sectie waar het stuk op staat dat verplaatst moet
     * worden
     * @param section2 de sectie waar het stuk naar toe verplaatst moet worden
     * @param time de tijd die de speler nog overheeft
     * @param Username
     * @throws RemoteException
     */
    public void doTurn(Point section1, Point section2, double time, String Username) throws RemoteException;

    public void test() throws RemoteException;

    /**
     * Een methoden om de gamelobbys op te halen
     *
     * @return list met alle beschikbaren Igamelobbys
     * @throws RemoteException
     */
    public List<IGameLobby> GameLobbys() throws RemoteException;

    /**
     * Haalt een gamelobby op
     *
     * @param gamelobbyName
     * @return de gekozen Igamelobby
     * @throws RemoteException
     */
    public IGameLobby GetIGameLobby(String gamelobbyName) throws RemoteException;

    /**
     * Er wordt een niewue gamelobby gemaakt
     *
     * @param naam de naam van de gamelobby
     * @param player1 de speler die de gamelobby aanmaakt
     * @throws RemoteException
     */
    public boolean CreateGameLobby(String naam, Player player1) throws RemoteException;

    /**
     * Een moethoden die een namen van alle gamelobbys terug geeft
     *
     * @return een lijst met strings van de namen van de Igamelobbys
     * @throws RemoteException
     */
    public ArrayList<String> GetGameLobbys() throws RemoteException;

    /**
     * Stuurt een bericht naar de chat
     *
     * @param message De regel die in de chat moet komen
     * @param naamLobby de naam van de gamelobby waar het bericht is verstuurd
     * @throws RemoteException
     */
    public void SendMessage(Chatline message, String naamLobby) throws RemoteException;

    /**
     * Zet de speler op ready
     *
     * @param ready een bool met de waarden of de speler ready is
     * @param naamLobby de naam van de gamelobby waar de speler zich in bevind
     * @param userName de naam van de speler die ready heeft geklikt
     * @throws RemoteException
     */
    public void playerReady(boolean ready, String naamLobby, String userName) throws RemoteException;

    public void registerClient(IrmiClient client) throws RemoteException;

    public void removeGameLobby(String gameLobbyname) throws RemoteException;

    public void updateGameLobbyClient(IGameLobby lobby) throws RemoteException;

    public int IrmiClientCounter() throws RemoteException;

    public void SendInGameMessage(IinGameController controller, Chatline message) throws RemoteException;

    public ArrayList<Point> getLastMove(String userName) throws RemoteException;

     public void PromotePawn(Piece piece,Pawn pawn,String receiver) throws RemoteException;

    public void PlayerIsPromoting(IinGameController S_controller, String Username) throws RemoteException;
    
    public boolean addFriend(String player,String friend)throws RemoteException;
    
    public Player selectPlayer(String username)throws RemoteException;
    
    public boolean insterPlayer(String username,String password,String email)throws RemoteException;

    public void SendSurrender(String loser,String winner)throws RemoteException;
    
    public void draw(String userNameOtherPlayer) throws RemoteException;
    
    public void recieveGameover(String userNameOtherPlayer) throws RemoteException;

    public ArrayList<Game> GetUserGames(String username)throws RemoteException;

    public void SaveGame(Game game,String leaver)throws RemoteException;
}
