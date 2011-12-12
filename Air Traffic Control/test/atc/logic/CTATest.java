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
 * @author johan
 */
public class CTATest {
    
    private CTA cta;
    private Airspace airspace = new Airspace(); 
    private GeoLocation geoLocation, geoLocation2;
    private GeoSector geosector;
    private Airport airport;
    private Airplane airplane;
    private ArrayList<Airport> airportList = new ArrayList<Airport>();
    
    public CTATest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        geoLocation = new GeoLocation(100,100,0); //Hier moeten nog goede waardes inkomen maar ik weet niet welke.
        geosector = new GeoSector(0, 0, 0, 0);
        cta = new CTA(geosector, airportList); // Hier moeten ook nog goede waardes in
        geoLocation2 = new GeoLocation(2,2,2);
        airport = new Airport(5555, "", "", "","", "", geoLocation, 0, 0, "");
        airplane = new Airplane(600, 400, 10000, "", "", 10, 10, 10, 10000, 500, 0, 0, 10000, 0 , geoLocation, geoLocation2, 9999);
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of DetectAirplane method, of class CTA.
     */
    @Test
    public void testDetectAirplane() {
        System.out.println("DetectAirplane");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

 
    /** 
     * Test of addAirplane method, of class CTA.
     */
    @Test
    public void testAddAirplane() {
        System.out.println("addAirplane");
         cta.addAirplane(airplane);
         Assert.assertEquals("Airplane has been added.", airplane, cta.GetAirplane(9999));
    }
    

    
    /**
     * Test of deleteAirplane method, of class CTA.
     */
    @Test
    public void testDeleteAirplane() {
        cta.removeAirplane(airplane);
        Assert.assertEquals("Airplane should not be found in the list", null, cta.GetAirplane(9999));
        System.out.println("deleteAirplane");
        //cta.deleteAirplane(airplane.getAirplaneNumber());
        //Assert.assertEquals("Airport has been added.", false, cta.getAirportList().contains(airport));
    }
    
    /**
     * Test of addAirport method, of class CTA.
     */
    @Test
    public void testAddAirport() {
        cta.addAirport(airport);
       Assert.assertEquals("Airplane has been added.", airport, cta.GetAirport(5555));
        System.out.println("addAirport");
        //cta.addAirport(airport);
        //Assert.assertEquals("Airport has been added.", true, cta.getAirportList().contains(airport));
    }
    
}
