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
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author redxice
 */
public class GameTest
{

    private Game game;
    private Player p1;
    private Player p2;
    private Board board;
    private IrmiClient client;
    private IngameController controller;

    public GameTest()
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

    @After
    public void tearDown()
    {
    }

    /**
     * Test of resterend method, of class Game.
     */
    @Test
    public void testResterend1()
    {
        System.out.println("resterend1");
        int i = 1;
        Game instance = game;
        int remaining = (int) game.getResterend1();
        int h = remaining / 60;
        int m = remaining % 60;
        String expResult = String.format("%02d", h) + ":" + String.format("%02d", m);
        String result = instance.resterend(i);
        if (result.equalsIgnoreCase(expResult))
        {

        } else
        {
            fail("Incorrecte gegevens");
        }
    }

    @Test
    public void testResterend2()
    {
        System.out.println("resterend2");
        int i = 2;
        Game instance = game;
        int remaining = (int) game.getResterend2();
        int h = remaining / 60;
        int m = remaining % 60;
        String expResult = String.format("%02d", h) + ":" + String.format("%02d", m);
        String result = instance.resterend(i);
        if (result.equalsIgnoreCase(expResult))
        {

        } else
        {
            fail("Incorrecte gegevens");
        }
    }

    /**
     * Test of getTime method, of class Game.
     */
    @Test
    public void testGetTime()
    {
        System.out.println("getTime");
        Game instance = game;
        double expResult = 0.0;
        double result = instance.getTime();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setTime method, of class Game.
     */
    @Test
    public void testSetTime()
    {
        System.out.println("setTime");
        int time = 0;
        Game instance = game;
        instance.setTime(time);
        if ((int) game.getTime() == time)
        {

        } else
        {
            fail("Set time has failed");
        }
    }

    /**
     * Test of getResterend1 method, of class Game.
     */
    @Test
    public void testGetResterend1()
    {
        System.out.println("getResterend1");
        Game instance = game;
        int expResult = 1800;
        int result = (int) instance.getResterend1();
        assertEquals(expResult, result);
    }

    /**
     * Test of getResterend1 method, of class Game.
     */
    @Test
    public void testGetResterend2()
    {
        System.out.println("getResterend2");
        Game instance = game;
        int expResult = 1800;
        int result = (int) instance.getResterend2();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTimer method, of class Game.
     */
    @Test
    public void testGetTimer()
    {
        System.out.println("getTimer");
        Game instance = game;
        Timer result = instance.getTimer();
        if (result != null)
        {

        }
        {
            fail("Timer was null");
        }
    }

    /**
     * Test of setResterend1 method, of class Game.
     */
    @Test
    public void testSetResterend1()
    {
        System.out.println("setResterend1");
        int seconde = 1;
        int expresult = 1799;
        Game instance = game;
        instance.setResterend1(seconde);
        assertEquals(expresult, (int) instance.getResterend1());
    }

    /**
     * Test of setResterend2 method, of class Game.
     */
    @Test
    public void testSetResterend2()
    {
        System.out.println("setResterend2");
        int seconde = 1;
        int expresult = 1799;
        Game instance = game;
        instance.setResterend2(seconde);
        assertEquals(expresult, (int) instance.getResterend2());
    }

    /**
     * Test of isFinished method, of class Game.
     */
    @Test
    public void testIsFinished()
    {
        System.out.println("isFinished");
        Game instance = game;
        boolean expResult = false;
        boolean result = instance.isFinished();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFinished method, of class Game.
     */
    @Test
    public void testSetFinishedFalse()
    {
        System.out.println("setFinished");
        boolean finished = false;
        Game instance = game;
        instance.setFinished(finished);
        assertEquals(false, game.isFinished());
    }

    @Test
    public void testSetFinished()
    {
        try
        {
            System.out.println("setFinished");
            boolean finished = true;
            Game instance = game;
            instance.setFinished(finished);
            assertEquals(true, game.isFinished());
        } catch (Exception e)
        {
            return;
        }
    }

    /**
     * Test of update method, of class Game.
     */
    @Ignore
    @Test
    public void testUpdate()
    {
        System.out.println("update");
        Game instance = null;
        instance.update();
    }

    /**
     * Test of getTournament method, of class Game.
     */
    @Ignore
    @Test
    public void testGetTournament()
    {
        System.out.println("getTournament");
        Game instance = null;
        Tournament expResult = null;
        Tournament result = instance.getTournament();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTournament method, of class Game.
     */
    @Ignore
    @Test
    public void testSetTournament()
    {
        System.out.println("setTournament");
        Tournament tournament = null;
        Game instance = null;
        instance.setTournament(tournament);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayer1 method, of class Game.
     */
    @Test
    public void testGetPlayer1()
    {
        System.out.println("getPlayer1");
        Game instance = game;
        Player expResult = p1;
        Player result = instance.getPlayer1();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPlayer1 method, of class Game.
     */
    @Test
    public void testSetPlayer1()
    {
        System.out.println("setPlayer1");
        Player player1 = p1;
        Game instance = game;
        instance.setPlayer1(player1);
        assertEquals(p1, instance.getPlayer1());
    }

    /**
     * Test of getPlayer2 method, of class Game.
     */
    @Test
    public void testGetPlayer2()
    {
        System.out.println("getPlayer2");
        Game instance = game;
        Player expResult = p2;
        Player result = instance.getPlayer2();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPlayer2 method, of class Game.
     */
    @Test
    public void testSetPlayer2()
    {
        System.out.println("setPlayer2");
        Player player2 = p2;
        Game instance = game;
        instance.setPlayer1(player2);
        assertEquals(p1, instance.getPlayer2());
    }

    /**
     * Test of getSpectators method, of class Game.
     */
    @Test
    public void testGetSpectators()
    {
        System.out.println("getSpectators");
        Game instance = game;
        List<Player> expResult = new ArrayList<>();
        List<Player> result = instance.getSpectators();
        assertEquals(expResult, result);
    }

    /**
     * Test of setSpectators method, of class Game.
     */
    @Test
    public void testSetSpectators()
    {
        System.out.println("setSpectators");
        Player spectator = new Player("X", "D@D.com", 1000);
        List<Player> spectators = new ArrayList<>();
        spectators.add(spectator);
        Game instance = game;
        instance.setSpectators(spectators);
        assertEquals(spectators, game.getSpectators());
    }

    /**
     * Test of getWinner method, of class Game.
     */
    @Test
    public void testGetWinner()
    {
        System.out.println("getWinner");
        Game instance = game;
        Player expResult = null;
        Player result = instance.getWinner();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWinner method, of class Game.
     */
    @Test
    public void testSetWinner()
    {
        System.out.println("setWinner");
        Player winner = p1;
        Game instance = game;
        instance.setWinner(winner);
        assertEquals(p1, game.getWinner());
    }

    /**
     * Test of isWhiteTurn method, of class Game.
     */
    @Test
    public void testIsWhiteTurn()
    {
        System.out.println("isWhiteTurn");
        Game instance = game;
        boolean expResult = true;
        boolean result = instance.isWhiteTurn();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWhiteTurn method, of class Game.
     */
    @Test
    public void testSetWhiteTurn()
    {
        System.out.println("setWhiteTurn");
        boolean whiteTurn = false;
        Game instance = game;
        instance.setWhiteTurn(whiteTurn);
        assertEquals(whiteTurn, game.isWhiteTurn());
    }

    /**
     * Test of getChat method, of class Game.
     */
    @Test
    public void testGetChat()
    {
        System.out.println("getChat");
        Game instance = game;
        List<Chatline> expResult = new ArrayList<>();
        List<Chatline> result = instance.getChat();
        assertEquals(expResult, result);
    }

    /**
     * Test of setChat method, of class Game.
     */
    @Test
    public void testSetChat()
    {
        System.out.println("setChat");
        List<Chatline> chat = new ArrayList<>();
        Game instance = game;
        instance.setChat(chat);
        assertEquals(chat, game.getChat());
    }

    /**
     * Test of getBoard method, of class Game.
     */
    @Test
    public void testGetBoard()
    {
        System.out.println("getBoard");
        Game instance = game;
        Board expResult = board;
        Board result = instance.getBoard();
        assertEquals(expResult, result);
    }

    /**
     * Test of setBoard method, of class Game.
     */
    @Test
    public void testSetBoard()
    {
        System.out.println("setBoard");
        Board board = this.board;
        Game instance = game;
        instance.setBoard(board);
        assertEquals(this.board, instance.getBoard());
    }

    /**
     * Test of addSpectator method, of class Game.
     */
    @Test
    public void testAddSpectator()
    {
        System.out.println("addSpectator");
        Player p_spectator = new Player("X", "D@D.com", 1000);
        Game instance = game;
        instance.addSpectator(p_spectator);
        Player result = game.getSpectators().get(0);
        assertEquals(p_spectator, result);
    }

    /**
     * Test of removeSpectator method, of class Game.
     */
    @Test
    public void testRemoveSpectator()
    {
        try
        {
            System.out.println("removeSpectator");
            Player p_spectator = new Player("X", "D@D.com", 1000);
            Game instance = game;
            instance.addSpectator(p_spectator);
            instance.removeSpectator(p_spectator);
        } catch (Exception e)
        {
            fail("threw error when trying to remove spectator");
        }
    }

    /**
     * Test of addChatline method, of class Game.
     */
    @Test
    public void testAddChatline()
    {
        try
        {
            System.out.println("addChatline");
            Chatline p_chatline = new Chatline(p1.getUsername(), "DSF");
            Game instance = game;
            instance.addChatline(p_chatline);
        } catch (Exception e)
        {
            fail("Threw error when adding user");
        }
    }

    /**
     * Test of removeChatline method, of class Game.
     */
    @Test
    public void testRemoveChatline()
    {
        try
        {
            System.out.println("removeChatline");
            Chatline p_chatline = new Chatline(p1.getUsername(), "DSF");
            Game instance = game;
            instance.addChatline(p_chatline);
            instance.removeChatline(p_chatline);
        } catch (Exception e)
        {
            fail("Threw error when " + e.getMessage());
        }
    }

    /**
     * Test of Surrender method, of class Game.
     */
    @Test
    public void testSurrender()
    {
        System.out.println("Surrender");
        String Username = p1.getUsername();
        Game instance = game;
        instance.Surrender(Username);
        if (game.getWinner() == p2)
        {

        } else
        {
            fail("Wrong winner");
        }

    }

    /**
     * Test of checkMate method, of class Game.
     */
    @Ignore
    @Test
    public void testCheckMate()
    {
        System.out.println("checkMate");
        Game instance = null;
        boolean expResult = false;
        boolean result = instance.checkMate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SetPiecesAgain method, of class Game.
     */
    @Ignore
    @Test
    public void testSetPiecesAgain()
    {
        System.out.println("SetPiecesAgain");
        Game instance = game;
        instance.SetPiecesAgain();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPieces method, of class Game.
     */
    @Test
    public void testSetPieces()
    {
        System.out.println("setPieces");
        Game instance = game;
        instance.setPieces();
        if (p1.getPieces().size() > 0 && p2.getPieces().size() > 0)
        {

        } else
        {
            fail("setPieces failed");
        }
    }

    /**
     * Test of draw method, of class Game.
     */
    @Test
    public void testDraw()
    {
        System.out.println("draw");
        Game instance = game;
        game.setPieces();
        boolean expResult = false;
        boolean result = instance.draw();
        assertEquals(expResult, result);
    }

    /**
     * Test of impossibleCheckMate method, of class Game.
     */
    @Test
    public void testImpossibleCheckMate1()
    {
        System.out.println("impossibleCheckMate");
        Game instance = game;
        p1.getPieces().add(new Knight("white", p1, board.getSections(1, 2)));
        p2.getPieces().add(new Knight("black", p2, board.getSections(3, 3)));
        boolean expResult = true;
        boolean result = instance.impossibleCheckMate();
        assertEquals(expResult, result);
    }

    /**
     * Test of impossibleCheckMate method, of class Game.
     */
    @Test
    public void testImpossibleCheckMate2()
    {
        System.out.println("impossibleCheckMate2");
        Game instance = game;
        p1.getPieces().add(new Bishop("white", p1, board.getSections(1, 1)));
        p1.getPieces().add(new Pawn("white", p1, board.getSections(1, 2)));
        p2.getPieces().add(new Knight("black", p2, board.getSections(3, 3)));
        boolean expResult = true;
        boolean result = instance.impossibleCheckMate();
        assertEquals(expResult, result);
    }

    /**
     * Test of impossibleCheckMate method, of class Game.
     */
    @Test
    public void testImpossibleCheckMate3()
    {
        System.out.println("impossibleCheckMate3");
        Game instance = game;
        p1.getPieces().add(new Bishop("white", p1, board.getSections(1, 1)));
        p2.getPieces().add(new Pawn("white", p1, board.getSections(1, 2)));
        p2.getPieces().add(new Knight("black", p2, board.getSections(3, 3)));
        boolean expResult = true;
        boolean result = instance.impossibleCheckMate();
        assertEquals(expResult, result);
    }

    /**
     * Test of impossibleCheckMate method, of class Game.
     */
    @Test
    public void testImpossibleCheckMate4()
    {
        System.out.println("impossibleCheckMate4");
        Game instance = game;
        p1.getPieces().add(new Bishop("white", p1, board.getSections(1, 1)));
        p2.getPieces().add(new Pawn("white", p1, board.getSections(1, 2)));
        p2.getPieces().add(new Bishop("black", p2, board.getSections(3, 3)));
        boolean expResult = true;
        boolean result = instance.impossibleCheckMate();
        assertEquals(expResult, result);
    }

    @Test
    public void testImpossibleCheckMate5()
    {
        System.out.println("impossibleCheckMate5");
        Game instance = game;
        p1.getPieces().add(new Bishop("white", p1, board.getSections(1, 1)));
        p1.getPieces().add(new Bishop("white", p1, board.getSections(1, 5)));
        p2.getPieces().add(new Pawn("white", p1, board.getSections(1, 2)));
        p2.getPieces().add(new Bishop("black", p2, board.getSections(3, 3)));
        p2.getPieces().add(new Bishop("black", p2, board.getSections(3, 4)));
        boolean expResult = true;
        boolean result = instance.impossibleCheckMate();
        assertEquals(expResult, result);
    }

    /**
     * Test of staleMate method, of class Game.
     */
    @Test
    public void testStaleMate()
    {
        System.out.println("staleMate");
        Game instance = game;
        game.setPieces();
        boolean expResult = false;
        boolean result = instance.staleMate();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPlayer1Draw method, of class Game.
     */
    @Test
    public void testSetPlayer1Draw()
    {
        try{
        System.out.println("setPlayer1Draw");
        Game instance = game;
        instance.setPlayer1Draw();}catch(Exception e){
            fail(e.getMessage());
        }
    }

    /**
     * Test of setPlayer2Draw method, of class Game.
     */
    @Test
    public void testSetPlayer2Draw()
    {
        try{
        System.out.println("setPlayer2Draw");
        Game instance = game;
        instance.setPlayer2Draw();
        }catch(Exception e){
            fail(e.getMessage());
        }
    }

    /**
     * Test of PromotePawn method, of class Game.
     */
    @Ignore@Test
    public void testPromotePawn()
    {
        System.out.println("PromotePawn");
        Piece piece = null;
        Pawn pawn = null;
        Game instance = null;
        instance.PromotePawn(piece, pawn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isPlayer1Draw method, of class Game.
     */
    @Test
    public void testIsPlayer1Draw()
    {
        System.out.println("isPlayer1Draw");
        Game instance = game;
        boolean expResult = false;
        boolean result = instance.isPlayer1Draw();
        assertEquals(expResult, result);
    }

    /**
     * Test of isPlayer2Draw method, of class Game.
     */
    @Test
    public void testIsPlayer2Draw()
    {
        System.out.println("isPlayer2Draw");
        Game instance = game;
        boolean expResult = false;
        boolean result = instance.isPlayer2Draw();
        assertEquals(expResult, result);
    }

    /**
     * Test of checkDraw method, of class Game.
     */
    @Test
    public void testCheckDraw1()
    {
        System.out.println("checkDraw1");
        Game instance = game;
        instance.checkDraw();
    }
    @Test
    public void testCheckDraw2()
    {
       System.out.println("checkDraw2");
        Game instance = game;
        game.setPlayer1Draw();
        game.setPlayer2Draw();
        instance.checkDraw();
        if (!game.isFinished())
        {
            fail("game not finished");
        }
    }

    /**
     * Test of setGameNr method, of class Game.
     */
    @Test
    public void testSetGameNr()
    {
        try{
        System.out.println("setGameNr");
        int GameNr = 1;
        Game instance = game;
        instance.setGameNr(GameNr);
        }catch(Exception e){
            fail(e.getMessage());
        }
        
    }

    /**
     * Test of toString method, of class Game.
     */
    @Test
    public void testToString()
    {
        System.out.println("toString");
        Game instance = game;
        String expResult =  "Game{" + "GameNr=" +0+", player1="+p1+ ", player2=" + p2 + ", winner=" + game.getWinner() + '}';
        String result = instance.toString();
        if (!result.equalsIgnoreCase(expResult))
        {
            fail("Strings do not match");
        }
    }

    /**
     * Test of setCorrectImg method, of class Game.
     */
    @Test
    public void testSetCorrectImg()
    {
        System.out.println("setCorrectImg");
        Piece piece = new Pawn("white",p1,board.getSections(0, 0));
        Game instance = null;
        instance.setCorrectImg(piece);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
