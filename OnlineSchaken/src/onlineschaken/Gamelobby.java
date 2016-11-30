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

    private String name;
    private int maxPlayers;
    private List<Chatline> chatLines;

    public Gamelobby(String name, int maxPlayers)
    {
        this.name = name;
        this.maxPlayers = maxPlayers;
    }

    public String getName()
    {
        return this.name;
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
