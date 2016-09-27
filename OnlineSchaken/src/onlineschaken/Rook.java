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
public class Rook extends Piece{
    boolean hasMoved;
    public Rook(String p_color, Player p_player, String p_name) {
        super(p_color, p_player, p_name);
    }
    public Section castling()
    {
      Section section = new Section(true,1,1);
      return  section;
    }
}
