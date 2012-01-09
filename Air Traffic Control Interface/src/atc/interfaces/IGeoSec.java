package atc.interfaces;

import gov.nasa.worldwind.geom.Sector;
import java.rmi.*;

/**
 *
 * @author Henk
 */
public interface IGeoSec extends Remote {

    /**
     * Determines whether a GeoLocation is within the GeoSector.
     * @param location
     * @return true if GeoPosition is within the GeoSecor, false otherwise.
     */
    boolean containsGeoLocation(IGeoLoc location) throws RemoteException;

    /**
     * Tests the equality of the sectors' angles. Sectors are equal if all of their corresponding angles are equal.
     *
     * @param o the sector to compareTo with <code>this</code>.
     *
     * @return <code>true</code> if the four corresponding angles of each sector are equal, <code>false</code>
     * otherwise.
     */
    boolean equals(Object o);

    /**
     * Returns the latitude and longitude of the sector's angular center: (minimum latitude + maximum latitude) / 2,
     * (minimum longitude + maximum longitude) / 2.
     *
     * @return The latitude and longitude of the sector's angular center
     * @deprecated
     */
    IGeoLoc getCenterLocation() throws RemoteException;

    /**
     * @deprecated
     * @return
     */
    double getDeltaLatitude() throws RemoteException;

    /**
     * @deprecated
     * @return
     */
    double getDeltaLongitude() throws RemoteException;

    double getMaxLatitude() throws RemoteException;

    double getMaxLongitude() throws RemoteException;

    double getMinLatitude() throws RemoteException;

    double getMinLongitude() throws RemoteException;

    /**
     *
     * @return
     * @deprecated
     */
    boolean isWithinLatLonLimits() throws RemoteException;

    /**
     * Convets this GeoSector to an Sector for World Wind.
     * @return World Wind Sector
     */
    Sector toSector() throws RemoteException;

    /**
     * Due to minimal importance of an unit test for this method there is none.
     *
     * Returns a string indicating the sector's angles.
     *
     * @return A string indicating the sector's angles.
     */
    String toString();    
}