package main.model;

/** Class for divisions objects
 * @author Clayton Dixon
 */

public class Divisions {
    private int divisionID;
    private String division;
    private int countryID;

    /**
     * Constructor for divisions class
     * @param divisionID Division ID
     * @param division Division Name
     * @param countryID Country ID
     */
    public Divisions(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public String getDivision() {
        return division;
    }

    public int getCountryID() {
        return countryID;
    }
}
