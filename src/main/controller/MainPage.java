package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.DAO.DBAppointments;
import main.Main;
import main.model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.stream.Collectors;

/** Controller class for the main page
 * @author Clayton Dixon
 */

public class MainPage implements Initializable {


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
     *
     * @param event Handles click event
     * @throws IOException Throws error if fails
     */
    public void mainCustomerButton(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("view/customers.fxml");
    }

    /**
     * Handles the click of the button to transfer user to the appointment page
     *
     * @param event Handles click event
     * @throws IOException Throws error if fails
     */
    public void mainAppointmentButton(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("view/appointments.fxml");
    }

    /**
     * Handles the click of the button to transfer user to the report page
     *
     * @param event Handles click event
     * @throws IOException Throws error if fails
     */
    public void mainReportButton(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("view/reports.fxml");
    }

    /**
     * Handles the click of the button to transfer user to the login page
     *
     * @param event Handles click event
     * @throws IOException Throws error if fails
     */
    public void mainLogoutButton(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("view/login.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointments> appointmentList = DBAppointments.getAllAppointments();
//        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
//        System.out.println(TimeZone.getDefault());
        ZonedDateTime zdt = ZonedDateTime.now();
        System.out.println(zdt);
        Timestamp ts = Timestamp.valueOf(zdt.toLocalDateTime());
        System.out.println(ts);
        ObservableList<Appointments> user1List = FXCollections.observableList(appointmentList.stream().filter(a -> a.getUserID() == 1).collect(Collectors.toList()));
        List<Timestamp> timestamps = appointmentList.stream().filter(a -> a.getUserID() == 1).map(a -> a.getStartDateTime()).collect(Collectors.toList());

        System.out.println(timestamps);

        int i = 0;
        if (!user1List.isEmpty()) {
            for (Timestamp t : timestamps) {
                long diff = Math.abs(ChronoUnit.MINUTES.between(ts.toLocalDateTime(), t.toLocalDateTime()));
                System.out.println("ts is " + ts.toLocalDateTime());
                System.out.println("t is " + t.toLocalDateTime());
                System.out.println("diff is " + diff);
                int listSize = timestamps.size();

                if (i <= listSize) {
                    i++;
                    if (diff <= 15) {
                        Stage window = new Stage();
                        window.initModality(Modality.APPLICATION_MODAL);
                        window.setMinWidth(250);
                        String str = "Appointment with ID of " + user1List.get(timestamps.indexOf(t)).getAppointmentID() + " is starting or has started within 15 minutes or less with start date and time of " + user1List.get(timestamps.indexOf(t)).getStartDateTime();
                        Label label = new Label();
                        label.setText(str);
                        Button closeButton = new Button("Close the window");
                        closeButton.setOnAction(e -> window.close());
                        VBox layout = new VBox(10);
                        layout.getChildren().addAll(label, closeButton);
                        layout.setAlignment(Pos.CENTER);
                        Scene scene = new Scene(layout);
                        window.setScene(scene);
                        window.showAndWait();
                        break;
                    }
                    System.out.println(i);
                    System.out.println(listSize);
                    } if (listSize == i) {
                        Stage window = new Stage();
                        window.initModality(Modality.APPLICATION_MODAL);
                        window.setMinWidth(250);
                        Label label = new Label();
                        label.setText("There are no appointments within 15 minutes");
                        Button closeButton = new Button("Close the window");
                        closeButton.setOnAction(e -> window.close());
                        VBox layout = new VBox(10);
                        layout.getChildren().addAll(label, closeButton);
                        layout.setAlignment(Pos.CENTER);
                        Scene scene = new Scene(layout);
                        window.setScene(scene);
                        window.showAndWait();
                    }


                }

            }
        }
    }

