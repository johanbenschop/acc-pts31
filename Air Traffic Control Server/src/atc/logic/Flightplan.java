package atc.logic;

import atc.interfaces.*;
import java.io.Serializable;
import java.util.*;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * All information about a planned flight is found in Flightplan.
 * 
 */
public class Flightplan implements IFlightplan, Serializable {

    /**************Datafields***********/
    /**
     * The Airport where the flight is going to. 
     */
    private IAirport destinationAirport;
    /**
     * The Airport where the flight is taking off from
     */
    private IAirport takeoffAirport;
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
    private IAirplane airplane;
    /**
     * The assigned flightcontroller to this flightplan
     */
    private IFC assignedController;

    /***************Constructor**********/
    /**A flightplan is made with the following parameters:
     * @param destinationAirport: The Airport where the flight is going to. 
     * @param takeoffAirport: The Airport where the flight is taking off from.
     * @param flightnumber: Flightnumber of the flight.
     * @param DepartureDate: The date and time when the flight departs.
     * @param ArrivalDate: The date and time of when the flight arrives.
     * @param airplane: The airplane that is gonna be used for the flight.
     */
    public Flightplan(IAirport destination, IAirport takeOff, int Flightnumber, GregorianCalendar departure, GregorianCalendar arrival, IAirplane airplane) {
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
        try {
            return "[" + Flightnumber + "] " + takeoffAirport.getIATA_FAA() + "- " + destinationAirport.getIATA_FAA();
        } catch (RemoteException ex) {
            Logger.getLogger(Flightplan.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
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
    public IAirplane getAirplane() {
        return airplane;
    }

    @Override
    public IFC getAssignedController() {
        return assignedController;
    }

    @Override
    public IAirport getDestinationAirport() {
        return destinationAirport;
    }

    @Override
    public IAirport getTakeoffAirport() {
        return takeoffAirport;
    }
    
    /**************Setters**************/
    @Override
    public void setAssignedController(IFC assignedController) {
        this.assignedController = assignedController;
    }
}