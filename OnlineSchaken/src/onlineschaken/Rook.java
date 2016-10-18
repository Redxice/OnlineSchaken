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
            this.img = new Image("ChessPieces/White Rook.jpg");
        }
        if (p_color == "black")
        {
            this.img = new Image("ChessPieces/Black Rook.jpg");
        }
    }

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
        // kijken of er bewogen is
        if (hasMoved == false)
        {
            // kijken of de geklikte plek een koning bevat
            if (p_section.getPiece() instanceof King)
            {
                // kijken of de kleur van de koning en de toren het zelfde zijn
                if (this.color == p_section.getPiece().color)
                {
                    // kijken of de koning heeft bewogen
                    if (p_section.getPiece().hasMoved == false)
                    {
                        // kijken of er iets tussen staat
                        if (section.getID().x == 0 && section.getID().y == 0)
                        {
                            for (int i = 0; i < 3; i++)
                            {
                                if (section.getBoard().getSections()[1 + i][0].isOccupied())
                                {
                                    return false;
                                }
                            }
                            return true;
                        } else if (section.getID().x == 7 && section.getID().y == 0)
                        {
                            for (int i = 0; i < 2; i++)
                            {
                                if (section.getBoard().getSections()[6 - i][0].isOccupied())
                                {
                                    return false;
                                }
                            }
                            return true;
                        } else if (section.getID().x == 0 && section.getID().y == 7)
                        {
                            for (int i = 0; i < 3; i++)
                            {
                                if (section.getBoard().getSections()[1 + i][0].isOccupied())
                                {
                                    return false;
                                }
                            }
                            return true;
                        } else if (section.getID().x == 7 && section.getID().y == 7)
                        {
                            for (int i = 0; i < 2; i++)
                            {
                                if (section.getBoard().getSections()[6 - i][0].isOccupied())
                                {
                                    return false;
                                }
                            }
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
     *
     * @param p_section de section waar het stuk neergezet wil worden.
     * @return true waneer de zet geldig is en false waneer de zet niet geldig
     * is.
     */
    @Override
    public Boolean checkMove(Section p_section)
    {
        // kijkt of er een rokade gedaan wordt
        if (checkRokade(p_section) == true)
        {
            return true;
        }
        // Kijkt of de target section bezet is en door welke kleur
        if (isValidMove(p_section) == true)
        {
            // Kijkt of de toren naar rechts of links gaat
            if (this.section.getID().x == p_section.getID().x)
            {
                if (this.section.getID().y > p_section.getID().y)
                {
                    // kijkt of er iets in de weg staat
                    for (int i = 0; i < this.section.getID().y - p_section.getID().y - 1; i++)
                    {
                        if (this.section.getBoard().getSections()[this.section.getID().x][this.section.getID().y - i - 1].isOccupied() == true)
                        {
                            return false;
                        }
                    }
                    return true;
                } else if (this.section.getID().y < p_section.getID().y)
                {
                    // kijkt of er iets in de weg staat
                    for (int i = 0; i < p_section.getID().y - this.section.getID().y - 1; i++)
                    {
                        if (this.section.getBoard().getSections()[this.section.getID().x][this.section.getID().y + i + 1].isOccupied() == true)
                        {
                            return false;
                        }
                    }
                    return true;
                }
                // Kijkt of de toren over de x as kan bewegen
            } // Kijkt of de toren naar boven of beneden gaat
            else if (this.section.getID().y == p_section.getID().y)
            {
                if (this.section.getID().x < p_section.getID().x)
                {
                    // kijkt of er iets in de weg staat
                    for (int i = 0; i < p_section.getID().x - this.section.getID().x - 1; i++)
                    {
                        if (this.section.getBoard().getSections()[this.section.getID().x + i + 1][this.section.getID().y].isOccupied() == true)
                        {
                            return false;
                        }
                    }
                    return true;
                } else if (this.section.getID().x > p_section.getID().x)
                {
                    // kijkt of er iets in de weg staat
                    for (int i = 0; i < this.section.getID().x - p_section.getID().x - 1; i++)
                    {
                        if (this.section.getBoard().getSections()[this.section.getID().x - i - 1][this.section.getID().y].isOccupied() == true)
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
