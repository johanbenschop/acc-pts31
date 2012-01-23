package atc.logic;

import atc.interfaces.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.Timer;

/**
 * An ACC which has control over a CTA
 * 
 * @author Henk
 */
public class ACC extends UnicastRemoteObject implements IACC {

    /**************Datafields***********/
    private ArrayList<RemoteListener> listeners;
    private final Object lockListener = new Object();
    /**
     * unique identification number of the ACC
     */
    private int ID;
    /**
     * logging file to write all the events that happen into a file
     */
    private Logging logging;
    /**
     * the CTA over which is has control
     */
    private ICTA cta;
    /**
     * an arrayList of flightplans
     */
    private ArrayList<IFlightplan> fp;
    /**
     * the identification number of a flightplan
     */
    private int flightnumber = 1;
    /**
     * an arraylist of airplanefactories
     */
    private ArrayList<IAirplaneFactory> airplaneFactoryList;
    /**
     * an airplane factory
     */
    private IAirplaneFactory airplaneFactory;
    private IFC flightcontroller;
    private ArrayList<IFC> flightControllers;
    private ArrayList<IACC> adjacentACCList;
    private Publisher publisher;
    private Timer publishtimer;

    /***************Constructor**********/
    /** 
     * An ACC is made with its own unique ID linked to a Control Area (CTA).
     * 
     * @Param ID: ID is the unique identification number for each Area Control Center (ACC).
     * 
     * @Param CTA is the Control Area over which this specific ACC has control.
     */
    public ACC(int ID, ICTA CTA) throws RemoteException {
        this.ID = ID;
        cta = CTA;
        fp = new ArrayList<>();
        airplaneFactoryList = new ArrayList<>();
        flightControllers = new ArrayList<>();
        publisher = new Publisher();

        try {
            loadAirplaneFactoryList();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        logging = new Logging(ID);
        publishtimer = new Timer(300, null);
        publishtimer.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                try {
                publisher.publishFlightplans(fp);
                } catch (RemoteException re) {
                    re.printStackTrace();
                }
            }
        });
        publishtimer.start();
    }

    /**************Getters**************/
    /**
     * Method to get ID of this ACC
     * 
     * @return ID
     */
    @Override
    public int GetID() {
        return ID;
    }

    /**
     * Method to get a CTA
     * 
     * @return CTA
     */
    @Override
    public ICTA GetCTA() {
        return cta;
    }

    /**
     * Method to get list of flightplans
     * 
     * @return list of flightplans
     */
    @Override
    public ArrayList<IFlightplan> getfp() {
        return fp;
    }

    /**
     * Method to get list of flight controllers
     * 
     * @return list of flight controllers
     */
    @Override
    public ArrayList<IFC> getfc() {
        return flightControllers;
    }

    /**
     * Method to get the FlightController 
     * 
     * @return FlightController
     */
    @Override
    public IFC GetFlightController(int FlightControllerID) throws RemoteException {
        for (IFC a : flightControllers) {
            if (a.getID() == FlightControllerID) {
                flightcontroller = a;
            }
        }
        return flightcontroller;
    }

    /**
     * Method to get the Airplane Factory
     * 
     * @return airplane factory
     */
    @Override
    public IAirplaneFactory GetAirplaneFactory(int AirplaneFactoryID) throws RemoteException {
        for (IAirplaneFactory a : airplaneFactoryList) {
            if (a.getID() == AirplaneFactoryID) {
                return a;
            }
        }
        return null;
    }

    /**
     * Method to get all Available Airplanes
     * 
     * @return list of available airplanes
     */
    @Override
    public ArrayList<IAirplaneFactory> getAvailableAirplanes() {
        return airplaneFactoryList;
    }

    @Override
    public ArrayList<IFC> getFlightControllers() {
        return flightControllers;
    }

    /**************Methods**************/
    /**
     * All airplanes in the AvailableAirplanes.dat list will be read into a list.
     */
    @Override
    public void loadAirplaneFactoryList() throws FileNotFoundException, IOException {
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

                IAirplaneFactory airplaneFactory = new AirplaneFactory(MaxSpeed, MinSpeed, Weight, Type, Manufacturer, PlaneHeight, PlanWidth, PlaneLength, MaxFuel, FuelUsage);

                airplaneFactoryList.add(airplaneFactory);
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Corrupt data line...");
            }
        }
    }

    // TODO edit comment block below. Block has no return value!
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
    @Override
    public void ChangeSpeed(double speed, IAirplane a) throws AssignmentException, RemoteException {
        if (speed >= a.getMinSpeed() && speed <= a.getMaxSpeed()) {
            a.setAimedSpeed(speed);
            try {
                String command = "change speed to: " + speed;
                logging.WriteCommand("sab", command);
            } catch (IOException e) {
                System.out.println("logging failed");
            }
        } else {
            throw new AssignmentException("The speed given is too low or high.");
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
    @Override
    public void ChangeDirection(double direction, IAirplane a) throws AssignmentException, RemoteException {
        if (direction >= -360 && direction <= 360) {
            a.setAimedDirection(direction);
            try {
                String command = "change direction to: " + direction;
                logging.WriteCommand("sab", command);
            } catch (IOException e) {
                System.out.println("logging failed");
            }
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
    @Override
    public void ChangeHeight(int flightlevel, IAirplane a) throws AssignmentException, RemoteException {
        if (flightlevel == 1) {
            a.setAimedAltitude(1000);
        } else if (flightlevel == 2) {
            a.setAimedAltitude(2000);
        } else if (flightlevel == 3) {
            a.setAimedAltitude(3000);
        } else {
            throw new AssignmentException();
        }
        try {
            String command = "change flightlevel to: " + flightlevel;
            logging.WriteCommand("sab", command);
        } catch (IOException e) {
            System.out.println("logging failed");
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
    @Override
    public void LandFlight(IFlightplan fp) throws AssignmentException, RemoteException {
//        Runway runway = fp.getDestinationAirport().getRunway();
//        if (runway.getAvailability() == true) {
//            runway.ChangeAvailability(false);
//            fp.getAirplane().Land(runway);
//        } else {
//            throw new AssignmentException("Runway is unavailable.");
//        }
        addRunwayTimer(fp.getTakeoffAirport(), fp.getAirplane());
    }

    /**
     * Method has to be called when assignmentexception is given on the
     * GiveLandingRunway method incase the runway is not available.
     * 
     * @param a is the airplane that has to start circling the airport.
     */
    @Override
    public void CircleAirplane(IAirplane a) throws RemoteException {
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
    @Override
    public void CreateFlight(IAirplaneFactory a, IAirport start, IAirport end, GregorianCalendar arrival, GregorianCalendar departure) throws RemoteException {
        Airplane ap = new Airplane(a.getMaxSpeed(), a.getMinSpeed(), a.getWeight(), a.getType(), a.getManufacturer(),
                a.getPlaneHeight(), a.getPlaneWidth(), a.getPlaneLength(), a.getMaxFuel(), a.getFuelUsage(),
                0, 0, 300, 0, start.getLocation().getNewGeoLocation(), end.getLocation().getNewGeoLocation(), flightnumber);
        IFlightplan flightplan = new Flightplan(end, start, flightnumber, departure, arrival, ap);
        fp.add(flightplan);
        flightnumber++;
        cta.addAirplane(ap);
        publisher.publishFlightplan(flightplan);
        addRunwayTimer(start, ap);
        //assignFlightToController(flightplan);  // TODO Fix it someday
    }

    /**
     * Assign a controller to a flightplan based on the contollers busyness.
     * @param flightplan 
     */
    @Override
    public void assignFlightToController(IFlightplan flightplan) throws RemoteException {
        // Assign an controller to an airplane.
        IFC assignedController = null;
        for (IFC flightController : flightControllers) {
            if (assignedController != null) {
                if (flightController.getNumberAssignedFlights() < assignedController.getNumberAssignedFlights()) {
                    assignedController = flightController;
                }
            } else {
                assignedController = flightController;
            }
        }
        if (true) {
            assignedController.assignFlight(flightplan);
        }
        
    }

    /**
     * Creates and adds a new FlightController and add it to the list of controller as well as return it.
     * @return FlightController
     * @deprecated 
     */
    @Override
    public IFC addFlightController() throws RemoteException {
//        IFC controller = new FlightController();
//        flightControllers.add(controller);
//        return controller;
        return null;
    }

    /**
     * Creates and adds a new FlightController and add it to the list of controller.
     * @return FlightController
     */
    @Override
    public void addFlightController(IFC flightController) throws RemoteException {
        flightControllers.add(flightController);
    }

    /**
     * Unassign the flight from the controller.
     * @param flightplan 
     */
    @Override
    public void unassignFlightFromController(IFlightplan flightplan) throws RemoteException {
        flightplan.getAssignedController().unassignFlight(flightplan);
    }

    @Override
    public void removeFlightController(IFC flightController) throws RemoteException {
        flightControllers.remove(flightController);
        flightController.unassignAllFlights();
    }

    /**
     * Method to get all Flightplans
     * 
     * @return a list with all Flightplans
     */
    @Override
    public ArrayList<IFlightplan> getFlightplans() {
        return fp;
    }

    private void addRunwayTimer(final IAirport airport, final IAirplane airplane) throws RemoteException {
        final Timer timer = new Timer(300, null);
        timer.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                try {
                    IRunway runway = airport.getRunway();
                    if (runway != null && runway.getAvailability()) {
                        runway.ChangeAvailability(false);

                        if (airplane.getStatus() == Airplane.Statusses.INTAKEOFFQUEUE) {
                            airplane.TakeOff(runway, runway.getDirection(), 0, (0.7 * airplane.getMaxSpeed()));
                        } else if (airplane.getStatus() == Airplane.Statusses.INLANDINGQUEUE) {
                            airplane.Land();
                        }
                        timer.stop();
                    }
                } catch (RemoteException rex) {
                    rex.printStackTrace();
                }
            }
        });
        timer.start();
    }

    @Override
    public void setAdjacentACCList(ArrayList<IACC> adjacentACCList) {
        this.adjacentACCList = adjacentACCList;
    }

    @Override
    public ArrayList<IACC> getAdjacentACCList() {
        return adjacentACCList;
    }

    @Override
    public Boolean ContainsFlightplan(IFlightplan flightplan) {
        return fp.contains(flightplan);
    }

    @Override
    public void removeFlightPlan(IFlightplan flightplan) {
        fp.remove(flightplan);
    }

    @Override
    public void addFlightPlan(IFlightplan flightplan) {
        fp.add(flightplan);
    }

    @Override
    public RemotePublisher getPublisher() {
        return publisher;
    }
}
