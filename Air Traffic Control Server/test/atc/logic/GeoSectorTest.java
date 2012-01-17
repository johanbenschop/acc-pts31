package atc.logic;

import junit.framework.Assert;
import java.rmi.RemoteException;
import org.junit.*;

/**
 *
 * @author Mateusz
 */
public class GeoSectorTest {
    
    private GeoSector geoSector;
    private GeoSector geoSectorWrongLatLon;
    private GeoSector geoSectorWrongLatLon1;
    private GeoSector geoSectorWrongLatLon2;
    private GeoSector geoSectorWrongLatLon3;
    private GeoSector geoSectorIntersectsThis;
    private GeoSector geoSectorIntersectsThat;
    private GeoSector geoSectorIntersectsThis1;
    private GeoSector geoSectorIntersectsThat1;
    private GeoSector geoSectorIntersectsThis2;
    private GeoSector geoSectorIntersectsThat2;
    private GeoSector geoSectorIntersectsThis3;
    private GeoSector geoSectorIntersectsThat3;
    private GeoSector geoSectorIntersectsThatNull;
    private GeoSector geoSectorIntersectsThisNull;
    private GeoSector geoSectorIntersectsThatTrue;
    private GeoSector geoSectorIntersectsThisTrue;
    private GeoSector geoSectorEquals;
    private GeoSector geoSectorEquals1; // equal
    private GeoSector geoSectorEquals2; // null
    private GeoLocation geoSectorEquals3; // not the same class
    private GeoSector geoSectorEquals4; // maxLatitude is not equal
    private GeoSector geoSectorEquals5; // minLatitude is not equal
    private GeoSector geoSectorEquals6; // maxLongitude is not equal
    private GeoSector geoSectorEquals7; // MinLongitude is not equal
    private GeoSector geoSectorEquals8; // All is okay
    private GeoLocation geoLocation;
    private GeoLocation geoLocation1;
    private GeoLocation geoLocation2;
    private GeoLocation geoLocation3;
    private GeoLocation geoLocationTrue;
    private GeoSector geosectorToString;
    private GeoSector geosectorTestGetters;
    
    
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() throws RemoteException {
        geoSector = new GeoSector(-80, 20, -120, 80);
        geoSectorWrongLatLon = new GeoSector(-100, 0, 0, 0);
        geoSectorWrongLatLon1 = new GeoSector(0, 100, 0, 0);
        geoSectorWrongLatLon2 = new GeoSector(0, 0, -190, 0);
        geoSectorWrongLatLon3 = new GeoSector(0, 0, 0, 190);


        geoSectorIntersectsThisNull = new GeoSector(0, 0, 0, 0);
        geoSectorIntersectsThatNull = new GeoSector(0, 0, 0, 0);
        geoSectorIntersectsThatNull = null;

        geoSectorIntersectsThis = new GeoSector(20, 21, -120, 80); // if (that.maxLatitude < this.minLatitude)
        geoSectorIntersectsThat = new GeoSector(-80, -79, -120, 80);

        geoSectorIntersectsThis1 = new GeoSector(-80, -79, -120, 80); // if (that.minLatitude > this.maxLatitude)
        geoSectorIntersectsThat1 = new GeoSector(20, 21, -120, 80);

        geoSectorIntersectsThis2 = new GeoSector(-80, 20, -120, -119); // if (that.minLongitude > this.maxLongitude)
        geoSectorIntersectsThat2 = new GeoSector(-80, 20, 80, 81);

        geoSectorIntersectsThis3 = new GeoSector(-80, 20, 80, 81); // if (that.maxLongitude < this.minLongitude)
        geoSectorIntersectsThat3 = new GeoSector(-80, 20, -120, -119);

        geoSectorIntersectsThisTrue = new GeoSector(-79, 21, -119, 81);
        geoSectorIntersectsThatTrue = new GeoSector(-80, 20, -120, 80);


        geoSectorEquals = new GeoSector(-80, 20, -120, 80);
        geoSectorEquals1 = geoSectorEquals;
        geoSectorEquals2 = new GeoSector(-80, 20, -120, 80);
        geoSectorEquals2 = null;
        geoSectorEquals3 = new GeoLocation(0, 0);
        geoSectorEquals4 = new GeoSector(-80, 21, -120, 80);
        geoSectorEquals5 = new GeoSector(-79, 20, -120, 80);
        geoSectorEquals6 = new GeoSector(-80, 20, -120, 81);
        geoSectorEquals7 = new GeoSector(-80, 20, -119, 80);
        geoSectorEquals8 = new GeoSector(-80, 20, -120, 80);


        geoLocation = new GeoLocation(-81, 0); //location.getLatitude() >= minLatitude
        geoLocation1 = new GeoLocation(21, 0); //location.getLatitude() <= maxLatitude
        geoLocation2 = new GeoLocation(0, -121); //location.getLongitude() >= minLongitude
        geoLocation3 = new GeoLocation(0, 81); //location.getLongitude() <= maxLongitude
        geoLocationTrue = new GeoLocation(-50, 50);
                
        geosectorToString = new GeoSector(0.0, 0.0, 0.0, 0.0);
        geosectorTestGetters = new GeoSector(0.0, 0.0, 0.0, 0.0);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isWithinLatLonLimits method, of class GeoSector.
     */
    @Test
    public void testIsWithinLatLonLimits() {
        System.out.println("isWithinLatLonLimits");
        Assert.assertEquals("GeoSector is not within latitude/ longitude limits", false, geoSectorWrongLatLon.isWithinLatLonLimits());
        Assert.assertEquals("GeoSector is not within latitude/ longitude limits", false, geoSectorWrongLatLon1.isWithinLatLonLimits());
        Assert.assertEquals("GeoSector is not within latitude/ longitude limits", false, geoSectorWrongLatLon2.isWithinLatLonLimits());
        Assert.assertEquals("GeoSector is not within latitude/ longitude limits", false, geoSectorWrongLatLon3.isWithinLatLonLimits());

        Assert.assertEquals("GeoSector is within latitude/ longitude limits", true, geoSector.isWithinLatLonLimits());
    }

    /**
     * Test of getCenterLocation method, of class GeoSector.
     */
    @Test
    public void testGetCenterLocation() throws Exception {
        System.out.println("getCenterLocation");
        Assert.assertEquals("The center location of the GeoSector is returned", -30.0, geoSector.getCenterLocation().getLatitude());
        Assert.assertEquals("The center location of the GeoSector is returned", -20.0, geoSector.getCenterLocation().getLongitude());
    }

    /**
     * Test of intersects method, of class GeoSector.
     */
    @Test
    public void testIntersects() {
        System.out.println("intersects");
        Assert.assertEquals("The given sector is null", false, geoSectorIntersectsThisNull.intersects(geoSectorIntersectsThatNull));
        Assert.assertEquals("The sectors don't intersect", false, geoSectorIntersectsThis.intersects(geoSectorIntersectsThat));
        Assert.assertEquals("The sectors don't intersect", false, geoSectorIntersectsThis1.intersects(geoSectorIntersectsThat1));
        Assert.assertEquals("The sectors don't intersect", false, geoSectorIntersectsThis2.intersects(geoSectorIntersectsThat2));
        Assert.assertEquals("The sectors don't intersect", false, geoSectorIntersectsThis3.intersects(geoSectorIntersectsThat3));
        Assert.assertEquals("The sectors intersect", true, geoSectorIntersectsThisTrue.intersects(geoSectorIntersectsThatTrue));
    }

    /**
     * Test of intersectsInterior method, of class GeoSector.
     */
    @Test
    public void testIntersectsInterior() {
        System.out.println("intersectsInterior");
        Assert.assertEquals("The given sector is null", false, geoSectorIntersectsThisNull.intersectsInterior(geoSectorIntersectsThatNull));
        Assert.assertEquals("The sectors don't intersect", false, geoSectorIntersectsThis.intersectsInterior(geoSectorIntersectsThat));
        Assert.assertEquals("The sectors don't intersect", false, geoSectorIntersectsThis1.intersectsInterior(geoSectorIntersectsThat1));
        Assert.assertEquals("The sectors don't intersect", false, geoSectorIntersectsThis2.intersectsInterior(geoSectorIntersectsThat2));
        Assert.assertEquals("The sectors don't intersect", false, geoSectorIntersectsThis3.intersectsInterior(geoSectorIntersectsThat3));
        Assert.assertEquals("The sectors intersect", true, geoSectorIntersectsThisTrue.intersectsInterior(geoSectorIntersectsThatTrue));
    }

    /**
     * Test of toString method, of class GeoSector.
     */
    @Test
    public void testToString() throws RemoteException {
        System.out.println("toString");
        String expResult = "(0.0, 0.0), (0.0, 0.0)";
        String result = geosectorToString.toString();
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class GeoSector.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Assert.assertEquals("The sectors are equal", true, geoSectorEquals.equals(geoSectorEquals1));
        Assert.assertEquals("The sectors aren't equal", false, geoSectorEquals.equals(geoSectorEquals2));
        Assert.assertEquals("The sectors aren't equal", false, geoSectorEquals.equals(geoSectorEquals3));
        Assert.assertEquals("The sectors aren't equal", false, geoSectorEquals.equals(geoSectorEquals4));
        Assert.assertEquals("The sectors aren't equal", false, geoSectorEquals.equals(geoSectorEquals5));
        Assert.assertEquals("The sectors aren't equal", false, geoSectorEquals.equals(geoSectorEquals6));
        Assert.assertEquals("The sectors aren't equal", false, geoSectorEquals.equals(geoSectorEquals7));
        Assert.assertEquals("The sectors are equal", true, geoSectorEquals.equals(geoSectorEquals8));
    }

    /**
     * Test of containsGeoLocation method, of class GeoSector.
     */
    @Test
    public void testContainsGeoLocation() throws Exception {
        System.out.println("containsGeoLocation");
        Assert.assertEquals("This GeoLocation is within the GeoSector", false, geoSector.containsGeoLocation(geoLocation));
        Assert.assertEquals("This GeoLocation is within the GeoSector", false, geoSector.containsGeoLocation(geoLocation1));
        Assert.assertEquals("This GeoLocation is within the GeoSector", false, geoSector.containsGeoLocation(geoLocation2));
        Assert.assertEquals("This GeoLocation is within the GeoSector", false, geoSector.containsGeoLocation(geoLocation3));

        Assert.assertEquals("This GeoLocation is within the GeoSector", true, geoSector.containsGeoLocation(geoLocationTrue));
    }

    /**
     * Test of getDeltaLatitude method, of class GeoSector.
     */
    @Test
    public void testGetDeltaLatitude() {
        System.out.println("getDeltaLatitude");
        double expResult = 0.0;
        double result = geosectorTestGetters.getDeltaLatitude();
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of getDeltaLongitude method, of class GeoSector.
     */
    @Test
    public void testGetDeltaLongitude() {
        System.out.println("getDeltaLongitude");
        double expResult = 0.0;
        double result = geosectorTestGetters.getDeltaLongitude();
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of getMaxLatitude method, of class GeoSector.
     */
    @Test
    public void testGetMaxLatitude() {
        System.out.println("getMaxLatitude");
        double expResult = 0.0;
        double result = geosectorTestGetters.getMaxLatitude();
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of getMaxLongitude method, of class GeoSector.
     */
    @Test
    public void testGetMaxLongitude() {
        System.out.println("getMaxLongitude");
        double expResult = 0.0;
        double result = geosectorTestGetters.getMaxLongitude();
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of getMinLatitude method, of class GeoSector.
     */
    @Test
    public void testGetMinLatitude() {
        System.out.println("getMinLatitude");
        double expResult = 0.0;
        double result = geosectorTestGetters.getMinLatitude();
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of getMinLongitude method, of class GeoSector.
     */
    @Test
    public void testGetMinLongitude() {
        System.out.println("getMinLongitude");
        double expResult = 0.0;
        double result = geosectorTestGetters.getMinLongitude();
        Assert.assertEquals(expResult, result);
    }
}