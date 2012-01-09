package atc.interfaces;

import java.rmi.*;
/**
 *
 * @author Henk, Johan & Mateusz
 */
public interface RemotePublisher extends Remote {
    public void addListener(RemoteListener listener) throws RemoteException;
    public void removeListener(RemoteListener listener) throws RemoteException;
}