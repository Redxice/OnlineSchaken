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
    boolean occupied;
    Piece piece;
    Board board;
    
    //constructor
    public Section(boolean light, int x, int y)
    {
        setWidth(OnlineSchaken.TILE_SIZE);
        setHeight(OnlineSchaken.TILE_SIZE);

        relocate(x * OnlineSchaken.TILE_SIZE, y * OnlineSchaken.TILE_SIZE);

        setFill(light ? Color.valueOf("#feb") : Color.valueOf("#582"));
    }
    
    //methode



    public void setId(Point id) {
        this.id = id;
    }

    public boolean isOccupied() {
        return piece != null;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
   
    
}
