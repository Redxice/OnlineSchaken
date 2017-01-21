/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import Server.ClientApp;
import Shared.IrmiClient;
import gui.IngameController;
import gui.OnlineSchaken;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sander
 */
public class BishopTest
{

    private OnlineSchaken onlineSchaken;
    private Game game;
    private Player p1;
    private Player p2;
    private Board board;
    private IrmiClient client;
    private IngameController controller;

    public BishopTest()
    {
        onlineSchaken = new OnlineSchaken();
        p1 = new Player("p1", "ww", 0);
        p2 = new Player("p2", "ww", 0);
        client = new ClientApp();
        try
        {
            controller = new IngameController();
        } catch (Exception e)
        {

        }
        game = new Game(p1, p2, client, controller);
        game.getBoard().createContent();
    }

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
     * Test of constructor, of class Bishop.
     */
    @Test
    public void TestBlackKnight()
    {
        Bishop b = new Bishop("Black", p1, game.getBoard().getSections(3, 5));
    }

    /**
     * Test of constructor, of class Bishop.
     */
    @Test
    public void TestWiteKnight()
    {
        Bishop b = new Bishop("White", p1, game.getBoard().getSections(3, 5));
    }

    /**
     * Test of checkMove method, of class Bishop.
     */
    @Test
    public void testInvalidCheckMove()
    {
        Bishop b1 = new Bishop("White", p1, game.getBoard().getSections(3, 5));
        Bishop b2 = new Bishop("White", p1, game.getBoard().getSections(4, 6));
        assertFalse(b1.checkMove(b2.getSection()));
    }
    
    /**
     * Test of checkMove method, of class Bishop.
     */
    @Test
    public void testLeftUpBlockedCheckMove()
    {
        Bishop b1 = new Bishop("White", p1, game.getBoard().getSections(3, 3));
        Bishop b2 = new Bishop("White", p1, game.getBoard().getSections(2, 2));
        assertFalse(b1.checkMove(game.getBoard().getSections(1, 1)));
    }
    
    /**
     * Test of checkMove method, of class Bishop.
     */
    @Test
    public void testLeftUpCheckMove()
    {
        Bishop b1 = new Bishop("White", p1, game.getBoard().getSections(3, 3));
        assertTrue(b1.checkMove(game.getBoard().getSections(2, 2)));
    }
    
    /**
     * Test of checkMove method, of class Bishop.
     */
    @Test
    public void testLeftDownBlockedCheckMove()
    {
        Bishop b1 = new Bishop("White", p1, game.getBoard().getSections(3, 3));
        Bishop b2 = new Bishop("White", p1, game.getBoard().getSections(2, 4));
        assertFalse(b1.checkMove(game.getBoard().getSections(1, 5)));
    }
    
    /**
     * Test of checkMove method, of class Bishop.
     */
    @Test
    public void testLeftDownCheckMove()
    {
        Bishop b1 = new Bishop("White", p1, game.getBoard().getSections(3, 3));
        assertTrue(b1.checkMove(game.getBoard().getSections(2, 4)));
    }
    
    /**
     * Test of checkMove method, of class Bishop.
     */
    @Test
    public void testRightUpBlockedCheckMove()
    {
        Bishop b1 = new Bishop("White", p1, game.getBoard().getSections(3, 3));
        Bishop b2 = new Bishop("White", p1, game.getBoard().getSections(4, 2));
        assertFalse(b1.checkMove(game.getBoard().getSections(5, 1)));
    }
    
    /**
     * Test of checkMove method, of class Bishop.
     */
    @Test
    public void testRightUpCheckMove()
    {
        Bishop b1 = new Bishop("White", p1, game.getBoard().getSections(3, 3));
        assertTrue(b1.checkMove(game.getBoard().getSections(4, 2)));
    }
    
    /**
     * Test of checkMove method, of class Bishop.
     */
    @Test
    public void testRightDownBlockedCheckMove()
    {
        Bishop b1 = new Bishop("White", p1, game.getBoard().getSections(3, 3));
        Bishop b2 = new Bishop("White", p1, game.getBoard().getSections(4, 4));
        assertFalse(b1.checkMove(game.getBoard().getSections(5, 5)));
    }
    
    /**
     * Test of checkMove method, of class Bishop.
     */
    @Test
    public void testRightDownCheckMove()
    {
        Bishop b1 = new Bishop("White", p1, game.getBoard().getSections(3, 3));
        assertTrue(b1.checkMove(game.getBoard().getSections(4, 4)));
    }
}
