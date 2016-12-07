/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.Point;
import java.rmi.RemoteException;
import Shared.IrmiServer;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
    
}
