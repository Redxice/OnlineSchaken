/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.IGameLobby;
import Shared.IrmiServer;
import java.awt.Point;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import onlineschaken.*;


/**
 *
 * @author Sander
 */
public class ClientApp
{
    private ArrayList<IGameLobby> GameLobbys = new ArrayList<>();
    private String ip = "127.0.0.1";/*"169.254.183.180";*/
    /**
     * Wordt aangeroepen in de GameLobbyController in de create GameLobby methode.
     * @param LobbyName
     * @param host 
     */
    public void createGameLobby(String LobbyName,Player host){
       
        try
        {
            Registry registry = LocateRegistry.getRegistry(ip, 666);
            IrmiServer stub;
            try
            {
                stub = (IrmiServer) registry.lookup("setTurn");
                stub.CreateGameLobby(LobbyName,host);
                
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
    public void sendTurn(Point prev, Point next, double time)
    {
        String ip = /*"127.0.0.1";*/"169.254.183.180";
        try
        {
            Registry registry = LocateRegistry.getRegistry(ip, 666);
            IrmiServer stub;
            try
            {
                stub = (IrmiServer) registry.lookup("setTurn");
                stub.doTurn(prev, next, time);
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

    /**
     * haalt van de server al zijn gamelobbys op en returned deze. Deze methode wordt aangeroepen in de initialize van de LobbyController.
     * @return 
     */
    public ArrayList<String> GetGameLobbys(){
            try
            {
                Registry registry = LocateRegistry.getRegistry(ip, 666);
                IrmiServer stub;
                try
                {
                 stub = (IrmiServer) registry.lookup("setTurn");
                 return stub.GetGameLobbys();
                } catch (NotBoundException e)
                {
                    System.err.println("Client exception:" + e.toString());
                    e.printStackTrace();
                }
            } catch (RemoteException ex)
            {
                Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
            }
           return null;
    }
}

    public void SendMessage(Chatline chatline, String naamLobby)
    {
        String ip = "127.0.0.1";/*"169.254.183.180";*/
        try
        {
            Registry registry = LocateRegistry.getRegistry(ip, 666);
            IrmiServer stub;
            try
            {
                stub = (IrmiServer) registry.lookup("setTurn");
                stub.SendMessage(chatline, naamLobby);
                
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
    
    public void playerReady(boolean ready,String lobbyName, String userName)
    {
        String ip = "127.0.0.1";/*"169.254.183.180";*/
        try
        {
            Registry registry = LocateRegistry.getRegistry(ip, 666);
            IrmiServer stub;
            try
            {
                stub = (IrmiServer) registry.lookup("setTurn");
                stub.playerReady(ready,lobbyName,userName);
                
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

