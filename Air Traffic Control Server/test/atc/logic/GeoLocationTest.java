package atc.logic;

import atc.interfaces.*;
import java.rmi.RemoteException;
import org.junit.*;
import junit.framework.Assert;

/**
 *
 * @author Mateusz
 */
public class GeoLocationTest {
    
    private GeoLocation geoLocation;
    private GeoLocation geoLocationTakeoff;
    private GeoLocation geoLocationDestination;
    private Airport Amsterdam;
    private Airport Eindhoven;
    
    public GeoLocationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() throws RemoteException {
        geoLocation = new GeoLocation(10.0, 15.0, 25.0);
        geoLocationTakeoff = new GeoLocation(47.26021896305513, 11.413107452921505 );
        geoLocationDestination = new GeoLocation(47.1734, 12.4719 );
        Amsterdam = new Airport(0,"","","","","",geoLocationTakeoff,0,0.0,"");
        Eindhoven = new Airport(0,"","","","","",geoLocationDestination,0,0.0,"");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of CalcDirection method, of class GeoLocation.
     */
    @Test
    public void testCalcDirection_Airport_Airport() throws Exception {
        System.out.println("CalcDirection");
        Assert.assertEquals("The direction has been calculated", 96.49485788698077 , geoLocation.CalcDirection(Amsterdam, Eindhoven));
    }

    /**
     * Test of CalcDirection method, of class GeoLocation.
     */
    @Test
    public void testCalcDirection_IGeoLoc_IGeoLoc() throws Exception {
        System.out.println("CalcDirection");
        Assert.assertEquals("The direction has been calculated", 96.49485788698077 , geoLocation.CalcDirection(geoLocationTakeoff, geoLocationDestination));
    }

    /**
     * Test of CalcPosition method, of class GeoLocation.
     */
    @Test
    public void testCalcPosition() throws RemoteException {
        // Why does this test crash? Something went wrong through RMI...
        System.out.println("CalcPosition");
        double lon1 = 0.0;
        double lat1 = 0.0;
        double direction = 0.0;
        double distance = 0.0;
        IGeoLoc expResult = GeoLocation.CalcPosition(0.0, 0.0, 0.0, 0.0);
        IGeoLoc result = GeoLocation.CalcPosition(lon1, lat1, direction, distance);
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of getAltitude method, of class GeoLocation.
     */
    @Test
    public void testGetAltitude() throws RemoteException {
        System.out.println("getAltitude");
        GeoLocation instance = new GeoLocation(0.0, 0.0, 0.0);
        double expResult = 0.0;
        double result = instance.getAltitude();
        Assert.assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getLatitude method, of class GeoLocation.
     */
    @Test
    public void testGetLatitude() throws RemoteException {
        System.out.println("getLatitude");
        GeoLocation instance = new GeoLocation(0.0, 0.0, 0.0);
        double expResult = 0.0;
        double result = instance.getLatitude();
        Assert.assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getLongitude method, of class GeoLocation.
     */
    @Test
    public void testGetLongitude() throws RemoteException {
        System.out.println("getLongitude");
        GeoLocation instance = new GeoLocation(0.0, 0.0, 0.0);
        double expResult = 0.0;
        double result = instance.getLongitude();
        Assert.assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getNewGeoLocation method, of class GeoLocation.
     */
    @Test
    public void testGetNewGeoLocation() throws RemoteException {
        // Why does this test crash? Something went wrong through RMI...
        System.out.println("getNewGeoLocation");
        GeoLocation instance = new GeoLocation(0.0, 0.0, 0.0);
        IGeoLoc expResult = new GeoLocation(0.0, 0.0, 0.0);
        IGeoLoc result = instance.getNewGeoLocation();
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of setAltitude method, of class GeoLocation.
     */
    @Test
    public void testSetAltitude() throws RemoteException {
        // Why does this test crash? Something went wrong through RMI...
        System.out.println("setAltitude");
        double Altitude = 0.1;
        GeoLocation instance = new GeoLocation(0.0, 0.0, 0.0);
        instance.setAltitude(Altitude);
        GeoLocation instance2 = new GeoLocation(0.0, 0.0, Altitude);
        Assert.assertEquals(instance, instance2);
    }

    /**
     * Test of setLatitude method, of class GeoLocation.
     */
    @Test
    public void testSetLatitude() throws RemoteException {
        // Why does this test crash? Something went wrong through RMI...
        System.out.println("setLatitude");
        double Latitude = 0.1;
        GeoLocation instance = new GeoLocation(0.0, 0.0, 0.0);
        instance.setLatitude(Latitude);
        GeoLocation instance2 = new GeoLocation(0.0, Latitude, 0.0);
        Assert.assertEquals(instance, instance2);
    }

    /**
     * Test of setLongitude method, of class GeoLocation.
     */
    @Test
    public void testSetLongitude() throws RemoteException {
        // Why does this test crash? Something went wrong through RMI...
        System.out.println("setLongitude");
        double Longitude = 0.1;
        GeoLocation instance = new GeoLocation(0.0, 0.0, 0.0);
        instance.setLongitude(Longitude);
        GeoLocation instance2 = new GeoLocation(Longitude, 0.0, 0.0);
        Assert.assertEquals(instance, instance2);
    }
}