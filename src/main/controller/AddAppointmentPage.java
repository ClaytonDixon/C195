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
     * Handles the back button click event
     *
     * @param event The button click event for the back button in JavaFX
     */

    public void backButtonEvent(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("view/appointments.fxml");
    }

    /**
     * Initializes and fills the contact combo with all of the contacts
     *
     * @param url            Needed for initialize function
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
     *
     * @param event Handles the click event for the add appointment button in the form
     * @throws IOException Throws an error if event fails
     */

    public void addAppointment(ActionEvent event) throws IOException {
        if (customerIDField.getText().isEmpty() || descriptionField.getText().isEmpty() || locationField.getText().isEmpty()
                || typeField.getText().isEmpty() || userIDField.getText().isEmpty() || contactCombo.getSelectionModel().isEmpty() || startDatePicker.getValue().equals("")
                || endDatePicker.getValue().equals("")) {
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
            calendar.setTimeZone(TimeZone.getDefault());
            calendar.setTime(sqlStartTS);


            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeZone(TimeZone.getDefault());
            calendar2.setTime(sqlEndTS);


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

            if (appointmentList.isEmpty()) {
                if (sqlStartTS.toLocalDateTime().toLocalTime().isBefore(openTime) || sqlEndTS.toLocalDateTime().toLocalTime().isBefore(closeTime)
                        || sqlStartTS.equals(openTime) || sqlEndTS.equals(closeTime)) {
                    openTimeLabel.setText("The office is only open from 8am to 10pm");
                    return;
                }
                if (calendar.get(Calendar.DAY_OF_WEEK) == 7 || calendar.get(Calendar.DAY_OF_WEEK) == 1) {
                    openTimeLabel.setText("The office is not open Saturday or Sunday");
                    return;
                }
                if (calendar2.get(Calendar.DAY_OF_WEEK) == 7 || calendar2.get(Calendar.DAY_OF_WEEK) == 1) {
                    openTimeLabel.setText("The office is not open Saturday or Sunday");
                    return;
                }
                if (sqlStartTS.before(sqlEndTS)) {
                    DBAppointments.addAppointments(title, description, location, type, sqlStartTS, sqlEndTS, customerID, userID, contactID);
                    Main m = new Main();
                    m.changeScene("view/appointments.fxml");
                    System.out.println("test");
                } else conflictingLabel.setText("Please choose a start time that is before the end time");

            } else {

                Integer count = 1;
                if (java.util.TimeZone.getDefault().getDisplayName().equals("Central European Standard Time")) {
                    final long duration = 21600000;
                    sqlStartTS.setTime(sqlStartTS.getTime() - duration);
                    sqlEndTS.setTime(sqlEndTS.getTime() - duration);
                    if (sqlStartTS.toLocalDateTime().toLocalTime().isBefore(openTime) || sqlEndTS.toLocalDateTime().toLocalTime().isAfter(closeTime)
                            || sqlStartTS.toLocalDateTime().toLocalTime().equals(openTime) || sqlEndTS.toLocalDateTime().toLocalTime().equals(closeTime)) {
                        openTimeLabel.setText("The office is only open from 8am to 10pm EST.");
                    }
                    sqlStartTS.setTime(sqlStartTS.getTime() + duration);
                    sqlEndTS.setTime(sqlEndTS.getTime() + duration);
                }
                    for (Appointments a : appointmentList) {
                        System.out.println("sqlStart is " + sqlStartTS.toLocalDateTime().toLocalTime() + " agetStart is " + a.getStartDateTime().toLocalDateTime().toLocalTime() + " sqlend is" + sqlEndTS.toLocalDateTime().toLocalTime() + " agetEnd is " + a.getEndDateTime().toLocalDateTime().toLocalTime());
                        System.out.println("count is " + count);
                        System.out.println("appointmentList size is " + appointmentList.size());
                        System.out.println("sqlStart is " + sqlStartTS.toLocalDateTime().toLocalTime() + " opentime is " + openTime + " sqlend is" + sqlEndTS.toLocalDateTime().toLocalTime() + " closetime is " + closeTime);

                        if (calendar.get(Calendar.DAY_OF_WEEK) == 7 || calendar.get(Calendar.DAY_OF_WEEK) == 1) {
                            openTimeLabel.setText("The office is not open Saturday or Sunday");
                            System.out.println("failed day of week check of sql start " + a.getAppointmentID());
                            continue;
                        }
                        if (calendar2.get(Calendar.DAY_OF_WEEK) == 7 || calendar2.get(Calendar.DAY_OF_WEEK) == 1) {
                            openTimeLabel.setText("The office is not open Saturday or Sunday");
                            System.out.println("failed day of week check of sql end " + a.getAppointmentID());
                            continue;
                        }
                        if (sqlStartTS.after(sqlEndTS)) {
                            conflictingLabel.setText("Please choose a start time that is before the end time");
                            System.out.printf("failed sql start is after sql end at " + a.getAppointmentID());
                            continue;
                        }
                        if (sqlStartTS.equals(a.getStartDateTime()) || sqlEndTS.equals(a.getEndDateTime())
                                || sqlStartTS.equals(a.getEndDateTime()) || sqlEndTS.equals(a.getStartDateTime())) {
                            conflictingLabel.setText("Appointment start or end time is the same as another appointment");
                            System.out.println("failed check at start or end of both is equals at " + a.getAppointmentID());
                            continue;
                        }
                        if (sqlStartTS.equals(sqlEndTS)) {
                            conflictingLabel.setText("Appointment start and end cannot be at the same time");
                            System.out.println("failed sql start == sql end at " + a.getAppointmentID());
                            continue;
                        }
                        if (sqlStartTS.after(a.getStartDateTime()) && sqlEndTS.before(a.getEndDateTime())
                            || sqlStartTS.before(a.getEndDateTime()) && sqlEndTS.after(a.getEndDateTime())
                            || sqlStartTS.before(a.getStartDateTime()) && sqlEndTS.after(a.getStartDateTime())) {
                            conflictingLabel.setText("Appointment start or end time interferes with another appointment");
                            System.out.println("failed at appointment conflict at " + a.getAppointmentID());
                            continue;
                        }
                        if (appointmentList.size() == count){
                            System.out.println("appointment added with sqlStart is " + sqlStartTS + " agetStart is " + a.getStartDateTime() + " sqlend is" + sqlEndTS + " agetEnd is " + a.getEndDateTime());
                            DBAppointments.addAppointments(title, description, location, type, sqlStartTS, sqlEndTS, customerID, userID, contactID);
                            Main m = new Main();
                            m.changeScene("view/appointments.fxml");
                            break;
                        } else {
                            count++;
                            System.out.println("count has just changed to " + count);
                        }

                    }
                }
            }
        }
    }

