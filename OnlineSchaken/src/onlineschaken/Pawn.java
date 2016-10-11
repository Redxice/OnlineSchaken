/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 *
 * @author redxice
 */
public class Pawn extends Piece{
    boolean hasMoved;

    public Pawn(String p_color, Player p_player, Section p_section) {
        super(p_color, p_player, p_section);
        if (p_color == "white")
        {
        this.img = new Image("ChessPieces/White Pawn.png");
        }
        else if (p_color == "black")
        {
        this.img = new Image("ChessPieces/Black Pawn.png");
        }
        
    }
    
    public void promotion()
    {}
    
    public boolean getFirsMove(){
        return this.hasMoved;
    }

    @Override
    public Boolean move(Section p_section) {
        
       Board board = p_section.getBoard();
       if(isValidMove(p_section)==false){
           return false;
       }
      
       else if(hasMoved == false) {
         if(this.color =="black"){
          if(p_section.getID().x == this.section.getID().x && p_section.getID().y == this.section.getID().y+1){
          this.section.setPiece(null);
          p_section.setPiece(this);
          this.setSection(p_section);
          this.hasMoved =true;
          }
          else if(p_section.getID().x == this.section.getID().x+1 && p_section.getID().y == this.section.getID().y+1 ||
                  p_section.getID().x == this.section.getID().x-1 && p_section.getID().y == this.section.getID().y+1 ){
              if(p_section.getID().x == this.section.getID().x+1 && p_section.getID().y == this.section.getID().y+1){
               
              }
              else if()
          }
          else{
            for (int i = 1; i < 3; i++) {
               int x= this.section.getID().x+i;
               int y= this.section.getID().y+i;
              Section section = board.getSections(x, y);
              if(section.isOccupied()==true){
                 return false;
              }
            }
         }
          
        }
           if(this.color =="white"){
          if(p_section.getID().x == this.section.getID().x-1 && p_section.getID().y == this.section.getID().y-1){
          this.section.setPiece(null);
          p_section.setPiece(this);
          }
          else{
            for (int i = 1; i < 3; i++) {
               int x= this.section.getID().x-i;
               int y= this.section.getID().y-i;
              Section section = board.getSections(x, y);
            }
         }
        }
       else if (hasMoved == true) {
            
        }
       return true;
    }
}
