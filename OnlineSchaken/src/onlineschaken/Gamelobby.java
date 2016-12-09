/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import Shared.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author redxice
 */
public class Gamelobby extends UnicastRemoteObject implements IGameLobby
{
    private String naam;
    private int maxPlayers = 2;
    private int id;
    private int currentPlayers;
    private List<Chatline> chatLines;
    private Player player1;
    private Player player2;
    private List<Player> spectators;

    public Gamelobby(String naam, Player player1, int id) throws RemoteException

    {
        this.naam = naam;
        this.player1 = player1;
        currentPlayers = 1;
        this.id = id;
    }

    public Gamelobby(String naam, Player player1, Player player2, int id) throws RemoteException

    {
        this.naam = naam;
        this.player1 = player1;
        this.player2 = player2;
        currentPlayers = 2;
        this.id = id;
    }

    public Gamelobby(String naam, Player player1) throws RemoteException
    {
        this.naam = naam;
        this.player1 = player1;
        currentPlayers = 1;
    }

    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    public void addChatLine(Chatline message)
    {
        chatLines.add(message);
    }

    public void addSpectator(Player spectator)
    {
        spectators.add(spectator);
    }

    public void setPlayer2(Player player2)
    {
        this.player2 = player2;
        currentPlayers = 2;
    }

    public int getCurrentPlayers()
    {
        return currentPlayers;
    }

    public List<Chatline> getChatLines()
    {
        return chatLines;
    }

    public Player getPlayer1()
    {
        return player1;
    }

    public Player getPlayer2()
    {
        return player2;
    }
    
    @Override
    public String getNaam()throws RemoteException
    {
        return naam;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public List<Player> getSpectators()
    {
        return spectators;
    }

    @Override
    public String toString()
    {
        return "ID=" + id + ", Naam=" + naam + ", Players=" + currentPlayers + "/" + maxPlayers;
    }

    @Override
    public boolean joinGameLobby(Player player) throws RemoteException
    {
        if (player2 != null)
        {
            player2 = player;
            return true;
        }
        return false;
    }

    @Override
    public boolean leaveGameLobby(Player player) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void PlayerIsReady() throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SendMessage(Chatline message) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
