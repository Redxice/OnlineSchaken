/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import Shared.IinGameController;
import Shared.IrmiClient;
import java.rmi.RemoteException;
import java.util.TimerTask;

/**
 *
 * @author Sander
 */
public class TurnTimer extends TimerTask
{

    private IinGameController controller;
    private IrmiClient client;

    public TurnTimer(IinGameController controller, IrmiClient client)
    {
        this.controller = controller;
        this.client = client;
    }

    @Override
    public void run()
    {
        try
        {
            System.out.println("1-" + client.getLastMove());
            System.out.println("2-" + controller.getLocalLastMove());
            System.out.println("3-" + controller.getLocalLastMove().get(0));
            System.out.println("4-" + controller.getLocalLastMove().get(0));
            System.out.println("5-" + controller.getLocalLastMove().get(1));
            System.out.println("6-" + controller.getLocalLastMove().get(1));
            if (client.getLastMove() != null && controller.getLocalLastMove() != null)
            {
                if (client.getLastMove().get(0) != controller.getLocalLastMove().get(0))
                {
                    if (client.getLastMove().get(1) != controller.getLocalLastMove().get(1))
                    {
                      controller.move(controller.getLocalLastMove().get(0), controller.getLocalLastMove().get(1), 4.00);
                    }
                }
            }
            controller.getLocalLastMove();
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("timer");
        }
    }
}
