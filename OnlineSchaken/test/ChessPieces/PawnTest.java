/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChessPieces;

import onlineschaken.*;
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
public class PawnTest
{
    private static OnlineSchaken onlineSchaken;
    private Game game;
    private Player p1;
    private Player p2;
    private Board board;
    private Section testSection;
    private Section testSection2;
    private Pawn pawnWhite;
    private Pawn pawnBlack;
    
    public PawnTest()
    {
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
        onlineSchaken = new OnlineSchaken();
         p1 = new Player("p1", "ww", 0);
         p2 = new Player("p2", "ww", 0);
         game = new Game(p1, p2);
        game.getBoard().createContent();
        game.setPieces();
        game.getBoard().createContent2();
         
    }
    
    @After
    public void tearDown()
    {
        p1 = null;
        testSection = null;
        testSection2= null;
        pawnWhite = null;  
    }
/**
 * check de getter and setter van PrevSection
 */
    @Test
    public void setPrevSectionAndGetSection(){
        
       pawnWhite.setPrevSection(testSection2);
       Section prevSection =testSection2;
       assertEquals(prevSection.getID(),pawnWhite.getPrevSection().getID());
    }
}
