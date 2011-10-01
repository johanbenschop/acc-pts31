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
public class AirplaneFactoryTest {
    
    public AirplaneFactoryTest() {
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
     * Test of getAirplanes method, of class AirplaneFactory.
     */
    @Test
    public void testGetAirplanes() {
        System.out.println("getAirplanes");
        AirplaneFactory instance = new AirplaneFactory();
        instance.getAirplanes();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
