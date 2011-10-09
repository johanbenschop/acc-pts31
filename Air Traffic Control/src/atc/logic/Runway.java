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
     * 
     * @return Availability
     */
    public boolean ChangeAvailability() {
        if (Availability) {
            Availability = false;
        } else {
            Availability = true;
        }
        return Availability;
    }
}