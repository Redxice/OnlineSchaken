/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author redxice
 */
public interface ILobbyController
{
      /**
     * wordt aangeroepen wanneer er 
     * veranderingen zijn in de het aantal gamelobbys
     */
    public void UpdateGameLobbys(ArrayList<String> lobbys)throws RemoteException;
    
}