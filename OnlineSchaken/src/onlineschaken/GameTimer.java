/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.rmi.RemoteException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author redxice
 */
public class GameTimer extends TimerTask
{

    private Game game;
    private Board board;

    /**
     *
     * @param game
     * @param board
     */
    public GameTimer(Game game, Board board)
    {
        this.game = game;
        this.board = board;
    }

    /**
     * run method
     */
    @Override
    public void run()
    {
        if (game.isFinished() != true)
        {
            try
            {
                if (board.getClient().getGameLobbyController().getLogInUser().getColor().equals("white"))
                {
                    if (board.getClient().GetGameController().getMyTurn())
                    {
                        game.setResterend2(1);
                        game.update();
                    } else
                    {
                        game.setResterend1(1);
                        game.update();
                    }
                } else if (board.getClient().getGameLobbyController().getLogInUser().getColor().equals("black"))
                {
                    if (board.getClient().GetGameController().getMyTurn())
                    {
                        game.setResterend1(1);
                        game.update();
                    } else
                    {
                        game.setResterend2(1);
                        game.update();
                    }
                }
            } catch (RemoteException ex)
            {
                Logger.getLogger(GameTimer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
