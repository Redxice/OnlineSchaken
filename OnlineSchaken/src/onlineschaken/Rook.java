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

    /**
     *
     * @param p_color
     * @param p_player
     * @param p_section
     */
    public Rook(String p_color, Player p_player, Section p_section)
    {
        super(p_color, p_player, p_section);
        if (p_color == "white")
        {
            this.setImg(new Image("ChessPieces/White Rook.jpg"));
        }
        if (p_color == "black")
        {
            this.setImg(new Image("ChessPieces/Black Rook.jpg"));
        }
        this.MyType = "Rook";
    }

    /**
     *
     * @return
     */
    public Section castling()
    {
        Section section = null;
        return section;
    }

    /**
     * Deze methode kijkt of een rokade gedaan mag worden.
     *
     * @param p_section de section waar het stuk neergezet wil worden.
     * @return true waneer de zet geldig is en false waneer de zet niet geldig
     * is.
     */
    public Boolean checkRokade(Section p_section)
    {
        if (isHasMoved() == false)
        {
            if (p_section.getPiece() instanceof King)
            {
                if (this.getColor() == p_section.getPiece().getColor())
                {
                    // kijken of er iets tussen staat
                    if (getSection().getID().x == 0 && getSection().getID().y == 0)
                    {
                        for (int i = 0; i < 3; i++)
                        {
                            if (getSection().getBoard().getSections()[1 + i][0].isOccupied())
                            {
                                return false;
                            }
                        }
                        return true;
                    } else if (getSection().getID().x == 7 && getSection().getID().y == 0)
                    {
                        for (int i = 0; i < 2; i++)
                        {
                            if (getSection().getBoard().getSections()[6 - i][0].isOccupied())
                            {
                                return false;
                            }
                        }
                        return true;
                    } else if (getSection().getID().x == 0 && getSection().getID().y == 7)
                    {
                        for (int i = 0; i < 3; i++)
                        {
                            if (getSection().getBoard().getSections()[1 + i][7].isOccupied())
                            {
                                return false;
                            }
                        }
                        return true;
                    } else if (getSection().getID().x == 7 && getSection().getID().y == 7)
                    {
                        for (int i = 0; i < 2; i++)
                        {
                            if (getSection().getBoard().getSections()[6 - i][7].isOccupied())
                            {
                                return false;
                            }
                        }
                        return true;
                    }
                    if (p_section.getPiece().isHasMoved() == false)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Deze methode kijkt of de gekozen zet mag volgens de spelregels.
     *
     * @param p_section de section waar het stuk neergezet wil worden.
     * @return true waneer de zet geldig is en false waneer de zet niet geldig
     * is.
     */
    @Override
    public Boolean checkMove(Section p_section)
    {
        if (checkRokade(p_section) == true)
        {
            return true;
        }
        // Kijkt of de target section bezet is en door welke kleur
        if (isValidMove(p_section) == true)
        {
            // Kijkt of rokade kan

            // Kijkt of de toren over de y as kan bewegen
            if (this.getSection().getID().x == p_section.getID().x)
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
                // Kijkt of de toren over de x as kan bewegen
            } else if (this.getSection().getID().y == p_section.getID().y)
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
            }
        }
        return false;
    }
}
