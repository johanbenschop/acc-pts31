/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import atc.interfaces.IFlightplan;
import atc.interfaces.RemoteListener;
import atc.interfaces.RemotePublisher;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author Henk
 */
public class Publisher extends UnicastRemoteObject implements RemotePublisher {
    
    private ArrayList<RemoteListener> listeners;
    private final Object lockListener = new Object();
    
    public Publisher() throws RemoteException {
        listeners = new ArrayList<>();
    }
    
    public void publishFlightplans(ArrayList<IFlightplan> fp) throws RemoteException {
        synchronized (lockListener) {
            for (RemoteListener listener : listeners) {
                listener.newFlightplanLocation(fp);
            }
        }
    }
    
    public void publishFlightplan(Flightplan fp) throws RemoteException {
        synchronized (lockListener) {
            for (RemoteListener listener : listeners) {
                listener.newFlightplan(fp);
            }
        }
    }
    
    @Override
    public void addListener(RemoteListener listener) throws RemoteException {
        synchronized (lockListener) {
            listeners.add(listener);
            System.out.println("Listener added");
        }
    }

    @Override
    public void removeListener(RemoteListener listener) throws RemoteException {
        synchronized (lockListener) {
            listeners.remove(listener);
            System.out.println("Listener removed");
        }
    }
}
