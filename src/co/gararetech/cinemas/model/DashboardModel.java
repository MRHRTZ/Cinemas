package co.gararetech.cinemas.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;

public class DashboardModel {
    private URL nowPlayingUrl;
    private JSONArray playingList;
    private String token;
    
    public DashboardModel() {
        try {
            this.nowPlayingUrl = new URL("https://api.tix.id/v1/movies/now_playing?tz=7");
        } catch (MalformedURLException ex) {
            Logger.getLogger(DashboardModel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    
}
