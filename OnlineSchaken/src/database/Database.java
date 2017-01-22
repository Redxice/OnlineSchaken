/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import onlineschaken.Game;
import onlineschaken.Piece;
import onlineschaken.Player;

/**
 *
 * @author Sander
 */
public class Database
{

    private Connection con;
    private static final Logger LOGGER = Logger.getLogger(Piece.class.getName());

    /**
     * sluit de connectie met de database als dat niet lukt wordt er een sql
     * injection gethrowed.
     */
    public void closeConnection()
    {
        try
        {
            con.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

    private boolean initConnection()
    {
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proftaaktest", "TestUser", "Test");
            return true;
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     *
     * @return
     */
    public boolean init()
    {
        return initConnection();
    }

    /**
     *
     * @param username
     * @param password
     * @param email
     * @return
     */
    public boolean insertPlayer(String username, String password, String email)
    {
        if (!username.equals("")&&!password.equals(""))
        {
        try
        {
            init();
            PreparedStatement statement = con.prepareStatement("INSERT INTO player(Username, Password, Email, Rating) VALUES(?,?,?,1000);");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception e)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally
        {
            closeConnection();
        }
        }
        return false;
    }

    /**
     *
     * @param username
     * @return
     */
    public boolean removePlayer(String username)
    {
        if (!username.equals(""))
        {
        try
        {
            init();
            PreparedStatement statement = con.prepareStatement("delete from player Where Username=?;");
            statement.setString(1, username);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception e)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally
        {
            closeConnection();
        }
        }
        return false;
    }

    /**
     * //Dit moet nog worden gechecked worden in de methode die hem aanroept.
     *
     * @param username
     * @return Player
     */
    public Player selectPlayer(String username)
    {
        if (username.equals(""))
        {
            return null;
        }
        try
        {
            init();
            PreparedStatement statement = con.prepareStatement("SELECT username, password, email, rating FROM player WHERE username = ?;");
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();
            Player player = null;
            while (results.next())
            {
                String name = results.getString("username");
                player = new Player(results.getString("username"), results.getString("password"), results.getString("email"), results.getInt("rating"));
                LOGGER.log(Level.FINE, player.getUsername() + " + " + player.getPassword());
            }
            statement.close();
            return player;
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        } finally
        {
            closeConnection();
        }
    }

/**
 * 
 * @param username1
 * @param username2
 * @return 
 */
    public boolean addFriend(String username1, String username2)
    {
        try
        {
            init();
            int playerid1 = selectPlayerId(username1);
            int playerid2 = selectPlayerId(username2);
            PreparedStatement statement = con.prepareStatement("INSERT INTO friendlist(username1, username2) VALUES(?, ?);");
            statement.setString(1, username1);
            statement.setString(2, username2);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally
        {
            closeConnection();
        }
    }

    /**
     *
     * @param username
     * @return
     */
    public int selectPlayerId(String username)
    {
        try
        {
            init();
            PreparedStatement statement = con.prepareStatement("SELECT PlayerID FROM player WHERE username = ?;");
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();
            int id = 0;
            while (results.next())
            {
                id = results.getInt("PlayerID");
            }
            statement.close();
            return id;
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally
        {
            closeConnection();
        }
    }

    /**
     *
     * @param userName
     * @param userName2
     * @param move
     * @param nr
     * @return
     */
    public boolean addMoveToHistory(String userName, String userName2, String move, int nr)
    {
        try
        {
            init();
            PreparedStatement statement = con.prepareStatement("INSERT INTO movehistory(Player1, Player2,Move,MoveNr) VALUES(?,?,?,?);");
            statement.setString(1, userName);
            statement.setString(2, userName2);
            statement.setString(3, move);
            statement.setInt(4, nr);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally
        {
            closeConnection();
        }
    }

    /**
     *
     * @param game
     */
    public void SaveGame(Game game)
    {
        try
        {
            init();
            PreparedStatement statement = con.prepareStatement("INSERT INTO games(Game,Player1,Player2) VALUES(?,?,?);");
            System.out.println("game :" + game + " Player1 : " + game.getPlayer1().getUsername() + " Player2 : " + game.getPlayer2().getUsername());
            statement.setObject(1, game);
            statement.setString(2, game.getPlayer1().getUsername());
            statement.setString(3, game.getPlayer2().getUsername());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            closeConnection();
        }
    }

    /**
     *
     * @param Username
     * @return
     */
    public ArrayList<Game> GetUsersGames(String Username)
    {
        ArrayList<Game> games = new ArrayList<>();
        Game game = null;
        try
        {
            init();
            PreparedStatement statement = con.prepareStatement("Select Game,GameNr From Games where Player1=? or Player2=?;");
            statement.setString(1, Username);
            statement.setString(2, Username);
            ResultSet results = statement.executeQuery();
            while (results.next())
            {
                ByteArrayInputStream in = new ByteArrayInputStream(results.getBytes("Game"));
                ObjectInputStream is = new ObjectInputStream(in);
                Object test = is.readObject();
                if (test instanceof Game)
                {
                    game = (Game) test;
                    game.setGameNumber(results.getInt("GameNr"));
                    games.add(game);
                }
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            closeConnection();
        }
        return games;
    }

}
