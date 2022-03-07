package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.Main;

import java.io.IOException;

/** Controller class for the main page
 * @author Clayton Dixon
 */

public class MainPage {




    @FXML
    private Button customerButton;
    @FXML
    private Button appointmentButton;
    @FXML
    private Button reportButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Label customerLabel;
    @FXML
    private Label appointmentLabel;
    @FXML
    private Label reportLabel;
    @FXML
    private Label backLabel;

    /**
     * Handles the click of the button to transfer user to the customer page
     * @param event Handles click event
     * @throws IOException Throws error if fails
     */
    public void mainCustomerButton(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("view/customers.fxml");
        }

    /**
     * Handles the click of the button to transfer user to the appointment page
     * @param event Handles click event
     * @throws IOException Throws error if fails
     */
    public void mainAppointmentButton(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("view/appointments.fxml");
        }

    /**
     * Handles the click of the button to transfer user to the report page
     * @param event Handles click event
     * @throws IOException Throws error if fails
     */
    public void mainReportButton(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("view/reports.fxml");
        }

    /**
     * Handles the click of the button to transfer user to the login page
     * @param event Handles click event
     * @throws IOException Throws error if fails
     */
    public void mainLogoutButton(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("view/login.fxml");
    }



    }

