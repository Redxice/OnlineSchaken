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
            this.setImg(new Image("ChessPieces/White King.jpg"));
        }
        if (p_color == "black")
        {
            this.setImg(new Image("ChessPieces/Black King.jpg"));
        }
        check = false;
        checkMate = false;
        this.MyType = "King";
    }

    public int countCheckSections()
    {
        int counter = 0;
        for (Section[] x : getSection().getBoard().getSections())
        {
            for (Section y : x)
            {
                if (y.getPiece() != null)
                {
                    if (!y.getPiece().getColor().equals(this.getColor()))
                    {
                        if (y.getPiece().checkMove(this.getSection()))
                        {
                            counter++;
                        }
                    }
                }
            }
        }
        return counter;
    }

    public Section getSingleCheckSection()
    {
        Section checkSection = null;
        for (Section[] x : getSection().getBoard().getSections())
        {
            for (Section y : x)
            {
                if (y.getPiece() != null)
                {
                    if (!y.getPiece().getColor().equals(this.getColor()))
                    {
                        if (y.getPiece().checkMove(this.getSection()))
                        {
                            checkSection = y;
                        }
                    }
                }
            }
        }
        return checkSection;
    }

    public boolean isCheck()
    {
        check = false;
        for (Section[] x : getSection().getBoard().getSections())
        {
            for (Section y : x)
            {
                if (y.getPiece() != null)
                {
                    if (!y.getPiece().getColor().equals(this.getColor()))
                    {
                        if (y.getPiece().checkMove(this.getSection()))
                        {
                            check = true;
                            return check;
                        }
                    }
                }
            }
        }
        return check;
    }

    public boolean becomeCheck(Section p_section)
    {
        Piece previousPiece;
        for (Section[] x : getSection().getBoard().getSections())
        {
            for (Section y : x)
            {
                if (y.getPiece() != null)
                {
                    if (!y.getPiece().getColor().equals(this.getColor()))
                    {
                        previousPiece = p_section.getPiece();
                        p_section.tempSetPiece(this);
                        if (y.getPiece().checkMove(p_section))
                        {
                            p_section.tempSetPiece(previousPiece);
                            return true;
                        }
                        p_section.tempSetPiece(previousPiece);
                    }
                }
            }
        }
        return false;
    }

    public void setCheck(boolean check)
    {
        this.check = check;
    }

    public void setCheckMate(boolean checkMate)
    {
        this.checkMate = checkMate;
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
        else if (p_section.getID().x + 1 < getSection().getID().x)
        {
            return false;
        } else if (p_section.getID().x - 1 > getSection().getID().x)
        {
            return false;
        } else if (p_section.getID().y + 1 < getSection().getID().y)
        {
            return false;
        } else if (p_section.getID().y - 1 > getSection().getID().y)
        {
            return false;
        }
        //check of je niet versliest door de koning naar de plek te verplaatsen
        if (becomeCheck(p_section))
        {
            return false;
        }
        return true;
    }
}
