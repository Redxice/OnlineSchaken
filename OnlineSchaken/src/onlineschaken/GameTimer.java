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
{  private Game game;
   public GameTimer (Game game){
      this.game = game;
   }

    @Override
    public void run()
    {if(game.isFinished()!= true){
        if (game.whiteTurn)
        {
            game.setResterend1(1);
        }
        else{
            game.setResterend2(1);
        }
    }
    }
    
}
