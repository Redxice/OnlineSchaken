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
public class KingTest
{

    private OnlineSchaken onlineSchaken;
    private Game game;
    private Player p1;
    private Player p2;
    private Board board;
    private IrmiClient client;
    private IngameController controller;

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
    public void testCountCheckSections()
    {
        System.out.println("countCheckSections");
        King instance = null;
        int expResult = 0;
        int result = instance.countCheckSections();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSingleCheckSection method, of class King.
     */
    @Test
    public void testGetSingleCheckSection()
    {
        System.out.println("getSingleCheckSection");
        King instance = null;
        Section expResult = null;
        Section result = instance.getSingleCheckSection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isCheck method, of class King.
     */
    @Test
    public void testIsCheck()
    {
        System.out.println("isCheck");
        King instance = null;
        boolean expResult = false;
        boolean result = instance.isCheck();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of becomeCheck method, of class King.
     */
    @Test
    public void testBecomeCheck()
    {
        System.out.println("becomeCheck");
        Section p_section = null;
        King instance = null;
        boolean expResult = false;
        boolean result = instance.becomeCheck(p_section);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCheck method, of class King.
     */
    @Test
    public void testSetCheck()
    {
        System.out.println("setCheck");
        boolean check = true;
        King instance = new King("White", p1, game.getBoard().getSections(3, 3));
        instance.setCheck(check);
        assertTrue(instance.checkMate);
    }

    /**
     * Test of isCheckMate method, of class King.
     */
    @Test
    public void testIsCheckMate()
    {
        System.out.println("isCheckMate");
        King instance = null;
        boolean expResult = false;
        boolean result = instance.isCheckMate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCheckMate method, of class King.
     */
    @Test
    public void testSetCheckMate()
    {
        System.out.println("setCheckMate");
        boolean checkMate = true;
        King instance = new King("White", p1, game.getBoard().getSections(3, 3));
        instance.setCheckMate(checkMate);
        assertTrue(instance.checkMate);
    }

    /**
     * Test of checkMove method, of class King.
     */
    @Test
    public void testInvalidCheckMove()
    {
        King k1 = new King("White", p1, game.getBoard().getSections(3, 3));
        King k2 = new King("White", p1, game.getBoard().getSections(3, 4));
        assertFalse(k1.checkMove(k2.getSection()));
    }

    /**
     * Test of checkMove method, of class King.
     */
    @Test
    public void testUpTooFarCheckMove()
    {
        King k1 = new King("White", p1, game.getBoard().getSections(3, 3));
        King k2 = new King("White", p1, game.getBoard().getSections(3, 1));
        assertFalse(k1.checkMove(k2.getSection()));
    }
    
    /**
     * Test of checkMove method, of class King.
     */
    @Test
    public void testDownTooFarCheckMove()
    {
        King k1 = new King("White", p1, game.getBoard().getSections(3, 3));
        King k2 = new King("White", p1, game.getBoard().getSections(3, 5));
        assertFalse(k1.checkMove(k2.getSection()));
    }
    
    /**
     * Test of checkMove method, of class King.
     */
    @Test
    public void testLeftTooFarCheckMove()
    {
        King k1 = new King("White", p1, game.getBoard().getSections(3, 3));
        King k2 = new King("White", p1, game.getBoard().getSections(1, 3));
        assertFalse(k1.checkMove(k2.getSection()));
    }
    
    /**
     * Test of checkMove method, of class King.
     */
    @Test
    public void testRightTooFarCheckMove()
    {
        King k1 = new King("White", p1, game.getBoard().getSections(3, 3));
        King k2 = new King("White", p1, game.getBoard().getSections(5, 3));
        assertFalse(k1.checkMove(k2.getSection()));
    }
    
    /**
     * Test of checkMove method, of class King.
     */
    @Test
    public void testGameOverCheckMove()
    {
        King k1 = new King("White", p1, game.getBoard().getSections(3, 3));
        Queen q1 = new Queen("Black", p1, game.getBoard().getSections(5, 4));
        assertFalse(k1.checkMove(game.getBoard().getSections(3,4)));
    }
}
