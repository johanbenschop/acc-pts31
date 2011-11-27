/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

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
    
    //private CTA cta;
    private GeoLocation geoLocation, geoLocation2;
    //private Airplane airplane;
    private Airport airport;
    
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
        //cta = new CTA(geoLocation, 100, 100); // Hier moeten ook nog goede waardes in
        geoLocation2 = new GeoLocation(2,2,2);
        //airplane = new Airplane(500, 300, 16000, "747-300", "Boeing", 300, 300, 500, 200, 1, 100, Double.parseDouble("299"), 100, Double.parseDouble("650"), geoLocation, geoLocation2, 1);
        airport = new Airport(0, "", "", "","", "", geoLocation, 0, 0, "");
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

//    /**
//     * Test of Collision method, of class CTA.
//     */
//    @Test
//    public void testCollision() {
//        System.out.println("Collision");
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
 
    /** 
     * Test of addAirplane method, of class CTA.
     */
    @Test
    public void testAddAirplane() {
        System.out.println("addAirplane");
        //cta.addAirplane(airplane);
        //Assert.assertEquals("Airplane has been added.", true, cta.getAirplaneList().contains(airplane));
    }
    
    /**
     * Test of GetActiveAirplane method, of class CTA.
     */
    @Test
    public void testGetActiveAirplane() {
        //cta.addAirplane(airplane);
        System.out.println("GetActiveAirplane");
        //cta.GetActiveAirplane(airplane.getAirplaneNumber());
        //Assert.assertEquals("Retrieved active airplane", airplane, cta.getAirplane());
    }
    
    /**
     * Test of deleteAirplane method, of class CTA.
     */
    @Test
    public void testDeleteAirplane() {
        System.out.println("deleteAirplane");
        //cta.deleteAirplane(airplane.getAirplaneNumber());
        //Assert.assertEquals("Airport has been added.", false, cta.getAirportList().contains(airport));
    }
    
    /**
     * Test of addAirport method, of class CTA.
     */
    @Test
    public void testAddAirport() {
        System.out.println("addAirport");
        //cta.addAirport(airport);
        //Assert.assertEquals("Airport has been added.", true, cta.getAirportList().contains(airport));
    }
    
    /**
     * Test of GetAirport method, of class CTA.
     */
    @Test
    public void testGetAirport() {
        //cta.addAirport(airport);
        System.out.println("GetAirport");
        //cta.GetAirport(0);
        //Assert.assertEquals("Retrieved airport with given ID", airport, cta.getAirport());
    }
    
    /**
     * Test of loadAirportList method, of class CTA.
     */
    @Test
    public void testloadAirportList() throws FileNotFoundException, IOException {
        System.out.println("loadAirportList");
        //cta.loadAirportList();
        //airport = cta.GetAirport(1);
        Assert.assertEquals("This objects number should be number 1", 1, airport.getAirportID());
    }
}
