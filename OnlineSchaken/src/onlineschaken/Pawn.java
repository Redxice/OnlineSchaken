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
{   private enum type{
    Knight,
    Bishop,
    Queen,
    Rook
}

    boolean hasMoved;

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

    public void promotion()
    {
    }

    public boolean getFirsMove()
    {
        return this.hasMoved;
    }
    public Popup menu(){
        Popup menu = new Popup();
         Piece pawn = this;
        Button Bishop = new Button(type.Bishop.name());
        Button Knight = new Button(type.Knight.name());
        Button Queen = new Button(type.Queen.name());
        Button Rook = new Button(type.Rook.name());
        if (this.getColor()=="white")
        {
             Bishop.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
         Section section = pawn.getSection();
         Bishop bishop = new Bishop(pawn.getColor(),pawn.player,pawn.getSection());
         pawn.setSection(null);
         section.setPiece(bishop);
         bishop.moveWithoutCheck(section);
         menu.hide();
      }
    });
        }
       
        HBox box = new HBox(5);
       box.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
       box.getChildren().addAll(Bishop,Knight,Queen,Rook);
        menu.getContent().add(box);
        
       
       return menu;
    }
    public boolean Promotion(Section p_section){
      if(this.color=="white"){
          if (p_section.getID().y==7)
          {
              return true;
          }
      }
      else{
          if(p_section.getID().y==0){
              return true;
          }
      }
      return false;
    }
    public boolean toCaptureWhite(Section p_section,Board board){
       if (p_section.getID().x == this.section.getID().x - 1 && p_section.getID().y == this.section.getID().y + 1
                        || p_section.getID().x == this.section.getID().x + 1 && p_section.getID().y == this.section.getID().y + 1)
                {
                    if (p_section.getID().x == this.section.getID().x + 1 && p_section.getID().y == this.section.getID().y + 1)
                    {
                      if(board.getSections(p_section.getID().x, p_section.getID().y).isOccupied()){
                        if (isValidMove(p_section))
                        {
                            return true;
                        }
                      } 
                    } else if(p_section.getID().x == this.section.getID().x - 1 && p_section.getID().y == this.section.getID().y + 1){
                    if(board.getSections(p_section.getID().x, p_section.getID().y).isOccupied()){
                        if (isValidMove(p_section))
                        {
                            return true;
                        }
                    }
                    }
    }
   return false;
    }
    public boolean moveOneTileForwardWhite(Section p_section,Board board){
                {if(p_section.getID().x == this.section.getID().x && p_section.getID().y == this.section.getID().y + 1){
                    if (board.getSections(p_section.getID().x,p_section.getID().y).isOccupied())
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
    
    public boolean moveTwoTilesForwardWhite(Section p_section,Board board){
       if (hasMoved==false)
        {
           if(p_section.getID().x == this.section.getID().x && p_section.getID().y == this.section.getID().y + 2){
               if (board.getSections(p_section.getID().x,p_section.getID().y-1).isOccupied())
                    {
                        return false;
                    } 
               else if(board.getSections(p_section.getID().x,p_section.getID().y).isOccupied())
                    {
                        return false;
                    }
               return true;
           }
        }
        return false;  
    }
    public boolean moveTwoTilesForwardBlack(Section p_section,Board board){
        if (hasMoved==false)
        {
           if(p_section.getID().x == this.section.getID().x && p_section.getID().y == this.section.getID().y - 2){
               if (board.getSections(p_section.getID().x,p_section.getID().y+1).isOccupied())
                    {
                        return false;
                    } 
               else if(board.getSections(p_section.getID().x,p_section.getID().y).isOccupied())
                    {
                        return false;
                    }
               return true;
           }
        }
        return false;
    }
    
    public boolean moveOneTileForwardBlack(Section p_section,Board board){
                {if(p_section.getID().x == this.section.getID().x && p_section.getID().y == this.section.getID().y - 1){
                    if (board.getSections(p_section.getID().x,p_section.getID().y).isOccupied())
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
    
    public boolean toCaptureBlack(Section p_section,Board board){
       if (p_section.getID().x == this.section.getID().x + 1 && p_section.getID().y == this.section.getID().y - 1
                        || p_section.getID().x == this.section.getID().x - 1 && p_section.getID().y == this.section.getID().y - 1)
                {
                    if (p_section.getID().x == this.section.getID().x + 1 && p_section.getID().y == this.section.getID().y - 1)
                    {
                      if(board.getSections(p_section.getID().x, p_section.getID().y).isOccupied()){
                        if (isValidMove(board.getSections(this.section.getID().x + 1, this.section.getID().y - 1)))
                        {
                            return true;
                        }
                      } 
                    } else if(board.getSections(p_section.getID().x, p_section.getID().y).isOccupied()){
                    
                        if (isValidMove(board.getSections(this.section.getID().x - 1, this.section.getID().y - 1)))
                        {
                            return true;
                        }
                    }
    }
   return false;
    }
    
    
    @Override
    public Boolean checkMove(Section p_section)
    {

        Board board = p_section.getBoard();
        if (isValidMove(p_section) == false)
        {
            return false;
        } else if (hasMoved == false)
        {
            if (this.color == "black")
            {
                //1 section naar voren.
                if (this.moveOneTileForwardBlack(p_section,board))
                {
                    this.hasMoved= true;
                    return true;
                }
                  //2 section naar voren
                else if(this.moveTwoTilesForwardBlack(p_section, board)){
                    this.hasMoved= true;
                    return true;
                }
                //schuin slaan van andere piece.
                else if(this.toCaptureBlack(p_section, board)){
                    this.hasMoved= true;
                    return true;
                }
            }
            if (this.color == "white")
            {
                //1 section naar voren.
                if (this.moveOneTileForwardWhite(p_section, board))
                {
                    this.hasMoved= true;
                    return true;
                } //2 section naar voren
                else if(this.moveTwoTilesForwardWhite(p_section, board)){
                    this.hasMoved= true;
                    return true;
                }
               //schuin slaan van andere pawn.
                else if (this.toCaptureWhite(p_section, board))
                {
                    this.hasMoved= true;
                    return true;
                }
                    }
        }else if (hasMoved == true)
        {

            if (this.color == "black")
            {
                //1 section naar voren.
                if (this.moveOneTileForwardBlack(p_section, board)){
                    return true;
                }
                //schuin slaan van andere pawn.
                else if (this.toCaptureBlack(p_section, board)){
                    return true;     
                }

            } else if (this.color == "white")
            {
                //1 section naar voren.
                if(this.moveOneTileForwardWhite(p_section, board)){
                    return true;
                }
                 //schuin slaan van andere pawn.
                else if (this.toCaptureWhite(p_section, board))
                {
                   return true;
                }
        }
    }
        return false;
  }
}

