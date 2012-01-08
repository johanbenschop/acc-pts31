/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 *
 * @author Henk
 */
public interface ICTA {

    /**
     *
     * @param AirplaneID
     * @return
     * @deprecated
     */
    Airplane GetAirplane(int AirplaneID);

    /**
     * TODO moet hier een unittest voor? nee toch?
     * Returns the airport with the given AirportID
     * @return
     */
    Airport GetAirport(int AirportID);

    /**
     * Turns the airport list into a Iterator
     * @return Iterator airportList
     */
    ListIterator<Airport> GetAirports();

    /**
     * Adds a airplane to the airplaneList
     * @return
     */
    void addAirplane(Airplane a);

    /**
     * Adds a airport to the airportList
     * @return
     */
    void addAirport(Airport a);

    /**
     * TODO...is dit niet dubbel met de methode die eronder staat??
     * Deletes the airplane with the corresponding AirplaneNumber from the airplaneList
     * @return
     */
    void deleteAirplane(int AirplaneNumber);

    /**
     * Checks the distance between 2 given points
     * @param lat1 is the first given latitude
     * @param lon1 is the first given longitude
     * @param lat2 is the second given latitude
     * @param lon2 is the second given longitude
     * @return a double with the calculated distance
     */
    double distFrom(double lat1, double lon1, double lat2, double lon2);

    /**
     *
     * @return
     * @deprecated
     */
    Airplane getAirplane();

    /**
     *
     * @return
     * @deprecated
     */
    ArrayList<Airplane> getAirplaneList();

    /**
     *
     * @return
     * @deprecated
     */
    Airport getAirport();

    /**
     *
     * @return
     * @deprecated
     */
    ArrayList<Airport> getAirportList();

    GeoSector getSector();

    void removeAirplane(Airplane airplane);

    void resetCollision(Airplane airplane);
    
}
