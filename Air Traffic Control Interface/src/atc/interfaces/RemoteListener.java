package atc.interfaces;

import java.util.*;
import java.rmi.*;

/**
 *
 * @author Henk, Johan & Mateusz
 */
public interface RemoteListener extends EventListener, Remote{
    public void newAirplaneLocation(ArrayList<IAirplane> ap) throws RemoteException;
    public void newFlightplan(IFlightplan fp) throws RemoteException;
}