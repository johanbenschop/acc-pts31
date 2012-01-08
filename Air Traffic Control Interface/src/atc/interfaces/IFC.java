/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import java.util.Iterator;

/**
 *
 * @author Henk
 */
public interface IFC {

    /**
     * Assigns a flightcontroller to an flighplan and adds add to a list
     * @param flightplan
     */
    void assignFlight(Flightplan flightplan);

    Iterator<Flightplan> getFlights();

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
    void unassignFlight(Flightplan flightplan);
    
}
