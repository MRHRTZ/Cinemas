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
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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
    
    public JSONObject getMovieDetail(String movieId) throws ProtocolException, IOException {
        System.out.println("Get Movie Detail : " + movieId);

        URL url = new URL(model.getMovieDetailUrl().toString() + movieId);

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
        JSONObject result = null;

        if (response.getBoolean("success")) {
            System.out.println("Success get movie");
            result = response.getJSONObject("results");
        } else {
            System.out.println("Failed get movie : " + response.toString());
        }

        return result;
    }
    
    public JSONArray getOrderHistory() throws MalformedURLException, IOException {
        System.out.println("Get Order History");
        URL url = new URL(model.getOrderHistoryUrl().toString() + "?uid=" + model.getUserData().getString("user_id"));

        model.setConnection((HttpURLConnection) url.openConnection());
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
    
    public void setGrid(DashboardView view)throws IOException{
        
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new BoxLayout(gridPanel, BoxLayout.PAGE_AXIS)); //new BoxLayout(gridPane, BoxLayout.PAGE_AXIS
        gridPanel.setBackground(Color.decode("#42382F"));
        System.out.println("Masuk set Grid");
        
        JSONArray orderList = getOrderHistory();
        if(orderList.isEmpty()){
            System.out.println("Uji");
            JLabel posterImage = new JLabel();
            BufferedImage rawPosterImg = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/history kosong.png"));
            Image scaledPoster = rawPosterImg.getScaledInstance(400, 250, Image.SCALE_SMOOTH);
            ImageIcon iconPoster = new ImageIcon(scaledPoster);
            posterImage.setHorizontalAlignment(SwingConstants.CENTER);
            posterImage.setIcon(iconPoster);
            
            view.getContent().add(posterImage);
        }else{
            
            for (int i = 0; i < orderList.length(); i++) {
                JSONObject rowData = orderList.getJSONObject(i);
                JSONObject movieDetail = getMovieDetail(rowData.getString("movie_id"));

                String sutradara = movieDetail.getString("director");
                System.out.println("sutradara : " + sutradara);


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
                BufferedImage rawPosterImg = ImageIO.read(new URL(movieDetail.getString("poster")));
                Image scaledPoster = rawPosterImg.getScaledInstance(155, 250, Image.SCALE_SMOOTH);
                ImageIcon iconPoster = new ImageIcon(scaledPoster);
                posterImage.setBounds(40,35, iconPoster.getIconWidth(), iconPoster.getIconHeight());
                posterImage.setIcon(iconPoster);
                cardPanel.add(posterImage);
                
                int heightSpace = 38;
                int labelX = 40 + 135 + 40;
                int labelXStudio = 208 + 450 + 208;
                
                JLabel idTiket = new JLabel();
                idTiket.setText("#"+rowData.getString("order_id"));
                idTiket.setForeground(Color.WHITE);
                idTiket.setFont(new Font("Serif", Font.PLAIN, 35));
                idTiket.setBounds(labelX, heightSpace, 500, 50);
                cardPanel.add(idTiket);
                
                JLabel judulFilm = new JLabel();
                judulFilm.setText(movieDetail.getString("name"));
                judulFilm.setForeground(Color.WHITE);
                judulFilm.setFont(new Font("Serif", Font.PLAIN, 25));
                judulFilm.setBounds(labelX, heightSpace*2, 580, 70);
                cardPanel.add(judulFilm);
                
                JLabel lokasi = new JLabel();
                BufferedImage rawPoster2 = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/play-52.png"));
                Image imgLks = rawPoster2.getScaledInstance(20,30, Image.SCALE_SMOOTH);
                ImageIcon iconLks = new ImageIcon(imgLks);
                lokasi.setIcon(iconLks);
                lokasi.setText("  "+rowData.getString("theater_name"));
                lokasi.setForeground(Color.WHITE);
                lokasi.setFont(new Font("Serif", Font.PLAIN, 15));
                lokasi.setBounds(labelX, heightSpace*3, 500, 70);
                cardPanel.add(lokasi);
                
                JLabel kursi = new JLabel();
                BufferedImage rawPoster3 = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/play-52.png"));
                Image imgKrs = rawPoster3.getScaledInstance(20,30, Image.SCALE_SMOOTH);
                ImageIcon iconKrs = new ImageIcon(imgKrs);
                kursi.setIcon(iconKrs);
                kursi.setText("  "+rowData.getString("chair"));
                kursi.setForeground(Color.WHITE);
                kursi.setFont(new Font("Serif", Font.PLAIN, 15));
                kursi.setBounds(labelX, heightSpace*4, 500, 70);
                cardPanel.add(kursi);
                
                JLabel tanggal = new JLabel();
                BufferedImage rawPoster4 = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/play-52.png"));
                Image imgtgl = rawPoster4.getScaledInstance(20,30, Image.SCALE_SMOOTH);
                ImageIcon iconImg = new ImageIcon(imgtgl);
                tanggal.setIcon(iconImg);
                tanggal.setText("  "+rowData.getString("updated_at"));
                tanggal.setForeground(Color.WHITE);
                tanggal.setFont(new Font("Serif", Font.PLAIN, 15));
                tanggal.setBounds(labelX, heightSpace*5, 500, 70);
                cardPanel.add(tanggal);
                
                JLabel harga = new JLabel();
                BufferedImage rawPoster5 = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/play-52.png"));
                Image imghrg = rawPoster5.getScaledInstance(20,30, Image.SCALE_SMOOTH);
                ImageIcon iconHrg = new ImageIcon(imghrg);
                harga.setIcon(iconHrg);
                harga.setText("  Rp."+rowData.getString("total"));
                harga.setForeground(Color.WHITE);
                harga.setFont(new Font("Serif", Font.PLAIN, 15));
                harga.setBounds(labelX, heightSpace*6, 500, 70);
                cardPanel.add(harga);
                
                if(rowData.getString("movie_status").equals("active")){
                    JLabel sudahTayang = new JLabel();
                    BufferedImage sudahTayangImg = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/Sudah Tayang.png"));
                    Image imgSdhTyg = sudahTayangImg.getScaledInstance(220,200, Image.SCALE_SMOOTH);
                    ImageIcon iconTyg= new ImageIcon(imgSdhTyg);
                    sudahTayang.setIcon(iconTyg);
                    sudahTayang.setBounds(labelXStudio, heightSpace, 500, 250);
                    cardPanel.add(sudahTayang);
                }
                
                JLabel studio= new JLabel();
                studio.setText("AULA "+rowData.getString("studio_hall"));
                studio.setForeground(Color.WHITE);
                studio.setFont(new Font("Serif", Font.PLAIN, 50));
                studio.setBounds(labelXStudio, (heightSpace*4)-24, 580, 70);
                cardPanel.add(studio);
                
                contentPanel.add(cardPanel);
                gridPanel.add(contentPanel);

                gridPanel.add(Box.createVerticalBox());
                view.getContent().add(gridPanel);

            System.out.println("Success Load Grid");
        } 
        }
    }
}
