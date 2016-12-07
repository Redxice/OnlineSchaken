/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

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
public class start
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        String ip = /*"127.0.0.1";*/"169.254.183.180";
        try
        {
            System.out.println(ip);
            Registry registry = LocateRegistry.getRegistry(ip, 666);
            IrmiServer stub;
            try
            {
                stub = (IrmiServer) registry.lookup("setTurn");
                stub.doTurn(new Point(3,6), new Point(3,3), 3.00);
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
