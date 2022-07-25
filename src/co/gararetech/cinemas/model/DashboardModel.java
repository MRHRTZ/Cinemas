package co.gararetech.cinemas.model;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class DashboardModel {
    private URL nowPlayingUrl;
    private URL tokenEndpoint;
    private JSONArray playingList;
    private HttpURLConnection connection;
    private String token;
    private JSONObject userData;
    
    public DashboardModel() {
        try {
            this.tokenEndpoint = new URL("https://api.tix.id/v1/token");
            this.nowPlayingUrl = new URL("https://api.tix.id/v1/movies/now_playing?tz=7");
        } catch (MalformedURLException ex) {
            Logger.getLogger(DashboardModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public URL getTokenEndpoint() {
        return tokenEndpoint;
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public void setConnection(HttpURLConnection connection) {
        this.connection = connection;
    }
    
    public URL getNowPlayingUrl() {
        return nowPlayingUrl;
    }

    public void setNowPlayingUrl(URL nowPlayingUrl) {
        this.nowPlayingUrl = nowPlayingUrl;
    }

    public JSONArray getPlayingList() {
        return playingList;
    }

    public void setPlayingList(JSONArray playingList) {
        this.playingList = playingList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JSONObject getUserData() {
        return userData;
    }

    public void setUserData(JSONObject userData) {
        this.userData = userData;
    }
    
    
    
    
}
