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

    /**
     *
     * @param p_color
     * @param p_player
     * @param p_section
     */
    public Bishop(String p_color, Player p_player, Section p_section)
    {
        super(p_color, p_player, p_section);
        if (p_color == "white")
        {
            this.setImg(new Image("ChessPieces/White Bishop.jpg"));
        }
        if (p_color == "black")
        {
            this.setImg(new Image("ChessPieces/Black Bishop.jpg"));
        }

    }

    @Override
    public Boolean checkMove(Section p_section)
    {
        // kijkt of er een stuk op de gekoze positie staat
        if (isValidMove(p_section))
        {
            // kijkt of het een move is die een loper mag doen
            return abs(p_section.getID().x - this.getSection().getID().x) == abs(p_section.getID().y - this.getSection().getID().y) && CheckMoveLeft(p_section) || CheckMoveRight(p_section);

        }
        return false;

    }

    /**
     * Checked of de Bishop naar links loopt
     *
     * @param p_section
     * @return
     */
    public boolean CheckMoveLeft(Section p_section)
    {
        if (this.getSection().getID().x > p_section.getID().x)
        { //checked of de bishop naar boven loopt
            return CheckLeftUp(p_section) || CheckLeftDown(p_section);
        }
        return false;
    }

    /**
     *
     * @param p_section
     * @return
     */
    public boolean CheckLeftUp(Section p_section)
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
        }
        return false;
    }

    /**
     *
     * @param p_section
     * @return
     */
    public boolean CheckLeftDown(Section p_section)
    {
        if (this.getSection().getID().y < p_section.getID().y)
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
        return false;
    }

    /**
     *
     * @param p_section
     * @return
     */
    public boolean CheckMoveRight(Section p_section)
    {
        if (this.getSection().getID().x < p_section.getID().x)
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
        return false;
    }

}
