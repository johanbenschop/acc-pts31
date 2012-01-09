package atc.logic;

import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import java.io.*;

/**
 *
 * @author Sabrina
 */

public class Logging {
   private int AccID; 
   private int count = 0;
   
   public Logging(int accID){
       AccID = accID;
    }
   
   public void WriteCommand(String Name, String Command)throws IOException{
       String name = Name;
       String command = Command;
       String text = "\r" + AccID + " " + name + " " + command;
       String TextfileName = AccID + ".txt";
       Writer output = null;
       FileOutputStream fStream = new FileOutputStream(TextfileName, true);
       DataOutputStream dStream = new DataOutputStream(fStream);
       //File file = new File(TextfileName);
       output = new BufferedWriter(new OutputStreamWriter(dStream));
       //output.write(text);
       output.write(text);
       output.write(java.security.AccessController.doPrivileged(new sun.security.action.GetPropertyAction("line.separator")));
       output.close();
       System.out.println("logged");
   }
}
