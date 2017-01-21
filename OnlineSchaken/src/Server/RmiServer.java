/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.IGameLobby;
import Shared.IinGameController;
import Shared.IrmiClient;
import java.awt.Point;
import java.rmi.RemoteException;
import Shared.IrmiServer;
import database.Database;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import onlineschaken.Chatline;
import onlineschaken.Game;
import onlineschaken.Gamelobby;
import onlineschaken.Pawn;
import onlineschaken.Piece;
import onlineschaken.Player;

/**
 *
 * @author Sander
 */
public class RmiServer implements IrmiServer
{

    private ArrayList<String> GameLobbys = new ArrayList<>();
    private ArrayList<IrmiClient> Clients = new ArrayList<>();
    private static final Database database = new Database();
    private String ip = "127.0.0.1"/*"169.254.183.180"*/;

    @Override
    public void doTurn(Point section1, Point section2, double time, String userName) throws RemoteException
    {
        for (IrmiClient i : Clients)
        {
            if (i.getUserName().equals(i.GetGameController().getPlayer1()) && userName.equals(i.GetGameController().getPlayer2()))
            {
                try
                {
                    IinGameController controller = i.GetGameController();
                    ArrayList<String> MoveHistory = controller.GetMyMoveHisotry();
                    controller.move(section1, section2, time);   //.getTurn(section1, section2, time);
                    new Thread(()
                            -> 
                            {
                                try
                                {
                                    database.addMoveToHistory(i.getUserName(), userName, MoveHistory.get(MoveHistory.size() - 1), MoveHistory.size());
                                } catch (RemoteException ex)
                                {
                                    Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                                }
                    }).start();
                } catch (RemoteException ex)
                {
                    Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (i.getUserName().equals(i.GetGameController().getPlayer2()) && userName.equals(i.GetGameController().getPlayer1()))
            {
                try
                {
                    i.GetGameController().move(section1, section2, time);   //.getTurn(section1, section2, time);
                } catch (RemoteException ex)
                {
                    Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (Player p : i.GetGameController().getSpectators())
            {
                if (p.getUsername().equals(i.getUserName()))
                {
                    try
                    {
                        i.GetGameController().move(section1, section2, time);   //.getTurn(section1, section2, time);
                    } catch (RemoteException ex)
                    {
                        Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    @Override
    public void test() throws RemoteException
    {
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
        Registry registry = LocateRegistry.getRegistry(ip, 666);
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
            Registry registry = LocateRegistry.getRegistry(ip, 666);
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
            Registry registry = LocateRegistry.getRegistry(ip, 666);
            IGameLobby lobby = (IGameLobby) registry.lookup(naamLobby);
            lobby.SendMessage(message);
            for (IrmiClient i : Clients)
            {

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
                for (Player p : lobby.getSpectators())
                {
                    if (p.getUsername().equals(i.getUserName()))
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
            Registry registry = LocateRegistry.getRegistry(ip, 666);
            IGameLobby lobby = (IGameLobby) registry.lookup(lobbyName);
            lobby.PlayerIsReady(ready, lobbyName, userName);
            for (IrmiClient i : Clients)
            {
                if (lobby.checkPlayer1Exists() && lobby.checkPlayer2Exists())
                {

                    if (i.getUserName().equals(lobby.GetPlayer1().getUsername()) && userName.equals(lobby.GetPlayer2().getUsername()))
                    {
                        i.updateReady(userName);
                    } else if (i.getUserName().equals(lobby.GetPlayer2().getUsername()) && userName.equals(lobby.GetPlayer1().getUsername()))
                    {
                        i.updateReady(userName);
                    }
                    for (Player p : lobby.getSpectators())
                    {
                        if (p.getUsername().equals(i.getUserName()))
                        {
                            i.updateReady(userName);
                        }
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
        System.out.println("Before " + GameLobbys);
        GameLobbys.remove(gameLobbyname);
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
    public void updateGameLobbyClient(IGameLobby lobby) throws RemoteException
    {
        for (IrmiClient i : Clients)
        {
            try
            {
                if (i.getGameLobbyController() != null)
                {
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
                    for (Player p : lobby.getSpectators())
                    {
                        if (p.getUsername().equals(i.getUserName()))
                        {
                            i.updatePlayerList();
                        }
                    }
                }
            } catch (RemoteException ex)
            {
                Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void registerClient(IrmiClient client) throws RemoteException
    {
        Clients.add(client);
    }

    @Override
    public int IrmiClientCounter() throws RemoteException
    {
        if (this.Clients.size() == 0)
        {
            return 1;
        }
        return this.Clients.size() + 1;
    }

    @Override
    public void SendInGameMessage(IinGameController controller, Chatline message)
    {
        for (IrmiClient client : Clients)
        {
            try
            {
                if (client.GetGameController().getPlayer1().equals(controller.getPlayer1()))
                {
                    client.UpdateInGameChat(message);
                } else if (client.GetGameController().getPlayer2().equals(controller.getPlayer2()))
                {
                    client.UpdateInGameChat(message);
                }
            } catch (RemoteException ex)
            {
                Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<Point> getLastMove(String userName) throws RemoteException
    {
        for (IrmiClient client : Clients)
        {

            for (IrmiClient i : Clients)
            {
                if (i.getUserName().equals(i.GetGameController().getPlayer1()) && userName.equals(i.GetGameController().getPlayer2()))
                {
                    try
                    {
                        System.out.println("Client op server :" + i);
                        return i.GetGameController().getLocalLastMove();
                    } catch (RemoteException ex)
                    {
                        Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                        return null;
                    }
                } else if (i.getUserName().equals(i.GetGameController().getPlayer2()) && userName.equals(i.GetGameController().getPlayer1()))
                {
                    try
                    {
                        return i.GetGameController().getLocalLastMove();
                    } catch (RemoteException ex)
                    {
                        Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                        return null;
                    }
                }
                return null;
            }
        }
        return null;
    }

    @Override
    public void PromotePawn(Piece piece, Pawn pawn, String receiver) throws RemoteException
    {
        for (IrmiClient c : Clients)
        {
            if (c.getUserName().equals(receiver))
            {
                c.PromotePawn(piece, pawn);
            }
            for (Player p : c.GetGameController().getSpectators())
            {
                c.PromotePawn(piece, pawn);
            }
        }
    }

    @Override
    public void PlayerIsPromoting(IinGameController sender, String Username) throws RemoteException
    {
        String Receiver = null;
        if (Username != null && sender.getPlayer1().equals(Username) || sender.getPlayer2().equals(Username))
        {
            if (sender.getPlayer1().equals(Username))
            {
                Receiver = sender.getPlayer2();
            } else if (sender.getPlayer2().equals(Username))
            {
                Receiver = sender.getPlayer1();
            }
            for (IrmiClient c : Clients)
            {
                if (c.getUserName().equals(Receiver))
                {
                    c.isWaitinPromotion(true);
                    break;
                }
            }
        }

    }

    @Override
    public boolean addFriend(String player, String Friend) throws RemoteException
    {
        return database.addFriend(player, Friend);
    }

    

    @Override
    public Player selectPlayer(String username) throws RemoteException
    {
        return database.selectPlayer(username);
    }

    @Override
    public boolean insterPlayer(String username, String password, String email) throws RemoteException
    {
        return database.insertPlayer(username, password, email);
    }

    @Override
    public void SendSurrender(String loser, String winner) throws RemoteException
    {
        for (IrmiClient i : Clients)
        {
            if (i.getUserName().equals(winner))
            {
                i.GetGameController().ReceiveSurrender(loser);
                for (Player p : i.GetGameController().getSpectators())
                {
                    i.GetGameController().ReceiveSurrender(loser);
                }
                break;
            }
        }
    }

    @Override
    public void draw(String userNameOtherPlayer) throws RemoteException
    {
        for (IrmiClient client : Clients)
        {
            try
            {
                if (client.GetGameController().getPlayer1().equals(userNameOtherPlayer) && client.getUserName().equals(userNameOtherPlayer))
                {
                    client.GetGameController().recieveDraw();
                } else if (client.GetGameController().getPlayer2().equals(userNameOtherPlayer) && client.getUserName().equals(userNameOtherPlayer))
                {
                    client.GetGameController().recieveDraw();
                }
                for (Player p : client.GetGameController().getSpectators())
                {
                    client.GetGameController().recieveDraw();
                }
            } catch (RemoteException ex)
            {
                Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void recieveGameover(String userNameOtherPlayer) throws RemoteException
    {
        for (IrmiClient client : Clients)
        {
            try
            {
                if (client.GetGameController().getPlayer1().equals(userNameOtherPlayer) && client.getUserName().equals(userNameOtherPlayer))
                {
                    client.GetGameController().recieveGameover();
                } else if (client.GetGameController().getPlayer2().equals(userNameOtherPlayer) && client.getUserName().equals(userNameOtherPlayer))
                {
                    client.GetGameController().recieveGameover();
                }
                client.GetGameController().recieveGameover();
            } catch (RemoteException ex)
            {
                Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<Game> GetUserGames(String username) throws RemoteException
    {
        ArrayList<Game> games = database.GetUsersGames(username);
        return games;
    }

    @Override
    public void SaveGame(Game game, String leaver) throws RemoteException
    {
        database.SaveGame(game);
        if (!game.getPlayer1().getUsername().equals(leaver))
        {
            leaveGameLobbys(game.getPlayer1());
        } else if (!game.getPlayer2().getUsername().equals(leaver))
        {
            leaveGameLobbys(game.getPlayer2());
        }
        if (game.getSpectators() != null)
        {
            for (Player player : game.getSpectators())
            {
                leaveGameLobbys(player);
            }
        }

    }

    public void leaveGameLobbys(Player player)
    {
        for (IrmiClient client : Clients)
        {
            try
            {
                if (client.getUserName().equals(player.getUsername()))
                {
                    client.GetGameController().leaveGame();
                }
            } catch (RemoteException ex)
            {
                Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean RestartGame(String receiver,Game game)throws RemoteException
    {
        for (IrmiClient client : Clients)
        {
            try
            {
                if (client.getUserName().equals(receiver))
                {
                    if (client.getLobbyController()!=null)
                    {
                        client.getLobbyController().RestartGame(game);
                        return true;
                    }
                }
            } catch (RemoteException ex)
            {
                Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
           return false;
    }

}
