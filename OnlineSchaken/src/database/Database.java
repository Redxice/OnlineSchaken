/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import onlineschaken.Gamelobby;
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

    private void initConnection() throws SQLException
    {
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://studmysql01.fhict.local/dbi353331", "dbi353331", "Wachtwoord123");
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean init()
    {
        try
        {
            initConnection();
            return true;
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean insertPlayer(String username, String password, String email)
    {
        try
        {
            init();
            PreparedStatement statement = con.prepareStatement("INSERT INTO player(Username, Password, Email) VALUES(?,?,?);");
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
        }
        catch(Exception e)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        finally
        {
            closeConnection();
        }
    }

    /**
     * //Dit moet nog worden gechecked worden in de methode die hem aanroept.
     *
     * @param username
     * @return Player
     */
    public Player selectPlayer(String username)
    {
        try
        {
            init();
            PreparedStatement statement = con.prepareStatement("SELECT username, password, email FROM player WHERE username = ?;");
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();
            Player player = new Player();
            while (results.next())
            {
                player = new Player(results.getString("username"), results.getString("password"), results.getString("email"));
                LOGGER.log(Level.FINE, player.getUsername() + " + " + player.getPassword());
            }
            statement.close();
            return player;
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        }
        finally
        {
            closeConnection();
        }
    }

    public boolean insertLobby(String username)
    {
        try
        {            
            int playerid = selectPlayerId(username);
            init();
            PreparedStatement statement = con.prepareStatement("INSERT INTO lobby(player1ID) VALUES(?);");
            statement.setInt(1, playerid);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally
        {
            closeConnection();
        }
    }

    public boolean joinLobby(String username, int lobbyID)
    {
        try
        {
            init();
            int playerid = selectPlayerId(username);
            PreparedStatement statement = con.prepareStatement("INSERT INTO lobby(player2ID) VALUES(?) WHERE lobbyID = ?;");
            statement.setInt(1, playerid);
            statement.setInt(2, lobbyID);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally
        {
            closeConnection();
        }
    }

    public boolean addFriend(String username1, String username2)
    {
        try
        {
            init();
            int playerid1 = selectPlayerId(username1);
            int playerid2 = selectPlayerId(username2);
            PreparedStatement statement = con.prepareStatement("INSERT INTO friendlist(playerID, friendID) VALUES(?, ?);");
            statement.setInt(1, playerid1);
            statement.setInt(2, playerid2);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally
        {
            closeConnection();
        }
    }
    
    public boolean addLobbyMessage(int lobbyid, String username)
    {
       try
        {
            init();
            int playerid = selectPlayerId(username);
            PreparedStatement statement = con.prepareStatement("INSERT INTO lobbymessage(playerID, friendID) VALUES(?, ?);");
            statement.setInt(1, playerid);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        finally
        {
            closeConnection();
        }
    }

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
        }
        finally
        {
            closeConnection();
        }
    }
    
    public ObservableList<Gamelobby>  selectAllGameLobbys()
    {
        try
        {
            init();
            PreparedStatement statement = con.prepareStatement("SELECT LobbyID, player1ID, player2ID FROM lobby;");
            ResultSet results = statement.executeQuery();
            ObservableList<Gamelobby> items =FXCollections.observableArrayList();
            while (results.next())
            {
                int count = 1;
                if(results.getString("player2ID") != null)
                {
                    count = 2;
                }
                Gamelobby lobby = new Gamelobby(results.getInt("LobbyID"),count);
                items.add(lobby);
            }
            statement.close();
            return items;
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        }
        finally
        {
            closeConnection();
        }
    }
}
