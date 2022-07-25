package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.utils.ScaleImage;
import co.gararetech.cinemas.view.elements.RoundedPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.json.JSONArray;
import org.json.JSONObject;

public class NowPlayingController {
    private DashboardModel model;

    public DashboardModel getModel() {
        return model;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
    }
    
    public void getNowPlaying() throws ProtocolException, IOException {
        URL url = model.getNowPlayingUrl();

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
        model.setPlayingList(response.getJSONArray("results"));
    }

    public void setGrid(JPanel content) throws MalformedURLException, IOException {
        
        // Now Playing Container
        JPanel gridPane = new JPanel(new GridLayout(0, 3));
        gridPane.setBackground(Color.decode("#42382F"));

        // List Data
        JSONArray listData = model.getPlayingList();
        for (int i = 0; i < listData.length(); i++) {
            JSONObject rowData = listData.getJSONObject(i);
            
            // Grid panel
            final JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new CardLayout(50, 50));
            contentPanel.setPreferredSize(new Dimension(250, 600));
            contentPanel.setBackground(Color.decode("#42382F"));

            // Card Panel
            final JPanel cardPanel = new RoundedPanel();
            cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
            cardPanel.setSize(150, 300);
            cardPanel.setBackground(Color.decode("#222222"));

            // Film Content 

                // Top Space
                JLabel topSpace = new JLabel();
                topSpace.setText("---------");
                topSpace.setForeground(Color.decode("#222222"));
                topSpace.setAlignmentX(Component.CENTER_ALIGNMENT);
                cardPanel.add(topSpace);

                // Poster Image
                URL posterUrl = new URL(rowData.getString("poster_path"));
                JLabel poster = new JLabel();
                poster.setPreferredSize(new Dimension(230, 287));
                Image icon = ImageIO.read(posterUrl);
                ImageIcon posterIcon = new ImageIcon(icon);
                ScaleImage scaleImg = new ScaleImage(posterIcon, 230, 287);
                ImageIcon resizePoster = scaleImg.scaleImage();
                poster.setIcon(resizePoster);
                poster.setAlignmentX(Component.CENTER_ALIGNMENT);
                cardPanel.add(poster);

                // Film Rating Panel
                JPanel ratingPanel = new JPanel();
                ratingPanel.setPreferredSize(new Dimension(250, 10));
                ratingPanel.setLayout(new BoxLayout(ratingPanel, BoxLayout.X_AXIS));
                ratingPanel.setBackground(Color.decode("#222222"));
                
                // Top Rating Space
                JLabel topRatingSpace = new JLabel();
                topRatingSpace.setText("---------");
                topRatingSpace.setForeground(Color.decode("#222222"));
                topRatingSpace.setAlignmentX(Component.CENTER_ALIGNMENT);
                cardPanel.add(topRatingSpace);

                    // Rating Icon
                    JLabel starIcon = new JLabel();
                    URL starIconPath = getClass().getResource("../view/images/star-25.png");
                    ImageIcon starImage = new ImageIcon(starIconPath);
                    starIcon.setIcon(starImage);
                    starIcon.setAlignmentX(Component.LEFT_ALIGNMENT);
                    ratingPanel.add(starIcon);

                    // Rating Score & Age
                    JLabel ratingScore = new JLabel();
                    ratingScore.setForeground(Color.WHITE);
                    ratingScore.setText("  " + String.valueOf(rowData.getFloat("rating_score") + "                    " + rowData.getString("age_category")));
                    ratingScore.setFont(new Font("Serif", Font.PLAIN, 18));
                    ratingScore.setAlignmentX(Component.LEFT_ALIGNMENT);
                    ratingPanel.add(ratingScore);
                    
                    // Rating Space
                    JLabel ratingSpace = new JLabel();
                    ratingSpace.setText("-----");
                    ratingSpace.setForeground(Color.decode("#222222"));
                    ratingSpace.setAlignmentX(Component.CENTER_ALIGNMENT);
                    ratingPanel.add(ratingSpace);
                    
                cardPanel.add(ratingPanel);
                
                // Top Film Space
                JLabel topFilmSpace = new JLabel();
                topFilmSpace.setText("---------");
                topFilmSpace.setForeground(Color.decode("#222222"));
                topFilmSpace.setAlignmentX(Component.CENTER_ALIGNMENT);
                cardPanel.add(topFilmSpace);
                
                // Film Title
                JLabel filmTitle = new JLabel();
                filmTitle.setPreferredSize(new Dimension(230, 10));
                filmTitle.setMaximumSize(new Dimension(220, 30));
                filmTitle.setHorizontalAlignment(SwingConstants.CENTER);
                filmTitle.setText(rowData.getString("title"));
                filmTitle.setForeground(Color.WHITE);
                filmTitle.setFont(new Font("Serif", Font.PLAIN, 20));
                filmTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
                cardPanel.add(filmTitle);
                
                // Top Button Space
                JLabel topButtonSpace = new JLabel();
                topButtonSpace.setText("---------");
                topButtonSpace.setForeground(Color.decode("#222222"));
                topButtonSpace.setAlignmentX(Component.CENTER_ALIGNMENT);
                cardPanel.add(topButtonSpace);
                
                // Detail Button
                JButton detailButton = new JButton();
                detailButton.setForeground(Color.WHITE);
                detailButton.setBackground(Color.decode("#555553"));
                detailButton.setText("Detail Film");
                detailButton.setName("detail-" + i);
                detailButton.setFont(new Font("Serif", Font.PLAIN, 18));
                detailButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                detailButton.setPreferredSize(new Dimension(200, 30));
                detailButton.setMaximumSize(new Dimension(200, 30));
                cardPanel.add(detailButton);
                
                // Top Button Space
                JLabel topButtonSpace2 = new JLabel();
                topButtonSpace2.setText("---------");
                topButtonSpace2.setForeground(Color.decode("#222222"));
                topButtonSpace.setAlignmentX(Component.CENTER_ALIGNMENT);
                cardPanel.add(topButtonSpace2);
                
                // Order Button
                JButton orderButton = new JButton();
                orderButton.setForeground(Color.WHITE);
                orderButton.setBackground(Color.decode("#A27B5C"));
                orderButton.setText("Beli Tiket");
                orderButton.setName("order-" + i);
                orderButton.setFont(new Font("Serif", Font.PLAIN, 18));
                orderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                orderButton.setPreferredSize(new Dimension(200, 30));
                orderButton.setMaximumSize(new Dimension(200, 30));
                cardPanel.add(orderButton);

            
            contentPanel.add(cardPanel);
            gridPane.add(contentPanel);
        }
        content.add(gridPane);
    }
    
    public void setNewGrid(JPanel content) throws IOException {
        this.getNowPlaying();
        this.setGrid(content);
    }
}
