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
import onlineschaken.RMIClient;

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
        RMIClient client = new RMIClient();
        client.sendTurn(new Point(3, 3), new Point(3, 4), "3000");
    }

}
