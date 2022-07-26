package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.utils.ScaleImage;
import co.gararetech.cinemas.view.DashboardView;
import co.gararetech.cinemas.view.elements.RoundedPanel;
import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.json.JSONArray;
import org.json.JSONObject;

public class UpcomingController {

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

    public void getUpcoming() throws ProtocolException, IOException {
        System.out.println("Get API Now Upcoming ..");
        String cityId = getCityId(model.getUserData().getString("city_id"));
        URL url = new URL(model.getUpcomingUrl().toString() + "?city_id=" + cityId);

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
            System.out.println("success get API upcoming");
            model.setUpcomingList(response.getJSONArray("results"));
        } else {
            System.out.println("failed get API upcoming");
            model.setUpcomingList(new JSONArray());
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

    public BufferedImage cropImage(BufferedImage bufferedImage, int x, int y, int width, int height) {
        BufferedImage croppedImage = bufferedImage.getSubimage(x, y, width, height);
        return croppedImage;
    }

    public void setGrid(DashboardView view) throws MalformedURLException, IOException {
        view.getDashboardController().removeLoadingContent(view.getContent(), view.getLoadingPanel());
        int nextRID = model.nextRequestID();
        System.out.println("Building upcoming content .. " + "~" + nextRID);

        // Now Playing Container
        JPanel gridPane = new JPanel(new GridLayout(0, 4));
        gridPane.setBackground(Color.decode("#42382F"));

        // List Data
        JSONArray listData = model.getUpcomingList();
        if (listData.isEmpty()) {
            System.out.println("Uji");
            JLabel posterImage = new JLabel();
            BufferedImage rawPosterImg = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/upcoming.png"));
            Image scaledPoster = rawPosterImg.getScaledInstance(400, 250, Image.SCALE_SMOOTH);
            ImageIcon iconPoster = new ImageIcon(scaledPoster);
            posterImage.setHorizontalAlignment(SwingConstants.CENTER);
            posterImage.setIcon(iconPoster);

            view.getContent().add(posterImage);
        } else {
            for (int i = 0; i < listData.length(); i++) {
                JSONObject rowData = listData.getJSONObject(i);
                removeLoadingContent(view.getContent(), view.getLoadingPanel());
                view.setLoadingPanel(addLoadingContent(view.getContent(), "GET RESOURCES : " + rowData.getString("title")));

                // Grid panel
                final JPanel contentPanel = new JPanel();
                contentPanel.setLayout(new CardLayout(25, 10));
                contentPanel.setPreferredSize(new Dimension(250, 400));
                contentPanel.setBackground(Color.decode("#42382F"));

                // Card Panel
                final JPanel cardPanel = new JPanel();
                cardPanel.setBorder(BorderFactory.createEmptyBorder());
                cardPanel.setLayout(null);
                cardPanel.setSize(150, 250);
                cardPanel.setBackground(Color.decode("#42382F"));

                JLabel poster = new JLabel();
                BufferedImage imageBuffer = null;
                try {
                    imageBuffer = ImageIO.read(new URL(rowData.getString("poster_path")));
                    if (imageBuffer == null) {
                        imageBuffer = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/blankposter.png"));
                    } else {
                    }
                } catch (IIOException e) {
                    imageBuffer = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/blankposter.png"));
                }
                BufferedImage roundedPosterImage = makeRoundedCorner(imageBuffer, 70);
                Image scaledPoster = roundedPosterImage.getScaledInstance(250, 360, Image.SCALE_SMOOTH);
                ImageIcon iconPoster = new ImageIcon(scaledPoster);
                poster.setPreferredSize(new Dimension(230, 287));

                poster.setIcon(iconPoster);
                poster.setBounds(0, 0, 250, 360);

                // Rating Film
                JLabel ratingFilm = new JLabel();
                ImageIcon ratingIcon = getRatingIcon(rowData.getDouble("rating_score"));
                ratingFilm.setIcon(ratingIcon);
                ratingFilm.setHorizontalAlignment(SwingConstants.CENTER);
                ratingFilm.setBounds(0, poster.getHeight() - 80, poster.getWidth(), 11);
                cardPanel.add(ratingFilm);
                ratingFilm.setVisible(false);
                ratingFilm.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Title
                JLabel titleLabel = new JLabel(rowData.getString("title"));
                titleLabel.setForeground(Color.WHITE);
                titleLabel.setFont(new Font("Serif", Font.PLAIN, 18));
                titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                titleLabel.setBounds(0, ratingFilm.getY() + 10, poster.getWidth(), 30);
                cardPanel.add(titleLabel);
                titleLabel.setVisible(false);
                titleLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Selengkapnya
                JLabel selengkapnya = new JLabel("<html><u>Lihat Selengkapnya</u></html>");
                selengkapnya.setForeground(Color.WHITE);
                selengkapnya.setFont(new Font("Serif", Font.PLAIN, 12));
                selengkapnya.setHorizontalAlignment(SwingConstants.CENTER);
                selengkapnya.setBounds(0, titleLabel.getY() + 25, poster.getWidth(), 20);
                cardPanel.add(selengkapnya);
                selengkapnya.setVisible(false);
                selengkapnya.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Gradient Label
                JLabel labelGradient = new JLabel();
                labelGradient.setIcon(new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/gradientHover.png")));
                labelGradient.setBounds(-5, poster.getHeight() - 118, 255, 128);
                cardPanel.add(labelGradient);
                labelGradient.setVisible(false);
                labelGradient.setCursor(new Cursor(Cursor.HAND_CURSOR));

                poster.setCursor(new Cursor(Cursor.HAND_CURSOR));
                poster.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        DetailFilmController detailFilmController = new DetailFilmController();
                        try {
                            detailFilmController.showDetail(view, rowData);
                        } catch (IOException ex) {
                            Logger.getLogger(UpcomingController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        onMouseEntered(e, labelGradient, cardPanel, ratingFilm, titleLabel, selengkapnya);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        onMouseExited(e, labelGradient, cardPanel, ratingFilm, titleLabel, selengkapnya);
                    }
                });

                cardPanel.add(poster);

                contentPanel.add(cardPanel);
                gridPane.add(contentPanel);

            }

            if (model.getRequestID() == nextRID) {
                view.getContent().add(gridPane);
                System.out.println("Success load upcoming");
            } else {
                System.out.println("cancel load upcoming");
            }
        }
    }

    public ImageIcon getRatingIcon(double scoreDouble) {
        ImageIcon icon = null;
        int score = (int) scoreDouble;
        System.out.println("Score : " + score);
        if (score == 0) {
            System.out.println("Bintang 0");
            icon = new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/star-0.png"));
        } else if (score > 0 && score < 2) {
            System.out.println("Bintang 1");
            icon = new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/star-1.png"));
        } else if (score >= 2 && score < 4) {
            System.out.println("Bintang 2");
            icon = new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/star-2.png"));
        } else if (score >= 4 && score < 6) {
            System.out.println("Bintang 3");
            icon = new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/star-3.png"));
        } else if (score >= 6 && score <= 8) {
            System.out.println("Bintang 4");
            icon = new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/star-4.png"));
        } else if (score > 8) {
            System.out.println("Bintang 5");
            icon = new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/star-5.png"));
        }
        return icon;
    }

    public void onMouseEntered(MouseEvent e, JLabel info, JPanel parent, JLabel ratingFilm, JLabel titleLabel, JLabel selengkapnya) {
        titleLabel.setVisible(true);
        selengkapnya.setVisible(true);
        ratingFilm.setVisible(true);
        info.setVisible(true);
        parent.revalidate();
    }

    public void onMouseExited(MouseEvent e, JLabel info, JPanel parent, JLabel ratingFilm, JLabel titleLabel, JLabel selengkapnya) {
        titleLabel.setVisible(false);
        selengkapnya.setVisible(false);
        ratingFilm.setVisible(false);
        info.setVisible(false);
        parent.revalidate();
    }

    public void setNewGrid(DashboardView view) throws IOException {
        if (model.getUpcomingList() == null) {
            this.getUpcoming();
        }
        this.setGrid(view);
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
