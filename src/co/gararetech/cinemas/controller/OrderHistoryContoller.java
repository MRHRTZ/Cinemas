/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.view.DashboardView;
import co.gararetech.cinemas.view.elements.RoundJButton;
import co.gararetech.cinemas.view.elements.RoundedPanel;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author user
 */
public class OrderHistoryContoller {
    private DashboardModel model;

    public DashboardModel getModel() {
        return model;
    }
    
    public void setModel(DashboardModel model) {
        this.model = model;
    }
    public void setgrid(DashboardView view) throws IOException{
        this.getOrderHistory(view);
//        view.revalidate();
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
    
    public void getOrderHistory(DashboardView view)throws IOException{
        
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new BoxLayout(gridPanel, BoxLayout.PAGE_AXIS)); //new BoxLayout(gridPane, BoxLayout.PAGE_AXIS
        gridPanel.setBackground(Color.decode("#42382F"));
        
            // Grid panel
            final JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new CardLayout(20, 10));
            contentPanel.setPreferredSize(new Dimension(view.getContent().getWidth(), 350));
            contentPanel.setMaximumSize(new Dimension(view.getContent().getWidth(), 350));
            contentPanel.setBackground(Color.decode("#42382F"));

            // Card Panel
            final JPanel cardPanel = new RoundedPanel();
            cardPanel.setLayout(null);
            cardPanel.setMaximumSize(new Dimension(50, 50));
            cardPanel.setBackground(Color.decode("#222222"));
           
      
//         // Poster
            JLabel posterImage = new JLabel();
            BufferedImage rawPosterImg = ImageIO.read(new URL("https://asset.tix.id/movie/3459e29c312117a2a8205e70113c4189.jpeg"));
            BufferedImage roundedPosterImage = makeRoundedCorner(rawPosterImg, 10);
            Image scaledPoster = roundedPosterImage.getScaledInstance(175, 250, Image.SCALE_SMOOTH);
            ImageIcon iconPoster = new ImageIcon(scaledPoster);
            posterImage.setBounds(25,30, iconPoster.getIconWidth(), iconPoster.getIconHeight());
            posterImage.setIcon(iconPoster);
            
            cardPanel.add(posterImage);
            contentPanel.add(cardPanel);
            gridPanel.add(contentPanel);
        
            gridPanel.add(Box.createVerticalBox());
            view.getContent().add(gridPanel);

        System.out.println("Success Load Grid");
         
    }
}
