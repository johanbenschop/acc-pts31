/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import atc.interfaces.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
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

    IAirspace airspace;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws RemoteException {
    airspace = new Airspace();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of the loadAirportList method, of class Airspace.
     */
    @Test
    public void testloadAirportList() throws RemoteException {
        System.out.println("Load airportlist");

        try {
            airspace.loadAirportList();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Assert.assertEquals("Airportname should be Goraka", "Goroka", airspace.GetAirport(1).getAirportName());
    }


    /**
     * Test of the BorderControl method, of the class Airspace.
     */
    @Test
    public void testBorderControl() {
        System.out.println("BorderControl2");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
