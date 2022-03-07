package main.model;

/** Class for customers objects
 * @author Clayton Dixon
 */

public class Customers {
    private Integer customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private Integer divisionID;

    /**
     * Customers class constructor
     * @param customerID Customer ID
     * @param customerName Customer NAme
     * @param address Customer Address
     * @param postalCode Customer postal code
     * @param phone Customer phone number
     * @param divisionID Customer division ID
     */


    public Customers(Integer customerID, String customerName, String address, String postalCode, String phone, Integer divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
    }

    public Customers(String customerName, String address, String postalCode, String phone) {
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
    }


    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(Integer divisionID) {
        this.divisionID = divisionID;
    }
}
