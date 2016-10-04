/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sander
 */
public class Database {
    
    private Properties props;
    private Connection con;
    
    public Database()
    {
    }
    
    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    
    private void initConnection() throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://studmysql01.fhict.local/dbi353331", "dbi353331" , "Wachtwoord123");
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public boolean init() {
        try {
            initConnection();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
    
    
    
}
