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
    
    private CTA cta;
    private GeoLocation geoLocation;
    private Airplane airplane;
    private AirplaneFactory airplaneFactory;
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
        geoLocation = new GeoLocation(0,0,0); //Hier moeten nog goede waardes inkomen maar ik weet niet welke.
        cta = new CTA(geoLocation, 0, 0); // Hier moeten ook nog goede waardes in
       // airplane = new Airplane(); 
       // airport = new Airport();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of GetActiveAirplane method, of class CTA.
     */
    @Test
    public void testGetActiveAirplane() {
        System.out.println("GetActiveAirplane");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetAirport method, of class CTA.
     */
    @Test
    public void testGetAirport() {
        System.out.println("GetAirport");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of GetAirports method, of class CTA.
     */
    @Test
    public void testGetAirports() {
        System.out.println("GetAirports");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
     * Test of Collision method, of class CTA.
     */
    @Test
    public void testCollision() {
        System.out.println("Collision");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
 
    /** //Geen idee hoe dit moet
     * Test of addAirplane method, of class CTA.
     */
    @Test
    public void testAddAirplane() {
        System.out.println("addAirplane");
        cta.addAirplane(airplane);
        Assert.assertEquals("Airplane has been added.", true, cta.getAirplaneList().contains(airplane));
    }
    
    /**
     * Test of addAirport method, of class CTA.
     */
    @Test
    public void testAddAirport() {
        System.out.println("addAirport");
        cta.addAirport(airport);
        Assert.assertEquals("Airport has been added.", true, cta.getAirportList().contains(airport));
    }
    
    /**
     * Test of deleteAirplane method, of class CTA.
     */
    @Test
    public void testDeleteAirplane() {
        System.out.println("deleteAirplane");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of loadAirportList method, of class CTA.
     */
    @Test
    public void testloadAirportList() throws FileNotFoundException, IOException {
        System.out.println("loadAirportList");
        cta.loadAirportList();
        airport = cta.GetAirport(1);
        assertEquals("This objects number should be number 1", airport.getAirportID(), 1);
    }
    
    /**
     * Test of loadAvailableAirplaneList method, of class CTA.
     */
    @Test
    public void testLoadAvailableAirplaneList() {
        System.out.println("loadAvailableAirplaneList");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
