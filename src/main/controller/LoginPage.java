package main.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Main;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**Controller class for the login page
 * @author Clayton Dixon
 */

public class LoginPage implements Initializable {


    @FXML
    private Button button;
    @FXML
    private Label wrongLogin;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label zoneID;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label usernameLabel;
    public String locale;
    public String currentLanguage = System.getProperty("user.language");

    /**
     * Gets the current utc time stamp
     * @return utc Returns utc timestamp as a string
     */
    private String getUTCTimestamp() {
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        return utc.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    /**
     * Checks the login and verifies the username and password are correct and displays appropriate error in appropriate language if incorrect
     * @param event Handles click event
     * @throws IOException Throws error if fails
     */

    public void checkLogin(ActionEvent event) throws IOException {
        Main m = new Main();
        if (username.getText().equals("admin") && password.getText().equals("admin")) {
            try
            {
                String filename= "login_activity.txt";
                FileWriter fw = new FileWriter(filename,true);
                fw.write("User admin successfully logged in on " + getUTCTimestamp() + " UTC Time" + System.lineSeparator());
                fw.close();
            }
            catch(IOException ioe)
            {
                System.err.println("IOException: " + ioe.getMessage());
            }
            m.changeScene("view/main.fxml");
        } else if (username.getText().equals("test") && password.getText().equals("test")) {
            try
            {
                String filename= "login_activity.txt";
                FileWriter fw = new FileWriter(filename,true);
                fw.write("User test successfully logged in on " + getUTCTimestamp() + " UTC Time" + System.lineSeparator());
                fw.close();
            }
            catch(IOException ioe)
            {
                System.err.println("IOException: " + ioe.getMessage());
            }
            m.changeScene("view/main.fxml");

        } else if (username.getText().isEmpty() && password.getText().isEmpty()) {

            if (currentLanguage.equals("fr")) {
                wrongLogin.setText("Veuillez saisir les identifiants de connexion");
            } else wrongLogin.setText("Please enter login credentials");

        } else if (!username.getText().equals("test") || password.getText().isEmpty() || !password.getText().equals("test") || !password.getText().equals("admin") || !username.getText().equals("admin") || username.getText().isEmpty()) {
            try
            {
                String filename= "login_activity.txt";
                FileWriter fw = new FileWriter(filename,true);
                fw.write("User " + username.getText() + " unsuccessfully logged in on " + getUTCTimestamp() + " UTC Time" + System.lineSeparator());
                fw.close();
            }
            catch(IOException ioe)
            {
                System.err.println("IOException: " + ioe.getMessage());
            }

            if (currentLanguage.equals("fr")) {
                wrongLogin.setText("Mauvais nom d'utilisateur ou mot de passe");

            } else wrongLogin.setText("Wrong username or password");

        }
    }

    /**
     * Gets the locale and zone ID of the user's computer and sets zoneID label
     * @param url Needed for initialize
     * @param resourceBundle Needed for initialize
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locale = Locale.getDefault().toString();
        ZoneId zone = ZoneId.systemDefault();

//        Locale localeTemp = Locale.FRANCE;
//        String locale2 = localeTemp.toString();
//        System.out.println("Locale is " + locale2);
        zoneID.setText(zone.toString());

        System.out.println(java.util.TimeZone.getDefault().getDisplayName());
//        if (locale.equals("en_US")) {
//            zoneID.setText("Zone ID: 1033");
//        } else if (locale.equals("fr_FR")) {
//            zoneID.setText("Zone ID: 1036");
//        } else
//            zoneID.setText("Invalid locale detected");

        if (currentLanguage.equals("fr")) {
            usernameLabel.setText("nom d'utilisateur");
            passwordLabel.setText("le mot de passe");
            button.setText("connexion");
        }

//        if (currentLanguage.equals("fr") && zoneID.getText().equals("Zone ID: 1036")) {
//            zoneID.setText("ID de zone : 1036");
//        } else if (currentLanguage.equals("en") && zoneID.getText().equals("Zone ID: 1036")) {
//            zoneID.setText("Zone ID: 1036");
//        } else if (currentLanguage.equals("en") && zoneID.getText().equals("Zone ID: 1033")) {
//            zoneID.setText("Zone ID: 1033");
//        } else if (currentLanguage.equals("fr") && zoneID.getText().equals("Zone ID: 1033")) {
//            zoneID.setText("ID de zone : 1033");
//        }



    }
//    public void changeLanguage() throws IOException {
//
//        if (languageBox.getValue().equals("French") && zoneID.getText().equals("Zone ID: 1036")) {
//            zoneID.setText("ID de zone : 1036");
//        } else if (languageBox.getValue().equals("English") && zoneID.getText().equals("Zone ID: 1036")) {
//            zoneID.setText("Zone ID: 1036");
//        } else if (languageBox.getValue().equals("English") && zoneID.getText().equals("Zone ID: 1033")) {
//            zoneID.setText("Zone ID: 1033");
//        } else if (languageBox.getValue().equals("French") && zoneID.getText().equals("Zone ID: 1033")) {
//            zoneID.setText("ID de zone : 1033");
//        }
//
//            if (languageBox.getValue().equals("French") || locale.toString().equals("fr_FR")) {
//                usernameLabel.setText("Nom d'utilisateur");
//                passwordLabel.setText("Mot de passe");
//                button.setText("Connexion");
//            } else if (languageBox.getValue().equals("English")) {
//                usernameLabel.setText("Username");
//                passwordLabel.setText("Password");
//                button.setText("Login");
//            }
//            if (username.getText().equals("test") && password.getText().equals("test")) {
//                wrongLogin.setText("Success!");
//
//
//            } else if (username.getText().isEmpty() && password.getText().isEmpty()) {
//                if (languageBox.getValue().equals("French") || locale.toString().equals("fr_FR")) {
//                    wrongLogin.setText("Veuillez saisir les identifiants de connexion");
//                } else wrongLogin.setText("Please enter login credentials");
//
//            } else {
//                if (languageBox.getValue().equals("French") || locale.toString().equals("fr_FR")) {
//                    wrongLogin.setText("Mauvais nom d'utilisateur ou mot de passe");
//
//                } else wrongLogin.setText("Wrong username or password");
//
//            }
//        }

    }



