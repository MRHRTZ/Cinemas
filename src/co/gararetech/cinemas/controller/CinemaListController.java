package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.utils.ScaleImage;
import co.gararetech.cinemas.view.DashboardView;
import co.gararetech.cinemas.view.elements.RoundJButton;
import co.gararetech.cinemas.view.elements.RoundJCity;
import co.gararetech.cinemas.view.elements.RoundedPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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

    public void getCinemas(String cityId) throws ProtocolException, IOException {
        URL url = null;
        
        if (cityId.equals("0")) {
            url = model.getCinemaUrl();
        } else {
            url = new URL(model.getCinemaUrl().toString() + "?city_id=" + cityId);
        }
        
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
        System.out.println("Get API Cinemas ..");
        model.setCinemaList(response.getJSONArray("results"));
    }
    
    public void setGrid(DashboardView view) throws MalformedURLException, IOException {
        System.out.println("Building cinemas content ..");
        
        // Cineamas Container
        JPanel containerPane = new JPanel(new BorderLayout());
        
        // Cinemas Head (Select City)
        JPanel selectCityPane = new JPanel(new BorderLayout());
        selectCityPane.setBackground(Color.decode("#42382F"));
        
        // Select city Card
        final JPanel cityCard = new JPanel();
        cityCard.setLayout(new CardLayout(5, 10));
        cityCard.setBackground(Color.decode("#42382F"));
            
            // Select City
            RoundJCity cityCombobox = new RoundJCity();
            JSONArray cities = model.getCityList();
            for (int i = 0; i < cities.length(); i++) {
                cityCombobox.addItem(cities.getJSONObject(i).getString("name"));
            }
            cityCombobox.setPreferredSize(new Dimension(50, 30));
            selectCityPane.add(cityCombobox);
            
        cityCard.add(selectCityPane);
        containerPane.add(cityCard, BorderLayout.PAGE_START);
        
        // Cinemas Content
        JPanel gridPane = new JPanel(new GridLayout(0, 5));
        gridPane.setBackground(Color.decode("#42382F"));

        // List Data
        JSONArray listData = model.getCinemaList();
        for (int i = 0; i < listData.length(); i++) {
            JSONObject rowData = listData.getJSONObject(i);

            // Grid panel
            final JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new CardLayout(10, 5));
            contentPanel.setPreferredSize(new Dimension(100, 50));
            contentPanel.setBackground(Color.decode("#42382F"));

            // Card Panel
            final JPanel cardPanel = new RoundedPanel();
            cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
            cardPanel.setSize(100, 50);
            cardPanel.setBackground(Color.decode("#222222"));
           
            
            // Card cinemas content
            RoundJButton cardContent = new RoundJButton();
            cardContent.setLayout(new BorderLayout());
            cardContent.setBackground(Color.decode("#222222"));
            cardContent.setForeground(Color.decode("#222222"));
            cardContent.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DetailTheaterController detailTheaterController = new DetailTheaterController();
                    detailTheaterController.setModel(model);
                    detailTheaterController.showDetail(view, rowData.getString("id"));
                }
            });
            
                // Star icon like rating
                JLabel starIcon = new JLabel();
                URL starIconPath = getClass().getResource("../view/images/star-25.png");
                ImageIcon starImage = new ImageIcon(starIconPath);
                starIcon.setIcon(starImage);
                starIcon.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
                starIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
                cardContent.add(starIcon, BorderLayout.WEST);
                
                // Theater Name
                JLabel theaterTitle = new JLabel();
                theaterTitle.setText(" " + rowData.getString("name"));
                theaterTitle.setFont(new Font("Serif", Font.BOLD, 12));
                theaterTitle.setForeground(Color.WHITE);
                cardContent.add(theaterTitle, BorderLayout.CENTER);
                

            cardPanel.add(cardContent);
            contentPanel.add(cardPanel);
            gridPane.add(contentPanel);
            
            containerPane.add(gridPane, BorderLayout.CENTER);

        }

        view.getContent().add(containerPane);

        System.out.println("Success load cinemas ..");
    }
    
    public void setNewGrid(DashboardView view) throws IOException {
        String cityId = model.getUserData().getString("city_id");
        System.out.println("city id : " + cityId);
        this.getCinemas(cityId);
        this.setGrid(view);
    }
}
