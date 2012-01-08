/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import java.util.GregorianCalendar;

/**
 *
 * @author Henk
 */
public interface IFlightplan {

    Airplane getAirplane();

    /**
     * Getters
     */
    GregorianCalendar getArrivalDate();

    FlightController getAssignedController();

    GregorianCalendar getDepartureDate();

    Airport getDestinationAirport();

    int getFlightnumber();

    Airport getTakeoffAirport();

    /**
     * Setters
     */
    void setAssignedController(FlightController assignedController);

    /**
     * Method to return a string value of the Flightplan with its Flightnumber, takeoffAirport and destinationAirport
     *
     * @return A string value of the Flightplan with its Flightnumber, takeoffAirport and destinationAirport
     */
    String toString();
    
}
