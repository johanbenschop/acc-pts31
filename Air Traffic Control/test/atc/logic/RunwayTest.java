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
 * @author Mateusz
 */
public class RunwayTest {
    
    public RunwayTest() {
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
     * Test of ChangeAvailability method, of class Runway.
     */
    @Test
    public void testChangeAvailability() throws InterruptedException {
        System.out.println("ChangeAvailability");
        int longitude = 1, latitude = 1, altitude = 1, length = 30, direction = 2;
        boolean availability = false;
        Runway instance = new Runway(longitude, latitude, altitude, length, direction, availability);
        instance.ChangeAvailability(false);
        Assert.assertEquals("Availability has changed to false", false, instance.getAvailability());
        instance.ChangeAvailability(true);
        Thread.sleep(180001);
        Assert.assertEquals("Availability has changed to true", true, instance.getAvailability());
   }
}
