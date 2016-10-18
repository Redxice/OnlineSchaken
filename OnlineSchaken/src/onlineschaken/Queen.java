/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import static java.lang.Math.abs;
import javafx.scene.image.Image;

/**
 *
 * @author redxice
 */
public class Queen extends Piece
{

    public Queen(String p_color, Player p_player, Section p_section)
    {
        super(p_color, p_player, p_section);
        if (p_color == "white")
        {
            this.img = new Image("ChessPieces/White Queen.png");
        }
        if (p_color == "black")
        {
            this.img = new Image("ChessPieces/Black Queen.png");
        }
    }

    @Override
    public Boolean checkMove(Section p_section)
    {
        if (isValidMove(p_section) == true)
        {
            if (abs(p_section.getID().x - this.section.getID().x) == abs(this.section.getID().y - p_section.getID().y))//p_section.getID().x - this.section.getID().x == p_section.getID().y - this.section.getID().y)
            {
                if (this.section.getID().x > p_section.getID().x)
                {
                    if (this.section.getID().y > p_section.getID().y)
                    {
                        for (int i = 0; i < this.section.getID().y - p_section.getID().y; i++)
                        {
                            if (this.section.getBoard().getSections()[this.section.getID().x - 1][this.section.getID().y - 1].getPiece() != null)
                            {
                                return false;
                            }
                        }
                        return true;
                    }
                    if (this.section.getID().y < p_section.getID().y)
                    {
                        for (int i = 0; i < this.section.getID().y - p_section.getID().y; i++)
                        {
                            if (this.section.getBoard().getSections()[this.section.getID().x + i][this.section.getID().y - i].getPiece() != null)
                            {
                                return false;
                            }
                        }
                        return true;
                    }
                } else if (this.section.getID().x < p_section.getID().x)
                {
                    if (this.section.getID().y > p_section.getID().y)
                    {
                        for (int i = 0; i < this.section.getID().y - p_section.getID().y; i++)
                        {
                            if (this.section.getBoard().getSections()[p_section.getID().x - 1][p_section.getID().y + 1].getPiece() != null)
                            {
                                return false;
                            }
                        }
                        return true;
                    }
                    if (this.section.getID().y < p_section.getID().y)
                    {
                        for (int i = 0; i < this.section.getID().y - p_section.getID().y; i++)
                        {
                            if (this.section.getBoard().getSections()[this.section.getID().x - 1][this.section.getID().y - 1].getPiece() != null)
                            {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            } 
                // Kijkt of de toren over de y as kan bewegen
                else if (this.section.getID().x == p_section.getID().x)
            {

                if (this.section.getID().y < p_section.getID().y)
                {
                    for (int i = 0; i < this.section.getID().y - p_section.getID().y; i++)
                    {
                        if (this.section.getBoard().getSections()[this.section.getID().x][this.section.getID().y + 1].isOccupied())
                        {
                            return false;
                        }
                    }
                    return true;
                } else if (this.section.getID().y > p_section.getID().y)
                {
                    for (int i = 0; i < p_section.getID().y - this.section.getID().y; i++)
                    {
                        if (this.section.getBoard().getSections()[this.section.getID().x][this.section.getID().y + 1].isOccupied())
                        {
                            return false;
                        }
                    }
                    return true;
                }
            // Kijkt of de toren over de x as kan bewegen
            } else if (this.section.getID().y == p_section.getID().y)
            {
                if (this.section.getID().x < p_section.getID().x)
                {
                    for (int i = 0; i < p_section.getID().x - this.section.getID().x; i++)
                    {
                        if (this.section.getBoard().getSections()[this.section.getID().x - 1][this.section.getID().y].isOccupied())
                        {
                            return false;
                        }
                    }
                    return true;
                } else if (this.section.getID().x > p_section.getID().x)
                {
                    for (int i = 0; i < this.section.getID().x - p_section.getID().x; i++)
                    {
                        if (this.section.getBoard().getSections()[this.section.getID().x - 1][this.section.getID().y].isOccupied())
                        {
                            return false;
                        }
                    }
                    return true;
                }
            }
            
            /*    
            } else if (p_section.getID().x == this.section.getID().x || p_section.getID().y == this.section.getID().y)
            {
                if (this.section.getID().x > p_section.getID().x)
                {
                    if (this.section.getID().y > p_section.getID().y)
                    {
                        for (int i = 0; i < this.section.getID().y - p_section.getID().y; i++)
                        {
                            if (this.section.getBoard().getSections()[this.section.getID().x + i][this.section.getID().y + i] != null)
                            {
                                return false;
                            }
                        }
                        return true;
                    }
                    if (this.section.getID().y < p_section.getID().y)
                    {
                        for (int i = 0; i < this.section.getID().y - p_section.getID().y; i++)
                        {
                            if (this.section.getBoard().getSections()[this.section.getID().x + i][this.section.getID().y - i] != null)
                            {
                                return false;
                            }
                        }
                        return true;
                    }
                } else if (this.section.getID().x < p_section.getID().x)
                {
                    if (this.section.getID().y > p_section.getID().y)
                    {
                        for (int i = 0; i < this.section.getID().y - p_section.getID().y; i++)
                        {
                            if (this.section.getBoard().getSections()[this.section.getID().x - i][this.section.getID().y + i] != null)
                            {
                                return false;
                            }
                        }
                        return true;
                    }
                    if (this.section.getID().y < p_section.getID().y)
                    {
                        for (int i = 0; i < this.section.getID().y - p_section.getID().y; i++)
                        {
                            if (this.section.getBoard().getSections()[this.section.getID().x - i][this.section.getID().y - i] != null)
                            {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
            */
        }

        return false;
    }

}
