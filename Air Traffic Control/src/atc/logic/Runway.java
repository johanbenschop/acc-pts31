package atc.logic;

/**
 *
 * @author Mateusz
 */
public class Runway {

    private int Length;
    private int Direction;
    private boolean Availability;

    Runway(int length, int direction, boolean availability) {
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
        // TODO maak een timer...
        if (!r) {
            Availability = r;
        } else {
            Availability = r;
        }
        
    }
}