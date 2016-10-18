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
public class King extends Piece
{

    boolean check;
    boolean checkMate;

    public King(String p_color, Player p_player, Section p_section)
    {
        super(p_color, p_player, p_section);
        //check of de koning zwart of wit is en bepaal de juiste afbeelding
        if (p_color == "white")
        {
            this.img = new Image("ChessPieces/White King.jpg");
        }
        if (p_color == "black")
        {
            this.img = new Image("ChessPieces/Black King.jpg");
        }
        check = false;
        checkMate = false;
    }
    
    public boolean isCheck()
    {
        for (Section[] x : section.getBoard().getSections())
            {
                for (Section y : x)
                {
                    if (!y.getPiece().color.equals(this.color))
                    {
                        if (y.getPiece().checkMove(this.section))
                        {
                            check = true;
                            isCheckMate();
                        }
                    }
                }
            } 
        return check;
    }

    public void setCheck(boolean check)
    {
        this.check = check;
    }

    public boolean isCheckMate()
    {
        //voor elke rij
        for (Section[] x : section.getBoard().getSections())
            {
                //voor elk vakje in de rij
                for (Section y : x)
                {
                    if (!y.getPiece().color.equals(this.color))
                    {
                        if (y.getPiece().checkMove(this.section.getBoard().getSections()[this.section.getID().x +1][this.section.getID().y]))
                        {
                          checkMate = true;  
                        }
                        else if (y.getPiece().checkMove(this.section.getBoard().getSections()[this.section.getID().x +1][this.section.getID().y +1]))
                        {
                          checkMate = true;  
                        }
                        else if (y.getPiece().checkMove(this.section.getBoard().getSections()[this.section.getID().x][this.section.getID().y +1]))
                        {
                          checkMate = true;  
                        }
                        else if (y.getPiece().checkMove(this.section.getBoard().getSections()[this.section.getID().x -1][this.section.getID().y]))
                        {
                          checkMate = true;  
                        }
                        else if (y.getPiece().checkMove(this.section.getBoard().getSections()[this.section.getID().x][this.section.getID().y -1]))
                        {
                          checkMate = true;  
                        }
                        else if (y.getPiece().checkMove(this.section.getBoard().getSections()[this.section.getID().x -1][this.section.getID().y -1]))
                        {
                          checkMate = true;  
                        }
                        else if (y.getPiece().checkMove(this.section.getBoard().getSections()[this.section.getID().x +1][this.section.getID().y -1]))
                        {
                          checkMate = true;  
                        }
                        else if (y.getPiece().checkMove(this.section.getBoard().getSections()[this.section.getID().x -1][this.section.getID().y +1]))
                        {
                          checkMate = true;  
                        }
                    }
                }
            }        
        return checkMate;
    }

    public void setCheckMate(boolean checkMate)
    {
        this.checkMate = checkMate;
    }

    public Section castling()
    {
        Section section = null;
        return section;
    }

    /**
     *
     * @param p_section waarna toe moet worden bewogen
     * @return geeft true terug als de koning naar dit vak mag bewegen
     */
    @Override
    public Boolean checkMove(Section p_section)
    {
            //check of het een geldige vak is om naar toe te verschuiven
            if (isValidMove(p_section) == false)
            {
                return false;
            } //check of koning niet meer dan 1 vakje verschuift
            else if (p_section.getID().x + 1 < section.getID().x)
            {
                return false;
            } else if (p_section.getID().x - 1 > section.getID().x)
            {
                return false;
            } else if (p_section.getID().y + 1 < section.getID().y)
            {
                return false;
            } else if (p_section.getID().y - 1 > section.getID().y)
            {
                return false;
            }
            //check of je niet versliest door de koning naar de plek te verplaatsen
            for (Section[] x : section.getBoard().getSections())
            {
                for (Section y : x)
                {
                    if(y.getPiece() != null)
                    {
                        if (!y.getPiece().color.equals(this.color))
                        {
                            if (y.getPiece().checkMove(p_section))
                            {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
    }
}
