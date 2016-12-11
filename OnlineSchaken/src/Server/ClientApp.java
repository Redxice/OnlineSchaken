/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.IGameLobby;
import Shared.IGameLobbyController;
import Shared.ILobbyController;
import Shared.IrmiClient;
import Shared.IrmiServer;
import java.awt.Point;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import onlineschaken.*;


/**
 *
 * @author Sander
 */
public class ClientApp implements IrmiClient
{
    private ArrayList<IGameLobby> GameLobbys = new ArrayList<>();
    private ILobbyController lobbyController ;
    private IGameLobbyController gameLobbyController;
    private String ip = "127.0.0.1";/*"169.254.183.180";*/
    private String userName;

    /**
     * Wordt aangeroepen in de GameLobbyController in de create GameLobby methode.
     * @param LobbyName
     * @param host 
     */
    public boolean createGameLobby(String LobbyName,Player host){
       
        try
        {
            Registry registry = LocateRegistry.getRegistry(ip, 666);
            IrmiServer stub;
            try
            {
                stub = (IrmiServer) registry.lookup("Server");
              return stub.CreateGameLobby(LobbyName,host);
                
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
       return false;
    }
   
    public void sendTurn(Point prev, Point next, double time)
    {
        String ip = "127.0.0.1";/*"169.254.183.180";*/
        try
        {
            Registry registry = LocateRegistry.getRegistry(ip, 666);
            IrmiServer stub;
            try
            {   
                stub = (IrmiServer) registry.lookup("Server");
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
                 stub = (IrmiServer) registry.lookup("Server");
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

    public void SendMessage(Chatline chatline, String naamLobby)
    {
        String ip = "127.0.0.1";/*"169.254.183.180";*/
        try
        {
            Registry registry = LocateRegistry.getRegistry(ip, 666);
            IrmiServer stub;
            try
            {  
                stub = (IrmiServer) registry.lookup("Server");
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
                stub = (IrmiServer) registry.lookup("Server");
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
    public IGameLobby GetGameLobby(String LobbyName){
        IGameLobby lobby =null ;
        try
        {
            Registry registry = LocateRegistry.getRegistry(ip, 666);
            IrmiServer stub;
            try
            {
                stub = (IrmiServer) registry.lookup("Server");
                lobby = stub.GetIGameLobby(LobbyName);
                
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
        return lobby;
    }
    public void unBindLobby(String lobbyName)
    {
        try
        {            
            Registry registry = LocateRegistry.getRegistry(ip, 666);
            IrmiServer stub;
            try
            {
                stub = (IrmiServer) registry.lookup("Server");
                stub.removeGameLobby(lobbyName);
                
            } catch (NotBoundException e)
            {
                System.err.println("Client exception:" + e.toString());
                e.printStackTrace();
            }
            try {
                registry.unbind(lobbyName);
            } catch (NotBoundException ex) {
                Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AccessException ex) {
                Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (RemoteException e)
        {
            System.err.println("Server exception:" + e.toString());
            e.printStackTrace();
        }
    }
    @Override
    public void getTurn(Point section1, Point section2, double time) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addController(ILobbyController controller) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ILobbyController> GetLobbyControllers() throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void UpdateLobbyController() throws RemoteException
    {
        lobbyController.UpdateGameLobbys();
    }
    @Override
    public void setLobbyController(ILobbyController controller)throws RemoteException{
        System.out.println("Lobby is set in ICLient "+controller);
        this.lobbyController = controller;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public IGameLobbyController getGameLobbyController() {
        return gameLobbyController;
    }

    @Override
    public void setGameLobbyController(IGameLobbyController controller) throws RemoteException {
        
        this.gameLobbyController = controller;
        System.out.println("Dit moet null zijn ;"+controller);
        if(this.gameLobbyController!= null){
        RefreshGameLobby();
        }
    
    }
    
    @Override
    public void RefreshGameLobby( )throws RemoteException{
         try
        {
            Registry registry = LocateRegistry.getRegistry(ip, 666);
            IrmiServer stub;
            try
            {
              stub = (IrmiServer) registry.lookup("Server");
              stub.updateGameLobbyClient(this.gameLobbyController.getIGameLobby());
                
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

    @Override
    public void updateChat() throws RemoteException {
        gameLobbyController.updateChat();
    }

    @Override
    public ILobbyController getLobbyController() throws RemoteException
    {
        return this.lobbyController;
    }

    @Override
    public void updatePlayerList() throws RemoteException
    {
        this.gameLobbyController.updatePlayerList();
    }

    @Override
    public void updateReady() throws RemoteException {
        this.gameLobbyController.ready();
    }
    
       
    
}

