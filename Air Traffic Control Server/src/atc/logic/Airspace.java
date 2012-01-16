package atc.logic;

import atc.interfaces.*;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 *
 * @author Robbert
 */
public class Airspace extends UnicastRemoteObject implements IAirspace, Serializable {

    /**************Datafields***********/
    /**
     * A list used for collecting all the airports within the CTA
     */
    private ArrayList<IAirport> airportList;
    /**
     * A list used for collecting all the ACCs
     */
    private ArrayList<IACC> ACCs;
    /**
     * An ID used for identifying the ACCs
     */
    private int ID = 0;
    /**
     * A starters value for the ACC ID numbering
     */
    private int IDStart = 1000;
    /**
     * Contains the selection of the ACC for the instance of this program.
     */
    private IACC currentACC;
    private boolean onlyOneACC;
    private ArrayList<IFC> flightcontroller;
    private int FCLastID;

    /***************Constructor**********/
    /**
     * This is a constructor used for making multiple ACCs and recording their information in a list
     * 
     */
    public Airspace() throws RemoteException {
        flightcontroller = new ArrayList<>();
        airportList = new ArrayList<>();
        ACCs = new ArrayList<>();
        FCLastID = 0;
        try {
            loadAirportList();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        IGeoSec sector;
        for (int hori = -50; hori < 70; hori += 20) {
            ID = IDStart;
            for (int verti = -180; verti < 180; verti += 20) {
                sector = new GeoSector(hori, hori + 20, verti, verti + 20);
                ICTA cta = new CTA(sector, getAirportCTA(sector));
                ACCs.add(new ACC(ID, cta));
                ID++;
            }
            IDStart += 100;
        }
        currentACC = ACCs.get(50);
        System.err.println(currentACC.GetID());
        System.err.println(currentACC.GetCTA().getSector().getMaxLatitude());
//
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(40, 60, -10, 10))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, -160, -140))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, -140, -120))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, -120, -100))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, -100, -80))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, -80, -60))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, -60, -40))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, -40, -20))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, -20, 0))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, 0, 20))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, 20, 40))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, 40, 60))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, 60, 80))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, 80, 100))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, 100, 120))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, 120, 140))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, 140, 160))));
//        ACCs.add(new ACC(ID++, new CTA(new GeoSector(e, e+20, 160, 180))));
        //}
//        
    }

    /**
     * A method to load the list with all the airports in the airports.dat file
     * 
     */
    @Override
    public void loadAirportList() throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream("airports.dat");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String strline;
        while ((strline = br.readLine()) != null) {
            try {
                String[] props = strline.split(",");
                int id = Integer.parseInt(props[0]);
                String name = props[1].replaceAll("\"", "");
                String city = props[2].replaceAll("\"", "");
                String country = props[3].replaceAll("\"", "");
                String iata_faa = props[4].replaceAll("\"", "");
                String icao = props[5].replaceAll("\"", "");
                double latitude = Double.parseDouble(props[6]);
                double longitude = Double.parseDouble(props[7]);
                int altitude = Integer.parseInt(props[8]);
                double timezone = Double.parseDouble(props[9]);
                String dst = props[10].replaceAll("\"", "");

                IGeoLoc location = new GeoLocation(longitude, latitude, altitude);

                IAirport airport = new Airport(id, name, city, country, iata_faa, icao, location, altitude, timezone, dst);
                airportList.add(airport);
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Corrupt data line on airports.dat...");
            }
        }
    }

    /**
     * A method to get the airport list within the given GeoSector
     * 
     * @return An list with airports in the given GeoSector
     */
    @Override
    public ArrayList<IAirport> getAirportCTA(IGeoSec sector) throws RemoteException {
        ArrayList<IAirport> airportlist = new ArrayList<IAirport>();
        for (IAirport airport : airportList) {
            if (sector.containsGeoLocation(airport.getLocation())) {
                airportlist.add(airport);
            }
        }
        return airportlist;
    }

    /**
     * A method to get the airport with the given AirportID
     * 
     * @return The airport with the given AirportID
     */
    @Override
    public IAirport GetAirport(int AirportID) throws NullPointerException, RemoteException {
        IAirport airport = null;
        for (IAirport a : airportList) {
            if (a.getAirportID() == AirportID) {
                airport = a;
            }
        }
        try {
            if (airport != null) {
                return airport;
            } else if (airport == null) {
                new NullPointerException("Airport doesn't exist.");
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * Gets all the adjacent ACC's from the current ACC and returns this ArrayList.
     * @param ACCID
     * @return Arraylist with adjacent ACC's
     */
    @Override
    public ArrayList getAdjacentACCs(int CurrentACCID) throws RemoteException {
        ArrayList<IACC> adjacentACCList = new ArrayList();
        for (IACC acc : ACCs) {
            if (((CurrentACCID - 101) == acc.GetID()) || ((CurrentACCID - 100) == acc.GetID())
                    || ((CurrentACCID - 99) == acc.GetID()) || ((CurrentACCID - 1) == acc.GetID())
                    || ((CurrentACCID + 1) == acc.GetID()) || ((CurrentACCID + 99) == acc.GetID())
                    || ((CurrentACCID + 100) == acc.GetID()) || ((CurrentACCID + 101) == acc.GetID())) {
                adjacentACCList.add(acc);
            }
        }
        return adjacentACCList;
    }

    @Override
    public synchronized void BorderControl() {
        try {
            for (Iterator<IACC> itla = this.GetACCs().listIterator(); itla.hasNext();) {
                IACC leavingACC = itla.next();
                for (Iterator<IFlightplan> itfp = leavingACC.getFlightplans().listIterator(); itfp.hasNext();) {
                    IFlightplan flightplan = itfp.next();
                    if (leavingACC.GetCTA().getGreaterSector() != null) {
                        if (leavingACC.GetCTA().getGreaterSector().containsGeoLocation(flightplan.getAirplane().getLocation())
                                && !leavingACC.GetCTA().getSector().containsGeoLocation(flightplan.getAirplane().getLocation())) {
                            for (Iterator<IACC> itra = this.getAdjacentACCs(leavingACC.GetID()).iterator(); itra.hasNext();) {
                                IACC receivingACC = itra.next();
                                if (receivingACC.GetCTA().getSector().containsGeoLocation(flightplan.getAirplane().getLocation())) {
                                    if (!leavingACC.getfc().isEmpty()) {// && flightplan.getAssignedController() != null) {
                                        leavingACC.unassignFlightFromController(flightplan);
                                    }
                                    leavingACC.removeFlightPlan(flightplan);
                                    if (!receivingACC.getfc().isEmpty()) {
                                        receivingACC.assignFlightToController(flightplan);
                                    }
                                    receivingACC.addFlightPlan(flightplan);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
        
    /**************Getters**************/
    @Override
    public ArrayList<IACC> GetACCs() {
        return ACCs;
    }

    @Override
    public IACC getCurrentACC() {
        return currentACC;
    }

    @Override
    public ArrayList<IAirport> GetAirports() {
        return airportList;
    }

    @Override
    public boolean getOnlyOneACC() {
        return onlyOneACC;
    }

    /**
     * A method to get the ACC with the given ID
     * 
     * @return The acc with the given ID
     */
    @Override
    public IACC getACC(int ID) throws RemoteException {
        for (IACC acc : ACCs) {
            if (acc.GetID() == ID) {
                return acc;
            }
        }
        return null;
    }

    /**************Setters**************/
    @Override
    public IACC setCurrentACC(IACC currentACC) {
        this.currentACC = currentACC;
        return currentACC;
    }

    @Override
    public IACC setCurrentACC(int ID) throws RemoteException {
        for (IACC acc : ACCs) {
            if (acc.GetID() == ID) {
                this.currentACC = acc;
                return acc;
            }
        }
        return null;
    }

    @Override
    public void setOnlyOneACC(boolean onlyOneAcc) {
        onlyOneACC = onlyOneAcc;
    }

    @Override
    public int makeNewFlightController() throws RemoteException {
        FCLastID++;
        return FCLastID;
    }
}
