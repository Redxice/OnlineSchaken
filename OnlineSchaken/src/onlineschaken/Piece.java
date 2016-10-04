/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.util.List;
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
    
    
//constructor
    public Piece(String p_color,Player p_player, Section p_section) {
        this.color = p_color;
        this.player = p_player;       
        this.section = p_section;
    }
  
 //methode
   public abstract void move(Section p_section);
    
   public Section getSection(){
       return this.section;
   }
   //geeft een lijst met opties
   public List<Section> moveOption(Section p_curSection){
       List<Section> sections =null;
      return sections;
   }
   
   
}
