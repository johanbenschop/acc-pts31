package atc.interfaces;

/**
 *
 * @author Henk
 */
public abstract class IAirplane extends Thread {

    public abstract void Land();
    
    public abstract void TakeOff(IRunway r, double direction, double altitude, double speed);
    
    public abstract double getAimedAltitude();

    public abstract double getAimedDirection();

    public abstract double getAimedSpeed();

    public abstract int getAirplaneNumber();

    public abstract double getAltitude();

    public abstract double getDirection();

    public abstract IGeoLoc getLocation();

    public abstract double getLongitudeTravelled();

    public abstract String getManufacturer();

    public abstract int getMaxFuel();

    public abstract int getMaxSpeed();

    public abstract int getMinSpeed();

    public abstract int getPlaneHeight();

    public abstract int getPlaneLength();

    public abstract int getPlaneWidth();

    public abstract double getSpeed();
    
    public abstract int getWeight();
    
    public abstract int getFuelUsage();
    
    public abstract double getDistanceTravelled();
    
    public abstract int getCurrentFuel();
    
    public abstract String getType();

    public abstract Statusses getStatus();

    public abstract void setAimedAltitude(double altitude);

    public abstract void setAimedDirection(double direction);

    public abstract void setAimedSpeed(double speed);

    public abstract void setCollcheck(boolean collcheck);

    public abstract void setStatus(Statusses Status);
    
    /**
     * Enumerator of possible statusses a airplane can have
     */
    public enum Statusses {
        STANDINGONAIRPORT, TAKINGOFF, INFLIGHT, INLANDINGQUEUE, LANDING,
        CRASHING1, CRASHING2, CRASHED, INTAKEOFFQUEUE, HASLANDED;
    }
}
