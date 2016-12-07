/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.IGameLobby;
import java.awt.Point;
import java.rmi.RemoteException;
import Shared.IrmiServer;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import onlineschaken.Gamelobby;

/**
 *
 * @author Sander
 */
public class RmiServer implements IrmiServer
{
    @Override
    public void doTurn(Point section1, Point section2, double time) throws RemoteException
    {
        System.out.println(section1.toString() + section2.toString() + time);
    }

    @Override
    public void test() throws RemoteException
    {
        System.out.println("works");
    }

    /*@Override
    public List<IGameLobby> GameLobbys() throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IGameLobby GetIGameLobby(Gamelobby gamelobby) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
}
