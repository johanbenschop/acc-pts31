package atc.logic;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Paul
 */
public class Airplane extends Thread {

    /**************Datafields***********/
    private int MaxSpeed;
    private int MinSpeed;
    private int Weight;
    private String Type;
    private String Manufacturer;
    private int PlaneHeight;
    private int PlaneWidth;
    private int PlaneLength;
    private int AirplaneNumber; //Number
    private double Direction; // Degree's
    private double Speed; // Kilometers per hour
    private int MaxFuel;  // Gallons
    private int CurrentFuel; // Gallons
    private int FuelUsage; // Gallons
    private double Altitude; // Kilometers
    private double AimedSpeed; // Kilometers per hour
    private double AimedDirection; // Degree's (could be changed)
    private double AimedAltitude; // Kilometers
    private Statusses Status;
    //private Date SecondsBeforeRunning = new Date();
    //private Date SecondsRunning = new Date();
    private double takeOffAccelerationSpeed = 0.667; // Kilometers per hour
    private GeoLocation destinationLocation;
    private GeoLocation location;
    private double distanceTravelled; // distance travelled per 1/10e sec in km/h.
    private double longitudeTravelled;
    private double latitudeTravelled;
    private boolean boolTakeOff = false;
    

    public enum Statusses {

        STANDINGONAIRPORT, TAKINGOFF, INFLIGHT, INLANDINGQUEUE, LANDING, CRASHING1, CRASHING2, CRASHED, INTAKEOFFQUEUE;
    }

    /***************Constructor**********/
    /**
     * An Airplane is made with its own maximum speed, minimum speed, weight,
     * type, manufacturer, planeheight, planewidth, planelength, maximum fuel, 
     * fuel usage, direction, speed, currentfuel and altitude.
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
            int PlaneHeight, int PlaneWidth, int PlaneLength, int MaxFuel, int FuelUsage, int Direction, double Speed, int CurrentFuel, double Altitude, GeoLocation Location, GeoLocation DestinationLocation, int AirplaneNumber) {
        this.MaxSpeed = MaxSpeed;
        this.MinSpeed = MinSpeed;
        this.Weight = Weight;
        this.Type = Type;
        this.Manufacturer = Manufacturer;
        this.PlaneHeight = PlaneHeight;
        this.PlaneWidth = PlaneWidth;
        this.PlaneLength = PlaneLength;
        this.MaxFuel = MaxFuel;
        this.FuelUsage = FuelUsage;
        this.Direction = Direction;
        this.Speed = Speed;
        this.CurrentFuel = CurrentFuel;
        this.Altitude = Altitude;
        this.AirplaneNumber = AirplaneNumber;
        this.location = Location;
        this.destinationLocation = DestinationLocation;
        this.Status = Statusses.INTAKEOFFQUEUE;
        
    }

    @Override
    public void run() {
        while (Status == Statusses.INFLIGHT || Status == Statusses.TAKINGOFF || Status == Statusses.CRASHING1 || Status == Statusses.CRASHING2 || Status == Statusses.INLANDINGQUEUE || Status == Statusses.LANDING) {
            try {
                Fly();
                Thread.sleep(100);// er word telkens 1/10e seconde gewacht.
            } catch (InterruptedException ex) {
                Logger.getLogger(Airplane.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This will run the methods ChangeSpeed, ChangeDirection and ChangeAltitude,
     * which change the speed, direction and altitude from an airplane.
     */
    public void Fly() {
//        if(this.Status == Statusses.TAKINGOFF && boolTakeOff == false)
//        {
//            boolTakeOff = true;
//            
//        }
        if (distFrom(this.getLocation().getLatitude(), this.getLocation().getLongitude(), destinationLocation.getLatitude(), destinationLocation.getLongitude()) <= 20000) {
            this.Status = Statusses.INLANDINGQUEUE;
            Circling();
            ChangeSpeed();
            ChangeAltitude();
            ChangeGeoLocation();
        } else {
            ChangeSpeed();
            ChangeDirection();
            ChangeAltitude();
            ChangeGeoLocation(); 
        }
    }

    /**
     * This will make an airplane land on a runway.
     * @param r : Runway
     * @param direction : The direction of the runway
     */
    public void Land(Runway r) {
        if(this.Status == Statusses.INLANDINGQUEUE)
        {
        this.Status = Statusses.LANDING;
        SetAimedDirection(r.getDirection());
        SetAimedAltitude(0);
        SetAimedSpeed(0);
        }
    }

    /**
     * This will take off and airplane.
     * @param r : runway
     * @param direction : The direction in degrees
     * @param altitude : The altitude in feet
     * @param speed : The speed in km/h
     */
    public void TakeOff(Runway r, double direction, double altitude, double speed) {
        System.out.println(direction);
        location.setAltitude(r.getAltitude());
        location.setLatitude(r.getLatitude());
        location.setLongitude(r.getLongitude());
        this.Status = Statusses.TAKINGOFF;
        SetAimedDirection(direction);
        SetAimedAltitude(altitude);
        SetAimedSpeed(speed);
    }
    
    void Circling()
    {
        Direction += 10;
    }

    /**
     * Change the longitude and lattitude based on the distance travelled.
     */
    public void ChangeGeoLocation() {
        //Latitude: 1 deg = 110.54 kmLongitude: 1 deg = 111.320*cos(latitude) km
        distanceTravelled = (this.Speed / 36000);
        latitudeTravelled = distanceTravelled * Math.sin(Direction);
        longitudeTravelled = distanceTravelled * Math.cos(Direction);
        location.setLatitude((latitudeTravelled / 110.54) + location.getLatitude());
        location.setLongitude((longitudeTravelled / (111.320 * Math.cos(location.getLatitude()))) + location.getLongitude());
        location.setAltitude(Altitude);

        System.out.println(location.ToString());

        /*
        longitudeTravelled = distanceTravelled * Math.sin(Direction);
        latitudeTravelled = distanceTravelled * Math.cos(Direction);
        location.setLatitude((latitudeTravelled / 110.54) + location.getLatitude());
        location.setLongitude((longitudeTravelled / (111.320*Math.cos(location.getLatitude()))) + location.getLongitude());*/
    }

    /**
     * The speed at the takeoff will change with 6,67 km/h every second.
     * If it finished the takeoff then the speed wil increase or decrease with 10 km/h every second.
     */
    public void ChangeSpeed() {
        double amountChangeSpeed = 1;
        if (this.Status == Statusses.TAKINGOFF) {
            if (this.Speed < 300) {
                this.Speed += takeOffAccelerationSpeed;
                this.Status = Statusses.INFLIGHT;
            }
        } else if (this.Status == Statusses.CRASHED) {
            this.Speed = 0;
        } else if (AimedSpeed != this.Speed) {
            if (this.Speed - amountChangeSpeed > AimedSpeed) {
                this.Speed -= amountChangeSpeed;
            } else if (this.Speed + amountChangeSpeed < AimedSpeed) {
                this.Speed += amountChangeSpeed;
            } else {
                this.Speed = AimedSpeed;
            }
        }

    }

    /**
     * The direction will increase or decrease every second with 3 degrees.
     */
    public void ChangeDirection() {
//        double amountChangeDirection = 0.3;
//        if (AimedDirection != this.Direction) {
//            if (this.Direction - amountChangeDirection > AimedDirection) {
//                this.Direction -= amountChangeDirection;
//            } else if (this.Direction + amountChangeDirection < AimedDirection) {
//                this.Direction += amountChangeDirection;
//            } else {
                this.Direction = this.AimedDirection;
//            }
//        }
    }

    /**
     * The altitude will increase or decrease with 20 feet every second.
     */
    public void ChangeAltitude() {
//        double amountChangeHeight = 2.0;
//        if (AimedAltitude != this.Altitude) {
//            if (this.Altitude - amountChangeHeight > AimedAltitude) {
//                this.Altitude -= amountChangeHeight;
//            } else if (this.Altitude + amountChangeHeight < AimedAltitude) {
//                this.Altitude += amountChangeHeight;
//            } else {
                this.Altitude = this.AimedAltitude;
//            }
//        }
    }

    public void ChangeFuel() {
        this.CurrentFuel = (this.MaxFuel - this.FuelUsage);
    }
    
    public double distFrom(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        int meterConversion = 1609;

        return new Double(dist * meterConversion).doubleValue();
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

    public void setStatus(Statusses Status) {
        this.Status = Status;
    }

    //Getters
    public int getAirplaneNumber() {
        return AirplaneNumber;
    }

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

    public double getAimedAltitude() {
        return AimedAltitude;
    }

    public double getAimedDirection() {
        return AimedDirection;
    }

    public double getAimedSpeed() {
        return AimedSpeed;
    }

    public Statusses getStatus() {
        return Status;
    }

    public GeoLocation getLocation() {
        return location;
    }

    public int getFuelUsage() {
        return FuelUsage;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public int getMaxFuel() {
        return MaxFuel;
    }

    public int getMaxSpeed() {
        return MaxSpeed;
    }

    public int getMinSpeed() {
        return MinSpeed;
    }

    public int getPlaneHeight() {
        return PlaneHeight;
    }

    public int getPlaneLength() {
        return PlaneLength;
    }

    public int getPlaneWidth() {
        return PlaneWidth;
    }

    public String getType() {
        return Type;
    }

    public int getWeight() {
        return Weight;
    }

    public GeoLocation getDestinationLocation() {
        return destinationLocation;
    }

    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    public double getLatitudeTravelled() {
        return latitudeTravelled;
    }

    public double getLongitudeTravelled() {
        return longitudeTravelled;
    }

    public double getTakeOffAccelerationSpeed() {
        return takeOffAccelerationSpeed;
    }
    
    
}
