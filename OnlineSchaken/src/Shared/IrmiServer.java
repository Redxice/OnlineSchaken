/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Sander
 */
public interface IrmiServer extends Remote
{
    public void doTurn(Point section1, Point section2, double time) throws RemoteException;
    
    public void test() throws RemoteException;
    
}
