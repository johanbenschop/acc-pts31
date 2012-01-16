package atc.interfaces;

import java.rmi.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Henk
 */
public interface IFC extends Remote {

    /**
     * Assigns a flightcontroller to an flighplan and adds add to a list
     * @param flightplan
     */
    void assignFlight(IFlightplan flightplan) throws RemoteException;

    ArrayList<IFlightplan> getFlights() throws RemoteException;

    int getID() throws RemoteException;

    /**
     * Gets the amount of flights from the Flightcontroller.
     * @return
     */
    int getNumberAssignedFlights() throws RemoteException;

    /**
     * Method to unassign all flightplan from this Flightcontroller
     */
    void unassignAllFlights() throws RemoteException;

    /**
     * Method to unassign a flightplan from this Flightcontroller
     *
     */
    void unassignFlight(IFlightplan flightplan) throws RemoteException;
    
}
