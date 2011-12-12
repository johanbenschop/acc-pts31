/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import junit.framework.Assert;
import atc.logic.Airplane.Statusses;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import junit.framework.Assert;

/**
 *
 * @author PuHa
 */
public class AirplaneTest {
    
    private GeoLocation location, location2;
    private Airplane airplane;
    private Runway runway;
    
    public AirplaneTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        //een vliegtuig met speed, direction en altitude 0;
        location2 = new GeoLocation(2,2,2);
        location = new GeoLocation(4.765293926879027, 52.30667884074721, 2.0);
        airplane = new Airplane(500, 300, 16000, "747-300", "Boeing", 300, 300, 500, 200, 10, 0, Double.parseDouble("0"), 100, Double.parseDouble("0"), location, location2, 1);
        runway = new Runway(4.765293926879027, 52.30667884074721, 2.0,300, 270, false);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of ChangeGeoLocation method, of class Airplane.
     */
    @Test
    public void testChangeGeoLocation() {
        System.out.println("ChangeGeoLocation");
        airplane.setStatus(Statusses.INFLIGHT);
        airplane.setAimedSpeed(300);
        airplane.ChangeSpeed();
        airplane.ChangeGeoLocation();
//        double latitudeTravelled = 10 * Math.sin(30);
//        double longitudeTravelled = 10 * Math.cos(30);
//        System.out.println(latitudeTravelled);
//        System.out.println(longitudeTravelled);
//        location.setLatitude((latitudeTravelled / 110.54) + location.getLatitude());
//        location.setLongitude((longitudeTravelled / (111.320*Math.cos(location.getLatitude()))) + location.getLongitude());
//        System.out.println(location.getLatitude());
//        System.out.println(location.getLongitude());
        Assert.assertEquals("Latitude should have changed", 52.30667909055877 , airplane.getLocation().getLatitude());
        Assert.assertEquals("Longitude should have changed",4.765293926879027 , airplane.getLocation().getLongitude());
    }
    
    /**
     * Test of ChangeSpeed method, of class Airplane.
     */
    @Test
    public void testChangeSpeed() {
        System.out.println("ChangeSpeed");
        airplane.setAimedSpeed(200);
        airplane.ChangeSpeed();
        Assert.assertEquals("Speed should have changed",1.0 , airplane.getSpeed());
        
        airplane.setAimedSpeed(500);
        while(airplane.getSpeed() != airplane.getAimedSpeed())
        {
        airplane.ChangeSpeed();
        }
        Assert.assertEquals("Speed should have changed",500.0 , airplane.getSpeed());
        
        airplane.setAimedSpeed(700);
        airplane.setStatus(Statusses.CRASHED);
        airplane.ChangeSpeed();
        Assert.assertEquals("Speed should have changed",0.0 , airplane.getSpeed());
                
    }

    /**
     * Test of ChangeDirection method, of class Airplane.
     */
    @Test
    public void testChangeDirection() {
        System.out.println("ChangeDirection");
        airplane.setAimedDirection(180);
        airplane.ChangeDirection();
        Assert.assertEquals("Direction should have changed", 180.0 , airplane.getDirection());
        
        airplane.setAimedDirection(180);
        while(airplane.getDirection() != airplane.getAimedDirection())
        {
            airplane.ChangeDirection();
        }
        Assert.assertEquals("Direction should have changed",180.0 , airplane.getDirection());
    }

    /**
     * Test of ChangeAltitude method, of class Airplane.
     */
    @Test
    public void testChangeAltitude() {
        System.out.println("ChangeAltitude");
        airplane.setAimedAltitude(3);
        airplane.ChangeAltitude();
        Assert.assertEquals("Altitude should have changed", 3.0 , airplane.getAimedAltitude());
        
        airplane.setAimedAltitude(2);
        while(airplane.getAltitude() != airplane.getAimedAltitude())
        {
            airplane.ChangeAltitude();
        }
        Assert.assertEquals("Altitude should have changed", 2.0 , airplane.getAimedAltitude());
    }

    /**
     * Test of Landing method, of class Airplane.
     */
    @Test
    public void testLanding() {
        System.out.println("Landing");
        airplane.setStatus(Statusses.INLANDINGQUEUE);
        airplane.Land();
        Assert.assertEquals("Status should have changed", Airplane.Statusses.LANDING , airplane.getStatus());
        System.out.println("1e voltooid");
    }
    
    @Test
    public void testTakeOff() {
        System.out.println("TakeOff");
        airplane.TakeOff(runway, runway.getDirection(), 0, 300.0);
        Assert.assertEquals("Status should have changed", Airplane.Statusses.TAKINGOFF, airplane.getStatus());
        Assert.assertEquals("Direction should have changed", 270.0, airplane.getAimedDirection());
        Assert.assertEquals("Speed should have changed", 300.0, airplane.getAimedSpeed());
        Assert.assertEquals("Altitude should have changed", 1000.0, airplane.getAimedAltitude());
    }
    
    @Test
    public void testCircling() {
        System.out.println("Circling");
        airplane.Circling();
        Assert.assertEquals("Direction should have changed by 0.5", 89.0, airplane.getDirection());
    }
    
    @Test
    public void testChangeFuel() {
        System.out.println("ChangeFuel");
        airplane.ChangeFuel();
        Assert.assertEquals("CurrentFuel should change.", 90, airplane.getCurrentFuel());
    }
    
    @Test
    public void testdistFrom() {
        System.out.println("distFrom");
        double distance;
        distance = airplane.distFrom(location.getLatitude(), location.getLongitude(), location2.getLatitude(), location2.getLongitude());
        Assert.assertEquals("Distance between point 1 and 2 is not correct", 5598531.406476725, distance);
    }
}
