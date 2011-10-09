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
 * @author henk
 */
public class ACCTest {
    
    private CTA cta;
    private GeoLocation loc;
    private ACC acc;
    private Airplane ap;
    private Runway ra;
    private Runway ru;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    /**
     * Values of the seperate instances still have to be set correct.
     * @throws Exception 
     */
    @Before
    public void setUp() throws Exception {
        loc = new GeoLocation(1,1,1);
        cta = new CTA(loc, 1,1);
        acc = new ACC(1, cta);
        ap = new Airplane();
        ra = new Runway();
        ru = new Runway();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of ChangeSpeed method, of class ACC.
     * Currently Airplane does not yet have a constructor. I will update the constructor and tests once this is done.
     */
    @Test
    public void testChangeSpeed() {
        System.out.println("ChangeSpeed");
        boolean passed = acc.ChangeSpeed(100, ap);
        assertFalse("Is below minimum speed", passed);
        passed = acc.ChangeSpeed(100000, ap);
        assertFalse("Is above maximum speed", passed);
        passed = acc.ChangeSpeed(400, ap);
        assertTrue("Is a possible speed", passed);
    }

    /**
     * Test of ChangeDirection method, of class ACC.
     */
    @Test
    public void testChangeDirection() {
        System.out.println("ChangeDirection");
        boolean passed = acc.ChangeDirection(370, ap);
        assertFalse("370 graden is geen mogelijkheid", passed);
        passed = acc.ChangeDirection(180, ap);
        assertTrue("Possible direction", passed);
    }
    
    /**
     * Test of ChangeHeight method, of class ACC.
     * Assumed that this is done in flightlevels rather then actual feet.
     */
    @Test
    public void testChangeHeight() {
        System.out.println("ChangeHeight");
        boolean passed = acc.ChangeHeight(5, ap);
        assertFalse("Not a possible flightlevel", passed);
        passed = acc.ChangeHeight(2, ap);
        assertTrue("This is a possible flightlevel", passed);
    }

    /**
     * Test of GiveRunwayLand method, of class ACC.
     */
    @Test
    public void GiveRunwayLand() {
        System.out.println("GiveRunwayLand");
        boolean passed = acc.GiveRunwayLand(ra, ap, 160);
        assertTrue("runway is available", passed);
        passed = acc.GiveRunwayLand(ra, ap, 270);
        assertFalse("runway is unavailable", passed);
    }
    
    /*
     * Test of GIveRunwayTakeOff method, of class ACC.
     */
    @Test
    public void GiveRunwayTakeOff() {
        System.out.println("GiveRunwayTakeOff");
        boolean passed = acc.GiveRunwayTakeOff(ra, ap, 90, 2, 350);
        assertTrue("runway is available", passed);
        passed = acc.GiveRunwayTakeOff(ru, ap, 104, 3, 450);
        assertFalse("runway is unavailable", passed);
    }
}
