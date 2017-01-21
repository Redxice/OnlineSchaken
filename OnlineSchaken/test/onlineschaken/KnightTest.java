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
import javafx.scene.image.Image;
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
    public void testCheckMove()
    {
        System.out.println("checkMove");
        Section p_section = null;
        Knight instance = null;
        Boolean expResult = null;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
