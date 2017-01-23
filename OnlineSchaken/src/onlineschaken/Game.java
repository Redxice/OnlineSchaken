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
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javax.swing.JOptionPane;

/**
 *
 * @author redxice
 */
public class Game implements Serializable
{

    //fields
    private int time;
    private static long serialVersionUID = 684982647421852022L;
    private int gameNumber;
    private int remaining1;
    private int remaining2;
    private transient Timer timer;
    private boolean finished;
    private transient Tournament tournament;
    private Player player1;
    private Player player2;
    private transient List<Player> spectators = new ArrayList<>();
    private Player winner;
    private boolean whiteTurn;
    private transient IngameController ingame;
    private transient List<Chatline> chat = new ArrayList<>();
    private transient Board board;
    private OnlineSchaken javaFX;
    private boolean player1Draw = false;
    private boolean player2Draw = false;
    private boolean gameDraw = false;

    //constructor voor game die geen deel uitmaakt van een tournament

    /**
     *
     * @param p_player1
     * @param p_player2
     * @param javaFX
     * @param client
     * @param ingame
     */
    public Game(Player p_player1, Player p_player2, OnlineSchaken javaFX, ClientApp client, IngameController ingame)
    {
        this.player1 = p_player1;
        this.player2 = p_player2;
        this.javaFX = javaFX;
        remaining1 = 1800;
        remaining2 = 1800;
        timer = new Timer();
        timer.schedule(new GameTimer(this, board), 0, 1000);
        board.setGame(this);
        this.ingame = ingame;
    }

    //zonder timer

    /**
     *
     * @param p_player1
     * @param p_player2
     * @param client
     * @param ingame
     */
    public Game(Player p_player1, Player p_player2, IrmiClient client, IngameController ingame)
    {
        this.player1 = p_player1;
        this.player2 = p_player2;
        this.javaFX = javaFX;
        board = new Board(client);
        remaining1 = 1800;
        remaining2 = 1800;
        board.setGame(this);
        this.ingame = ingame;
        timer = new Timer();
        timer.schedule(new GameTimer(this, board), 0, 1000);
    }

    /**
     * Voor het hervatten van een game
     *
     * @param game
     * @param ingame
     * @param client
     */
    public Game(Game game, IrmiClient client, IngameController ingame)
    {
        this.player1 = game.getPlayer1();
        this.player2 = game.getPlayer2();
        this.javaFX = javaFX;
        this.whiteTurn = game.isWhiteTurn();
        this.finished = game.isFinished();
        this.remaining1 = game.remaining1;
        this.remaining2 = game.remaining2;
        this.ingame = ingame;
        board = new Board(client);
        board.setGame(this);
    }

    //constructor vor een game die deel is van een tournament

    /**
     *
     * @param p_time
     * @param p_player1
     * @param p_player2
     * @param p_tournament
     * @param javaFX
     * @param client
     * @param ingame
     */
    public Game(int p_time, Player p_player1, Player p_player2,
            Tournament p_tournament, OnlineSchaken javaFX, ClientApp client, IngameController ingame)
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
    }

    /**
     *
     * @param i
     * @return
     */
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
    
    /**
     *
     * @return
     */
    public double getTime()
    {
        return time;
    }

    /**
     *
     * @param time
     */
    public void setTime(int time)
    {
        this.time = time;
    }

    /**
     *
     * @return
     */
    public double getResterend1()
    {
        return remaining1;
    }

    /**
     *
     * @return
     */
    public Timer getTimer()
    {
        return timer;
    }

    /**
     *
     * @param seconde
     */
    public void setResterend1(int seconde)
    {
        this.remaining1 = remaining1 - seconde;
    }

    /**
     *
     * @return
     */
    public double getResterend2()
    {
        return remaining2;
    }

    /**
     *
     * @param seconde
     */
    public void setResterend2(int seconde)
    {
        this.remaining2 = remaining2 - seconde;
    }

    /**
     *
     * @return
     */
    public boolean isFinished()
    {
        return finished;
    }

    /**
     *
     * @param finished
     */
    public void setFinished(boolean finished)
    {
        this.finished = finished;
        if (this.finished == true)
        {
            if (gameDraw == false)
            {
                Platform.runLater(()->JOptionPane.showOptionDialog(null, String.valueOf(winner.getUsername()) + " has won.", "Victory!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null));
                int exit = 0;//JOptionPane.showOptionDialog(null, String.valueOf(winner.getUsername()) + " has won.", "Victory!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (exit == 0)
                {
                    this.ingame.GoToLobby();
                }
            } else
            {
                int exit = JOptionPane.showOptionDialog(null, "Het is Gelijkspel.", "Gelijkspel!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (exit == 0)
                {
                    this.ingame.GoToLobby();
                }
            }
        }
    }

    /**
     * update the game timers
     */
    public void update()
    {
        ingame.updateTimers();
    }

    /**
     *
     * @return
     */
    public Tournament getTournament()
    {
        return tournament;
    }

    /**
     *
     * @param tournament
     */
    public void setTournament(Tournament tournament)
    {
        this.tournament = tournament;
    }

    /**
     *
     * @return
     */
    public Player getPlayer1()
    {
        return player1;
    }

    /**
     *
     * @param player1
     */
    public void setPlayer1(Player player1)
    {
        this.player1 = player1;
    }

    /**
     *
     * @return
     */
    public Player getPlayer2()
    {
        return player2;
    }

    /**
     *
     * @param player2
     */
    public void setPlayer2(Player player2)
    {
        this.player2 = player2;
    }

    /**
     *
     * @return
     */
    public List<Player> getSpectators()
    {
        if (this.spectators == null)
        {
            spectators = new ArrayList<>();
        }
        return spectators;
    }

    /**
     *
     * @param spectators
     */
    public void setSpectators(List<Player> spectators)
    {
        this.spectators = spectators;
    }

    /**
     *
     * @return
     */
    public Player getWinner()
    {
        return winner;
    }

    /**
     *
     * @param winner
     */
    public void setWinner(Player winner)
    {
        this.winner = winner;
    }

    /**
     *
     * @return
     */
    public boolean isWhiteTurn()
    {
        return whiteTurn;
    }

    /**
     *
     * @param whiteTurn
     */
    public void setWhiteTurn(boolean whiteTurn)
    {
        this.whiteTurn = whiteTurn;
    }

    /**
     *
     * @return
     */
    public List<Chatline> getChat()
    {
        return chat;
    }

    /**
     *
     * @param chat
     */
    public void setChat(List<Chatline> chat)
    {
        this.chat = chat;
    }

    /**
     *
     * @return
     */
    public Board getBoard()
    {
        return board;
    }

    /**
     *
     * @param board
     */
    public void setBoard(Board board)
    {
        this.board = board;
    }

    //methodes
    //voegt spectator toe aan de lijst spectators

    /**
     *
     * @param p_spectator
     */
    public void addSpectator(Player p_spectator)
    {
        spectators.add(p_spectator);
    }
    //verwijdert de player uit de spectators lijst.

    /**
     *
     * @param p_spectator
     */
    public void removeSpectator(Player p_spectator)
    {
        spectators.remove(p_spectator);
    }

    //voegt chatline toe aan de lijst Chat.

    /**
     *
     * @param p_chatline
     */
    public void addChatline(Chatline p_chatline)
    {
        chat.add(p_chatline);
    }

    //verwijdert chatline toe aan de lijst Chat.

    /**
     *
     * @param p_chatline
     */
    public void removeChatline(Chatline p_chatline)
    {
        chat.remove(p_chatline);
    }

    /**
     *
     * @param Username
     */
    public void Surrender(String Username)
    {
        if (this.player1.getUsername().equals(Username))
        {
            setWinner(this.player2);
            setFinished(true);
        } else if (this.player2.getUsername().equals(Username))
        {
            setWinner(this.player1);
            setFinished(true);
        }
    }

    /**
     *
     * @return
     */
    public boolean checkMate()
    {
        Piece previousPiece;
        if ("white".equals(board.getTurn()))
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

    /**
     * reset the pieces
     */
    public void SetPiecesAgain()
    {
        for (Piece piece : this.player1.getPieces())
        {
            setCorrectImg(piece);
            piece.fillInTheBlanks(player1);
            piece.resetMySection(this.board.getSections(piece.getX(), piece.getY()));
        }
        for (Piece piece : this.player2.getPieces())
        {
            setCorrectImg(piece);
            piece.fillInTheBlanks(player2);
            piece.resetMySection(this.board.getSections(piece.getX(), piece.getY()));
        }

    }

    /**
     * Zet alle stukken in de begin positie op het bord;
     */
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

    /**
     *
     * @return
     */
    public boolean draw()
    {
        if (staleMate())
        {
            gameDraw = true;
            setFinished(true);
            return true;
        } else if (impossibleCheckMate())
        {
            gameDraw = true;
            setFinished(true);
            return true;
        }
        return false;
    }

    // kijkt of het onmogenlijk is om schaak te zetten

    /**
     *
     * @return
     */
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
        }
        if (player2.getPieces().size() <= 2)
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

    /**
     *
     * @return
     */
    public boolean staleMate()
    {
        for (Piece p : player1.getPieces())
        {
            if (p.canMove())
            {
                return false;
            }
        }
        return true;
    }

    /**
     * player 1 requests draw
     */
    public void setPlayer1Draw()
    {
        if (player1Draw == true)
        {
            this.player1Draw = false;
        } else
        {
            this.player1Draw = true;
        }
    }

    /**
     * player 2 requests draw
     */
    public void setPlayer2Draw()
    {
        this.player2Draw = player2Draw != true;
    }

    /**
     * pawn wordt hier gepromote zonder popup.
     *
     * @param piece
     * @param pawn
     */
    public void PromotePawn(Piece piece, Pawn pawn)
    {
        Section section = this.board.getSections(pawn.getX(), pawn.getY());
        Piece HopefullyAPawn = section.getPiece();
        if (HopefullyAPawn instanceof Pawn)
        {
            System.out.println("Deze pawn word promoted"+HopefullyAPawn);
            Pawn localPawn = (Pawn) HopefullyAPawn;
            localPawn.PromoteThisPawn(piece);
        }
        try
        {
            this.board.getClient().GetGameController().setIsWaitForPromotion(false);

        } catch (RemoteException ex)
        {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    public boolean isPlayer1Draw()
    {
        return player1Draw;
    }

    /**
     *
     * @return
     */
    public boolean isPlayer2Draw()
    {
        return player2Draw;
    }

    /**
     * check if both players agree to a draw
     */
    public void checkDraw()
    {
        if (this.player1Draw && this.player2Draw)
        {
            this.gameDraw = true;
            setFinished(true);
        }
    }

    /**
     *
     * @param gameNumber
     */
    public void setGameNumber(int gameNumber)
    {
        this.gameNumber = gameNumber;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString()
    {
        return "Game{" + "GameNr=" + gameNumber + ", player1=" + player1 + ", player2=" + player2 + ", winner=" + winner + '}';
    }
    
    /**
     *
     * @param piece
     */
    public void setCorrectImg(Piece piece)
    {
        switch (piece.MyType)
        {
            case "Bishop":
                if (piece.getColor().equals("white"))
                {
                    piece.setImg(new Image("ChessPieces/White Bishop.jpg"));
                } else if (piece.getColor().equals("black"))
                {
                    piece.setImg(new Image("ChessPieces/Black Bishop.jpg"));
                }   break;
            case "King":
                if (piece.getColor().equals("white"))
                {
                    piece.setImg(new Image("ChessPieces/White King.jpg"));
                }   if (piece.getColor().equals("black"))
                {
                    piece.setImg(new Image("ChessPieces/Black King.jpg"));
                }   break;
            case "Knight":
                if (piece.getColor().equals("white"))
                {
                    piece.setImg(new Image("ChessPieces/White Knight.jpg"));
                } else if (piece.getColor().equals("black"))
                {
                    piece.setImg(new Image("ChessPieces/Black Knight.jpg"));
                }   break;
            case "Pawn":
                if (piece.getColor().equals("white"))
                {
                    piece.setImg(new Image("ChessPieces/White Pawn.jpg"));
                } else if (piece.getColor().equals("black"))
                {
                    piece.setImg(new Image("ChessPieces/Black Pawn.jpg"));
                }   break;
            case "Queen":
                if (piece.getColor().equals("white"))
                {
                    piece.setImg(new Image("ChessPieces/White Queen.jpg"));
                } else if (piece.getColor().equals("black"))
                {
                    piece.setImg(new Image("ChessPieces/Black Queen.jpg"));
                }   break;
            case "Rook":
                if (piece.getColor().equals("white"))
                {
                    piece.setImg(new Image("ChessPieces/White Rook.jpg"));
                } else if (piece.getColor().equals("black"))
                {
                    piece.setImg(new Image("ChessPieces/Black Rook.jpg"));
                }   break;
            default:
                break;
        }
    }
  
}
