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
public class Section extends Rectangle
{

    //fields
    private Point id;
    private transient Board board;
    private Piece piece;

    //constructor

    /**
     *
     * @param light
     * @param x
     * @param y
     * @param board
     */
    public Section(boolean light, int x, int y, Board board)
    {
        this.board = board;
        setWidth(board.getTileSize());
        setHeight(board.getTileSize());

        relocate(x * board.getTileSize(), y * board.getTileSize());

        setFill(light ? Color.valueOf("#feb") : Color.valueOf("#582"));

        id = new Point(x, y);

    }

    //methode

    /**
     *
     * @return
     */
    public Point getID()
    {
        return this.id;
    }

    /**
     *
     * @param id
     */
    public void setId(Point id)
    {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public boolean isOccupied()
    {
        return piece != null;
    }

    /**
     *
     * @return
     */
    public Piece getPiece()
    {
        return piece;
    }

    /**
     *
     * @return
     */
    public Board getBoard()
    {
        return board;
    }

    /**
     *
     * @param piece
     */
    public void setPiece(Piece piece)
    {
        if (this.piece != null && piece != null)
        {
            if (this.piece.getColor() != piece.getColor())
            {
                this.piece.getPlayer().removePiece(this.piece);
                piece.setX(getID().x);
                piece.setY(getID().y);
            }
        }
        this.piece = piece;

    }

    /**
     *
     * @param piece
     */
    public void tempSetPiece(Piece piece)
    {
        this.piece = piece;
    }

    /**
     *
     * @param board
     */
    public void setBoard(Board board)
    {
        this.board = board;
    }

    @Override
    public String toString()
    {
        return "Section{" + "Mijn x & y-as in setPiece "+id.x+" "+id.y+ '}';
    }

}
