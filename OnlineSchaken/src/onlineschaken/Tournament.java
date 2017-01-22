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
public class Tournament
{

    //fields
    private String name;
    private List<Game> games;

    //constructor

    /**
     *
     * @param p_name
     * @param games
     */
    public Tournament(String p_name, List<Game> games)
    {
        this.name = p_name;
        this.games = games;
    }

    /**
     *
     * @return
     */
    public String getNaam()
    {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setNaam(String name)
    {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public List<Game> getGame()
    {
        return this.games;
    }

    /**
     *
     * @param game
     */
    public void addGame(Game game)
    {

    }

}
