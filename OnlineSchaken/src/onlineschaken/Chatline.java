/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.io.Serializable;

/**
 *
 * @author redxice
 */
public class Chatline implements Serializable
{

    private String userName;
    private String message;
    private Game game;
    private Gamelobby gameLobby;
    private long time;

    /**
     *
     * @param userName
     * @param message
     * @param game
     */
    public Chatline(String userName, String message, Game game)
    {
        time = System.currentTimeMillis();;
        this.userName = userName;
        this.message = message;
        this.game = game;
    }
/**
     *
     * @param userName
     * @param message
     * @param gameLobby
     */
    public Chatline(String userName, String message, Gamelobby gameLobby)
    {
        this.userName = userName;
        this.message = message;
        this.gameLobby = gameLobby;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getMessage()
    {
        return message;
    }

    @Override
    public String toString()
    {
        return System.currentTimeMillis() + this.userName + this.message;
    }

}
