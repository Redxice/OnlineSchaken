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
import java.util.List;
import java.util.Properties;
import onlineschaken.Player;

/**
 *
 * @author Sander
 */
public class Database
{

    private Properties props;
    private Connection con;

    public Database()
    {
    }

    public void closeConnection()
    {
        try
        {
            con.close();
        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }
    }

    private void initConnection() throws SQLException
    {
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://studmysql01.fhict.local/dbi353331", "dbi353331", "Wachtwoord123");
        } catch (Exception e)
        {
            System.out.println(e.toString());
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
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean insertPlayer(String username, String password, String email)
    {
        try
        {
            PreparedStatement statement = con.prepareStatement("INSERT INTO player(Username, Password, Email) VALUES(?,?,?)");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.executeUpdate();
            return true;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Player selectPlayer(String username)
    {
        try
        {
            PreparedStatement statement = con.prepareStatement("SELECT username, password, email FROM player WHERE username = ?");
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();
            Player player = new Player();
            while (results.next())
            {
                player = new Player(results.getString("username"), results.getString("password"), results.getString("email"));
                System.out.println(player.getUsername() + " + " + player.getPassword());
            }
            return player;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
