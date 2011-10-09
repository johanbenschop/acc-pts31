package atc.logic;

import java.util.ArrayList;

public class AirplaneFactory {

    //Properties
    
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
    private ArrayList<AirplaneFactory> AvailableAirplanes;

    
    //Constructor of AirplaneFactory.
    
    public AirplaneFactory(int maxSpeed, int minSpeed, int weight, String type, String manucaturer, int planeHeight, int planeWidth, int planeLength, int maxFuel, int fuelUsage) {
        this.MaxSpeed = maxSpeed;
        this.MinSpeed = minSpeed;
        this.Weight = weight;
        this.Type = type;
        this.Manufacturer = manucaturer;
        this.PlaneHeight = planeHeight;
        this.PlaneWidth = planeWidth;
        this.PlaneLength = planeLength;
        this.MaxFuel = maxFuel;
        this.FuelUsage = fuelUsage;
    }
 
    
    // Getters and setters of AirplaneFactory

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

    public ArrayList<AirplaneFactory> getAirplanes() {
        return AvailableAirplanes;
    }
}