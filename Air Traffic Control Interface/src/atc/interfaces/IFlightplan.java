package atc.interfaces;

import java.rmi.*;
import java.util.GregorianCalendar;

/**
 *
 * @author Henk
 */
public interface IFlightplan extends Remote {

    IAirplane getAirplane() throws RemoteException;

    /**
     * Getters
     */
    GregorianCalendar getArrivalDate() throws RemoteException;

    IFC getAssignedController() throws RemoteException;

    GregorianCalendar getDepartureDate() throws RemoteException;

    IAirport getDestinationAirport() throws RemoteException;

    int getFlightnumber() throws RemoteException;

    IAirport getTakeoffAirport() throws RemoteException;

    /**
     * Setters
     */
    void setAssignedController(IFC assignedController) throws RemoteException;

    /**
     * Method to return a string value of the Flightplan with its Flightnumber, takeoffAirport and destinationAirport
     *
     * @return A string value of the Flightplan with its Flightnumber, takeoffAirport and destinationAirport
     */
//    String ToString();    
}