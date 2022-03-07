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
import main.model.Divisions;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller class for the add customer page
 * @author Clayton Dixon
 *
 */

public class AddCustomerPage implements Initializable{
    @FXML
    private Button backButton;
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerAddressField;
    @FXML
    private TextField customerPostalField;
    @FXML
    private TextField customerPhoneField;
    @FXML
    private Button addCustomerButton;
    @FXML
    private Label errorLabel;
    @FXML
    private ComboBox countryCombo;
    @FXML
    private ComboBox stateCombo;

    /**
     * Initializes and fills the countries and divisions combo with all of the available countries and divisions
     * Instead of using a for loop I use a lambda to make it a short one liner and to prevent making a new ArrayList and for loop just for filling the combo boxes
     * @param url Needed for initialize function
     * @param resourceBundle Needed for initialize function
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Countries> countriesList = DBCountries.getAllCountries();
//        ArrayList<String> clist = new ArrayList<>();
//        for(Countries c : countriesList) {
//           clist.add(c.getName());
//        }

        List<String> clist = countriesList.stream().map(Countries::getName).collect(Collectors.toList());
        countryCombo.getItems().addAll(clist);
//        System.out.println(clist);

        ObservableList<Divisions> divisionsList = DBFirstLevelDivisions.getAllDivisions();
//        ArrayList<String> dlist = new ArrayList<>();
//        for(Divisions d : divisionsList) {
//            dlist.add(d.getDivision());
//        }
        List<String> dlist = divisionsList.stream().map(Divisions::getDivision).collect(Collectors.toList());
        stateCombo.getItems().addAll(dlist);
//        System.out.println(dlist);

    }

    /**
     *Handles the back button click event
     * @param event The button click event for the back button in JavaFX
     */

    public void backButtonEvent (ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("view/customers.fxml");
    }

    public void stateComboAction() throws IOException{


        String country = countryCombo.getSelectionModel().getSelectedItem().toString();
        System.out.println(country);
        ObservableList<Divisions> divisionsList = DBFirstLevelDivisions.getAllDivisions();

        ArrayList<String> dlist = new ArrayList<>();

        if(country.equals("U.S")) {
            for(Divisions d : divisionsList) {
                if (d.getCountryID() == 1 ) {
                    dlist.add(d.getDivision());
                }
                stateCombo.getItems().clear();
                stateCombo.getItems().addAll(dlist);
            }
        } else if (country.equals("UK")) {
            for(Divisions d : divisionsList) {
                if (d.getCountryID() == 2 ) {
                    dlist.add(d.getDivision());
                }
                stateCombo.getItems().clear();
                stateCombo.getItems().addAll(dlist);
            }
        } else if (country.equals("Canada")) {
            for(Divisions d : divisionsList) {
                if (d.getCountryID() == 3 ) {
                    dlist.add(d.getDivision());
                }
                stateCombo.getItems().clear();
                stateCombo.getItems().addAll(dlist);
            }
        }

//        if(!countryCombo.getSelectionModel().getSelectedItem().toString().equals("U.S") ||
//        (!countryCombo.getSelectionModel().getSelectedItem().toString().equals("Canada") ||
//        (!countryCombo.getSelectionModel().getSelectedItem().toString().equals("UK"))))
//        {
//            errorLabel.setText("Please select a country first");
//        } else {
//            String country = countryCombo.getSelectionModel().getSelectedItem().toString();
//        }
    }

    /**
     * Takes all the information in the fields and runs a function that adds a new customer to the customer table
     * @param event Handles the click event for the add appointment button in the form
     * @throws IOException Throws an error if event fails
     */

    public void addCustomer(ActionEvent event) throws IOException {
        if (customerAddressField.getText().isEmpty() || customerNameField.getText().isEmpty() || customerPostalField.getText().isEmpty()
        || customerPhoneField.getText().isEmpty() || countryCombo.getSelectionModel().isEmpty() || stateCombo.getSelectionModel().isEmpty()) {
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


            int divisionID = translateDivision();


            DBCustomers.addCustomer(customerName, address, postalCode, phone, divisionID);
            Main m = new Main();
            m.changeScene("view/customers.fxml");
        }


    }

    /**
     * Translates the division selected into the divsion's ID number for the add customer function
     * @return divisionID The division ID for the selected division in the state combo box
     */

    public int translateDivision () {
        ObservableList<Divisions> dlist = DBFirstLevelDivisions.getAllDivisions();


        Integer divisionID = 0;
        for(Divisions d : dlist) {

            if (d.getDivision().equals(stateCombo.getSelectionModel().getSelectedItem().toString())
            ) {
                divisionID = d.getDivisionID();

            }     } return divisionID;

    }


}

