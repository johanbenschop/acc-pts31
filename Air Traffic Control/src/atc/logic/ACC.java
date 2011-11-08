package atc.logic;

import java.util.*;
import java.io.*;

/**
 * 
 * @author Henk
 */
public class ACC {

    /**************Datafields***********/
    private int ID;
    private CTA cta;
    private ArrayList<Flightplan> fp;
    private int flightnumber = 1;
    private ArrayList<AirplaneFactory> airplaneFactoryList;
    private AirplaneFactory airplaneFactory;

    /***************Constructor**********/
    /** 
     * An ACC is made with its own unique ID linked to a Control Area (CTA).
     * 
     * @Param ID: ID is the unique identification number for each Area Control Center(ACC).
     * 
     * @param CTA is the Control Area over wich this specific ACC has control.
     */
    public ACC(int ID, CTA CTA) {
        this.ID = ID;
        cta = CTA;
        fp = new ArrayList<Flightplan>();
        airplaneFactoryList = new ArrayList<>();
        try {
            loadAvailableAirplaneList();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**************Getters**************/
    public int GetID() {
        return ID;
    }

    public CTA GetCTA() {
        return cta;
    }

    public ArrayList<Flightplan> getfp() {
        return fp;
    }

    public AirplaneFactory GetAirplaneFactory(int AirplaneFactoryID) {
        for (AirplaneFactory a : airplaneFactoryList) {
            if (a.getID() == AirplaneFactoryID) {
                airplaneFactory = a;
            }
        }
        return airplaneFactory;
    }
    
    public ListIterator<AirplaneFactory> getAvailableAirplanes() {
        return airplaneFactoryList.listIterator();
    }

    /**************Methods**************/
    
     /**
     * All airplanes in the AvailableAirplanes.dat list will be read into a list.
     * @return
     */
    public void loadAvailableAirplaneList() throws FileNotFoundException, IOException {
        FileInputStream fstream2 = new FileInputStream("AvailableAirplanes.dat");

        DataInputStream in2 = new DataInputStream(fstream2);
        BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));

        String strline2;
        while ((strline2 = br2.readLine()) != null) {
            try {
                String[] props2 = strline2.split(",");

                int MaxSpeed = Integer.parseInt(props2[0]);
                int MinSpeed = Integer.parseInt(props2[1]);
                int Weight = Integer.parseInt(props2[2]);
                String Type = props2[3];
                String Manufacturer = props2[4];
                int PlaneHeight = Integer.parseInt(props2[5]);
                int PlanWidth = Integer.parseInt(props2[6]);
                int PlaneLength = Integer.parseInt(props2[7]);
                int MaxFuel = Integer.parseInt(props2[8]);
                int FuelUsage = Integer.parseInt(props2[9]);

                AirplaneFactory airplaneFactory = new AirplaneFactory(MaxSpeed, MinSpeed, Weight, Type, Manufacturer, PlaneHeight, PlanWidth, PlaneLength, MaxFuel, FuelUsage);
                
                airplaneFactoryList.add(airplaneFactory);
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Corrupt data line...");
            }
        }
    }

/**
 * Method to change the speed of the airplane. First its set to the Aimed
 * speed of the airplane so it can take its needed time to get to this speed
 * and doesnt suddenly change from 100 to 300 without taking time to do so.
 * 
 * @param speed is the desired new speed for the airplane.
 * 
 * @param a is the Airplane of wich you want to change the speed.
 * 
 * Question: Do we want to handle the not allowed assignements thru an exception
 * or do we want to return false as ive done now. If we do it thru an exception,
 * do we want to make one ourselves or is there an exception in java wich we
 * can use.
 * 
 * @return true is returned when the speed has succesfully been change.
 * @return false is returned if the speed was above the planes maximum speed.
 */
public void ChangeSpeed(double speed, Airplane a) throws AssignmentException {
        if (speed > a.getMinSpeed() && speed < a.getMaxSpeed()) {
            a.SetAimedSpeed(speed);
        } else {
            throw new AssignmentException("The Speed given is too low or high.");
        }
    }

    /**
     * Method to change the direction of the airplane. This is done thru setting
     * the aimed direction of the airplane so it has time to change the direction.
     * 
     * @param direction is the new aimed direction in degrees?
     * 
     * @param a is the airplane for wich the new direction is intended.
     * 
     * Question: Do we want to recalculate the direction in degrees to the direction
     * in radial or will we do that elsewhere as in before we invoke this method
     * or in the airplane class itself during the i guess fly method?
     * At the moment i have programmed it doing the calculation here. If we want
     * to move it the change calculation can be copied.
     * 
     * @return true is returned if the new direction has succesfully been set.
     * @return false is when it was not possible to set the new direction.
     */
    public void ChangeDirection(double direction, Airplane a) throws AssignmentException {
        if (direction >= -180 && direction <= 180) {
            a.SetAimedDirection(direction);
        } else {
            throw new AssignmentException("The given direction is not possible.");
        }
    }

    /**
     * Method to change the height of an airplane. This is done by setting the
     * aimedheight and have the plane climb to this altitude in its own speed.
     * 
     * @param height is the new altitude to wich the airplane should climb.
     * @param a is the airplane for whom this change is intended.
     * 
     * Question: I seem to recall that the height is being set by flightlevels (1,2 or 3)
     * does height represent the new flightlevel to wich the plane will climb or does it
     * actually represent an amount in feet? Height will be given in flightleven then
     * recalculated to feet for the actual airplane.
     * 
     * @return true if the change in altitude has succesfully been done.
     * 
     * @return false if the change was incorrect and could not be made.
     * 
     * The further implementation of this method will need to wait till the answer has been given
     * to the question, current implementation is a placeholder.
     */
    public void ChangeHeight(int flightlevel, Airplane a) throws AssignmentException {
        if (flightlevel == 1) {
            a.SetAimedAltitude(1000);
        } else if (flightlevel == 2) {
            a.SetAimedAltitude(2000);
        } else if (flightlevel == 3) {
            a.SetAimedAltitude(3000);
        } else {
            throw new AssignmentException();
        }
    }

    /**
     * Method to give the airplane the direction in wich it has to approach
     * a runway.
     * 
     * @param r is the runway on wich the airplane has to land.
     * 
     * @param a is the airplane to wich this assignement is given.
     * 
     * @param direction this is the direction in wich the airplane will take
     * off or will use for its final approach to the runway.
     * 
     * Again this is placeholder method changes can still be made depending on
     * how we decide it will eventually have to work.
     * 
     * Question: Do we want to use a boolean or shall we create our own exception for these cases.
     * 
     * @return true is given when the assignment has been succesfully transferred to the airplane.
     * @return false is given when the assignement wasnt succesfully given to the airplane.
     */
    public void LandFlight(Flightplan fp) throws AssignmentException {
        Runway runway = fp.getDestinationAirport().getRunway();
        if (runway.getAvailability() == true) {
            runway.ChangeAvailability(false);
            fp.getAirplane().Land(runway);
        } else {
            throw new AssignmentException("Runway is unavailable.");
        }
    }

    /**
     * Method to give the airplane the direction, speed and height wich it has
     * to use after its initial takeoff.
     * 
     * @param r is the runway on wich the airplane has to land.
     * 
     * @param a is the airplane to wich this assignement is given.
     * 
     * @param direction this is the direction in wich the airplane will take
     * off or will use for its final approach to the runway.
     * 
     * @param height to wich the airplane has to climb right after its takeoff.
     * 
     * @param speed it has to maintain once it has taken off.
     * 
     * Again this is placeholder method changes can still be made depending on
     * how we decide it will eventually have to work.
     * 
     * Question: Do we want to use a boolean or shall we create our own exception for these cases.
     * 
     * @return true is given when the assignment has been succesfully transferred to the airplane.
     * @return false is given when the assignement wasnt succesfully given to the airplane.
     */
    public void GiveRunwayTakeOff(Runway r, Airplane a, double direction, double height, double speed) throws AssignmentException {
        if (r.getAvailability() == true) {
            r.ChangeAvailability(true);
            a.TakeOff(r, direction, height, speed);
            a.setStatus(Airplane.Statusses.TAKINGOFF);
        } else {
            throw new AssignmentException("Runway is not available.");
        }
    }

    /**
     * Method has to be called when assignmentexception is given on the
     * GiveLandingRunway method incase the runway is not unavailable.
     * 
     * @param a is the airplane that as to start circling the airport.
     */
    public void CircleAirplane(Airplane a) {
        a.setStatus(Airplane.Statusses.INLANDINGQUEUE);
    }

    /**
     * Method to create a flightplan
     * @param a
     * @param start
     * @param end
     * @param arrival
     * @param departure
     * @param flightnumber 
     */
    public void CreateFlight(AirplaneFactory a, Airport start, Airport end, GregorianCalendar arrival, GregorianCalendar departure) throws AssignmentException {
        
        Airplane ap = new Airplane(a.getMaxSpeed(), a.getMinSpeed(), a.getWeight(), a.getType(), a.getManufacturer(),
                a.getPlaneHeight(), a.getPlaneWidth(), a.getPlaneLength(), a.getMaxFuel(), a.getFuelUsage(), 0, 0, 300, 0, start.getLocation(), end.getLocation(), flightnumber);
        
        fp.add(new Flightplan(end, start, flightnumber, departure, arrival, ap));
        flightnumber++;
        cta.addAirplane(ap);
        new Thread(ap).start();
        double direction = CalcDirection(start, end);
            System.out.println(ap.getStatus());
            int i = 0;
            while (ap.getStatus() == Airplane.Statusses.INTAKEOFFQUEUE) {
                System.out.println(direction);
                i++;
            if(start.getRunway() != null){
            GiveRunwayTakeOff(start.getRunway(), ap, direction , 2, 300);
            System.out.println(ap.getStatus());
            }
        }
    }
    
    // READ ME! According to the class diagram the ACC does not have an direct relation to the Flightplan.
    // However if you think that it was an wrong decision then please notify the rest of us :)....
    
    public ListIterator<Flightplan> getFlightplans() {
        return fp.listIterator();
    }
    
    public double CalcDirection(Airport a, Airport b){
        GeoLocation locationA = new GeoLocation(0,0,0);
        GeoLocation locationB = new GeoLocation(0,0,0);
        locationA = a.getLocation();
        locationB = b.getLocation();
        double dLat = Math.toRadians(locationB.getLatitude() - locationA.getLatitude());
        double dLon = Math.toRadians(locationB.getLongitude() - locationA.getLongitude());
        double lat1 = Math.toRadians(locationA.getLatitude());
        double lat2 = Math.toRadians(locationB.getLatitude());
        
        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.cos(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon);
        double direction = Math.toDegrees(Math.toRadians(Math.atan2(y, x)));
        return direction;
    }
}
