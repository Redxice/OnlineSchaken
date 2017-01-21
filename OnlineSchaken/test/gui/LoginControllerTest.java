/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Server.ClientApp;
import Server.RmiServer;
import Shared.IrmiServer;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author redxice
 */
public class LoginControllerTest
{

    private ClientApp client1 = null;
    private static  IrmiServer server = null;
    private LoginController controller = null;

    public LoginControllerTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
        
        
        try
        {
            server = new RmiServer();
            IrmiServer stub = (IrmiServer) UnicastRemoteObject.exportObject(server, 0);
            //Bind the remote object stub in the registry
            Registry registry = LocateRegistry.createRegistry(666);
            registry.bind("Server", stub);
            client1 = new ClientApp();
            controller = new LoginController();
        } catch (RemoteException ex)
        {
            Logger.getLogger(LoginControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AlreadyBoundException ex)
        {
            Logger.getLogger(LoginControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @After
    public void tearDown()
    {
        server=null;
        client1=null;
        controller=null;
        
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void TestSetClient()
    {
     controller.setClient(client1);
     ClientApp expResult = client1;
     ClientApp result = controller.getClient();
     assertEquals(result,expResult);
    }
    
}
