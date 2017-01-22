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
    private final String userName;
    private final String message;

    /**
     * @param userName
     * @param message
     */
    public Chatline(String userName, String message)
    {
        this.userName = userName;
        this.message = message;
    }

    /**
     *
     * @return
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     *
     * @return
     */
    public String getMessage()
    {
        return message;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString()
    {
        return this.userName+ ": "+ this.message;
    }

}
