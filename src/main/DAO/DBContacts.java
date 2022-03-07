package main.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.model.Contacts;
import main.utils.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Class for functions that interact with the contact table
 * @author Clayton Dixon
 */

public class DBContacts {

    /**
     * Runs sql statement that gets all of the contact and assigns them to an observable list
     * @return clist Returns an observable list of all the contact
     */
    public static ObservableList<Contacts> getAllContacts() {

        ObservableList<Contacts> clist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from contacts";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Integer contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contacts c = new Contacts(contactID, contactName, email);
                clist.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throwables.getCause();
        }

        return clist;
    }
}
