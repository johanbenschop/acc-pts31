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
    private Runway runway1;
    private Runway runway2;
    
    //DST is a letter specifying the DST: E (Europe), A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown)
    private String DST;

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
        runway1 = new Runway(location.getLongitude(), location.getLatitude(), Altitude, 100, 90, true);
        runway2 = new Runway(location.getLongitude(), location.getLatitude(), Altitude, 100, -90, true);
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
    public void getRunway() {
        throw new UnsupportedOperationException();
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