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
public class Bishop extends Piece
{

    public Bishop(String p_color, Player p_player, Section p_section)
    {
        super(p_color, p_player, p_section);
        if (p_color == "white")
        {
            this.img = new Image("ChessPieces/White Bishop.jpg");
        }
        if (p_color == "black")
        {
            this.img = new Image("ChessPieces/Black Bishop.jpg");
        }

    }

    @Override
    public Boolean checkMove(Section p_section)
    {
        // kijkt of er een stuk op de gekoze positie staat
        if (isValidMove(p_section) == true)
        {
            // kijkt of het een move is die een loper mag doen
            if (abs(p_section.getID().x - this.section.getID().x) == abs(p_section.getID().y - this.section.getID().y))
            {
                // kijkt of hij naar links beweegt
                if (this.section.getID().x > p_section.getID().x)
                {
                    // kijkt of hij naar boven beweegt
                    if (this.section.getID().y > p_section.getID().y)
                    {
                        // kijkt of er een stuk in de weg staat
                        for (int i = 0; i < this.section.getID().y - p_section.getID().y -1; i++)
                        {
                            if (this.section.getBoard().getSections()[this.section.getID().x - i -1][this.section.getID().y - i -1].isOccupied() == true)
                            {
                                return false;
                            }
                        }
                        return true;
                    }
                    // kijkt of hij naar beneden beweegt
                    else if (this.section.getID().y < p_section.getID().y)
                    {
                        // kijkt of er een stuk in de weg staat
                        for (int i = 0; i < p_section.getID().y - this.section.getID().y -1; i++)
                        {
                            if (this.section.getBoard().getSections()[this.section.getID().x - i -1][this.section.getID().y + i +1].isOccupied() == true)
                            {
                                return false;
                            }
                        }
                        return true;
                    }
                } 
                // kijkt of hij naar rechts beweegt
                else if (this.section.getID().x < p_section.getID().x)
                {
                    // kijkt of hij naar boven beweegt
                    if (this.section.getID().y > p_section.getID().y)
                    {
                        // kijkt of er een stuk in de weg staat
                        for (int i = 0; i < this.section.getID().y - p_section.getID().y -1; i++)
                        {
                            if (this.section.getBoard().getSections()[this.section.getID().x + i +1][this.section.getID().y - i -1].isOccupied() == true)
                            {
                                return false;
                            }
                        }
                        return true;
                    }
                    // kijkt of hij naar beneden beweegt
                    else if (this.section.getID().y < p_section.getID().y)
                    {
                        // kijkt of er een stuk in de weg staat
                        for (int i = 0; i < p_section.getID().y - this.section.getID().y -1; i++)
                        {
                            if (this.section.getBoard().getSections()[this.section.getID().x + i +1][this.section.getID().y + i +1].isOccupied() == true)
                            {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
