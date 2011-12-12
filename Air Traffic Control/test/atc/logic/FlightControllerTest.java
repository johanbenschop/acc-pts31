/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robbert
 */
public class FlightControllerTest {
    
    public FlightControllerTest() {
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
     * Test of controllingFlights method, of class FlightController.
     */
    @Test
    public void testControllingFlights() {
        System.out.println("controllingFlights");
        FlightController instance = new FlightController();
        int expResult = 0;
       // int result = instance.controllingFlights();
    //    assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of assignFlight method, of class FlightController.
     */
    @Test
    public void testAssignFlight() {
        System.out.println("assignFlight");
        Flightplan flightplan = null;
        FlightController instance = new FlightController();
        instance.assignFlight(flightplan);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unassignFlight method, of class FlightController.
     */
    @Test
    public void testUnassignFlight() {
        System.out.println("unassignFlight");
        Flightplan flightplan = null;
        FlightController instance = new FlightController();
        instance.unassignFlight(flightplan);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
