/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.Point;
import onlineschaken.RMIClient;

/**
 *
 * @author Sander
 */
public class ClientApp
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        RMIClient client = new RMIClient();
        client.sendTurn(new Point(3, 3), new Point(3, 4), "3000");
    }
    
}
