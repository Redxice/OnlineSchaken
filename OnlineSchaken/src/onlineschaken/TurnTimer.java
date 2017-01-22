/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import Shared.IinGameController;
import Shared.IrmiClient;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sander
 */
public class TurnTimer extends TimerTask
{

    private final IinGameController controller;
    private final IrmiClient client;

    /**
     *
     * @param controller
     * @param client
     */
    public TurnTimer(IinGameController controller, IrmiClient client)
    {
        this.controller = controller;
        this.client = client;
    }

    /**
     * get last move
     */
    @Override
    public void run()
    {
        try
        {

            if (client.getLastMove() != null && controller.getLocalLastMove() != null)
            {
                if (controller.getLocalLastMove().get(0) != null && controller.getLocalLastMove().get(1) != null)
                {
                    if (client.getLastMove().get(0) != controller.getLocalLastMove().get(0))
                    {
                        if (client.getLastMove().get(1) != controller.getLocalLastMove().get(1))
                        {
                            controller.move(controller.getLocalLastMove().get(0), controller.getLocalLastMove().get(1), 4.00);
                        }
                    }
                }
            }
            controller.getLocalLastMove();
        } catch (Exception e)
        {
            Logger.getLogger(TurnTimer.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
