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
     * moet nog worden geimplementeerd
     */
    public Database()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * sluit de connectie met de database als dat niet lukt wordt 
     * er een sql injection gethrowed.
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
            con = DriverManager.getConnection("jdbc:mysql://studmysql01.fhict.local/dbi353331", "dbi353331", "Wachtwoord123;");
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
            PreparedStatement statement = con.prepareStatement("SELECT username, password, email FROM player WHERE username = ?;");
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();
            Player player = new Player();
            while (results.next())
            {
                player = new Player(results.getString("username"), results.getString("password"), results.getString("email"));
                LOGGER.log(Level.FINE,player.getUsername() + " + " + player.getPassword());
            }
            statement.close();
            return player;
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        }
    }
}
