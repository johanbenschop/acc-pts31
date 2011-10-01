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
 * @author johan
 */
public class AirplaneStatussesTest {
    
    public AirplaneStatussesTest() {
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
     * Test of values method, of class AirplaneStatusses.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        AirplaneStatusses[] expResult = null;
        AirplaneStatusses[] result = AirplaneStatusses.values();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of valueOf method, of class AirplaneStatusses.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "";
        AirplaneStatusses expResult = null;
        AirplaneStatusses result = AirplaneStatusses.valueOf(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
