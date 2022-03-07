package main.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.model.Customers;
import main.utils.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Class for functions that interact with the customers table
 * @author Clayton Dixon
 */

public class DBCustomers {

    /**
     * Runs sql statement that gets all of the customers and assigns them to an observable list
     * @return clist Returns an observable list of all the customers
     */
    public static ObservableList<Customers> getAllCustomers() {

        ObservableList<Customers> clist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from customers";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Integer customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                Integer divisionID = rs.getInt("Division_ID");
                Customers c = new Customers(customerID, customerName, address, postalCode, phone, divisionID);
                clist.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throwables.getCause();
        }

        return clist;
    }

    /**
     * Takes parameters and creates a sql insert statement and then runs the statement to add a new customer to the customer table
     * @param customerName Customer's name
     * @param address Customer's address
     * @param postalCode Customer's postal code
     * @param phone Customer's phone number
     * @param divisionID Customer's division ID
     */
    public static void addCustomer(String customerName, String address, String postalCode, String phone, Integer divisionID) {

        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (? , ?, ?, ?, ?)";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionID);

            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throwables.getCause();
        }

    }

    /**
     * Runs a sql DELETE statement and takes the given customer ID and deletes the customer with that ID
     * @param customerID Customer ID of the customer you want deleted
     */
    public static void deleteCustomer(Integer customerID) {

        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);

            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throwables.getCause();
        }

    }

    /**
     * Takes parameters and creates a sql UPDATE statement and then runs the statement to modify a customer in the customer table
     * @param customerName Customer's name
     * @param address Customer's address
     * @param postalCode Customer's postal code
     * @param phone Customer's phone number
     * @param divisionID Customer's division ID
     */
    public static void modifyCustomer(String customerName, String address, String postalCode, String phone, Integer divisionID, int customerID) {

        try {
            String sql = "UPDATE customers SET Customer_name = ? , Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionID);
            ps.setInt(6, customerID);

            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throwables.getCause();
        }

    }



}
