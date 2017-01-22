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
import gui.ProfileController;
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
    private ILobbyController lobbyController;
    private IGameLobbyController gameLobbyController;
    private IinGameController game;
    private String ip = "127.0.0.1"/* "169.254.183.180"*/;
    private String userName;
    private Player player;
    private Registry registry;
    private IrmiServer stub;
    private ProfileController profileController;

    public ClientApp()
    {

        try
        {
            registry = LocateRegistry.getRegistry(ip, 666);
            stub = (IrmiServer) registry.lookup("Server");
        } catch (RemoteException ex)
        {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex)
        {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    /**
     * Wordt aangeroepen in de GameLobbyController in de create GameLobby
     * methode.
     *
     * @param LobbyName
     * @param host
     */
    public boolean createGameLobby(String LobbyName, Player host)
    {

        try
        {
            return stub.CreateGameLobby(LobbyName, host);

        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void sendTurn(Point prev, Point next, double time) throws RemoteException
    {
        try
        {
            stub.doTurn(prev, next, time, this.userName);

        } catch (RemoteException ex)
        {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * haalt van de server al zijn gamelobbys op en returned deze. Deze methode
     * wordt aangeroepen in de initialize van de LobbyController.
     *
     * @return
     */
    public ArrayList<String> GetGameLobbys()
    {

        try
        {
            return stub.GetGameLobbys();
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
            stub.SendMessage(chatline, naamLobby);
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    public void playerReady(boolean ready, String lobbyName, String userName)
    {

        try
        {
            stub.playerReady(ready, lobbyName, userName);
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    public IGameLobby GetGameLobby(String LobbyName)
    {
        IGameLobby lobby = null;

        try
        {
            lobby = stub.GetIGameLobby(LobbyName);
        } catch (RemoteException ex)
        {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lobby;
    }

    public void unBindLobby(String lobbyName)
    {
        try
        {

            stub.removeGameLobby(lobbyName);
            try
            {
                registry.unbind(lobbyName);
            } catch (NotBoundException ex)
            {
                Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AccessException ex)
            {
                Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void getTurn(Point section1, Point section2, double time) throws RemoteException
    {
        game.move(section1, section2, time);
    }

    @Override
    public void UpdateLobbyController() throws RemoteException
    {
        if (lobbyController != null)
        {
            lobbyController.UpdateGameLobbys();
        }

    }

    @Override
    public void setLobbyController(ILobbyController controller) throws RemoteException
    {
        this.lobbyController = controller;
        this.gameLobbyController = null;
        this.game = null;
        this.profileController = null;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
        try
        {

            int count = stub.IrmiClientCounter();
            IrmiClient Test = this;
            UnicastRemoteObject.exportObject(Test, count);
            stub.registerClient(this);

        } catch (RemoteException ex)
        {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public IGameLobbyController getGameLobbyController()
    {
        return gameLobbyController;
    }

    @Override
    public void setGameLobbyController(IGameLobbyController controller) throws RemoteException
    {

        this.gameLobbyController = controller;
        this.lobbyController = null;
        if (this.gameLobbyController != null)
        {
            RefreshGameLobby();
        }

    }

    @Override
    public void RefreshGameLobby() throws RemoteException
    {

        stub.updateGameLobbyClient(this.gameLobbyController.getIGameLobby());

    }

    @Override
    public void updateChat() throws RemoteException
    {
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
    public void updateReady(String playerName) throws RemoteException
    {
        this.gameLobbyController.ready(playerName);
    }

    @Override
    public void setIinGameController(IinGameController controller) throws RemoteException
    {
        this.game = controller;
    }

    @Override
    public void UpdateInGameChat(Chatline message) throws RemoteException
    {
        this.game.updateChat(message);
    }

    @Override
    public void sendInGameMessage(Chatline message) throws RemoteException
    {
        stub.SendInGameMessage(game, message);
    }

    @Override
    public ArrayList<Point> getLastMove() throws RemoteException
    {
        return stub.getLastMove(userName);
    }

    @Override
    public void castPiece(Piece piece, Pawn pawn) throws RemoteException
    {
        String receiver = null;
        if (game.getPlayer1().equals(this.userName))
        {
            receiver = game.getPlayer2();
        } else if (game.getPlayer2().equals(userName))
        {
            receiver = game.getPlayer1();
        }
        stub.PromotePawn(piece, pawn, receiver);
    }

    @Override
    public void isPromoting() throws RemoteException
    {
        stub.PlayerIsPromoting(this.game, this.userName);
    }

    @Override
    public void isWaitinPromotion(boolean bool) throws RemoteException
    {
        this.game.setIsWaitingForPromotion(bool);
    }

    @Override
    public void PromotePawn(Piece piece, Pawn pawn) throws RemoteException
    {
        this.game.PromotePawn(piece, pawn);
    }

    @Override
    public boolean addFriend(String Player, String Friend) throws RemoteException
    {
        return stub.addFriend(Player, Friend);
    }

    @Override
    public Player selectPlayer(String username) throws RemoteException
    {
        return stub.selectPlayer(username);
    }

    @Override
    public boolean insertPlayer(String username, String password, String email) throws RemoteException
    {
        return stub.insterPlayer(username, password, email);
    }

    @Override
    public void surrender() throws RemoteException
    {
        String winner = null;
        if (this.getUserName().equals(this.game.getPlayer1()))
        {
            winner = game.getPlayer2();
        } else if (this.getUserName().equals(game.getPlayer2()))
        {
            winner = game.getPlayer1();
        }

        stub.SendSurrender(this.getUserName(), winner);
    }

    @Override
    public void draw(String userNameOtherPlayer) throws RemoteException
    {
        stub.draw(userNameOtherPlayer);
    }

    @Override
    public void sendGameOver(String userNameOtherPlayer) throws RemoteException
    {
        stub.recieveGameover(userNameOtherPlayer);
    }

    @Override
    public ArrayList<Game> GetGames(String username) throws RemoteException
    {
        ArrayList<Game> games = new ArrayList<>();
        games = stub.GetUserGames(username);

        return games;
    }

    @Override
    public void SaveGame(Game game) throws RemoteException
    {
        stub.SaveGame(game, this.userName);
    }

    @Override
    public void leaveGame() throws RemoteException
    {
        this.game.leaveGame();
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    @Override
    public boolean RestartGame(Game SelectedGame)throws RemoteException
    {
        String receiver = null;
        if (this.getUserName().equals(SelectedGame.getPlayer1().getUsername()))
        {
            receiver = SelectedGame.getPlayer2().getUsername();
        } else if (this.getUserName().equals(SelectedGame.getPlayer2()))
        {
            receiver = SelectedGame.getPlayer1().getUsername();
        }
        return stub.RestartGame(receiver,SelectedGame);
    }
    
}
