package atc.interfaces;

import java.rmi.*;

/**
 *
 * @author Henk
 */
public interface IAirport extends Remote {

    /**
     * This gives information of an airport.
     * @return string with airport information.
     */
    String ToString() throws RemoteException;

    /**
     * Gets the AirportID from an airport.
     * @return AirportID.
     */
    int getAirportID() throws RemoteException;

    /**
     * Gets the AirportName from an airport.
     * @return AirportName.
     */
    String getAirportName() throws RemoteException;

    /**
     * Gets the City where the airport is located.
     * @return City.
     */
    String getCity() throws RemoteException;

    /**
     * Gets the Country where the airport is located.
     * @return Country.
     */
    String getCountry() throws RemoteException;

    /**
     * Gets the DST of the airport.
     * @return DST.
     */
    String getDST() throws RemoteException;

    /**
     * Gets the IATA_FAA of the airport.
     * @return IATA_FAA.
     */
    String getIATA_FAA() throws RemoteException;

    /**
     * Gets the ICAO of the airport.
     * @return IcAO.
     */
    String getICAO() throws RemoteException;

    /**
     * Gets the location of the airport.
     * @return Location.
     */
    IGeoLoc getLocation() throws RemoteException;

    /**
     * If a runway is free, it returns that runway
     * @return runway.
     */
    IRunway getRunway() throws RemoteException;

    /**
     * Gets the Timezone of the airport.
     * @return Timezone.
     */
    double getTimezone() throws RemoteException;

    @Override
    String toString();
}