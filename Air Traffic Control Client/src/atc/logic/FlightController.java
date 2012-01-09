package atc.logic;

import atc.interfaces.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.naming.*;
import java.util.Enumeration;

/**
 *
 * @author Johan Benschop & Jeanique van der Sanden
 */
public class FlightController implements IAirFC {

    private int ID;
    /**
     * The last ID that was given to a flightcontroller (+1 = next free ID) 
     */
    private static int lastID;
    /**
     * Arraylist of lightplans assigned to the flightcontroller
     */
    private ArrayList<IFlightplan> flights;

    /***************Constructor**********/
    /**
     * This is a constructor used for making a Flightcontroller it assigns it
     * an ID and creates a new Arraylist to contain flightplans
     * 
     */
    public FlightController() {
        try {
            Context namingContext = new InitialContext();

            System.out.print("RMI registry bindings: ");
            Enumeration<NameClassPair> e = namingContext.list("rmi://145.93.232.92");//subject to change the IP of the server, currently IP of Henk's laptop but yeah they change.
            while (e.hasMoreElements()) {
                System.out.println(e.nextElement().getName());
            }
            String url = "rmi://145.93.232.92/ATCServer";
            System.out.println(namingContext.lookup(url).toString());
        } catch (NamingException ex) {
            System.out.println(ex.toString());
        }
        ID = lastID++;
        flights = new ArrayList<>();
    }

    
    /**
     * Assigns a flightcontroller to an flighplan and adds add to a list
     * @param flightplan 
     */
    @Override
    public void assignFlight(IFlightplan flightplan) {
        if (flightplan.getAssignedController() == null || flightplan.getAssignedController() != this) {
            flights.add(flightplan);
            flightplan.setAssignedController((IFC)this);
        }
    }

    /**
     * Method to unassign a flightplan from this Flightcontroller
     * 
     */
    @Override
    public void unassignFlight(IFlightplan flightplan) {
        flights.remove(flightplan);
        flightplan.setAssignedController(null);
    }

    /**
     * Method to unassign all flightplan from this Flightcontroller
     */
    @Override
    public void unassignAllFlights() {
        for (IFlightplan flightplan : flights) {
            flightplan.setAssignedController(null);
        }
    }

    /**
     * Gets the amount of flights from the Flightcontroller.
     * @return 
     */
    @Override
    public int getNumberAssignedFlights() {
        return flights.size();
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public Iterator<IFlightplan> getFlights() {
        return flights.iterator();
    }
}
