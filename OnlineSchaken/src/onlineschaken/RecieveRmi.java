/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import Server.RmiServer;
import Shared.IrmiClient;
import Shared.IrmiServer;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Sander
 */
public class RecieveRmi
{
    private static Registry registry;
    private static Board t1;
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {     
              RMIClient client = new RMIClient();
              IrmiClient stub = (IrmiClient)UnicastRemoteObject.exportObject(client,0);
              registry = LocateRegistry.createRegistry(667);
              registry.bind("Client", stub);
              System.err.println("Client ready");
              
//            t1 = new Board();
//            IrmiClient stub = (IrmiClient) UnicastRemoteObject.exportObject(t1,0);
//            //Bind the remote object stub in the registry
//            registry = LocateRegistry.createRegistry(600);
//            registry.bind("Client", stub);
//            System.err.println("Server ready");
            
        } catch (Exception e)
        {
            System.err.println("Server exception:" + e.toString());
            e.printStackTrace();
        }
    }
    
}
