//package atc.logic;
//
//import atc.interfaces.*;
//import java.io.Serializable;
//import java.rmi.RemoteException;
//import java.rmi.server.UnicastRemoteObject;
//import java.util.ArrayList;
//
///**
// *
// * @author Johan Benschop & Jeanique van der Sanden
// */
//public class FlightController extends UnicastRemoteObject implements Serializable {
//
//    private int ID;
//    private IAirspace iAirspace;
//    private IACC acc;
//    /**
//     * The last ID that was given to a flightcontroller (+1 = next free ID) 
//     * Note method wont work anymore because its on the client side abd each
//     * client will start at 1 so has to be smth saved at the server. 
//     */
//    private static int lastID;
//    /**
//     * Arraylist of flightplans assigned to the flightcontroller
//     */
//    private ArrayList<IFlightplan> flights;
//
//    /***************Constructor**********/
//    /**
//     * This is a constructor used for making a Flightcontroller it assigns it
//     * an ID and creates a new Arraylist to contain flightplans
//     * 
//     */
//    public FlightController() throws RemoteException {
//        ID = lastID++;
//        flights = new ArrayList<>();
//    }
//
//    public IAirspace getAirspace() {
//        return iAirspace;
//    }
//    
//    public void setCurrentACC(IACC acc) throws RemoteException {
//        this.acc = acc;
//        this.acc.addFlightController((IFC)this);
//    }
//
//    /**
//     * Assigns a flightcontroller to an flighplan and adds add to a list
//     * @param flightplan 
//     */
//    @Override
//    public void assignFlight(IFlightplan flightplan) throws RemoteException {
//        if (flightplan.getAssignedController() == null || flightplan.getAssignedController() != this) {
//            flights.add(flightplan);
//            flightplan.setAssignedController((IFC) this);
//        }
//    }
//
//    /**
//     * Method to unassign a flightplan from this Flightcontroller
//     * 
//     */
//    @Override
//    public void unassignFlight(IFlightplan flightplan) throws RemoteException {
//        flights.remove(flightplan);
//        flightplan.setAssignedController(null);
//    }
//
//    /**
//     * Method to unassign all flightplan from this Flightcontroller
//     */
//    @Override
//    public void unassignAllFlights() throws RemoteException {
//        for (IFlightplan flightplan : flights) {
//            flightplan.setAssignedController(null);
//        }
//    }
//
//    /**
//     * Gets the amount of flights from the Flightcontroller.
//     * @return 
//     */
//    @Override
//    public int getNumberAssignedFlights() {
//        return flights.size();
//    }
//
//    @Override
//    public int getID() {
//        return ID;
//    }
//
//    @Override
//    public ArrayList<IFlightplan> getFlights() {
//        return flights;
//    }
//}
