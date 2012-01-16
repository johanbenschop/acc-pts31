package atc.interfaces;

import java.rmi.*;

/**
 *
 * @author Henk
 */
//zet extends Remote op een of andere manier erbij...
public interface IAirplane extends Remote {

    public abstract void ChangeSpeed() throws RemoteException;
    
    public abstract void Land() throws RemoteException;
    
    public abstract void TakeOff(IRunway r, double direction, double altitude, double speed) throws RemoteException;
    
    public abstract double getAimedAltitude() throws RemoteException;

    public abstract double getAimedDirection() throws RemoteException;

    public abstract double getAimedSpeed() throws RemoteException;

    public abstract int getAirplaneNumber() throws RemoteException;

    public abstract double getAltitude() throws RemoteException;

    public abstract double getDirection() throws RemoteException;

    public abstract IGeoLoc getLocation() throws RemoteException;

    public abstract double getLongitudeTravelled() throws RemoteException;

    public abstract String getManufacturer() throws RemoteException;

    public abstract int getMaxFuel() throws RemoteException;

    public abstract int getMaxSpeed() throws RemoteException;

    public abstract int getMinSpeed() throws RemoteException;

    public abstract int getPlaneHeight() throws RemoteException;

    public abstract int getPlaneLength() throws RemoteException;

    public abstract int getPlaneWidth() throws RemoteException;

    public abstract double getSpeed() throws RemoteException;
    
    public abstract int getWeight() throws RemoteException;
    
    public abstract int getFuelUsage() throws RemoteException;
    
    public abstract double getDistanceTravelled() throws RemoteException;
    
    public abstract int getCurrentFuel() throws RemoteException;
    
    public abstract String getType() throws RemoteException;

    public abstract Statusses getStatus() throws RemoteException;

    public abstract void setAimedAltitude(double altitude) throws RemoteException;

    public abstract void setAimedDirection(double direction) throws RemoteException;

    public abstract void setAimedSpeed(double speed) throws RemoteException;

    public abstract void setCollcheck(boolean collcheck) throws RemoteException;

    public abstract void setStatus(Statusses Status) throws RemoteException;
    
    /**
     * Enumerator of possible statusses a airplane can have 
     */
    public enum Statusses {
        STANDINGONAIRPORT, TAKINGOFF, INFLIGHT, INLANDINGQUEUE, LANDING,
        CRASHING1, CRASHING2, CRASHED, INTAKEOFFQUEUE, HASLANDED;
    }
}