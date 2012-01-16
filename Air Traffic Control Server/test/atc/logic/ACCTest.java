/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import atc.interfaces.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Sabrina
 */
public class ACCTest {
    //private CTA cta; //cta Thats being used for the test
    private GeoLocation loc, loc2; // Location of the CTA
    //private ACC acc; //ACC thats being used for this test
    //private Airplane apa; //Airplane thats in flight
    //private Airplane apt; //Airplane thats ready for takeoff
    private Runway ra; //available runway
    private Runway ru; //unavailable runway
    private Flightplan fp; // flightplan
    private Airport airport1;
    private Airport airport2;
    private AirplaneFactory airplaneFactory;
    private ArrayList<Airport> airportList = new ArrayList<>();
    private ACC acc;
    private Airplane airplane;
    private Airspace airspace;
    private GregorianCalendar gc1;
    private GregorianCalendar gc2;
    private Flightplan flightPlan;
    private IFC fc;
    public ACCTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() throws Exception{
        loc = new GeoLocation(1,1,1);
        loc2 = new GeoLocation(2,2,2);
        //cta = new CTA(loc, 1,1);
        //acc = new ACC(1, cta);
        //apa = new Airplane(500, 300, 16000, "747-300", "Boeing", 300, 300, 500, 200, 1, 100, Double.parseDouble("299"), 100, Double.parseDouble("650"), loc, loc2, 1);
        //apt = new Airplane(500, 300, 1600, "747-300", "Boeing", 300, 300, 500, 200, 1, 100, Double.parseDouble("299"), 100, Double.parseDouble("640"), loc, loc2, 2);
        ra = new Runway(1,1,50, 300, 180, true);
        ru = new Runway(1,1,50,300, 270, false);
        airplane = new Airplane(600, 400, 10000, "", "", 10, 10, 10, 10000, 500, 0, 0, 10000, 0 , loc, loc2, 9999);
        acc = new ACC(2, null);
        airspace = new Airspace();
        airspace.setCurrentACC(acc);
        airport1 = new Airport(1,"bla", "bla", "bla", "bla", "bla",loc, 1000, 10.0, "EU");
        airport2 = new Airport(2,"bla", "bla", "bla", "bla", "bla",loc, 1000, 10.0, "EU");
        airplaneFactory = new AirplaneFactory(900, 500, 100, "bla" , "bla", 20, 20, 50, 10000, 500);
        gc1 = new GregorianCalendar();
        gc2 = new GregorianCalendar();
        fc = new IFC() {

            @Override
            public void assignFlight(IFlightplan flightplan) throws RemoteException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Iterator<IFlightplan> getFlights() throws RemoteException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int getID() throws RemoteException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int getNumberAssignedFlights() throws RemoteException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void unassignAllFlights() throws RemoteException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void unassignFlight(IFlightplan flightplan) throws RemoteException {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        
        acc.addFlightController(fc);
        flightPlan = new Flightplan(airport1, airport2, 5, gc1, gc2, airplane);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of GetID method, of class ACC.
     */
    @Test
    public void testGetID() {
        System.out.println("GetID");
        ACC instance = null;
        int expResult = 0;
        int result = instance.GetID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetCTA method, of class ACC.
     */
    @Test
    public void testGetCTA() {
        System.out.println("GetCTA");
        ACC instance = null;
        ICTA expResult = null;
        ICTA result = instance.GetCTA();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getfp method, of class ACC.
     */
    @Test
    public void testGetfp() {
        System.out.println("getfp");
        ACC instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getfp();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getfc method, of class ACC.
     */
    @Test
    public void testGetfc() {
        System.out.println("getfc");
        ACC instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getfc();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetFlightController method, of class ACC.
     */
    @Test
    public void testGetFlightController() throws Exception {
        System.out.println("GetFlightController");
        int FlightControllerID = 0;
        ACC instance = null;
        IFC expResult = null;
        IFC result = instance.GetFlightController(FlightControllerID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetAirplaneFactory method, of class ACC.
     */
    @Test
    public void testGetAirplaneFactory() throws Exception {
        System.out.println("GetAirplaneFactory");
        int AirplaneFactoryID = 0;
        ACC instance = null;
        IAirplaneFactory expResult = null;
        IAirplaneFactory result = instance.GetAirplaneFactory(AirplaneFactoryID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAvailableAirplanes method, of class ACC.
     */
    @Test
    public void testGetAvailableAirplanes() {
        System.out.println("getAvailableAirplanes");
        ACC instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getAvailableAirplanes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFlightControllers method, of class ACC.
     */
    @Test
    public void testGetFlightControllers() {
        System.out.println("getFlightControllers");
        ACC instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getFlightControllers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadAirplaneFactoryList method, of class ACC.
     */
    @Test
    public void testLoadAirplaneFactoryList() throws FileNotFoundException, IOException  {
        System.out.println("Load airportlist");
        try {
            acc.loadAirplaneFactoryList();
            junit.framework.Assert.assertEquals("Manufacturer should be Fokker", "Fokker", acc.GetAirplaneFactory(1).getManufacturer());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test of ChangeSpeed method, of class ACC.
     */
    @Test
    public void testChangeSpeed() throws AssignmentException, RemoteException {       
        System.out.println("ChangeSpeed");
        acc.ChangeSpeed(500.0, airplane);
        junit.framework.Assert.assertEquals("Current speed is expected to be changed to 500", 500.0, airplane.getAimedSpeed());
    }

    /**
     * Test of ChangeDirection method, of class ACC.
     */
    @Test
    public void testChangeDirection() throws AssignmentException, RemoteException  {
        System.out.println("ChangeDirection");
        acc.ChangeDirection(200, airplane);
        junit.framework.Assert.assertEquals("Direction was expected to change to 200", 200.0, airplane.getAimedDirection());
    }

    /**
     * Test of ChangeHeight method, of class ACC.
     */
    @Test
    public void testChangeHeight() throws AssignmentException, RemoteException  {
        System.out.println("ChangeHeight");
        acc.ChangeHeight(3, airplane);
        junit.framework.Assert.assertEquals("Height should have been changed to 3", 3000.0, airplane.getAimedAltitude());
    }

    /**
     * Test of LandFlight method, of class ACC.
     */
    @Test
    public void testLandFlight() throws Exception {
        System.out.println("LandFlight");
        IFlightplan fp = null;
        ACC instance = null;
        instance.LandFlight(fp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of CircleAirplane method, of class ACC.
     */
    @Test
    public void testCircleAirplane() throws FileNotFoundException, IOException  {
        System.out.println("Circling airplane");
        acc.CircleAirplane(airplane);
        junit.framework.Assert.assertEquals("Airplane status should have been changed to INLANDINGQUEUE", Airplane.Statusses.INLANDINGQUEUE, airplane.getStatus());
    }

    /**
     * Test of CreateFlight method, of class ACC.
     */
    @Test
    public void testCreateFlight() throws FileNotFoundException, IOException  {
        System.out.println("Create flight");
        acc.CreateFlight(airplaneFactory, airport1, airport2, gc1, gc2);
        for (Iterator<IFlightplan> it = acc.getfp().iterator(); it.hasNext();) {
            Flightplan fp = (Flightplan) it.next();
            if (fp.getTakeoffAirport() == airport1)
            {
              junit.framework.Assert.assertEquals("Flight is created", airport1, fp.getTakeoffAirport());
            }
        }
    }

    /**
     * Test of assignFlightToController method, of class ACC.
     */
    @Test
    public void testAssignFlightToController() throws FileNotFoundException, IOException{
        System.out.println("Assign flight to controller");
        
        //todo
    }

    /**
     * Test of addFlightController method, of class ACC.
     */
    @Test
    public void testAddFlightController_0args() throws Exception {
        System.out.println("addFlightController");
        ACC instance = null;
        IFC expResult = null;
        IFC result = instance.addFlightController();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addFlightController method, of class ACC.
     */
    @Test
    public void testAddFlightController_IFC() throws Exception {
        System.out.println("addFlightController");
        IFC flightController = null;
        ACC instance = null;
        instance.addFlightController(flightController);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unassignFlightFromController method, of class ACC.
     */
    @Test
    public void testUnassignFlightFromController() throws Exception {
        System.out.println("unassignFlightFromController");
        IFlightplan flightplan = null;
        ACC instance = null;
        instance.unassignFlightFromController(flightplan);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeFlightController method, of class ACC.
     */
    @Test
    public void testRemoveFlightController() throws FileNotFoundException, IOException  {
        //        todo        
//        System.out.println("adding flightcontroller");
//        for(FlightController flightcontroller: atc2.airspace.getCurrentACC().getfc())
//        {
//            if(flightcontroller.getID() == fc.getID())
//            {
//                int a = flightcontroller.getID();
//                atc2.airspace.getCurrentACC().removeFlightController(flightcontroller);
//                Assert.assertNull(atc2.airspace.getCurrentACC().GetFlightController(a));}
//            }
//        }

    }

    /**
     * Test of getFlightplans method, of class ACC.
     */
    @Test
    public void testGetFlightplans() {
        System.out.println("getFlightplans");
        ACC instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getFlightplans();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAdjacentACCList method, of class ACC.
     */
    @Test
    public void testSetAdjacentACCList() {
        System.out.println("setAdjacentACCList");
        ArrayList<IACC> adjacentACCList = null;
        ACC instance = null;
        instance.setAdjacentACCList(adjacentACCList);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAdjacentACCList method, of class ACC.
     */
    @Test
    public void testGetAdjacentACCList() {
        System.out.println("getAdjacentACCList");
        ACC instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getAdjacentACCList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ContainsFlightplan method, of class ACC.
     */
    @Test
    public void testContainsFlightplan() {
        System.out.println("ContainsFlightplan");
        IFlightplan flightplan = null;
        ACC instance = null;
        Boolean expResult = null;
        Boolean result = instance.ContainsFlightplan(flightplan);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeFlightPlan method, of class ACC.
     */
    @Test
    public void testRemoveFlightPlan() throws FileNotFoundException, IOException {
        //      TO DO  
//        System.out.println("Create flight");
//       
//  
//        for(Flightplan fp: atc2.airspace.getCurrentACC().getfp())
//        {
//        if (fp.getFlightnumber() == flightPlan.getFlightnumber())
//        {
//          atc2.airspace.getCurrentACC().removeFlightPlan(fp);  
//          Assert.assertEquals("Flight is deleted", false, atc2.airspace.getCurrentACC().getfp().contains(fp));
//        }
//        }
    }

    /**
     * Test of addFlightPlan method, of class ACC.
     */
    @Test
    public void testAddFlightPlan() throws FileNotFoundException, IOException {
        //TODO
        //System.out.println("Create flight");
        //ACC.addFlightPlan(flightPlan);
        //for (Iterator<IFlightplan> it = ACC.getfp().iterator(); it.hasNext();) {
           // Flightplan fp = (Flightplan) it.next();
            //if (fp.getFlightnumber() == flightPlan.getFlightnumber())
            //{
              //junit.framework.Assert.assertEquals("Flightplan is created", fp.getFlightnumber(), flightPlan.getFlightnumber());
            //}
        //}
    }

    /**
     * Test of addListener method, of class ACC.
     */
    @Test
    public void testAddListener() throws Exception {
        System.out.println("addListener");
        RemoteListener listener = null;
        ACC instance = null;
        instance.addListener(listener);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeListener method, of class ACC.
     */
    @Test
    public void testRemoveListener() throws Exception {
        System.out.println("removeListener");
        RemoteListener listener = null;
        ACC instance = null;
        instance.removeListener(listener);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
