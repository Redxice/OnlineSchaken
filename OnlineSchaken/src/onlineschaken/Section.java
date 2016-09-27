/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.awt.Point;

/**
 *
 * @author redxice
 */
public class Section {
    //fields
    Point id;
    boolean occupied;
    Piece piece;
    Board board;
    
    //constructor
    public Section(Point p_id,Board p_board)
    {
        
    }
    
    //methode

    public Point getId() {
        return id;
    }

    public void setId(Point id) {
        this.id = id;
    }

    public boolean isOccupied() {
        return occupied;
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
