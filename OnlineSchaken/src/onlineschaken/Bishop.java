/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

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
            this.img = new Image("ChessPieces/White Bishop.png");
        }
        if (p_color == "black")
        {
            this.img = new Image("ChessPieces/Black Bishop.png");
        }

    }

    @Override
    public Boolean checkMove(Section p_section)
    {
        if (isValidMove(p_section) == true)
        {
            if (p_section.getID().x - this.section.getID().x == p_section.getID().y - this.section.getID().y)
            {
                if (this.section.getID().x > p_section.getID().x)
                {
                    if (this.section.getID().y > p_section.getID().y)
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
        }
        return false;
    }

}
