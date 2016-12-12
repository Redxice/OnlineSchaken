/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.IGameLobby;
import Shared.IGameLobbyController;
import Shared.ILobbyController;
import Shared.IinGameController;
import Shared.IrmiClient;
import Shared.IrmiServer;
import gui.IngameController;
import java.awt.Point;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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
    private IinGameController game;
    private String ip = "169.254.183.180";
    private String userName;

    public ClientApp(){
        try
        {   
            Registry registry = LocateRegistry.getRegistry(ip, 666);
            IrmiServer stub = (IrmiServer)registry.lookup("Server");
            int count = stub.IrmiClientCounter();
            IrmiClient Test = this;
            UnicastRemoteObject.exportObject(Test, count);
            stub.registerClient(this);
            
            
        } catch (RemoteException ex)
        {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex)
        {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
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
   
    @Override
    public void sendTurn(Point prev, Point next, double time) throws RemoteException
    {
        try
        {
               
                Registry registry = LocateRegistry.getRegistry(ip, 666);
                IrmiServer stub;
                stub = (IrmiServer) registry.lookup("Server");
                stub.doTurn(prev, next, time,this.userName);
          
        } catch (RemoteException ex)
        {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex)
        {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
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
        game.move(section1, section2, time);
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

    /**
     * @return the game
     */
    @Override
    public IinGameController GetGameController()
    {
        return game;
    }

    /**
     * @param game the game to set
     */
    public void setGame(IngameController game)
    {
        this.game = game;
    }
    
    @Override
    public void updateReady() throws RemoteException {
        this.gameLobbyController.ready();
    }

    @Override
    public void setIinGameController(IinGameController controller) throws RemoteException
    {
        this.game=controller;
    }

    @Override
    public void UpdateInGameChat(Chatline message) throws RemoteException
    {
       this.game.updateChat(message);
    }

    @Override
    public void sendInGameMessage(Chatline message) throws RemoteException
    {
         try
        {
            Registry registry = LocateRegistry.getRegistry(ip, 666);
            IrmiServer stub;
            try
            {
              stub = (IrmiServer) registry.lookup("Server");
              stub.SendInGameMessage(game, message);
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
    public ArrayList<Point> getLastMove() throws RemoteException
    {
        try
        {
            Registry registry = LocateRegistry.getRegistry(ip, 666);
            IrmiServer stub;
            try
            {
                stub = (IrmiServer) registry.lookup("Server");
              return stub.getLastMove(userName);
                
            } catch (NotBoundException e)
            {
                System.err.println("Client exception:" + e.toString());
                e.printStackTrace();
                return null;
            }
        } catch (RemoteException e)
        {
            System.err.println("Server exception:" + e.toString());
            e.printStackTrace();
            return null;
        }
    }
    public void castPiece(Piece piece) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}

