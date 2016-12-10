/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import onlineschaken.Chatline;
import onlineschaken.Player;

/**
 *
 * @author redxice
 */
public interface IGameLobby extends Remote
{
    /**
     * Deze methode is bedoelt voor het adden van een player in de remote gamelobby.
     * Check of de gameLobby niet vol zit. 
     * @param player 
     */
    public boolean joinGameLobby(Player player)throws RemoteException;;
    /**
     * Deze methode moet de speler verwijderen uit de gamelobby.
     * Check of de gameLobby leeg is na het verwijderen van de speler. 
     * Als de gameLobby leeg is moet hij worden verwijdert uit de database.
     * @param player 
     */
    public boolean leaveGameLobby(Player player)throws RemoteException;;
    /**
     * Deze methode moet worden gebruikt wanneer de clients in 
     * de gamelobby ui op de Btn_Ready clicken.
     */
    public void PlayerIsReady(boolean ready,String lobbyName, String userName)throws RemoteException;;
    
    /**
     * Deze Chatline wordt geadd in  List Chat van de Gamelobby klassen
     * @param message 
     */
    public void SendMessage(Chatline message)throws RemoteException;
    
    /**
     * Haalt player1 op
     * @return een player object van de eerste speler
     * @throws RemoteException 
     */
    public Player GetPlayer1()throws RemoteException;
    
    /**
     * haalt player2 op
     * @return een player object van de tweede speler
     * @throws RemoteException 
     */
    public Player GetPlayer2()throws RemoteException;

    public ArrayList<String> GetPlayerNames()throws RemoteException;

    
    /**
     * Haalt de naam van de gamelobby op
     * @return de naam van de gamelobby
     * @throws RemoteException 
     */

    public String getName()throws RemoteException;
}
