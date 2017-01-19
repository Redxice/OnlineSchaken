/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import onlineschaken.Game;

/**
 *
 * @author redxice
 */
public interface ILobbyController extends Remote
{
      /**
     * wordt aangeroepen wanneer er 
     * veranderingen zijn in de het aantal gamelobbys
     */
    public void UpdateGameLobbys()throws RemoteException;
    /**
     * 
     * @param game
     * @throws RemoteException 
     */
    public void RestartGame(Game game)throws RemoteException;
    
}
