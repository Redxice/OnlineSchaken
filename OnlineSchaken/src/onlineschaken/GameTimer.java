/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.util.TimerTask;

/**
 *
 * @author redxice
 */
public class GameTimer extends TimerTask
{  private Player player1;
   private Player player2;
   public GameTimer (Player player1,Player player2){
       this.player1 = player1;
       this.player2 = player2;
   }

    @Override
    public void run()
    {
      
    }
    
}
