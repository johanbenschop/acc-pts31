/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import atc.interfaces.IAirspace;
import atc.interfaces.IFlightplan;
import java.rmi.RemoteException;
import java.util.Iterator;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Mateusz
 */
public class FlightControllerTest {
    
    public FlightControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws RemoteException {
    }

    @AfterClass
    public static void tearDownClass() throws RemoteException {
    }

    @Before
    public void setUp() throws RemoteException {
    }

    @After
    public void tearDown() throws RemoteException {
    }

    /**
     * Test of getAirspace method, of class FlightController.
     */
    @Test
    public void testGetAirspace() throws RemoteException {
        System.out.println("getAirspace");
        FlightController instance = new FlightController();
        IAirspace expResult = null;
        IAirspace result = instance.getAirspace();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of assignFlight method, of class FlightController.
     */
    @Test
    public void testAssignFlight() throws RemoteException {
        System.out.println("assignFlight");
        IFlightplan flightplan = null;
        FlightController instance = new FlightController();
        instance.assignFlight(flightplan);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unassignFlight method, of class FlightController.
     */
    @Test
    public void testUnassignFlight() throws RemoteException {
        System.out.println("unassignFlight");
        IFlightplan flightplan = null;
        FlightController instance = new FlightController();
        instance.unassignFlight(flightplan);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unassignAllFlights method, of class FlightController.
     */
    @Test
    public void testUnassignAllFlights() throws RemoteException {
        System.out.println("unassignAllFlights");
        FlightController instance = new FlightController();
        instance.unassignAllFlights();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumberAssignedFlights method, of class FlightController.
     */
    @Test
    public void testGetNumberAssignedFlights() throws RemoteException {
        System.out.println("getNumberAssignedFlights");
        FlightController instance = new FlightController();
        int expResult = 0;
        int result = instance.getNumberAssignedFlights();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class FlightController.
     */
    @Test
    public void testGetID() throws RemoteException {
        System.out.println("getID");
        FlightController instance = new FlightController();
        int expResult = 0;
        int result = instance.getID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFlights method, of class FlightController.
     */
    @Test
    public void testGetFlights() throws RemoteException {
        System.out.println("getFlights");
        FlightController instance = new FlightController();
        Iterator expResult = null;
        Iterator result = instance.getFlights();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
