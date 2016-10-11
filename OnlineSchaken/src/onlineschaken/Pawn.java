/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 *
 * @author redxice
 */
public class Pawn extends Piece
{

    boolean hasMoved;

    public Pawn(String p_color, Player p_player, Section p_section)
    {
        super(p_color, p_player, p_section);
        if (p_color == "white")
        {
            this.img = new Image("ChessPieces/White Pawn.png");
        } else if (p_color == "black")
        {
            this.img = new Image("ChessPieces/Black Pawn.png");
        }
    this.hasMoved = false;
    }

    public void promotion()
    {
    }

    public boolean getFirsMove()
    {
        return this.hasMoved;
    }

    @Override
    public Boolean checkMove(Section p_section)
    {

        Board board = p_section.getBoard();
        if (isValidMove(p_section) == false)
        {
            return false;
        } else if (hasMoved == false)
        {
            if (this.color == "black")
            {
                //1 section naar voren.
                if (p_section.getID().x == this.section.getID().x && p_section.getID().y == this.section.getID().y - 1)
                {
                    if (board.getSections(this.section.getID().x, this.section.getID().y - 1).isOccupied())
                    {
                        return false;

                    } else
                    {
                        hasMoved = true;
                        return true;

                    }
                } //schuin slaan van andere pawn.
                else if (p_section.getID().x == this.section.getID().x + 1 && p_section.getID().y == this.section.getID().y - 1
                        || p_section.getID().x == this.section.getID().x - 1 && p_section.getID().y == this.section.getID().y - 1)
                {
                    if (p_section.getID().x == this.section.getID().x + 1 && p_section.getID().y == this.section.getID().y - 1)
                    {
                        if (isValidMove(board.getSections(this.section.getID().x + 1, this.section.getID().y - 1)))
                        {
                            hasMoved = true;
                        }
                        return true;
                    } else
                    {
                        if (isValidMove(board.getSections(this.section.getID().x - 1, this.section.getID().y - 1)))
                        {
                            hasMoved = true;
                        }
                        return true;
                    }

                } //2 section naar voren
                else
                {
                    for (int i = 1; i < 3; i++)
                    {
                        int x = this.section.getID().x ;
                        int y = this.section.getID().y - i;
                        Section section = board.getSections(x, y);
                        if (section.isOccupied() == true)
                        {
                            return false;
                        }
                    }
                    hasMoved = true;
                    return true;
                }

            }
            if (this.color == "white")
            {
                //1 section naar voren.
                if (p_section.getID().x == this.section.getID().x && p_section.getID().y == this.section.getID().y + 1)
                {
                    if (board.getSections(this.section.getID().x, this.section.getID().y + 1).isOccupied())
                    {
                        return false;
                    } else
                    {
                        hasMoved = true;
                        return true;
                    }
                } //schuin slaan van andere pawn.
                else if (p_section.getID().x == this.section.getID().x + 1 && p_section.getID().y == this.section.getID().y + 1
                        || p_section.getID().x == this.section.getID().x - 1 && p_section.getID().y == this.section.getID().y + 1)
                {
                    if (p_section.getID().x == this.section.getID().x + 1 && p_section.getID().y == this.section.getID().y + 1)
                    {
                        if (isValidMove(board.getSections(this.section.getID().x + 1, this.section.getID().y + 1)))
                        {
                            hasMoved = true;
                        }
                        return true;
                    } else
                    {
                        if (isValidMove(board.getSections(this.section.getID().x - 1, this.section.getID().y + 1)))
                        {
                            hasMoved = true;
                        }
                        return true;
                    }

                } //2 section naar voren
                else
                {
                    for (int i = 1; i < 3; i++)
                    {
                        int x = this.section.getID().x ;
                        int y = this.section.getID().y + i;
                        Section section = board.getSections(x, y);
                        if (section.isOccupied() == true)
                        {
                            return false;
                        }
                    }
                }

            }
        } else if (hasMoved == true)
        {

            if (this.color == "black")
            {
                //1 section naar voren.
                if (p_section.getID().x == this.section.getID().x && p_section.getID().y == this.section.getID().y - 1)
                {
                    if (board.getSections(this.section.getID().x, this.section.getID().y - 1).isOccupied())
                    {
                        return false;

                    } else
                    {

                        return true;

                    }
                } //schuin slaan van andere pawn.
                else if (p_section.getID().x == this.section.getID().x + 1 && p_section.getID().y == this.section.getID().y - 1
                        || p_section.getID().x == this.section.getID().x - 1 && p_section.getID().y == this.section.getID().y - 1)
                {
                    if (p_section.getID().x == this.section.getID().x + 1 && p_section.getID().y == this.section.getID().y - 1)
                    {if(board.getSections(this.section.getID().x + 1, this.section.getID().y - 1).isOccupied()){
                        if (isValidMove(board.getSections(this.section.getID().x + 1, this.section.getID().y - 1)))

                        {
                            return true;
                        }
                    }
                    } else if (board.getSections(this.section.getID().x - 1, this.section.getID().y - 1).isOccupied())

                    {
                        if(isValidMove(board.getSections(this.section.getID().x - 1, this.section.getID().y - 1))){
                        return true;
                        }
                    }
                    return false;
                }

            } else if (this.color == "white")
            {
                //1 section naar voren.
                if (p_section.getID().x == this.section.getID().x && p_section.getID().y == this.section.getID().y + 1)
                {
                    if (board.getSections(this.section.getID().x, this.section.getID().y + 1).isOccupied())
                    {
                        return false;
                    } else
                    {
                        return true;
                    }
                } //schuin slaan van andere pawn.
                else if (p_section.getID().x == this.section.getID().x + 1 && p_section.getID().y == this.section.getID().y + 1
                        || p_section.getID().x == this.section.getID().x - 1 && p_section.getID().y == this.section.getID().y + 1)
                {
                    if (p_section.getID().x == this.section.getID().x + 1 && p_section.getID().y == this.section.getID().y - 1)
                    {if(board.getSections(this.section.getID().x + 1, this.section.getID().y + 1).isOccupied()){
                        if (isValidMove(board.getSections(this.section.getID().x + 1, this.section.getID().y + 1)))

                        {
                            return true;
                        }
                    }
                    } else if (board.getSections(this.section.getID().x - 1, this.section.getID().y + 1).isOccupied())

                    {
                        if(isValidMove(board.getSections(this.section.getID().x - 1, this.section.getID().y + 1))){
                        return true;
                        }
                    }
                    return false;
                }

            }
        }
        return false;
    }
}
