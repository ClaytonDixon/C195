package main.model;

/** Class for countries objects
 * @author Clayton Dixon
 */

public class Countries {
    private int id;
    private String name;

    /**
     * Constructor for countries class
     * @param id Country ID
     * @param name Country name
     */
    public Countries(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Getter for country ID
     * @return id Returns country ID
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for country name
     * @return name Returns country name
     */
    public String getName() {
        return name;
    }
}
