/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sander
 */
public class ChatlineTest
{
    
    public ChatlineTest()
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
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of getUserName method, of class Chatline.
     */
    @Test
    public void testGetUserName()
    {
        System.out.println("getUserName");
        Chatline instance = new Chatline("testUser","testMessage");
        String expResult = "testUser";
        String result = instance.getUserName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMessage method, of class Chatline.
     */
    @Test
    public void testGetMessage()
    {
        System.out.println("getMessage");
        Chatline instance = new Chatline("testUser","testMessage");
        String expResult = "testMessage";
        String result = instance.getMessage();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Chatline.
     */
    @Test
    public void testToString()
    {
        System.out.println("toString");
        Chatline instance = new Chatline("testUser","testMessage");
        String expResult = "testUser: testMessage";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
