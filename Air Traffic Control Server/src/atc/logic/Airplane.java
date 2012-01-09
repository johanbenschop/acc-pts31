package atc.logic;

import atc.interfaces.*;
import java.util.logging.*;
import java.util.prefs.Preferences;

/**
 * @author Paul
 */
public class Airplane extends IAirplane {

    /**************Datafields***********/
    /**
     * The max speed of the airplane
     */
    private int MaxSpeed;
    /**
     * The min speed of the airplane
     */
    private int MinSpeed;
    /**
     * The weight of the airplane
     */
    private int Weight;
    /**
     * The type of the airplane
     */
    private String Type;
    /**
     * The manufacturer of the airplane
     */
    private String Manufacturer;
    /**
     * The height of the airplane
     */
    private int PlaneHeight;
    /**
     * The width of the airplane
     */
    private int PlaneWidth;
    /**
     * The length of the airplane
     */
    private int PlaneLength;
    /**
     * The number of the airplane
     */
    private int AirplaneNumber;
    /**
     * The direction the airplane is facing in degrees
     */
    private double Direction;
    /**
     * The speed of the airplane in km/h
     */
    private double Speed;
    /**
     * The maximal fuel the airplane can contain in gallons
     */
    private int MaxFuel;
    /**
     * The current fuel of the airplane in gallons
     */
    private int CurrentFuel;
    /**
     * The fuel usage in gallons                                        *per wat??????*
     */
    private int FuelUsage;
    /**
     * The altitude in km of the airplane
     */
    private double Altitude;
    /**
     * The speed the airplane must get at in km/h
     */
    private double AimedSpeed;
    /**
     * The direction the airplane has to get facing in degrees
     */
    private double AimedDirection;
    /**
     * The altitude the airplane must get at in km
     */
    private double AimedAltitude;
    /**
     * The status of the airplane
     */
    private Statusses Status;
    /**
     * The takeoff acceleration speed of the airplane
     */
    private double takeOffAccelerationSpeed = 0.667; // Kilometers per hour
    /**
     * The destination location as a GeoLocation
     */
    private IGeoLoc destinationLocation;
    /**
     * The current location of the airplane as a GeoLocation
     */
    private IGeoLoc location;
    /**
     * The distance the airplane has travelled per 1/10e sec in km/h
     */
    private double distanceTravelled;
    /**
     * The longitude the airplane has travelled                           klopt dit???
     */
    private double longitudeTravelled;
    /**
     * The latitude the airplane has travelled                              klopt dit???
     */
    private double latitudeTravelled;
    /**
     * Whether the airplane is in a landing queue
     */
    private boolean InLandingQueue = false;
    /**
     * Whether the airplane is within radius of the destination                      klopt dit??
     */
    private boolean withinRadius = false;
    /**
     * Whether the airplane is colliding with another airplane
     */
    private boolean collcheck;
    /**
     *                                                                              wat is dit?
     */
    private static Preferences prefs = Preferences.userRoot().node("/atc/gui");

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
            int PlaneHeight, int PlaneWidth, int PlaneLength, int MaxFuel, int FuelUsage, int Direction, double Speed, int CurrentFuel, double Altitude, IGeoLoc Location, IGeoLoc DestinationLocation, int AirplaneNumber) {
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
        collcheck = false;
    }

    /**
     * Run method to start the thread which contains the current airplane. Start
     * this and the airplane starts flying.
     */
    @Override
    public void run() {
        double SIM_SPEED = prefs.getDouble("SIM_SPEED", 1);
        try {
            Thread.sleep(1000); // I need this to let the plane leave...wierd...
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        while (true) {
            //System.out.println("Speed: " + Speed + "Status: " + Status.toString());
            while (Status == Statusses.INFLIGHT || Status == Statusses.TAKINGOFF
                    || Status == Statusses.CRASHING1 || Status == Statusses.CRASHING2
                    || Status == Statusses.INLANDINGQUEUE || Status == Statusses.LANDING) {
                try {
                    Fly();
                    //System.out.println("Speed: " + Speed + "Status: " + Status.toString());
                    Thread.sleep((int) (100 / prefs.getDouble("SIM_SPEED", 1)));// er word telkens 1/10e seconde gewacht.
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
            setAimedSpeed(0);
            setAimedAltitude(0);
            ChangeSpeed();
            ChangeAltitude();
            ChangeGeoLocation();
        }
    }

    /**
     * This will make an airplane land on a runway.
     */
    @Override
    public void Land() {
        if (this.Status == Statusses.INLANDINGQUEUE) {
            this.Status = Statusses.LANDING;
            this.AimedDirection = GeoLocation.CalcDirection(location, destinationLocation);
            setAimedAltitude(0);
        }
    }

    /**
     * This will take off and airplane.
     * @param r : runway
     * @param direction : The direction in degrees
     * @param altitude : The altitude in feet
     * @param speed : The speed in km/h
     */
    @Override
    public void TakeOff(IRunway r, double direction, double altitude, double speed) {
        System.out.println(direction);
        location.setAltitude(r.getLocation().getAltitude());
        location.setLatitude(r.getLocation().getLatitude());
        location.setLongitude(r.getLocation().getLongitude());
        this.Status = Statusses.TAKINGOFF;
        setAimedDirection(direction);
        setAimedAltitude(1000);
        setAimedSpeed(speed);
    }

    /**
     * When the airplane is circling around an airport this method is called to change the direction in wich it flies.
     */
    void Circling() {
        if (this.InLandingQueue == false) {
            this.Direction += 90;
            InLandingQueue = true;
        }
        if (InLandingQueue == true) {
            if (distFrom(this.getLocation().getLatitude(), this.getLocation().getLongitude(), destinationLocation.getLatitude(), destinationLocation.getLongitude()) <= 20000) {
                this.withinRadius = true;
            } else if (distFrom(this.getLocation().getLatitude(), this.getLocation().getLongitude(), destinationLocation.getLatitude(), destinationLocation.getLongitude()) > 20000) {
                this.withinRadius = false;
            }
            if (this.withinRadius == true) {
                if (this.Direction + 1 > 360) {
                    double newDirection;
                    newDirection = this.Direction;
                    newDirection += 1;
                    newDirection -= 360;
                    this.Direction = newDirection;
                } else {
                    this.Direction += 1;
                }
            } else if (withinRadius == false) {
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
     * Change the longitude and latitude based on the distance travelled and the direction in which the airplane flies.
     */
    public void ChangeGeoLocation() {
        // Formula:	lat2 = asin(sin(lat1)*cos(d/R) + cos(lat1)*sin(d/R)*cos(θ))
        //              lon2 = lon1 + atan2(sin(θ)*sin(d/R)*cos(lat1), cos(d/R)−sin(lat1)*sin(lat2))
        //
        //              θ is the bearing (in radians, clockwise from north);
        //              d/R is the angular distance (in radians), where d is the distance travelled and R is the earth’s radius

        double d = (this.Speed / 36000d);
        double θ = Direction / 180d * Math.PI;
        double R = 6371; // Mean radius / radius of the Earh

        double lat = location.getLatitude() / 180d * Math.PI;
        double lon = location.getLongitude() / 180d * Math.PI;

        double destLat = Math.asin(Math.sin(lat) * Math.cos(d / R)
                + Math.cos(lat) * Math.sin(d / R) * Math.cos(θ));
        double destLon = lon + Math.atan2(Math.sin(θ) * Math.sin(d / R) * Math.cos(lat),
                Math.cos(d / R) - Math.sin(lat) * Math.sin(destLat));

        location.setLatitude(destLat * 180 / Math.PI);
        location.setLongitude(destLon * 180 / Math.PI);
        location.setAltitude(Altitude);
    }

    /**
     * The speed at the takeoff will change with 6,67 km/h every second.
     * If it finished the takeoff then the speed wil increase or decrease with 10 km/h every second.
     */
    public void ChangeSpeed() {
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
    @Override
    public void setAimedSpeed(double speed) {
        this.AimedSpeed = speed;
    }

    @Override
    public void setAimedDirection(double direction) {
        this.AimedDirection = direction;
    }

    @Override
    public void setAimedAltitude(double altitude) {
        this.AimedAltitude = altitude;
    }

    @Override
    public void setStatus(Statusses Status) {
        this.Status = Status;
    }

    //Getters
    public int getFlightLevel() {
        return (int) this.Altitude / 1000;
    }

    @Override
    public int getAirplaneNumber() {
        return AirplaneNumber;
    }

    @Override
    public double getAltitude() {
        return Altitude;
    }

    @Override
    public int getCurrentFuel() {
        return CurrentFuel;
    }

    @Override
    public double getDirection() {
        return Direction;
    }

    @Override
    public double getSpeed() {
        //return 15000;
        return Speed;
    }

    @Override
    public double getAimedAltitude() {
        return AimedAltitude;
    }

    @Override
    public double getAimedDirection() {
        return AimedDirection;
    }

    @Override
    public double getAimedSpeed() {
        return AimedSpeed;
    }

    @Override
    public Statusses getStatus() {
        return Status;
    }

    @Override
    public IGeoLoc getLocation() {
        return location;
    }

    @Override
    public int getFuelUsage() {
        return FuelUsage;
    }

    @Override
    public String getManufacturer() {
        return Manufacturer;
    }

    @Override
    public int getMaxFuel() {
        return MaxFuel;
    }

    @Override
    public int getMaxSpeed() {
        return MaxSpeed;
    }

    @Override
    public int getMinSpeed() {
        return MinSpeed;
    }

    @Override
    public int getPlaneHeight() {
        return PlaneHeight;
    }

    @Override
    public int getPlaneLength() {
        return PlaneLength;
    }

    @Override
    public int getPlaneWidth() {
        return PlaneWidth;
    }

    @Override
    public String getType() {
        return Type;
    }

    @Override
    public int getWeight() {
        return Weight;
    }

    public IGeoLoc getDestinationLocation() {
        return destinationLocation;
    }

    @Override
    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    public double getLatitudeTravelled() {
        return latitudeTravelled;
    }

    @Override
    public double getLongitudeTravelled() {
        return longitudeTravelled;
    }

    public double getTakeOffAccelerationSpeed() {
        return takeOffAccelerationSpeed;
    }

    public boolean isCollcheck() {
        return collcheck;
    }

    @Override
    public void setCollcheck(boolean collcheck) {
        this.collcheck = collcheck;
    }    
}