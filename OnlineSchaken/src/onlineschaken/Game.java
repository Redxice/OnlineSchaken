/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import Server.ClientApp;
import Shared.IrmiClient;
import gui.OnlineSchaken;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javax.swing.JOptionPane;

/**
 *
 * @author redxice
 */
public class Game
{

    //fields
    private int time;
    private int remaining1;
    private int remaining2;
    private Timer timer;
    private boolean finished;
    private Tournament tournament;
    private Player player1;
    private Player player2;
    private List<Player> spectators = new ArrayList<>();
    private Player winner;
    private boolean whiteTurn;
    private List<Chatline> chat = new ArrayList<>();
    private Board board;
    private Gamelobby gamelobby;
    private OnlineSchaken javaFX;
    private boolean player1Draw = false;
    private boolean player2Draw = false;
    private boolean gameDraw = false;
    //constructor voor game die geen deel uitmaakt van een tournament
    public Game(Player p_player1, Player p_player2, OnlineSchaken javaFX, ClientApp client)
    {
        this.player1 = p_player1;
        this.player2 = p_player2;
        this.javaFX = javaFX;
        board = new Board(client);
        remaining1 = 1800;
        remaining2 = 1800;
        timer = new Timer();
        timer.schedule(new GameTimer(this, board, this.javaFX), 0, 1000);
        board.setGame(this);
        //RecieveRmi rmi = new RecieveRmi();
    }

    //zonder timer
    public Game(Player p_player1, Player p_player2, IrmiClient client)
    {
        this.player1 = p_player1;
        this.player2 = p_player2;
        this.javaFX = javaFX;
        board = new Board(client);
        board.setGame(this);
    }

    //constructor vor een game die deel is van een tournament
    public Game(int p_time, Player p_player1, Player p_player2,
            Tournament p_tournament, OnlineSchaken javaFX, ClientApp client)
    {

        this.player1 = p_player1;
        this.player2 = p_player2;
        this.time = p_time;
        this.remaining1 = p_time;
        this.remaining2 = p_time;
        this.tournament = p_tournament;
        this.javaFX = javaFX;
        board = new Board(client);
        timer = new Timer();
        timer.schedule(new GameTimer(this, board, this.javaFX), 0, 1000);
    }

    public String resterend(int i)
    {
        if (i == 1)
        {
            int h = remaining1 / 60;
            int m = remaining1 % 60;
            String newtime = String.format("%02d", h) + ":" + String.format("%02d", m);
            return newtime;
        } else
        {
            int h = remaining2 / 60;
            int m = remaining2 % 60;
            String newtime = String.format("%02d", h) + ":" + String.format("%02d", m);
            return newtime;
        }
    }

    //getters and setters
    public double getTime()
    {
        return time;
    }

    public void setTime(int time)
    {
        this.time = time;
    }

    public double getResterend1()
    {
        return remaining1;
    }

    public Timer getTimer()
    {
        return timer;
    }

    public void setResterend1(int seconde)
    {
        this.remaining1 = remaining1 - seconde;
        if (remaining1 <= 0)
        {
            setWinner(player2);
            setFinished(true);
            //timer.cancel();
        }
    }

    public double getResterend2()
    {
        return remaining2;
    }

    public void setResterend2(int seconde)
    {
        this.remaining2 = remaining2 - seconde;
        if (remaining2 <= 0)
        {
            setWinner(player1);
            setFinished(true);
            //timer.cancel();
        }
    }

    public boolean isFinished()
    {
        return finished;
    }

    public void setFinished(boolean finished)
    {
        this.finished = finished;
        if (this.finished == true)
        {
            //timer.cancel();
            if(gameDraw == false)
            {
            int exit = JOptionPane.showOptionDialog(null, String.valueOf(winner.getUsername()) + " has won.", "Victory!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (exit == 0)
            {
                Platform.exit();
            }
            }else if(gameDraw)
            {
            int exit = JOptionPane.showOptionDialog(null, "Het is Gelijkspel.", "Gelijkspel!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (exit == 0)
            {
                Platform.exit();
            }
            }
        }
    }

    public Tournament getTournament()
    {
        return tournament;
    }

    public void setTournament(Tournament tournament)
    {
        this.tournament = tournament;
    }

    public Player getPlayer1()
    {
        return player1;
    }

    public void setPlayer1(Player player1)
    {
        this.player1 = player1;
    }

    public Player getPlayer2()
    {
        return player2;
    }

    public void setPlayer2(Player player2)
    {
        this.player2 = player2;
    }

    public List<Player> getSpectators()
    {
        return spectators;
    }

    public void setSpectators(List<Player> spectators)
    {
        this.spectators = spectators;
    }

    public Player getWinner()
    {
        return winner;
    }

    public void setWinner(Player winner)
    {
        this.winner = winner;
    }

    public boolean isWhiteTurn()
    {
        return whiteTurn;
    }

    public void setWhiteTurn(boolean whiteTurn)
    {
        this.whiteTurn = whiteTurn;
    }

    public List<Chatline> getChat()
    {
        return chat;
    }

    public void setChat(List<Chatline> chat)
    {
        this.chat = chat;
    }

    public Board getBoard()
    {
        return board;
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }

    //methodes
    //voegt spectator toe aan de lijst spectators
    public void addSpectator(Player p_spectator)
    {
        spectators.add(p_spectator);
    }
    //verwijdert de player uit de spectators lijst.

    public void removeSpectator(Player p_spectator)
    {
        spectators.remove(p_spectator);
    }

    //voegt chatline toe aan de lijst Chat.
    public void addChatline(Chatline p_chatline)
    {
        chat.add(p_chatline);
    }

    //verwijdertt chatline toe aan de lijst Chat.
    public void removeChatline(Chatline p_chatline)
    {
        chat.remove(p_chatline);
    }
    public void Surrender(String Username){
        if (this.player1.getUsername().equals(Username))
        {
            setWinner(this.player2);
            setFinished(true);
        }
        else if (this.player2.getUsername().equals(Username))
        {
            setWinner(this.player1);
            setFinished(true);
        }
    }
    public boolean checkMate()
    {
        Piece previousPiece;
        if (board.getTurn() == "white")
        {
            for (Piece p : player2.getPieces())
            {
                for (Section[] x : board.getSections())
                {
                    for (Section section : x)
                    {
                        if (p.checkMove(section))
                        {
                            previousPiece = section.getPiece();
                            section.tempSetPiece(p);
                            for (Piece p2 : player2.getPieces())
                            {
                                if (p2 instanceof King)
                                {
                                    ((King) p2).isCheck();
                                    if (((King) p2).check == false)
                                    {
                                        section.tempSetPiece(previousPiece);
                                        return false;
                                    }
                                }
                            }
                            section.tempSetPiece(previousPiece);
                        }

                    }
                }
            }
            return true;
        } else
        {
            for (Piece p : player1.getPieces())
            {
                for (Section[] x : board.getSections())
                {
                    for (Section section : x)
                    {
                        if (p.checkMove(section))
                        {
                            previousPiece = section.getPiece();
                            section.tempSetPiece(p);
                            for (Piece p2 : player1.getPieces())
                            {
                                if (p2 instanceof King)
                                {
                                    ((King) p2).isCheck();
                                    if (((King) p2).check == false)
                                    {
                                        section.tempSetPiece(previousPiece);
                                        return false;
                                    }
                                }
                            }
                            section.tempSetPiece(previousPiece);
                        }
                    }
                }
            }
            return true;
        }
    }

    // Zet alle stukken in de begin positie op het bord;
    public void setPieces()
    {
        Piece piece;
        player1.getPieces().add(piece = new Rook("white", player1, board.getSections(0, 0)));
        player1.getPieces().add(piece = new Knight("white", player1, board.getSections(1, 0)));
        player1.getPieces().add(piece = new Bishop("white", player1, board.getSections(2, 0)));
        player1.getPieces().add(piece = new Queen("white", player1, board.getSections(3, 0)));
        player1.getPieces().add(piece = new King("white", player1, board.getSections(4, 0)));
        player1.getPieces().add(piece = new Bishop("white", player1, board.getSections(5, 0)));
        player1.getPieces().add(piece = new Knight("white", player1, board.getSections(6, 0)));
        player1.getPieces().add(piece = new Rook("white", player1, board.getSections(7, 0)));
        player1.getPieces().add(piece = new Pawn("white", player1, board.getSections(0, 1)));
        player1.getPieces().add(piece = new Pawn("white", player1, board.getSections(1, 1)));
        player1.getPieces().add(piece = new Pawn("white", player1, board.getSections(2, 1)));
        player1.getPieces().add(piece = new Pawn("white", player1, board.getSections(3, 1)));
        player1.getPieces().add(piece = new Pawn("white", player1, board.getSections(4, 1)));
        player1.getPieces().add(piece = new Pawn("white", player1, board.getSections(5, 1)));
        player1.getPieces().add(piece = new Pawn("white", player1, board.getSections(6, 1)));
        player1.getPieces().add(piece = new Pawn("white", player1, board.getSections(7, 1)));

        player2.getPieces().add(piece = new Rook("black", player2, board.getSections(0, 7)));
        player2.getPieces().add(piece = new Knight("black", player2, board.getSections(1, 7)));
        player2.getPieces().add(piece = new Bishop("black", player2, board.getSections(2, 7)));
        player2.getPieces().add(piece = new Queen("black", player2, board.getSections(3, 7)));
        player2.getPieces().add(piece = new King("black", player2, board.getSections(4, 7)));
        player2.getPieces().add(piece = new Bishop("black", player2, board.getSections(5, 7)));
        player2.getPieces().add(piece = new Knight("black", player2, board.getSections(6, 7)));
        player2.getPieces().add(piece = new Rook("black", player2, board.getSections(7, 7)));
        player2.getPieces().add(piece = new Pawn("black", player2, board.getSections(0, 6)));
        player2.getPieces().add(piece = new Pawn("black", player2, board.getSections(1, 6)));
        player2.getPieces().add(piece = new Pawn("black", player2, board.getSections(2, 6)));
        player2.getPieces().add(piece = new Pawn("black", player2, board.getSections(3, 6)));
        player2.getPieces().add(piece = new Pawn("black", player2, board.getSections(4, 6)));
        player2.getPieces().add(piece = new Pawn("black", player2, board.getSections(5, 6)));
        player2.getPieces().add(piece = new Pawn("black", player2, board.getSections(6, 6)));
        player2.getPieces().add(piece = new Pawn("black", player2, board.getSections(7, 6)));
    }

    // kijkt of het draw is
    public boolean draw()
    {
        if (staleMate())
        {
            //timer.cancel();
            gameDraw = true;
            setFinished(true);
            return true;
        } else if (impossibleCheckMate())
        {
            //timer.cancel();
            gameDraw = true;
            setFinished(true);
            return true;
        } 
        return false;
    }

    // kijkt of het onmogenlijk is om schaak te zetten
    public boolean impossibleCheckMate()
    {
        if (player1.getPieces().size() <= 2)
        {
            if (player2.getPieces().size() == 1)
            {
                for (Piece piece : player1.getPieces())
                {
                    if (piece instanceof Knight || piece instanceof Bishop)
                    {
                        return true;
                    }
                }
            }
        } else if (player2.getPieces().size() <= 2)
        {
            if (player1.getPieces().size() == 1)
            {
                for (Piece piece : player2.getPieces())
                {
                    if (piece instanceof Knight || piece instanceof Bishop)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // kijkt of de speler die aan zet is nog een stuk kan verzetten
    public boolean staleMate()
    {
       for(Piece p : player1.getPieces())
       {
          if(p.canMove())
          {
              return false;
          }
       }
        return true;
    }

    /**
     * @param player1Draw the player1Draw to set
     */
    public void setPlayer1Draw()
    {
        if(player1Draw == true)
        {
            this.player1Draw = false;
        }
        else
        {
            this.player1Draw = true;
        }
    }

    /**
     * @param player2Draw the player2Draw to set
     */
    public void setPlayer2Draw()
    {
        if(player2Draw == true)
        {
            this.player2Draw = false;
        }
        else
        {
            this.player2Draw = true;
        }
    }

    /**
     * pawn wordt hier gepromote zonder popup.
     *
     * @param piece
     */
    public void PromotePawn(Piece piece, Pawn pawn)
    {
        Section section = this.board.getSections(pawn.getX(), pawn.getY());
        Piece HopefullyAPawn = section.getPiece();
        if (HopefullyAPawn instanceof Pawn)
        {
            Pawn localPawn = (Pawn) HopefullyAPawn;
            localPawn.PromoteThisPawn(piece);
        }
        try
        {
            this.board.getClient().GetGameController().setIsWaitingForPromotion(false);
        } catch (RemoteException ex)
        {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isPlayer1Draw() {
        return player1Draw;
    }

    public boolean isPlayer2Draw() {
        return player2Draw;
    }

    public void checkDraw()
    {
        if(this.player1Draw && this.player2Draw)
        {
            this.gameDraw = true;
            setFinished(true);
        }
    }
    
}
