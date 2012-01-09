package atc.interfaces;

import java.rmi.*;
import java.util.*;

/**
 *
 * @author Henk
 */
public interface ICTA extends Remote{

    /**
     *
     * @param AirplaneID
     * @return
     * @deprecated
     */
    IAirplane GetAirplane(int AirplaneID) throws RemoteException;

    /**
     * TODO moet hier een unittest voor? nee toch?
     * Returns the airport with the given AirportID
     * @return
     */
    IAirport GetAirport(int AirportID) throws RemoteException;

    /**
     * Turns the airport list into a Iterator
     * @return Iterator airportList
     */
    ListIterator<IAirport> GetAirports() throws RemoteException;

    /**
     * Adds a airplane to the airplaneList
     * @return
     */
    void addAirplane(IAirplane a) throws RemoteException;

    /**
     * Adds a airport to the airportList
     * @return
     */
    void addAirport(IAirport a) throws RemoteException;

    /**
     * TODO...is dit niet dubbel met de methode die eronder staat??
     * Deletes the airplane with the corresponding AirplaneNumber from the airplaneList
     * @return
     */
    void deleteAirplane(int AirplaneNumber) throws RemoteException;

    /**
     * Checks the distance between 2 given points
     * @param lat1 is the first given latitude
     * @param lon1 is the first given longitude
     * @param lat2 is the second given latitude
     * @param lon2 is the second given longitude
     * @return a double with the calculated distance
     */
    double distFrom(double lat1, double lon1, double lat2, double lon2) throws RemoteException;

    /**
     *
     * @return
     * @deprecated
     */
    IAirplane getAirplane() throws RemoteException;

    /**
     *
     * @return
     * @deprecated
     */
    ArrayList<IAirplane> getAirplaneList() throws RemoteException;

    /**
     *
     * @return
     * @deprecated
     */
    IAirport getAirport() throws RemoteException;

    /**
     *
     * @return
     * @deprecated
     */
    ArrayList<IAirport> getAirportList() throws RemoteException;

    IGeoSec getSector() throws RemoteException;
    
    IGeoSec getGreaterSector() throws RemoteException;

    void removeAirplane(IAirplane airplane) throws RemoteException;

    void resetCollision(IAirplane airplane) throws RemoteException;    
}