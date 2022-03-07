package main.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.sql.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Clayton Dixon
 */

public class ReportPage implements Initializable {

    @FXML
    private TextField customerField;
    @FXML
    private ComboBox typeCombo;
    @FXML
    private ComboBox monthCombo;
    @FXML
    private Button backButton;
    @FXML
    private Button appointmentButton;
    @FXML
    private Label reportErrorLabel;
    @FXML
    private TextArea scheduleText;
    @FXML
    private ComboBox scheduleCombo;
    @FXML
    private Label scheduleComboLabel;
    @FXML
    private Button scheduleButton;
    @FXML
    private Label contactErrorLabel;
    @FXML
    private ComboBox locationCombo;
    @FXML
    private Label locationErrorLabel;
    @FXML
    private TextField locationField;

    private static final Integer anikaID = 1;
    private static final Integer danielID = 2;
    private static final Integer liID = 3;


    /**
     * Handles the click of the button to transfer user to the main page
     * @param event Handles click event
     * @throws IOException Throws error if fails
     */
    public void backButtonEvent(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("view/main.fxml");
    }

    /**
     * Takes the selected contact from the combo box and lists all of the appointments of that contact in the below text area
     * @param event Click event
     * @throws IOException Throws error if fails
     */
    public void scheduleReport(ActionEvent event) throws IOException {

        if (scheduleCombo.getSelectionModel().getSelectedItem() == null) {
            contactErrorLabel.setText("Please choose a contact");
        } else if (scheduleCombo.getSelectionModel().getSelectedItem() != null) {
            scheduleText.clear();
            ObservableList<Contacts> contactList = DBContacts.getAllContacts();
//            ArrayList<String> clist = new ArrayList<>();
//            for (Contacts c: contactList) {
//                clist.add(c.getContactName());
//            }
            List<String>clist = contactList.stream().map(Contacts::getContactName).collect(Collectors.toList());
            ObservableList<Appointments> appointmentList = DBAppointments.getAllAppointments();
//            ArrayList<Integer> alist = new ArrayList<>();
//            for (Appointments a: appointmentList) {
//                alist.add(a.getAppointmentID());
//            }
            List<Integer> alist = appointmentList.stream().map(Appointments::getAppointmentID).collect(Collectors.toList());

            if (scheduleCombo.getSelectionModel().getSelectedIndex() == anikaID -1) {

                for (Appointments a: appointmentList) {
                    if(a.getContactID() == anikaID) {
                          int appointmentID = a.getAppointmentID();
                          String title = a.getTitle();
                          String description = a.getDescription();
                          String location = a.getLocation();
                          String type = a.getType();
                          Timestamp startDateTime = a.getStartDateTime();
                          Timestamp endDateTime = a.getEndDateTime();
                          Integer customerID = a.getCustomerID();
                          Integer userID = a.getUserID();
                          Integer contactID = a.getContactID();
                        String textOutput = "Appointment ID: " + appointmentID + System.lineSeparator() + "Title: " + title + System.lineSeparator() + "Description: " + description + System.lineSeparator() + "Location: " + location + System.lineSeparator() +
                                "Type: " + type + System.lineSeparator() + "Start DateTime: " + startDateTime + System.lineSeparator() + "End DateTime: " + endDateTime + System.lineSeparator() + "Customer ID: " + customerID + System.lineSeparator() +
                                "User ID: " + userID + System.lineSeparator() + "Contact ID: " + contactID + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() + System.lineSeparator();
                        scheduleText.setText(textOutput);
//
                    }
                }
            }

            if (scheduleCombo.getSelectionModel().getSelectedIndex() == danielID -1) {

                for (Appointments a: appointmentList) {
                    if(a.getContactID() == danielID) {
                        int appointmentID = a.getAppointmentID();
                        String title = a.getTitle();
                        String description = a.getDescription();
                        String location = a.getLocation();
                        String type = a.getType();
                        Timestamp startDateTime = a.getStartDateTime();
                        Timestamp endDateTime = a.getEndDateTime();
                        Integer customerID = a.getCustomerID();
                        Integer userID = a.getUserID();
                        Integer contactID = a.getContactID();
//
                        String textOutput = "Appointment ID: " + appointmentID + System.lineSeparator() + "Title: " + title + System.lineSeparator() + "Description: " + description + System.lineSeparator() + "Location: " + location + System.lineSeparator() +
                                "Type: " + type + System.lineSeparator() + "Start DateTime: " + startDateTime + System.lineSeparator() + "End DateTime: " + endDateTime + System.lineSeparator() + "Customer ID: " + customerID + System.lineSeparator() +
                                "User ID: " + userID + System.lineSeparator() + "Contact ID: " + contactID + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() + System.lineSeparator();
                        scheduleText.appendText(textOutput);
//
                    }
                }
            }
            if (scheduleCombo.getSelectionModel().getSelectedIndex() == liID -1) {

                for (Appointments a: appointmentList) {
                    if(a.getContactID() == liID) {
                        int appointmentID = a.getAppointmentID();
                        String title = a.getTitle();
                        String description = a.getDescription();
                        String location = a.getLocation();
                        String type = a.getType();
                        Timestamp startDateTime = a.getStartDateTime();
                        Timestamp endDateTime = a.getEndDateTime();
                        Integer customerID = a.getCustomerID();
                        Integer userID = a.getUserID();
                        Integer contactID = a.getContactID();
                        String textOutput = "Appointment ID: " + appointmentID + System.lineSeparator() + "Title: " + title + System.lineSeparator() + "Description: " + description + System.lineSeparator() + "Location: " + location + System.lineSeparator() +
                                "Type: " + type + System.lineSeparator() + "Start DateTime: " + startDateTime + System.lineSeparator() + "End DateTime: " + endDateTime + System.lineSeparator() + "Customer ID: " + customerID + System.lineSeparator() +
                                "User ID: " + userID + System.lineSeparator() + "Contact ID: " + contactID + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() + System.lineSeparator();
                        scheduleText.setText(textOutput);
//
                    }
                }
            }
//            System.out.println(alist);
        }
    }

    /**
     * Takes the selected location from the combo box and fills the field with the number of appointments at that location
     * @param event Click Event
     */
    public void locationAppointmentReport(ActionEvent event) {
        if(locationCombo.getSelectionModel().getSelectedItem() == null) {
            locationErrorLabel.setText("Location combo box is empty please select a location");
        } else if(locationCombo.getSelectionModel().getSelectedItem() != null) {
            locationField.clear();
            ObservableList<Appointments> appointmentList = DBAppointments.getAllAppointments();
//            ArrayList<String> locationList = new ArrayList<>();
//            for (Appointments a : appointmentList) {
//                locationList.add(a.getLocation());
//            }

            List<String> locationList = appointmentList.stream().map(Appointments::getLocation).collect(Collectors.toList());
//            System.out.println(locationList);

            int i = 0;

            ArrayList<Integer> intList = new ArrayList<>();
            ArrayList<String> stringList = new ArrayList<>();

            while(i < locationList.size()) {
                int occur = Collections.frequency(locationList, locationList.get(i));
                intList.add(occur);
                stringList.add(locationList.get(i));
//                System.out.println(occur + " " + locationList.get(i));
                i++;
            }
//            System.out.println("intList after while loop " + intList);
//            System.out.println("stringList after while loop " + stringList);



            int j = 0;
            String output = "";

            while (j < intList.size()) {
                if(locationCombo.getSelectionModel().getSelectedItem().toString().equals(stringList.get(j))) {

                    String s = String.valueOf(intList.get(j));
                    locationField.setText(s);
                }
                ++j;
            }

//            for(String s: locationList) {
//                int occur = Collections.frequency(locationList, s);
//                System.out.println(occur);
//            }

//            int occur = Collections.frequency(locationList, "test");


        }
    }

    /**
     * Takes the selected month and type of appointments and fills the field with the number that matches the criteria
     * @param event Click event
     * @throws IOException Throws error if fails
     */
    public void appointmentReport(ActionEvent event) throws IOException {

        if (typeCombo.getSelectionModel().getSelectedItem() == null || monthCombo.getSelectionModel().getSelectedItem() == null) {
            reportErrorLabel.setText("Type or month combo box is empty please select items in both");
        } else if (typeCombo.getSelectionModel().getSelectedItem() != null && monthCombo.getSelectionModel().getSelectedItem() != null) {
            customerField.clear();
            ObservableList<Appointments> appointmentList = DBAppointments.getAllAppointments();
//            ArrayList<String> alist = new ArrayList<>();
//            ArrayList<Timestamp> tlist = new ArrayList<>();

//            System.out.println(arrayList);

//            for (Appointments a : appointmentList) {
//                alist.add(a.getType());
//                tlist.add(a.getStartDateTime());
//            }

            List<String> alist = appointmentList.stream().map(Appointments::getType).collect(Collectors.toList());
            List<Timestamp> tlist = appointmentList.stream().map(Appointments::getStartDateTime).collect(Collectors.toList());

//            System.out.println(tlist);
//            System.out.println(alist);

            ArrayList<ArrayList> arrayList = new ArrayList(Arrays.asList(alist, tlist));




            ArrayList<Timestamp> tempTimeList = arrayList.get(1);
            ArrayList<String> tempTypeList = arrayList.get(0);
//            System.out.println("TempTypeList before for loop is " + tempTypeList);
//            System.out.println("TempTimeList before for loop is " + tempTimeList);

            int i = 0;
            Integer j = 0;
            for (Timestamp t : tempTimeList) {

                String type = tempTypeList.get(i);
//            Timestamp tempTimestamp = tempTypeList.get(i);
                String month = tempTimeList.get(i).toString();
                Timestamp ts = Timestamp.valueOf(month);
                long timestamp = ts.getTime();
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(timestamp);
                int monthNum = cal.get(Calendar.MONTH);
                String monthString;
                switch (monthNum) {
                    case 0:
                        monthString = "January";
                        break;
                    case 1:
                        monthString = "February";
                        break;
                    case 2:
                        monthString = "March";
                        break;
                    case 3:
                        monthString = "April";
                        break;
                    case 4:
                        monthString = "May";
                        break;
                    case 5:
                        monthString = "June";
                        break;
                    case 6:
                        monthString = "July";
                        break;
                    case 7:
                        monthString = "August";
                        break;
                    case 8:
                        monthString = "September";
                        break;
                    case 9:
                        monthString = "October";
                        break;
                    case 10:
                        monthString = "November";
                        break;
                    case 11:
                        monthString = "December";
                        break;
                    default:
                        monthString = "Invalid month";
                        break;
                }
//                ObservableList<Appointments> tempAppointmentList = DBAppointments.getAllAppointments();
//                ArrayList<String> tempAList = new ArrayList<>();
//                ArrayList<Timestamp> tempTList = new ArrayList<>();
//                for (Appointments app : tempAppointmentList) {
//                    tempAList.add(app.getType());
//                    tempTList.add(app.getStartDateTime());
//                }

                if (monthString.equals(monthCombo.getSelectionModel().getSelectedItem().toString()) && type.equals(typeCombo.getSelectionModel().getSelectedItem().toString())) {
                    j += 1;

                }
//                System.out.println(monthString);
                i++;

            }
            String jString = j.toString();
            customerField.setText(jString);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointments> appointmentList = DBAppointments.getAllAppointments();
//        ArrayList alist = new ArrayList();
//        ArrayList clist = new ArrayList();
//        ArrayList locationList = new ArrayList();
        ArrayList<String> mlist = new ArrayList<>(Arrays.asList("January", "February", "March", "April", "May", "June"
                , "July", "August", "September", "October", "November", "December"));
        monthCombo.getItems().addAll(mlist);

        ObservableList<Contacts> contactList = DBContacts.getAllContacts();

//        for(Contacts c : contactList) {
//            clist.add(c.getContactName());
//        }

        List<String> locationList = appointmentList.stream().map(Appointments::getLocation).distinct().collect(Collectors.toList());
        List<String> clist = contactList.stream().map(Contacts::getContactName).collect(Collectors.toList());
        List<String> alist = appointmentList.stream().map(Appointments::getType).distinct().collect(Collectors.toList());
        System.out.println(locationList);

        scheduleCombo.getItems().addAll(clist);


//        for (Appointments a : appointmentList) {
//            if(!locationList.contains(a.getLocation())) {
//                locationList.add(a.getLocation());
//            }
//            if (!alist.contains(a.getType())) {
//                alist.add(a.getType());
//            }
//        }
        locationCombo.getItems().addAll(locationList);
        typeCombo.getItems().addAll(alist);
    }


}



//        for(String s: mlist) {
//            int i =0;
//            String month = mlist.get(i);
//            Timestamp ts = Timestamp.valueOf(month);
//            long timestamp = ts.getTime();
//            Calendar cal = Calendar.getInstance();
//            cal.setTimeInMillis(timestamp);
//            int monthNum = cal.get(Calendar.MONTH);
//            ArrayList<String> newList = new ArrayList<>();
//            String monthString;
//            switch (monthNum) {
//                case 0:  monthString = "January";
//                    break;
//                case 1:  monthString = "February";
//                    break;
//                case 2:  monthString = "March";
//                    break;
//                case 3:  monthString = "April";
//                    break;
//                case 4:  monthString = "May";
//                    break;
//                case 5:  monthString = "June";
//                    break;
//                case 6:  monthString = "July";
//                    break;
//                case 7:  monthString = "August";
//                    break;
//                case 8:  monthString = "September";
//                    break;
//                case 9: monthString = "October";
//                    break;
//                case 10: monthString = "November";
//                    break;
//                case 11: monthString = "December";
//                    break;
//                default: monthString = "Invalid month";
//                    break;
//            }
//            System.out.println(monthString);
//            if(!newList.contains(monthString)) {
//                newList.add(monthString);
//                monthCombo.getItems().add(monthString);
//            }
//
//        }


//        String month = mlist.get(0);
//        Timestamp ts = Timestamp.valueOf(month);
//        long timestamp = ts.getTime();
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(timestamp);
//
//        int monthNum = cal.get(Calendar.MONTH);




