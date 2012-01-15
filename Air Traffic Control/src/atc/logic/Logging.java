/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import java.io.*;
import java.util.*;

/**
 *
 * @author Sabrina
 */

public class Logging {
   private int AccID; 
   private int count = 0;
   private String TextfileName;
   
   public Logging(int accID){
       AccID = accID;
       TextfileName = AccID + ".txt";
    }
   
   public void WriteCommand(String Name, String Command)throws IOException{
       String name = Name;
       String command = Command;
       String text = AccID + ": " + name + " " + command;
       
       Writer output = null;
       FileOutputStream fStream = new FileOutputStream(TextfileName, true);
       DataOutputStream dStream = new DataOutputStream(fStream);
       output = new BufferedWriter(new OutputStreamWriter(dStream));
       output.write(text);
       output.write(java.security.AccessController.doPrivileged(new sun.security.action.GetPropertyAction("line.separator")));
       output.close();
       System.out.println("logged");
   }
   
   public void WriteCollision(long airplane1, long airplane2, String name, String status)throws IOException{
       Writer output = null;
       String date = Calendar.MONTH + "/" + Calendar.DATE + "/" + Calendar.YEAR + " " + Calendar.HOUR + ":" + Calendar.MINUTE;
       String text = AccID + ": " + airplane1 + " is in collision with " + airplane2 + ". status is " + status + ". Air traffic controller in control is: " + name + ". date: " + date;
       FileOutputStream fStream = new FileOutputStream(TextfileName, true);
       DataOutputStream dStream = new DataOutputStream(fStream);
       output = new BufferedWriter(new OutputStreamWriter(dStream));
       output.write(text);
       output.write(java.security.AccessController.doPrivileged(new sun.security.action.GetPropertyAction("line.separator")));
       output.close();
       System.out.println("logged");
   }
}
