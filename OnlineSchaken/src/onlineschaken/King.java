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
public class King extends Piece {
    boolean check;
    boolean checkMate;

    public King(String p_color, Player p_player,Section p_section) {
        super(p_color, p_player, p_section);
        if(p_color == "white")
        {
        this.img = new Image("ChessPieces/White King.png");
        }
        if(p_color == "black")
        {
        this.img = new Image("ChessPieces/Black King.png");
        }
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isCheckMate() {
        return checkMate;
    }

    public void setCheckMate(boolean checkMate) {
        this.checkMate = checkMate;
    }
    
    public Section castling(){
       Section section = null;
      return  section;
    }

    @Override
    public Boolean checkMove(Section p_section) {
        if(p_section.getID().x != section.getID().x && p_section.getID().y != section.getID().y)
        {
            if(isValidMove(p_section) == false)
            {
               return false; 
            }
            else if(p_section.getID().x + 1 > section.getID().x)
            {
                return false;
            }
            else if(p_section.getID().x - 1 < section.getID().x)
            {
                return false;
            }
            else if(p_section.getID().y + 1 > section.getID().y)
            {
                return false;
            }
            else if(p_section.getID().y - 1 < section.getID().y)
            {
                return false;
            }
            else if(section.getBoard().getSections()[section.getID().x][p_section.getID().y].getPiece() == null)
            {
                return false;
            }
            this.section = p_section;
        }
        return true;
    }
}
