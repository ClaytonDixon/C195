package main.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.DAO.DBAppointments;
import main.DAO.DBCustomers;
import main.Main;
import main.model.Appointments;
import main.model.Customers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**Controller class for the customer page
 * @author Clayton Dixon
 */


public class CustomerPage implements Initializable{

    @FXML
    private Button backButton;
    @FXML
    private Button modifyCustomerButton;
    @FXML
    private Button addCustomerButton;
    @FXML
    private Button deleteCustomerButton;
    @FXML
    private Label errorLabel;
    @FXML
    private TableView<Customers> table;
    @FXML
    private TableColumn<Customers, Integer> customerIDCol;
    @FXML
    private TableColumn<Customers, String> nameCol;
    @FXML
    private TableColumn<Customers, String> addressCol;
    @FXML
    private TableColumn<Customers, String> postalCol;
    @FXML
    private TableColumn<Customers, String> phoneCol;
    @FXML
    private TableColumn<Customers, Integer> divisionIDCol;
    public Customers selectedCustom;
    private Scene scene;
    private Parent root;
    private Stage stage;

    /**
     * Initializes and fills the table view with all of the customers
     * @param url Needed for initialize function
     * @param resourceBundle Needed for initialize function
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDCol.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Customers, String>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Customers, String>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<Customers, String>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Customers, String>("phone"));
        divisionIDCol.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("divisionID"));
        ObservableList<Customers> customerList = DBCustomers.getAllCustomers();
        table.setItems(customerList);
         }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        //Customer ID Column
//        TableColumn<Customers, Integer> customerIDCol = new TableColumn<Customers, Integer>("Customer ID");
//        customerIDCol.setMinWidth(200);
//        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
//        //Name Column
//        TableColumn<Customers, String> nameCol = new TableColumn<Customers, String>("Name");
//        nameCol.setMinWidth(200);
//        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
//        //Address Column
//        TableColumn<Customers, String> addressCol = new TableColumn<Customers, String>("Address");
//        addressCol.setMinWidth(200);
//        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
//        //Postal Column
//        TableColumn<Customers, String> postalCol = new TableColumn<Customers, String>("Postal");
//        postalCol.setMinWidth(200);
//        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
//        //Phone Column
//        TableColumn<Customers, String> phoneCol = new TableColumn<Customers, String>("Phone");
//        phoneCol.setMinWidth(200);
//        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
//        //Customer ID Column
//        TableColumn<Customers, Integer> divisionIDCol = new TableColumn<Customers, Integer>("Division ID");
//        divisionIDCol.setMinWidth(200);
//        divisionIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
//
//        ObservableList<Customers> customerList = DBCustomers.getAllCustomers();
//        table = new TableView<>();
//        table.setItems(customerList);
//        table.getColumns().addAll(customerIDCol, nameCol, addressCol, postalCol, phoneCol, divisionIDCol);
//
//    }


    /**
     * Gets the selected customer in the table view and checks to see if the customer has any appointments before deleting the customer and calling the alertBox function to display
     * @param event Handles click event
     * @throws IOException Throws error if fails
     */

    public void deleteCustomer (ActionEvent event) throws IOException {
        Customers customers = table.getSelectionModel().getSelectedItem();

        ObservableList<Appointments> alist = DBAppointments.getAllAppointments();
//        ArrayList<Integer> clist = new ArrayList<>();
//
//        for(Appointments a :alist) {
//            clist.add(a.getCustomerID());
//        }

        List<Integer> clist = alist.stream().map(Appointments::getCustomerID).collect(Collectors.toList());

        if(clist.contains(customers.getCustomerID())) {
            errorLabel.setText("Please delete all of customer's appointments before deleting customer");
        } else {
            if(customers.getCustomerID().equals(null)){
                errorLabel.setText("Please select a valid item");
            } else  {
                DBCustomers.deleteCustomer(customers.getCustomerID());
                ObservableList<Customers> customerList = DBCustomers.getAllCustomers();
                table.setItems(customerList);
                alertBox();
            }
        }
    }

    /**
     * Displays alert box on the screen to notify the customer has been successfully deleted
     * @throws IOException Throws error if fails
     */

    public void alertBox() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("Customer successfully deleted.");
        Button closeButton = new Button("Close the window");
        closeButton.setOnAction(e -> window.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }

    /**
     * Returns the selected customer and passes it to the modify customer page to autofill the data in the form
     * @param event Handles click event
     * @return customers Returns the selected customer in the tableview
     * @throws IOException Throws error if fails
     */

    public Customers modifyCustomer (ActionEvent event) throws IOException {

        Customers customers = table.getSelectionModel().getSelectedItem();

        if (customers == null || customers.getCustomerName().isEmpty()) {
            errorLabel.setText("Please choose a customer to modify");
        }
         else if (customers.getCustomerName() != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/view/modifyCustomers.fxml"));
            Parent root = loader.load();

            ModifyCustomerPage modifyCustomerPage = loader.getController();

            modifyCustomerPage.loadData(customers);

            stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        return customers;
    }


    /**
     * Handles back button click event
     * @param event Click event
     * @throws IOException Throws error if fails
     */

    public void backButtonEvent(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("view/main.fxml");
    }

    /**
     * Handles the add customer event and takes user to add customer form
     * @throws IOException Throws error if fails
     */

    public void addCustomer () throws IOException{
        Main m = new Main();
        m.changeScene("view/addCustomers.fxml");
    }



}
