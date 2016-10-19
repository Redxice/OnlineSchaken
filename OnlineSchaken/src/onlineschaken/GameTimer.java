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
{

    private Game game;
    private Board board;
    private OnlineSchaken javaFX;
    public GameTimer(Game game, Board board, OnlineSchaken javaFX)
    {
        this.game = game;
        this.board = board;
        this.javaFX = javaFX;
    }

    @Override
    public void run()
    {
        if (game.isFinished() != true)
        {
            if (board.getTurn().equals("white"))
            {
                game.setResterend1(1);
                javaFX.update();
                
            } else if(board.getTurn().equals("black"))
            {
                game.setResterend2(1);
                javaFX.update();
            }
            
        }
    }

}
