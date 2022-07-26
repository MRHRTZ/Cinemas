/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.gararetech.cinemas.model;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Acer
 */
public class ProfileModel {
    private URL showUserURL;
    private URL UpdateUserURL;
    private String email;
    private String newPassword;
    private String oldPassword;
    private JSONObject userData;

    public ProfileModel() {
        try {
            this.showUserURL = new URL("https://mrhrtz.com/gararetech/cinemas/api/v1/show_user.php");
            this.UpdateUserURL = new URL("https://mrhrtz.com/gararetech/cinemas/api/v1/update_user.php");
        } catch (MalformedURLException ex) {
            Logger.getLogger(RegisterModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public JSONObject getUserData() {
        return userData;
    }

    public void setUserData(JSONObject userData) {
        this.userData = userData;
    }

    public URL getShowUserURL() {
        return showUserURL;
    }

    public void setShowUserURL(URL showUserURL) {
        this.showUserURL = showUserURL;
    }

    public URL getUpdateUserURL() {
        return UpdateUserURL;
    }

    public void setUpdateUserURL(URL UpdateUserURL) {
        this.UpdateUserURL = UpdateUserURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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
    private String cityId;
    private HttpURLConnection connection;
    
}
