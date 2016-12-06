/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import Shared.IrmiClient;
import Shared.IrmiServer;
import java.awt.Point;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Sander
 */
public class RmiClient implements IrmiClient
{

    @Override
    public void getTurn(Point section1, Point section2, String time) throws RemoteException
    {

    }
    
    public void sendTurn(Point section1, Point section2, String time)
    {
        try
        {
            Registry registry = LocateRegistry.getRegistry("169.254.183.180", 666);
            IrmiServer stub;
            try
            {
                stub = (IrmiServer) registry.lookup("setTurn");
                stub.doTurn(section1, section2, "3000");
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
