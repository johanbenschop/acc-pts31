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
    
    private Runway runway;
    
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
        int longitude = 1, latitude = 1, altitude = 1, length = 30, direction = 2;
        boolean availability = false;
        runway = new Runway(longitude, latitude, altitude, length, direction, availability);
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
        runway.ChangeAvailability(false);
        Assert.assertEquals("Availability has changed to false", false, runway.getAvailability());
        runway.ChangeAvailability(true);
        Thread.sleep(180001);
        Assert.assertEquals("Availability has changed to true", true, runway.getAvailability());
   }
}
