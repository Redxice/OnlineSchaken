/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

/**
 *
 * @author redxice
 */
public class Tournament {
    //fields
    String naam;
    Game[] game;
    
    
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    
    public void addGame(Game game){
        
    }
    
   
}
