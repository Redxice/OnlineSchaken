/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import Shared.IinGameController;
import Shared.IrmiClient;
import java.awt.Point;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import java.util.logging.*;
import javafx.application.Platform;
import javax.swing.SwingUtilities;

/**
 *
 * @author redxice
 */
public abstract class Piece extends StackPane implements Serializable
{

    //fields
    private transient static final Logger LOGGER = Logger.getLogger(Piece.class.getName());
    private transient String color;
    private transient Player player;
    private transient Section section;
    private int x;
    private int y;
    private transient Image img;
    private boolean hasMoved;
    private transient Section previousState;

    //constructor
    public Piece(String p_color, Player p_player, Section p_section)
    {
        this.color = p_color;
        this.player = p_player;
        this.section = p_section;
        if (this.section != null)
        {
            this.section.setPiece(this);
            this.x = p_section.getID().x;
            this.y = p_section.getID().y;
        }

    }

    public static Logger getLOGGER()
    {
        return LOGGER;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public void setImg(Image img)
    {
        this.img = img;
    }

    public void setHasMoved(boolean hasMoved)
    {
        this.hasMoved = hasMoved;
    }

    public void setPreviousState(Section previousState)
    {
        this.previousState = previousState;
    }

    public Image getImg()
    {
        return img;
    }

    public boolean isHasMoved()
    {
        return hasMoved;
    }

    public Section getPreviousState()
    {
        return previousState;
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
            this.x = (int)section.getX();
            this.y = (int)section.getY();
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
        Point idKing = null;
        Piece p_sectionPiece = p_section.getPiece();

        if (CheckCheckMate(p_section) != true)
        {
            return false;
        }
        try
        {
            if (p_sectionPiece != null)
            {
                p_section.setPiece(p_sectionPiece);
            }

            if (checkMove(p_section))
            {
                if (RookCheck(p_section) == false)
                {
                    return false;
                }
                section.getBoard().drawSpecificPieces(section, p_section);
                section.setPiece(null);
                section.getID().x = p_section.getID().x;
                section.getID().y = p_section.getID().y;
                section.getBoard().getSections()[section.getID().x][section.getID().y].setPiece(this);
                this.section = section.getBoard().getSections()[section.getID().x][section.getID().y];
                PawnPromotion(p_section);
                hasMoved = true;
                try
                {
                    this.section.getBoard().getClient().GetGameController().setMyturn();
                } catch (RemoteException ex)
                {
                    Logger.getLogger(Piece.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }
            return false;
        } catch (NullPointerException e)
        {
            LOGGER.log(Level.FINE, e.getMessage(), e);
        }
        return false;
    }

    public void moveWithoutCheck(Section p_section)
    {
        try
        {
            CheckCheckMate(p_section);
            section.getBoard().drawSpecificPieces(section, p_section);
            section.setPiece(null);
            section.getID().x = p_section.getID().x;
            section.getID().y = p_section.getID().y;
            section.getBoard().getSections()[section.getID().x][section.getID().y].setPiece(this);
            this.section = section.getBoard().getSections()[section.getID().x][section.getID().y];
        } catch (NullPointerException e)
        {
            LOGGER.log(Level.FINE, e.getMessage(), e);
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

    public boolean CheckCheckMate(Section p_section)
    {
        Point idKing = null;

        for (Piece p : player.getPieces())
        {
            if (p instanceof King)
            {
                ((King) p).isCheck();
                if (((King) p).check)
                {
                    if (this.section.getPiece() instanceof King)
                    {
                        if (((King) p).becomeCheck(p_section))
                        {
                            return false;
                        }
                    }
                    if (((King) p).countCheckSections() == 1)
                    {
                        idKing = ((King) p).getSingleCheckSection().getID();
                    } else
                    {
                        return false;
                    }

                }
            }

        }
        if (!(this instanceof King) /*&& check == true*/)
        {
            if (idKing != p_section.getID())
            {
                previousState = this.section;
                p_section.setPiece(this);
                this.section.setPiece(null);
                for (Piece p2 : player.getPieces())
                {
                    if (p2 instanceof King)
                    {
                        ((King) p2).isCheck();
                        if (((King) p2).check)
                        {
                            p_section.setPiece(null);
                            this.section = previousState;
                            section.setPiece(this);
                            return false;
                        }
                    }
                }
                p_section.setPiece(null);
                this.section = previousState;
                this.section.setPiece(this);
            }
        }
        return true;
    }

    public boolean RookCheck(Section p_section)
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

                    if (((King) p_section.getPiece()).becomeCheck(section.getBoard().getSections()[3][0]) == false)
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
                    if (((King) p_section.getPiece()).becomeCheck(section.getBoard().getSections()[3][7]) == false)
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
                    if (((King) p_section.getPiece()).becomeCheck(section.getBoard().getSections()[5][7]) == false)
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
                    if (((King) p_section.getPiece()).becomeCheck(section.getBoard().getSections()[5][0]) == false)
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
        return true;
    }

    public void PawnPromotion(Section p_section)
    {
        try
        {
            if (this instanceof Pawn)
            {
                IinGameController IngameController = this.section.getBoard().getClient().GetGameController();
                if (this instanceof Pawn)
                {
                    Pawn pawn = (Pawn) this;
                    if (pawn.Promotion(p_section))
                    {
                        if (this.color == "white" && IngameController.isWhite() == true || this.color == "black" && IngameController.isWhite() == false)
                        {
                            if (IngameController.getMyTurn())
                            {
                                IngameController.setisPromoting(true);
                                final IrmiClient RmiClient = this.section.getBoard().getClient();
                                Platform.runLater(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        try
                                        {
                                            pawn.menu(IngameController, RmiClient);
                                        } catch (RemoteException ex)
                                        {
                                            Logger.getLogger(Piece.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        } catch (RemoteException ex)
        {
            Logger.getLogger(Piece.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }
    
    public boolean canMove()
    {
        for(Section[] listSections : section.getBoard().getSections())
        {
            for(Section s : listSections)
            {
                if(checkMove(s))
                {                 
                    return true;
                }
            }
        }
        return false;
    }

}
