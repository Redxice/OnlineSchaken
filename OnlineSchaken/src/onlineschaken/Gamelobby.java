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

    private int id;
    private int maxPlayers;
    private int currentPlayers;
    private List<Chatline> chatLines;

    public Gamelobby(int id, int currentPlayers)
    {
        this.id = id;
        this.currentPlayers = currentPlayers;
    }    

    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    public void addChatLine(Chatline message)
    {
        chatLines.add(message);
    }

}
