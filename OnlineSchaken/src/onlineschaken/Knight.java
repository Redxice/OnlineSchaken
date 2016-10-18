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
public class Knight extends Piece
{

    public Knight(String p_color, Player p_player, Section p_section)
    {
        super(p_color, p_player, p_section);
        if (p_color == "white")
        {
            this.img = new Image("ChessPieces/White Knight.jpg");
        }
        if (p_color == "black")
        {
            this.img = new Image("ChessPieces/Black Knight.jpg");
        }
    }

    @Override
    public Boolean checkMove(Section p_section)
    {
        //check of het een geldige vak is om naar toe te verschuiven
        if (isValidMove(p_section) == false)
        {
            return false;
        }
        //kijk of de move geldig is2 opzij 1 omhoog(of in een andererichting)
        if (section.id.x == p_section.id.x + 2)
        {
            if (section.id.y == p_section.id.y + 1)
            {
                return true;
            }
        }
        if (section.id.x == p_section.id.x + 2)
        {
            if (section.id.y == p_section.id.y - 1)
            {
                return true;
            }
        }
        if (section.id.x == p_section.id.x - 2)
        {
            if (section.id.y == p_section.id.y + 1)
            {
                return true;
            }
        }
        if (section.id.x == p_section.id.x - 2)
        {
            if (section.id.y == p_section.id.y - 1)
            {
                return true;
            }
        }

        if (section.id.x == p_section.id.x + 1)
        {
            if (section.id.y == p_section.id.y + 2)
            {
                return true;
            }
        }

        if (section.id.x == p_section.id.x + 1)
        {
            if (section.id.y == p_section.id.y - 2)
            {
                return true;
            }
        }

        if (section.id.x == p_section.id.x - 1)
        {
            if (section.id.y == p_section.id.y + 2)
            {
                return true;
            }
        }

        if (section.id.x == p_section.id.x - 1)
        {
            if (section.id.y == p_section.id.y - 2)
            {
                return true;
            }
        }
        return false;
    }
}
