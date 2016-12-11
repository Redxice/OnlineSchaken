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
        try
        {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1"/*"169.254.183.180"*/, 600);
            IrmiClient stub;
            try
            {
                stub = (IrmiClient) registry.lookup("Client");
                stub.getTurn(new Point(5, 0), new Point(7, 2), 3.00);
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
        GameLobbys.remove(gameLobbyname);
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
