package main.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.DAO.DBCountries;
import main.DAO.DBCustomers;
import main.DAO.DBFirstLevelDivisions;
import main.Main;
import main.model.Countries;
import main.model.Customers;
import main.model.Divisions;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/** Controller class for the modify customer page
 * @author Clayton Dixon
 */

public class ModifyCustomerPage implements Initializable {
    @FXML
    private TextField customerIDField;
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerAddressField;
    @FXML
    private TextField customerPostalField;
    @FXML
    private TextField customerPhoneField;
    @FXML
    private Button modifyCustomerButton;
    @FXML
    private Label errorLabel;
    @FXML
    private Label customerIDLabel;
    @FXML
    private ComboBox countryCombo;
    @FXML
    private ComboBox stateCombo;


    /**
     * Handles the click of the button to transfer user to the main page
     * @param event Handles click event
     * @throws IOException Throws error if fails
     */
    public void backButtonEvent (ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("view/customers.fxml");
    }

    /**
     * Takes all the information in the fields and runs a function that modifies the selected customer
     * @param event Handles the click event for the modify customer button in the form
     * @throws IOException Throws an error if event fails
     */
    public void modifyCustomer(ActionEvent event) throws IOException {
        if (customerAddressField.getText().isEmpty() || customerNameField.getText().isEmpty() || customerPostalField.getText().isEmpty()
                || customerPhoneField.getText().isEmpty() || countryCombo.getItems().isEmpty() || stateCombo.getItems().isEmpty()) {
            errorLabel.setText("One or more fields are empty");
        } else if (!customerNameField.getText().isEmpty()) {
            String customerName = customerNameField.getText();
            String country = countryCombo.getSelectionModel().getSelectedItem().toString();
            String state = stateCombo.getSelectionModel().getSelectedItem().toString();
            System.out.println(state);
            System.out.println(country);
            String address = customerAddressField.getText();
            if (country.equals("UK")) {
                address = customerAddressField.getText() + " , " + state;
            }

            String postalCode = customerPostalField.getText();
            String phone = customerPhoneField.getText();

            int customerID = Integer.parseInt(customerIDField.getText());
            int divisionID = translateDivision();

            DBCustomers.modifyCustomer(customerName, address, postalCode, phone, divisionID, customerID);
            Main m = new Main();
            m.changeScene("view/customers.fxml");
        }

    }




    public int translateDivision () {
        ObservableList<Divisions> dlist = DBFirstLevelDivisions.getAllDivisions();

        Integer divisionID = 0;
        for(Divisions d : dlist) {

            if (d.getDivision().equals(stateCombo.getSelectionModel().getSelectedItem().toString())
            ) {
                divisionID = d.getDivisionID();

            }     } return divisionID;

    }

    public int translateDivisionName (int divID) {
        ObservableList<Divisions> dlist = DBFirstLevelDivisions.getAllDivisions();

        int index = 0;
        for(Divisions d : dlist) {

            if (d.getDivisionID() == divID)
            {

                 index = dlist.indexOf(d);

            }   } return index;

    };

    /**
     * Loads data from the customer page and passes the selected customer for modification and autofills the fields with the information
     * @param customer Takes customer from the modifyCustomer function on the customer page
     * @return Returns customer and passes to the modifyCustomer function on the customer page
     */
    public Customers loadData(Customers customer) {
        Customers updateCustomer = customer;
        customerIDField.setText(updateCustomer.getCustomerID().toString());
        customerNameField.setText(updateCustomer.getCustomerName());
        customerAddressField.setText(updateCustomer.getAddress());
        customerPhoneField.setText(updateCustomer.getPhone());
        customerPostalField.setText(updateCustomer.getPostalCode());
        int divID = updateCustomer.getDivisionID();
        if(divID <= 54) {
            countryCombo.getSelectionModel().select(0);
        } else if(divID > 54 && divID<= 72) {
            countryCombo.getSelectionModel().select(2);
        } else if (divID > 72 && divID < 105) {
            countryCombo.getSelectionModel().select(1);
        }

        stateCombo.getSelectionModel().select(translateDivisionName(divID));
        return updateCustomer;
    }

    /**
     * Fills state and country combo with all of the states and countries
     * @param url Needed for initialize
     * @param resourceBundle Needed for initialize
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Countries> countriesList = DBCountries.getAllCountries();
//        ArrayList<String> clist = new ArrayList<>();
//        for(Countries c : countriesList) {
//            clist.add(c.getName());
//        }
        List<String> clist = countriesList.stream().map(Countries::getName).collect(Collectors.toList());

        countryCombo.getItems().addAll(clist);
        System.out.println(clist);

        ObservableList<Divisions> divisionsList = DBFirstLevelDivisions.getAllDivisions();
//        ArrayList<String> dlist = new ArrayList<>();
//        for(Divisions d : divisionsList) {
//            dlist.add(d.getDivision());
//        }
        List<String> dlist = divisionsList.stream().map(Divisions::getDivision).collect(Collectors.toList());
        stateCombo.getItems().addAll(dlist);
        System.out.println(dlist);
    }
}
