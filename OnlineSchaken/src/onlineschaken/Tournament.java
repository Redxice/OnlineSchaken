/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.util.List;

/**
 *
 * @author redxice
 */
public class Tournament {
    //fields
    private String name;
    private List<Game> games;
    
    
    //constructor
    public Tournament(String p_name,List<Game> games)
    {
        this.name = p_name;
        this.games = games;
    }

    public String getNaam() {
        return name;
    }

    public void setNaam(String name) {
        this.name = name;
    }

    public List<Game> getGame() {
        return this.games;
    }

    public void addGame(Game game){
        
    }
    
   
}
