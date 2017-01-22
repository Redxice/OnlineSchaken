/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import onlineschaken.Player;

/**
 *
 * @author Dhr van Baast
 */
public interface IGameLobbyController extends Remote{
    
    /**
     *
     * @throws RemoteException
     */
    public void updateChat()throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void updatePlayerList()throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public IGameLobby getIGameLobby()throws RemoteException;

    /**
     *
     * @param userName
     * @throws RemoteException
     */
    public void ready(String userName) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public Player getLogInUser() throws RemoteException;
}
