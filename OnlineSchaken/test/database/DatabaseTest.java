/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import Server.ClientApp;
import Shared.IrmiClient;
import gui.IngameController;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import onlineschaken.Game;
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
public class DatabaseTest
{

    /**
     *
     */
    public DatabaseTest()
    {
    }

    /**
     *
     */
    @BeforeClass
    public static void setUpClass()
    {
    }

    /**
     *
     */
    @AfterClass
    public static void tearDownClass()
    {
    }

    /**
     *
     */
    @Before
    public void setUp()
    {
        Database database = new Database();
        database.insertPlayer("Henk", "x", "Henk@outlook.com");
    }

    /**
     *
     */
    @After
    public void tearDown()
    {
        Database database = new Database();
        database.removePlayer("Henk");
    }

    @Test
    public void testCloseConnectionException()
    {

        try
        {
            Database instance = new Database();
            instance.closeConnection();
            fail("Geen exception gethrowed");
        } catch (Exception e)
        {
            Logger.getLogger(DatabaseTest.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Test of insertPlayer method, of class Database.
     */
    @Test
    public void testInsertPlayerNull()
    {
        System.out.println("insertPlayer");
        String username = "";
        String password = "b";
        String email = "";
        Database instance = new Database();
        boolean expResult = false;
        boolean result = instance.insertPlayer(username, password, email);
        assertEquals(expResult, result);
    }

    /**
     * HIer wordt de happy flow van insert player en remove player getest. voor
     * deze test moet er een henk in database staan.
     */
    @Test
    public void testInsertPlayerSucces()
    {
        System.out.println("testInsertPlayerSucces");
        Database instance = new Database();
        String username = "Henk";
        String password = "x";
        String email = "x@outlook.com";
        boolean RemoveResult = instance.removePlayer(username);
        boolean expRresult = true;
        assertEquals(RemoveResult, expRresult);
        boolean expResult = true;
        boolean result = instance.insertPlayer(username, password, email);
        assertEquals(expResult, result);
    }

    @Test
    public void testInsertPlayerFail()
    {
        System.out.println("testInsertPlayerFail");
        Database instance = new Database();
        String username = "Henk";
        String password = "x";
        String email = "x@outlook.com";
        boolean expResult = false;
        boolean result = instance.insertPlayer(username, password, email);
        assertEquals(expResult, result);
    }

    @Test
    public void testFailRemovePlayer()
    {
        System.out.println("testFailRemovePlayer");
        Database instance = new Database();
        String username = "P";
        boolean expResult = true;
        boolean result = instance.removePlayer(username);
        assertEquals(expResult, result);
    }

    @Test
    public void testInstertPlayerEmptyPassword()
    {
        System.out.println("insertPlayer");
        String username = "a";
        String password = "";
        String email = "x";
        Database instance = new Database();
        boolean expResult = false;
        boolean result = instance.insertPlayer(username, password, email);
        assertEquals(expResult, result);
    }

    @Test
    public void testRemovePlayerNull()
    {
        System.out.println("testRemovePlayerNull");
        Database instance = new Database();
        String username = "";
        boolean expResult = false;
        boolean result = instance.removePlayer(username);
        assertEquals(expResult, result);
    }

    /**
     * Test of selectPlayer method, of class Database.
     */
    @Test
    public void testSelectPlayer()
    {
        System.out.println("selectPlayer");
        String username = "";
        Database instance = new Database();
        Player expResult = null;
        Player result = instance.selectPlayer(username);
        assertEquals(expResult, result);
    }

    @Test
    public void testSeledctPlayerSucces()
    {
        System.out.println("testSeledctPlayerSucces");
        String username = "a";
        Database instance = new Database();
        String expResult = "a";
        String result = instance.selectPlayer(username).getUsername();
        if (expResult.equals(result))
        {
            
        }
        else{
            
            fail("Kon player niet vinden");
        }
        
    }

    /**
     * Test of addMoveToHistory method, of class Database.
     */
    @Test
    public void testAddMoveToHistory()
    {
        System.out.println("addMoveToHistory");
        String userName = "";
        String userName2 = "";
        String move = "";
        int nr = 0;
        Database instance = new Database();
        boolean result = instance.addMoveToHistory(userName, userName2, move, nr);
        boolean expResult = true;
        assertEquals(result, expResult);
    }

    /**
     * Test of SaveGame method, of class Database.
     */
    @Test
    public void testSaveGameError()
    {
        try
        {
            System.out.println("SaveGame");
            Game game = null;
            Database instance = new Database();
            instance.SaveGame(game);
            fail("Kon een null als game oplsaan in database");
        } catch (Exception e)
        {
            Logger.getLogger(DatabaseTest.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @Test
    public void testSaveGameSucces()
    {
        try
        {
            System.out.println("SaveGame");

            Player p1 = new Player("JOEP", "d", 20);
            Player p2 = new Player("JOEP2", "d", 20);
            IngameController controller = new IngameController();
            IrmiClient app = new ClientApp();
            Game game = new Game(p1, p2, app, controller);
            Database instance = new Database();
            instance.SaveGame(game);
        } catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    /**
     * Test of GetUsersGames method, of class Database.
     */
    @Test
    public void testGetUsersGames()
    {
        System.out.println("GetUsersGames");
        String Username = "";
        Database instance = new Database();
        ArrayList<Game> expResult = new ArrayList<>();
        ArrayList<Game> result = instance.GetUsersGames(Username);
        assertEquals(expResult, result);
    }
     @Test
    public void testGetUsersGamesResults()
    {
        System.out.println("GetUsersGames");
        String Username = "a";
        Database instance = new Database();
        
        int result = instance.GetUsersGames(Username).size();
        if (result>0)
        {
            
        }
        else{
            fail("Geen games gevonden");
        }
    }

    /**
     * Test of closeConnection method, of class Database.
     */
    @Test
    public void testCloseConnection()
    {
        try
        {
            System.out.println("closeConnection");
            Database instance = new Database();
            instance.init();
            instance.closeConnection();
        } catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    /**
     * Test of init method, of class Database.
     */
    @Test
    public void testInit()
    {
        System.out.println("init");
        Database instance = new Database();
        boolean expResult = true;
        boolean result = instance.init();
        assertEquals(expResult, result);
    }

}
