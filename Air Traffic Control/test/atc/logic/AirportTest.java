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
 * @author PuHa
 */
public class AirportTest {
    
    private Airport airport;
    private GeoLocation location;
    
    public AirportTest() {
        
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        location = new GeoLocation(1,1,1);
        airport = new Airport(12345, "Schiphol", "Amsterdam", "The Netherlands", "AS", "AS", location, 0, 0.1, "E");
    }
    
    @After
    public void tearDown() {
    }
}
