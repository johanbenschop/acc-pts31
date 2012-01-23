/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import atc.interfaces.*;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
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
    private Airspace airspace;
    private IGeoLoc geoLocation, geoLocation2;
    private IGeoSec geosector;
    private Airport airport;
    private Airplane airplane;
    private ArrayList<IAirport> airportList = new ArrayList<>();

    public CTATest() throws RemoteException {
        airspace = new Airspace();
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws RemoteException {
        geoLocation = new GeoLocation(100, 100, 0);
        geosector = new GeoSector(0, 0, 0, 0);
        cta = new CTA(geosector, airportList, 1);
        geoLocation2 = new GeoLocation(2, 2, 2);
        airport = new Airport(5555, "", "", "", "", "", geoLocation, 0, 0, "");
        airplane = new Airplane(600, 400, 10000, "", "", 10, 10, 10, 10000, 500, 0, 0, 10000, 0, geoLocation, geoLocation2, 9999);
    }

    @After
    public void tearDown() {
    }
    
    /** 
     * Test of GetActiveAirplane method, of class CTA.
     */
    @Test
    public void testGetActiveAirplane() throws RemoteException {
        System.out.println("GetActiveAirplane");
        cta.addAirplane(airplane);
        cta.GetAirplane(9999);
        Assert.assertEquals("Active Airplane has been set.", airplane, cta.getAirplane());
    }

    /** 
     * Test of distFrom method, of class CTA.
     */
    @Test
    public void testDistFrom() {
        System.out.println("distFrom");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**                           TODO in deze methode wordt vliegtuig ook aan de collisionlist toegevoegd, dit moet nog worden getest!!
     * Test of addAirplane method, of class CTA.
     */
    @Test
    public void testAddAirplane() throws RemoteException {
        System.out.println("addAirplane");
        cta.addAirplane(airplane);
        Assert.assertEquals("Airplane has been added.", airplane, cta.GetAirplane(9999));

    }

    /**
     * Test of deleteAirplane method, of class CTA.
     */
    @Test
    public void testDeleteAirplane() throws RemoteException {
        System.out.println("deleteAirplane");
        cta.deleteAirplane(9999);
        Assert.assertEquals("Airplane should not be found in the list", null, cta.GetAirplane(9999));
    }

    /** 
     * Test of removeAirplane method, of class CTA.
     */
    @Test
    public void testRemoveAirplane() throws RemoteException {
        System.out.println("removeAirplane");
        cta.removeAirplane(airplane);
        Assert.assertEquals("Airplane should not be found in the list", null, cta.GetAirplane((int) airplane.getAirplaneNumber()));
    }

    /**
     * Test of addAirport method, of class CTA.
     */
    @Test
    public void testAddAirport() throws RemoteException {
        System.out.println("addAirport");
        cta.addAirport(airport);
        Assert.assertEquals("Airplane has been added.", airport, cta.GetAirport(5555));
    }

    /** 
     * Test of CreateGreaterSector method, of class CTA.
     */
    @Test
    public void testCreateGreaterSector() {
        System.out.println("CreateGreaterSector");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
