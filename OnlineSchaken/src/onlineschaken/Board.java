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
public class Board {
    Section[] sections;
    Game game;

    public Board(Section[] p_sections, Game p_game) {
        this.sections = p_sections;
        this.game = p_game;
    }

    public Section[] getSections() {
        return sections;
    }

    public void setSections(Section[] sections) {
        this.sections = sections;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    
}
