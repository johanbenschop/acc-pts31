package atc.interfaces;

import java.util.GregorianCalendar;

/**
 *
 * @author Henk
 */
public interface IFlightplan {

    IAirplane getAirplane();

    /**
     * Getters
     */
    GregorianCalendar getArrivalDate();

    IFC getAssignedController();

    GregorianCalendar getDepartureDate();

    IAirport getDestinationAirport();

    int getFlightnumber();

    IAirport getTakeoffAirport();

    /**
     * Setters
     */
    void setAssignedController(IFC assignedController);

    /**
     * Method to return a string value of the Flightplan with its Flightnumber, takeoffAirport and destinationAirport
     *
     * @return A string value of the Flightplan with its Flightnumber, takeoffAirport and destinationAirport
     */
    String toString();
    
}
