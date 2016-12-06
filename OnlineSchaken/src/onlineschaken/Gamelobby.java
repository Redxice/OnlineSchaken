/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.util.List;

/**
 *
 * @author redxice
 */
public class Gamelobby
{

    private String naam;
    private int maxPlayers = 2;
    private int currentPlayers;
    private List<Chatline> chatLines;
    private Player player1;
    private Player player2;
    private List<Player> spectators;

    public Gamelobby(String naam, Player player1)
    {
        this.naam= naam;
        this.player1 = player1;
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

    public List<Player> getSpectators()
    {
        return spectators;
    }

    @Override
    public String toString()
    {
        return "Gamelobby{" + "naam=" + naam + ", Players=" + currentPlayers + "/"+ maxPlayers+ '}';
    }
    
    
}
