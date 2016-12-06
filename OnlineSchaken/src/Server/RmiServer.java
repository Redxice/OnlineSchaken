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
    public void doTurn(Point section1, Point section2, String time) throws RemoteException
    {
        try
        {
            Registry registry = LocateRegistry.getRegistry("INSERT IP CLIENT HERE", 666);
            IrmiServer stub;
            try
            {
                stub = (IrmiServer) registry.lookup("getTurn");
                stub.doTurn(new Point(3, 3), new Point(3, 4), "3000");
            } catch (NotBoundException e)
            {
                System.err.println("Client exception:" + e.toString());
                e.printStackTrace();
            }
        } catch (RemoteException e)
        {
            System.err.println("Server exception:" + e.toString());
            e.printStackTrace();
        }
    }
    
}
