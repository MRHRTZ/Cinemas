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
    private URL upcomingUrl;
    private URL cinemaUrl;
    private URL citiesUrl;
    private URL movieScheduleUrl;
    private URL movieDetailUrl;
    private URL tokenEndpoint;
    private URL usersEndpoint;
    private URL orderHistoryUrl;
    private URL bugReportUrl;
    private URL showStudioUrl;
    private URL createStudioUrl;
    private URL updateStudioUrl;
    private URL createOrderUrl;

    private JSONArray playingList;
    private JSONArray upcomingList;
    private JSONArray cinemaList;
    private JSONArray cityList;
    private JSONArray movieScheduleList;
    private JSONArray orderHistoryList;

    private HttpURLConnection connection;
    private String token;
    private JSONObject userData;
    private JSONObject orderHistoryDelete;
    private JSONObject orderHistoryUpdate;
    private String activeTab;
    private String invalidMessage;
    private Boolean needRefresh;
    private int requestID;

    private int mousepX;
    private int mousepY;

    public DashboardModel() {
        try {
            String baseUrl = "https://api.tix.id";
            String DbBaseUrl = "https://mrhrtz.com/gararetech/cinemas/api/v1";
            this.tokenEndpoint = new URL(baseUrl + "/v1/token");
            this.nowPlayingUrl = new URL(baseUrl + "/v1/movies/now_playing?tz=7");
            this.upcomingUrl = new URL(baseUrl + "/v1/movies/upcoming");
            this.cinemaUrl = new URL(baseUrl + "/v1/theaters");
            this.citiesUrl = new URL(baseUrl + "/v1/cities");
            this.movieScheduleUrl = new URL(baseUrl + "/v3/schedule");
            this.movieDetailUrl = new URL("https://curated.tix.id/v1/app/movie/");
            this.usersEndpoint = new URL(DbBaseUrl + "/show_user.php");
            this.bugReportUrl = new URL(DbBaseUrl + "/create_report.php");
            this.orderHistoryUrl = new URL(DbBaseUrl + "/");
            this.showStudioUrl = new URL(DbBaseUrl + "/show_studio.php");
            this.createStudioUrl = new URL(DbBaseUrl + "/create_studio.php");
            this.updateStudioUrl = new URL(DbBaseUrl + "/update_studio.php");
            this.createOrderUrl = new URL(DbBaseUrl + "/create_order.php");
            this.invalidMessage = null;
            this.requestID = 0;
        } catch (MalformedURLException ex) {
            Logger.getLogger(DashboardModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getRequestID() {
        return requestID;
    }

    public int nextRequestID() {
        this.requestID += 1;
        return this.requestID;
    }

    public JSONObject getOrderHistoryUpdate() {
        return orderHistoryUpdate;
    }

    public URL getCreateOrderUrl() {
        return createOrderUrl;
    }

    public URL getBugReportUrl() {
        return bugReportUrl;
    }

    public void setOrderHistoryList(JSONArray orderHistoryList) {
        this.orderHistoryList = orderHistoryList;
    }

    public JSONArray getOrderHistoryList() {
        return orderHistoryList;
    }

    public void setOrderHistoryUpdate(JSONObject orderHistoryUpdate) {
        this.orderHistoryUpdate = orderHistoryUpdate;
    }

    public URL getOrderHistoryUrl() {
        return orderHistoryUrl;
    }

    public Boolean getNeedRefresh() {
        return needRefresh;
    }

    public void setNeedRefresh(Boolean needRefresh) {
        this.needRefresh = needRefresh;
    }

    public URL getUsersEndpoint() {
        return usersEndpoint;
    }

    public String getInvalidMessage() {
        return invalidMessage;
    }

    public void setInvalidMessage(String invalidMessage) {
        this.invalidMessage = invalidMessage;
    }

    public String getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(String activeTab) {
        this.activeTab = activeTab;
    }

    public URL getUpcomingUrl() {
        return upcomingUrl;
    }

    public URL getCinemaUrl() {
        return cinemaUrl;
    }

    public URL getCitiesUrl() {
        return citiesUrl;
    }

    public URL getMovieScheduleUrl() {
        return movieScheduleUrl;
    }

    public URL getMovieDetailUrl() {
        return movieDetailUrl;
    }

    public JSONArray getUpcomingList() {
        return upcomingList;
    }

    public JSONArray getCityList() {
        return cityList;
    }

    public void setCityList(JSONArray cityList) {
        this.cityList = cityList;
    }

    public void setUpcomingList(JSONArray upcomingList) {
        this.upcomingList = upcomingList;
    }

    public JSONArray getCinemaList() {
        return cinemaList;
    }

    public void setCinemaList(JSONArray cinemaList) {
        this.cinemaList = cinemaList;
    }

    public JSONArray getMovieScheduleList() {
        return movieScheduleList;
    }

    public void setMovieScheduleList(JSONArray movieScheduleList) {
        this.movieScheduleList = movieScheduleList;
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

    public JSONObject getOrderHistoryDelete() {
        return orderHistoryDelete;
    }

    public void setOrderHistoryDelete(JSONObject orderHistoryDelete) {
        this.orderHistoryDelete = orderHistoryDelete;
    }

    public URL getShowStudioUrl() {
        return showStudioUrl;
    }

    public URL getCreateStudioUrl() {
        return createStudioUrl;
    }

    public URL getUpdateStudioUrl() {
        return updateStudioUrl;
    }

    public int getMousepX() {
        return mousepX;
    }

    public void setMousepX(int mousepX) {
        this.mousepX = mousepX;
    }

    public int getMousepY() {
        return mousepY;
    }

    public void setMousepY(int mousepY) {
        this.mousepY = mousepY;
    }
    

}
