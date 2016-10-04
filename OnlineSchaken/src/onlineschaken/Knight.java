/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import javafx.scene.image.Image;

/**
 *
 * @author redxice
 */
public class Knight extends Piece{
    
    public Knight(String p_color, Player p_player, Section p_section) {
        super(p_color, p_player,p_section);
        if(p_color == "white")
        {
        this.img = new Image("ChessPieces/White Knight.png");
        }
        if(p_color == "black")
        {
        this.img = new Image("ChessPieces/Black Knight.png");
        }
    }

    @Override
    public void move(Section p_section) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
