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
public class Rook extends Piece
{

    public Rook(String p_color, Player p_player, Section p_section)
    {
        super(p_color, p_player, p_section);
        if (p_color == "white")
        {
            this.img = new Image("ChessPieces/White Rook.png");
        }
        if (p_color == "black")
        {
            this.img = new Image("ChessPieces/Black Rook.png");
        }
    }

    public Section castling()
    {
        Section section = null;
        return section;
    }
    
    /**
     * Deze methode kijkt of een rokade gedaan mag worden.
     * @param p_section de section waar het stuk neergezet wil worden.
     * @return true waneer de zet geldig is en false waneer de zet niet geldig
     * is.
     */
    public Boolean checkRokade(Section p_section)
    {
        if (isValidMove(p_section) == true)
        { 
            if (hasMoved == false)
            {
                if (p_section.getPiece() instanceof King)
                {
                    if (this.color == p_section.getPiece().color)
                    {
                        if (p_section.getPiece().hasMoved == false)
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    /**
     * Deze methode kijkt of de gekozen zet mag volgens de spelregels.
     * @param p_section de section waar het stuk neergezet wil worden.
     * @return true waneer de zet geldig is en false waneer de zet niet geldig
     * is.
     */
    @Override
    public Boolean checkMove(Section p_section)
    {
        // Kijkt of de target section bezet is en door welke kleur
        if (isValidMove(p_section) == true)
        {    
            // Kijkt of rokade kan
            if(checkRokade(p_section) == true)
            {
            return true;
            }
            // Kijkt of de toren over de y as kan bewegen
            else if (this.section.getID().x == p_section.getID().x)
            {
                if (this.section.getID().y > p_section.getID().y)
                {
                    for (int i = 0; i < p_section.getID().y - this.section.getID().y; i++)
                    {
                        if (this.section.getBoard().getSections()[this.section.getID().x][this.section.getID().y + i].getPiece() != null)
                        {
                            return false;
                        }
                    }
                    return true;
                } else if (this.section.getID().y < p_section.getID().y)
                {
                    for (int i = 0; i < this.section.getID().y - p_section.getID().y; i++)
                    {
                        if (this.section.getBoard().getSections()[this.section.getID().x][this.section.getID().y - i] != null)
                        {
                            return false;
                        }
                    }
                    return true;
                }
            // Kijkt of de toren over de x as kan bewegen
            } else if (this.section.getID().y == p_section.getID().y)
            {
                if (this.section.getID().x > p_section.getID().x)
                {
                    for (int i = 0; i < this.section.getID().x - p_section.getID().x; i++)
                    {
                        if (this.section.getBoard().getSections()[this.section.getID().x + i][this.section.getID().y] != null)
                        {
                            return false;
                        }
                    }
                    return true;
                } else if (this.section.getID().x < p_section.getID().x)
                {
                    for (int i = 0; i < p_section.getID().x - this.section.getID().x; i++)
                    {
                        if (this.section.getBoard().getSections()[this.section.getID().x - i][this.section.getID().y] != null)
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
