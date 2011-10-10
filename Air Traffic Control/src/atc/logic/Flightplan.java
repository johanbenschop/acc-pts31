package atc.logic;

import java.util.*;

public class Flightplan {

    private Airport destinationAirport;
    private Airport takeoffAirport;
    private int Flightnumber;
    private GregorianCalendar DepartureDate;
    private GregorianCalendar ArrivalDate;

    public Flightplan(Airport destination, Airport takeOff, int Flightnumber, GregorianCalendar departure, GregorianCalendar arrival) {
        destinationAirport = destination;
        takeoffAirport = takeOff;
        this.Flightnumber = Flightnumber;
        DepartureDate = departure;
        ArrivalDate = arrival;
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