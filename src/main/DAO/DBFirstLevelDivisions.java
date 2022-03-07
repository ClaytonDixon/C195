package main.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.model.Divisions;
import main.utils.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Class for functions that interact with the first level divisions table
 * @author Clayton Dixon
 */

public class DBFirstLevelDivisions {

    /**
     * Runs sql statement that gets all of the divisions and assigns them to an observable list
     * @return dlist Returns an observable list of all the divisions
     */
    public static ObservableList<Divisions> getAllDivisions() {

        ObservableList<Divisions> dlist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from first_level_divisions";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                Divisions c = new Divisions(divisionID, division, countryID);
                dlist.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return dlist;
    }
}
