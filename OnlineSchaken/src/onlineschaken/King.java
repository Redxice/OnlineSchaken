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

    /**
     *
     * @param p_color
     * @param p_player
     * @param p_section
     */
    public King(String p_color, Player p_player, Section p_section)
    {
        super(p_color, p_player, p_section);
        //check of de koning zwart of wit is en bepaal de juiste afbeelding
        if ("white".equals(p_color))
        {
            this.setImg(new Image("ChessPieces/White King.jpg"));
        }
        if ("black".equals(p_color))
        {
            this.setImg(new Image("ChessPieces/Black King.jpg"));
        }
        check = false;
        checkMate = false;
        this.MyType = "King";
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @return
     */
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

    /**
     *
     * @return
     */
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

    /**
     *
     * @param p_section
     * @return
     */
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

    /**
     *
     * @param check
     */
    public void setCheck(boolean check)
    {
        this.check = check;
    }

    /**
     *
     * @param checkMate
     */
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
                return !becomeCheck(p_section);
    }
}
