

import java.util.*;

/**
 * All information about a planned flight is found in Flightplan.
 * 
 */
public class Flightplan implements IFlightplan {

    /**************Datafields***********/
    /**
     * The Airport where the flight is going to. 
     */
    private Airport destinationAirport;
    /**
     * The Airport where the flight is taking off from
     */
    private Airport takeoffAirport;
    /**
     * Flightnumber of the flight.
     */
    private int Flightnumber;
    /**
     * The date and time when the flight departs
     */
    private GregorianCalendar DepartureDate;
    /**
     * The date and time of when the flight arrives.
     */
    private GregorianCalendar ArrivalDate;
    /**
     * The airplane that is gonna be used for the flight.
     */
    private Airplane airplane;
    /**
     * The assigned flightcontroller to this flightplan
     */
    private FlightController assignedController;

    /***************Constructor**********/
    /**A flightplan is made with the following parameters:
     * @param destinationAirport: The Airport where the flight is going to. 
     * @param takeoffAirport: The Airport where the flight is taking off from.
     * @param flightnumber: Flightnumber of the flight.
     * @param DepartureDate: The date and time when the flight departs.
     * @param ArrivalDate: The date and time of when the flight arrives.
     * @param airplane: The airplane that is gonna be used for the flight.
     */
    public Flightplan(Airport destination, Airport takeOff, int Flightnumber, GregorianCalendar departure, GregorianCalendar arrival, Airplane airplane) {
        destinationAirport = destination;
        takeoffAirport = takeOff;
        this.Flightnumber = Flightnumber;
        DepartureDate = departure;
        ArrivalDate = arrival;
        this.airplane = airplane;
    }

    /**
     * Method to return a string value of the Flightplan with its Flightnumber, takeoffAirport and destinationAirport
     * 
     * @return A string value of the Flightplan with its Flightnumber, takeoffAirport and destinationAirport
     */
    @Override
    public String toString() {
        return "[" + Flightnumber + "] " + takeoffAirport.getIATA_FAA() + "- " + destinationAirport.getIATA_FAA();
    }

    /**************Getters**************/
    @Override
    public GregorianCalendar getArrivalDate() {
        return ArrivalDate;
    }

    @Override
    public GregorianCalendar getDepartureDate() {
        return DepartureDate;
    }

    @Override
    public int getFlightnumber() {
        return Flightnumber;
    }

    @Override
    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    @Override
    public Airplane getAirplane() {
        return airplane;
    }

    @Override
    public Airport getTakeoffAirport() {
        return takeoffAirport;
    }

    @Override
    public FlightController getAssignedController() {
        return assignedController;
    }

    /**************Setters**************/
    @Override
    public void setAssignedController(FlightController assignedController) {
        this.assignedController = assignedController;
    }
}