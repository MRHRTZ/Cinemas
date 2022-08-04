/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.view.DashboardView;
import co.gararetech.cinemas.view.LoginView;
import co.gararetech.cinemas.view.ProfileView;
import co.gararetech.cinemas.view.elements.RoundedPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;
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

    public JSONArray getUserList() throws MalformedURLException, IOException {
        URL usersUrl = model.getUsersEndpoint();

        model.setConnection((HttpURLConnection) usersUrl.openConnection());
        model.getConnection().setRequestMethod("GET");
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

        return new JSONArray(responseContent.toString());
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
        System.out.println("Init API Token ..");
        model.setToken(token);
    }

    public void initPage(DashboardView view) throws IOException, UnsupportedLookAndFeelException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        while (model.getUserData() == null) {
            // While user data model empty, do nothing.
        }
        view.getLoadingUser().dispose();
        JSONObject userData = model.getUserData();
        System.out.println("---[ Get User Data ]---");
        System.out.println("User ID  : " + userData.getString("user_id"));
        System.out.println("Email    : " + userData.getString("email"));
        System.out.println("City ID  : " + userData.getString("city_id"));
        System.out.println("-----------------------");
        this.initToken();
        view.setVisible(true);

    }

    public void getCities() throws ProtocolException, IOException {
        System.out.println("Get API Cities");

        URL url = model.getCitiesUrl();

        model.setConnection((HttpURLConnection) url.openConnection());
        model.getConnection().setRequestMethod("GET");
        model.getConnection().setRequestProperty("Authorization", "Bearer " + model.getToken());
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

        JSONObject response = new JSONObject(responseContent.toString());
        System.out.println("Load api cities success");
        model.setCityList(response.getJSONArray("results"));
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
        } else if (model.getActiveTab().equals("profile")) {
            view.getBtnNowPlaying().setBackground(Color.decode("#D9D9D9"));
            view.getBtnNowPlaying().setForeground(Color.BLACK);

            view.getBtnUpcoming().setBackground(Color.decode("#D9D9D9"));
            view.getBtnUpcoming().setForeground(Color.BLACK);

            view.getBtnOrderHistory().setBackground(Color.decode("#D9D9D9"));
            view.getBtnOrderHistory().setForeground(Color.BLACK);

            view.getBtnCinema().setBackground(Color.decode("#D9D9D9"));
            view.getBtnCinema().setForeground(Color.BLACK);
        }

    }

    public void removeContent(DashboardView view) {
        view.getContent().removeAll();
        view.getContent().revalidate();

    }

    public JPanel addLoadingContent(JPanel content, String message) {
        JPanel loading = new JPanel(new CardLayout(0, 200));
        loading.setBackground(Color.decode("#42382F"));
        loading.setName("loadingPanel");

        JPanel contentLoadingPanel = new JPanel();
        contentLoadingPanel.setLayout(new BoxLayout(contentLoadingPanel, BoxLayout.Y_AXIS));
        contentLoadingPanel.setBackground(Color.decode("#42382F"));

        JLabel infoLoading = new JLabel(message);
        infoLoading.setName("infoLoading");
        infoLoading.setForeground(Color.WHITE);
        infoLoading.setFont(new Font("Serif", Font.BOLD, 18));
        infoLoading.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        contentLoadingPanel.add(infoLoading);

        JLabel loadingImage = new JLabel(new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/content-load.gif")));
        loadingImage.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        contentLoadingPanel.add(loadingImage);

        loading.add(contentLoadingPanel);
        content.add(loading);
        content.revalidate();
        return loading;
    }

    public void removeLoadingContent(JPanel content, JPanel loading) {
        content.remove(loading);
        content.revalidate();
    }

    public void exitButton(DashboardView view) {
        if (JOptionPane.showConfirmDialog(view, "Apakah Anda Mau Keluar ?", "Cinemas",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public void minimizeButton(DashboardView view) {
        view.setState(DashboardView.ICONIFIED);
    }

    public void viewProfile(DashboardView dashboardView, ProfileView profileView) throws MalformedURLException, IOException {
        if (model.getCityList() == null) {
            JOptionPane.showMessageDialog(dashboardView, "Masih proses loading, tunggu sebentar ...");
            viewProfile(dashboardView, profileView);
        } else {
            profileView.getjCity().removeAllItems();
            JSONArray cities = model.getCityList();
            for (int i = 0; i < cities.length(); i++) {
                profileView.getjCity().addItem(cities.getJSONObject(i).getString("name"));
            }
            profileView.getjCity().setSelectedItem(model.getUserData().getString("city_id"));
            String email = model.getUserData().getString("email");
            profileView.getTxtEmail().setText(email);
            profileView.getTxtNewPassword().setEnabled(false);
            profileView.getTxtOldPassword().setEnabled(false);

            if (!model.getUserData().isNull("image")) {
                if (model.getUserData().getString("image").equals("")) {
                    ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/profile.png"));
                    profileView.getProfilePicture().setIcon(defaultIcon);
                } else {
                    System.out.println("Img url : " + model.getUserData().getString("image"));
                    URL dataImageUrl = new URL(model.getUserData().getString("image").replaceAll(" ", "%20"));
                    Image iconURL = ImageIO.read(dataImageUrl);

                    ImageIcon image = new ImageIcon(iconURL);
                    Image img;
                    if (image.getIconWidth() > image.getIconHeight()) {
                        img = image.getImage().getScaledInstance(100, -1, Image.SCALE_SMOOTH);
                    } else {
                        img = image.getImage().getScaledInstance(-1, 100, Image.SCALE_SMOOTH);
                    }
                    profileView.getProfilePicture().setIcon(new ImageIcon(img));
                }
            } else {
                ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/profile.png"));
                profileView.getProfilePicture().setIcon(defaultIcon);
            }

            profileView.setDashboardView(dashboardView);
            profileView.getProfileModel().setUserData(model.getUserData());
            profileView.setVisible(true);
            dashboardView.setVisible(false);
        }
    }

    public JDialog addDialogLoading(DashboardView view, String message) {
        JDialog frame = new JDialog(view);

        JPanel framePanel = new RoundedPanel();
        framePanel.setBackground(Color.decode("#42382F"));
        JPanel refreshPanel = new JPanel(new CardLayout(5, 5));
        refreshPanel.setBackground(new Color(0, 0, 0, 0));
        JPanel contentRefreshPane = new JPanel();
        contentRefreshPane.setBackground(Color.decode("#42382F"));
        refreshPanel.add(contentRefreshPane);

        JLabel refreshContent = new JLabel();
        ImageIcon loadIcon = new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/loading-25.gif"));
        refreshContent.setIcon(loadIcon);
        refreshContent.setText(message);
        refreshContent.setFont(new Font("Serial", Font.BOLD, 15));
        refreshContent.setForeground(Color.WHITE);
        contentRefreshPane.add(refreshContent);

        refreshPanel.setPreferredSize(new Dimension(400, 50));
        framePanel.add(refreshPanel);
        frame.getContentPane().add(framePanel);

        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }

    public void removeDialogLoading(DashboardView view) {
        view.getLoadingUser().dispose();
    }

    public void refreshUserData(DashboardView view) throws InterruptedException, IOException {
        if (model.getNeedRefresh()) {
            System.out.println("Refreshing userData");
            Thread.sleep(3000);
            String user_id = model.getUserData().getString("user_id");
            JSONArray newUserDataList = getUserList();
            for (int i = 0; i < newUserDataList.length(); i++) {
                JSONObject rowData = newUserDataList.getJSONObject(i);
                if (rowData.getString("user_id").equals(user_id)) {
                    model.setUserData(rowData);
                    model.setPlayingList(null);
                    model.setUpcomingList(null);
                    System.out.println("Refresh success for id " + rowData.getString("user_id"));
                    removeDialogLoading(view);
                }
            }
        } else {
            System.out.println("Opening dashboard, no need to refresh");
        }
    }
    
    public void logout(DashboardView dashboardView, LoginView loginView) {
        if (JOptionPane.showConfirmDialog(dashboardView, "Apakah anda mau mengakhiri hidup anda ?", "Cinemas", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dashboardView.dispose();
            loginView.setVisible(true);
        }
    }

}
