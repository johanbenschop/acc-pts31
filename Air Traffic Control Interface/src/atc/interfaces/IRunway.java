package atc.interfaces;

import java.rmi.*;

/**
 *
 * @author Henk
 */
public interface IRunway extends Remote {

    /**
     * @param r is what the availability should become after calling this method.
     * @return Availability
     */
    void ChangeAvailability(boolean r) throws RemoteException;

    boolean getAvailability() throws RemoteException;

    int getDirection() throws RemoteException;

    /**
     *
     * @return
     * @deprecated
     */
    int getLength() throws RemoteException;

    /**
     * Getters
     */
    IGeoLoc getLocation() throws RemoteException;
    
}
