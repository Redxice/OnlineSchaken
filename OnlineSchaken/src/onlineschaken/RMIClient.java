/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import Shared.IGameLobbyController;
import Shared.ILobbyController;
import Shared.IrmiClient;
import Shared.IrmiServer;
import gui.LobbyController;
import java.awt.Point;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 *
 * @author Sander
 */
public class RMIClient implements IrmiClient
{
    private ArrayList<ILobbyController> LobbyControllers= new ArrayList<>();
 
    @Override
    public void getTurn(Point section1, Point section2, double time) throws RemoteException
    {
        System.out.println(section1.toString() + " " + section2.toString() + " " + time);
    }

    public void sendTurn(Point section1, Point section2, String time)
    {
        try
        {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1"/*"169.254.183.180"*/, 666);
            IrmiServer stub;
            try
            {
                stub = (IrmiServer) registry.lookup("Servers");
                stub.doTurn(section1, section2, 3000);
            } catch (NotBoundException e)
            {
                System.err.println("Client exception:" + e.toString());
                e.printStackTrace();
            }
        } catch (RemoteException e)
        {
            System.err.println("Server exception:" + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void addController(ILobbyController controller) throws RemoteException
    {  
        System.out.println("Controler added"+controller);
        this.LobbyControllers.add(controller);
    }

    @Override
    public ArrayList<ILobbyController> GetLobbyControllers() throws RemoteException
    {
       return this.LobbyControllers;
    }

    @Override
    public void UpdateLobbyController() throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLobbyController(ILobbyController controller) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUserName() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setUserName(String userName) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IGameLobbyController getGameLobbyController() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setGameLobbyController(IGameLobbyController controller) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateChat() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ILobbyController getLobbyController() throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
