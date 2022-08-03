package co.gararetech.cinemas.database.entity;

import co.gararetech.cinemas.database.CinemasDB;
import co.gararetech.cinemas.utils.JSONResultSet;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;

public class CinemasOrder extends CinemasDB {

    final private String sqlShowOrderHistoryByID = "SELECT * FROM cinemas_orders \n"
            + "INNER JOIN cinemas_studio ON cinemas_studio.studio_id = cinemas_orders.studio_id \n"
            + "WHERE user_id = ?";

    public CinemasOrder() throws ClassNotFoundException, SQLException {
        super();
    }

    public JSONArray showOrderHistoryByID(String userId) {
        PreparedStatement statement = null;
        JSONArray resultJSON = null;
        try {
            statement = super.getConnection().prepareStatement(this.sqlShowOrderHistoryByID);
            statement.setString(1, userId);
            ResultSet result = statement.executeQuery();
            resultJSON = new JSONResultSet(result).mapResultSet();
        } catch (SQLException ex) {
            Logger.getLogger(CinemasOrder.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CinemasOrder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return resultJSON;
    }
}
