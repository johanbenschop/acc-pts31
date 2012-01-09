package atc.interfaces;

/**
 *
 * @author Henk
 */
public interface IAirport {

    /**
     * This gives information of an airport.
     * @return string with airport information.
     */
    String ToString();

    /**
     * Gets the AirportID from an airport.
     * @return AirportID.
     */
    int getAirportID();

    /**
     * Gets the AirportName from an airport.
     * @return AirportName.
     */
    String getAirportName();

    /**
     * Gets the City where the airport is located.
     * @return City.
     */
    String getCity();

    /**
     * Gets the Country where the airport is located.
     * @return Country.
     */
    String getCountry();

    /**
     * Gets the DST of the airport.
     * @return DST.
     */
    String getDST();

    /**
     * Gets the IATA_FAA of the airport.
     * @return IATA_FAA.
     */
    String getIATA_FAA();

    /**
     * Gets the ICAO of the airport.
     * @return IcAO.
     */
    String getICAO();

    /**
     * Gets the location of the airport.
     * @return Location.
     */
    IGeoLoc getLocation();

    /**
     * If a runway is free, it returns that runway
     * @return runway.
     */
    IRunway getRunway();

    /**
     * Gets the Timezone of the airport.
     * @return Timezone.
     */
    double getTimezone();

    String toString();
    
}
