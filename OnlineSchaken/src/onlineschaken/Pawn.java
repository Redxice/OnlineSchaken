/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 *
 * @author redxice
 */
public class Pawn extends Piece{
    boolean hasMoved;

    public Pawn(String p_color, Player p_player, Section p_section) {
        super(p_color, p_player, p_section);
        if (p_color == "white")
        {
        this.img = new Image("ChessPieces/White Pawn.png");
        }
        else if (p_color == "black")
        {
        this.img = new Image("ChessPieces/Black Pawn.png");
        }
        
    }
    
    public void promotion()
    {}
    
    public boolean getFirsMove(){
        return false;
    }

    @Override
    public void move(Section p_section) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
