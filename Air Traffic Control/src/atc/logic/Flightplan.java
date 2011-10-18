package atc.logic;

import java.util.*;

public class Flightplan {

    private Airport destinationAirport;
    private Airport takeoffAirport;
    private int Flightnumber;
    private GregorianCalendar DepartureDate;
    private GregorianCalendar ArrivalDate;
    private Airplane airplane;
    
    public Flightplan(Airport destination, Airport takeOff, int Flightnumber, GregorianCalendar departure, GregorianCalendar arrival, Airplane airplane) {
        destinationAirport = destination;
        takeoffAirport = takeOff;
        this.Flightnumber = Flightnumber;
        DepartureDate = departure;
        ArrivalDate = arrival;
        this.airplane = airplane;
    }

    public GregorianCalendar getArrivalDate() {
        return ArrivalDate;
    }

    public GregorianCalendar getDepartureDate() {
        return DepartureDate;
    }

    public int getFlightnumber() {
        return Flightnumber;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public Airport getTakeoffAirport() {
        return takeoffAirport;
    }
}