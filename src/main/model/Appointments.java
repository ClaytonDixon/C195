package main.model;

import java.sql.Timestamp;

/** Class for appointments objects
 * @author Clayton Dixon
 */

public class Appointments {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private Integer customerID;
    private Integer userID;
    private Integer contactID;

    /**
     * Constructor for appointments class
     * @param appointmentID Appointment ID
     * @param title Title of appointment
     * @param description Description of appointment
     * @param location Location of appointment
     * @param type Type of appointment
     * @param startDateTime State date and time of appointment
     * @param endDateTime End date and time of appointment
     * @param customerID ID of customer
     * @param userID User ID of customer
     * @param contactID ID of contact who is running appointment
     */
    public Appointments (Integer appointmentID, String title, String description, String location, String type, Timestamp startDateTime,
                         Timestamp endDateTime, Integer customerID, Integer userID, Integer contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Getter for appointment ID
     * @return appointmentID Gets appointment ID
     */
    public Integer getAppointmentID() {
        return appointmentID;
    }

    /**
     * Setter for appointment ID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Getter for title
     * @return title Gets title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for description
     * @return description Gets description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for location
     * @return location Gets location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter for type
     * @return type Gets type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for start date and time
     * @return startDateTime Gets start date and time
     */
    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    /**
     * Setter for start date and time
     */
    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Getter for end date and time
     * @return endDateTime Gets end date and time
     */
    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    /**
     * Setter for end date and time
     */
    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Getter for customer ID
     * @return appointmentID Gets customer ID
     */
    public Integer getCustomerID() {
        return customerID;
    }

    /**
     * Setter for customer ID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter for user ID
     * @return userID Gets user ID
     */
    public Integer getUserID() {
        return userID;
    }

    /**
     * Setter for user ID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Getter for contact ID
     * @return contactID Gets contact ID
     */
    public Integer getContactID() {
        return contactID;
    }

    /**
     * Setter for contact ID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}
