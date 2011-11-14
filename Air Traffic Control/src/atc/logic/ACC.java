package atc.logic;

import java.util.*;
import java.io.*;

/**
 * An ACC which has control over a CTA
 * 
 * @author Henk
 */
public class ACC {

    /**************Datafields***********/
    
    /**
     * unique identification number of the ACC
     */
    private int ID;
    /**
     * the CTA over which is has control
     */
    private CTA cta;
    /**
     * an arrayList of flightplans
     */
    private ArrayList<Flightplan> fp;
    /**
     * the identification number of a flightplan
     */
    private int flightnumber = 1;
    /**
     * an arraylist of airplanefactories
     */
    private ArrayList<AirplaneFactory> airplaneFactoryList;
    /**
     * an airplane factory
     */
    private AirplaneFactory airplaneFactory;

    /***************Constructor**********/
    
    /** 
     * An ACC is made with its own unique ID linked to a Control Area (CTA).
     * 
     * @Param ID: ID is the unique identification number for each Area Control Center (ACC).
     * 
     * @Param CTA is the Control Area over which this specific ACC has control.
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
    /**
     * Method to get ID of this ACC
     * 
     * @return ID
     */
    public int GetID() {
        return ID;
    }

    /**
     * Method to get a CTA
     * 
     * @return CTA
     */
    public CTA GetCTA() {
        return cta;
    }

    /**
     * Method to get list of flightplans
     * 
     * @return list of flightplans
     */
    public ArrayList<Flightplan> getfp() {
        return fp;
    }
    
    /**
     * Method to get the Airplane Factory
     * 
     * @return airplane factory
     */
    public AirplaneFactory GetAirplaneFactory(int AirplaneFactoryID) {
        for (AirplaneFactory a : airplaneFactoryList) {
            if (a.getID() == AirplaneFactoryID) {
                airplaneFactory = a;
            }
        }
        return airplaneFactory;
    }
    
    /**
     * Method to get all Available Airplanes
     * 
     * @return list of available airplanes
     */
    public ListIterator<AirplaneFactory> getAvailableAirplanes() {
        return airplaneFactoryList.listIterator();
    }

    /**************Methods**************/
    
    /**
    * All airplanes in the AvailableAirplanes.dat list will be read into a list.
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
     * and does not suddenly change from 100 to 300 without taking time to do so.
     * 
     * @param speed is the desired new speed for the airplane.
     * 
     * @param a is the Airplane of which you want to change the speed.
     *  
     * @return true is returned when the speed has succesfully been changed.
     * 
     * @return false is returned if the speed was above/below the planes maximum/minimum speed.
     */
    public void ChangeSpeed(double speed, Airplane a) throws AssignmentException {
        if (speed > a.getMinSpeed() && speed < a.getMaxSpeed()) {
            a.SetAimedSpeed(speed);
        } else {
            throw new AssignmentException("The Speed given is too low or high.");
        }
    }

    /**
     * Method to change the direction of the airplane. This is done through setting
     * the aimed direction of the airplane so it has time to change the direction.
     * 
     * @param direction is the new aimed direction in degrees
     * 
     * @param a is the airplane for which the new direction is intended.
     * 
     * Question: Do we want to recalculate the direction in degrees to the direction
     * in radial or will we do that elsewhere as in before we invoke this method
     * or in the airplane class itself during the I guess fly method?
     * At the moment I have programmed it doing the calculation here. If we want
     * to move it the change calculation can be copied.
     * 
     * @return true is returned if the new direction has succesfully been set.
     * 
     * @return false is when it was not possible to set the new direction.
     */
    public void ChangeDirection(double direction, Airplane a) throws AssignmentException {
        if (direction >= 0 && direction <= 360) {
            a.SetAimedDirection(direction);
        } else {
            throw new AssignmentException("The given direction is not possible.");
        }
    }

    /**
     * Method to change the height of an airplane. This is done by setting the
     * aimed height and have the plane climb to this altitude in its own speed.
     * 
     * @param height is the new altitude to which the airplane will climb.
     * 
     * @param a is the airplane for whom this change is intended.
     * 
     * Question: I seem to recall that the height is being set by flightlevels (1,2 or 3)
     * does height represent the new flightlevel to which the plane will climb or does it
     * actually represent an amount in feet? Height will be given in flightlevel then
     * recalculated to feet for the actual airplane.
     * 
     * @return true if the change in altitude has succesfully been done.
     * 
     * @return false if the change was incorrect and could not be made.
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
     * Method to give the airplane the direction in which it has to approach
     * a runway.
     * 
     * @param r is the runway on which the airplane has to land.
     * 
     * @param a is the airplane to which this assignement is given.
     * 
     * @param direction this is the direction in which the airplane will use for its final approach to the runway.
     *  
     * @return true is given when the assignment has been succesfully transferred to the airplane.
     * 
     * @return false is given when the assignement has not been succesfully transferred to the airplane.
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
     * Method to give the airplane the direction, speed and height which it has
     * to use after its initial takeoff.
     * 
     * @param r is the runway on which the airplane has to land.
     * 
     * @param a is the airplane to which this assignement is given.
     * 
     * @param direction this is the direction in which the airplane will take
     * off or will use for its final approach to the runway.
     * 
     * @param height to which the airplane has to climb right after its takeoff.
     * 
     * @param speed which the airplane has to maintain once it has taken off.
     *  
     * @return true is given when the assignment has been succesfully transferred to the airplane.
     * 
     * @return false is given when the assignement has not been succesfully transferred to the airplane.
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
     * GiveLandingRunway method incase the runway is not available.
     * 
     * @param a is the airplane that has to start circling the airport.
     */
    public void CircleAirplane(Airplane a) {
        a.setStatus(Airplane.Statusses.INLANDINGQUEUE);
    }

    /**
     * Method to create a flightplan
     * 
     * @param a is an AirplaneFactory which contains the airplane
     * 
     * @param start is an airport where the airplane takes off
     * 
     * @param end is an airport where the airplane lands
     * 
     * @param arrival is the date and time when the airplane arrives
     * 
     * @param departure is the date and time when the airplane departures
     * 
     * @param flightnumber is the flightnumber of the airplane
     */
    public void CreateFlight(AirplaneFactory a, Airport start, Airport end, GregorianCalendar arrival, GregorianCalendar departure) throws AssignmentException {
        Airplane ap = new Airplane(a.getMaxSpeed(), a.getMinSpeed(), a.getWeight(), a.getType(), a.getManufacturer(),
                a.getPlaneHeight(), a.getPlaneWidth(), a.getPlaneLength(), a.getMaxFuel(), a.getFuelUsage(), 
                0, 0, 300, 0, start.getLocation(), end.getLocation(), flightnumber);
        
        fp.add(new Flightplan(end, start, flightnumber, departure, arrival, ap));
        flightnumber++;
        cta.addAirplane(ap);
        new Thread(ap).start();
        double direction = GeoLocation.CalcDirection(start, end);
        System.out.println("The direction to the airfield is " + direction);
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
    
    /**
     * Method to get all Flightplans
     * 
     * @return a list with all Flightplans
     */
    public ListIterator<Flightplan> getFlightplans() {
        return fp.listIterator();
    }
}
