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
public class Bishop extends Piece{
    
    public Bishop(String p_color, Player p_player, Section p_section) {
        super(p_color, p_player, p_section);
        if(p_color == "white")
        {
        this.img = new Image("ChessPieces/White Bishop.png");
        }   
        if(p_color == "black")
        {
        this.img = new Image("ChessPieces/Black Bishop.png");
        }
         
    }

    @Override
    public Boolean checkMove(Section p_section) {
       
    }
    
}
