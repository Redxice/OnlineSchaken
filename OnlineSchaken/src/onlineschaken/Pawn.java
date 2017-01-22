/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import Shared.IinGameController;
import Shared.IrmiClient;
import java.rmi.RemoteException;
import java.util.Optional;
import java.util.logging.Level;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Screen;

/**
 *
 * @author redxice
 */
public class Pawn extends Piece
{

    private enum type
    {
        Knight,
        Bishop,
        Queen,
        Rook
    }

    private transient Section prevSection;
    private double prevX;
    private double prevY;

    /**
     *
     * @param p_color
     * @param p_player
     * @param p_section
     */
    public Pawn(String p_color, Player p_player, Section p_section)
    {
        super(p_color, p_player, p_section);
        if (p_color == "white")
        {
            this.setImg(new Image("ChessPieces/White Pawn.jpg"));
        } else if (p_color == "black")
        {
            this.setImg(new Image("ChessPieces/Black Pawn.jpg"));
        }
        this.setHasMoved(false);
         this.MyType = "Pawn";
    }

    /**
     *
     * @param section
     */
    public void setPrevSection(Section section)
    {
        this.prevX = section.getID().x;
        this.prevY = section.getID().y;
        this.prevSection = section;
    }

    /**
     *
     * @return
     */
    public double getPrevSectionX()
    {
        return prevX;
    }

    /**
     *
     * @return
     */
    public double getPrevSectionY()
    {
        return prevY;
    }

    /**
     *
     * @return
     */
    public Section getPrevSection()
    {
        return this.prevSection;
    }

    /**
     *
     * @param p_section
     * @return
     */
    @Override
    public Boolean checkMove(Section p_section)
    {
        Section prevsection = getSection();
        Board board = p_section.getBoard();
        if (isValidMove(p_section) == false)
        {
            return false;
        } else if (isHasMoved() == false)
        {
            if (this.getColor().equals("black") )
            {
                //1 section naar voren.
                if (this.moveOneTileForwardBlack(p_section, board))
                {
                    this.setPrevSection(prevsection);
                    return true;
                } //2 section naar voren
                else if (this.moveTwoTilesForwardBlack(p_section, board))
                {
                    this.setPrevSection(prevsection);
                    return true;
                } //schuin slaan van andere piece.
                else if (this.toCaptureBlack(p_section, board))
                {
                    this.setPrevSection(prevsection);
                    return true;
                }
            }
            if (this.getColor().equals("white"))
            {
                //1 section naar voren.
                if (this.moveOneTileForwardWhite(p_section, board))
                {
                    this.setPrevSection(prevsection);
                    return true;
                } //2 section naar voren
                else if (this.moveTwoTilesForwardWhite(p_section, board))
                {
                    this.setPrevSection(prevsection);
                    return true;
                } //schuin slaan van andere pawn.
                else if (this.toCaptureWhite(p_section, board))
                {
                    this.setPrevSection(prevsection);
                    return true;
                }
            }
        } else if (isHasMoved() == true)
        {
            if (this.getColor().equals("black") )
            {
                //1 section naar voren.
                if (this.moveOneTileForwardBlack(p_section, board))
                {
                    this.setPrevSection(prevsection);
                    return true;
                } //schuin slaan van andere pawn.
                else if (this.toCaptureBlack(p_section, board))
                {
                    this.setPrevSection(prevsection);
                    return true;
                }

            } else if (this.getColor().equals("white"))
            {
                //1 section naar voren.
                if (this.moveOneTileForwardWhite(p_section, board))
                {
                    this.setPrevSection(prevsection);
                    return true;
                } //schuin slaan van andere pawn.
                else if (this.toCaptureWhite(p_section, board))
                {
                    this.setPrevSection(prevsection);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Deze methode wordt aangeroepen wanneer de promotion methode true returned
     *
     * @param controller
     * @param client
     * @throws java.rmi.RemoteException
     */
    public void menu(IinGameController controller, IrmiClient client) throws RemoteException
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        Pawn pawn = this;
        ButtonType Bishop = new ButtonType(type.Bishop.name());
        ButtonType Knight = new ButtonType(type.Knight.name());
        ButtonType Queen = new ButtonType(type.Queen.name());
        ButtonType Rook = new ButtonType(type.Rook.name());

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        alert.setX(screenBounds.getWidth() / 2);
        alert.setY(screenBounds.getHeight() / 2);
        alert.getButtonTypes().setAll(Bishop, Knight, Queen, Rook);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == Bishop)
        {
            Section section = pawn.getSection();
            Bishop bishop = new Bishop(pawn.getColor(), pawn.getPlayer(), section.getBoard().getSections((int) prevX, (int) prevY));
            pawn.getPlayer().getPieces().add(bishop);
            bishop.getPlayer().getPieces().remove(pawn);
            bishop.moveWithoutCheck(section);
            alert.close();
            client.castPiece(bishop, pawn);
            controller.setisPromoting(false);

        } else if (result.get() == Knight)
        {
            Section section = pawn.getSection();
            Knight knight = new Knight(pawn.getColor(), pawn.getPlayer(), section.getBoard().getSections((int) prevX, (int) prevY));
            pawn.getPlayer().getPieces().add(knight);
            knight.getPlayer().getPieces().remove(pawn);
            knight.moveWithoutCheck(section);
            client.castPiece(knight, pawn);
            controller.setisPromoting(false);
            alert.close();

        } else if (result.get() == Queen)
        {
            Section section = pawn.getSection();
            Queen queen = new Queen(pawn.getColor(), pawn.getPlayer(), section.getBoard().getSections((int) prevX, (int) prevY));
            pawn.getPlayer().getPieces().add(queen);
            queen.getPlayer().getPieces().remove(pawn);
            queen.moveWithoutCheck(section);
            client.castPiece(queen, pawn);
            controller.setisPromoting(false);
            alert.close();
        } else if (result.get() == Rook)
        {
            Section section = pawn.getSection();
            Rook rook = new Rook(pawn.getColor(), pawn.getPlayer(), section.getBoard().getSections((int) prevX, (int) prevY));
            pawn.getPlayer().getPieces().add(rook);
            rook.getPlayer().getPieces().remove(pawn);
            rook.moveWithoutCheck(section);
            client.castPiece(rook, pawn);
            controller.setisPromoting(false);
            alert.close();
        }
    }

    /**
     *
     * @param piece
     */
    public void PromoteThisPawn(Piece piece)
    {
        if (piece instanceof Bishop)
        {
            System.out.println("Ben in promotion als Bishop gezien");
            Section section = this.getSection();
            Bishop bishop = new Bishop(this.getColor(), this.getPlayer(), section.getBoard().getSections((int) prevX, (int) prevY));
            this.getPlayer().getPieces().add(bishop);
            bishop.getPlayer().getPieces().remove(this);
            bishop.moveWithoutCheck(section);
        } else if (piece instanceof Knight)
        {
            Section section = this.getSection();
            Knight knight = new Knight(this.getColor(), this.getPlayer(), section.getBoard().getSections((int) prevX, (int) prevY));
            this.getPlayer().getPieces().add(knight);
            knight.getPlayer().getPieces().remove(this);
            knight.moveWithoutCheck(section);
        } else if (piece instanceof Queen)
        {
            Section section = this.getSection();
            Queen queen = new Queen(this.getColor(), this.getPlayer(), section.getBoard().getSections((int) prevX, (int) prevY));
            this.getPlayer().getPieces().add(queen);
            queen.getPlayer().getPieces().remove(this);
            queen.moveWithoutCheck(section);
        }else if(piece instanceof Rook){
            Section section = this.getSection();
            Rook rook = new Rook(this.getColor(), this.getPlayer(), section.getBoard().getSections((int) prevX, (int) prevY));
            this.getPlayer().getPieces().add(rook);
            rook.getPlayer().getPieces().remove(this);
            rook.moveWithoutCheck(section);
        }
    }

    /**
     *
     * @param p_section
     * @return
     */
    public boolean Promotion(Section p_section)
    {
        if (this.getColor().equals("white") )
        {
            if (p_section.getID().y == 7)
            {
                return true;
            }
        } else if (p_section.getID().y == 0)
        {
            return true;
        }
        return false;
    }

    /**
     * Checked of schuin voor de pion is geclicked. En of daar een pion staat
     * van de tegenstander.Zo niet kijkt hij of er onder de geclickte locatie
     * een pion staat van de tegenstander
     *
     * @param p_section
     * @param board
     * @return
     */
    public boolean toCaptureWhite(Section p_section, Board board)
    {
        Section Leftsection = null;
        Section Rightsection = null;
        try
        {
            Leftsection = getSection().getBoard().getSections(getSection().getID().x - 1, getSection().getID().y);
            Rightsection = getSection().getBoard().getSections(getSection().getID().x + 1, getSection().getID().y);
        } catch (ArrayIndexOutOfBoundsException ex)
        {
            getLOGGER().log(Level.FINE, ex.getMessage(), ex);
        }
        if (p_section.getID().x == this.getSection().getID().x - 1 && p_section.getID().y == this.getSection().getID().y + 1)
        {

            if (p_section.isOccupied())
            {
                if (isValidMove(p_section))
                {
                    return true;
                }
            } else if (Leftsection != null)
            {
                if (Leftsection.isOccupied())
                {
                    if (isValidMove(Leftsection))
                    {
                        if (moveEnPassant(Leftsection))
                        {
                            return true;
                        }
                    }
                }
            }

        } else if (p_section.getID().x == this.getSection().getID().x + 1 && p_section.getID().y == this.getSection().getID().y + 1)
        {
            if (p_section.isOccupied())
            {
                if (isValidMove(p_section))
                {
                    return true;
                }
            } else if (Rightsection != null)
            {
                if (Rightsection.isOccupied())
                {
                    if (isValidMove(Rightsection))
                    {
                        if (moveEnPassant(Rightsection))
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
     *
     * @param p_section
     * @param board
     * @return
     */
    public boolean moveOneTileForwardWhite(Section p_section, Board board)
    {
        {
            if (p_section.getID().x == this.getSection().getID().x && p_section.getID().y == this.getSection().getID().y + 1)
            {
                if (board.getSections(p_section.getID().x, p_section.getID().y).isOccupied())
                {
                    return false;
                } else
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param p_section
     * @param board
     * @return
     */
    public boolean moveTwoTilesForwardWhite(Section p_section, Board board)
    {
        if (isHasMoved() == false)
        {
            if (p_section.getID().x == this.getSection().getID().x && p_section.getID().y == this.getSection().getID().y + 2)
            {
                if (board.getSections(p_section.getID().x, p_section.getID().y - 1).isOccupied())
                {
                    return false;
                } else if (board.getSections(p_section.getID().x, p_section.getID().y).isOccupied())
                {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param p_section
     * @param board
     * @return
     */
    public boolean moveTwoTilesForwardBlack(Section p_section, Board board)
    {
        if (isHasMoved() == false)
        {
            if (p_section.getID().x == this.getSection().getID().x && p_section.getID().y == this.getSection().getID().y - 2)
            {
                if (board.getSections(p_section.getID().x, p_section.getID().y + 1).isOccupied())
                {
                    return false;
                } else if (p_section.isOccupied())
                {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param p_section
     * @param board
     * @return
     */
    public boolean moveOneTileForwardBlack(Section p_section, Board board)
    {
        {
            if (p_section.getID().x == this.getSection().getID().x && p_section.getID().y == this.getSection().getID().y - 1)
            {
                if (board.getSections(p_section.getID().x, p_section.getID().y).isOccupied())
                {
                    return false;
                } else
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checked of schuin voor de pion is geclicked. En of daar een pion staat
     * van de tegenstander.Zo niet kijkt hij of er onder de geclickte locatie
     * een pion staat van de tegenstander
     *
     * @param p_section
     * @param board
     * @return
     */
    public boolean toCaptureBlack(Section p_section, Board board)
    {
        Section Leftsection = null;
        Section Rightsection = null;
        try
        {
            Leftsection = getSection().getBoard().getSections(getSection().getID().x - 1, getSection().getID().y);
            Rightsection = getSection().getBoard().getSections(getSection().getID().x + 1, getSection().getID().y);
        } catch (ArrayIndexOutOfBoundsException ex)
        {
            getLOGGER().log(Level.FINE, ex.getMessage(), ex);
        }
        if (p_section.getID().x == this.getSection().getID().x + 1 && p_section.getID().y == this.getSection().getID().y - 1)
        {
            if (p_section.isOccupied())
            {
                if (isValidMove(board.getSections(this.getSection().getID().x + 1, this.getSection().getID().y - 1)))
                {
                    return true;
                }
            } else if (Rightsection != null)
            {
                if (Rightsection.isOccupied())
                {
                    if (isValidMove(Rightsection))
                    {
                        if (moveEnPassant(Rightsection))
                        {
                            return true;
                        }
                    }
                }
            }
        } else if (p_section.getID().x == this.getSection().getID().x - 1 && p_section.getID().y == this.getSection().getID().y - 1)
        {
            if (p_section.isOccupied())
            {

                if (isValidMove(board.getSections(this.getSection().getID().x - 1, this.getSection().getID().y - 1)))
                {
                    return true;
                }
            } else if (Leftsection != null)
            {
                if (Leftsection.isOccupied())
                {
                    if (isValidMove(Leftsection))
                    {
                        if (moveEnPassant(Leftsection))
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
     *
     * @param p_section Deze methode verwijdert een pawn van een section wanneer
     * de prevY op de start positie was.
     * @return 
     *
     */
    public boolean moveEnPassant(Section p_section)
    {
        if (p_section.getPiece() instanceof Pawn)
        {
            Pawn pawn = (Pawn) p_section.getPiece();
            if (pawn.getColor() == "black")
            {

                if ((int) pawn.getPrevSectionY() == 6)
                {
                    p_section.getBoard().ClearSection(p_section);
                    return true;
                }
            } else if (pawn.getColor() == "white")
            {
                if ((int) pawn.getPrevSectionY() == 1)
                {
                    p_section.getBoard().ClearSection(p_section);
                    return true;
                }

            }

        }
        return false;
    }

    /**
     * set new previoussection
     */
    public void resetThePrevSection(){
        if(this.getSection()!= null){
        this.prevSection = this.getSection().getBoard().getSections((int)this.prevX,(int)this.prevY);
        }}

    /**
     *
     * @return
     */
    public double getPrevX()
    {
        return prevX;
    }

    /**
     *
     * @return
     */
    public double getPrevY()
    {
        return prevY;
    }

}
