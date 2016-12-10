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
public  class Server 
{
  private static Registry registry;
  private static RmiServer t1 = new RmiServer();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            t1 = new RmiServer();
            IrmiServer stub = (IrmiServer) UnicastRemoteObject.exportObject(t1,0);
            //Bind the remote object stub in the registry
            registry = LocateRegistry.createRegistry(666);
            registry.bind("Server", stub);
            System.err.println("Server ready");
            
        } catch (Exception e)
        {
            System.err.println("Server exception:" + e.toString());
            e.printStackTrace();
        }
       
    }

}
