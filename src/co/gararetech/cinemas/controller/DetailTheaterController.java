package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.utils.StaticMap;
import co.gararetech.cinemas.view.DashboardView;
import co.gararetech.cinemas.view.elements.RoundedPanel;
import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import org.json.JSONObject;

public class DetailTheaterController {

    private DashboardModel model;

    public DashboardModel getModel() {
        return model;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
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
    
    private static void openURL(URI uri, JDialog detailDialog) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(detailDialog, "Maaf system kamu tidak support untuk membuka link ini.");
        }
    }

    public void showDetail(DashboardView view, JSONObject theaterData) throws IOException {
        System.out.println("Opening Detail Theater : " + theaterData.getString("name"));
        // JDialog untuk modal screen
        JDialog frame = new JDialog(view, theaterData.getString("name"), true);
        frame.setUndecorated(false);

        // MainPanel dengan card margin 50x50
        JPanel mainPanel = new JPanel(new CardLayout(50, 50));
        mainPanel.setBackground(Color.decode("#19181C"));

        // Content Panel
        int contentPanelWidth = 700;
        JPanel contentPanel = new RoundedPanel();
        contentPanel.setBackground(Color.decode("#222222"));
        contentPanel.setLayout(null);

        // Location marker icon
        int markerX = 20;
        int markerY = 20;
        JLabel locationMarker = new JLabel();
        ImageIcon locationMarkerIcon = new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/marker-25.png"));
        locationMarker.setIcon(locationMarkerIcon);
        locationMarker.setBounds(markerX, markerY, locationMarkerIcon.getIconWidth(), locationMarkerIcon.getIconHeight());
        contentPanel.add(locationMarker);

        // Location Text
        int locTextX = markerX + locationMarker.getWidth() + 10;
        int locTextY = markerY;
        JLabel locText = new JLabel(theaterData.getString("name"));
        locText.setFont(new Font("Serif", Font.BOLD, 24));
        locText.setForeground(Color.WHITE);
        locText.setBounds(locTextX, locTextY, contentPanelWidth - locationMarker.getWidth() - 70, 30);
        contentPanel.add(locText);

        // Full address text
        int locAddressTextX = locTextX;
        int locAddressTextY = locTextY + 30;
        JTextArea locAddressText = new JTextArea(theaterData.getString("address").replaceAll("\\r\\n", ", "));
        locAddressText.setFont(new Font("Serif", Font.PLAIN, 15));
        locAddressText.setBackground(Color.decode("#222222"));
        locAddressText.setForeground(Color.WHITE);
        locAddressText.setBounds(locAddressTextX, locAddressTextY, contentPanelWidth - locationMarker.getWidth() - 70, 50);
        locAddressText.setWrapStyleWord(true);
        locAddressText.setLineWrap(true);
        locAddressText.setEditable(false);
        contentPanel.add(locAddressText);

        // Static Maps
        int staticMapX = markerX;
        int staticMapY = locAddressTextX + 60;

        // Get static image map data
        StaticMap staticMapCreate = new StaticMap();
        staticMapCreate.setWidth(610);
        staticMapCreate.setHeight(325);
        staticMapCreate.setQuery(theaterData.getString("name"));
        staticMapCreate.setZoom(15);
        staticMapCreate.setLatitude(theaterData.getFloat("latitude"));
        staticMapCreate.setLongitude(theaterData.getFloat("longitude"));
        BufferedImage imageStaticMap = staticMapCreate.getImage();
        BufferedImage roundedImageMap = makeRoundedCorner(imageStaticMap, 30);
        ImageIcon mapIcon = new ImageIcon(roundedImageMap);

        JLabel staticMap = new JLabel(mapIcon);
        staticMap.setBackground(Color.decode("#222222"));
        staticMap.setBounds(staticMapX, staticMapY, contentPanelWidth - 60, 325); // 610x325
        staticMap.setCursor(new Cursor(Cursor.HAND_CURSOR));
        staticMap.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    URI uri = new URI("https://maps.google.com/maps/search/" + theaterData.getString("name").replaceAll(" ", "+"));
                    openURL(uri, frame);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(DetailTheaterController.class.getName()).log(Level.SEVERE, null, ex);
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
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        contentPanel.add(staticMap);

        mainPanel.add(contentPanel);
        frame.getContentPane().add(mainPanel);

        // Ukuran modal screen
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        System.out.println("Success close theater detail modal");
    }
}
