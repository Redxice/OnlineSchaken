/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;


import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 *
 * @author redxice
 */
public class Game {
    //fields
    int time;
    int resterend1;
    int resterend2;
    Timer timer ;
    boolean finished;
    Tournament tournament;
    Player player1;
    Player player2;
    List<Player> spectators = new ArrayList<>();
    Player winner;
    boolean whiteTurn;
    List<Chatline> chat = new ArrayList<>();
    Board board;
    Gamelobby gamelobby;
    //constructor voor game die geen deel uitmaakt van een tournament
    public Game(Player p_player1,Player p_player2){        
        this.player1 = p_player1;
        this.player2 = p_player2;
        //this.time = p_time;
        board = new Board();
        resterend1 = 100;
        resterend2 = 150;
        timer = new Timer();
        timer.schedule(new GameTimer(this), 0,1000);
    }
    //constructor vor een game die deel is van een tournament
    public Game(int p_time,Player p_player1,Player p_player2,
            Tournament p_tournament){
        
        this.player1 = p_player1;
        this.player2 = p_player2;
        this.time = p_time;
        this.resterend1 =p_time;
        this.resterend2 =p_time;
        this.tournament = p_tournament;
        board = new Board();  
        timer = new Timer();
        timer.schedule(new GameTimer(this), 0,1000);
    }

    //getters and setters
    public double getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getResterend1() {
        return resterend1;
    }

    public void setResterend1(int seconde) {
        this.resterend1 = resterend1-seconde;
          if (resterend1 <=0)
        {
         setWinner(player2)  ;
         setFinished(true);
         timer.cancel();
        }
    }

    public double getResterend2() {
        return resterend2;
    }

    public void setResterend2(int seconde) {
        this.resterend2 = resterend2-seconde;
        if (resterend2 <=0)
        {
         setWinner(player1)  ;
         setFinished(true);
         timer.cancel();
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public List<Player> getSpectators() {
        return spectators;
    }

    public void setSpectators(List<Player> spectators) {
        this.spectators = spectators;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void setWhiteTurn(boolean whiteTurn) {
        this.whiteTurn = whiteTurn;
    }

    public List<Chatline> getChat() {
        return chat;
    }

    public void setChat(List<Chatline> chat) {
        this.chat = chat;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
    
  //methodes
    //voegt spectator toe aan de lijst spectators
   public void addSpectator(Player p_spectator){
       spectators.add(p_spectator);
   }
   //verwijdert de player uit de spectators lijst.
   public void removeSpectator(Player p_spectator){
       spectators.remove(p_spectator);
   }
   
   //voegt chatline toe aan de lijst Chat.
   public void addChatline(Chatline p_chatline){
      chat.add(p_chatline);
   }
   
   //verwijdertt chatline toe aan de lijst Chat.
   public void removeChatline(Chatline p_chatline){
      chat.remove(p_chatline);
   }
   
   public boolean checkMate(){
      return false;
   }
   
   // Zet alle stukken in de begin positie op het bord;
    public void setPieces()
    {
    Piece piece;
    player1.pieces.add(piece = new Rook("white",player1,board.getSections(0,0)));
    player1.pieces.add(piece = new Knight("white",player1,board.getSections(1,0)));
    player1.pieces.add(piece = new Bishop("white",player1,board.getSections(2,0)));
    player1.pieces.add(piece = new Queen("white",player1,board.getSections(3,0)));
    player1.pieces.add(piece = new King("white",player1,board.getSections(4,0)));
    player1.pieces.add(piece = new Bishop("white",player1,board.getSections(5,0)));
    player1.pieces.add(piece = new Knight("white",player1,board.getSections(6,0)));
    player1.pieces.add(piece = new Rook("white",player1,board.getSections(7,0)));
    player1.pieces.add(piece = new Pawn("white",player1,board.getSections(0,1)));
    player1.pieces.add(piece = new Pawn("white",player1,board.getSections(1,1)));
    player1.pieces.add(piece = new Pawn("white",player1,board.getSections(2,1)));
    player1.pieces.add(piece = new Pawn("white",player1,board.getSections(3,1)));
    player1.pieces.add(piece = new Pawn("white",player1,board.getSections(4,1)));
    player1.pieces.add(piece = new Pawn("white",player1,board.getSections(5,1)));
    player1.pieces.add(piece = new Pawn("white",player1,board.getSections(6,1)));
    player1.pieces.add(piece = new Pawn("white",player1,board.getSections(7,1)));
    
    player2.pieces.add(piece = new Rook("black",player2,board.getSections(0,7)));
    player2.pieces.add(piece = new Knight("black",player2,board.getSections(1,7)));
    player2.pieces.add(piece = new Bishop("black",player2,board.getSections(2,7)));
    player2.pieces.add(piece = new Queen("black",player2,board.getSections(3,7)));
    player2.pieces.add(piece = new King("black",player2,board.getSections(4,7)));
    player2.pieces.add(piece = new Bishop("black",player2,board.getSections(5,7)));
    player2.pieces.add(piece = new Knight("black",player2,board.getSections(6,7)));
    player2.pieces.add(piece = new Rook("black",player2,board.getSections(7,7)));
    player2.pieces.add(piece = new Pawn("black",player2,board.getSections(0,6)));
    player2.pieces.add(piece = new Pawn("black",player2,board.getSections(1,6)));
    player2.pieces.add(piece = new Pawn("black",player2,board.getSections(2,6)));
    player2.pieces.add(piece = new Pawn("black",player2,board.getSections(3,6)));
    player2.pieces.add(piece = new Pawn("black",player2,board.getSections(4,6)));
    player2.pieces.add(piece = new Pawn("black",player2,board.getSections(5,6)));
    player1.pieces.add(piece = new Pawn("black",player2,board.getSections(6,6)));
    player1.pieces.add(piece = new Pawn("black",player2,board.getSections(7,6)));    
    }
  
   
}
