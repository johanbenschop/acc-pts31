/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import atc.gui.atc2;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.GregorianCalendar;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.util.calendar.Gregorian;
import static org.junit.Assert.*;

/**
 *
 * @author henk
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
    private ArrayList<Airport> airportList = new ArrayList<Airport>();
    private GeoSector geosector;
    private ACC acc;
    private CTA cta;
    private Airplane airplane;
    private Airspace airspace;
    private GregorianCalendar gc1;
    private GregorianCalendar gc2;
    private Flightplan flightPlan;
    private ArrayList<Runway> runways;
    private FlightController fc;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    /**
     * Values of the seperate instances still have to be set correct.
     * @throws Exception 
     */
    @Before
    public void setUp() throws Exception {
        
        loc = new GeoLocation(1,1,1);
        loc2 = new GeoLocation(2,2,2);
        //cta = new CTA(loc, 1,1);
        //acc = new ACC(1, cta);
        //apa = new Airplane(500, 300, 16000, "747-300", "Boeing", 300, 300, 500, 200, 1, 100, Double.parseDouble("299"), 100, Double.parseDouble("650"), loc, loc2, 1);
        //apt = new Airplane(500, 300, 1600, "747-300", "Boeing", 300, 300, 500, 200, 1, 100, Double.parseDouble("299"), 100, Double.parseDouble("640"), loc, loc2, 2);
        ra = new Runway(1,1,50, 300, 180, true);
        ru = new Runway(1,1,50,300, 270, false);
        geosector = new GeoSector(0, 0, 0, 0);
        airplane = new Airplane(600, 400, 10000, "", "", 10, 10, 10, 10000, 500, 0, 0, 10000, 0 , loc, loc2, 9999);
        cta = new CTA(geosector, airportList); // Hier moeten ook nog goede waardes in
        acc = new ACC(2, null);
        airspace = new Airspace();
        airspace.setCurrentACC(acc);
        runways = new ArrayList<Runway>();
        airport1 = new Airport(1,"bla", "bla", "bla", "bla", "bla",loc, 1000, 10.0, "EU");
        airport2 = new Airport(2,"bla", "bla", "bla", "bla", "bla",loc, 1000, 10.0, "EU");
        airplaneFactory = new AirplaneFactory(900, 500, 100, "bla" , "bla", 20, 20, 50, 10000, 500);
        gc1 = new GregorianCalendar();
        gc2 = new GregorianCalendar();
        fc = new FlightController();
        atc2.airspace.getCurrentACC().addFlightController(fc);
        flightPlan = new Flightplan(airport1, airport2, 5, gc1, gc2, airplane);
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of ChangeSpeed method, of class ACC.
     * Currently Airplane does not yet have a constructor. I will update the constructor and tests once this is done.
     */
    @Test
    public void testChangeSpeed() throws AssignmentException {
        System.out.println("ChangeSpeed");

             atc2.airspace.getCurrentACC().ChangeSpeed(500.0, airplane);
        Assert.assertEquals("Current speed is expected to be changed to 500", 500.0, airplane.getAimedSpeed());
        
    }

    /**
     * Test of ChangeDirection method, of class ACC.
     */
    @Test
    public void testChangeDirection() throws AssignmentException {
        System.out.println("ChangeDirection");
        acc.ChangeDirection(200, airplane);
        Assert.assertEquals("Direction was expected to change to 200", 200.0, airplane.getAimedDirection());
       
     }
    
    /**
     * Test of ChangeHeight method, of class ACC.
     * Assumed that this is done in flightlevels rather then actual feet.
     */
    @Test
    public void testChangeHeight() throws AssignmentException {
        System.out.println("ChangeHeight");
        acc.ChangeHeight(3, airplane);
        Assert.assertEquals("Height should have been changed to 3", 3000.0, airplane.getAimedAltitude()); 
   }

        /**
     * Test of loadAirplaneFactoryList method, of class ACC.
     */
    @Test
    public void testLoadAirplaneFactoryList() throws FileNotFoundException, IOException {
        System.out.println("Load airportlist");
        try {
            acc.loadAirplaneFactoryList();
            Assert.assertEquals("Manufacturer should be Fokker", "Fokker", acc.GetAirplaneFactory(1).getManufacturer());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
     /**
     * Test of CircleAirplane method, of class ACC.
     */
    @Test
    public void testCircleAirplane() throws FileNotFoundException, IOException {
        System.out.println("Circling airplane");
        atc2.airspace.getCurrentACC().CircleAirplane(airplane);
        Assert.assertEquals("Airplane status should have been changed to INLANDINGQUEUE", Airplane.Statusses.INLANDINGQUEUE, airplane.getStatus());
    }
    
     /**
     * Test of CreateFlight method, of class ACC.
     */
    @Test
    public void testCreateFlight() throws FileNotFoundException, IOException {
        System.out.println("Create flight");
        atc2.airspace.getCurrentACC().CreateFlight(airplaneFactory, airport1, airport2, gc1, gc2);
        for(Flightplan fp: atc2.airspace.getCurrentACC().getfp())
        {
        if (fp.getTakeoffAirport() == airport1)
        {
          Assert.assertEquals("Flight is created", airport1, fp.getTakeoffAirport());
        }
        }
    }
    
    @Test
    public void testAssignFlightToController() throws FileNotFoundException, IOException {
        System.out.println("Assign flight to controller");
        
        //todo
      }
    
        @Test
    public void testAddFlightController() throws FileNotFoundException, IOException {
        System.out.println("adding flightcontroller");
        for(FlightController flightcontroller: atc2.airspace.getCurrentACC().getfc())
        {
            if(flightcontroller == fc)
            {
                Assert.assertEquals(flightcontroller.getID(), fc.getID());}
        }
      }
        
        @Test
    public void testRemoveFlightController() throws FileNotFoundException, IOException {
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
     * Test of CreateFlight method, of class ACC.
     */
    @Test
    public void testCreateFlightPlan() throws FileNotFoundException, IOException {
        System.out.println("Create flight");
        atc2.airspace.getCurrentACC().addFlightPlan(flightPlan);
        for(Flightplan fp: atc2.airspace.getCurrentACC().getfp())
        {
        if (fp.getFlightnumber() == flightPlan.getFlightnumber())
        {
          Assert.assertEquals("Flightplan is created", fp.getFlightnumber(), flightPlan.getFlightnumber());
        }
        }
    }
          /**
     * Test of CreateFlight method, of class ACC.
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
}

