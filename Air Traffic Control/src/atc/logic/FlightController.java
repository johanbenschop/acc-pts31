package atc.logic;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Johan Benschop & Jeanique van der Sanden
 */
public class FlightController {
    private int ID;
    private static int lastID;
    private ArrayList<Flightplan> flights;
    
    public FlightController() {
        ID = lastID++;
        flights = new ArrayList<>();
    }
    
    public int controllingFlights() {
        return flights.size();
    }
    
    public void assignFlight(Flightplan flightplan) {
        if (flightplan.getAssignedController() == null || flightplan.getAssignedController() != this) {
            flights.add(flightplan);
            flightplan.setAssignedController(this);
        }
    }
    
    public void unassignFlight(Flightplan flightplan) {
        flights.remove(flightplan);
        flightplan.setAssignedController(null);
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
