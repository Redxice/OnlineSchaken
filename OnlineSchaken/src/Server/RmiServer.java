/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.IGameLobby;
import java.awt.Point;
import java.rmi.RemoteException;
import Shared.IrmiServer;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.logging.Level;
import java.util.logging.Logger;
import onlineschaken.Chatline;
import onlineschaken.Gamelobby;
import onlineschaken.Player;

/**
 *
 * @author Sander
 */
public class RmiServer implements IrmiServer
{  
    private ArrayList<String> GameLobbys = new ArrayList<>();
    
    @Override
    public void doTurn(Point section1, Point section2, double time) throws RemoteException
    {
        System.out.println(section1.toString() + section2.toString() + time);
    }

    @Override
    public void test() throws RemoteException
    {
        System.out.println("works");
    }

    @Override
    public List<IGameLobby> GameLobbys() throws RemoteException
    {
       return null; 
    }

/**
 * Deze Methode checked niet of de lobbyNaam & player null is.
 * 
 * @param lobbyNaam
 * @param player1
 * @return
 * @throws RemoteException 
 */
    @Override
    public boolean CreateGameLobby(String lobbyNaam, Player player1) throws RemoteException
    {   
        
            Gamelobby lobby = new Gamelobby(lobbyNaam,player1);            
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 666);
            IGameLobby LobbyCheck=null;
        try
        {
            LobbyCheck = (IGameLobby)registry.lookup(lobbyNaam);
             return false;
        } catch (NotBoundException | AccessException ex)
        {
            registry.rebind(lobby.getNaam(),(IGameLobby)lobby);
            this.GameLobbys.add(lobby.getName());
            return true;
            
        }
    }
/**
 * Deze methode wordt gebruikt om een gamelobby te vinden en te returnen aan de gebruiker. 
 * Care als deze methode niks kan vinden returned hij null. Check dus altijd van te voren 
 * of de return waarde niet null is.
 * @param gamelobbyName
 * @return
 * @throws RemoteException 
 */
    @Override
    public IGameLobby GetIGameLobby(String gamelobbyName) throws RemoteException
    {
        try
        {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 666);
            IGameLobby lobby = (IGameLobby)registry.lookup(gamelobbyName);
            return lobby;
        } catch (NotBoundException ex)
        {
            Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessException ex)
        {
            Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
    }

    @Override

    public ArrayList<String> GetGameLobbys() throws RemoteException
    {
       return GameLobbys;
    }
    public void SendMessage(Chatline message, String naamLobby) throws RemoteException
    {        
        try
        {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 666);
        IGameLobby lobby = (IGameLobby)registry.lookup(naamLobby);
        System.out.println(message.getMessage());
        lobby.SendMessage(message);
        } catch (NotBoundException ex)
        {
        Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessException ex)
        {
        Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void playerReady(boolean ready,String lobbyName, String userName) throws RemoteException
    {
        try
        {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 666);
        IGameLobby lobby = (IGameLobby)registry.lookup(lobbyName);
        System.out.println(ready + lobbyName + userName);
        lobby.PlayerIsReady(ready,lobbyName,userName);
        } catch (NotBoundException ex)
        {
        Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessException ex)
        {
        Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
        }   

    }
    
}
