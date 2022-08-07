/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.view.BugReportView;
import co.gararetech.cinemas.view.DashboardView;
import co.gararetech.cinemas.view.LoginView;
import co.gararetech.cinemas.view.ProfileView;
import co.gararetech.cinemas.view.elements.RoundJButton;
import co.gararetech.cinemas.view.elements.RoundedPanel;
import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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
            // Unlimited Check
            System.out.println("User Data : " + String.valueOf(model.getUserData() != null));
            System.out.println("Invalid Message : " + String.valueOf(model.getInvalidMessage() != null));
            if (model.getUserData() != null) {
                view.getLoadingUser().dispose();
                JSONObject userData = model.getUserData();
                System.out.println("---[ Get User Data ]---");
                System.out.println("User ID  : " + userData.getString("user_id"));
                System.out.println("Email    : " + userData.getString("email"));
                System.out.println("City ID  : " + userData.getString("city_id"));
                System.out.println("-----------------------");
                this.initToken();
                view.setVisible(true);
                break;
            } else if (model.getInvalidMessage() != null) {
                view.getLoadingUser().dispose();
                JOptionPane.showMessageDialog(view, model.getInvalidMessage());
                model.setInvalidMessage(null);
                new LoginView().setVisible(true);
                view.dispose();
                break;
            }
        }
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
        if (JOptionPane.showConfirmDialog(view, "Apakah anda yakin ingin keluar ?", "Cinemas",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public void minimizeButton(DashboardView view) {
        view.setState(DashboardView.ICONIFIED);
    }

    public void viewProfile(DashboardView dashboardView, ProfileView profileView) throws MalformedURLException, IOException {
        if (model.getCityList() == null) {
            JOptionPane.showMessageDialog(dashboardView, "Sedang diproses, mohon tunggu sebentar");
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
                    BufferedImage Img = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/profile.png"));
                    BufferedImage Images = makeRoundedCorner(Img, 1000);
                    profileView.getProfilePicture().setIcon(new ImageIcon(Images));
                } else {
                    System.out.println("Img url : " + model.getUserData().getString("image"));
                    URL dataImageUrl = new URL(model.getUserData().getString("image").replaceAll(" ", "%20"));
                    
                    BufferedImage Img = ImageIO.read(dataImageUrl);
                    BufferedImage roundedImage = makeRoundedCorner(Img, 8100);
                    
                    Image images = roundedImage.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                    ImageIcon img = new ImageIcon(images);
                    profileView.getProfilePicture().setIcon(img);
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

    public BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        // This is what we want, but it only does hard-clipping, i.e. aliasing
        // g2.setClip(new RoundRectangle2D ...)
        // so instead fake soft-clipping by first drawing the desired clip shape
        // in fully opaque white with antialiasing enabled...
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();

        return output;
    }
    
    public void btnProfile(DashboardView view) throws MalformedURLException, IOException{
        if (!model.getUserData().isNull("image")) {
                if (model.getUserData().getString("image").equals("")) {
                    BufferedImage Img = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/ProfileIconBlack.png"));
                    BufferedImage Images = makeRoundedCorner(Img, 1000);
                    view.getBtnProfile().setIcon(new ImageIcon(Images));
                    view.getBtnProfile().setBackground(Color.decode("#1D1C1C"));
                } else {
                    System.out.println("Img url : " + model.getUserData().getString("image"));
                    URL dataImageUrl = new URL(model.getUserData().getString("image").replaceAll(" ", "%20"));
                    
                    BufferedImage Img = ImageIO.read(dataImageUrl);
                    BufferedImage roundedImage = makeRoundedCorner(Img, 7000);
                    
                    Image images = roundedImage.getScaledInstance(55, 55, Image.SCALE_SMOOTH);
                    ImageIcon img = new ImageIcon(images);
                    view.getBtnProfile().setIcon(img);
                    view.getBtnProfile().setBackground(Color.decode("#1D1C1C"));
                }
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
                    model.setOrderHistoryList(null);
                    System.out.println("Refresh success for id " + rowData.getString("user_id"));
                    removeDialogLoading(view);
                    
                }
            }
        } else {
            System.out.println("Opening dashboard, no need to refresh");
        }
    }
    
    public void viewBugReport(DashboardView view) {
        try {
            BugReportView bgView = new BugReportView();
            bgView.getBugReportController().setModel(model);
            String currentEmail = model.getUserData().getString("email");
            bgView.getTxtEmail().setText(currentEmail);
            bgView.setVisible(true);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void hidePopupProfile(DashboardView view) {
        view.getPopupProfile().setVisible(false);
        
    }
    
    public void showPopupProfile(DashboardView view) {
        view.getPopupProfile().show(view, 965, 65);
        view.getPopupProfile().setVisible(true);
    }

    public void logout(DashboardView dashboardView, LoginView loginView) {
        if (JOptionPane.showConfirmDialog(dashboardView, "Apakah anda yakin ingin keluar?", "Cinemas", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dashboardView.dispose();
            loginView.setVisible(true);
        }
    }

}
