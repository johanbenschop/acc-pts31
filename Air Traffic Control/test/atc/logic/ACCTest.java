/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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

        acc.ChangeSpeed(500, airplane);
        Assert.assertEquals("Current speed is expected to be changed to 500", 500, airplane.getSpeed());
        
    }

    /**
     * Test of ChangeDirection method, of class ACC.
     */
    @Test
    public void testChangeDirection() throws AssignmentException {
        System.out.println("ChangeDirection");
        acc.ChangeDirection(200, airplane);
        Assert.assertEquals("Direction was expected to change to 200", 200, airplane.getDirection());
       
     }
    
    /**
     * Test of ChangeHeight method, of class ACC.
     * Assumed that this is done in flightlevels rather then actual feet.
     */
    @Test
    public void testChangeHeight() throws AssignmentException {
        System.out.println("ChangeHeight");
        acc.ChangeHeight(3, airplane);
        Assert.assertEquals("Height should have been changed to 3", 3, airplane.getAltitude()); 
   }

        /**
     * Test of loadAvailableAirplaneList method, of class CTA.
     */
    @Test
    public void testLoadAirplaneFactoryList() throws FileNotFoundException, IOException {
        System.out.println("Load airportlist");
        
        try {
            acc.loadAirplaneFactoryList();
            System.out.println(acc.GetAirplaneFactory(1).getType());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
     //   Assert.assertEquals("airplanename should be Goraka", "Goroka", airspace.GetAirport(1).getAirportName());
   }
}
