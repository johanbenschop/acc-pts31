package atc.interfaces;

import java.rmi.*;

/**
 *
 * @author Mateusz
 */
public interface IAirplaneFactory extends Remote {

    /**
     * Method to return a string value of the Manufacturer and type.
     *
     * @return A string value with the Manufacturer and type is returned.
     */
    String ToString() throws RemoteException;

    int getFuelUsage() throws RemoteException;

    /**
     * Getters
     */
    int getID() throws RemoteException;

    String getManufacturer() throws RemoteException;

    int getMaxFuel() throws RemoteException;

    int getMaxSpeed() throws RemoteException;

    int getMinSpeed() throws RemoteException;

    int getPlaneHeight() throws RemoteException;

    int getPlaneLength() throws RemoteException;

    int getPlaneWidth() throws RemoteException;

    String getType() throws RemoteException;

    int getWeight() throws RemoteException;    
}