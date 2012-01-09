package atc.interfaces;

import java.util.Iterator;

/**
 *
 * @author Henk
 */
public interface IAirFC {

    /**
     * Assigns a flightcontroller to an flighplan and adds add to a list
     * @param flightplan
     */
    void assignFlight(IFlightplan flightplan);

    Iterator<IFlightplan> getFlights();

    int getID();

    /**
     * Gets the amount of flights from the Flightcontroller.
     * @return
     */
    int getNumberAssignedFlights();

    /**
     * Method to unassign all flightplan from this Flightcontroller
     */
    void unassignAllFlights();

    /**
     * Method to unassign a flightplan from this Flightcontroller
     *
     */
    void unassignFlight(IFlightplan flightplan);
    
}
