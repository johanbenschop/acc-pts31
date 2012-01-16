package atc.logic;

import atc.interfaces.IGeoLoc;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Mateusz
 */
public class GeoLocationTest {
    
    public GeoLocationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    /**
     * Test of ToString method, of class GeoLocation.
     */
    @Test
    public void testToString() {
        System.out.println("ToString");
        GeoLocation instance = null;
        String expResult = "";
        String result = instance.ToString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toLatLon method, of class GeoLocation.
     */
    @Test
    public void testToLatLon() {
        System.out.println("toLatLon");
        GeoLocation instance = null;
        LatLon expResult = null;
        LatLon result = instance.toLatLon();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toPosition method, of class GeoLocation.
     */
    @Test
    public void testToPosition() {
        System.out.println("toPosition");
        GeoLocation instance = null;
        Position expResult = null;
        Position result = instance.toPosition();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of CalcDirection method, of class GeoLocation.
     */
    @Test
    public void testCalcDirection() throws Exception {
        System.out.println("CalcDirection");
        IGeoLoc locationA = null;
        IGeoLoc locationB = null;
        double expResult = 0.0;
        double result = GeoLocation.CalcDirection(locationA, locationB);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of CalcPosition method, of class GeoLocation.
     */
    @Test
    public void testCalcPosition() {
        System.out.println("CalcPosition");
        double lon1 = 0.0;
        double lat1 = 0.0;
        double direction = 0.0;
        double distance = 0.0;
        IGeoLoc expResult = null;
        IGeoLoc result = GeoLocation.CalcPosition(lon1, lat1, direction, distance);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAltitude method, of class GeoLocation.
     */
    @Test
    public void testGetAltitude() {
        System.out.println("getAltitude");
        GeoLocation instance = null;
        double expResult = 0.0;
        double result = instance.getAltitude();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLatitude method, of class GeoLocation.
     */
    @Test
    public void testGetLatitude() {
        System.out.println("getLatitude");
        GeoLocation instance = null;
        double expResult = 0.0;
        double result = instance.getLatitude();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLongitude method, of class GeoLocation.
     */
    @Test
    public void testGetLongitude() {
        System.out.println("getLongitude");
        GeoLocation instance = null;
        double expResult = 0.0;
        double result = instance.getLongitude();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNewGeoLocation method, of class GeoLocation.
     */
    @Test
    public void testGetNewGeoLocation() {
        System.out.println("getNewGeoLocation");
        GeoLocation instance = null;
        IGeoLoc expResult = null;
        IGeoLoc result = instance.getNewGeoLocation();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAltitude method, of class GeoLocation.
     */
    @Test
    public void testSetAltitude() {
        System.out.println("setAltitude");
        double Altitude = 0.0;
        GeoLocation instance = null;
        instance.setAltitude(Altitude);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLatitude method, of class GeoLocation.
     */
    @Test
    public void testSetLatitude() {
        System.out.println("setLatitude");
        double Latitude = 0.0;
        GeoLocation instance = null;
        instance.setLatitude(Latitude);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLongitude method, of class GeoLocation.
     */
    @Test
    public void testSetLongitude() {
        System.out.println("setLongitude");
        double Longitude = 0.0;
        GeoLocation instance = null;
        instance.setLongitude(Longitude);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
