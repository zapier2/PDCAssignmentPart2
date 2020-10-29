/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcproject;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Victor
 */
public class PlayerTest {
    
    private Player playerTest;
    
    public PlayerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
            playerTest = new Player();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setPlayerName method, of class Player.
     */
    @Test
    public void testSetPlayerName() {
        System.out.println("setPlayerName");
        String expResult = "Victor";
        playerTest.setPlayerName("Victor");
        String result = playerTest.getPlayerName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWinnings method, of class Player.
     */
    @Test
    public void testSetWinnings() {
        System.out.println("setWinnings");
        int expResult = 100;
        playerTest.setWinnings(100);
        int result = playerTest.getWinnings();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlayerName method, of class Player.
     */
    @Test
    public void testGetPlayerName() {
        System.out.println("getPlayerName");
        String expResult = "Victor";
        playerTest.playerName = "Victor";
        String result = playerTest.getPlayerName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWinnings method, of class Player.
     */
    @Test
    public void testGetWinnings() {
        System.out.println("getWinnings");
        int expResult = 100;
        playerTest.setWinnings(100);
        int result = playerTest.getWinnings();
        assertEquals(expResult, result);

    }

    /**
     * Test of Player method, of class Player.
     */
    @Test
    public void testPlayer() {
        System.out.println("Player class");
        String expResult_1 = "Victor";
        int expResult_2 = 100;
        Player instance = new Player();
        instance.Player("Victor", 100);
        String result_1 = instance.getPlayerName();
        int result_2 = instance.getWinnings();
        assertEquals(expResult_1, result_1);
        assertEquals(expResult_2, result_2);
        
    }
    
}
