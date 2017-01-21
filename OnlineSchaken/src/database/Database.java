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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import onlineschaken.Game;
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proftaaktest", "TestUser", "Test");
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
            PreparedStatement statement = con.prepareStatement("SELECT username, password, email, rating FROM player WHERE username = ?;");
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();
            Player player = new Player();
            while (results.next())
            {
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

    public boolean insertLobby(String username, String gameName)
    {
        try
        {
            int playerid = selectPlayerId(username);
            init();
            PreparedStatement statement = con.prepareStatement("INSERT INTO lobby(player1ID,LobbyNaam) VALUES(?,?);");
            statement.setInt(1, playerid);
            statement.setString(2, gameName);
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
        } finally
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
        } finally
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
        } finally
        {
            closeConnection();
        }
    }

    public ObservableList<Gamelobby> selectAllGameLobbys() throws RemoteException
    {
        try
        {
            init();
            PreparedStatement statement = con.prepareStatement("SELECT LobbyID, player1ID, player2ID, LobbyNaam FROM lobby;");
            ResultSet results = statement.executeQuery();
            ObservableList<Gamelobby> items = FXCollections.observableArrayList();
            while (results.next())
            {
                if (results.getString("player2ID") != null)
                {
                    Player player1 = selectPlayerFromID(results.getInt("player1ID"));
                    Player player2 = selectPlayerFromID(results.getInt("player2ID"));
                    Gamelobby lobby = new Gamelobby(results.getString("LobbyNaam"), player1, player2, results.getInt("LobbyID"));
                    items.add(lobby);
                } else
                {
                    Player player1 = selectPlayerFromID(results.getInt("player1ID"));
                    Gamelobby lobby = new Gamelobby(results.getString("LobbyNaam"), player1, results.getInt("LobbyID"));
                    items.add(lobby);
                }
            }
            statement.close();
            return items;
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        } finally
        {
            closeConnection();
        }
    }

    public Player selectPlayerFromID(int id)
    {
        try
        {
            init();
            PreparedStatement statement = con.prepareStatement("SELECT username, password, email, rating FROM player WHERE PlayerID = ?;");
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            Player player = new Player();
            while (results.next())
            {
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

    public void addMoveToHistory(String userName, String userName2, String move, int nr)
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
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            closeConnection();
        }
    }

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
                Object test = is.readObject() ;
                if (test instanceof Game)
                {  
                    game = (Game) test;
                    game.setGameNr(results.getInt("GameNr"));
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
