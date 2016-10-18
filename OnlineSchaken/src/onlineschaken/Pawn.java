/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
            this.img = new Image("ChessPieces/White Pawn.png");
        } else if (p_color == "black")
        {
            this.img = new Image("ChessPieces/Black Pawn.png");
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
        Button show = new Button("Test");
        menu.getContent().addAll(show);
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

