package atc.logic;

import atc.gui.atc2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.io.*;
import java.util.InputMismatchException;

/**
 *
 * @author Robbert
 */
public class Airspace {

    /**************Datafields***********/
    /**
     * A list used for collecting all the airports within the CTA
     */
    private ArrayList<Airport> airportList;
    /**
     * A list used for collecting all the ACCs
     */
    private ArrayList<ACC> ACCs;
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
    private ACC currentACC;
    private boolean onlyOneACC;

    /***************Constructor**********/
    /**
     * This is a constructor used for making multiple ACCs and recording their information in a list
     * 
     */
    public Airspace() {
        airportList = new ArrayList<Airport>();
        ACCs = new ArrayList<ACC>();
        try {
            loadAirportList();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        GeoSector sector;
        for (int hori = -50; hori < 70; hori += 20) {
            ID = IDStart;
            for (int verti = -180; verti < 180; verti += 20) {
                sector = new GeoSector(hori, hori + 20, verti, verti + 20);
                CTA cta = new CTA(sector, getAirportCTA(sector));
                ACCs.add(new ACC(ID, cta));
                ID++;
            }
            IDStart += 100;
        }
        currentACC = ACCs.get(35);
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

                GeoLocation location = new GeoLocation(longitude, latitude, altitude);

                Airport airport = new Airport(id, name, city, country, iata_faa, icao, location, altitude, timezone, dst);
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
    public ArrayList<Airport> getAirportCTA(GeoSector sector) {
        ArrayList<Airport> airportlist = new ArrayList<Airport>();
        for (Airport airport : airportList) {
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
    public Airport GetAirport(int AirportID) throws NullPointerException {
        Airport airport = null;
        for (Airport a : airportList) {
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
    public ArrayList getAdjacentACCs(int CurrentACCID) {
        ArrayList<ACC> adjacentACCList = new ArrayList();
        for (ACC acc : ACCs) {
            if (((CurrentACCID - 101) == acc.GetID()) || ((CurrentACCID - 100) == acc.GetID())
                    || ((CurrentACCID - 99) == acc.GetID()) || ((CurrentACCID - 1) == acc.GetID())
                    || ((CurrentACCID + 1) == acc.GetID()) || ((CurrentACCID + 99) == acc.GetID())
                    || ((CurrentACCID + 100) == acc.GetID()) || ((CurrentACCID + 101) == acc.GetID())) {
                adjacentACCList.add(acc);
            }
        }
        return adjacentACCList;
    }

    public synchronized void BorderControl() {
        try {
            for (Iterator<ACC> itla = this.GetACCs(); itla.hasNext();) {
                ACC leavingACC = itla.next();
                for (Iterator<Flightplan> itfp = leavingACC.getFlightplans(); itfp.hasNext();) {
                    Flightplan flightplan = itfp.next();
                    if (leavingACC.GetCTA().sectorGreater != null) {
                        if (leavingACC.GetCTA().sectorGreater.containsGeoLocation(flightplan.getAirplane().getLocation())
                                && !leavingACC.GetCTA().sector.containsGeoLocation(flightplan.getAirplane().getLocation())) {
                            for (Iterator<ACC> itra = this.getAdjacentACCs(leavingACC.GetID()).iterator(); itra.hasNext();) {
                                ACC receivingACC = itra.next();
                                if (receivingACC.GetCTA().sector.containsGeoLocation(flightplan.getAirplane().getLocation())) {
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
    public ListIterator<ACC> GetACCs() {
        return ACCs.listIterator();
    }

    public ACC getCurrentACC() {
        return currentACC;
    }

    public ListIterator<Airport> GetAirports() {
        return airportList.listIterator();
    }

    public boolean getOnlyOneACC() {
        return onlyOneACC;
    }

    /**
     * A method to get the ACC with the given ID
     * 
     * @return The acc with the given ID
     */
    public ACC getACC(int ID) {
        for (ACC acc : ACCs) {
            if (acc.GetID() == ID) {
                return acc;
            }
        }
        return null;
    }

    /**************Setters**************/
    public void setCurrentACC(ACC currentACC) {
        this.currentACC = currentACC;
    }

    public void setCurrentACC(int ID) {
        for (ACC acc : ACCs) {
            if (acc.GetID() == ID) {
                this.currentACC = acc;
            }
        }
        this.currentACC = null;
    }

    public void setOnlyOneACC(boolean onlyOneAcc) {
        onlyOneACC = onlyOneAcc;
    }
}
