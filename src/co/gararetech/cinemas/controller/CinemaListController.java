package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.utils.ScaleImage;
import co.gararetech.cinemas.view.DashboardView;
import co.gararetech.cinemas.view.elements.RoundJButton;
import co.gararetech.cinemas.view.elements.RoundJCBox;
import co.gararetech.cinemas.view.elements.RoundedPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import org.json.JSONArray;
import org.json.JSONObject;

public class CinemaListController {

    private DashboardModel model;

    public DashboardModel getModel() {
        return model;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
    }

    public String getCityId(String cityName) {
        String cityId = "";
        JSONArray cities = model.getCityList();
        for (int i = 0; i < cities.length(); i++) {
            if (cities.getJSONObject(i).getString("name").equals(cityName)) {
                cityId = cities.getJSONObject(i).getString("id");
            }
        }

        return cityId;
    }

    public void getCinemas(String cityName) throws ProtocolException, IOException {
        System.out.println("Get API Cinemas ..");
        String cityId = getCityId(cityName);
        URL url = new URL(model.getCinemaUrl().toString() + "?city_id=" + cityId);

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

        if (response.getBoolean("success")) {
            System.out.println("success get API Cinemas");
            model.setCinemaList(response.getJSONArray("results"));
        } else {
            System.out.println("failed get API Cinemas ");
            model.setCinemaList(new JSONArray());
        }
    }

    public void setGrid(DashboardView view) throws MalformedURLException, IOException {
        System.out.println("Refreshing cinemas content ..");

        // Now Playing Container
        JPanel gridPane = new JPanel();
        gridPane.setLayout(new BoxLayout(gridPane, BoxLayout.PAGE_AXIS)); //new BoxLayout(gridPane, BoxLayout.PAGE_AXIS
        gridPane.setBackground(Color.decode("#42382F"));
        
        
        // List Data
        JSONArray listData = model.getCinemaList();
        if(listData.isEmpty()){
            JLabel posterImage = new JLabel();
            BufferedImage rawPosterImg = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/gadabioskop.png"));
            Image scaledPoster = rawPosterImg.getScaledInstance(550, 290, Image.SCALE_SMOOTH);
            ImageIcon iconPoster = new ImageIcon(scaledPoster);
            posterImage.setHorizontalAlignment(SwingConstants.CENTER);
            posterImage.setIcon(iconPoster);
            
            view.getContent().add(posterImage);
        }else{
            for (int i = 0; i < listData.length(); i++) {
                JSONObject rowData = listData.getJSONObject(i);
                removeLoadingContent(view.getContent(), view.getLoadingPanel());
                view.setLoadingPanel(addLoadingContent(view.getContent(), "GET THEATER : " + rowData.getString("name")));

                // Grid panel
                final JPanel contentPanel = new JPanel();
                contentPanel.setLayout(new CardLayout(50, 10));
                contentPanel.setPreferredSize(new Dimension(view.getContent().getWidth(), 90));
                contentPanel.setMaximumSize(new Dimension(view.getContent().getWidth(), 90));
                contentPanel.setBackground(Color.decode("#42382F"));

                // Card Panel
                final JPanel cardPanel = new RoundedPanel();
                cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
                cardPanel.setMaximumSize(new Dimension(150, 50));
                cardPanel.setBackground(Color.decode("#222222"));

                // Card cinemas content
                RoundJButton cardContent = new RoundJButton();
                cardContent.setLayout(new BorderLayout());
                cardContent.setBackground(Color.decode("#222222"));
                cardContent.setForeground(Color.decode("#222222"));
                cardContent.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        viewTheaterDetail(view, rowData);
                    }
                });

                // Star icon like rating
                JLabel starIcon = new JLabel();
                URL starIconPath = getClass().getResource("/co/gararetech/cinemas/view/images/star-25.png");
                ImageIcon starImage = new ImageIcon(starIconPath);
                starIcon.setIcon(starImage);
                starIcon.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
                starIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
                cardContent.add(starIcon, BorderLayout.WEST);

                // Theater Name
                JLabel theaterTitle = new JLabel();
                theaterTitle.setText(" " + rowData.getString("name"));
                theaterTitle.setFont(new Font("Serif", Font.BOLD, 18));
                theaterTitle.setForeground(Color.WHITE);
                cardContent.add(theaterTitle, BorderLayout.CENTER);

                // Icon >
                JLabel btnIcon = new JLabel();
                btnIcon.setText(">   ");
                btnIcon.setFont(new Font("Serif", Font.BOLD, 18));
                btnIcon.setForeground(Color.WHITE);
                cardContent.add(btnIcon, BorderLayout.EAST);

                cardPanel.add(cardContent);
                contentPanel.add(cardPanel);
                gridPane.add(contentPanel);
            }

        gridPane.add(Box.createVerticalBox());
        view.getContent().add(gridPane);

        System.out.println("Success load cinemas");
        }
    }

    public void viewTheaterDetail(DashboardView view, JSONObject rowData) {
        new Thread() {
            public void run() {
                try {
                    DetailTheaterController detailTheaterController = new DetailTheaterController();
                    detailTheaterController.setModel(model);
                    detailTheaterController.showDetail(view, rowData);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void setNewGrid(DashboardView view) throws IOException {
        String cityId = model.getUserData().getString("city_id");
        this.getCinemas(cityId);
        this.setGrid(view);
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
}
