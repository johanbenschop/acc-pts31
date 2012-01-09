/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.interfaces;

import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;

/**
 *
 * @author Henk
 */
public interface IGeoLoc {

    /**
     * Due to minimal importance of an unit test for this method there is none.
     *
     * Method to return a string value with the longitude, latitude and altitude.
     *
     * @return A string value with the longitude, latitude and altitude is returned.
     * @deprecated
     */
    String ToString();

    /**
     * Getters
     */
    double getAltitude();

    double getLatitude();

    double getLongitude();

    IGeoLoc getNewGeoLocation();

    /**
     * Setters
     */
    void setAltitude(double Altitude);

    void setLatitude(double Latitude);

    void setLongitude(double Longitude);

    /**
     * Method to return the Latlon made from the latitude and longitude
     *
     * @return A LatLon value in degrees.
     * @deprecated
     */
    LatLon toLatLon();

    /**
     * Converts the geolocation to the location used in WWJ.
     * @return position.
     */
    Position toPosition();
    
}
