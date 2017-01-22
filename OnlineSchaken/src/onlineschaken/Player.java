/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author redxice
 */
public class Player implements Serializable
{

    //fields
    private String username;
    private String password;
    private String email;
    private String color;
    private int rating;
    private boolean online;
    private transient List<Player> friends;
    private List<Piece> pieces;
    private transient Game game;
    private transient List<Game> history ;
    private transient List<Game> activeGames ;
    
    //constructor

    /**
     *
     * @param p_username
     * @param p_password
     * @param p_rating
     */
    public Player(String p_username, String p_password, int p_rating)
    {
        this.username = p_username;
        this.password = p_password;
        this.rating = p_rating;
        pieces = new ArrayList<Piece>();
    }
    
    /**
     *
     * @param p_username
     * @param p_password
     * @param email
     * @param p_rating
     */
    public Player(String p_username,String p_password,String email, int p_rating)
    {
        this.username = p_username;
        this.password = p_password;
        this.email = email;
        this.rating = p_rating;
        pieces = new ArrayList<Piece>();
    }
    
    /**
     * constructor for player
     */
    public Player()
    {
        
    }

    /**
     *
     * @return
     */
    public String getUsername()
    {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getPassword()
    {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getColor()
    {
        return color;
    }

    /**
     *
     * @param color
     */
    public void setColor(String color)
    {
        this.color = color;
    }

    /**
     *
     * @return
     */
    public int getRating()
    {
        return rating;
    }

    /**
     *
     * @param rating
     */
    public void setRating(int rating)
    {
        this.rating = rating;
    }

    /**
     *
     * @return
     */
    public boolean isOnline()
    {
        return online;
    }

    /**
     *
     * @param online
     */
    public void setOnline(boolean online)
    {
        this.online = online;
    }

    /**
     *
     * @return
     */
    public Game getGame()
    {
        return game;
    }

    /**
     *
     * @param game
     */
    public void setGame(Game game)
    {
        this.game = game;
    }

    /**
     *
     * @return
     */
    public List<Player> getFriends()
    {
        return friends;
    }

    /**
     *
     * @param friends
     */
    public void setFriends(List<Player> friends)
    {
        this.friends = friends;
    }

    /**
     *
     * @param friend
     */
    public void addFriend(Player friend)
    {
        this.friends.add(friend);
    }

    /**
     *
     * @param friend
     */
    public void removeFriend(Player friend)
    {
        this.friends.remove(friend);
    }

    /**
     *
     * @param game
     */
    public void addHistory(Game game)
    {
        history.add(game);
    }

    /**
     *
     * @return
     */
    public List<Piece> getPieces()
    {
        return pieces;
    }

    /**
     *
     * @param pieces
     */
    public void setPieces(List<Piece> pieces)
    {
        this.pieces = pieces;
    }

    /**
     *
     * @return
     */
    public List<Game> getHistory()
    {
        return history;
    }

    /**
     *
     * @param history
     */
    public void setHistory(List<Game> history)
    {
        this.history = history;
    }

    /**
     *
     * @param piece
     */
    public void removePiece(Piece piece)
    {
        pieces.remove(piece);
    }

    /**
     *
     * @return
     */
    public List<Game> getActiveGames()
    {
        return activeGames;
    }

    /**
     *
     * @param activeGames
     */
    public void setGames(List<Game> activeGames)
    {
       history = new ArrayList<>();
       this.activeGames = new ArrayList<>();
        for (Game game :activeGames)
        {
            if (game.isFinished())
            {
                this.history.add(game);
            }
            else{
               this.activeGames.add(game);
            }
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String toString()
    {
        return "username=" + username + ", rating=" + rating ;
    }
    
    

}
