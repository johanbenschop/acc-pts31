/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author johan
 */
public class AirportTest {
    
    public AirportTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getRunway method, of class Airport.
     */
    @Test
    public void testGetRunway() {
        System.out.println("getRunway");
        Airport instance = new Airport();
        instance.getRunway();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of CreateActiveAirplane method, of class Airport.
     */
    @Test
    public void testCreateActiveAirplane() {
        System.out.println("CreateActiveAirplane");
        Airport instance = new Airport();
        instance.CreateActiveAirplane();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
