package atc.logic;

import java.util.ArrayList;

public class Airplane extends AirplaneFactory {

        /**************Datafields***********/
    private int Direction;
    private double Speed;
    private int MaxFuel;
    private int CurrentFuel;
    private int FuelUsage;
    private double Altitude;
    private ArrayList<Runway> Runways;
    private double AimedSpeed;
    private double AimedDirection;
    private double AimedAltitude;

       
    
        /***************Constructor**********/
    /**
     * An Airplane is made with its own maximum speed, minimum speed, weight,
     * type, manufacturer, planeheight, planewidth, planelength, maximum fuel, 
     * fuel usage, direction, speed, currentfuel and altitude.
     * 
     * @param MaxSpeed: This is the maximum speed of the airplane.
     * @param MinSpeed: This is the minimum speed of the airplane.
     * @param Weight: This is they weight of the airplane.
     * @param Type: This is the type of the airplane
     * @param Manufacturer: This is the builder of the airplane.
     * @param PlaneHeight: The height of the airplane.
     * @param PlaneWidth: The width of the airplane.
     * @param PlaneLength: The length of the airplane.
     * @param MaxFuel: The maximum fuel of the airplane.
     * @param FuelUsage: The usage of fuel of the airplane
     * @param Direction: The current direction of the airplane.
     * @param Speed: The current speed of the airplane.
     * @param CurrentFuel: The current amount of fuel in the tank.
     * @param Altitude: The current flight height of the airplane.
     */
    public Airplane(int MaxSpeed, int MinSpeed, int Weight, String Type, String Manufacturer,
            int PlaneHeight, int PlaneWidth, int PlaneLength, int MaxFuel, int FuelUsage, int Direction, double Speed, int CurrentFuel, double Altitude) {
        super(MaxSpeed, MinSpeed, Weight, Type, Manufacturer, PlaneHeight, PlaneWidth, PlaneLength, MaxFuel, FuelUsage);
        this.Direction = Direction;
        this.Speed = Speed;
        this.CurrentFuel = CurrentFuel;
        this.Altitude = Altitude;
    }
    

    
    
    public void Fly(int direction, int speed, double altitude) {
        ChangeSpeed();
        ChangeDirection();
        ChangeAltitude();
        this.Direction = direction;
        this.Speed = speed;
        this.Altitude = altitude;
    }

    void Landing(Runway r, int direction) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void TakeOff(Runway r, int direction, double height, double speed) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    

    public void ChangeSpeed() {
        if (AimedSpeed != this.Speed) {
            if (this.Speed > AimedSpeed) {
                this.Speed--;
            } else if (this.Speed < AimedSpeed) {
                this.Speed++;
            }
        }
    }
        

    public void ChangeDirection() {
        if (AimedDirection != this.Direction) {
            if (this.Direction > AimedDirection) {
                this.Direction--;
            } else if (this.Direction < AimedDirection) {
                this.Direction++;
            }
        }
    }

    public void ChangeAltitude() {
        if (AimedAltitude != this.Altitude) {
            if (this.Altitude > AimedAltitude) {
                this.Altitude--;
            } else if (this.Altitude < AimedAltitude) {
                this.Altitude++;
            }
        }
    }

    public void ChangeFuel() {
        this.CurrentFuel = (this.MaxFuel - this.FuelUsage);
    }

    //Setters
    public void SetAimedSpeed(double speed) {
        this.AimedSpeed = speed;
    }

    public void SetAimedDirection(double direction) {
        this.AimedDirection = direction;
    }

    public void SetAimedAltitude(double altitude) {
        this.AimedAltitude = altitude;
    }

    //Getters
    public double getAltitude() {
        return Altitude;
    }

    public int getCurrentFuel() {
        return CurrentFuel;
    }

    public double getDirection() {
        return Direction;
    }

    public double getSpeed() {
        return Speed;
    }
}