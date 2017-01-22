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
import org.junit.Rule;

/**
 *
 * @author Sander
 */
public class QueenTest
{

    private OnlineSchaken onlineSchaken;
    private Game game;
    private Player p1;
    private Player p2;
    private Board board;
    private IrmiClient client;
    private IngameController controller;

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    
    public QueenTest()
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
     * Test of constructor, of class Queen.
     */
    @Test
    public void TestBlackQueen()
    {
        Queen q = new Queen("black", p1, game.getBoard().getSections(3, 5));
    }

    /**
     * Test of constructor, of class Queen.
     */
    @Test
    public void TestWiteQueen()
    {
        Queen q = new Queen("white", p1, game.getBoard().getSections(3, 5));
    }

    /**
     * Test of checkMove method, of class Queen.
     */
    @Test
    public void testInvalidCheckMove()
    {
        Queen q1 = new Queen("white", p1, game.getBoard().getSections(3, 5));
        Queen q2 = new Queen("white", p1, game.getBoard().getSections(1, 2));
        assertFalse(q1.checkMove(q2.getSection()));
    }
    
    /**
     * Test of checkMove method, of class Queen.
     */
    @Test
    public void testLeftUpBlockedCheckMove()
    {
        Queen q1 = new Queen("white", p1, game.getBoard().getSections(3, 3));
        Queen q2 = new Queen("white", p1, game.getBoard().getSections(2, 2));
        assertFalse(q1.checkMove(game.getBoard().getSections(1, 1)));
    }
    
    /**
     * Test of checkMove method, of class Queen.
     */
    @Test
    public void testLeftUpCheckMove()
    {
        Queen q1 = new Queen("white", p1, game.getBoard().getSections(3, 3));
        assertTrue(q1.checkMove(game.getBoard().getSections(2, 2)));
    }
    
    /**
     * Test of checkMove method, of class Queen.
     */
    @Test
    public void testLeftDownBlockedCheckMove()
    {
        Queen q1 = new Queen("white", p1, game.getBoard().getSections(3, 3));
        Queen q2 = new Queen("white", p1, game.getBoard().getSections(2, 4));
        assertFalse(q1.checkMove(game.getBoard().getSections(1, 5)));
    }
    
    /**
     * Test of checkMove method, of class Queen.
     */
    @Test
    public void testLeftDownCheckMove()
    {
        Queen q1 = new Queen("white", p1, game.getBoard().getSections(3, 3));
        assertTrue(q1.checkMove(game.getBoard().getSections(2, 4)));
    }
    
    /**
     * Test of checkMove method, of class Queen.
     */
    @Test
    public void testRightUpBlockedCheckMove()
    {
        Queen q1 = new Queen("white", p1, game.getBoard().getSections(3, 3));
        Queen q2 = new Queen("white", p1, game.getBoard().getSections(4, 2));
        assertFalse(q1.checkMove(game.getBoard().getSections(5, 1)));
    }
    
    /**
     * Test of checkMove method, of class Queen.
     */
    @Test
    public void testRightUpCheckMove()
    {
        Queen q1 = new Queen("white", p1, game.getBoard().getSections(3, 3));
        assertTrue(q1.checkMove(game.getBoard().getSections(4, 2)));
    }
    
    /**
     * Test of checkMove method, of class Queen.
     */
    @Test
    public void testRightDownBlockedCheckMove()
    {
        Queen q1 = new Queen("white", p1, game.getBoard().getSections(3, 3));
        Queen q2 = new Queen("white", p1, game.getBoard().getSections(4, 4));
        assertFalse(q1.checkMove(game.getBoard().getSections(5, 5)));
    }
    
    /**
     * Test of checkMove method, of class Queen.
     */
    @Test
    public void testRightDownCheckMove()
    {
        Queen q1 = new Queen("white", p1, game.getBoard().getSections(3, 3));
        assertTrue(q1.checkMove(game.getBoard().getSections(4, 4)));
    }

    /**
     * Test of checkMove method, of class Queen.
     */
    @Test
    public void testLeftBlockedCheckMove()
    {
        Queen q1 = new Queen("white", p1, game.getBoard().getSections(3, 3));
        Queen q2 = new Queen("white", p1, game.getBoard().getSections(2, 3));
        assertFalse(q1.checkMove(game.getBoard().getSections(1, 3)));
    }
    
    /**
     * Test of checkMove method, of class Queen.
     */
    @Test
    public void testLeftCheckMove()
    {
        Queen q1 = new Queen("white", p1, game.getBoard().getSections(3, 3));
        assertTrue(q1.checkMove(game.getBoard().getSections(2, 3)));
    }
    
    /**
     * Test of checkMove method, of class Queen.
     */
    @Test
    public void testRightBlockedCheckMove()
    {
        Queen q1 = new Queen("white", p1, game.getBoard().getSections(3, 3));
        Queen q2 = new Queen("white", p1, game.getBoard().getSections(4, 3));
        assertFalse(q1.checkMove(game.getBoard().getSections(5, 3)));
    }
    
    /**
     * Test of checkMove method, of class Queen.
     */
    @Test
    public void testRightCheckMove()
    {
        Queen q1 = new Queen("white", p1, game.getBoard().getSections(3, 3));
        assertTrue(q1.checkMove(game.getBoard().getSections(4, 3)));
    }
    
    /**
     * Test of checkMove method, of class Queen.
     */
    @Test
    public void testUpBlockedCheckMove()
    {
        Queen q1 = new Queen("white", p1, game.getBoard().getSections(3, 3));
        Queen q2 = new Queen("white", p1, game.getBoard().getSections(3, 2));
        assertFalse(q1.checkMove(game.getBoard().getSections(3, 1)));
    }
    
    /**
     * Test of checkMove method, of class Queen.
     */
    @Test
    public void testUpCheckMove()
    {
        Queen q1 = new Queen("white", p1, game.getBoard().getSections(3, 3));
        assertTrue(q1.checkMove(game.getBoard().getSections(3, 2)));
    }
    
    /**
     * Test of checkMove method, of class Queen.
     */
    @Test
    public void testDownBlockedCheckMove()
    {
        Queen q1 = new Queen("white", p1, game.getBoard().getSections(3, 3));
        Queen q2 = new Queen("white", p1, game.getBoard().getSections(3, 4));
        assertFalse(q1.checkMove(game.getBoard().getSections(3, 5)));
    }
    
    /**
     * Test of checkMove method, of class Queen.
     */
    @Test
    public void testDownCheckMove()
    {
        Queen q1 = new Queen("white", p1, game.getBoard().getSections(3, 3));
        assertTrue(q1.checkMove(game.getBoard().getSections(3, 4)));
    }
}
