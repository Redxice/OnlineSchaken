/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Server.ClientApp;
import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import onlineschaken.Game;

/**
 *
 * @author Sander
 */
public interface IinGameController extends Remote
{
    public void move(Point section1, Point section2, double time) throws RemoteException;
    public IrmiClient getIClient() throws RemoteException;
    public IrmiClient getClient() throws RemoteException;
    public void setIClient(IrmiClient iClient) throws RemoteException;
    public void setClient(ClientApp client) throws RemoteException;
    public String getPlayer1() throws RemoteException;
    public String getPlayer2() throws RemoteException;
}
