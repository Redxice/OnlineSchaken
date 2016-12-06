/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.Point;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import Shared.IrmiServer;

/**
 *
 * @author Sander
 */
public class Client
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            Registry registry = LocateRegistry.getRegistry("169.254.183.180", 666);
            IrmiServer stub;
            try
            {
                stub = (IrmiServer) registry.lookup("doTurn");
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
