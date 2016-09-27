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
public class King extends Piece {
    boolean check;
    boolean checkMate;
    boolean hasMoved;

    public King(String p_color, Player p_player, String p_name) {
        super(p_color, p_player, p_name);
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
        
    }
   
}
