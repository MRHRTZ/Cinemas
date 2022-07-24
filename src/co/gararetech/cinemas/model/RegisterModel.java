package co.gararetech.cinemas.model;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterModel {
    private URL showUserURL;
    private URL createUserURL;
    private String email;
    private String password;
    private String cityId;
    private HttpURLConnection connection;

    public RegisterModel() {
        try {
            this.showUserURL = new URL("https://mrhrtz.com/gararetech/cinemas/api/v1/show_user.php");
            this.createUserURL = new URL("https://mrhrtz.com/gararetech/cinemas/api/v1/create_user.php");
        } catch (MalformedURLException ex) {
            Logger.getLogger(RegisterModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public URL getShowUserURL() {
        return showUserURL;
    }

    public URL getCreateUserURL() {
        return createUserURL;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public void setConnection(HttpURLConnection connection) {
        this.connection = connection;
    }

    
}
