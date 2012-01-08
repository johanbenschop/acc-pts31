

import java.util.*;
/**
 * @author Robbert
 */

public class Airport implements IAirport {

   //ToDo: Change Strings to ints when the numbers can be correctly loaded into an int. 
    
    private int AirportID;//The ID of an airport.
    private String AirportName;//The name of an airport.
    private String City;//The city where an airport is builded.
    private String Country;//The country where an aiport is builded.
    private String IATA_FAA;//The IATA_FAA of an airpot.
    private String ICAO;//The ICAO of an aiport.
    private GeoLocation location;//The geolocation of an aiport.
    private int Altitude;//The Altitdue of an airport.
    private double Timezone;//The timezone.
    private String DST;//DST is a letter specifying the DST: E (Europe), 
    //A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown).
    private ArrayList<Runway> runways;// A list with the runways.

      /***************Constructor**********/
    /**
     * This is the constructor to make an airport with its AirportID, AirportName, 
     * City, Country, ITATA_FAA, ICAO, Altitude, Timezone, DST and location.
     * @param AirportID: This is the ID of the airport.
     * @param AirportName: This is the name of the airport.
     * @param City: This is the city where the aiport is in.
     * @param Country: This is the County where the airport is in.
     * @param ITATA_FAA: This is the ITATA_FAA of the airport.
     * @param ICAO: This is the ICAO of the airport.
     * @param location: This is the location where the aiport is placed.
     * @param Altitude: The altitude of the airport.
     * @param Timezone: The timezone of the aiport.
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
    
    
    /**
     * This gives information of an airport.
     * @return string with airport information.
     */
    @Override
    public String ToString()
    {
        String gegevens;
        gegevens = "AirportID: "+this.getAirportID()+" , Airport name: " +this.getAirportName()+" , City: " +this.getCity()+" , Country: "+this.getCountry();
        return gegevens;
    }

    /**
     * If a runway is free, it returns that runway
     * @return runway.
     */
    @Override
    public Runway getRunway() {
        for (Runway runway : runways) {
            if (runway.getAvailability() == true)
            {
            return runway;
            }
        }
        return null;
    }

    /**
     * Gets the AirportID from an airport.
     * @return AirportID.
     */
    @Override
    public int getAirportID() {
        return AirportID;
    }
    
    /**
     * Gets the AirportName from an airport.
     * @return AirportName.
     */
    @Override
    public String getAirportName() {
        return AirportName;
    }

    /**
     * Gets the City where the airport is located.
     * @return City.
     */
    @Override
    public String getCity() {
        return City;
    }

    /**
     * Gets the Country where the airport is located.
     * @return Country.
     */
    @Override
    public String getCountry() {
        return Country;
    }

    /**
     * Gets the IATA_FAA of the airport.
     * @return IATA_FAA.
     */
    @Override
    public String getIATA_FAA() {
        return IATA_FAA;
    }

    /**
     * Gets the ICAO of the airport.
     * @return IcAO.
     */
    @Override
    public String getICAO() {
        return ICAO;
    }

    /**
     * Gets the Timezone of the airport.
     * @return Timezone.
     */
    @Override
    public double getTimezone() {
        return Timezone;
    }

    /**
     * Gets the DST of the airport.
     * @return DST.
     */
    @Override
    public String getDST() {
        return DST;
    }
    
    /**
     * Gets the location of the airport.
     * @return Location.
     */
    @Override
    public GeoLocation getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return this.AirportName + ", " + this.City;
    }
    
    
}