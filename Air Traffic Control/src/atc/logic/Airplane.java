package atc.logic;

import atc.cli.CommandLine;
import atc.gui.atc2;
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
    private double takeOffAccelerationSpeed = 0.667; // Kilometers per hour
    private GeoLocation destinationLocation;
    private GeoLocation location;
    private double distanceTravelled; // distance travelled per 1/10e sec in km/h.
    private double longitudeTravelled;
    private double latitudeTravelled;
    private boolean InLandingQeueu = false;
    private boolean binnenStraal = false;

    public enum Statusses {

        STANDINGONAIRPORT, TAKINGOFF, INFLIGHT, INLANDINGQUEUE, LANDING,
        CRASHING1, CRASHING2, CRASHED, INTAKEOFFQUEUE, HASLANDED;
    }

    /***************Constructor**********/
    /**
     * Constructor to create an airplane.
     * @param MaxSpeed maximum speed for the airplane.
     * @param MinSpeed minimum speed for the airplane.
     * @param Weight the weight of the airplane.
     * @param Type the type of airplane (as in for boeing a 747)
     * @param Manufacturer the company that manufactured the airplane.
     * @param PlaneHeight the height of the airplane in meters
     * @param PlaneWidth the width of the airplane in meters.
     * @param PlaneLength the length of the airplane in meters.
     * @param MaxFuel the maximum amount of fuel in liters that an airplane can carry
     * @param FuelUsage the amount of fuel it uses on average during flight in liters.
     * @param Direction the current direction in wicht the airplane is heading.
     * @param Speed the current speed with wich the airplane is flying.
     * @param CurrentFuel the amount of fuel that is currently on board.
     * @param Altitude the current altitude at wich the airplane is flying.
     * @param Location the location in longitgude and latitude as well as the altitude.
     * @param DestinationLocation the location of the destination in longitude and latitude.
     * @param AirplaneNumber the number of the airplane.
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

    /**
     * Run method to start the thread wich contains the current airplane. Start
     * this and the airplane starts flying.
     */
    @Override
    public void run() {
        
        while (true) {
            //System.out.println("Speed: " + Speed + "Status: " + Status.toString());
            while (Status == Statusses.INFLIGHT || Status == Statusses.TAKINGOFF
                    || Status == Statusses.CRASHING1 || Status == Statusses.CRASHING2
                    || Status == Statusses.INLANDINGQUEUE || Status == Statusses.LANDING) {
                try {
                    Fly();
                    //System.out.println("Speed: " + Speed + "Status: " + Status.toString());
                    Thread.sleep(100);// er word telkens 1/10e seconde gewacht.
                } catch (InterruptedException ex) {
                    Logger.getLogger(Airplane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    /**
     * This will run the methods ChangeSpeed, ChangeDirection and ChangeAltitude,
     * which change the speed, direction and altitude from an airplane.
     */
    public void Fly() {
        if ((distFrom(this.getLocation().getLatitude(), this.getLocation().getLongitude(), destinationLocation.getLatitude(), destinationLocation.getLongitude()) <= 20000 && this.Status != Statusses.LANDING)
                || this.Status == Statusses.INLANDINGQUEUE) {
            this.Status = Statusses.INLANDINGQUEUE;
            Circling();
            Land();
//            ChangeSpeed();
//            ChangeAltitude();
//            ChangeGeoLocation();
        } else {
            ChangeSpeed();
            ChangeDirection();
            ChangeAltitude();
            ChangeGeoLocation();
        }
        if (distFrom(this.getLocation().getLatitude(), this.getLocation().getLongitude(), destinationLocation.getLatitude(), destinationLocation.getLongitude()) <= 500
                && this.Status == Statusses.LANDING) {
            this.Status = Statusses.HASLANDED;
            SetAimedSpeed(0);
            SetAimedAltitude(0);
            ChangeSpeed();
            ChangeAltitude();
            ChangeGeoLocation();
        }
    }

    /**
     * This will make an airplane land on a runway.
     * @param r : Runway where the airplane will land.
     */
    public void Land() {
        if (this.Status == Statusses.INLANDINGQUEUE) {
            this.Status = Statusses.LANDING;
            this.AimedDirection = GeoLocation.CalcDirection(location, destinationLocation);
            SetAimedAltitude(0);
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
        location.setAltitude(r.getLocation().getAltitude());
        location.setLatitude(r.getLocation().getLatitude());
        location.setLongitude(r.getLocation().getLongitude());
        this.Status = Statusses.TAKINGOFF;
        SetAimedDirection(direction);
        SetAimedAltitude(altitude);
        SetAimedSpeed(speed);
    }

    /**
     * When the airplane is circling around an airport this method is called to change the direction in wich it flies.
     */
    void Circling() {
        if (this.InLandingQeueu == false) {
            this.Direction += 90;
            InLandingQeueu = true;
        }
        if (InLandingQeueu == true) {
            if (distFrom(this.getLocation().getLatitude(), this.getLocation().getLongitude(), destinationLocation.getLatitude(), destinationLocation.getLongitude()) <= 20000) {
                this.binnenStraal = true;
            } else if (distFrom(this.getLocation().getLatitude(), this.getLocation().getLongitude(), destinationLocation.getLatitude(), destinationLocation.getLongitude()) > 20000) {
                this.binnenStraal = false;
            }
            if (this.binnenStraal == true) {
                if (this.Direction + 1 > 360) {
                    double newDirection;
                    newDirection = this.Direction;
                    newDirection += 1;
                    newDirection -= 360;
                    this.Direction = newDirection;
                } else {
                    this.Direction += 1;
                }
            } else if (binnenStraal == false) {
                if (this.Direction - 1 < 0) {
                    double newDirection;
                    newDirection = this.Direction;
                    newDirection -= 1;
                    newDirection += 360;
                    this.Direction = newDirection;
                } else {
                    this.Direction -= 1;
                }
            }
        }
        System.err.println(this.Direction);
    }

    /**
     * Change the longitude and lattitude based on the distance travelled and the direction in wich the airplane flies.
     */
    public void ChangeGeoLocation() {
        distanceTravelled = (this.Speed / 36000d);
        double bearing = Direction / 180d * Math.PI;

        double R = 6371;

        double lat = location.getLatitude() / 180d * Math.PI;
        double lon = location.getLongitude() / 180d * Math.PI;

        double destLat = Math.asin(Math.sin(lat) * Math.cos(distanceTravelled / R) + Math.cos(lat) * Math.sin(distanceTravelled / R) * Math.cos(bearing));
        double destLon = lon + Math.atan2(Math.sin(bearing) * Math.sin(distanceTravelled / R) * Math.cos(lat), Math.cos(distanceTravelled / R) - Math.sin(lat) * Math.sin(destLat));

        location.setLatitude(destLat * 180 / Math.PI);
        location.setLongitude(destLon * 180 / Math.PI);
        location.setAltitude(Altitude);

        // System.out.println(location.ToString());
    }

    /**
     * The speed at the takeoff will change with 6,67 km/h every second.
     * If it finished the takeoff then the speed wil increase or decrease with 10 km/h every second.
     */
    public void ChangeSpeed() {
        //System.out.println("Speed: " + Speed + "Status: " + Status.toString());
       double amountChangeSpeed = 1;
        if (this.Status == Statusses.TAKINGOFF) {
            if (this.Speed < MinSpeed) {
                this.Speed += takeOffAccelerationSpeed;
            } else {
                // The airplane is up in the air and can go to it's destination
                this.Status = Statusses.INFLIGHT;
                this.AimedDirection = GeoLocation.CalcDirection(location, destinationLocation);
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
     * The direction will increase or decrease every second with 3 degrees, this
     * will currently instantly change the direction, subject to change.
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
     * The altitude will increase or decrease with 20 feet every second. Currently
     * will change the altitude immeadiatly, subject to change if theres time.
     */
    public void ChangeAltitude() {
        double amountChangeHeight = 2.0;
        if (AimedAltitude != this.Altitude && this.Status != this.Status.TAKINGOFF) {
            if (this.Altitude - amountChangeHeight > AimedAltitude) {
                this.Altitude -= amountChangeHeight;
            } else if (this.Altitude + amountChangeHeight < AimedAltitude) {
                this.Altitude += amountChangeHeight;
            } else {
                this.Altitude = this.AimedAltitude;
            }
        }
    }
    
    /**
     * Method for calculating the current amount of fuel. Currently not in use.
     */
    public void ChangeFuel() {
        this.CurrentFuel = (this.CurrentFuel - this.FuelUsage);
    }

    /**
     * Method to calculate the distance from 1 point on the globe to the other point,
     * using latitude and longitude for both points.
     * 
     * @param lat1 latitude of point 1.
     * @param lon1 longitude of point 1.
     * @param lat2 latitude of point 2.
     * @param lon2 longitude of point 2.
     * @return the distance in meters between point 1 and 2.
     */
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
    public int getFlightLevel()
    {
        return (int) this.Altitude / 1000;
    }
    
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
