/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.utils.ScaleImage;
import co.gararetech.cinemas.view.DashboardView;
import co.gararetech.cinemas.view.elements.RoundedPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author user
 */
public class DashboardController {

    private DashboardModel model;

    public DashboardModel getModel() {
        return model;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
    }
    
    public void initToken() throws MalformedURLException, IOException {
        URL tokenUrl = model.getTokenEndpoint();

        model.setConnection((HttpURLConnection) tokenUrl.openConnection());
        model.getConnection().setRequestMethod("POST");
        model.getConnection().setRequestProperty("Content-Type", "application/json");
        model.getConnection().setRequestProperty("Client-Secret", "123456");
        model.getConnection().setConnectTimeout(5000);
        model.getConnection().setReadTimeout(5000);

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        int status = model.getConnection().getResponseCode();

        if (status > 299) {
            reader = new BufferedReader(new InputStreamReader(model.getConnection().getErrorStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        } else {
            reader = new BufferedReader(new InputStreamReader(model.getConnection().getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        }

        JSONObject result = new JSONObject(responseContent.toString());
        String token = result.getJSONObject("results").getString("token");
        
        model.setToken(token);
    }
}
