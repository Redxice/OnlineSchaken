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
    private long time;

    /**
     * @param userName
     * @param message
     */
    public Chatline(String userName, String message)
    {
        this.userName = userName;
        this.message = message;
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
        return this.userName+ ": "+ this.message;
    }

}
