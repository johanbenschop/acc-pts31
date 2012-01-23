package atc.interfaces;

import java.rmi.*;

/**
 *
 * @author Henk
 */
public interface IGeoLoc extends Remote {

    /**
     * Due to minimal importance of an unit test for this method there is none.
     *
     * Method to return a string value with the longitude, latitude and altitude.
     *
     * @return A string value with the longitude, latitude and altitude is returned.
     * @deprecated
     */
//    String ToString();

    /**
     * Getters
     */
    double getAltitude() throws RemoteException;

    double getLatitude() throws RemoteException;

    double getLongitude() throws RemoteException;
    
    double CalcDirection(IGeoLoc pos, IGeoLoc pos2) throws RemoteException;

    IGeoLoc getNewGeoLocation() throws RemoteException;

    /**
     * Setters
     */
    void setAltitude(double Altitude) throws RemoteException;

    void setLatitude(double Latitude) throws RemoteException;

    void setLongitude(double Longitude) throws RemoteException;
    
    /**
     * Method to return the Latlon made from the latitude and longitude
     *
     * @return A LatLon value in degrees.
     * @deprecated
     */
//    LatLon toLatLon() throws RemoteException;

    /**
     * Converts the geolocation to the location used in WWJ.
     * @return position.
     */
//    Position toPosition() throws RemoteException;    
}
