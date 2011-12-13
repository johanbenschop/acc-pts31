/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Henk
 */
public class CollisionTest {
    
    public CollisionTest() {
    }
    
    private Airplane target;
    private Airplane crashobject;
    private GeoLocation loc1;
    private GeoLocation loc2;
    private GeoLocation dest1;
    private GeoLocation dest2;
    private Collision coll;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        loc1 = new GeoLocation(52.3019, 4.7609);
        loc2 = new GeoLocation(52.3020, 4.7610);
        dest1 = new GeoLocation(10.00, 10.00);
        dest2 = new GeoLocation(20.00, 20.00);
        target = new Airplane(900, 300, 1, "747", "Boeing", 1, 1, 1, 1, 1, 10, 600, 1, 1000, loc1, dest1, 1);
        crashobject = new Airplane(900, 300, 1, "747", "Boeing", 1, 1, 1, 1, 1, 350, 600, 1, 1000, loc2, dest2, 1);
        coll = new Collision(target, crashobject);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of colldetect method, of class Collision.
     */
    @Test
    public void testColldetect() {
        System.out.println("colldetect");
        coll.colldetect();
        assertTrue("vliegtuigen zijn niet gecrashed", target.getStatus().equals(Airplane.Statusses.CRASHED));
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of distFrom method, of class Collision.
     */
    @Test
    public void testDistFrom() {
        System.out.println("distFrom");
        double expResult = 0.69837940036599;
        double result = coll.distFrom(loc1.getLatitude(), loc1.getLongitude(), loc2.getLatitude(), loc2.getLongitude());
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
    }
}
