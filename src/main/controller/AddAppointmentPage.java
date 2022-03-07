package main.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.DAO.DBAppointments;
import main.DAO.DBContacts;
import main.Main;
import main.model.Appointments;
import main.model.Contacts;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller class for the add appointment page
 * @author Clayton Dixon
 *
 */

public class AddAppointmentPage implements Initializable {
    @FXML
    private Button addAppointment;
    @FXML
    private Button backButton;
    @FXML
    private TextField startTimeField;
    @FXML
    private TextField endTimeField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField appointmentIDField;
    @FXML
    private TextField customerIDField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField userIDField;
    @FXML
    private ComboBox contactCombo;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Label errorLabel;
    @FXML
    private Label conflictingLabel;
    @FXML
    private Label openTimeLabel;

    /**
     * Creating DateTimeFormatter variables to use in the addAppointment function
     */


    private DateTimeFormatter timeDTF = DateTimeFormatter.ofPattern("HH:mm:ss");
    private DateTimeFormatter dateDTF = DateTimeFormatter.ofPattern("yyyy-mm-dd");
    private DateTimeFormatter datetimeDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private ZoneId zoneID = ZoneId.of("UTC");

    /**
     *Handles the back button click event
     * @param event The button click event for the back button in JavaFX
     */

    public void backButtonEvent (ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("view/appointments.fxml");
    }

    /**
     * Initializes and fills the contact combo with all of the contacts
     * Instead of using a for loop I use a lambda to make it a short one liner and to prevent making a new ArrayList just for filling the combo box
     * @param url Needed for initialize function
     * @param resourceBundle Needed for initialize function
     */


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Contacts> contactList = DBContacts.getAllContacts();
//        ArrayList<String> clist = new ArrayList<>();
//        for(Contacts c : contactList) {
//            clist.add(c.getContactName());
//        }
        List<String> clist = contactList.stream().map(Contacts::getContactName).collect(Collectors.toList());

        contactCombo.getItems().addAll(clist);
//        System.out.println(clist);
    }

    /**
     * Takes all the information in the fields and runs a function that adds a new appointment to the appointment table
     * @param event Handles the click event for the add appointment button in the form
     * @throws IOException Throws an error if event fails
     */

    public void addAppointment(ActionEvent event) throws IOException {
        if (customerIDField.getText().isEmpty() || descriptionField.getText().isEmpty() || locationField.getText().isEmpty()
                || typeField.getText().isEmpty() || userIDField.getText().isEmpty() || contactCombo.getSelectionModel().isEmpty() || startDatePicker.getValue().equals("")
         || endDatePicker.getValue().equals("")){
            errorLabel.setText("One or more fields are empty");
        } else if (!customerIDField.getText().isEmpty()) {
            Integer customerID = Integer.parseInt(customerIDField.getText());
            String title = titleField.getText();
            String description = descriptionField.getText();
            String location = locationField.getText();
            String type = typeField.getText();
            Integer userID = Integer.parseInt(userIDField.getText());

            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            String startTime = startTimeField.getText();
            System.out.println(startTime);
            LocalTime startTime2 = LocalTime.parse(startTime);
            String endTime = endTimeField.getText();
            LocalTime endTime2 = LocalTime.parse(endTime);
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime2);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime2);
            ZonedDateTime startUTC = startDateTime.atZone(zoneID).withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime endUTC = endDateTime.atZone(zoneID).withZoneSameInstant(ZoneId.of("UTC"));

            Timestamp sqlStartTS = Timestamp.valueOf(startUTC.toLocalDateTime());
            Timestamp sqlEndTS = Timestamp.valueOf(endUTC.toLocalDateTime());


            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sqlStartTS);
            calendar.setTimeZone(TimeZone.getDefault());

            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(sqlEndTS);
            calendar2.setTimeZone(TimeZone.getDefault());

            Integer contactID = contactCombo.getSelectionModel().getSelectedIndex() + 1;
            System.out.println(contactID);
            ObservableList<Appointments> appointmentList = DBAppointments.getAllAppointments();

//            Instant instant1 = Instant.parse("07:59");
//            Instant instant2 = Instant.parse("22:01");
//            LocalTime localTime = LocalTime.parse("07:59");
//            ZonedDateTime openTime = localTime.at
//            ZonedDateTime openTime = instant1.atZone(ZoneId.of("America/New_York"));
//            ZonedDateTime closeTime = instant2.atZone(ZoneId.of("America/New_York"));
            LocalTime openTime = LocalTime.parse("07:59");
            openTime.format(DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.of("America/New_York")));
            LocalTime closeTime = LocalTime.parse("22:01");
            closeTime.format(DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.of("America/New_York")));
            System.out.println();


            if (appointmentList.isEmpty()) {
                if (sqlStartTS.toLocalDateTime().toLocalTime().isAfter(openTime) && sqlEndTS.toLocalDateTime().toLocalTime().isBefore(closeTime)) {
                    if (calendar.get(Calendar.DAY_OF_WEEK) != 7 && calendar.get(Calendar.DAY_OF_WEEK) != 1) {
                        if (calendar2.get(Calendar.DAY_OF_WEEK) != 7 && calendar2.get(Calendar.DAY_OF_WEEK) != 1) {
                            if(sqlStartTS.before(sqlEndTS)) {
                                DBAppointments.addAppointments(title, description, location, type, sqlStartTS, sqlEndTS, customerID, userID, contactID);
                                Main m = new Main();
                                m.changeScene("view/appointments.fxml");
                            } else conflictingLabel.setText("Please choose a start time that is before the end time");
                        } else openTimeLabel.setText("The office is not open Saturday or Sunday");
                    } else openTimeLabel.setText("The office is not open Saturday or Sunday");
                } else openTimeLabel.setText("The office is only open from 8am to 10pm");
            } else if (!appointmentList.isEmpty()) {
                for (Appointments a : appointmentList) {
                    if (sqlStartTS.toLocalDateTime().toLocalTime().isAfter(openTime) && sqlEndTS.toLocalDateTime().toLocalTime().isBefore(closeTime)) {
                        if (calendar.get(Calendar.DAY_OF_WEEK) != 7 && calendar.get(Calendar.DAY_OF_WEEK) != 1) {
                            if (calendar2.get(Calendar.DAY_OF_WEEK) != 7 && calendar2.get(Calendar.DAY_OF_WEEK) != 1) {
                                if(sqlStartTS.before(sqlEndTS)) {
                                    if(sqlStartTS.before(a.getStartDateTime()) && sqlEndTS.before(a.getStartDateTime()) || sqlStartTS.after(a.getEndDateTime()) && sqlEndTS.after(a.getEndDateTime())) {
                                        DBAppointments.addAppointments(title, description, location, type, sqlStartTS, sqlEndTS, customerID, userID, contactID);
                                        Main m = new Main();
                                        m.changeScene("view/appointments.fxml");
                                        break;
                                    } else conflictingLabel.setText("Appointment start or end time interferes with another appointment");
                                } else conflictingLabel.setText("Please choose a start time that is before the end time");
                            } else openTimeLabel.setText("The office is not open Saturday or Sunday");
                        } else openTimeLabel.setText("The office is not open Saturday or Sunday");
                    } else openTimeLabel.setText("The office is only open from 8am to 10pm.");
                }



            }
}
    }
}
//
//if(sqlStartTS.before(a.getStartDateTime()) || sqlStartTS.after(a.getEndDateTime())) {
//        if(sqlEndTS.before(a.getStartDateTime()) || sqlEndTS.after(a.getEndDateTime())) {
//        if(sqlStartTS.after(a.getStartDateTime()) || sqlEndTS.before(a.getStartDateTime())) {
//        if(sqlStartTS.before(a.getStartDateTime()) || sqlEndTS.before(a.getEndDateTime())) {
//        if(sqlStartTS.after(a.getEndDateTime()) || sqlEndTS.before(a.getStartDateTime())) {
//        DBAppointments.addAppointments(title, description, location, type, sqlStartTS, sqlEndTS, customerID, userID, contactID);
//        Main m = new Main();
//        m.changeScene("view/appointments.fxml");
//        } else conflictingLabel.setText("5Appointment start or end time interferes with another appointment.");
//        } else conflictingLabel.setText("4Appointment start or end time interferes with another appointment.");
//        } else conflictingLabel.setText("3Appointment start or end time interferes with another appointment.");
//        } else conflictingLabel.setText("1Appointment start or end time interferes with another appointment.");
//        }else conflictingLabel.setText("2Appointment start or end time interferes with another appointment.");
