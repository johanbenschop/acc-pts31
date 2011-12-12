package atc.logic;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Johan Benschop & Jeanique van der Sanden
 */
public class FlightController {

    /**************Datafields***********/
    /**
     * The id of the flightcontroller 
     */
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
     * Method to assign a flightplan to this Flightcontroller
     * 
     */
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
    public void unassignFlight(Flightplan flightplan) {
        flights.remove(flightplan);
        flightplan.setAssignedController(null);
    }

    /**************Getters**************/
    public int controllingFlights() {
        return flights.size();
    }

    public int getNumberAssignedFlights() {
        return flights.size();
    }

    public int getID() {
        return ID;
    }

    public Iterator<Flightplan> getFlights() {
        return flights.iterator();
    }
}
