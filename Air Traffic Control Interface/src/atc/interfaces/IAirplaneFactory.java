package atc.interfaces;

/**
 *
 * @author Mateusz
 */
public interface IAirplaneFactory {

    /**
     * Method to return a string value of the Manufacturer and type.
     *
     * @return A string value with the Manufacturer and type is returned.
     */
    String ToString();

    int getFuelUsage();

    /**
     * Getters
     */
    int getID();

    String getManufacturer();

    int getMaxFuel();

    int getMaxSpeed();

    int getMinSpeed();

    int getPlaneHeight();

    int getPlaneLength();

    int getPlaneWidth();

    String getType();

    int getWeight();
    
}
