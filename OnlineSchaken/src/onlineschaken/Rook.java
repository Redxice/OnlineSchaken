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
public class Rook extends Piece{
    boolean hasMoved;
    public Rook(String p_color, Player p_player, String p_name) {
        super(p_color, p_player, p_name);
        if(p_color == "white")
        {
        this.img = new Image("ChessPieces/White Rook.png");
        }
        if(p_color == "black")
        {
        this.img = new Image("ChessPieces/Black Rook.png");
        }
    }
    public Section castling()
    {
      Section section = new Section(true,1,1);
      return  section;
    }

    @Override
    public void move(Section p_section) {
        
    }
}
