/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author henk
 */
public class ACCTest {
    
    private CTA cta; //cta Thats being used for the test
    private GeoLocation loc; // Location of the CTA
    private ACC acc; //ACC thats being used for this test
    private Airplane apa; //Airplane thats in flight
    private Airplane apt; //Airplane thats ready for takeoff
    private Runway ra; //available runway
    private Runway ru; //unavailable runway

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
        apa = new Airplane(500, 300, 16000, "747-300", "Boeing", 300, 300, 500, 200, 1, 100, 450, 299, 100, 650);
        apt = new Airplane(500, 300, 1600, "747-300", "Boeing", 300, 300, 500, 200, 1, 100, 0, 299, 100, 651);
        ra = new Runway(1,1,50, 300, 180, true);
        ru = new Runway(1,1,50,300, 270, false);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of ChangeSpeed method, of class ACC.
     * Currently Airplane does not yet have a constructor. I will update the constructor and tests once this is done.
     */
    @Test (expected = AssignmentException.class)
    public void testChangeSpeed() throws AssignmentException {
        System.out.println("ChangeSpeed");
        acc.ChangeSpeed(200, apa);
        fail("AssignementException was expected");
        
        acc.ChangeSpeed(400, apa);
        Assert.assertEquals("Speed should have changed",400 , apa.getAimedSpeed());
        
        acc.ChangeSpeed(800, apa);
        fail("AssignmentException was expected");
    }

    /**
     * Test of ChangeDirection method, of class ACC.
     */
    @Test (expected = AssignmentException.class)
    public void testChangeDirection() throws AssignmentException {
        System.out.println("ChangeDirection");
        acc.ChangeDirection(400, apa);
        fail("AssignmentException was expected");
        
        acc.ChangeDirection(90, apa);
        Assert.assertEquals("Direction should have been changed", 90, apa.getAimedDirection());
    }
    
    /**
     * Test of ChangeHeight method, of class ACC.
     * Assumed that this is done in flightlevels rather then actual feet.
     */
    @Test (expected = AssignmentException.class)
    public void testChangeHeight() throws AssignmentException {
        System.out.println("ChangeHeight");
        acc.ChangeHeight(5, apa);
        fail("AssignmentException was expected");
        
        acc.ChangeHeight(2, apa);
        Assert.assertEquals("Height should have changed.", 600, apa.getAimedAltitude());
    }

    /**
     * Test of GiveRunwayLand method, of class ACC.
     */
    @Test
    public void GiveRunwayLand() throws AssignmentException {
        System.out.println("GiveRunwayLand");
        acc.GiveRunwayLand(ru, apa, ru.getDirection());
        fail("AssignmentException was expected because runway is unavailable.");
        
        acc.GiveRunwayLand(ra, apa, 400);
        fail("AssignementException was expected because the direction is not possible");
        
        acc.GiveRunwayLand(ra, apa, ra.getDirection());
        Assert.assertEquals("Runway did not change availability" ,false , ra.getAvailability());
        Assert.assertEquals("Airplane did not change direction.",180 ,apa.getAimedDirection());       
    }
    
    /*
     * Test of GIveRunwayTakeOff method, of class ACC.
     */
    @Test
    public void GiveRunwayTakeOff() {
        System.out.println("GiveRunwayTakeOff");
        
    }
}
