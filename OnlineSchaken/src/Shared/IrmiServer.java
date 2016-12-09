/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import onlineschaken.Chatline;
import onlineschaken.Gamelobby;
import onlineschaken.Player;

/**
 *
 * @author Sander
 */
public interface IrmiServer extends Remote
{
    public void doTurn(Point section1, Point section2, double time) throws RemoteException;
    
    public void test() throws RemoteException;
    
    public List<IGameLobby> GameLobbys()throws RemoteException;
    
    public String GetIGameLobby(Gamelobby gamelobby)throws RemoteException;
    
    public void CreateGameLobby(String naam, Player player1)throws RemoteException;
   
    public ArrayList<String> GetGameLobbys()throws RemoteException;
    
    public void SendMessage(Chatline message,String naamLobby)throws RemoteException;
    
    public void playerReady(boolean ready,String naamLobby, String userName)throws RemoteException;
}
