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

    private ArrayList<IGameLobby> gameLobbys = new ArrayList<>();
    private ILobbyController lobbyController;
    private IGameLobbyController gameLobbyController;
    private IinGameController game;
    private String ip = "127.0.0.1"/* "169.254.183.180"*/;
    private String userName;
    private Player player;
    private Registry registry;
    private IrmiServer stub;
    private ProfileController profileController;

    /**
     * constructor
     */
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
     * @return 
     */
    public boolean createGameLobby(String LobbyName, Player host)
    {

        try
        {
            return stub.CreateGameLobby(LobbyName, host);

        } catch (RemoteException e)
        {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    /**
     *
     * @param prev
     * @param next
     * @param time
     * @throws RemoteException
     */
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

    /**
     *
     * @param chatline
     * @param naamLobby
     */
    public void SendMessage(Chatline chatline, String naamLobby)
    {

        try
        {
            stub.SendMessage(chatline, naamLobby);
        } catch (RemoteException e)
        {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     *
     * @param ready
     * @param lobbyName
     * @param userName
     */
    public void playerReady(boolean ready, String lobbyName, String userName)
    {

        try
        {
            stub.playerReady(ready, lobbyName, userName);
        } catch (RemoteException e)
        {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     *
     * @param LobbyName
     * @return
     */
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

    /**
     *
     * @param lobbyName
     */
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
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     *
     * @param section1
     * @param section2
     * @param time
     * @throws RemoteException
     */
    @Override
    public void getTurn(Point section1, Point section2, double time) throws RemoteException
    {
        game.move(section1, section2, time);
    }

    /**
     *
     * @throws RemoteException
     */
    @Override
    public void UpdateLobbyController() throws RemoteException
    {
        if (lobbyController != null)
        {
            lobbyController.UpdateGameLobbys();
        }

    }

    /**
     *
     * @param controller
     * @throws RemoteException
     */
    @Override
    public void setLobbyController(ILobbyController controller) throws RemoteException
    {
        this.lobbyController = controller;
        this.gameLobbyController = null;
        this.game = null;
        this.profileController = null;
    }

    /**
     *
     * @return
     */
    @Override
    public String getUserName()
    {
        return userName;
    }

    /**
     *
     * @param userName
     */
    @Override
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

    /**
     *
     * @return
     */
    @Override
    public IGameLobbyController getGameLobbyController()
    {
        return gameLobbyController;
    }

    /**
     *
     * @param controller
     * @throws RemoteException
     */
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

    /**
     *
     * @throws RemoteException
     */
    @Override
    public void RefreshGameLobby() throws RemoteException
    {

        stub.updateGameLobbyClient(this.gameLobbyController.getIGameLobby());

    }

    /**
     *
     * @throws RemoteException
     */
    @Override
    public void updateChat() throws RemoteException
    {
        gameLobbyController.updateChat();
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public ILobbyController getLobbyController() throws RemoteException
    {
        return this.lobbyController;
    }

    /**
     *
     * @throws RemoteException
     */
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

    /**
     *
     * @param playerName
     * @throws RemoteException
     */
    @Override
    public void updateReady(String playerName) throws RemoteException
    {
        this.gameLobbyController.ready(playerName);
    }

    /**
     *
     * @param controller
     * @throws RemoteException
     */
    @Override
    public void setIinGameController(IinGameController controller) throws RemoteException
    {
        this.game = controller;
    }

    /**
     *
     * @param message
     * @throws RemoteException
     */
    @Override
    public void UpdateInGameChat(Chatline message) throws RemoteException
    {
        this.game.updateChat(message);
    }

    /**
     *
     * @param message
     * @throws RemoteException
     */
    @Override
    public void sendInGameMessage(Chatline message) throws RemoteException
    {
        stub.SendInGameMessage(game, message);
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
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

    /**
     *
     * @throws RemoteException
     */
    @Override
    public void isPromoting() throws RemoteException
    {
        stub.PlayerIsPromoting(this.game, this.userName);
    }

    /**
     *
     * @param bool
     * @throws RemoteException
     */
    @Override
    public void isWaitinPromotion(boolean bool) throws RemoteException
    {
        this.game.setIsWaitForPromotion(bool);
    }

    @Override
    public void PromotePawn(Piece piece, Pawn pawn) throws RemoteException
    {
        this.game.PromotePawn(piece, pawn);
    }

    /**
     *
     * @param Player
     * @param Friend
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean addFriend(String Player, String Friend) throws RemoteException
    {
        return stub.addFriend(Player, Friend);
    }

    /**
     *
     * @param username
     * @return
     * @throws RemoteException
     */
    @Override
    public Player selectPlayer(String username) throws RemoteException
    {
        return stub.selectPlayer(username);
    }

    /**
     *
     * @param username
     * @param password
     * @param email
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean insertPlayer(String username, String password, String email) throws RemoteException
    {
        return stub.insterPlayer(username, password, email);
    }

    /**
     *
     * @throws RemoteException
     */
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

    /**
     *
     * @param userNameOtherPlayer
     * @throws RemoteException
     */
    @Override
    public void draw(String userNameOtherPlayer) throws RemoteException
    {
        stub.draw(userNameOtherPlayer);
    }

    /**
     *
     * @param userNameOtherPlayer
     * @throws RemoteException
     */
    @Override
    public void sendGameOver(String userNameOtherPlayer) throws RemoteException
    {
        stub.recieveGameover(userNameOtherPlayer);
    }

    /**
     *
     * @param username
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<Game> GetGames(String username) throws RemoteException
    {
        ArrayList<Game> games;
        games = new ArrayList<>();
        games = stub.GetUserGames(username);
        return games;
    }

    /**
     *
     * @param game
     * @throws RemoteException
     */
    @Override
    public void SaveGame(Game game) throws RemoteException
    {
        stub.SaveGame(game, this.userName);
    }

    /**
     *
     * @throws RemoteException
     */
    @Override
    public void leaveGame() throws RemoteException
    {
        this.game.leaveGame();
    }

    /**
     *
     * @return
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     *
     * @param player
     */
    public void setPlayer(Player player)
    {
        this.player = player;
    }

    /**
     *
     * @param SelectedGame
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean RestartGame(Game SelectedGame)throws RemoteException
    {
        String receiver = null;
        if (this.getUserName().equals(SelectedGame.getPlayer1().getUsername()))
        {
            receiver = SelectedGame.getPlayer2().getUsername();
        } else if (this.getUserName().equals(SelectedGame.getPlayer2().getUsername()))
        {
            receiver = SelectedGame.getPlayer1().getUsername();
        }
        return stub.RestartGame(receiver,SelectedGame);
    }
    
}
