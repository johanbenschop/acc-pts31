package atc.logic;

/**
 * @author Robbert
 */
public class AirplaneFactory {

    /**************Datafields***********/
    private int Id, nr = 1;
    private int MaxSpeed;
    private int MinSpeed;
    private int Weight;
    private String Type;
    private String Manufacturer;
    private int PlaneHeight;
    private int PlaneWidth;
    private int PlaneLength;
    private int MaxFuel;
    private int FuelUsage;

    /***************Constructor**********/
    /**
     * An AirplaneFactory is made with its own maximum speed, minimum speed, weight,
     * type, manufacturer, planeheight, planewidth, planelength, maximum fuel and 
     * fuel usage.
     * @param MaxSpeed: This is the maximum speed of the airplane.
     * @param MinSpeed: This is the minihe airplane
     * @param Manufacturer: This is the builder of the airplane.
     * @param PlaneHeight: The height of the airplane.
     * @param PlaneWidth: The width of the airplane.
     * @param PlaneLength: The length of the airplane.
     * @param MaxFuel: The maximum fuel of the airplane.
     * @param FuelUsage: The usage mum speed of the airplane.
     * @param Weight: This is they weight of the airplane.
     * @param Type: This is the type of tof fuel of the airplane
     */
    public AirplaneFactory(int maxSpeed, int minSpeed, int weight, String type, String manufacturer, int planeHeight, int planeWidth, int planeLength, int maxFuel, int fuelUsage) {

        Id = nr;
        this.MaxSpeed = maxSpeed;
        this.MinSpeed = minSpeed;
        this.Weight = weight;
        this.Type = type;
        this.Manufacturer = manufacturer;
        this.PlaneHeight = planeHeight;
        this.PlaneWidth = planeWidth;
        this.PlaneLength = planeLength;
        this.MaxFuel = maxFuel;
        this.FuelUsage = fuelUsage;
        nr++;
    }

    /**
     * Method to return a string value of the Manufacturer and type.
     * 
     * @return A string value with the Manufacturer and type is returned.
     */
    public String ToString() {
        String gegevens;
        gegevens = "Manufacturer: " + this.getManufacturer() + ", type: " + this.getType();
        return gegevens;
    }
    
    /**************Getters**************/
    public int getID(){
        return Id;
    }

    public int getMaxSpeed() {
        return MaxSpeed;
    }

    public int getMinSpeed() {
        return MinSpeed;
    }

    public int getWeight() {
        return Weight;
    }

    public String getType() {
        return Type;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public int getPlaneHeight() {
        return PlaneHeight;
    }

    public int getPlaneWidth() {
        return PlaneWidth;
    }

    public int getPlaneLength() {
        return PlaneLength;
    }

    public int getMaxFuel() {
        return MaxFuel;
    }

    public int getFuelUsage() {
        return FuelUsage;
    }
}