/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.gararetech.cinemas.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author user
 */
public class StaticMap {
    final private String apikey = "AIzaSyDaKRWCyxkScPm8mYRf4aFYsgpW2ljnml8";
    private String query;
    private int height;
    private int width;
    private int zoom;
    private float latitude;
    private float longitude;
    
    public StaticMap() {
        
    }

    public String getApikey() {
        return apikey;
    }

    public String getQuery() {
        return query;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getZoom() {
        return zoom;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    
    
    public String getUrl() {
        String urlString = "https://maps.googleapis.com/maps/api/staticmap?center=" + this.getQuery().replaceAll(" ", "+") + "&zoom=" + this.getZoom() + "&size=" + this.getWidth() + "x" + this.getHeight() + "&markers=icon:https://mrhrtz.com/gararetech/cinemas/CinemaMarker-48.png|" + String.valueOf(this.getLatitude()) + "," + String.valueOf(this.getLongitude()) + "&key=" + this.getApikey();
        return urlString;
    }
    
    public BufferedImage getImage() throws MalformedURLException, IOException {
        URL mapStatic = new URL(this.getUrl());
        BufferedImage imageBuffer = ImageIO.read(mapStatic.openStream());
        return imageBuffer;
    }
    
    public ImageIcon getIcon() throws IOException {
        ImageIcon iconStaticMap = new ImageIcon(this.getImage());
        return iconStaticMap;
    }
}
