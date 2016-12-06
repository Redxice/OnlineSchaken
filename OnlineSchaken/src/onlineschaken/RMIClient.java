/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Sander
 */
public class RMIClient
{

    public RMIClient() throws RemoteException, NotBoundException
    {
        Registry reg = LocateRegistry.getRegistry("127.0.0.1", 666);
    }
}
