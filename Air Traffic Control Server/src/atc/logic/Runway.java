package atc.logic;

import atc.interfaces.IGeoLoc;
import atc.interfaces.IRunway;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

/**
 * A runway of an airplane
 * 
 * @author Mateusz
 */
public class Runway extends UnicastRemoteObject implements IRunway, Serializable {

    /**************Datafields***********/
    /**
     * The length of the runway
     */
    private int Length;
    /**
     * The direction the runway is set.
     */
    private int Direction;
    /**
     * If the runway is available to land or take off an airplane
     */
    private boolean Availability;
    /**
     * A timer to calculate when the 3 minutes are past before the runway is available again
     */
    private Timer timer;
    /**
     * The geolocation of the runway. 
     */
    private IGeoLoc location;
    private static Preferences prefs = Preferences.userRoot().node("/atc/gui");

    /***************Constructor**********/
    /**A runway is made with the following parameters:
     * @param Longitude: The longitude of the runway.
     * @param Latitude: The latitude of the runway.
     * @param Altitude: The altitude of the runway.
     * @param length: The length of the runway
     * @param direction: The direction that the runway lies
     * @param availability: Checks if the runway is available
     */
    public Runway(double Longitude, double Latitude, double Altitude, int length, int direction, boolean availability) throws RemoteException {
        location = new GeoLocation(Longitude, Latitude, Altitude);
        this.Length = length;
        this.Direction = direction;
        this.Availability = availability;
    }

    /**
     * @param r is what the availability should become after calling this method.
     * @return Availability
     */
    @Override
    public void ChangeAvailability(boolean r) {
        timer = new Timer();
        if (r) {
            timer.schedule(new timerTask(r), (int) (prefs.getDouble("SIM_SPEED", 1) * 180000)); //180,000 milliseconds = 3 minutes
        } else {
            Availability = r;
        }
    }    

    /**
     * A class for the timer.
     */
    private class timerTask extends TimerTask {

        boolean available;

        private timerTask(boolean available) {
            this.available = available;
        }

        @Override
        public void run() {
            Availability = available;
            timer.cancel();
        }
    }

    /**************Getters**************/
    @Override
    public IGeoLoc getLocation() {
        return location;
    }

    /**
     * 
     * @return 
     * @deprecated 
     */
    @Override
    public int getLength() {
        return Length;
    }

    @Override
    public int getDirection() {
        return Direction;
    }

    @Override
    public boolean getAvailability() {
        return Availability;
    }
}