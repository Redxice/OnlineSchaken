/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.util.List;
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
public class ChatfilterTest
{
    
    public ChatfilterTest()
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
     * Test of checkMessage method, of class Chatfilter.
     */
    @Test
    public void testCheckGoodMessage()
    {
        System.out.println("CheckGoodMessage");
        String message = "hallo";
        Chatfilter instance = new Chatfilter();
        String expResult = "hallo";
        String result = instance.checkMessage(message);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkMessage method, of class Chatfilter.
     */
    @Test
    public void testCheckBadMessage()
    {
        System.out.println("CheckBadMessage");
        String message = "zak";
        Chatfilter instance = new Chatfilter();
        String expResult = "****";
        String result = instance.checkMessage(message);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of readBannedWords method, of class Chatfilter.
     */
    @Test
    public void testReadBannedWords()
    {
        System.out.println("readBannedWords");
        Chatfilter instance = new Chatfilter();
        instance.readBannedWords();
        String expResult = "lul";
        String result = instance.getBannedWords().get(0);
        assertEquals(expResult, result);
        
        expResult = "kloot";
        result = instance.getBannedWords().get(121);
        assertEquals(expResult, result);
        
        expResult = "zanik";
        result = instance.getBannedWords().get(152);
        assertEquals(expResult, result);
    }
    
}
