package atc.logic;

import atc.interfaces.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CTA extends UnicastRemoteObject implements ICTA, Serializable {

    /**************Datafields***********/
    /**
     * The GeoSector of the CTA
     */
    public IGeoSec sector;
    public IGeoSec sectorGreater;
    /**
     * A airplane used for getting focus of a selected airplane within the CTA
     * another useless yet needed comment.
     */
    private IAirplane airplane;
    /**
     * A airport used for getting focus of a selected airport within the CTA
     */
    private IAirport airport;
    /**
     * A list used for collecting all the active airplanes within the CTA  
     */
    private ArrayList<IAirplane> airplaneList;
    /**
     * A list used for collecting all the airports within the CTA
     */
    private ArrayList<IAirport> airportList = new ArrayList<>();
    /**
     * A class used to determine the status of an airplane for possible collision danger
     */
    private ArrayList<Collision> collision;
    /**
     * logging file to write all the events that happen into a file
     */
    private Logging logging;
    
    /**
     * ID of corresponding ACC
     */
    private int AccID;
    /**
     * Timer used to calculate the collision status every xx miliseconds
     */
    
    
    ExecutorService pool;
    
    
    private Timer timer;

    private class collisionTimer extends TimerTask {

        public void run() {
            Collision temp = null;
            if (!collision.isEmpty()) {
                for (Collision coll : collision) {
                    try {
                        coll.colldetect();
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        if (coll.getTarget().getStatus().equals(Airplane.Statusses.CRASHED) || coll.getCrashobject().getStatus().equals(Airplane.Statusses.CRASHED)) {
                            temp = coll;
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(CTA.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (temp != null) {
                collision.remove(temp);
            }
        }
    }

    /***************Constructor**********/
    /**
     * This is a constructor used for making a CTA it contains a location and the width/length
     * @param Location is the location of the CTA
     * @param Width is the width of the CTA
     * @param Length is the length of the CTA
     */
    public CTA(IGeoSec location, ArrayList<IAirport> airportlist, int ACCID) throws RemoteException {
        this.sector = location;
        CreateGreaterSector();
        airplaneList = new ArrayList<>();
        collision = new ArrayList<>();
        this.airportList = airportlist;
        timer = new Timer();
        timer.schedule(new collisionTimer(), 0, 1000);
        pool = Executors.newCachedThreadPool();
        this.AccID = ACCID;

//        try {
//            loadAirportList(sector);
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
    }


    /**                                                                       TODO moet hier een unittest voor? nee toch?
     * Returns the airport with the given AirportID
     * @return
     */
    @Override
    public IAirport GetAirport(int AirportID) throws RemoteException {
        for (IAirport a : airportList) {
            if (a.getAirportID() == AirportID) {
                airport = a;
            }
        }
        return airport;
    }

    /**
     * 
     * @param AirplaneID
     * @return 
     * @deprecated 
     */
    @Override
    public IAirplane GetAirplane(int AirplaneID) throws RemoteException {                   //         TODO moet hier een unittest voor? nee toch?                              
        for (IAirplane a : airplaneList) {
            if (a.getAirplaneNumber() == AirplaneID) {
                airplane = a;
            }
        }
        return airplane;
    }

    /**
     * Checks the distance between 2 given points
     * @param lat1 is the first given latitude
     * @param lon1 is the first given longitude
     * @param lat2 is the second given latitude
     * @param lon2 is the second given longitude
     * @return a double with the calculated distance
     */
    @Override
    public double distFrom(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371;
        double distance;
        distance = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.cos(lon2 - lon1)) * earthRadius;
        distance = distance * 1000;
        return distance;
    }

    /**
     * Adds a airplane to the airplaneList
     * @return
     */
    @Override
    public void addAirplane(IAirplane a) {
        airplaneList.add(a);
        pool.submit((Airplane)a);
        for (IAirplane crashobject : airplaneList) {
            if (crashobject != (IAirplane)a) {
                collision.add(new Collision((Airplane)a, (Airplane)crashobject, AccID));
                System.out.println("added");
            }
        }
    }

    /**
     * Adds a airport to the airportList
     * @return
     */
    @Override
    public void addAirport(IAirport a) {
        airportList.add(a);
    }

    /**
     *                              TODO...is dit niet dubbel met de methode die eronder staat?? 
     * Deletes the airplane with the corresponding AirplaneNumber from the airplaneList
     * @return
     */
    @Override
    public void deleteAirplane(int AirplaneNumber) throws RemoteException {
        for (IAirplane a : airplaneList) {
            if (a.getAirplaneNumber() == AirplaneNumber) {
                airplaneList.remove(a);
                pool.submit((Airplane)a);
                return;
            }
            
        }
    }

    @Override
    public void removeAirplane(IAirplane airplane) {
        airplaneList.remove(airplane);
    }

    public void CreateGreaterSector() throws RemoteException {
        double maxLatitude = (GeoLocation.CalcPosition(sector.getMaxLongitude(), sector.getMaxLatitude(), 0, 100)).getLongitude();
        double minLatitude = (GeoLocation.CalcPosition(sector.getMinLongitude(), sector.getMinLatitude(), 180, 100)).getLongitude();
        double maxLongitude = (GeoLocation.CalcPosition(sector.getMaxLongitude(), sector.getMaxLatitude(), 90, 100)).getLatitude();
        double minLongitude = (GeoLocation.CalcPosition(sector.getMinLongitude(), sector.getMinLatitude(), -90, 100)).getLatitude();
        sectorGreater = new GeoSector(minLatitude, maxLatitude, minLongitude, maxLongitude);
    }
    
    @Override
    public void resetCollision(IAirplane airplane) throws RemoteException {
        for (Collision coll : collision) {
            if (coll.getTarget() == (Airplane)airplane || coll.getCrashobject() == (Airplane)airplane) {
                coll.getCrashobject().setStatus(Airplane.Statusses.INFLIGHT);
                coll.getTarget().setStatus(Airplane.Statusses.INFLIGHT);
            }
        }
    }

    /***************Getters**********/
    /**
     * 
     * @return 
     * @deprecated 
     */
    @Override
    public ArrayList<IAirplane> getAirplaneList() {
        return airplaneList;
    }

    /**
     * 
     * @return 
     * @deprecated 
     */
    @Override
    public ArrayList<IAirport> getAirportList() {
        return airportList;
    }

    /**
     * 
     * @return 
     * @deprecated 
     */
    @Override
    public IAirplane getAirplane() {
        return airplane;
    }

    @Override
    public IGeoSec getSector() {
        return sector;
    }

    @Override
    public IGeoSec getGreaterSector() {
        return sectorGreater;
    }
    
    /**
     * Turns the airport list into a Iterator
     * @return Iterator airportList
     */
    @Override
    public ArrayList<IAirport> GetAirports() {
        return airportList;
    }

    
    /**
     * 
     * @return 
     * @deprecated 
     */
    @Override
    public IAirport getAirport() {
        return (IAirport) airport;
    }
}