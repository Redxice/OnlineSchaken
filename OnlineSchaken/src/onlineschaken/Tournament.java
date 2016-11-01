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
    private String naam;
    private List<Game> games;
    
    
    //constructor
    public Tournament(String p_naam,Game game)
    {
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public List<Game> getGame() {
        return this.games;
    }

    public void addGame(Game game){
        
    }
    
   
}
