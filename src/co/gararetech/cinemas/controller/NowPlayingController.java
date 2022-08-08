package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.utils.ScaleImage;
import co.gararetech.cinemas.view.DashboardView;
import co.gararetech.cinemas.view.elements.RoundedPanel;
import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
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

    public void getNowPlaying() throws ProtocolException, IOException {
        System.out.println("Get API Now Playing .. ");
        String cityId = getCityId(model.getUserData().getString("city_id"));
        URL url = new URL(model.getNowPlayingUrl().toString() + "?city_id=" + cityId);

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
            System.out.println("success get API now playing");
            model.setPlayingList(response.getJSONArray("results"));
        } else {
            System.out.println("failed get API now playing");
            model.setPlayingList(new JSONArray());
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
        System.out.println("Building now playing content .. " + "~" + nextRID);

        // Now Playing Container
        JPanel gridPane = new JPanel(new GridLayout(0, 4));
        gridPane.setBackground(Color.decode("#42382F"));

        // List Data
        JSONArray listData = model.getPlayingList();

        for (int i = 0; i < listData.length(); i++) {
            JSONObject rowData = listData.getJSONObject(i);
            removeLoadingContent(view.getContent(), view.getLoadingPanel());
            view.setLoadingPanel(addLoadingContent(view.getContent(), "GET RESOURCES : " + rowData.getString("title")));
            // Filter jika film belum sepenuhnya rilis jangan ditampilkan
            Boolean isGrid;

            if (rowData.getInt("presale_flag") == 1) {
                isGrid = false;
            } else {
                isGrid = true;
            }

            // Grid panel
            final JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new CardLayout(25, 25));
            contentPanel.setPreferredSize(new Dimension(250, 400));
            //contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            contentPanel.setBackground(Color.decode("#42382F"));

            // Card Panel
            final JPanel cardPanel = new JPanel();
            cardPanel.setBorder(BorderFactory.createEmptyBorder());
            cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
            cardPanel.setSize(150, 300);
            cardPanel.setBackground(Color.decode("#42382F"));

//            // Film Content 
//            // Top Space
//            JLabel topSpace = new JLabel();
//            topSpace.setText("---------");
//            topSpace.setForeground(Color.decode("#222222"));
//            topSpace.setAlignmentX(Component.CENTER_ALIGNMENT);
//            cardPanel.add(topSpace);

            // Poster Image
//            URL posterUrl = new URL(rowData.getString("poster_path"));
            JLabel poster = new JLabel();
            BufferedImage icon = ImageIO.read(new URL(rowData.getString("poster_path")));
            BufferedImage imageCrop = cropImage(icon, 0, icon.getHeight() / 2, icon.getWidth(), 80); //0, 0, 453, 150
            BufferedImage roundedPosterImage = makeRoundedCorner(icon, 70);
            Image scaledPoster = roundedPosterImage.getScaledInstance(250, 360, Image.SCALE_SMOOTH);
            ImageIcon iconPoster = new ImageIcon(scaledPoster);
            poster.setPreferredSize(new Dimension(230, 287));
//            Image icon = ImageIO.read(posterUrl);
            ImageIcon posterIcon;
            if (icon == null) {
                posterIcon = new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/blankposter.png"));
            } else {
                posterIcon = new ImageIcon(icon);
            }
//            ScaleImage scaleImg = new ScaleImage(posterIcon, 230, 287);
//            ImageIcon resizePoster = scaleImg.scaleImage();
            poster.setIcon(iconPoster);
            poster.setAlignmentX(Component.CENTER_ALIGNMENT);
            cardPanel.add(poster);
//
//            // Film Rating Panel
//            JPanel ratingPanel = new JPanel();
//            ratingPanel.setPreferredSize(new Dimension(250, 10));
//            ratingPanel.setLayout(new BoxLayout(ratingPanel, BoxLayout.X_AXIS));
//            ratingPanel.setBackground(Color.decode("#222222"));
//
//            // Top Rating Space
//            JLabel topRatingSpace = new JLabel();
//            topRatingSpace.setText("---------");
//            topRatingSpace.setForeground(Color.decode("#222222"));
//            topRatingSpace.setAlignmentX(Component.CENTER_ALIGNMENT);
//            cardPanel.add(topRatingSpace);
//
//            // Rating Icon
//            JLabel starIcon = new JLabel();
//            URL starIconPath = getClass().getResource("/co/gararetech/cinemas/view/images/star-25.png");
//            ImageIcon starImage = new ImageIcon(starIconPath);
//            starIcon.setIcon(starImage);
//            starIcon.setAlignmentX(Component.LEFT_ALIGNMENT);
//            ratingPanel.add(starIcon);
//
//            // Rating Score
//            JLabel ratingScore = new JLabel();
//            ratingScore.setForeground(Color.WHITE);
//            ratingScore.setText(" " + String.valueOf(rowData.getFloat("rating_score")));
//            ratingScore.setFont(new Font("Serif", Font.PLAIN, 18));
//            ratingScore.setAlignmentX(Component.LEFT_ALIGNMENT);
//            ratingPanel.add(ratingScore);
//
//            // Rating Age
//            JLabel ageScore = new JLabel();
//            String ageCategory = rowData.getString("age_category");
//            if (ageCategory.equals("R")) {
//                ageScore.setForeground(Color.GREEN);
//                ageCategory = "R 13+";
//            } else if (ageCategory.equals("D")) {
//                ageScore.setForeground(Color.RED);
//                ageCategory = "D 17+";
//            } else {
//                ageScore.setForeground(Color.WHITE);
//            }
//            ageScore.setText("                      " + ageCategory);
//            ageScore.setFont(new Font("Serif", Font.PLAIN, 18));
//            ageScore.setAlignmentX(Component.RIGHT_ALIGNMENT);
//            ratingPanel.add(ageScore);
//
//            // Rating Space
//            JLabel ratingSpace = new JLabel();
//            ratingSpace.setText("-----");
//            ratingSpace.setForeground(Color.decode("#222222"));
//            ratingSpace.setAlignmentX(Component.CENTER_ALIGNMENT);
//            ratingPanel.add(ratingSpace);
//
//            cardPanel.add(ratingPanel);
//
//            // Top Film Space
//            JLabel topFilmSpace = new JLabel();
//            topFilmSpace.setText("---------");
//            topFilmSpace.setForeground(Color.decode("#222222"));
//            topFilmSpace.setAlignmentX(Component.CENTER_ALIGNMENT);
//            cardPanel.add(topFilmSpace);
//
//            // Film Title
//            JLabel filmTitle = new JLabel();
//            filmTitle.setPreferredSize(new Dimension(230, 10));
//            filmTitle.setMaximumSize(new Dimension(220, 30));
//            filmTitle.setHorizontalAlignment(SwingConstants.CENTER);
//            filmTitle.setText(rowData.getString("title"));
//            filmTitle.setForeground(Color.WHITE);
//            filmTitle.setFont(new Font("Serif", Font.PLAIN, 20));
//            filmTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
//            cardPanel.add(filmTitle);
//
//            // Top Button Space
//            JLabel topButtonSpace = new JLabel();
//            topButtonSpace.setText("---------");
//            topButtonSpace.setForeground(Color.decode("#222222"));
//            topButtonSpace.setAlignmentX(Component.CENTER_ALIGNMENT);
//            cardPanel.add(topButtonSpace);
//
//            // Detail Button
//            JButton detailButton = new JButton();
//            detailButton.setForeground(Color.WHITE);
//            detailButton.setBackground(Color.decode("#555553"));
//            detailButton.setText("Detail Film");
//            detailButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    detailFilmView(view, rowData);
//                }
//            });
//            detailButton.setFont(new Font("Serif", Font.PLAIN, 18));
//            detailButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//            detailButton.setPreferredSize(new Dimension(200, 30));
//            detailButton.setMaximumSize(new Dimension(200, 30));
//            cardPanel.add(detailButton);
//
//            // Top Button Space
//            JLabel topButtonSpace2 = new JLabel();
//            topButtonSpace2.setText("---------");
//            topButtonSpace2.setForeground(Color.decode("#222222"));
//            topButtonSpace.setAlignmentX(Component.CENTER_ALIGNMENT);
//            cardPanel.add(topButtonSpace2);
//
//            // Order Button
//            JButton orderButton = new JButton();
//            orderButton.setForeground(Color.WHITE);
//            orderButton.setBackground(Color.decode("#A27B5C"));
//            orderButton.setText("Beli Tiket");
//            orderButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    checkoutTicketView(view, rowData);
//                }
//            });
//            orderButton.setFont(new Font("Serif", Font.PLAIN, 18));
//            orderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//            orderButton.setPreferredSize(new Dimension(200, 30));
//            orderButton.setMaximumSize(new Dimension(200, 30));
//            cardPanel.add(orderButton);
//
            if (isGrid) {
                contentPanel.add(cardPanel);
                gridPane.add(contentPanel);
            }

        }

        if (model.getRequestID() == nextRID) {
            view.getContent().add(gridPane);
            System.out.println("Success load now playing");
        } else {
            System.out.println("Cancel load now playing");
        }

    }

    public void detailFilmView(DashboardView view, JSONObject rowData) {
        DetailFilmController detailFilmController = new DetailFilmController();
        new Thread() {
            public void run() {
                try {
                    detailFilmController.showDetail(view, rowData);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(view, "Kesalahan sistem " + e.getMessage());
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void checkoutTicketView(DashboardView view, JSONObject rowData) {
        view.setLoadingUser(addDialogLoading(view, "Sedang diproses, mohon tunggu sebentar"));
        CheckoutTicketController checkoutTicketController = new CheckoutTicketController();
        new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                try {
                    checkoutTicketController.setDashboardModel(model);
                    checkoutTicketController.setDashboardView(view);
                    checkoutTicketController.showFrame(rowData);
                    removeDialogLoading(view);
                    view.setVisible(false);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(view, "Kesalahan sistem " + e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public void setNewGrid(DashboardView view) throws IOException {
        if (model.getPlayingList() == null) {
            this.getNowPlaying();
        }
        this.setGrid(view);
    }

    public void loading(JButton button, Boolean status) {
        if (status) {
            button.setIcon(new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/loading-25.gif")));
        } else {
            button.setIcon(null);
        }
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

    public JDialog addDialogLoading(DashboardView view, String message) {
        JDialog frame = new JDialog();

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
}