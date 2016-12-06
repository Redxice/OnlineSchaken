/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.Itest;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
        String host = "169.254.183.180";
		
		Registry registry;
		try{
			registry = LocateRegistry.getRegistry("127.0.0.1", 666);
			Itest stub;
			try{
				System.currentTimeMillis();
				stub = (Itest)registry.lookup("Hello");
				stub.waarde();
				//System.out.println("response:" + response);
			} catch (NotBoundException e){
				System.err.println("Client exception:" + e.toString());
				e.printStackTrace();
			}
		} catch (RemoteException e) {
			System.err.println("Server exception:" + e.toString());
			e.printStackTrace();
}
    }
    
}
