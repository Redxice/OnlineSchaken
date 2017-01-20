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
            this.setImg(new Image("ChessPieces/White Queen.jpg"));
        }
        else if (p_color == "black")
        {
            this.setImg(new Image("ChessPieces/Black Queen.jpg"));
        }
        this.MyType = "Queen";
    }

    @Override
    public Boolean checkMove(Section p_section)
    {
        if (isValidMove(p_section) == true)
        {
            if (abs(p_section.getID().x - this.getSection().getID().x) == abs(p_section.getID().y - this.getSection().getID().y))
            {
                // kijkt of hij naar links beweegt
                if (this.getSection().getID().x > p_section.getID().x)
                {
                    // kijkt of hij naar boven beweegt
                    if (this.getSection().getID().y > p_section.getID().y)
                    {
                        // kijkt of er een stuk in de weg staat
                        for (int i = 0; i < this.getSection().getID().y - p_section.getID().y - 1; i++)
                        {
                            if (this.getSection().getBoard().getSections()[this.getSection().getID().x - i - 1][this.getSection().getID().y - i - 1].isOccupied() == true)
                            {
                                return false;
                            }
                        }
                        return true;
                    } // kijkt of hij naar beneden beweegt
                    else if (this.getSection().getID().y < p_section.getID().y)
                    {
                        // kijkt of er een stuk in de weg staat
                        for (int i = 0; i < p_section.getID().y - this.getSection().getID().y - 1; i++)
                        {
                            if (this.getSection().getBoard().getSections()[this.getSection().getID().x - i - 1][this.getSection().getID().y + i + 1].isOccupied() == true)
                            {
                                return false;
                            }
                        }
                        return true;
                    }
                } // kijkt of hij naar rechts beweegt
                else if (this.getSection().getID().x < p_section.getID().x)
                {
                    // kijkt of hij naar boven beweegt
                    if (this.getSection().getID().y > p_section.getID().y)
                    {
                        // kijkt of er een stuk in de weg staat
                        for (int i = 0; i < this.getSection().getID().y - p_section.getID().y - 1; i++)
                        {
                            if (this.getSection().getBoard().getSections()[this.getSection().getID().x + i + 1][this.getSection().getID().y - i - 1].isOccupied() == true)
                            {
                                return false;
                            }
                        }
                        return true;
                    } // kijkt of hij naar beneden beweegt
                    else if (this.getSection().getID().y < p_section.getID().y)
                    {
                        // kijkt of er een stuk in de weg staat
                        for (int i = 0; i < p_section.getID().y - this.getSection().getID().y - 1; i++)
                        {
                            if (this.getSection().getBoard().getSections()[this.getSection().getID().x + i + 1][this.getSection().getID().y + i + 1].isOccupied() == true)
                            {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            } // Kijkt of de toren over de x as kan bewegen
            else if (this.getSection().getID().y == p_section.getID().y)
            {
                if (this.getSection().getID().x < p_section.getID().x)
                {
                    for (int i = 0; i < p_section.getID().x - this.getSection().getID().x - 1; i++)
                    {
                        if (this.getSection().getBoard().getSections()[this.getSection().getID().x + i + 1][this.getSection().getID().y].isOccupied() == true)
                        {
                            return false;
                        }
                    }
                    return true;
                } else if (this.getSection().getID().x > p_section.getID().x)
                {
                    for (int i = 0; i < this.getSection().getID().x - p_section.getID().x - 1; i++)
                    {
                        if (this.getSection().getBoard().getSections()[this.getSection().getID().x - i - 1][this.getSection().getID().y].isOccupied() == true)
                        {
                            return false;
                        }
                    }
                    return true;
                }
            } // Kijkt of de toren over de y as kan bewegen
            else if (this.getSection().getID().x == p_section.getID().x)
            {
                if (this.getSection().getID().y > p_section.getID().y)
                {
                    for (int i = 0; i < this.getSection().getID().y - p_section.getID().y - 1; i++)
                    {
                        if (this.getSection().getBoard().getSections()[this.getSection().getID().x][this.getSection().getID().y - i - 1].isOccupied() == true)
                        {
                            return false;
                        }
                    }
                    return true;
                } else if (this.getSection().getID().y < p_section.getID().y)
                {
                    for (int i = 0; i < p_section.getID().y - this.getSection().getID().y - 1; i++)
                    {
                        if (this.getSection().getBoard().getSections()[this.getSection().getID().x][this.getSection().getID().y + i + 1].isOccupied() == true)
                        {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
