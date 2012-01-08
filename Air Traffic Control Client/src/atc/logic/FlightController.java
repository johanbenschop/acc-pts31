

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Johan Benschop & Jeanique van der Sanden
 */
public class FlightController implements NewInterface {

    private int ID;
    /**
     * The last ID that was given to a flightcontroller (+1 = next free ID) 
     */
    private static int lastID;
    /**
     * Arraylist of lightplans assigned to the flightcontroller
     */
    private ArrayList<Flightplan> flights;

    /***************Constructor**********/
    /**
     * This is a constructor used for making a Flightcontroller it assigns it
     * an ID and creates a new Arraylist to contain flightplans
     * 
     */
    public FlightController() {
        ID = lastID++;
        flights = new ArrayList<>();
    }

    
    /**
     * Assigns a flightcontroller to an flighplan and adds add to a list
     * @param flightplan 
     */
    @Override
    public void assignFlight(Flightplan flightplan) {
        if (flightplan.getAssignedController() == null || flightplan.getAssignedController() != this) {
            flights.add(flightplan);
            flightplan.setAssignedController(this);
        }
    }

    /**
     * Method to unassign a flightplan from this Flightcontroller
     * 
     */
    @Override
    public void unassignFlight(Flightplan flightplan) {
        flights.remove(flightplan);
        flightplan.setAssignedController(null);
    }

    /**
     * Method to unassign all flightplan from this Flightcontroller
     */
    @Override
    public void unassignAllFlights() {
        for (Flightplan flightplan : flights) {
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
    public Iterator<Flightplan> getFlights() {
        return flights.iterator();
    }
}
