/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.IGameLobby;
import Shared.ILobbyController;
import Shared.IrmiClient;
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
    private ArrayList<IrmiClient> Clients = new ArrayList<>();

    @Override
    public void doTurn(Point section1, Point section2, double time) throws RemoteException
    {
        for (IrmiClient client : Clients)
        {

            for (IrmiClient i : Clients)
            {
                try
                {
                    System.out.println("Client op server :" + i);
                    System.out.println("Controller op server :" + i.getGame());
                    i.getGame().move(section1, section2, time);   //.getTurn(section1, section2, time);
                    System.out.println("it moves 0.o");
                } catch (RemoteException ex)
                {
                    Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

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

        Gamelobby lobby = new Gamelobby(lobbyNaam, player1);
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 666);
        IGameLobby LobbyCheck = null;
        try
        {
            LobbyCheck = (IGameLobby) registry.lookup(lobbyNaam);
            return false;
        } catch (NotBoundException | AccessException ex)
        {
            registry.rebind(lobby.getNaam(), (IGameLobby) lobby);
            this.GameLobbys.add(lobby.getName());
            updateLobbysClients();
            return true;

        }
    }

    /**
     * Deze methode wordt gebruikt om een gamelobby te vinden en te returnen aan
     * de gebruiker. Care als deze methode niks kan vinden returned hij null.
     * Check dus altijd van te voren of de return waarde niet null is.
     *
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
            IGameLobby lobby = (IGameLobby) registry.lookup(gamelobbyName);
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
            IGameLobby lobby = (IGameLobby) registry.lookup(naamLobby);
            lobby.SendMessage(message);
            for (IrmiClient i : Clients)
            {
                System.out.println("Controller op server :" + i.getGameLobbyController());
                if (lobby.checkPlayer1Exists())
                {
                    if (i.getUserName().equals(lobby.GetPlayer1().getUsername()))
                    {
                        i.updateChat();
                    }
                }
                if (lobby.checkPlayer2Exists())
                {
                    if (i.getUserName().equals(lobby.GetPlayer2().getUsername()))
                    {
                        i.updateChat();
                    }
                }

            }
        } catch (NotBoundException ex)
        {
            Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessException ex)
        {
            Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void playerReady(boolean ready, String lobbyName, String userName) throws RemoteException
    {
        try
        {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 666);
            IGameLobby lobby = (IGameLobby) registry.lookup(lobbyName);
            lobby.PlayerIsReady(ready, lobbyName, userName);
            for (IrmiClient i : Clients)
            {                
                if(lobby.checkPlayer1Exists() && lobby.checkPlayer2Exists())
                {      
                    System.out.println("lobby player1 = " + lobby.GetPlayer1().getUsername());
                    System.out.println("lobby player2 = " + lobby.GetPlayer2().getUsername());
                    System.out.println("parameter user = " + userName);
                    if (i.getUserName().equals(lobby.GetPlayer1().getUsername()) && userName.equals(lobby.GetPlayer2().getUsername()))
                    {
                        System.out.println("Pleyer 2 heeft het verstuurt player 1 ontvangt het");
                        i.updateReady();
                    }
                    else if (i.getUserName().equals(lobby.GetPlayer2().getUsername()) && userName.equals(lobby.GetPlayer1().getUsername()))
                    {
                        System.out.println("Pleyer 1 heeft het verstuurt player 2 ontvangt het");
                        i.updateReady();
                    }
                }
            }
        } catch (NotBoundException ex)
        {
            Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessException ex)
        {
            Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void removeGameLobby(String gameLobbyname) throws RemoteException
    {
        System.out.println("Before "+GameLobbys);
        GameLobbys.remove(gameLobbyname);
        System.out.println("After "+GameLobbys);
        updateLobbysClients();
    }
    
    /**
     * stuur naar elke IrmiClient die een lobby controller heeft een update.
     */
    public void updateLobbysClients()
    {
        
        for (IrmiClient client : Clients)
        { 
            try
            {
                if (client.getLobbyController() != null)
                {
                    client.UpdateLobbyController();
                }
            } catch (RemoteException ex)
            {
                Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @Override
    public void updateGameLobbyClient(IGameLobby lobby)throws RemoteException
    {

        for (IrmiClient client : Clients)
        {

            for (IrmiClient i : Clients)
            {
                try
                {
                    System.out.println("Controller op server :" + i.getGameLobbyController());
                    if (lobby.checkPlayer1Exists())
                    {
                        if (i.getUserName().equals(lobby.GetPlayer1().getUsername()))
                        {
                            i.updatePlayerList();
                        }
                    }
                    if (lobby.checkPlayer2Exists())
                    {
                        if (i.getUserName().equals(lobby.GetPlayer2().getUsername()))
                        {
                            i.updatePlayerList();
                        }
                    }
                } catch (RemoteException ex)
                {
                    Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
    }

    @Override
    public void registerClient(IrmiClient client) throws RemoteException
    {
        Clients.add(client);
    }

}
