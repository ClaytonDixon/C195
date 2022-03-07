package main.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.DAO.DBAppointments;
import main.Main;
import main.model.Appointments;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

/**
 * Controller class for the main appointment page
 * @author Clayton Dixon
 */

public class AppointmentPage implements Initializable {

    public AppointmentPage() {

    }

    @FXML
    private Button backButton;
    @FXML
    private TableView<Appointments> table;
    @FXML
    private Button addAppointmentButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button modifyAppointmentButton;
    @FXML
    private Label errorLabel;
    @FXML
    private TableColumn<Appointments, Integer> appointmentIDCol;
    @FXML
    private TableColumn<Appointments, String> titleCol;
    @FXML
    private TableColumn<Appointments, String> descriptionCol;
    @FXML
    private TableColumn<Appointments, String> locationCol;
    @FXML
    private TableColumn<Appointments, String> typeCol;
    @FXML
    private TableColumn<Appointments, Timestamp> startCol;
    @FXML
    private TableColumn<Appointments, Timestamp> endCol;
    @FXML
    private TableColumn<Appointments, Integer> customerIDCol;
    @FXML
    private TableColumn<Appointments, Integer> userIDCol;
    @FXML
    private TableColumn<Appointments, Integer> contactIDCol;
    @FXML
    private RadioButton weekRadio;
    @FXML
    private RadioButton monthRadio;
    private Scene scene;
    private Parent root;
    private Stage stage;
    private LocalTime timeNow;

    ToggleGroup group = new ToggleGroup();


    /**
     * Initializes and fills the table view with all of the appointments
     * @param url Needed for initialize function
     * @param resourceBundle Needed for initialize function
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<Appointments, Timestamp>("startDateTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<Appointments, Timestamp>("endDateTime"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("customerID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("userID"));
        contactIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("contactID"));
        ObservableList<Appointments> appointmentList = DBAppointments.getAllAppointments();
        table.setItems(appointmentList);

        weekRadio.setToggleGroup(group);
        monthRadio.setToggleGroup(group);

        timeNow = LocalTime.now();

        Timestamp ts = Timestamp.from(Instant.now());

        ObservableList<Appointments> user1List = DBAppointments.getAllAppointments();
        user1List.clear();
        ArrayList<Timestamp> timestamps = new ArrayList<>();
        for(Appointments a : appointmentList) {

            if(a.getUserID()==1) {
                user1List.add(a);
                timestamps.add(a.getStartDateTime());
            }
        }
//        System.out.println(user1List);
//        System.out.println("Time stamps are " + timestamps);


        int i = 0;
        if(!user1List.isEmpty()) {
            for(Timestamp t: timestamps) {
                long diff = Math.abs(ChronoUnit.MINUTES.between(ts.toLocalDateTime(), t.toLocalDateTime()));
                int listSize = timestamps.size();
                i++;
                    if(i < listSize){
                        if (diff <= 15) {
                            Stage window = new Stage();
                            window.initModality(Modality.APPLICATION_MODAL);
                            window.setMinWidth(250);
                            String str = "Appointment with ID of " + user1List.get(timestamps.indexOf(t)).getAppointmentID() + " is starting in 15 minutes or less with start date and time of " + user1List.get(timestamps.indexOf(t)).getStartDateTime();
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

                    } else if(listSize == i){
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

    /**
     *Handles the back button click event
     * @param event The button click event for the back button in JavaFX
     */

    public void backButtonEvent (ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("view/main.fxml");
    }

    /**
     *Handles the changing of scenes when clicking on the add appointment button
     * @throws IOException Throws error if fails
     */

    public void addAppointment () throws IOException {
        Main m = new Main();
        m.changeScene("view/addAppointments.fxml");
    }

    public void setWeekRadio(ActionEvent event) throws IOException{
        weekRadio.setSelected(true);
        if(monthRadio.isSelected()) {
            monthRadio.setSelected(false);
        }

        if(weekRadio.isSelected()) {
            ObservableList<Appointments> appointmentList = DBAppointments.getAllAppointments();

            Calendar c = Calendar.getInstance();
            Integer year1 = c.get(c.YEAR);

            Integer week1 = c.get(c.WEEK_OF_YEAR);

            ObservableList<Appointments> filterdList = DBAppointments.getAllAppointments();
            filterdList.clear();



            for(Appointments a: appointmentList) {
                Timestamp ts = a.getStartDateTime();
                Calendar d = Calendar.getInstance();
                d.setTimeInMillis(ts.getTime());

                Integer year2 = d.get(d.YEAR);
                Integer week2 = d.get(d.WEEK_OF_YEAR);


//                System.out.println("year 1 is " + year1);
//                System.out.println("year 2 is " + year2);
//
//                System.out.println("week1 is " + week1);
//                System.out.println("week2 is " + week2);

                if(year1.equals(year2)) {
//                    System.out.println("year good");
                        if(week1.equals(week2)) {
                            filterdList.add(a);
//                            System.out.println(a.getAppointmentID());
                    }
                }
            }
//            System.out.println(filterdList);
            table.getItems().clear();
            table.setItems(filterdList);
        }
    }

    public void setMonthRadio(ActionEvent event) throws IOException{

        monthRadio.setSelected(true);
        if(weekRadio.isSelected()) {
            weekRadio.setSelected(false);
        }

        if(monthRadio.isSelected()) {
            ObservableList<Appointments> appointmentList = DBAppointments.getAllAppointments();

            Calendar c = Calendar.getInstance();
            Integer year1 = c.get(c.YEAR);
            Integer month1 = c.get(c.MONTH);

            ObservableList<Appointments> filterdList = DBAppointments.getAllAppointments();
            filterdList.clear();


            for(Appointments a: appointmentList) {
                Timestamp ts = a.getStartDateTime();
                Calendar d = Calendar.getInstance();
                d.setTimeInMillis(ts.getTime());

                Integer year2 = d.get(d.YEAR);
                Integer month2 = d.get(d.MONTH);
//                System.out.println("year 1 is " + year1);
//                System.out.println("year 2 is " + year2);
//                System.out.println("month1 is " + month1);
//                System.out.println("month2 is " + month2);


                if(year1.equals(year2)) {
//                    System.out.println("year good");
                    if(month1.equals(month2)) {
                        filterdList.add(a);
//                        System.out.println(a);
                    }
                }
//                System.out.println(filterdList);
            }


            table.getItems().clear();
            table.setItems(filterdList);

        }

    }

    /**
     * When clicking on the delete appointment button this function makes an alert box on the screen
     * @param str Takes string from the deleteAppointment function
     * @throws IOException Throws error if fails
     */

    public void alertBox(String str) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);

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
        //appointment id and type

    }

    /**
     * Returns the selected appointment and passes it to the modify appointment page to autofill the data in the form
     * @param event Handles the click event on the modify appointment buton
     * @return appointments Returns the selected appointment in the tableview
     * @throws IOException Throws error if fails
     */

    public Appointments modifyAppointment (ActionEvent event) throws IOException {
        Appointments appointments = table.getSelectionModel().getSelectedItem();



        if (appointments == null || appointments.getCustomerID() <= 0) {
            errorLabel.setText("Please choose a appointment to modify");
        }
        else if (appointments != null || appointments.getCustomerID() > 0) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/view/modifyAppointments.fxml"));
            Parent root = loader.load();

            ModifyAppointmentPage modifyAppointmentPage = loader.getController();

            modifyAppointmentPage.loadData(appointments);

            stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        return appointments;
    }

    /**
     * Gets the selected appointment in the table view and calls the alertBox function and passes the appointment ID and type to the function
     * @param event Handles click event
     * @throws IOException Throws error if fails
     */

    public void deleteAppointment (ActionEvent event) throws IOException {
        Appointments appointment = table.getSelectionModel().getSelectedItem();

        if(table.getItems().isEmpty() || appointment == null){
            errorLabel.setText("Please select a valid item");
//            System.out.println("Please select a valid item");
        } else  {
            DBAppointments.deleteAppointment(appointment.getAppointmentID());
            ObservableList<Appointments> appointmentsList = DBAppointments.getAllAppointments();
            table.setItems(appointmentsList);
            String alertString = "Appointment with ID " + appointment.getAppointmentID().toString() + " and type " + appointment.getType() + " has been deleted.";
            alertBox(alertString);

        }
    }

}
