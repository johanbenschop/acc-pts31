package atc.logic;

import atc.interfaces.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Johan Benschop & Jeanique van der Sanden
 */
public class FlightController implements IFC {

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
        ID = lastID++;
        flights = new ArrayList<IFlightplan>();
    }

    
    /**
     * Assigns a flightcontroller to an flighplan and adds add to a list
     * @param flightplan 
     */
    public void assignFlight(IFlightplan flightplan) {
        if (flightplan.getAssignedController() == null || flightplan.getAssignedController() != this) {
            flights.add(flightplan);
            flightplan.setAssignedController(this);
        }
    }

    /**
     * Method to unassign a flightplan from this Flightcontroller
     * 
     */
    public void unassignFlight(IFlightplan flightplan) {
        flights.remove(flightplan);
        flightplan.setAssignedController(null);
    }

    /**
     * Method to unassign all flightplan from this Flightcontroller
     */
    public void unassignAllFlights() {
        for (IFlightplan flightplan : flights) {
            flightplan.setAssignedController(null);
        }
    }

    /**
     * Gets the amount of flights from the Flightcontroller.
     * @return 
     */
    public int getNumberAssignedFlights() {
        return flights.size();
    }

    public int getID() {
        return ID;
    }

    public Iterator<IFlightplan> getFlights() {
        return flights.iterator();
    }
}
