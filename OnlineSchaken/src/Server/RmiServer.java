/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.IGameLobby;
import java.awt.Point;
import java.rmi.RemoteException;
import Shared.IrmiServer;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import onlineschaken.Gamelobby;
import onlineschaken.Player;

/**
 *
 * @author Sander
 */
public class RmiServer implements IrmiServer
{  
    private ArrayList<String> GameLobbys = new ArrayList<>();
    
    @Override
    public void doTurn(Point section1, Point section2, double time) throws RemoteException
    {
        System.out.println(section1.toString() + section2.toString() + time);
    }

    @Override
    public void test() throws RemoteException
    {
        System.out.println("works");
    }

    @Override
    public List<IGameLobby> GameLobbys() throws RemoteException
    {
       return null; 
    }

/**
 * Deze Methode checked niet of de lobbyNaam & player null is.
 * 
 * @param lobbyNaam
 * @param player1
 * @return
 * @throws RemoteException 
 */
    @Override
    public void CreateGameLobby(String lobbyNaam, Player player1) throws RemoteException
    {   
        Gamelobby lobby = new Gamelobby(lobbyNaam,player1);
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 666);
        registry.rebind(lobby.getNaam(),(IGameLobby)lobby);
        this.GameLobbys.add(lobby.getName());
    }

    @Override
    public String GetIGameLobby(Gamelobby gamelobby) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> GetGameLobbys() throws RemoteException
    {
       return GameLobbys;
    }
    
}
