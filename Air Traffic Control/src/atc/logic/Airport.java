package atc.logic;

import java.util.*;
/**
 * @author Robbert
 */

public class Airport {

   //ToDo: Change Strings to ints when the numbers can be correctly loaded into an int. 
    
    private int AirportID;
    private String AirportName;
    private String City;
    private String Country;
    private String IATA_FAA;
    private String ICAO;
    private GeoLocation location;
    private int Altitude;
    private double Timezone;

    
    //DST is a letter specifying the DST: E (Europe), A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown)
    private String DST;
    private ArrayList<Runway> runways;

      /***************Constructor**********/
    /**
     * An Airport is made with its own AirportID, AirportName, City, Country, ITATA_FAA, ICAO, Altitude, Timezone, DST and location.
     * @param AirportID: This is the ID of the airport.
     * @param AirportName: This is the name of the airport.
     * @param City: This is the city where the aiport is in.
     * @param County: This is the County where the airport is in.
     * @param ITATA_FAA: This is the ITATA_FAA of the airplane.
     * @param ICAO: The ICAO of the aiport.
     * @param Geolocation: The Geolocation of the airport.
     * @param Altitude: The altitude of the airport.
     * @param Timezone: The timezone of the airport.
     * @param DST: The DST of the airport.
     */
    
    public Airport(int AirportID, String AirportName, String City, String Country, String ITATA_FAA, String ICAO, GeoLocation location, int Altitude, double Timezone, String DST) {
        this.AirportID = AirportID;
        this.AirportName = AirportName;
        this.City = City;
        this.Country = Country;
        this.IATA_FAA = ITATA_FAA;
        this.ICAO = ICAO;
        this.Altitude = Altitude;
        this.Timezone = Timezone;
        this.DST = DST;
        this.location = location;
       runways = new ArrayList<>();
       
        runways.add( new Runway(location.getLongitude(), location.getLatitude(), Altitude, 100, 90, true));
        runways.add( new Runway(location.getLongitude(), location.getLatitude(), Altitude, 100, -90, true));
    }
    
    public String ToString()
    {
        String gegevens;
        gegevens = "AirportID: "+this.getAirportID()+" , Airport name: " +this.getAirportName()+" , City: " +this.getCity()+" , Country: "+this.getCountry();
        return gegevens;
    }

    /**
     * 
     * @return 
     */
    public Runway getRunway() {
        for (Runway runway : runways) {
            if (runway.getAvailability() == true)
            {
            return runway;
            }
        }
        return null;
    }

    public int getAirportID() {
        return AirportID;
    }
    
    public String getAirportName() {
        return AirportName;
    }

    public String getCity() {
        return City;
    }

    public String getCountry() {
        return Country;
    }

    public String getIATA_FAA() {
        return IATA_FAA;
    }

    public String getICAO() {
        return ICAO;
    }

    public double getTimezone() {
        return Timezone;
    }

    public String getDST() {
        return DST;
    }
    
    public GeoLocation getLocation() {
        return location;
    }
    
}