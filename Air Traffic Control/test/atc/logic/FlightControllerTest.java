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
import java.util.ArrayList;
import java.util.Iterator;
import junit.framework.Assert;

/**
 *
 * @author Robbert
 */
public class FlightControllerTest {

    private Flightplan aFlightplan;
    private Flightplan bFlightplan;
    private Flightplan cFlightplan;
    private FlightController flightController;
    private Iterator flights;
    private boolean flightAssigned = false;
    private boolean flightUnassigned = true;
    private boolean allFlightsUnassigned = true;

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
        aFlightplan = new Flightplan(null, null, 1, null, null, null);
        cFlightplan = new Flightplan(null, null, 3, null, null, null);
        flightController = new FlightController();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of assignFlight method, of class FlightController.
     */
    @Test
    public void testAssignFlight() {
        System.out.println("assignFlight");
        flightController.assignFlight(aFlightplan);
        flights = flightController.getFlights();

        while (flights.hasNext()) {
            bFlightplan = new Flightplan(null, null, 0, null, null, null);
            bFlightplan = (Flightplan) flights.next();
            if (bFlightplan.equals(aFlightplan)) {
                flightAssigned = true;
            }
        }
        Assert.assertEquals("The flight has been assigned", true, flightAssigned);
    }

    /**
     * Test of unassignFlight method, of class FlightController.
     */
    @Test
    public void testUnassignFlight() {
        System.out.println("unassignFlight");
        flightController.assignFlight(aFlightplan);
        flightController.assignFlight(cFlightplan);
        flightController.unassignFlight(aFlightplan);

        flights = flightController.getFlights();
        while (flights.hasNext()) {
            bFlightplan = new Flightplan(null, null, 0, null, null, null);
            bFlightplan = (Flightplan) flights.next();
            if (bFlightplan.equals(aFlightplan)) {
                flightUnassigned = false;
            }
        }
        Assert.assertEquals("The flight has been unassigned", true, flightUnassigned);
    }

    /**
     * Test of controllingFlights method, of class FlightController.
     */
    @Test
    public void testUnassignAllFlights() {
        System.out.println("unassignAllFlights");
        flightController.assignFlight(aFlightplan);
        flightController.assignFlight(cFlightplan);
        flightController.unassignAllFlights();

        flights = flightController.getFlights();
        
        while (flights.hasNext()) {
            bFlightplan = new Flightplan(null, null, 0, null, null, null);
            bFlightplan = (Flightplan) flights.next();
            if (bFlightplan.getAssignedController() != null) {
                allFlightsUnassigned = false;
            }
        }
        Assert.assertEquals("The flight has been unassigned", true, allFlightsUnassigned);
    }
}
