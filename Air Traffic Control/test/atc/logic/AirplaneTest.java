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
    
    private GeoLocation geoLocation;
    private Airplane airplane;
    
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
    }
    
    @After
    public void tearDown() {
    }



    /**
     * Test of run method, of class Airplane.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        Airplane instance = null;
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Fly method, of class Airplane.
     */
    @Test
    public void testFly() {
        System.out.println("Fly");
        Airplane instance = null;
        instance.Fly();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ChangeGeoLocation method, of class Airplane.
     */
    @Test
    public void testChangeGeoLocation() {
        System.out.println("ChangeGeoLocation");
    }

    /**
     * Test of ChangeSpeed method, of class Airplane.
     */
    @Test
    public void testChangeSpeed() {
        System.out.println("ChangeSpeed");
        airplane.SetAimedSpeed(500);
        airplane.ChangeSpeed();
        airplane.run();
        Assert.assertEquals("Speed should have changed",500 , airplane.getAimedSpeed());
    }

    /**
     * Test of ChangeDirection method, of class Airplane.
     */
    @Test
    public void testChangeDirection() {
        System.out.println("ChangeDirection");
        Airplane instance = null;
        instance.ChangeDirection();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ChangeAltitude method, of class Airplane.
     */
    @Test
    public void testChangeAltitude() {
        System.out.println("ChangeAltitude");
        Airplane instance = null;
        instance.ChangeAltitude();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ChangeFuel method, of class Airplane.
     */
    @Test
    public void testChangeFuel() {
        System.out.println("ChangeFuel");
        Airplane instance = null;
        instance.ChangeFuel();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }



  
}
