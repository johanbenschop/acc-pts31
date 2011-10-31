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
    
    private GeoLocation location;
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
        airplane = new Airplane(500, 300, 16000, "747-300", "Boeing", 300, 300, 500, 200, 1, 0, 0, 299, 0, 012345);
        location = new GeoLocation(10, 10, 10);
        runway = new Runway(1,1,50,300, 270, false);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of ChangeGeoLocation method, of class Airplane.
     */
    @Test
    public void testChangeGeoLocation() {
        System.out.println(location.getAltitude());
        System.out.println(location.getLongitude());
        System.out.println("ChangeGeoLocation");
        double latitudeTravelled = 10 * Math.sin(30);
        double longitudeTravelled = 10 * Math.cos(30);
        System.out.println(latitudeTravelled);
        System.out.println(longitudeTravelled);
        location.setLatitude((latitudeTravelled / 110.54) + location.getLatitude());
        location.setLongitude((longitudeTravelled / (111.320*Math.cos(location.getLatitude()))) + location.getLongitude());
        System.out.println(location.getLatitude());
        System.out.println(location.getLongitude());
        Assert.assertEquals("Latitude should have changed", 9.910617728958488 , location.getLatitude());
        Assert.assertEquals("Longitude should have changed",9.984330154398483 , location.getLongitude());
    }
    
    /**
     * Test of ChangeSpeed method, of class Airplane.
     */
    @Test
    public void testChangeSpeed() {
        System.out.println("ChangeSpeed");
        airplane.SetAimedSpeed(200);
        airplane.ChangeSpeed();
        Assert.assertEquals("Speed should have changed",1.0 , airplane.getSpeed());
        System.out.println("1e voltooid");
        
        airplane.SetAimedSpeed(500);
        while(airplane.getSpeed() != airplane.getAimedSpeed())
        {
        airplane.ChangeSpeed();
        }
        Assert.assertEquals("Speed should have changed",500.0 , airplane.getSpeed());
        System.out.println("2e voltooid");
        
        airplane.SetAimedSpeed(700);
        airplane.setStatus(Statusses.CRASHED);
        airplane.ChangeSpeed();
        Assert.assertEquals("Speed should have changed",0.0 , airplane.getSpeed());
        System.out.println("3e voltooid");
                
    }

    /**
     * Test of ChangeDirection method, of class Airplane.
     */
    @Test
    public void testChangeDirection() {
        System.out.println("ChangeDirection");
        airplane.SetAimedDirection(180);
        airplane.ChangeDirection();
        Assert.assertEquals("Direction should have changed", 0.3 , airplane.getDirection());
        System.out.println("1e voltooid");
        
        airplane.SetAimedDirection(180);
        while(airplane.getDirection() != airplane.getAimedDirection())
        {
            airplane.ChangeDirection();
        }
        Assert.assertEquals("Direction should have changed",180.0 , airplane.getDirection());
        System.out.println("2e voltooid");
    }

    /**
     * Test of ChangeAltitude method, of class Airplane.
     */
    @Test
    public void testChangeAltitude() {
        System.out.println("ChangeAltitude");
        airplane.SetAimedAltitude(100);
        airplane.ChangeAltitude();
        Assert.assertEquals("Altitude should have changed", 2.0 , airplane.getAltitude());
        System.out.println("1e voltooid");
        
        airplane.SetAimedAltitude(600);
        while(airplane.getAltitude() != airplane.getAimedAltitude())
        {
            airplane.ChangeAltitude();
        }
        Assert.assertEquals("Altitude should have changed", 600.0 , airplane.getAltitude());
        System.out.println("2e voltooid");
    }

    /**
     * Test of Landing method, of class Airplane.
     */
    @Test
    public void testLanding() {
        System.out.println("Landing");
        airplane.setStatus(Statusses.INFLIGHT);
        airplane.Landing(runway, 50);
        Assert.assertEquals("Status should have changed", Airplane.Statusses.LANDING , airplane.getStatus());
        System.out.println("1e voltooid");
    }
}
