/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.util.List;

/**
 *
 * @author redxice
 */
public abstract class Piece{
    //fields
    String color;
    String name;
    Player player;
    Section section;
    
//constructor
    public Piece(String p_color,Player p_player,String p_name) {
        this.color = p_color;
        this.name = p_name;
        this.player = p_player;
    }
  
 //methode
   public void move(Section p_section){
       
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
