/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.Itest;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Sander
 */
public class Server implements Hello
{

    public Server()
    {
    }

    public String sayHello()
    {
        return "Hello, world!";
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            test t1 = new test();
            //Server obj = new Server();
            Itest stub = (Itest) UnicastRemoteObject.exportObject(t1, 0);

            //Bind the remote object stub in the registry
            Registry registry = LocateRegistry.createRegistry(666);
            registry.bind("Hello", stub);

            System.err.println("Server ready");
        } catch (Exception e)
        {
            System.err.println("Server exception:" + e.toString());
            e.printStackTrace();
        }
    }

}
