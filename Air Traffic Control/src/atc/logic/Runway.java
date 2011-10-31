package atc.logic;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Mateusz
 */
public class Runway extends GeoLocation{

    private int Length;
    private int Direction;
    private boolean Availability;
    private Timer timer;

    public Runway(int Longitude, int Latitude, int Altitude, int length, int direction, boolean availability) {
        super(Longitude, Latitude, Altitude);
        this.Length = length;
        this.Direction = direction;
        this.Availability = availability;
    }
    
    public int getLength() {
        return Length;
    }

    public int getDirection() {
        return Direction;
    }

    public boolean getAvailability() {
        return Availability;
    }
    
    /**
     * @param r is what the availability should become after calling this method.
     * @return Availability
     */
    public void ChangeAvailability(boolean r) {
        timer = new Timer();
        if (r) {
            timer.schedule(new tim(r), 180000); //180,000 milliseconds = 3 minutes
        } else {
            Availability = r;
        }
    }

    private class tim extends TimerTask {

        boolean r;

        private tim(boolean r) {
            this.r = r;
        }

        @Override
        public void run() {
            Availability = r;
            timer.cancel();
        }
    }
}