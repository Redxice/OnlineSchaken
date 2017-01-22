/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import Server.ClientApp;
import Shared.IinGameController;
import Shared.IrmiClient;
import gui.IngameController;
import gui.OnlineSchaken;
import java.awt.Point;
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
public class PawnTest
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

    /**
     *
     */
    public PawnTest()
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
    public void TestGetPlayer()
    {
        Piece pawnWhite = new Pawn("black", p2, game.getBoard().getSections(0, 0));
        String actual = pawnWhite.getPlayer().getUsername();
        String expected = "p2";
        assertEquals(actual, expected);
    }

    /**
     *
     */
    @Test
    public void TestGetColor()
    {
        Piece pawnBlack = new Pawn("black", p2, game.getBoard().getSections(0, 0));
        String actual = pawnBlack.getColor();
        String expected = "black";
        if (actual.equals(expected))
        {

        } else
        {
            fail("Colors do not match");
        }
    }

    /**
     *
     */
    @Test
    public void TestGetSection()
    {
        Piece pawnWhite = new Pawn("white", p1, game.getBoard().getSections(3, 5));
        Point actual = pawnWhite.getSection().getID();
        Point expected = new Point(3, 5);
        assertEquals(actual, expected);
    }

    /**
     *
     */
    @Test
    public void TestPrevSection()
    {
        Pawn pawnWhite = new Pawn("white", p1, game.getBoard().getSections(3, 5));
        pawnWhite.setPrevSection(game.getBoard().getSections(2, 2));
        Point actual = pawnWhite.getPrevSection().getID();
        Point expected = new Point(2, 2);
        assertEquals(actual, expected);
    }

    /**
     * Test of setPrevSection method, of class Pawn & the methods getprevX &
     * getPrevY
     */
    @Test
    public void testSetPrevSection()
    {
        System.out.println("setPrevSection");
        Section section = game.getBoard().getSections(4, 6);
        Pawn instance = new Pawn("black", p1, game.getBoard().getSections(3, 5));
        instance.setPrevSection(section);
        if (4 == (int) instance.getPrevX() && 6 == (int) instance.getPrevY())
        {

        } else
        {
            fail("setPrevSection has failed");
        }
    }

    /**
     * Test of getPrevSection method, of class Pawn.
     */
    @Test
    public void testGetPrevSection()
    {
        System.out.println("setPrevSection");
        Section section = game.getBoard().getSections(4, 6);
        Pawn instance = new Pawn("black", p1, game.getBoard().getSections(3, 5));
        instance.setPrevSection(section);
        assertEquals(section, instance.getPrevSection());
    }

    /**
     * In deze test van pawn is hasMoved False
     */
    @Test
    public void testCheckMoveOneTileForwardWhite1()
    {
        System.out.println("testCheckMoveOneTileForwardWhite");
        Section p_section = board.getSections(3, 6);
        Pawn instance = new Pawn("white", p1, game.getBoard().getSections(3, 5));
        Boolean expResult = true;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckMoveFalseWhite()
    {
        System.out.println("testCheckMoveOneTileForwardWhite");
        Section p_section = board.getSections(3, 3);
        Pawn instance = new Pawn("white", p1, game.getBoard().getSections(3, 5));
        Boolean expResult = false;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckMoveFalseBlack()
    {
        System.out.println("testCheckMoveOneTileForwardWhite");
        Section p_section = board.getSections(3, 7);
        Pawn instance = new Pawn("black", p2, game.getBoard().getSections(3, 5));
        Boolean expResult = false;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    /**
     * In deze test van pawn is hasMoved true
     */
    @Test
    public void testCheckMoveOneTileForwardWhite2()
    {
        System.out.println("testCheckMoveOneTileForwardWhite");
        Section p_section = board.getSections(3, 6);
        Pawn instance = new Pawn("white", p1, game.getBoard().getSections(3, 5));
        instance.setHasMoved(true);
        Boolean expResult = true;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    /**
     * In deze test van pawn is hasMoved False
     */
    @Test
    public void testCheckMoveOneTileForwardBlack1()
    {
        System.out.println("testCheckMoveOneTileForwardBlack");
        Section p_section = board.getSections(3, 4);
        Pawn instance = new Pawn("black", p1, game.getBoard().getSections(3, 5));
        Boolean expResult = true;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    /**
     * In deze test van pawn is hasMoved true
     */
    @Test
    public void testCheckMoveOneTileForwardBlack2()
    {
        System.out.println("testCheckMoveOneTileForwardBlack");
        Section p_section = board.getSections(3, 4);
        Pawn instance = new Pawn("black", p1, game.getBoard().getSections(3, 5));
        instance.setHasMoved(true);
        Boolean expResult = true;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkMove method, of class Pawn.
     */
    @Test
    public void testCheckMoveTwoTilesForwardWhite()
    {
        System.out.println("testCheckMoveTwoTilesForwardWhite");
        Section p_section = board.getSections(3, 7);
        Pawn instance = new Pawn("white", p1, game.getBoard().getSections(3, 5));
        Boolean expResult = true;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkMove method, of class Pawn.
     */
    @Test
    public void testCheckMoveTwoTilesForwardBlack()
    {
        System.out.println("testCheckMoveTwoTilesForwardBlack");
        Section p_section = board.getSections(6, 3);
        Pawn instance = new Pawn("black", p2, game.getBoard().getSections(6, 5));
        Boolean expResult = true;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkMove method, of class Pawn.
     */
    @Test
    public void testCheckMoveTwoTilesForwardFalseWhite()
    {
        System.out.println("testCheckMoveTwoTilesForwardFalseWhite");
        Section p_section = board.getSections(3, 7);
        Pawn instance = new Pawn("white", p1, game.getBoard().getSections(3, 5));
        instance.setHasMoved(true);
        Boolean expResult = false;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkMove method, of class Pawn.
     */
    @Test
    public void testCheckMoveTwoTilesForwardFalseBlack()
    {
        System.out.println("testCheckMoveTwoTilesForwardFalseBlack");
        Section p_section = board.getSections(3, 3);
        Pawn instance = new Pawn("black", p2, game.getBoard().getSections(3, 5));
        instance.setHasMoved(true);
        Boolean expResult = false;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckMoveTwoTilesForwardFailBlack()
    {
        System.out.println("testCheckMoveTwoTilesForwardFailBlack");
        Section p_section = board.getSections(3, 3);
        Pawn instance = new Pawn("black", p2, game.getBoard().getSections(3, 5));
        instance.setHasMoved(true);
        Boolean expResult = false;
        Boolean result = instance.checkMove(p_section);
        assertEquals(expResult, result);
    }

    /**
     * Test of Promotion method, of class Pawn.
     */
    @Test
    public void testPromotionWhite()
    {
        System.out.println("Promotion");
        Section p_section = board.getSections(1, 7);
        Pawn instance = new Pawn("white", p1, board.getSections(1, 6));
        boolean expResult = true;
        boolean result = instance.Promotion(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testPromotionWhiteFalse()
    {
        System.out.println("Promotion");
        Section p_section = board.getSections(1, 6);
        Pawn instance = new Pawn("white", p1, board.getSections(1, 5));
        boolean expResult = false;
        boolean result = instance.Promotion(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testPromotionBlackFalse()
    {
        System.out.println("Promotion");
        Section p_section = board.getSections(1, 1);
        Pawn instance = new Pawn("black", p2, board.getSections(1, 2));
        boolean expResult = false;
        boolean result = instance.Promotion(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testPromotionBlack()
    {
        System.out.println("Promotion");
        Section p_section = board.getSections(1, 0);
        Pawn instance = new Pawn("black", p2, board.getSections(1, 1));
        boolean expResult = true;
        boolean result = instance.Promotion(p_section);
        assertEquals(expResult, result);
    }

    /**
     * Test of toCaptureWhite method, of class Pawn.
     */
    @Test
    public void testToCaptureWhite()
    {
        System.out.println("toCaptureWhite");
        Section p_section = board.getSections(5, 5);
        Pawn White = new Pawn("white", p1, p_section);
        p_section.setPiece(White);
        Section sectionBlack = board.getSections(6, 6);
        Pawn Black = new Pawn("black", p2, sectionBlack);
        sectionBlack.setPiece(Black);
        boolean expResult = true;
        boolean result = White.checkMove(sectionBlack);
        assertEquals(expResult, result);
    }

    /**
     * Test of toCaptureWhite method, of class Pawn.
     */
    @Test
    public void testToCaptureWhiteHasMoved()
    {
        System.out.println("toCaptureWhite");
        Section p_section = board.getSections(5, 5);
        Pawn White = new Pawn("white", p1, p_section);
        p_section.setPiece(White);
        Section sectionBlack = board.getSections(6, 6);
        Pawn Black = new Pawn("black", p2, sectionBlack);
        sectionBlack.setPiece(Black);
        White.setHasMoved(true);
        boolean expResult = true;
        boolean result = White.checkMove(sectionBlack);
        assertEquals(expResult, result);
    }

    @Test
    public void testToCaptureBlackHasMoved()
    {
        System.out.println("testToCaptureBlackHasMoved");
        Section p_section = board.getSections(5, 5);
        Pawn White = new Pawn("white", p1, p_section);
        p_section.setPiece(White);
        Section sectionBlack = board.getSections(6, 6);
        Pawn Black = new Pawn("black", p2, sectionBlack);
        Black.setHasMoved(true);
        sectionBlack.setPiece(Black);
        boolean expResult = true;
        boolean result = Black.checkMove(p_section);
        assertEquals(expResult, result);
    }

    @Test
    public void testToCaptureBlack()
    {
        System.out.println("testToCaptureBlackHasMoved");
        Section p_section = board.getSections(5, 5);
        Pawn White = new Pawn("white", p1, p_section);
        p_section.setPiece(White);
        Section sectionBlack = board.getSections(6, 6);
        Pawn Black = new Pawn("black", p2, sectionBlack);
        sectionBlack.setPiece(Black);
        boolean expResult = true;
        boolean result = Black.checkMove(p_section);
        assertEquals(expResult, result);
    }

    /**
     * Test of moveEnPassant method, of class Pawn.
     */
    @Test
    public void testMoveEnPassantWhite()
    {
        System.out.println("testMoveEnPassant");
        Section p_section = board.getSections(2, 4);
        Pawn White = new Pawn("white", p1, p_section);
        p_section.setPiece(White);
        Section sectionBlack = board.getSections(1, 4);
        Section PrevsectionBlack = board.getSections(1, 6);
        Pawn Black = new Pawn("black", p2, sectionBlack);
        sectionBlack.setPiece(Black);
        Black.setPrevSection(PrevsectionBlack);
        boolean expResult = true;
        boolean result = White.checkMove(board.getSections(1, 5));
        assertEquals(expResult, result);
    }

    @Test
    public void testMoveEnPassantWhiteHasMoved()
    {

        System.out.println("testMoveEnPassant");
        Section p_section = board.getSections(2, 4);
        Pawn White = new Pawn("white", p1, p_section);
        p_section.setPiece(White);
        Section sectionBlack = board.getSections(1, 4);
        Section PrevsectionBlack = board.getSections(1, 6);
        Pawn Black = new Pawn("black", p2, sectionBlack);
        sectionBlack.setPiece(Black);
        Black.setPrevSection(PrevsectionBlack);
        White.setHasMoved(true);
        boolean expResult = true;
        boolean result = White.checkMove(board.getSections(1, 5));
        assertEquals(expResult, result);

    }

    @Test
    public void testMoveEnPassantBlackHasMoved()
    {

        System.out.println("testMoveEnPassant");
        Section p_section = board.getSections(2, 3);
        Pawn White = new Pawn("white", p1, p_section);
        p_section.setPiece(White);
        Section sectionBlack = board.getSections(1, 3);
        Section PrevsectionWhite = board.getSections(2, 1);
        Pawn Black = new Pawn("black", p2, sectionBlack);
        sectionBlack.setPiece(Black);
        White.setPrevSection(PrevsectionWhite);
        Black.setHasMoved(true);
        boolean expResult = true;
        boolean result = Black.checkMove(board.getSections(2, 2));
        assertEquals(expResult, result);
    }

    @Test
    public void testMoveEnPassantBlack()
    {

        System.out.println("testMoveEnPassant");
        Section p_section = board.getSections(2, 3);
        Pawn White = new Pawn("white", p1, p_section);
        p_section.setPiece(White);
        Section sectionBlack = board.getSections(1, 3);
        Section PrevsectionWhite = board.getSections(2, 1);
        Pawn Black = new Pawn("black", p2, sectionBlack);
        sectionBlack.setPiece(Black);
        White.setPrevSection(PrevsectionWhite);
        boolean expResult = true;
        boolean result = Black.checkMove(board.getSections(2, 2));
        assertEquals(expResult, result);
    }

    /**
     * Test of resetThePrevSection method, of class Pawn.
     */
    @Test
    public void testResetThePrevSection()
    {
        Section p_section = board.getSections(2, 3);
        Section prevSection = board.getSections(2, 2);
        Pawn White = new Pawn("white", p1, p_section);
        White.setPrevSection(prevSection);
        White.resetThePrevSection();
        assertEquals(p_section,White.getPrevSection());
    }
    /**
     * Test of resetThePrevSection method, of class Pawn.
     */
    @Test
    public void testResetThePrevSectionNull()
    {
        Section p_section = board.getSections(2, 3);
        Section prevSection = board.getSections(2, 2);
        Pawn White = new Pawn("white", p1, p_section);
        White.setPrevSection(prevSection);
        White.resetThePrevSection();
        assertEquals(p_section,White.getPrevSection());
    }

}
