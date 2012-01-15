package atc.logic;

import atc.interfaces.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import javax.naming.*;
import java.util.Enumeration;

/**
 *
 * @author Johan Benschop & Jeanique van der Sanden
 */
public class FlightController implements IFC, Serializable {

    private int ID;
    private IAirspace iAirspace;
    /**
     * The last ID that was given to a flightcontroller (+1 = next free ID) 
     * Note method wont work anymore because its on the client side abd each
     * client will start at 1 so has to be smth saved at the server. 
     */
    private static int lastID;
    /**
     * Arraylist of flightplans assigned to the flightcontroller
     */
    private ArrayList<IFlightplan> flights;
    private ArrayList<IAirplane> airplane;

    public class AirplaneListener extends UnicastRemoteObject implements RemoteListener, Serializable {

        public AirplaneListener() throws RemoteException {
        }

        /**
         * Method to send out the new airplane locations to the client. Something
         * to check do we want to extract the airplanes from the flightplans
         * in that case each controller will need all flightplans or are we just
         * gonne send a list of all airplanes seperatly.
         * @param ap list of airplanes to extract there location from
         */
        @Override
        public void newAirplaneLocation(ArrayList<IAirplane> ap) throws RemoteException {
            airplane = ap;
        }
        
        /**
         * Method to send a new flightplan out
         * @param fp flightplan that has to be added to the flights under control of this flightcontroller. 
         */
        @Override
        public void newFlightplan(IFlightplan fp) throws RemoteException {
                flights.add(fp);
        }
    }

    /***************Constructor**********/
    /**
     * This is a constructor used for making a Flightcontroller it assigns it
     * an ID and creates a new Arraylist to contain flightplans
     * 
     */
    public FlightController() throws RemoteException {
        try {
            Context namingContext = new InitialContext();

            System.out.print("RMI registry bindings: ");
            Enumeration<NameClassPair> e = namingContext.list("rmi://localhost");//subject to change the IP of the server, currently IP of Henk's laptop but yeah they change.
            while (e.hasMoreElements()) {
                System.out.println(e.nextElement().getName());
            }
            String url = "rmi://localhost/ATCServer";
            System.out.println(namingContext.lookup(url).toString());
            iAirspace = (IAirspace) namingContext.lookup(url);
            System.out.println("Loaded airspace");
        } catch (NamingException ex) {
            System.out.println(ex.toString());
        }
        ID = lastID++;
        flights = new ArrayList<>();
        airplane = new ArrayList<>();
    }

    public IAirspace getAirspace() {
        return iAirspace;
    }

    /**
     * Assigns a flightcontroller to an flighplan and adds add to a list
     * @param flightplan 
     */
    @Override
    public void assignFlight(IFlightplan flightplan) throws RemoteException {
        if (flightplan.getAssignedController() == null || flightplan.getAssignedController() != this) {
            flights.add(flightplan);
            flightplan.setAssignedController((IFC) this);
        }
    }

    /**
     * Method to unassign a flightplan from this Flightcontroller
     * 
     */
    @Override
    public void unassignFlight(IFlightplan flightplan) throws RemoteException {
        flights.remove(flightplan);
        flightplan.setAssignedController(null);
    }

    /**
     * Method to unassign all flightplan from this Flightcontroller
     */
    @Override
    public void unassignAllFlights() throws RemoteException {
        for (IFlightplan flightplan : flights) {
            flightplan.setAssignedController(null);
        }
    }

    /**
     * Gets the amount of flights from the Flightcontroller.
     * @return 
     */
    @Override
    public int getNumberAssignedFlights() {
        return flights.size();
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public Iterator<IFlightplan> getFlights() {
        return flights.iterator();
    }
}
