/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.util.List;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

/**
 *
 * @author redxice
 */
public abstract class Piece extends StackPane{
    //fields
    String color;
    Player player;
    Section section;
    Image img;
    boolean hasMoved;
    
//constructor
    public Piece(String p_color,Player p_player, Section p_section) {
        this.color = p_color;
        this.player = p_player;       
        this.section = p_section;
        this.section.setPiece(this);
    }
    
 
   public boolean isValidMove(Section p_section){
       
       if (p_section.isOccupied() == true) {
           if (p_section.getPiece().getColor() != this.color) {
               return true;
           }
           else{
               return false;
           }
       }
       else{
           return true;
       }
   }
   
   
    public void setSection(Section section) {
        this.section = section;
        section.setPiece(this);
    }
   
   public  String getColor(){
       return this.color;
   }
 //methode
   public abstract Boolean checkMove(Section p_section);
    
   public void move(Section p_section)
   {
       try
       {
       if(checkMove(p_section))
       {
           
           if(section.getPiece() instanceof Rook && section.getPiece().hasMoved == false)
           {
               
               section.getBoard().drawSpecificPieces(this.getSection().getBoard().getSections()[3][3], section);
           }
           section.getBoard().drawSpecificPieces(section, p_section);
           section.setPiece(null);
           section.id.x = p_section.id.x;
           section.id.y = p_section.id.y;
           section.getBoard().getSections()[section.id.x][section.id.y].setPiece(this);
           this.section = section.getBoard().getSections()[section.id.x][section.id.y];
       }
       }
       catch(NullPointerException e)
       {
           //Logger.getLogger(e.getMessage());
           System.out.println(e.getMessage());
           System.out.println(section.getPiece());
           System.out.println(section.getBoard());
           System.out.println(p_section.getPiece());
       }
           hasMoved = true;      
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
       }
       catch(NullPointerException e)
       {
           //Logger.getLogger(e.getMessage());
           System.out.println(e.getMessage());
           System.out.println(section.getPiece());
           System.out.println(section.getBoard());
           System.out.println(p_section.getPiece());
       }
           hasMoved = true;      
   }
    
   public Section getSection(){
       return this.section;
   }
   //geeft een lijst met opties
   public List<Section> moveOption(Section p_curSection){
       List<Section> sections =null;
      return sections;
   }
}
