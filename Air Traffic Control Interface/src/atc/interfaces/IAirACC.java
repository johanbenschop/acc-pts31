/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 *
 * @author Henk
 */
public interface IAirACC {

    void BorderControl();

    /**
     * Getters
     */
    ListIterator<ACC> GetACCs();

    /**
     * A method to get the airport with the given AirportID
     *
     * @return The airport with the given AirportID
     */
    Airport GetAirport(int AirportID) throws NullPointerException;

    ListIterator<Airport> GetAirports();

    /**
     * A method to get the ACC with the given ID
     *
     * @return The acc with the given ID
     */
    ACC getACC(int ID);

    /**
     * Gets all the adjacent ACC's from the current ACC and returns this ArrayList.
     * @param ACCID
     * @return Arraylist with adjacent ACC's
     */
    ArrayList getAdjacentACCs(int CurrentACCID);

    /**
     * A method to get the airport list within the given GeoSector
     *
     * @return An list with airports in the given GeoSector
     */
    ArrayList<Airport> getAirportCTA(GeoSector sector);

    ACC getCurrentACC();

    boolean getOnlyOneACC();

    /**
     * A method to load the list with all the airports in the airports.dat file
     *
     */
    void loadAirportList() throws FileNotFoundException, IOException;

    /**
     * Setters
     */
    void setCurrentACC(ACC currentACC);

    void setCurrentACC(int ID);

    void setOnlyOneACC(boolean onlyOneAcc);
    
}
