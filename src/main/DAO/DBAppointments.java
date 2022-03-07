package main.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.model.Appointments;
import main.utils.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

/** Class for functions that interact with the appointments table
 * @author Clayton Dixon
 */

public class DBAppointments {

    /**
     * Runs sql statement that gets all of the appointments and assigns them to an observable list
     * @return alist Returns an observable list of all the appointments
     */
    public static ObservableList<Appointments> getAllAppointments() {

        ObservableList<Appointments> alist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startDateTime = rs.getTimestamp("Start");
                Timestamp endDateTime = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointments a = new Appointments(appointmentID, title, description, location, type, startDateTime, endDateTime, customerID, userID, contactID);
                alist.add(a);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return alist;
    }

    /**
     * Takes parameters and creates a sql insert statement and then runs the statement to add a new appointment to the appointment table
     * @param title Appointment title
     * @param description Appointment description
     * @param location Appointment location
     * @param type  Appointment type
     * @param start Appointment start date
     * @param end Appointment end date
     * @param customer_ID Appointment customerID
     * @param user_ID Appointment userID
     * @param contact_ID Appointment contactID
     */
    public static void addAppointments(String title, String description, String location, String type, Timestamp start, Timestamp end,
                                                               Integer customer_ID, Integer user_ID, Integer contact_ID) {

        ObservableList<Appointments> alist = FXCollections.observableArrayList();

        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            TimeZone timeZone = TimeZone.getTimeZone("UTC");
            Calendar calendar = Calendar.getInstance(timeZone);
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

                ps.setString(1, title);
                ps.setString(2, description);
                ps.setString(3, location);
                ps.setString(4, type);
                ps.setTimestamp(5, start, calendar);
                ps.setTimestamp(6, end, calendar);
                ps.setInt(7, customer_ID);
                ps.setInt(8, user_ID);
                ps.setInt(9, contact_ID);
                ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throwables.getCause();
        }

    }

    /**
     * Takes parameters and creates a sql update statement and then runs the statement to modify an appointment in the appointment table
     * @param title Appointment title
     * @param description Appointment description
     * @param location Appointment location
     * @param type  Appointment type
     * @param start Appointment start date
     * @param end Appointment end date
     * @param customer_ID Appointment customerID
     * @param user_ID Appointment userID
     * @param contact_ID Appointment contactID
     */
    public static void modifyAppointments(String title, String description, String location, String type, Timestamp start, Timestamp end,
                                       Integer customer_ID, Integer user_ID, Integer contact_ID, Integer appointment_ID) {

        ObservableList<Appointments> alist = FXCollections.observableArrayList();

        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type =?, Start =?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ? ";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setInt(7, customer_ID);
            ps.setInt(8, user_ID);
            ps.setInt(9, contact_ID);
            ps.setInt(10, appointment_ID);
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throwables.getCause();
        }

    }

    /**
     * Runs a sql DELETE statement and takes the given appointment ID and deletes the appointment with that ID
     * @param appointmentID Appointment ID of the appointment you want deleted
     */
    public static void deleteAppointment(Integer appointmentID) {

        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentID);

            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throwables.getCause();
        }

    }
}
