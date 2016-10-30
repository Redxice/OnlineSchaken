/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author redxice
 */
public class Player {
    //fields
    String username;
    String password;
    String color;
    int rating;
    boolean online;
    List<Player> friends;
    List<Piece> pieces;
    Game game;
    List<Game> history;
    
    //constructor
    public Player(String p_username,String p_password,int p_rating)
    {
        this.username = p_username;
        this.password = p_password;
        this.rating = p_rating;
        pieces = new ArrayList<Piece>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

   
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Player> getFriends() {
        return friends;
    }

    public void setFriends(List<Player> friends) {
        this.friends = friends;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public List<Game> getHistory() {
        return history;
    }

    public void setHistory(List<Game> history) {
        this.history = history;
    }
    
    public void removePiece(Piece piece)
    {
     pieces.remove(piece);
    }
    
}
