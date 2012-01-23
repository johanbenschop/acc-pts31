package atc.interfaces;

import java.rmi.*;
import java.util.ArrayList;
/**
 *
 * @author Henk, Johan & Mateusz
 */
public interface RemotePublisher extends Remote {
    public void publishFlightplans(ArrayList<IFlightplan> fp) throws RemoteException;
    public void publishFlightplan(IFlightplan fp) throws RemoteException;
    public void addListener(RemoteListener listener) throws RemoteException;
    public void removeListener(RemoteListener listener) throws RemoteException;
}