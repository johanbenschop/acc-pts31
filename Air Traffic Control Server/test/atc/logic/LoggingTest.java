/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Sabrina
 */
public class LoggingTest {
    
    private int AccID; 
    private int AccID2;
    private String TextfileName;
    private String TextfileName2;
    public LoggingTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        AccID = 2;
        AccID2 = 3;
        TextfileName = AccID + ".txt";
        TextfileName2 = AccID2 + ".txt";
       
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of WriteCommand method, of class Logging.
     */
    @Test
    public void testWriteCommand() throws Exception {
        System.out.println("WriteCommand");
        String Name = "sab";
        String Command = "change hight to 2";
        String text = AccID + ": " + Name + " " + Command;
        Logging instance = new Logging(AccID);
        instance.WriteCommand(Name, Command);
        
        BufferedReader reader = null;
        FileInputStream fStream = new FileInputStream(TextfileName);
        DataInputStream dStream = new DataInputStream(fStream);
        reader = new BufferedReader(new InputStreamReader(dStream));
        String text2 = reader.readLine();
        reader.close();
        assertEquals(text, text2);
    }

    /**
     * Test of WriteCollision method, of class Logging.
     */
    @Test
    public void testWriteCollision() throws Exception {
        System.out.println("WriteCollision");
        long airplane1 = 345;
        long airplane2 = 476;
        String name = "sab";
        String status = "crashed";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        String text = AccID2 + ": " + airplane1 + " is in collision with " + airplane2 + ". status is " + status + ". Air traffic controller in control is: " + name + ". date: " + dateFormat.format(date);
        Logging instance = new Logging(AccID2);
        instance.WriteCollision(airplane1, airplane2, name, status);
        
        BufferedReader reader = null;
        FileInputStream fStream = new FileInputStream(TextfileName2);
        DataInputStream dStream = new DataInputStream(fStream);
        reader = new BufferedReader(new InputStreamReader(dStream));
        String text2 = reader.readLine();
        reader.close();
        assertEquals(text, text2);
    }
}
