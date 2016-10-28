/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;

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

    boolean hasMoved;
    private Section prevSection;
    private double prevX;
    private double prevY;

    public Pawn(String p_color, Player p_player, Section p_section)
    {
        super(p_color, p_player, p_section);
        if (p_color == "white")
        {
            this.img = new Image("ChessPieces/White Pawn.jpg");
        } else if (p_color == "black")
        {
            this.img = new Image("ChessPieces/Black Pawn.jpg");
        }
        this.hasMoved = false;
    }

    public void setPrevSection(Section section)
    {
        this.prevX = section.id.x;
        this.prevY = section.id.y;
        this.prevSection = section;
    }

    public double getPrevSectionX()
    {
        return prevX;
    }
      public double getPrevSectionY()
    {
        return prevY;
    }
    public Section getPrevSection(){
        return this.prevSection;
    }

    public boolean getFirsMove()
    {
        return this.hasMoved;
    }
    @Override
    public Boolean checkMove(Section p_section)
    {
        Section prevsection = (Section)section;
        Board board = p_section.getBoard();
        if (isValidMove(p_section) == false)
        {
            return false;
        } else if (hasMoved == false)
        {
            if (this.color == "black")
            {
                //1 section naar voren.
                if (this.moveOneTileForwardBlack(p_section, board))
                {   this.setPrevSection(prevsection);
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
            if (this.color == "white")
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
        } else if (hasMoved == true)
        {
            if (this.color == "black")
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

            } else if (this.color == "white")
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

    public Popup menu()
    {
        Popup menu = new Popup();
        Pawn pawn = this;
        Button Bishop = new Button(type.Bishop.name());
        Button Knight = new Button(type.Knight.name());
        Button Queen = new Button(type.Queen.name());
        Button Rook = new Button(type.Rook.name());

        Bishop.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                Section section = pawn.getSection();
                Bishop bishop = new Bishop(pawn.getColor(), pawn.player, pawn.getPrevSection());
                pawn.player.getPieces().add(bishop);
                bishop.player.getPieces().remove(pawn);
                section.setPiece(bishop);
                bishop.moveWithoutCheck(section);
                menu.hide();
            }
        });
        Knight.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                Section section = pawn.getSection();
                Knight knight = new Knight(pawn.getColor(), pawn.player, pawn.getPrevSection());
                pawn.player.getPieces().add(knight);
                knight.player.getPieces().remove(pawn);
                section.setPiece(knight);
                knight.moveWithoutCheck(section);
                menu.hide();
            }
        });
        Queen.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                Section section = pawn.getSection();
                Queen queen = new Queen(pawn.getColor(), pawn.player, pawn.getPrevSection());
                pawn.player.getPieces().add(queen);
                queen.player.getPieces().remove(pawn);
                section.setPiece(queen);
                queen.moveWithoutCheck(section);
                menu.hide();
            }
        });
        Rook.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                Section section = pawn.getSection();
                Rook rook = new Rook(pawn.getColor(), pawn.player, pawn.getPrevSection());
                pawn.player.getPieces().add(rook);
                rook.player.getPieces().remove(pawn);
                section.setPiece(rook);
                rook.moveWithoutCheck(section);
                menu.hide();
            }
        });
        HBox box = new HBox(5);
        box.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
        box.getChildren().addAll(Bishop, Knight, Queen, Rook);
        menu.getContent().add(box);

        return menu;
    }

    public boolean Promotion(Section p_section)
    {
        if (this.color == "white")
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

    public boolean toCaptureWhite(Section p_section, Board board)
    {       Section Leftsection=null;
            Section Rightsection=null;
        try{
         Leftsection = section.getBoard().getSections(section.id.x-1, section.id.y);
         Rightsection = section.getBoard().getSections(section.id.x+1, section.id.y);
        }
        catch(ArrayIndexOutOfBoundsException ex){
            
        }
        if (p_section.getID().x == this.section.getID().x - 1 && p_section.getID().y == this.section.getID().y + 1)
        {
            
                if (p_section.isOccupied())
                {
                    if (isValidMove(p_section))
                    {
                        return true;
                    }
                }
                else if (Leftsection.isOccupied())
            {
                if (isValidMove(Leftsection))
                {
                    moveEnPassant(Leftsection);
                    return true;
                }
  
            }
              
            }  
        
        else if (p_section.getID().x == this.section.getID().x + 1 && p_section.getID().y == this.section.getID().y + 1)
        {
             if (p_section.isOccupied())
                {
                    if (isValidMove(p_section))
                    {
                        return true;
                    }
                }
        
                else if (Rightsection.isOccupied())
            {
                if(isValidMove(Rightsection))
                {    
                    moveEnPassant(Rightsection);
                    return true;
                }
            }
            
        }
        return false;
    }

    public boolean moveOneTileForwardWhite(Section p_section, Board board)
    {
        {
            if (p_section.getID().x == this.section.getID().x && p_section.getID().y == this.section.getID().y + 1)
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

    public boolean moveTwoTilesForwardWhite(Section p_section, Board board)
    {
        if (hasMoved == false)
        {
            if (p_section.getID().x == this.section.getID().x && p_section.getID().y == this.section.getID().y + 2)
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

    public boolean moveTwoTilesForwardBlack(Section p_section, Board board)
    {
        if (hasMoved == false)
        {
            if (p_section.getID().x == this.section.getID().x && p_section.getID().y == this.section.getID().y - 2)
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

    public boolean moveOneTileForwardBlack(Section p_section, Board board)
    {
        {
            if (p_section.getID().x == this.section.getID().x && p_section.getID().y == this.section.getID().y - 1)
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

    public boolean toCaptureBlack(Section p_section, Board board)
    {   
        Section Leftsection=null;
            Section Rightsection=null;
        try{
         Leftsection = section.getBoard().getSections(section.id.x-1, section.id.y);
         Rightsection = section.getBoard().getSections(section.id.x+1, section.id.y);
        }
        catch(ArrayIndexOutOfBoundsException ex){
            
        }
            if (p_section.getID().x == this.section.getID().x + 1 && p_section.getID().y == this.section.getID().y - 1)
            {
                if (board.getSections(p_section.getID().x, p_section.getID().y).isOccupied())
                {
                    if (isValidMove(board.getSections(this.section.getID().x + 1, this.section.getID().y - 1)))
                    {
                        return true;
                    }
                }
                else if (Rightsection.isOccupied())
                {
                    if (isValidMove(Rightsection))
                    {
                        moveEnPassant(Rightsection);
                        return true;
                    }
                }
            } else if (board.getSections(p_section.getID().x, p_section.getID().y).isOccupied())
            {

                if (isValidMove(board.getSections(this.section.getID().x - 1, this.section.getID().y - 1)))
                {
                    return true;
                }
                else if (Leftsection.isOccupied())
                {
                    if (isValidMove(Rightsection))
                    {
                        
                    }
                }
            }
        return false;
    }
    /**
     * 
     * @param p_section 
     * Deze methode verwijdert een pawn van een section wanneer de prev state op de start positie was.
     * 
     */
    
  public boolean moveEnPassant(Section p_section){
      if (p_section.getPiece() instanceof Pawn)
      {  Pawn pawn = (Pawn) p_section.getPiece();
      System.out.println(String.valueOf(pawn.getPrevSectionY()));
          if (pawn.color== "black")
          {
              if (pawn.getPrevSectionY() == 6)
              {
                   p_section.getBoard().ClearSection(p_section);
              }
          }
          else if(pawn.color =="white"){
              if (pawn.getPrevSectionY() == 1)
              {
                 p_section.getBoard().ClearSection(p_section);  
              }
              
          }
      }
      return false;
  } 
}
