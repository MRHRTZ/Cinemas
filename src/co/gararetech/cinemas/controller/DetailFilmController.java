package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.utils.ScaleImage;
import co.gararetech.cinemas.view.DashboardView;
import co.gararetech.cinemas.view.elements.RoundJLabel;
import co.gararetech.cinemas.view.elements.RoundedPanel;
import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import org.json.JSONArray;
import org.json.JSONObject;

public class DetailFilmController {

    public DetailFilmController() {

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

    public void showDetail(DashboardView view, JSONObject movieObject) throws MalformedURLException, IOException {
        System.out.println("Building Detail Film : " + movieObject.getString("title") + " ..");

        // JDialog untuk modal screen
        JDialog frame = new JDialog(view, movieObject.getString("title"), true);

        // MainPanel dengan card margin 50x50
        JPanel mainPanel = new JPanel(new CardLayout(50, 50));
        mainPanel.setBackground(Color.decode("#19181C"));

        // Content Panel
        JPanel contentPanel = new RoundedPanel();
        contentPanel.setBackground(Color.decode("#222222"));
        contentPanel.setLayout(null);

        // Gambar thumb video
        JPanel videoThumbPanel = new RoundedPanel();
        videoThumbPanel.setLayout(null);
        videoThumbPanel.setBounds(0, 0, 580, 150);
        videoThumbPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Play icon video redirect
        JLabel playIconLabel = new JLabel();
        ImageIcon playImage = new ImageIcon(getClass().getResource("../view/images/play-52.png"));
        playIconLabel.setIcon(playImage);
        playIconLabel.setBounds(0, 0, videoThumbPanel.getWidth(), videoThumbPanel.getHeight());
        playIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playIconLabel.setBackground(Color.red);

        // Gambar untuk video redirect
        JLabel videoThumb = new JLabel();
        // Baca data dari URL ke Buffer
        BufferedImage rawImg = ImageIO.read(new URL(movieObject.getString("poster_path")));
        // Crop pada tengah2image 
        BufferedImage imageCrop = cropImage(rawImg, 0, rawImg.getHeight() / 2, rawImg.getWidth(), 80); //0, 0, 453, 150
        // Buat image menjadi rounded
        BufferedImage roundedImage = makeRoundedCorner(imageCrop, 20);
        // Perbesar skala mencapai max panel thumb
        Image scaledThumb = roundedImage.getScaledInstance(videoThumbPanel.getWidth(), videoThumbPanel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageVidThumb = new ImageIcon(scaledThumb);
        videoThumb.setIcon(imageVidThumb);
        videoThumb.setHorizontalAlignment(JLabel.CENTER);
        videoThumb.setBounds(0, 0, videoThumbPanel.getWidth(), videoThumbPanel.getHeight());

        videoThumbPanel.add(playIconLabel);
        videoThumbPanel.add(videoThumb);

        // Poster
        JLabel posterImage = new JLabel();
        BufferedImage rawPosterImg = ImageIO.read(new URL(movieObject.getString("poster_path")));
        BufferedImage roundedPosterImage = makeRoundedCorner(rawPosterImg, 50);
        Image scaledPoster = roundedPosterImage.getScaledInstance(135, 200, Image.SCALE_SMOOTH);
        ImageIcon iconPoster = new ImageIcon(scaledPoster);
        posterImage.setIcon(iconPoster);
        posterImage.setBounds(20, videoThumbPanel.getHeight() - 50, iconPoster.getIconWidth(), iconPoster.getIconHeight());
        contentPanel.add(posterImage);

        // Title
        JLabel titleLabel = new JLabel();
        titleLabel.setText(movieObject.getString("title"));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        int labelX = 20 + 135 + 20;
        titleLabel.setBounds(labelX, videoThumbPanel.getHeight() + 10, videoThumbPanel.getWidth() - labelX, 30);
        contentPanel.add(titleLabel);

        // Film Property
        int heightSpace = 25;

        // Title key
        JLabel genreTitle = new JLabel();
        genreTitle.setText("Genre");
        genreTitle.setForeground(Color.decode("#A19696"));
        genreTitle.setFont(new Font("Serif", Font.PLAIN, 16));
        genreTitle.setBounds(labelX, videoThumbPanel.getHeight() + (heightSpace) * 2, 78, 20);
        contentPanel.add(genreTitle);

        JLabel durationTitle = new JLabel();
        durationTitle.setText("Durasi");
        durationTitle.setForeground(Color.decode("#A19696"));
        durationTitle.setFont(new Font("Serif", Font.PLAIN, 16));
        durationTitle.setBounds(labelX, videoThumbPanel.getHeight() + (heightSpace) * 3, 78, 20);
        contentPanel.add(durationTitle);

        JLabel directorTitle = new JLabel();
        directorTitle.setText("Sutradara");
        directorTitle.setForeground(Color.decode("#A19696"));
        directorTitle.setFont(new Font("Serif", Font.PLAIN, 16));
        directorTitle.setBounds(labelX, videoThumbPanel.getHeight() + (heightSpace) * 4, 78, 20);
        contentPanel.add(directorTitle);

        JLabel ageRatingTitle = new JLabel();
        ageRatingTitle.setText("Rating Usia");
        ageRatingTitle.setForeground(Color.decode("#A19696"));
        ageRatingTitle.setFont(new Font("Serif", Font.PLAIN, 16));
        ageRatingTitle.setBounds(labelX, videoThumbPanel.getHeight() + (heightSpace) * 5, 78, 20);
        contentPanel.add(ageRatingTitle);

        // Value
        int labelValueX = 100;
        JLabel genreValue = new JLabel();
        List<String> genreValueList = new ArrayList<String>();
        JSONArray genreObj = movieObject.getJSONArray("genre_ids");
        for (int i = 0; i < genreObj.length(); i++) {
            genreValueList.add(genreObj.getJSONObject(i).getString("name"));
        }
        genreValue.setText(String.join(", ", genreValueList));
        genreValue.setForeground(Color.WHITE);
        genreValue.setFont(new Font("Serif", Font.PLAIN, 16));
        genreValue.setBounds(labelX + labelValueX, videoThumbPanel.getHeight() + (heightSpace) * 2, videoThumbPanel.getWidth() - labelX - labelValueX, 20);
        contentPanel.add(genreValue);

        JLabel durationValue = new JLabel();
        int totalMinutes = movieObject.getInt("duration");
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        String timeString = timeString = String.format("%d jam %02d menit", hours, minutes);
        durationValue.setText(timeString);
        durationValue.setForeground(Color.WHITE);
        durationValue.setFont(new Font("Serif", Font.PLAIN, 16));
        durationValue.setBounds(labelX + labelValueX, videoThumbPanel.getHeight() + (heightSpace) * 3, videoThumbPanel.getWidth() - labelX - labelValueX, 20);
        contentPanel.add(durationValue);

        JLabel directorValue = new JLabel();
        directorValue.setText(movieObject.getString("director"));
        directorValue.setForeground(Color.WHITE);
        directorValue.setFont(new Font("Serif", Font.PLAIN, 16));
        directorValue.setBounds(labelX + labelValueX, videoThumbPanel.getHeight() + (heightSpace) * 4, videoThumbPanel.getWidth() - labelX - labelValueX, 20);
        contentPanel.add(directorValue);

        JLabel ageRatingValue = new JLabel();
        ageRatingValue.setText(movieObject.getString("age_category"));
        ageRatingValue.setForeground(Color.WHITE);
        ageRatingValue.setFont(new Font("Serif", Font.PLAIN, 16));
        ageRatingValue.setBounds(labelX + labelValueX, videoThumbPanel.getHeight() + (heightSpace) * 5, videoThumbPanel.getWidth() - labelX - labelValueX, 20);
        contentPanel.add(ageRatingValue);

        contentPanel.add(videoThumbPanel);

        mainPanel.add(contentPanel);
        frame.getContentPane().add(mainPanel);

        // Ukuran modal screen
        frame.setPreferredSize(new Dimension(700, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        System.out.println("Success load film detail");
    }
}
