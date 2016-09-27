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
public class Pawn extends Piece {
    boolean hasMoved;

    public Pawn(String p_color, Player p_player, String p_name) {
        super(p_color, p_player, p_name);
    }
    
    public void promotion()
    {}
    
    public boolean getFirsMove(){
        return false;
    }
}
