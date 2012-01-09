package atc.interfaces;

import java.io.*;
import java.rmi.*;
import java.util.*;

/**
 *
 * @author Mateusz
 */
public interface IAirspace extends Remote{

    void BorderControl() throws RemoteException;

    /**
     * Getters
     */
    ArrayList<IACC> GetACCs() throws RemoteException;

    /**
     * A method to get the airport with the given AirportID
     *
     * @return The airport with the given AirportID
     */
    IAirport GetAirport(int AirportID) throws NullPointerException, RemoteException;

    ListIterator<IAirport> GetAirports() throws RemoteException;

    /**
     * A method to get the ACC with the given ID
     *
     * @return The acc with the given ID
     */
    IACC getACC(int ID) throws RemoteException;

    /**
     * Gets all the adjacent ACC's from the current ACC and returns this ArrayList.
     * @param ACCID
     * @return Arraylist with adjacent ACC's
     */
    ArrayList getAdjacentACCs(int CurrentACCID) throws RemoteException;

    /**
     * A method to get the airport list within the given GeoSector
     *
     * @return An list with airports in the given GeoSector
     */
    ArrayList<IAirport> getAirportCTA(IGeoSec sector) throws RemoteException;

    IACC getCurrentACC() throws RemoteException;

    boolean getOnlyOneACC() throws RemoteException;

    /**
     * A method to load the list with all the airports in the airports.dat file
     *
     */
    void loadAirportList() throws FileNotFoundException, IOException, RemoteException;

    /**
     * Setters
     */
    void setCurrentACC(IACC currentACC) throws RemoteException;

    void setCurrentACC(int ID) throws RemoteException;

    void setOnlyOneACC(boolean onlyOneAcc) throws RemoteException;    
}