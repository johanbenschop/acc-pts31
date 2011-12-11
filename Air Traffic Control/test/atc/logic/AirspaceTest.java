/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import java.io.FileNotFoundException;
import java.io.IOException;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author JNeek
 */
public class AirspaceTest {

    Airspace airspace = new Airspace();
    
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
       @Test
    public void testloadAirportList() {
        System.out.println("Load airportlist");
        
        try {
            airspace.loadAirportList();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Assert.assertEquals("Airportname should be Goraka", "Goroka", airspace.GetAirport(1).getAirportName());
   }
}
