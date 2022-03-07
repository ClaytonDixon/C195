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
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/** Controller class for the modify appointment page
 * @author Clayton Dixon
 */

public class ModifyAppointmentPage implements Initializable {
    @FXML
    private Button modifyAppointment;
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
    private Label openTimeLabel;
    @FXML
    private Label conflictingLabel;

    /**
     * Creating DateTimeFormatter variables to use in the modifyAppointment function
     */
    private DateTimeFormatter timeDTF = DateTimeFormatter.ofPattern("HH:mm:ss");
    private DateTimeFormatter dateDTF = DateTimeFormatter.ofPattern("yyyy-mm-dd");
    private DateTimeFormatter datetimeDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private ZoneId zoneID = ZoneId.of("UTC");

    /**
     * Handles the click of the button to transfer user to the main page
     * @param event Handles click event
     * @throws IOException Throws error if fails
     */
    public void backButtonEvent(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("view/appointments.fxml");
    }

    /**
     * Fills contact combo with all of the contacts
     * @param url Needed for initialize
     * @param resourceBundle Needed for initialize
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Contacts> contactList = DBContacts.getAllContacts();
//        ArrayList<String> clist = new ArrayList<>();
//        for (Contacts c : contactList) {
//            clist.add(c.getContactName());
//        }
        List<String> clist = contactList.stream().map(Contacts::getContactName).collect(Collectors.toList());
        contactCombo.getItems().addAll(clist);
    }

    /**
     * Takes all the information in the fields and runs a function that modifies the selected appointment
     * @param event Handles the click event for the add appointment button in the form
     * @throws IOException Throws an error if event fails
     */
    public void modifyAppointment(ActionEvent event) throws IOException {
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
            Integer appointmentID = Integer.parseInt(appointmentIDField.getText());

            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            String startTime = startTimeField.getText();
            LocalTime startTime2 = LocalTime.parse(startTime);
            String endTime = endTimeField.getText();
            LocalTime endTime2 = LocalTime.parse(endTime);
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime2);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime2);
            ZonedDateTime startUTC = startDateTime.atZone(zoneID).withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime endUTC = endDateTime.atZone(zoneID).withZoneSameInstant(ZoneId.of("UTC"));
            Timestamp sqlStartTS = Timestamp.valueOf(startUTC.toLocalDateTime());
            Timestamp sqlEndTS = Timestamp.valueOf(endUTC.toLocalDateTime());


//            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EST"));
//            Timestamp ts = new Timestamp(1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sqlStartTS);
            calendar.setTimeZone(TimeZone.getDefault());

            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(sqlEndTS);
            calendar2.setTimeZone(TimeZone.getDefault());

            //7,1

            Integer contactID = contactCombo.getSelectionModel().getSelectedIndex() + 1;
            System.out.println(contactID);
            ObservableList<Appointments> appointmentList = DBAppointments.getAllAppointments();

            if (!appointmentList.isEmpty()) {
                for (Appointments a : appointmentList) {
                    if(a.getAppointmentID().equals(appointmentIDField.getText())) {
                        continue;
                    }
                    if (sqlStartTS.before(a.getStartDateTime()) && sqlEndTS.before(a.getStartDateTime()) || sqlStartTS.after(a.getEndDateTime()) && sqlEndTS.after(a.getEndDateTime())) {
                            if (sqlStartTS.toLocalDateTime().toLocalTime().isAfter(LocalTime.parse("07:59")) && sqlEndTS.toLocalDateTime().toLocalTime().isBefore(LocalTime.parse("22:01"))) {
                                if (calendar.get(Calendar.DAY_OF_WEEK) != 7 && calendar.get(Calendar.DAY_OF_WEEK) != 1) {
                                    if(calendar2.get(Calendar.DAY_OF_WEEK) != 7 && calendar.get(Calendar.DAY_OF_WEEK) != 1) {
                                        if(sqlStartTS.before(sqlEndTS)) {
                                            DBAppointments.modifyAppointments(title, description, location, type, sqlStartTS, sqlEndTS, customerID, userID, contactID, appointmentID);
                                            Main m = new Main();
                                            m.changeScene("view/appointments.fxml");
                                            break;
                                        } else conflictingLabel.setText("Appointment start or end time interferes with another appointment.");
                                    } else openTimeLabel.setText("The office is not open Saturday or Sunday");
                                } else openTimeLabel.setText("The office is not open Saturday or Sunday");
                            } else openTimeLabel.setText("The office is only open from 8am to 10pm");
                    } else
                        conflictingLabel.setText("Appointment start or end time interferes with another appointment.");
                }
            }
        }
    }

    /**
     * Loads data from the appointment page and passes the selected appointment for modification and autofills the fields with the information
     * @param appointments Takes appointment from the modifyAppointment function on the appointment page
     * @return Returns appointment and passes to the modifyAppointment function on the appointment page
     */
    public Appointments loadData(Appointments appointments) {
        Appointments updateAppointment = appointments;
        appointmentIDField.setText(updateAppointment.getAppointmentID().toString());
        customerIDField.setText(updateAppointment.getCustomerID().toString());
        appointmentIDField.setText(updateAppointment.getAppointmentID().toString());
        titleField.setText(updateAppointment.getTitle());
        descriptionField.setText(updateAppointment.getDescription());
        locationField.setText((updateAppointment.getLocation()));
        contactCombo.getSelectionModel().select(updateAppointment.getContactID() - 1);
        typeField.setText(updateAppointment.getType());
        userIDField.setText(updateAppointment.getUserID().toString());


        ObservableList<Appointments> appointmentList = DBAppointments.getAllAppointments();
        ArrayList<Integer> ilist = new ArrayList<>();

        for (Appointments a : appointmentList) {
            int i = 0;
            if (appointmentIDField.getText().equals(a.getAppointmentID().toString())) {
                break;
            } else ilist.add(i);
        }


            Appointments dateTimeAppointment = DBAppointments.getAllAppointments().get(ilist.size());
//          Appointments dateTimeAppointment = DBAppointments.getAllAppointments().get(updateAppointment.getAppointmentID()-1);

//            Calendar calendar1 = Calendar.getInstance();
//            Calendar calendar2 = Calendar.getInstance();
//
//
            Timestamp startTimeStamp = dateTimeAppointment.getStartDateTime();
            Timestamp endTimeStamp = dateTimeAppointment.getEndDateTime();
//            calendar1.setTime(startTimeStamp);
//            calendar2.setTime(endTimeStamp);

//            if(java.util.TimeZone.getDefault().getDisplayName().equals("Central European Standard Time")) {
//                int duration = 3600000;
//                startTimeStamp.setTime(startTimeStamp.getTime() + duration);
//                endTimeStamp.setTime(endTimeStamp.getTime() + duration);
//            } else if (java.util.TimeZone.getDefault().getDisplayName().equals("Eastern Standard Time")) {
//                int duration = 5 * 3600000;
//                startTimeStamp.setTime(startTimeStamp.getTime() - duration);
//                endTimeStamp.setTime(endTimeStamp.getTime() - duration);
//            }


            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-mm-dd");

            String startTimeStr = sdfTime.format(startTimeStamp);
//            String startDateStr = sdfDate.format(startTimeStamp);
            String endTimeStr = sdfTime.format(endTimeStamp);
//            String endDateStr = sdfDate.format(endTimeStamp);

//            System.out.println(dateTimeAppointment.getAppointmentID());
//            System.out.println(startTimeStamp);
//            System.out.println(startTimeStr);
//            System.out.println(endTimeStr);

            startTimeField.setText(startTimeStr);
            startDatePicker.setValue(startTimeStamp.toLocalDateTime().toLocalDate());
            endTimeField.setText(endTimeStr);
            endDatePicker.setValue(endTimeStamp.toLocalDateTime().toLocalDate());

            return updateAppointment;
        }

    }


//
//    public Appointments loadData(Appointments appointments) {
//        Appointments updateAppointment = appointments;
//        customerIDField.setText(updateAppointment.getCustomerID().toString());
//        appointmentIDField.setText(updateAppointment.getAppointmentID().toString());
//        titleField.setText(updateAppointment.getTitle());
//        descriptionField.setText(updateAppointment.getDescription());
//        locationField.setText((updateAppointment.getLocation()));
//        contactCombo.getSelectionModel().select(updateAppointment.getContactID()-1);
//        typeField.setText(updateAppointment.getType());
//        userIDField.setText(updateAppointment.getUserID().toString());
//
//        ObservableList<Appointments> appointmentList = DBAppointments.getAllAppointments();
//        if(true) {
//            for(Appointments a : appointmentList) {
//                int i = 0;
//                if(appointmentIDField.getText().equals(a.getAppointmentID())) {
//                    Appointments dateTimeAppointment = DBAppointments.getAllAppointments().get(i);
//                    Timestamp startTimeStamp = dateTimeAppointment.getStartDateTime();
//                    Timestamp endTimeStamp = dateTimeAppointment.getEndDateTime();
//                    SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");
//                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-mm-dd");
//                    String startTimeStr = sdfTime.format(startTimeStamp);
//                    String startDateStr = sdfDate.format(startTimeStamp);
//                    String endTimeStr = sdfTime.format(endTimeStamp);
//                    String endDateStr = sdfDate.format(endTimeStamp);
//                    sdfTime.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
//                    System.out.println(dateTimeAppointment.getAppointmentID());
//                    System.out.println(startTimeStamp);
//                    System.out.println(startTimeStr);
//                    System.out.println(endTimeStr);
//
//                    startTimeField.setText(startTimeStr);
//                    startDatePicker.setValue(startTimeStamp.toLocalDateTime().toLocalDate());
//                    endTimeField.setText(endTimeStr);
//                    endDatePicker.setValue(endTimeStamp.toLocalDateTime().toLocalDate());
//
//                    return updateAppointment;
//                } else i++;
//            }
//        }
//
//
//
//
//
//
//        return updateAppointment;    }