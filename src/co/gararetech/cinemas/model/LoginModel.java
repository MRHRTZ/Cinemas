package co.gararetech.cinemas.model;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

public class LoginModel {
    private URL tokenEndpoint;
    private URL usersEndpoint;
    private HttpURLConnection connection;
    private JSONObject userData;


    public LoginModel() {
        try {
            this.tokenEndpoint = new URL("https://api.tix.id/v1/token");
            this.usersEndpoint = new URL("https://mrhrtz.com/gararetech/cinemas/api/v1/show_user.php");
        } catch (MalformedURLException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JSONObject getUserData() {
        return userData;
    }
    public void setUserData(JSONObject userData) {
        this.userData = userData;
    }
    public URL getTokenEndpoint() {
        return tokenEndpoint;
    }
    public URL getUsersEndpoint() {
        return usersEndpoint;
    }
    public HttpURLConnection getConnection() {
        return connection;
    }
    public void setConnection(HttpURLConnection connection) {
        this.connection = connection;
    }
}

