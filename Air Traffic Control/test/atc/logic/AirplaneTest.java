/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import atc.logic.Airplane.Statusses;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author PuHa
 */
public class AirplaneTest {
    
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
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of ToString method, of class Airplane.
     */
    @Test
    public void testToString() {
        System.out.println("ToString");
        Airplane instance = null;
        String expResult = "";
        String result = instance.ToString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        Airplane instance = null;
        instance.ChangeGeoLocation();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ChangeSpeed method, of class Airplane.
     */
    @Test
    public void testChangeSpeed() {
        System.out.println("ChangeSpeed");
        Airplane instance = null;
        instance.ChangeSpeed();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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

    /**
     * Test of SetAirplaneNumber method, of class Airplane.
     */
    @Test
    public void testSetAirplaneNumber() {
        System.out.println("SetAirplaneNumber");
        int airplaneNumber = 0;
        Airplane instance = null;
        instance.SetAirplaneNumber(airplaneNumber);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SetAimedSpeed method, of class Airplane.
     */
    @Test
    public void testSetAimedSpeed() {
        System.out.println("SetAimedSpeed");
        double speed = 0.0;
        Airplane instance = null;
        instance.SetAimedSpeed(speed);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SetAimedDirection method, of class Airplane.
     */
    @Test
    public void testSetAimedDirection() {
        System.out.println("SetAimedDirection");
        double direction = 0.0;
        Airplane instance = null;
        instance.SetAimedDirection(direction);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SetAimedAltitude method, of class Airplane.
     */
    @Test
    public void testSetAimedAltitude() {
        System.out.println("SetAimedAltitude");
        double altitude = 0.0;
        Airplane instance = null;
        instance.SetAimedAltitude(altitude);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStatus method, of class Airplane.
     */
    @Test
    public void testSetStatus() {
        System.out.println("setStatus");
        Statusses Status = null;
        Airplane instance = null;
        instance.setStatus(Status);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

  
}
