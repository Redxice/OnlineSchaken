/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.Itest;
import java.rmi.RemoteException;

/**
 *
 * @author Sander
 */
public class test implements Itest
{

    @Override
    public void waarde() throws RemoteException
    {
        System.out.println("works");
    }
    
}
