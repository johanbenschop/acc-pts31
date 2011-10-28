package atc.logic;

public class Airport {

    private int AirportID;
    private String City;
    private String Country;
    private int IATA_FAA;
    private int ICAO;
    private String Timezone;
    private boolean DST;

    public Airport(int AirportID, String City, String Country, int ITATA_FAA, int ICAO, String Timezone, boolean DST) {
        this.AirportID = AirportID;
        this.City = City;
        this.Country = Country;
        this.IATA_FAA = ITATA_FAA;
        this.ICAO = ICAO;
        this.Timezone = Timezone;
        this.DST = DST;
    }

    /**
     * 
     * @return 
     */
    public void getRunway() {
        throw new UnsupportedOperationException();
    }

    public int getAirportID() {
        return AirportID;
    }

    public String getCity() {
        return City;
    }

    public String getCountry() {
        return Country;
    }

    public int getIATA_FAA() {
        return IATA_FAA;
    }

    public int getICAO() {
        return ICAO;
    }

    public String getTimezone() {
        return Timezone;
    }

    public boolean getDST() {
        return DST;
    }
}