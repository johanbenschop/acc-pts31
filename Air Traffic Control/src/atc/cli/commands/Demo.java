package atc.cli.commands;

import atc.cli.CommandLine;
import atc.gui.atc2;
import atc.logic.AirplaneFactory;
import atc.logic.Airport;
import atc.logic.AssignmentException;
import java.util.GregorianCalendar;

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

    public static String addFlightSchipholEindhoven() {
        CommandLine.println("Adding flight: Schiphol - Eindhoven");
        AirplaneFactory af = atc2.acc.GetAirplaneFactory(1);
        Airport ap_arr = atc2.acc.GetCTA().GetAirport(580);
        Airport ap_dest = atc2.acc.GetCTA().GetAirport(585);
        GregorianCalendar cal = new GregorianCalendar();
        atc2.acc.CreateFlight(af, ap_arr, ap_dest, cal, cal);
        return "Adding stuff....";
    }
}
