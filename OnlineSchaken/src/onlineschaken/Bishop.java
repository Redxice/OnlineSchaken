/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import static java.lang.Math.abs;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author redxice
 */
public class Bishop extends Piece
{

    /**
     *
     * @param p_color
     * @param p_player
     * @param p_section
     */
    public Bishop(String p_color, Player p_player, Section p_section)
    {
        super(p_color, p_player, p_section);
        if ("white".equals(p_color))
        {
            this.setImg(new Image("ChessPieces/White Bishop.jpg"));
        }
        if ("black".equals(p_color))
        {
            this.setImg(new Image("ChessPieces/Black Bishop.jpg"));
        }
        this.MyType = "Bishop";

    }

    /**
     *
     * @param p_section
     * @return
     */
    @Override
    public Boolean checkMove(Section p_section)
    {
        // kijkt of er een stuk op de gekoze positie staat
        if (isValidMove(p_section))
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
            }
        }
        return false;
    }
    private static final Logger LOG = Logger.getLogger(Bishop.class.getName());
}
