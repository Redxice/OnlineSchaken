/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Sander
 */
public interface IrmiClient extends Remote {

    public void getTurn(Point section1, Point section2, double time) throws RemoteException;

    public void addController(ILobbyController controller) throws RemoteException;

    public ArrayList<ILobbyController> GetLobbyControllers() throws RemoteException;

    public void UpdateLobbyController() throws RemoteException;

    public void setLobbyController(ILobbyController controller) throws RemoteException;
    
    public ILobbyController getLobbyController()throws RemoteException;

    public void setGameLobbyController(IGameLobbyController controller) throws RemoteException;
    
    public String getUserName() throws RemoteException;

    public void setUserName(String userName) throws RemoteException;
    
    public IGameLobbyController getGameLobbyController()throws RemoteException;
    
    public void updateChat() throws RemoteException;
    
    public void updatePlayerList() throws RemoteException;

    public void RefreshGameLobby()throws RemoteException;

    public void updateReady() throws RemoteException;
