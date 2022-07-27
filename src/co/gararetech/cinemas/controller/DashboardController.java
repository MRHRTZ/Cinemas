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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

    public void setActiveButton(DashboardView view, String tabName) {
        model.setActiveTab(tabName);

        if (model.getActiveTab().equals("nowplaying")) {
            view.getBtnNowPlaying().setBackground(Color.decode("#3D3C3A"));
            view.getBtnNowPlaying().setForeground(Color.WHITE);

            view.getBtnUpcoming().setBackground(Color.decode("#D9D9D9"));
            view.getBtnUpcoming().setForeground(Color.BLACK);

            view.getBtnOrderHistory().setBackground(Color.decode("#D9D9D9"));
            view.getBtnOrderHistory().setForeground(Color.BLACK);

            view.getBtnCinema().setBackground(Color.decode("#D9D9D9"));
            view.getBtnCinema().setForeground(Color.BLACK);
        } else if (model.getActiveTab().equals("upcoming")) {
            view.getBtnNowPlaying().setBackground(Color.decode("#D9D9D9"));
            view.getBtnNowPlaying().setForeground(Color.BLACK);

            view.getBtnUpcoming().setBackground(Color.decode("#3D3C3A"));
            view.getBtnUpcoming().setForeground(Color.WHITE);

            view.getBtnOrderHistory().setBackground(Color.decode("#D9D9D9"));
            view.getBtnOrderHistory().setForeground(Color.BLACK);

            view.getBtnCinema().setBackground(Color.decode("#D9D9D9"));
            view.getBtnCinema().setForeground(Color.BLACK);
        } else if (model.getActiveTab().equals("orderhistory")) {
            view.getBtnNowPlaying().setBackground(Color.decode("#D9D9D9"));
            view.getBtnNowPlaying().setForeground(Color.BLACK);

            view.getBtnUpcoming().setBackground(Color.decode("#D9D9D9"));
            view.getBtnUpcoming().setForeground(Color.BLACK);

            view.getBtnOrderHistory().setBackground(Color.decode("#3D3C3A"));
            view.getBtnOrderHistory().setForeground(Color.WHITE);

            view.getBtnCinema().setBackground(Color.decode("#D9D9D9"));
            view.getBtnCinema().setForeground(Color.BLACK);
        } else if (model.getActiveTab().equals("cinemas")) {
            view.getBtnNowPlaying().setBackground(Color.decode("#D9D9D9"));
            view.getBtnNowPlaying().setForeground(Color.BLACK);

            view.getBtnUpcoming().setBackground(Color.decode("#D9D9D9"));
            view.getBtnUpcoming().setForeground(Color.BLACK);

            view.getBtnOrderHistory().setBackground(Color.decode("#D9D9D9"));
            view.getBtnOrderHistory().setForeground(Color.BLACK);

            view.getBtnCinema().setBackground(Color.decode("#3D3C3A"));
            view.getBtnCinema().setForeground(Color.WHITE);
        }

    }

    public void removeContent(DashboardView view) {
        view.getContent().removeAll();
        view.getContent().revalidate();

    }

    public JPanel addLoadingContent(JPanel content) {
        JPanel loading = new JPanel(new CardLayout(0, 185));
        loading.setName("loadingPanel");
        JLabel loadingImage = new JLabel(new ImageIcon(getClass().getResource("../view/images/content-load.gif")));
        loading.setBackground(Color.decode("#42382F"));
        loading.add(loadingImage);
        content.add(loading);
        content.revalidate();
        return loading;
    }

    public void removeLoadingContent(JPanel content, JPanel loading) {
        content.remove(loading);
        content.revalidate();
    }
    public void exitButton(){
        JFrame frame = new JFrame("Exit");
        if (JOptionPane.showConfirmDialog( frame,"Apakah Anda Mau Keluar ?","Cinemas",
            JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
            System.exit(0);
    }
    public void minimizeButton(DashboardView view){
        view.setState(DashboardView.ICONIFIED);
    }

}
