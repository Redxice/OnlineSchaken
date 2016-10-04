/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.awt.Point;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author redxice
 */
public class Section extends Rectangle{
    //fields
    Point id;
    boolean occupied;
    Board board;
    Piece piece;
    
    //constructor
    // 
    //
    public Section(boolean light, int x, int y)
    {
        setWidth(OnlineSchaken.TILE_SIZE);
        setHeight(OnlineSchaken.TILE_SIZE);

        relocate(x * OnlineSchaken.TILE_SIZE, y * OnlineSchaken.TILE_SIZE);
        
        setFill(light ? Color.valueOf("#feb") : Color.valueOf("#582"));
        if(x == 0 & y == 0)
        { setFill(Color.BLUE);}
        if(y == 1 & x == 2)
                {
                Player p = new Player("hoi","doi",5);
                Piece pawn = new Pawn("white",p,"k");                
                ImagePattern ip = new ImagePattern(pawn.img);
                this.setFill(ip);
                }
        id = new Point(x,y);
    }
    
    //methode

    public void setId(Point id) {
        this.id = id;
    }

    public boolean isOccupied() {
        return true;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
   
    
}
