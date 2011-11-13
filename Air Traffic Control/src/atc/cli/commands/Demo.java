package atc.cli.commands;

import atc.cli.CommandLine;
import atc.gui.atc2;
import atc.logic.AssignmentException;

/**
 *
 * @author johan
 */
public class Demo {
    public static String collision() throws AssignmentException {
        try {
            atc2.acc.CreateFlight(null, null, null, null, null);
            
            CommandLine.println("Searching...");
            Thread.sleep(2000);
            } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return "Adding stuff....";
    }
}
