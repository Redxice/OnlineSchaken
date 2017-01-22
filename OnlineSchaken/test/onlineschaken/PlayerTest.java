/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import Server.ClientApp;
import Shared.IrmiClient;
import gui.IngameController;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

/**
 *
 * @author Sander
 */
public class PlayerTest
{
    private Section section;
    
    public PlayerTest()
    {
    }

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    
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
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of getUsername method, of class Player.
     */
    @Test
    public void testGetUsername()
    {
        System.out.println("getUsername");
        Player instance = new Player("testUser", "testPassword", 10);
        String expResult = "testUser";
        String result = instance.getUsername();
        assertEquals(expResult, result);
    }

    /**
     * Test of setUsername method, of class Player.
     */
    @Test
    public void testSetUsername()
    {
        System.out.println("setUsername");
        String expResult = "testUser2";
        Player instance = new Player("testUser", "testPassword", 10);
        instance.setUsername(expResult);
        assertEquals(expResult, instance.getUsername());
    }

    /**
     * Test of getPassword method, of class Player.
     */
    @Test
    public void testGetPassword()
    {
        System.out.println("getPassword");
        Player instance = new Player("testUser", "testPassword", 10);
        String expResult = "testPassword";
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPassword method, of class Player.
     */
    @Test
    public void testSetPassword()
    {
        System.out.println("setPassword");
        String expResult = "testPassword2";
        Player instance = new Player("testUser", "testPassword", 10);
        instance.setPassword(expResult);
        assertEquals(expResult, instance.getPassword());
    }

    /**
     * Test of getColor method, of class Player.
     */
    @Test
    public void testGetColor()
    {
        System.out.println("getColor");
        Player instance = new Player();
        String expResult = "black";
        instance.setColor("black");
        String result = instance.getColor();
        assertEquals(expResult, result);
    }

    /**
     * Test of setColor method, of class Player.
     */
    @Test
    public void testSetColor()
    {
        System.out.println("setColor");
        String expResult = "white";
        Player instance = new Player();
        instance.setColor(expResult);
        assertEquals(expResult, instance.getColor());
    }

    /**
     * Test of getRating method, of class Player.
     */
    @Test
    public void testGetRating()
    {
        System.out.println("getRating");
        Player instance = new Player("testUser", "testPassword", 10);
        int expResult = 10;
        int result = instance.getRating();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRating method, of class Player.
     */
    @Test
    public void testSetRating()
    {
        System.out.println("setRating");
        int expResult = 15;
        Player instance = new Player();
        instance.setRating(expResult);
        assertEquals(expResult, instance.getRating());
    }

    /**
     * Test of isOnline method, of class Player.
     */
    @Test
    public void testIsOnline()
    {
        System.out.println("isOnline");
        Player instance = new Player();
        boolean expResult = false;
        boolean result = instance.isOnline();
        assertEquals(expResult, result);
    }

    /**
     * Test of setOnline method, of class Player.
     */
    @Test
    public void testSetOnline()
    {
        System.out.println("setOnline");
        boolean expResult = true;
        Player instance = new Player();
        instance.setOnline(expResult);
        assertEquals(expResult, instance.isOnline());
    }

    /**
     * Test of getGame method, of class Player.
     */
    @Test
    public void testGetGame()
    {
        try
        {
            System.out.println("getGame");
            Player p1 = new Player("JOEP", "d", 20);
            Player p2 = new Player("JOEP2", "d", 20);
            IngameController controller = new IngameController();
            IrmiClient app = new ClientApp();
            Game expResult = new Game(p1, p2, app, controller);
            p1.setGame(expResult);
            Game result = p1.getGame();
            assertEquals(expResult, result);
        } catch (Exception e)
        {

        }
    }

    /**
     * Test of setGame method, of class Player.
     */
    @Test
    public void testSetGame()
    {
        try
        {
            System.out.println("getGame");
            Player p1 = new Player("JOEP", "d", 20);
            Player p2 = new Player("JOEP2", "d", 20);
            IngameController controller = new IngameController();
            IrmiClient app = new ClientApp();
            Game expResult = new Game(p1, p2, app, controller);
            p1.setGame(expResult);
            Game result = p1.getGame();
            assertEquals(expResult, result);
        } catch (Exception e)
        {

        }
    }

    /**
     * Test of getFriends method, of class Player.
     */
    @Test
    public void testGetFriends()
    {
        System.out.println("getFriends");
        Player instance = new Player();
        List<Player> expResult = new ArrayList<>();
        Player p1 = new Player();
        expResult.add(p1);
        List<Player> result = instance.getFriends();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFriends method, of class Player.
     */
    @Test
    public void testSetFriends()
    {
        System.out.println("setFriends");
        List<Player> expResult = new ArrayList<>();
        Player p1 = new Player();
        expResult.add(p1);
        Player instance = new Player();
        instance.setFriends(expResult);
        List<Player> result = instance.getFriends();
        assertEquals(expResult, result);
    }

    /**
     * Test of addFriend method, of class Player.
     */
    @Test
    public void testAddFriend()
    {
        System.out.println("addFriend");
        Player instance = new Player();
        Player expResult = new Player();
        instance.addFriend(expResult);
        Player result = instance.getFriends().get(0);
        assertEquals(expResult, result);
    }

    /**
     * Test of removeFriend method, of class Player.
     */
    @Test
    public void testRemoveFriend()
    {
        System.out.println("removeFriend");
        Player instance = new Player();
        Player expResult = new Player();
        instance.addFriend(expResult);
        instance.removeFriend(expResult);
        Player result = instance.getFriends().get(0);
        assertEquals(expResult, result);
    }

    /**
     * Test of addHistory method, of class Player.
     */
    @Test
    public void testAddHistory()
    {
        try
        {
            System.out.println("addHistory");
            Player p1 = new Player("JOEP", "d", 20);
            Player p2 = new Player("JOEP2", "d", 20);
            IngameController controller = new IngameController();
            IrmiClient app = new ClientApp();
            Game expResult = new Game(p1, p2, app, controller);
            p1.addHistory(expResult);
            Game result = p1.getHistory().get(0);
            assertEquals(expResult, result);
        } catch (Exception e)
        {

        }
    }

    /**
     * Test of getPieces method, of class Player.
     */
    @Test
    public void testGetPieces()
    {
        System.out.println("getPieces");
        List<Piece> pieces = new ArrayList<>();
        Player p1 = new Player("JOEP", "d", 20);
        Pawn expResult = new Pawn("black", p1, section);
        pieces.add(expResult);
        Player instance = new Player();
        instance.setPieces(pieces);
        assertEquals(expResult, instance.getPieces().get(0));
    }

    /**
     * Test of setPieces method, of class Player.
     */
    @Test
    public void testSetPieces()
    {
        System.out.println("setPieces");
        List<Piece> pieces = new ArrayList<>();
        Player p1 = new Player("JOEP", "d", 20);
        Pawn expResult = new Pawn("black", p1, section);
        pieces.add(expResult);
        Player instance = new Player();
        instance.setPieces(pieces);
        assertEquals(expResult, instance.getPieces().get(0));
    }

    /**
     * Test of getHistory method, of class Player.
     */
    @Test
    public void testGetHistory()
    {
        try
        {
            System.out.println("getHistory");
            Player p1 = new Player("JOEP", "d", 20);
            Player p2 = new Player("JOEP2", "d", 20);
            IngameController controller = new IngameController();
            IrmiClient app = new ClientApp();
            Game expResult = new Game(p1, p2, app, controller);
            p1.addHistory(expResult);
            Game result = p1.getHistory().get(0);
            assertEquals(expResult, result);
        } catch (Exception e)
        {

        }
    }

    /**
     * Test of setHistory method, of class Player.
     */
    @Test
    public void testSetHistory()
    {
        try
        {
            System.out.println("setHistory");
            Player p1 = new Player("JOEP", "d", 20);
            Player p2 = new Player("JOEP2", "d", 20);
            IngameController controller = new IngameController();
            IrmiClient app = new ClientApp();
            Game expResult = new Game(p1, p2, app, controller);
            List<Game> history = new ArrayList<>();
            history.add(expResult);
            p1.setHistory(history);
            Game result = p1.getHistory().get(0);
            assertEquals(expResult, result);
        } catch (Exception e)
        {

        }
    }

    /**
     * Test of removePiece method, of class Player.
     */
    @Test
    public void testRemovePiece()
    {
        System.out.println("removePiece");
        List<Piece> pieces = new ArrayList<>();
        Player p1 = new Player("JOEP", "d", 20);
        Pawn pawn = new Pawn("black", p1, section);
        pieces.add(pawn);
        Player instance = new Player();
        instance.setPieces(pieces);
        instance.removePiece(pawn);
        int expResult = 0;
        assertEquals(expResult, instance.getPieces().size());
    }

    /**
     * Test of getActiveGames method, of class Player.
     */
    @Test
    public void testGetActiveGames()
    {
        try
        {
            System.out.println("getActiveGame");
            Player p1 = new Player("JOEP", "d", 20);
            Player p2 = new Player("JOEP2", "d", 20);
            IngameController controller = new IngameController();
            IrmiClient app = new ClientApp();
            Game game = new Game(p1, p2, app, controller);
            List<Game> expResult = new ArrayList<>();
            expResult.add(game);
            p1.setGames(expResult);
            List<Game> result = p1.getActiveGames();
            assertEquals(expResult, result);
        } catch (Exception e)
        {

        }
    }

    /**
     * Test of setGames method, of class Player.
     */
    @Test
    public void testSetGames()
    {
        try
        {
            System.out.println("setGames");
            Player p1 = new Player("JOEP", "d", 20);
            Player p2 = new Player("JOEP2", "d", 20);
            IngameController controller = new IngameController();
            IrmiClient app = new ClientApp();
            Game game = new Game(p1, p2, app, controller);
            List<Game> expResult = new ArrayList<>();
            expResult.add(game);
            p1.setGames(expResult);
            List<Game> result = p1.getActiveGames();
            assertEquals(expResult, result);
        } catch (Exception e)
        {

        }
    }

    /**
     * Test of toString method, of class Player.
     */
    @Test
    public void testToString()
    {
        System.out.println("toString");
        Player instance = new Player("testUser", "testPassword", 100);
        String expResult = "username=testUser, rating=100";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

}
