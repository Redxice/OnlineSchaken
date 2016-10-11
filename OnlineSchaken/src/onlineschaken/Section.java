/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.awt.Point;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author redxice
 */
public class Section extends Rectangle{
    //fields
    Point id;
    Board board;
   private Piece piece;

   
    
    //constructor
    // 
    //
    public Section(boolean light, int x, int y,Board board)
    {
        this.board = board;
        setWidth(board.getTILE_SIZE());
        setHeight(board.getTILE_SIZE());

        relocate(x * board.getTILE_SIZE(), y * board.getTILE_SIZE());
        
        setFill(light ? Color.valueOf("#feb") : Color.valueOf("#582"));
                
        id = new Point(x,y);
       
    }
    
    //methode
   public Point getID(){
       return this.id;
   }
    public void setId(Point id) {
        this.id = id;
    }

    public boolean isOccupied() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }
    public Board getBoard() {
        return board;
    }
   public void setPiece(Piece piece){
        this.piece = piece;
   }
    public void setBoard(Board board) {
        this.board = board;
    }
   
    
}
