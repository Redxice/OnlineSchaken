/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import Shared.IrmiServer;

/**
 *
 * @author Sander
 */
public class Server
{

    public Server()
    {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            RmiServer t1 = new RmiServer();
            IrmiServer stub = (IrmiServer) UnicastRemoteObject.exportObject(t1, 0);

            //Bind the remote object stub in the registry
            Registry registry = LocateRegistry.createRegistry(666);
            registry.bind("setTurn", stub);

            System.err.println("Server ready");
        } catch (Exception e)
        {
            System.err.println("Server exception:" + e.toString());
            e.printStackTrace();
        }
    }

}