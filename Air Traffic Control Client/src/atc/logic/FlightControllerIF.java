package atc.logic;

import atc.interfaces.*;
import java.io.Serializable;
import java.rmi.Remote;
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
public class FlightControllerIF implements IFC, Serializable {

    private int ID;
    private IAirspace iAirspace;
    private IACC acc;
    private IFC flightController;
    private int ACCID;

    private ArrayList<IFlightplan> flights;

    
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
        public void newAirplaneLocation(ArrayList<IFlightplan> fp) throws RemoteException {
            flights = fp;
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
     * so i can commit ffs
     */
    public FlightControllerIF() throws RemoteException {
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
        ID = iAirspace.makeNewFlightController();
        ACCID = 0;
        }

    public IAirspace getAirspace() {
        return iAirspace;
    }

//    public Iterator<IFlightplan> getFlights() throws RemoteException {
//        return flightController.getFlights().iterator();
//    }
    
    public IFC GetFlightController()
    {
        return flightController;
    }
    
    public IACC getChosenACC() {
        return acc;
    }
    
    public void setChosenACC(IACC acc) throws RemoteException {
        this.acc = acc;
        this.ACCID = acc.GetID();
    }
    
    @Override
    public void assignFlight(IFlightplan flightplan) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<IFlightplan> getFlights() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getID() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getNumberAssignedFlights() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void unassignAllFlights() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void unassignFlight(IFlightplan flightplan) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**
     * Arraylist of flightplans assigned to the flightcontroller
     */

}
