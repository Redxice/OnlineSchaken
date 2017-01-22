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
 * @author redxice
 */
public class RookTest
{

    private OnlineSchaken onlineSchaken;
    private Game game;
    private Player p1;
    private Player p2;
    private Board board;
    private IrmiClient client;
    private IngameController controller;
    private Rook White_rook1;
    private Rook White_rook2;
    private King White_king;
    private Rook Black_rook1;
    private Rook Black_rook2;
    private King Black_king;
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    public RookTest()
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
        this.board = this.game.getBoard();
        game.getBoard().createContent();
        White_rook1 = new Rook("white", p1, board.getSections(0, 0));
        White_rook2 = new Rook("white", p1, board.getSections(7, 0));
        White_king = new King("white", p1, board.getSections(4, 0));
        Black_rook1 = new Rook("black", p2, board.getSections(0, 7));
        Black_rook2 = new Rook("black", p2, board.getSections(7, 7));
        Black_king = new King("black", p2, board.getSections(4, 7));

    }

    /**
     *
     */
    @After
    public void tearDown()
    {

    }

    /**
     * Test of castling method, of class Rook.
     */
    @Test
    public void testCastling()
    {
        System.out.println("castling");
        Section expResult = null;
        Section result = White_rook1.castling();
        assertEquals(expResult, result);
    }
//hier worden allen mogelijkheden van rokade gechecked

    /**
     * Test of checkRokade method, of class Rook.
     */
    @Test
    public void testCheckRokadeFalse()
    {
        Section p_section = White_king.getSection();
        Rook instance = Black_rook1;
        Boolean expResult = false;
        Boolean result = instance.checkRokade(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testWhiteCheckRokade1()
    {
        System.out.println("testWhiteCheckRokade1");
        Section p_section = White_king.getSection();
        Rook instance = White_rook1;
        Boolean expResult = true;
        Boolean result = instance.checkRokade(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testWhiteCheckRokade1False()
    {
        System.out.println("testCheckRokade1False");
        Section p_section = White_king.getSection();
        Rook instance = White_rook1;
        Pawn pawn = new Pawn("white", p1, board.getSections(2, 0));
        Boolean expResult = false;
        Boolean result = instance.checkRokade(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testWhiteCheckRokade2()
    {
        System.out.println("testWhiteCheckRokade2");
        Section p_section = White_king.getSection();
        Rook instance = White_rook2;
        Boolean expResult = true;
        Boolean result = instance.checkRokade(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testWhiteCheckRokade2False()
    {
        System.out.println("testWhiteCheckRokade2False");
        Section p_section = White_king.getSection();
        Rook instance = White_rook2;
        Pawn pawn = new Pawn("white", p1, board.getSections(6, 0));
        Boolean expResult = false;
        Boolean result = instance.checkRokade(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testBlackCheckRokade1()
    {
        System.out.println("testBlackCheckRokade1");
        Section p_section = Black_king.getSection();
        Rook instance = Black_rook1;
        Boolean expResult = true;
        Boolean result = instance.checkRokade(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testBlackCheckRokade1False()
    {
        System.out.println("testBlackCheckRokade1False");
        Section p_section = Black_king.getSection();
        Rook instance = Black_rook1;
        Pawn pawn = new Pawn("black", p2, board.getSections(3, 7));
        Boolean expResult = false;
        Boolean result = instance.checkRokade(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testBlackCheckRokade2()
    {
        System.out.println("testBlackCheckRokade2");
        Section p_section = Black_king.getSection();
        Rook instance = Black_rook2;
        Boolean expResult = true;
        Boolean result = instance.checkRokade(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testBlackCheckRokade2False()
    {
        System.out.println("testBlackCheckRokade2False");
        Section p_section = Black_king.getSection();
        Rook instance = Black_rook2;
        Pawn pawn = new Pawn("black", p2, board.getSections(6, 7));
        Boolean expResult = false;
        Boolean result = instance.checkRokade(p_section);
        assertEquals(expResult, result);
    }
// hier worden alle mogelijkheden getest van de checkmove methdoe van Rook.

    /**
     * Test of checkMove method, of class Rook.
     */
    @Test
    public void testCheckMoveWhiteUpwards()
    {
        System.out.println("testCheckMoveWhiteUpwards");
        Section p_section = board.getSections(0, 2);
        Rook instance = White_rook1;
        Boolean expResult = true;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckMOveWhiteDownwards()
    {
        System.out.println("testCheckMOveWhiteDownwards");
        Section p_section = board.getSections(0, 2);
        Rook instance = new Rook("white", p1, board.getSections(0, 4));
        Boolean expResult = true;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }
    @Test
    public void testCheckMoveWhiteUpwardsOccupied()
    {
        System.out.println("testCheckMoveWhiteUpwards");
        Section p_section = board.getSections(0, 3);
        Pawn pawn = new Pawn("black", p2, board.getSections(0, 2));
        Rook instance = White_rook1;
        Boolean expResult = false;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckMOveWhiteDownwardsOccupied()
    {
        System.out.println("testCheckMOveWhiteDownwards");
        Section p_section = board.getSections(0, 2);
        Pawn pawn = new Pawn("black", p2, board.getSections(0, 3));
        Rook instance = new Rook("white", p1, board.getSections(0, 4));
        Boolean expResult = false;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckMoveBlackUpwards()
    {
        System.out.println("testCheckMoveBlackUpwards");
        Section p_section = board.getSections(0, 5);
        Rook instance = new Rook("black", p2, board.getSections(0, 3));
        Boolean expResult = true;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckMOveBlackDownwards()
    {
        System.out.println("testCheckMOveBlackDownwards");
        Section p_section = board.getSections(0, 2);
        Rook instance = Black_rook1;
        Boolean expResult = true;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }
     @Test
    public void testCheckMoveBlackUpwardsOccupied()
    {
        System.out.println("testCheckMoveBlackUpwardsOccupied");
        Section p_section = board.getSections(0, 5);
        Rook instance = new Rook("black", p2, board.getSections(0, 3));
        Pawn pawn = new Pawn("black", p2, board.getSections(0,4));
        Boolean expResult = false;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckMOveBlackDownwardsOccupied()
    {
        System.out.println("testCheckMOveBlackDownwardsOccupied");
        Section p_section = board.getSections(0, 2);
        Rook instance = Black_rook1;
       Pawn pawn = new Pawn("black", p2, board.getSections(0,4));
        Boolean expResult = false;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckMoveWhiteRight()
    {
        System.out.println("testCheckMoveWhiteRight");
        Section p_section = board.getSections(2, 0);
        Rook instance = White_rook1;
        Boolean expResult = true;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckMoveWhiteLeft()
    {
        System.out.println("testCheckMoveWhiteLeft");
        Section p_section = board.getSections(5, 0);
        Rook instance = White_rook2;
        Boolean expResult = true;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckMoveBlackRight()
    {
        System.out.println("testCheckMOveBlackRight");
        Section p_section = board.getSections(2, 7);
        Rook instance = Black_rook1;
        Boolean expResult = true;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckMoveBlackLeft()
    {
        System.out.println("testCheckMOveBlackDownwards");
        Section p_section = board.getSections(6, 7);
        Rook instance = Black_rook2;
        Boolean expResult = true;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void CheckMoveFalse()
    {
        System.out.println("testCheckMOveBlackDownwards");
        Section p_section = board.getSections(6, 6);
        Rook instance = Black_rook2;
        Boolean expResult = false;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

}
