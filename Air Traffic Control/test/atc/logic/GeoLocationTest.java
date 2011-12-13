/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

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
public class GeoLocationTest {

    private GeoLocation geoLocation;

    public GeoLocationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        geoLocation = new GeoLocation(10.0, 15.0, 25.0);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of toSector method, of class GeoLocation.
     */
    @Test
    public void testToLatLon() {
        System.out.println("toLatLon");
        Assert.assertEquals("The LatLon is returned in degrees", "15.0°", geoLocation.toLatLon().latitude.toString());
        Assert.assertEquals("The LatLon is returned in degrees", "10.0°", geoLocation.toLatLon().longitude.toString());
    }

    /**
     * Test of toPosition method, of class GeoLocation.
     */
    @Test
    public void testToPosition() {
        System.out.println("toPosition");
        Assert.assertEquals("The LatLon is returned in degrees", "15.0°", geoLocation.toPosition().latitude.toString());
        Assert.assertEquals("The LatLon is returned in degrees", "10.0°", geoLocation.toPosition().longitude.toString());
        Assert.assertEquals("The LatLon is returned in degrees", 25.0, geoLocation.toPosition().elevation);
    }

    /**
     * Test of ToString method, of class GeoLocation.
     */
    @Test
    public void testCalcDirection() {
        System.out.println("CalcDirection(Airport a, Airport b)");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ToString method, of class GeoLocation.
     */
    @Test
    public void testCalcDirection2() {
        System.out.println("CalcDirection(GeoLocation locationA, GeoLocation locationB)");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ToString method, of class GeoLocation.
     */
    @Test
    public void testCalcPosition() {
        System.out.println("CalcPosition");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
