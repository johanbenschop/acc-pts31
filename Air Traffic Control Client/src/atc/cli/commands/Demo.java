package atc.cli.commands;

import atc.cli.CommandLine;
import atc.gui.atc2;
import atc.interfaces.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author johan
 */
public class Demo {

    public static String collision() throws AssignmentException, RemoteException {
        try {
            atc2.FC.getChosenACC().CreateFlight(null, null, null, null, null);

            CommandLine.println("Searching...");
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return "Adding stuff....";
    }

    public static String addFlightSchipholEindhoven() throws RemoteException {
        CommandLine.println("Adding flight: Schiphol - Eindhoven");
        IAirplaneFactory af = atc2.FC.getChosenACC().GetAirplaneFactory(1);
        IAirport ap_dept = atc2.airspace.GetAirport(580);
        IAirport ap_dest = atc2.airspace.GetAirport(585);
        GregorianCalendar cal = new GregorianCalendar();
        atc2.FC.getChosenACC().CreateFlight(af, ap_dept, ap_dest, cal, cal);
        return "Adding stuff....";
    }

    public static String addFlightEindhovenSchiphol() throws RemoteException {
        CommandLine.println("Adding flight: Eindhoven - Schiphol");
        IAirplaneFactory af = atc2.FC.getChosenACC().GetAirplaneFactory(1);
        IAirport ap_dept = atc2.airspace.GetAirport(585);
        IAirport ap_dest = atc2.airspace.GetAirport(580);
        GregorianCalendar cal = new GregorianCalendar();
        atc2.FC.getChosenACC().CreateFlight(af, ap_dept, ap_dest, cal, cal);
        return "Adding stuff....";
    }

    public static String addCrashEHVAMS() throws RemoteException {
        addFlightEindhovenSchiphol();
        addFlightSchipholEindhoven();
        return "Added two planes that will crash....";
    }

    public static String addFlightEindhovenBudel() throws RemoteException {
        CommandLine.println("Adding flight: Eindhoven - Budel");
        IAirplaneFactory af = atc2.FC.getChosenACC().GetAirplaneFactory(1);
        IAirport ap_dept = atc2.airspace.GetAirport(585);
        IAirport ap_dest = atc2.airspace.GetAirport(581);
        GregorianCalendar cal = new GregorianCalendar();
        atc2.FC.getChosenACC().CreateFlight(af, ap_dept, ap_dest, cal, cal);
        return "Adding stuff....";
    }

    public static String addFlightBudelEindhoven() throws RemoteException {
        CommandLine.println("Adding flight: Budel - Eindhoven");
        IAirplaneFactory af = atc2.FC.getChosenACC().GetAirplaneFactory(1);
        IAirport ap_dept = atc2.airspace.GetAirport(581);
        IAirport ap_dest = atc2.airspace.GetAirport(585);
        GregorianCalendar cal = new GregorianCalendar();
        atc2.FC.getChosenACC().CreateFlight(af, ap_dept, ap_dest, cal, cal);
        return "Adding stuff....";
    }
    
    public static String addFlightBastogne() throws RemoteException {
        CommandLine.println("Adding flight: St.Hubert - Luxemburg Stad");
        IAirplaneFactory af = atc2.FC.getChosenACC().GetAirplaneFactory(1);
        IAirport ap_dept = atc2.airspace.GetAirport(313);
        IAirport ap_dest = atc2.airspace.GetAirport(629);
        GregorianCalendar cal = new GregorianCalendar();
        atc2.FC.getChosenACC().CreateFlight(af, ap_dept, ap_dest, cal, cal);
        return "Adding stuff....";
    }

    public static String addCrashEHVBUD() throws RemoteException {
        addFlightEindhovenBudel();
        addFlightBudelEindhoven();
        return "Added two planes that will crash....";
    }

    public static String addRandomFlights(int amount) throws RemoteException {
        Random random = new Random();
        ArrayList<Integer> airportIDs = new ArrayList<>();
        IAirplaneFactory af = atc2.FC.getChosenACC().GetAirplaneFactory(1);
        GregorianCalendar cal = new GregorianCalendar();

        // We put the airport ID's in an ArrayList so we can get the airport
        // randomly, since not all airports might be availeble and an iterator
        // can not get a random index, only next, next, next, hence the name iterator.
        for (Iterator<IAirport> it = atc2.FC.getChosenACC().GetCTA().GetAirports().iterator(); it.hasNext();) {
            airportIDs.add(it.next().getAirportID());
        }

        int airports = airportIDs.size();

        for (int i = 0; i < amount; i++) {
            int dept_id = random.nextInt(airports);
            int dest_id = random.nextInt(airports);

            // We need to be not sure that the flight doesn't go anywhere (reread that sentence)
            while (dept_id == dest_id) {
                dept_id = random.nextInt(airports);
                dest_id = random.nextInt(airports);
            }

            IAirport ap_dept = atc2.FC.getChosenACC().GetCTA().GetAirport(airportIDs.get(dept_id));
            IAirport ap_dest = atc2.FC.getChosenACC().GetCTA().GetAirport(airportIDs.get(dest_id));

            atc2.FC.getChosenACC().CreateFlight(af, ap_dept, ap_dest, cal, cal);
        }

        return "Added " + amount + " of random flights.";
    }
    
    public static String addFlightTurkey() throws RemoteException {
        CommandLine.println("Adding flight: Eindhoven - Ankara");
        IAirplaneFactory af = atc2.FC.getChosenACC().GetAirplaneFactory(1);
        IAirport ap_arr = atc2.airspace.GetAirport(585);
        IAirport ap_dest = atc2.airspace.GetAirport(1684);
        GregorianCalendar cal = new GregorianCalendar();
        atc2.FC.getChosenACC().CreateFlight(af, ap_arr, ap_dest, cal, cal);
        return "Adding stuff....";
    }
}
