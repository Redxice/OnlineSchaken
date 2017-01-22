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
public class KingTest
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
    
    public KingTest()
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
     * Test of constructor, of class King.
     */
    @Test
    public void TestBlackKing()
    {
        King k = new King("black", p1, game.getBoard().getSections(3, 5));
    }

    /**
     * Test of constructor, of class King.
     */
    @Test
    public void TestWiteKing()
    {
        King k = new King("white", p1, game.getBoard().getSections(3, 5));
    }
    
    /**
     * Test of countCheckSections method, of class King.
     */
    @Test
    public void testCount0CheckSections()
    {
        System.out.println("countCheckSections");
        King k = new King("white", p1, game.getBoard().getSections(3, 5));
        int expResult = 0;
        int result = k.countCheckSections();
        assertEquals(expResult, result);
    }

    /**
     * Test of countCheckSections method, of class King.
     */
    @Test
    public void testCountCheckSections()
    {
        System.out.println("countCheckSections");
        King k = new King("white", p1, game.getBoard().getSections(3, 5));
        Queen q = new Queen("black", p1, game.getBoard().getSections(3, 6));
        int expResult = 1;
        int result = k.countCheckSections();
        assertEquals(expResult, result);
        
        k = new King("white", p1, game.getBoard().getSections(3, 5));
        q = new Queen("black", p1, game.getBoard().getSections(3, 6));
        q = new Queen("black", p1, game.getBoard().getSections(4, 6));
        q = new Queen("black", p1, game.getBoard().getSections(4, 5));
        expResult = 3;
        result = k.countCheckSections();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getSingleCheckSection method, of class King.
     */
    @Test
    public void testGetSingleCheckSection()
    {
        System.out.println("getSingleCheckSection");
        King k = new King("white", p1, game.getBoard().getSections(3, 5));
        Queen q = new Queen("black", p1, game.getBoard().getSections(3, 6));
        Section expResult = q.getSection();
        Section result = k.getSingleCheckSection();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getSingleCheckSection method, of class King.
     */
    @Test
    public void testGetNoCheckSingleCheckSection()
    {
        System.out.println("getSingleCheckSection");
        King k = new King("white", p1, game.getBoard().getSections(3, 5));
        Section expResult = null;
        Section result = k.getSingleCheckSection();
        assertEquals(expResult, result);
    }

    /**
     * Test of isCheck method, of class King.
     */
    @Test
    public void testIsCheckTrue()
    {
        System.out.println("isCheck");
        King k = new King("white", p1, game.getBoard().getSections(3, 5));
        Queen q = new Queen("black", p1, game.getBoard().getSections(3, 6));
        boolean expResult = true;
        boolean result = k.isCheck();
        assertEquals(expResult, result);
    }

    /**
     * Test of isCheck method, of class King.
     */
    @Test
    public void testIsCheckFalse()
    {
        System.out.println("isCheck");
        King k = new King("white", p1, game.getBoard().getSections(3, 5));
        boolean expResult = false;
        boolean result = k.isCheck();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of becomeCheck method, of class King.
     */
    @Test
    public void testBecomeCheckTrue()
    {
        System.out.println("becomeCheck");
        King k = new King("white", p1, game.getBoard().getSections(3, 5));
        Queen q = new Queen("black", p1, game.getBoard().getSections(1, 6));
        boolean expResult = true;
        boolean result = k.becomeCheck(game.getBoard().getSections(3, 6));
        assertEquals(expResult, result);
    }
    
    /**
     * Test of becomeCheck method, of class King.
     */
    @Test
    public void testBecomeCheckFalse()
    {
        System.out.println("becomeCheck");
        King k = new King("white", p1, game.getBoard().getSections(3, 5));
        boolean expResult = false;
        boolean result = k.becomeCheck(game.getBoard().getSections(3, 6));
        assertEquals(expResult, result);
    }

    /**
     * Test of setCheck method, of class King.
     */
    @Test
    public void testSetCheck()
    {
        System.out.println("setCheck");
        boolean check = true;
        King k = new King("white", p1, game.getBoard().getSections(3, 5));
        k.setCheck(check);
        assertTrue(k.check);
        
        check = false;
        k.setCheck(check);
        assertFalse(k.check);
    }

    /**
     * Test of setCheckMate method, of class King.
     */
    @Test
    public void testSetCheckMate()
    {
        System.out.println("setCheckMate");
        boolean check = true;
        King k = new King("white", p1, game.getBoard().getSections(3, 5));
        k.setCheckMate(check);
        assertTrue(k.checkMate);
        
        check = false;
        k.setCheckMate(check);
        assertFalse(k.checkMate);
    }

    /**
     * Test of checkMove method, of class King.
     */
    @Test
    public void testInvalidCheckMove()
    {
        King k1 = new King("white", p1, game.getBoard().getSections(3, 3));
        King k2 = new King("white", p1, game.getBoard().getSections(3, 4));
        assertFalse(k1.checkMove(game.getBoard().getSections(3, 4)));
    }

    /**
     * Test of checkMove method, of class King.
     */
    @Test
    public void testUpTooFarCheckMove()
    {
        King k1 = new King("white", p1, game.getBoard().getSections(3, 3));
        assertFalse(k1.checkMove(game.getBoard().getSections(3, 1)));
    }
    
    /**
     * Test of checkMove method, of class King.
     */
    @Test
    public void testDownTooFarCheckMove()
    {
        King k1 = new King("white", p1, game.getBoard().getSections(3, 3));
        assertFalse(k1.checkMove(game.getBoard().getSections(3, 5)));
    }
    
    /**
     * Test of checkMove method, of class King.
     */
    @Test
    public void testLeftTooFarCheckMove()
    {
        King k1 = new King("white", p1, game.getBoard().getSections(3, 3));
        assertFalse(k1.checkMove(game.getBoard().getSections(1, 3)));
    }
    
    /**
     * Test of checkMove method, of class King.
     */
    @Test
    public void testRightTooFarCheckMove()
    {
        King k1 = new King("white", p1, game.getBoard().getSections(3, 3));
        assertFalse(k1.checkMove(game.getBoard().getSections(5, 3)));
    }
    
    /**
     * Test of checkMove method, of class King.
     */
    @Test
    public void testGameOverCheckMove()
    {
        King k1 = new King("white", p1, game.getBoard().getSections(3, 3));
        Queen q1 = new Queen("black", p1, game.getBoard().getSections(5, 4));
        assertFalse(k1.checkMove(game.getBoard().getSections(3,4)));
    }
    
    /**
     * Test of checkMove method, of class King.
     */
    @Test
    public void testCheckMove()
    {
        King k1 = new King("white", p1, game.getBoard().getSections(3, 3));
        assertTrue(k1.checkMove(game.getBoard().getSections(3,4)));
    }
}
