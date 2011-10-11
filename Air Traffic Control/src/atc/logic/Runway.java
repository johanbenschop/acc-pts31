package atc.logic;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Mateusz
 */
public class Runway {
    private int Length;
    private int Direction;
    private boolean Availability;
    private Timer timer;

    public Runway(int length, int direction, boolean availability) {
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
        if (!r) {
            Availability = r;
            timer.schedule(new tim(r), 180000);
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