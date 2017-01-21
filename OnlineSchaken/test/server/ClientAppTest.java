/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import Server.*;
import Shared.IGameLobby;
import Shared.IGameLobbyController;
import Shared.ILobbyController;
import Shared.IinGameController;
import Shared.IrmiClient;
import Shared.IrmiServer;
import gui.IngameController;
import java.awt.Point;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import onlineschaken.Chatline;
import onlineschaken.Game;
import onlineschaken.Pawn;
import onlineschaken.Piece;
import onlineschaken.Player;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author redxice
 */
public class ClientAppTest
{

    private RmiServer server = null;
    private ClientApp client = null;

    @BeforeClass
    public static void setUpClass()
    {
        
    }

    @AfterClass
    public static void tearDownClass()
    {
        
    }

    @Before
    public void setUp()
    {
        try
        {
            server = new RmiServer();
            IrmiServer stub = (IrmiServer) UnicastRemoteObject.exportObject(server, 0);
            //Bind the remote object stub in the registry
            Registry registry = LocateRegistry.createRegistry(666);
            registry.bind("Server", stub);
        } catch (RemoteException ex)
        {
            Logger.getLogger(ClientAppTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AlreadyBoundException ex)
        {
            Logger.getLogger(ClientAppTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown()
    {
        try
        {
            Registry registry = LocateRegistry.createRegistry(666);
            registry.unbind("Server");
        } catch (RemoteException ex)
        {
            Logger.getLogger(ClientAppTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex)
        {
            Logger.getLogger(ClientAppTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    /**
     * Test of createGameLobby method, of class ClientApp.
     */
    @Test
    public void testCreateGameLobby()
    {
        String LobbyName = "Test";
        Player host = new Player();
        ClientApp instance = new ClientApp();
        boolean expResult = true;
        boolean result = instance.createGameLobby(LobbyName, host);
        assertEquals(expResult, result);
    }

    /**
     * Test of sendTurn method, of class ClientApp.
     */
    @Test
    public void testSendTurn() throws Exception
    { try{
        Point prev = new Point(0,1);
        Point next = new Point(0,2);
        double time = 0.0;
        IrmiClient instance = new ClientApp();
        instance.sendTurn(prev, next, time);
        fail("Hij heeft de gegevens niet proberen door te sturen naar de server");
    }catch(RemoteException e){
      
    }
    }

    /**
     * Test of GetGameLobbys method, of class ClientApp.
     */
    @Test
    public void testGetGameLobbys()
    {
        ClientApp instance = new ClientApp();
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.GetGameLobbys();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SendMessage method, of class ClientApp.
     */
    @Test
    public void testSendMessage()
    {
        Chatline chatline = null;
        String naamLobby = "";
        ClientApp instance = new ClientApp();
        instance.SendMessage(chatline, naamLobby);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of playerReady method, of class ClientApp.
     */
    @Test
    public void testPlayerReady()
    {
        boolean ready = false;
        String lobbyName = "";
        String userName = "";
        ClientApp instance = new ClientApp();
        instance.playerReady(ready, lobbyName, userName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetGameLobby method, of class ClientApp.
     */
    @Test
    public void testGetGameLobby()
    {
        String LobbyName = "";
        ClientApp instance = new ClientApp();
        IGameLobby expResult = null;
        IGameLobby result = instance.GetGameLobby(LobbyName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unBindLobby method, of class ClientApp.
     */
    @Test
    public void testUnBindLobby()
    {
        String lobbyName = "";
        ClientApp instance = new ClientApp();
        instance.unBindLobby(lobbyName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTurn method, of class ClientApp.
     */
    @Test
    public void testGetTurn() throws Exception
    {
        Point section1 = null;
        Point section2 = null;
        double time = 0.0;
        ClientApp instance = new ClientApp();
        instance.getTurn(section1, section2, time);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of UpdateLobbyController method, of class ClientApp.
     */
    @Test
    public void testUpdateLobbyController() throws Exception
    {
        ClientApp instance = new ClientApp();
        instance.UpdateLobbyController();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLobbyController method, of class ClientApp.
     */
    @Test
    public void testSetLobbyController() throws Exception
    {
        ILobbyController controller = null;
        ClientApp instance = new ClientApp();
        instance.setLobbyController(controller);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserName method, of class ClientApp.
     */
    @Test
    public void testGetUserName()
    {
        ClientApp instance = new ClientApp();
        String expResult = "";
        String result = instance.getUserName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUserName method, of class ClientApp.
     */
    @Test
    public void testSetUserName()
    {
        String userName = "";
        ClientApp instance = new ClientApp();
        instance.setUserName(userName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGameLobbyController method, of class ClientApp.
     */
    @Test
    public void testGetGameLobbyController()
    {
        ClientApp instance = new ClientApp();
        IGameLobbyController expResult = null;
        IGameLobbyController result = instance.getGameLobbyController();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGameLobbyController method, of class ClientApp.
     */
    @Test
    public void testSetGameLobbyController() throws Exception
    {
        IGameLobbyController controller = null;
        ClientApp instance = new ClientApp();
        instance.setGameLobbyController(controller);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of RefreshGameLobby method, of class ClientApp.
     */
    @Test
    public void testRefreshGameLobby() throws Exception
    {
        ClientApp instance = new ClientApp();
        instance.RefreshGameLobby();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateChat method, of class ClientApp.
     */
    @Test
    public void testUpdateChat() throws Exception
    {
        ClientApp instance = new ClientApp();
        instance.updateChat();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLobbyController method, of class ClientApp.
     */
    @Test
    public void testGetLobbyController() throws Exception
    {
        ClientApp instance = new ClientApp();
        ILobbyController expResult = null;
        ILobbyController result = instance.getLobbyController();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updatePlayerList method, of class ClientApp.
     */
    @Test
    public void testUpdatePlayerList() throws Exception
    {
        ClientApp instance = new ClientApp();
        instance.updatePlayerList();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetGameController method, of class ClientApp.
     */
    @Test
    public void testGetGameController()
    {
        ClientApp instance = new ClientApp();
        IinGameController expResult = null;
        IinGameController result = instance.GetGameController();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGame method, of class ClientApp.
     */
    @Test
    public void testSetGame()
    {
        IngameController game = null;
        ClientApp instance = new ClientApp();
        instance.setGame(game);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateReady method, of class ClientApp.
     */
    @Test
    public void testUpdateReady() throws Exception
    {
        String playerName = "";
        ClientApp instance = new ClientApp();
        instance.updateReady(playerName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIinGameController method, of class ClientApp.
     */
    @Test
    public void testSetIinGameController() throws Exception
    {
        IinGameController controller = null;
        ClientApp instance = new ClientApp();
        instance.setIinGameController(controller);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of UpdateInGameChat method, of class ClientApp.
     */
    @Test
    public void testUpdateInGameChat() throws Exception
    {
        Chatline message = null;
        ClientApp instance = new ClientApp();
        instance.UpdateInGameChat(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendInGameMessage method, of class ClientApp.
     */
    @Test
    public void testSendInGameMessage() throws Exception
    {
        Chatline message = null;
        ClientApp instance = new ClientApp();
        instance.sendInGameMessage(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastMove method, of class ClientApp.
     */
    @Test
    public void testGetLastMove() throws Exception
    {
        ClientApp instance = new ClientApp();
        ArrayList<Point> expResult = null;
        ArrayList<Point> result = instance.getLastMove();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of castPiece method, of class ClientApp.
     */
    @Test
    public void testCastPiece() throws Exception
    {
        Piece piece = null;
        Pawn pawn = null;
        ClientApp instance = new ClientApp();
        instance.castPiece(piece, pawn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isPromoting method, of class ClientApp.
     */
    @Test
    public void testIsPromoting() throws Exception
    {
        ClientApp instance = new ClientApp();
        instance.isPromoting();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isWaitinPromotion method, of class ClientApp.
     */
    @Test
    public void testIsWaitinPromotion() throws Exception
    {
        boolean bool = false;
        ClientApp instance = new ClientApp();
        instance.isWaitinPromotion(bool);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of PromotePawn method, of class ClientApp.
     */
    @Test
    public void testPromotePawn() throws Exception
    {
        Piece piece = null;
        Pawn pawn = null;
        ClientApp instance = new ClientApp();
        instance.PromotePawn(piece, pawn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addFriend method, of class ClientApp.
     */
    @Test
    public void testAddFriend() throws Exception
    {
        String Player = "";
        String Friend = "";
        ClientApp instance = new ClientApp();
        boolean expResult = false;
        boolean result = instance.addFriend(Player, Friend);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selectPlayer method, of class ClientApp.
     */
    @Test
    public void testSelectPlayer() throws Exception
    {
        String username = "";
        ClientApp instance = new ClientApp();
        Player expResult = null;
        Player result = instance.selectPlayer(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertPlayer method, of class ClientApp.
     */
    @Test
    public void testInsertPlayer() throws Exception
    {
        String username = "";
        String password = "";
        String email = "";
        ClientApp instance = new ClientApp();
        boolean expResult = false;
        boolean result = instance.insertPlayer(username, password, email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of surrender method, of class ClientApp.
     */
    @Test
    public void testSurrender() throws Exception
    {
        ClientApp instance = new ClientApp();
        instance.surrender();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of draw method, of class ClientApp.
     */
    @Test
    public void testDraw() throws Exception
    {
        String userNameOtherPlayer = "";
        ClientApp instance = new ClientApp();
        instance.draw(userNameOtherPlayer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendGameOver method, of class ClientApp.
     */
    @Test
    public void testSendGameOver() throws Exception
    {
        String userNameOtherPlayer = "";
        ClientApp instance = new ClientApp();
        instance.sendGameOver(userNameOtherPlayer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetGames method, of class ClientApp.
     */
    @Test
    public void testGetGames() throws Exception
    {
        String username = "";
        ClientApp instance = new ClientApp();
        ArrayList<Game> expResult = null;
        ArrayList<Game> result = instance.GetGames(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SaveGame method, of class ClientApp.
     */
    @Test
    public void testSaveGame() throws Exception
    {
        Game game = null;
        ClientApp instance = new ClientApp();
        instance.SaveGame(game);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of leaveGame method, of class ClientApp.
     */
    @Test
    public void testLeaveGame() throws Exception
    {
        ClientApp instance = new ClientApp();
        instance.leaveGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayer method, of class ClientApp.
     */
    @Test
    public void testGetPlayer()
    {
        ClientApp instance = new ClientApp();
        Player expResult = null;
        Player result = instance.getPlayer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPlayer method, of class ClientApp.
     */
    @Test
    public void testSetPlayer()
    {
        Player player = null;
        ClientApp instance = new ClientApp();
        instance.setPlayer(player);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of RestartGame method, of class ClientApp.
     */
    @Test
    public void testRestartGame() throws Exception
    {
        Game SelectedGame = null;
        ClientApp instance = new ClientApp();
        boolean expResult = false;
        boolean result = instance.RestartGame(SelectedGame);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
