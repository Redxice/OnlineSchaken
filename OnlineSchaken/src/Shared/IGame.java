/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import onlineschaken.Chatline;

/**
 *
 * @author redxice
 */
public interface IGame
{
    /**
     * Deze Chatline wordt geadd in  List Chat van de game klassen
     * @param message 
     */
    public void SendMessage(Chatline message);
    
}
