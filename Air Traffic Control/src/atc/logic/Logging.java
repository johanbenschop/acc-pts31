/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;
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
       String text = "\r" + AccID + " " + name + " " + command + " " + count;
       String TextfileName = AccID + ".txt";
       Writer output = null;
       FileOutputStream fStream = new FileOutputStream(TextfileName, true);
       DataOutputStream dStream = new DataOutputStream(fStream);
       //File file = new File(TextfileName);
       output = new BufferedWriter(new OutputStreamWriter(dStream));
       //output.write(text);
       output.write(text);
       output.close();
       System.out.println("logged");
       count++;
   }
}
