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
public class Gamelobby extends UnicastRemoteObject implements IGameLobby {

    private String naam;
    private int maxPlayers = 2;
    private int id;
    private int currentPlayers;
    private List<Chatline> chatLines = new ArrayList<>();
    private Player player1;
    private Player player2 = null;
    private boolean p1Ready;
    private boolean p2Ready;
    private List<Player> spectators = new ArrayList<>();

    /**
     *
     * @param naam
     * @param player1
     * @param id
     * @throws RemoteException
     */
    public Gamelobby(String naam, Player player1, int id) throws RemoteException {
        this.naam = naam;
        this.player1 = player1;
        currentPlayers = 1;
        this.id = id;
    }

    /**
     *
     * @param naam
     * @param player1
     * @param player2
     * @param id
     * @throws RemoteException
     */
    public Gamelobby(String naam, Player player1, Player player2, int id) throws RemoteException {
        this.naam = naam;
        this.player1 = player1;
        this.player2 = player2;
        currentPlayers = 2;
        this.id = id;
    }

    /**
     *
     * @param naam
     * @param player1
     * @throws RemoteException
     */
    public Gamelobby(String naam, Player player1) throws RemoteException {
        this.naam = naam;
        this.player1 = player1;
        currentPlayers = 1;
    }

    /**
     *
     * @return
     */
    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    /**
     *
     * @param message
     */
    public void addChatLine(Chatline message) {
        chatLines.add(message);
    }

    /**
     *
     * @param spectator
     */
    public void addSpectator(Player spectator) {
        spectators.add(spectator);
    }    

    /**
     *
     * @param player2
     */
    public void setPlayer2(Player player2) {
        this.player2 = player2;
        currentPlayers = 2;
    }

    /**
     *
     * @return
     */
    public int getCurrentPlayers() {
        return currentPlayers;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Chatline> getChatLines() {
        return chatLines;
    }

    /**
     *
     * @return
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     *
     * @return
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     *
     * @return
     */
    public String getNaam() {
        return naam;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<Player> getSpectators() {
        return spectators;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "ID=" + id + ", Naam=" + naam + ", Players=" + currentPlayers + "/" + maxPlayers;
    }

    @Override
    public boolean joinGameLobby(Player player) throws RemoteException {
        if(player1 == null)
        {
            player1 = player;
            return true;
        }
        if (player2 == null) {
            player2 = player;
            return true;
        }
        spectators.add(player);
        return true;
    }

    @Override
    public boolean leaveGameLobby(Player player) throws RemoteException {
        if (player1 != null) {
            if (player.getUsername().equals(player1.getUsername())) {
                player1 = null;
                return true;
            }
        }
        if (player2 != null) {
            if (player.getUsername().equals(player2.getUsername())) {
                player2 = null;
                return true;
            }
        }
        System.out.println(spectators + "voor");
        for(Player p : spectators)
        {
            if(player.getUsername().equals(p.getUsername()))
            {
                spectators.remove(p);
            }
        }
        return true;
    }

    @Override
    public void PlayerIsReady(boolean ready, String lobbyName, String userName) throws RemoteException {
        if (player1 != null && player2 != null) {
            if (userName.equals(player1.getUsername())) {
                p1Ready = ready;
            } else if (userName.equals(player2.getUsername())) {
                p1Ready = ready;
            }
            if (p1Ready && p2Ready) {

            }
        }
    }

    @Override
    public void SendMessage(Chatline message) throws RemoteException {
        chatLines.add(message);
    }

    @Override
    public String getName() throws RemoteException {
        return this.naam;
    }

    @Override
    public Player GetPlayer1() throws RemoteException {
        return this.player1;
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<Player> GetPlayerNames() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        if (player2 != null) {
            players.add(player2);
        }
        if(player1 != null){
        players.add(player1);
        }
        return players;
    }

    @Override
    public Player GetPlayer2() throws RemoteException {
        return this.player2;
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean checkPlayer2Exists() throws RemoteException
    {
        return this.player2 != null;
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean checkPlayer1Exists() throws RemoteException
    {
        return this.player1 != null;
    }

    
}
