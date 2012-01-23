package atc.interfaces;

import java.util.*;
import java.rmi.*;

/**
 *
 * @author Henk, Johan & Mateusz
 */
public interface RemoteListener extends EventListener, Remote{
    public void newFlightplanLocation(ArrayList<IFlightplan> fp) throws RemoteException;
    public void newFlightplan(IFlightplan fp) throws RemoteException;
}