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
public class Player
{

    //fields
    private String username;
    private String password;
    private String email;
    private String color;
    private int rating;
    private boolean online;
    private List<Player> friends;
    private List<Piece> pieces;
    private Game game;
    private List<Game> history;

    //constructor
    public Player(String p_username, String p_password, int p_rating)
    {
        this.username = p_username;
        this.password = p_password;
        this.rating = p_rating;
        pieces = new ArrayList<Piece>();
    }
    
    public Player(String p_username,String p_password,String email, int p_rating)
    {
        this.username = p_username;
        this.password = p_password;
        this.email = email;
        this.rating = p_rating;
        pieces = new ArrayList<Piece>();
    }
    
    public Player()
    {
        
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public int getRating()
    {
        return rating;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    public boolean isOnline()
    {
        return online;
    }

    public void setOnline(boolean online)
    {
        this.online = online;
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    public List<Player> getFriends()
    {
        return friends;
    }

    public void setFriends(List<Player> friends)
    {
        this.friends = friends;
    }

    public void addFriend(Player friend)
    {
        this.friends.add(friend);
    }

    public void removeFriend(Player friend)
    {
        this.friends.remove(friend);
    }

    public void addHistory(Game game)
    {
        history.add(game);
    }

    public List<Piece> getPieces()
    {
        return pieces;
    }

    public void setPieces(List<Piece> pieces)
    {
        this.pieces = pieces;
    }

    public List<Game> getHistory()
    {
        return history;
    }

    public void setHistory(List<Game> history)
    {
        this.history = history;
    }

    public void removePiece(Piece piece)
    {
        pieces.remove(piece);
    }

}
