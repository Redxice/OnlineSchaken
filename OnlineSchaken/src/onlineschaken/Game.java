/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.sql.Time;

/**
 *
 * @author redxice
 */
public class Game {
    //fields
    Time time;
    Time resterend1;
    Time resterend2;
    boolean finished;
    Tournament tournament;
    Player player1;
    Player player2;
    Player[] spectators;
    Player winner;
    boolean whiteTurn;
    Chatline[] chat;
    Board board;
    Gamelobby gamelobby;
    //constructor voor game die geen deel uitmaakt van een tournament
    public Game(Time p_time,Player p_player1,Player p_player2){
        
        this.player1 = p_player1;
        this.player2 = p_player2;
        this.time = p_time;
        
        
    }
    //constructor vor een game die deel is van een tournament
    public Game(Time p_time,Player p_player1,Player p_player2,
            Tournament p_tournament){
        
        this.player1 = p_player1;
        this.player2 = p_player2;
        this.time = p_time;
        this.tournament = p_tournament;
        
    }

    //getters and setters
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Time getResterend1() {
        return resterend1;
    }

    public void setResterend1(Time resterend1) {
        this.resterend1 = resterend1;
    }

    public Time getResterend2() {
        return resterend2;
    }

    public void setResterend2(Time resterend2) {
        this.resterend2 = resterend2;
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

    public Player[] getSpectators() {
        return spectators;
    }

    public void setSpectators(Player[] spectators) {
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

    public Chatline[] getChat() {
        return chat;
    }

    public void setChat(Chatline[] chat) {
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
       
   }
   //verwijdert de player uit de spectators lijst.
   public void removeSpectator(Player p_spectator){
       
   }
   
   //voegt chatline toe aan de lijst Chat.
   public void addChatline(Chatline p_chatline){
      
   }
   
   
   public boolean checkMate(){
      return false;
   }
  
   
}
