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
public class KnightTest
{

    private OnlineSchaken onlineSchaken;
    private Game game;
    private Player p1;
    private Player p2;
    private Board board;
    private IrmiClient client;
    private IngameController controller;
    
    /**
     *
     */
    public KnightTest()
    {
        onlineSchaken = new OnlineSchaken();
        p1 = new Player("p1", "ww", 0);
        p2 = new Player("p2", "ww", 0);
        client = new ClientApp();
        try
        {
        controller = new IngameController();
        }
        catch(Exception e)
        {
            
        }
        game = new Game(p1, p2, client, controller);
        game.getBoard().createContent();
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
    }

    /**
     *
     */
    @After
    public void tearDown()
    {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    /**
     *
     */
    @Test
    public void TestBlackKnight()
    {
        Knight k = new Knight("Black", p1, game.getBoard().getSections(3, 5));
    }

    /**
     *
     */
    @Test
    public void TestWiteKnight()
    {
        Knight k = new Knight("White", p1, game.getBoard().getSections(3, 5));
    }

    /**
     * Test of checkMove method, of class Knight.
     */
    @Test
    public void testInvalidCheckMove()
    {
        Knight k1 = new Knight("Black", p1, game.getBoard().getSections(3, 5));
        Knight k2 = new Knight("Black", p1, game.getBoard().getSections(3, 6));
        assertFalse(k1.checkMove(k2.getSection()));
    }
    
    /**
     * Test of checkMove method, of class Knight.
     */
    @Test
    public void test2Right1UpCheckMove()
    {
        Knight k1 = new Knight("Black", p1, game.getBoard().getSections(3, 5));
        assertTrue(k1.checkMove(game.getBoard().getSections(5, 6)));
    }
    
    /**
     * Test of checkMove method, of class Knight.
     */
    @Test
    public void test2Right1DownCheckMove()
    {
        Knight k1 = new Knight("Black", p1, game.getBoard().getSections(3, 5));
        assertTrue(k1.checkMove(game.getBoard().getSections(5, 4)));
    }
    
    /**
     * Test of checkMove method, of class Knight.
     */
    @Test
    public void test2Left1UpCheckMove()
    {
        Knight k1 = new Knight("Black", p1, game.getBoard().getSections(3, 5));
        assertTrue(k1.checkMove(game.getBoard().getSections(1, 6)));
    }
    
    /**
     * Test of checkMove method, of class Knight.
     */
    @Test
    public void test2Left1DownCheckMove()
    {
        Knight k1 = new Knight("Black", p1, game.getBoard().getSections(3, 5));
        assertTrue(k1.checkMove(game.getBoard().getSections(1, 4)));
    }
    
    /**
     * Test of checkMove method, of class Knight.
     */
    @Test
    public void test1Right2UpCheckMove()
    {
        Knight k1 = new Knight("Black", p1, game.getBoard().getSections(3, 5));
        assertTrue(k1.checkMove(game.getBoard().getSections(4, 7)));
    }
    
    /**
     * Test of checkMove method, of class Knight.
     */
    @Test
    public void test1Right2DownCheckMove()
    {
        Knight k1 = new Knight("Black", p1, game.getBoard().getSections(3, 5));
        assertTrue(k1.checkMove(game.getBoard().getSections(4, 3)));
    }
    
    /**
     * Test of checkMove method, of class Knight.
     */
    @Test
    public void test1Left2UpCheckMove()
    {
        Knight k1 = new Knight("Black", p1, game.getBoard().getSections(3, 5));
        assertTrue(k1.checkMove(game.getBoard().getSections(2, 7)));
    }
    
    /**
     * Test of checkMove method, of class Knight.
     */
    @Test
    public void test1Left2DownCheckMove()
    {
        Knight k1 = new Knight("Black", p1, game.getBoard().getSections(3, 5));
        assertTrue(k1.checkMove(game.getBoard().getSections(2, 3)));
    }
    
    /**
     * Test of checkMove method, of class Knight.
     */
    @Test
    public void testInvalidMovementCheckMove()
    {
        Knight k1 = new Knight("Black", p1, game.getBoard().getSections(3, 5));
        assertFalse(k1.checkMove(game.getBoard().getSections(1, 1)));
    }
}
