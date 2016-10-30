/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.awt.Point;
import java.util.List;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 *
 * @author redxice
 */
public abstract class Piece extends StackPane
{

    //fields
    String color;
    Player player;
    Section section;
    Image img;
    boolean hasMoved;
    Section previousState;
//constructor

    public Piece(String p_color, Player p_player, Section p_section)
    {
        this.color = p_color;
        this.player = p_player;
        this.section = p_section;
        if (this.section != null)
        {
            this.section.setPiece(this);
        }

    }
    public Player getPlayer()
    {
        return player;
    }

    public boolean isValidMove(Section p_section)
    {

        if (p_section.isOccupied() == true)
        {
            if (p_section.getPiece().getColor() != this.color)
            {
                return true;
            } else
            {
                return false;
            }
        } else
        {
            return true;
        }
    }

    public void setSection(Section section)
    {
        this.section = section;
        if (this.section != null)
        {
            section.setPiece(this);
        }

    }

    public String getColor()
    {
        return this.color;
    }
    //methode

    public abstract Boolean checkMove(Section p_section);

    public Boolean move(Section p_section)
    {
        System.out.println(String.valueOf(section.id));
        Point idKing = null;
        Boolean check = false;
        Piece p_sectionPiece = p_section.getPiece();
        
        //previousState = this.section;
        for (Piece p : player.pieces)
        {
            if (p instanceof King)
            {
                ((King) p).isCheck();
                if (((King) p).check)
                {
                    check = true;
                    if (this.section.getPiece() instanceof King)
                    {
                        if (((King) p).becomeCheck(p_section))
                        {
                            return false;
                        }
                    }
                    idKing = ((King) p).getSingleCheckSection().id;
                }
            }

            if (!(this instanceof King) /*&& check == true*/)
            {
                if (idKing != p_section.id)
                {
                    //this.section.setPiece(null);
                    previousState = this.section;
                    p_section.setPiece(this);
                    this.section.setPiece(null);
                    for (Piece p2 : player.pieces)
                    {
                        if (p2 instanceof King)
                        {
                            //p_section.setPiece(this);
                            //this.section.setPiece(null);
                            ((King) p2).isCheck();
                            if (((King) p2).check)
                            {
                                p_section.setPiece(null);
                                this.section = previousState;
                                section.setPiece(this);
                                System.out.println("---" + section);
                                return false;
                            }
                        }
                    }
                    p_section.setPiece(null);
                    this.section = previousState;
                    this.section.setPiece(this);
                    check = false;

                } else
                {
                    check = false;
                }
            }
        }

        try
        {
            if (p_sectionPiece != null)
        { 
            p_section.setPiece(p_sectionPiece);
        }
        
           if (checkMove(p_section))
            {
                // kijkt of het gekoze stuk een toren is
                if (section.getPiece() instanceof Rook && section.getPiece().hasMoved == false)
                {
                    // kijkt of op de gekoze locatie een koning staat
                    if (p_section.getPiece() instanceof King && p_section.getPiece().hasMoved == false)
                    {
                        // kijkt welke toren het is en zet ze dan op de goede plaats
                        if (section.getID().getX() == 0 && section.getID().getY() == 0)
                        {

                            if (((King) p_section.getPiece()).becomeCheck(section.getBoard().getSections()[3][0]))
                            {
                                section.getBoard().drawSpecificPieces(section, section.getBoard().getSections(3, 0));
                                p_section.getBoard().drawSpecificPieces(p_section, section.getBoard().getSections(2, 0));
                                section.getBoard().getSections()[3][0].setPiece(this);
                                section.getBoard().getSections()[2][0].setPiece(p_section.getPiece());
                                section.setPiece(null);
                                this.section = section.getBoard().getSections(3, 0);
                                p_section.getPiece().section = section.getBoard().getSections()[2][0];
                                p_section.setPiece(null);
                                return true;
                            }
                            return false;
                        } else if (section.getID().getX() == 0 && section.getID().getY() == 7)
                        {
                            if (((King) p_section.getPiece()).becomeCheck(section.getBoard().getSections()[3][7]))
                            {
                                section.getBoard().drawSpecificPieces(section, section.getBoard().getSections(3, 7));
                                p_section.getBoard().drawSpecificPieces(p_section, section.getBoard().getSections(2, 7));
                                section.getBoard().getSections()[3][7].setPiece(this);
                                section.getBoard().getSections()[2][7].setPiece(p_section.getPiece());
                                section.setPiece(null);
                                this.section = section.getBoard().getSections(3, 7);
                                p_section.getPiece().section = section.getBoard().getSections()[2][7];
                                p_section.setPiece(null);
                                return true;
                            }
                            return false;
                        } else if (section.getID().getX() == 7 && section.getID().getY() == 7)
                        {
                            if (((King) p_section.getPiece()).becomeCheck(section.getBoard().getSections()[5][7]))
                            {
                                section.getBoard().drawSpecificPieces(section, section.getBoard().getSections(5, 7));
                                p_section.getBoard().drawSpecificPieces(p_section, section.getBoard().getSections(6, 7));
                                section.getBoard().getSections()[5][7].setPiece(this);
                                section.getBoard().getSections()[6][7].setPiece(p_section.getPiece());
                                section.setPiece(null);
                                this.section = section.getBoard().getSections(5, 7);
                                p_section.getPiece().section = section.getBoard().getSections()[6][7];
                                p_section.setPiece(null);
                                return true;
                            }
                            return false;
                        } else if (section.getID().getX() == 7 && section.getID().getY() == 0)
                        {
                            if (((King) p_section.getPiece()).becomeCheck(section.getBoard().getSections()[5][0]))
                            {
                                section.getBoard().drawSpecificPieces(section, section.getBoard().getSections(5, 0));
                                p_section.getBoard().drawSpecificPieces(p_section, section.getBoard().getSections(6, 0));
                                section.getBoard().getSections()[5][0].setPiece(this);
                                section.getBoard().getSections()[6][0].setPiece(p_section.getPiece());
                                section.setPiece(null);
                                this.section = section.getBoard().getSections(5, 0);
                                p_section.getPiece().section = section.getBoard().getSections()[6][0];
                                p_section.setPiece(null);
                                return true;
                            }
                            return false;
                        }
                    }
                }
                /*if()
                {
                    return false;
                }*/
                System.out.println(section.id);
                System.out.println(p_section.id);
                System.out.println(section.getBoard());
                section.getBoard().drawSpecificPieces(section, p_section);
                section.setPiece(null);
                section.id.x = p_section.id.x;
                section.id.y = p_section.id.y;
                section.getBoard().getSections()[section.id.x][section.id.y].setPiece(this);
                this.section = section.getBoard().getSections()[section.id.x][section.id.y];
                if (this instanceof Pawn)
                {
                    Pawn pawn = (Pawn) this;
                    if (pawn.Promotion(p_section))
                    {
                        Popup menu = pawn.menu();
                        double x = p_section.getBoard().getWIDTH() * 100;
                        double y = p_section.getBoard().getHEIGHT() * 50;
                        menu.requestFocus();
                        menu.show(p_section, x, y);

                    }
                }
                hasMoved = true;
                return true;
            }
            return false;
        } catch (NullPointerException e)
        {
            //Logger.getLogger(e.getMessage());
            System.out.println(e.getMessage());
            System.out.println(section.getPiece());
            System.out.println(section.getBoard());
            System.out.println(p_section.getPiece());
        }
        return false;
    }

    public void moveWithoutCheck(Section p_section)
    {
        try
        {
            section.getBoard().drawSpecificPieces(section, p_section);
            section.setPiece(null);
            section.id.x = p_section.id.x;
            section.id.y = p_section.id.y;
            section.getBoard().getSections()[section.id.x][section.id.y].setPiece(this);
            this.section = section.getBoard().getSections()[section.id.x][section.id.y];
        } catch (NullPointerException e)
        {
            //Logger.getLogger(e.getMessage());
            System.out.println(e.getMessage());
            System.out.println(section.getPiece());
            System.out.println(section.getBoard());
            System.out.println(p_section.getPiece());
        }
        hasMoved = true;
    }

    public Section getSection()
    {
        return this.section;
    }
    //geeft een lijst met opties

    public List<Section> moveOption(Section p_curSection)
    {
        List<Section> sections = null;
        return sections;
    }
}
