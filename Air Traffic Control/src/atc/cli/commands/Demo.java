package atc.cli.commands;

import atc.cli.CommandLine;
import atc.gui.atc2;
import atc.logic.AirplaneFactory;
import atc.logic.Airport;
import atc.logic.Airspace;
import atc.logic.AssignmentException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author johan
 */
public class Demo {

    

    public static String collision() throws AssignmentException {
        try {
            atc2.airspace.getCurrentACC().CreateFlight(null, null, null, null, null);

            CommandLine.println("Searching...");
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return "Adding stuff....";
    }

    public static String addFlightSchipholEindhoven() {
        CommandLine.println("Adding flight: Schiphol - Eindhoven");
        AirplaneFactory af = atc2.airspace.getCurrentACC().GetAirplaneFactory(1);
        Airport ap_arr = atc2.airspace.GetAirport(580);
        Airport ap_dest = atc2.airspace.GetAirport(585);
        GregorianCalendar cal = new GregorianCalendar();
        atc2.airspace.getCurrentACC().CreateFlight(af, ap_arr, ap_dest, cal, cal);
        return "Adding stuff....";
    }

    public static String addFlightEindhovenSchiphol() {
        CommandLine.println("Adding flight: Eindhoven - Schiphol");
        AirplaneFactory af = atc2.airspace.getCurrentACC().GetAirplaneFactory(1);
        Airport ap_arr = atc2.airspace.GetAirport(585);
        Airport ap_dest = atc2.airspace.GetAirport(580);
        GregorianCalendar cal = new GregorianCalendar();
        atc2.airspace.getCurrentACC().CreateFlight(af, ap_arr, ap_dest, cal, cal);
        return "Adding stuff....";
    }

    public static String addCrashEHVAMS() {
        addFlightEindhovenSchiphol();
        addFlightSchipholEindhoven();
        return "Added two planes that will crash....";
    }

    public static String addFlightEindhovenBudel() {
        CommandLine.println("Adding flight: Eindhoven - Budel");
        AirplaneFactory af = atc2.airspace.getCurrentACC().GetAirplaneFactory(1);
        Airport ap_arr = atc2.airspace.GetAirport(585);
        Airport ap_dest = atc2.airspace.GetAirport(581);
        GregorianCalendar cal = new GregorianCalendar();
        atc2.airspace.getCurrentACC().CreateFlight(af, ap_arr, ap_dest, cal, cal);
        return "Adding stuff....";
    }

    public static String addFlightBudelEindhoven() {
        CommandLine.println("Adding flight: Budel - Eindhoven");
        AirplaneFactory af = atc2.airspace.getCurrentACC().GetAirplaneFactory(1);
        Airport ap_arr = atc2.airspace.GetAirport(581);
        Airport ap_dest = atc2.airspace.GetAirport(585);
        GregorianCalendar cal = new GregorianCalendar();
        atc2.airspace.getCurrentACC().CreateFlight(af, ap_arr, ap_dest, cal, cal);
        return "Adding stuff....";
    }

    public static String addCrashEHVBUD() {
        addFlightEindhovenBudel();
        addFlightBudelEindhoven();
        return "Added two planes that will crash....";
    }
    
    public static String addFlightBastogne() {
        CommandLine.println("Adding flight: St.Hubert - Luxemburg Stad");
        AirplaneFactory af = atc2.airspace.getCurrentACC().GetAirplaneFactory(1);
        Airport ap_arr = atc2.airspace.GetAirport(313);
        Airport ap_dest = atc2.airspace.GetAirport(629);
        GregorianCalendar cal = new GregorianCalendar();
        atc2.airspace.getCurrentACC().CreateFlight(af, ap_arr, ap_dest, cal, cal);
        return "Adding stuff....";
    }
    
    public static String addFlightTurkey() {
        CommandLine.println("Adding flight: Eindhoven - Ankara");
        AirplaneFactory af = atc2.airspace.getCurrentACC().GetAirplaneFactory(1);
        Airport ap_arr = atc2.airspace.GetAirport(585);
        Airport ap_dest = atc2.airspace.GetAirport(1684);
        GregorianCalendar cal = new GregorianCalendar();
        atc2.airspace.getCurrentACC().CreateFlight(af, ap_arr, ap_dest, cal, cal);
        return "Adding stuff....";
    }

    public static String addRandomFlights(int amount) {
        Random random = new Random();
        ArrayList<Integer> airportIDs = new ArrayList<>();
        AirplaneFactory af = atc2.airspace.getCurrentACC().GetAirplaneFactory(1);
        GregorianCalendar cal = new GregorianCalendar();

        // We put the airport ID's in an ArrayList so we can get the airport
        // randomly, since not all airports might be availeble and an iterator
        // can not get a random index, only next, next, next, hence the name iterator.
        for (Iterator<Airport> it = atc2.airspace.getCurrentACC().GetCTA().GetAirports(); it.hasNext();) {
            airportIDs.add(it.next().getAirportID());
        }

        int airports = airportIDs.size();

        for (int i = 0; i < amount; i++) {
            int arr_id = random.nextInt(airports);
            int dest_id = random.nextInt(airports);

            // We need to be not sure that the flight doesn't go anywhere (reread that sentence)
            while (arr_id == dest_id) {
                arr_id = random.nextInt(airports);
                dest_id = random.nextInt(airports);
            }

            Airport ap_arr = atc2.airspace.getCurrentACC().GetCTA().GetAirport(airportIDs.get(arr_id));
            Airport ap_dest = atc2.airspace.getCurrentACC().GetCTA().GetAirport(airportIDs.get(dest_id));

            atc2.airspace.getCurrentACC().CreateFlight(af, ap_arr, ap_dest, cal, cal);
        }

        return "Added " + amount + " of random flights.";
    }
}
