package main.model;

/** Class for contact objects
 * @author Clayton Dixon
 */

public class Contacts {
    private int contactID;
    private String contactName;
    private String email;

    /**
     * Constructor for contact class
     * @param contactID Contact ID
     * @param contactName Contact Name
     * @param email Contact email
     */
    public Contacts(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Getter for contactID
     * @return contactID returns contactID
     */
    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Getter for contactName
     * @return contactName returns contact name
     */
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Getter for email
     * @return email returns email address for contact
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
