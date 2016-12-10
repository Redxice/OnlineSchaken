/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import Shared.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
    private List<Chatline> chatLines = new ArrayList<>();
    private Player player1;
    private Player player2 = null;
    private boolean p1Ready;
    private boolean p2Ready;
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

    public String getNaam()
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
        if (player2 == null)
        {
            player2 = player;
            return true;
        }
        return false;
    }

    @Override
    public boolean leaveGameLobby(Player player) throws RemoteException
    {
        System.out.println(player1.toString());
        if (player1 == null)
        {
            return true;
        }
        if (player.getUsername() == player1.getUsername())
        {
            player1 = null;
            return true;
        }
        if (player2 == null)
        {
            return true;
        } else if (player.getUsername() == player2.getUsername())
        {
            player2 = null;
            return true;
        }
        return false;
    }

    @Override
    public void PlayerIsReady(boolean ready, String lobbyName, String userName) throws RemoteException
    {
        if (player1 != null && player2 != null)
        {
            if (userName == player1.getUsername())
            {
                p1Ready = ready;
            } else if (userName == player2.getUsername())
            {
                p1Ready = ready;
            }
            if (p1Ready && p2Ready)
            {

            }
        }
    }

    @Override
    public void SendMessage(Chatline message) throws RemoteException
    {
        chatLines.add(message);
    }

    @Override
    public String getName() throws RemoteException
    {
        return this.naam;
    }

    @Override
    public Player GetPlayer1() throws RemoteException
    {
        return this.player1;
    }

    @Override
    public ArrayList<String> GetPlayerNames() throws RemoteException
    {
        ArrayList<String> players = new ArrayList<>();
        if (player2 != null)
        {
            players.add(player2.getUsername());
        }
        players.add(player1.getUsername());
        return players;
    }

    public Player GetPlayer2() throws RemoteException
    {
        return this.player2;
    }

}
