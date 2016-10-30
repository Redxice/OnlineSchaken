/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.awt.Point;
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
public class PawnTest
{

    OnlineSchaken onlineSchaken;
    Game game;
    Player p1;
    Player p2;
    Board board;

    public PawnTest()
    {
        onlineSchaken = new OnlineSchaken();
        p1 = new Player("p1", "ww", 0);
        p2 = new Player("p2", "ww", 0);
        game = new Game(p1, p2);
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void TestGetPlayer()
    {
        Piece pawnWhite = new Pawn("Black", p1, game.board.getSections(0, 0));
        String actual = pawnWhite.player.username;
        String expected = "p1";
        assertEquals(actual, expected);
    }

    @Test
    public void TestGetColor()
    {
        Piece pawnWhite = new Pawn("Black", p1, game.board.getSections(0, 0));
        String actual = pawnWhite.getColor();
        String expected = "Black";
        assertEquals(actual, expected);
    }
    
    @Test
    public void TestGetSection()
    {
        Piece pawnWhite = new Pawn("Black", p1, game.board.getSections(3, 5));
        Point actual = pawnWhite.getSection().id;
        Point expected = new Point(3,5);
        assertEquals(actual, expected);
    }
    
    @Test
    public void TestPrevSection()
    {
        Pawn pawnWhite = new Pawn("Black", p1, game.board.getSections(3, 5));
        pawnWhite.setPrevSection(game.board.getSections(2, 2));
        Point actual = pawnWhite.getPrevSection().id;
        Point expected = new Point(2,2);
        assertEquals(actual, expected);
    }
    
}
