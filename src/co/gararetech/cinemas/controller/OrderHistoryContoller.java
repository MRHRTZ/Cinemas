/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.view.DashboardView;
import co.gararetech.cinemas.view.elements.RoundedPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
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

    public void deleteOrderHistory(String orderId) throws MalformedURLException, IOException {
        System.out.println("Delete Order History");
        URL url = new URL(model.getDeleteOrderHistoryUrl().toString() + "?order_id=" + orderId);

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

        JSONObject response = new JSONObject(responseContent.toString());
    }

    public void updateOrderHistory(String orderId) throws MalformedURLException, IOException {
        System.out.println("Update Order History");
        URL url = new URL(model.getUpdateOrderHistoryUrl().toString() + "?status=expired&order_id=" + orderId);

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

        JSONObject response = new JSONObject(responseContent.toString());
    }

    public JSONArray getOrderHistory() throws MalformedURLException, IOException {
        System.out.println("Get Order History");
        URL url = new URL(model.getShowOrderHistoryUrl().toString() + "?uid=" + model.getUserData().getString("user_id"));

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
    
    public String getIndoCurrency(String price) {
        BigDecimal valueToFormat = new BigDecimal(price);
        NumberFormat indoFormat = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        String formattedString = indoFormat.format(valueToFormat);

        return formattedString;
    }

    public void setGrid(DashboardView view) throws IOException, ParseException {
        int nextRID = model.nextRequestID();
        System.out.println("Building Order History Content .. " + "~" + nextRID);
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new BoxLayout(gridPanel, BoxLayout.PAGE_AXIS)); //new BoxLayout(gridPane, BoxLayout.PAGE_AXIS
        gridPanel.setBackground(Color.decode("#42382F"));

        JSONArray orderList = getOrderHistory();
        if (orderList.isEmpty()) {
            JLabel posterImage = new JLabel();
            BufferedImage rawPosterImg = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/history kosong.png"));
            Image scaledPoster = rawPosterImg.getScaledInstance(400, 250, Image.SCALE_SMOOTH);
            ImageIcon iconPoster = new ImageIcon(scaledPoster);
            posterImage.setHorizontalAlignment(SwingConstants.CENTER);
            posterImage.setIcon(iconPoster);

            view.getContent().add(posterImage);
        } else {
            for (int i = 0; i < orderList.length(); i++) {
                JSONObject rowData = orderList.getJSONObject(i);
                
                JSONObject movieDetail = getMovieDetail(rowData.getString("movie_id"));

                // Grid panel
                final JPanel contentPanel = new JPanel();
                contentPanel.setLayout(new CardLayout(33, 10));
                contentPanel.setPreferredSize(new Dimension(view.getContent().getWidth(), 380));
                contentPanel.setMaximumSize(new Dimension(view.getContent().getWidth(), 380));
                contentPanel.setBackground(Color.decode("#42382F"));

                // Card Panel
                final JPanel cardPanel = new RoundedPanel();
                cardPanel.setLayout(null);
                cardPanel.setMaximumSize(new Dimension(50, 50));
                cardPanel.setBackground(Color.decode("#222222"));

                // Poster
                JLabel posterImage = new JLabel();
                BufferedImage rawPosterImg = ImageIO.read(new URL(movieDetail.getString("poster")));
                Image scaledPoster = rawPosterImg.getScaledInstance(155, 250, Image.SCALE_SMOOTH);
                ImageIcon iconPoster = new ImageIcon(scaledPoster);
                posterImage.setBounds(40, 35, iconPoster.getIconWidth(), iconPoster.getIconHeight());
                posterImage.setIcon(iconPoster);
                cardPanel.add(posterImage);

                int heightSpace = 38;
                int labelX = 40 + 135 + 40;
                int labelXStudio = 208 + 450 + 208;

                JLabel idTiket = new JLabel();
                idTiket.setText("#" + rowData.getString("order_id"));
                idTiket.setForeground(Color.WHITE);
                idTiket.setFont(new Font("Serif", Font.PLAIN, 35));
                idTiket.setBounds(labelX, heightSpace, 500, 50);
                cardPanel.add(idTiket);

                JLabel judulFilm = new JLabel();
                judulFilm.setText(movieDetail.getString("name"));
                judulFilm.setForeground(Color.WHITE);
                judulFilm.setFont(new Font("Serif", Font.PLAIN, 25));
                judulFilm.setBounds(labelX, heightSpace * 2, 580, 70);
                cardPanel.add(judulFilm);

                JLabel lokasi = new JLabel();
                BufferedImage rawPoster2 = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/mall.png"));
                Image imgLks = rawPoster2.getScaledInstance(40, 30, Image.SCALE_SMOOTH);
                ImageIcon iconLks = new ImageIcon(imgLks);
                lokasi.setIcon(iconLks);
                lokasi.setText("  " + rowData.getString("theater_name"));
                lokasi.setForeground(Color.WHITE);
                lokasi.setFont(new Font("Serif", Font.PLAIN, 15));
                lokasi.setBounds(labelX, heightSpace * 3, 500, 70);
                cardPanel.add(lokasi);

                JLabel kursi = new JLabel();
                BufferedImage rawPoster3 = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/chairIcon.png"));
                Image imgKrs = rawPoster3.getScaledInstance(40, 30, Image.SCALE_SMOOTH);
                ImageIcon iconKrs = new ImageIcon(imgKrs);
                kursi.setIcon(iconKrs);
                kursi.setText("  " + rowData.getString("chair").replaceAll(",", ", "));
                kursi.setForeground(Color.WHITE);
                kursi.setFont(new Font("Serif", Font.PLAIN, 15));
                kursi.setBounds(labelX, heightSpace * 4, 500, 70);
                cardPanel.add(kursi);

                JLabel tanggal = new JLabel();
                BufferedImage rawPoster4 = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/date.png"));
                Image imgtgl = rawPoster4.getScaledInstance(40, 30, Image.SCALE_SMOOTH);
                ImageIcon iconImg = new ImageIcon(imgtgl);
                tanggal.setIcon(iconImg);
                int timestamp = rowData.getInt("show_time");
                Date date = new Date(timestamp * 1000L);
                Locale id = new Locale("in", "ID");
                String pattern = "EEEEE, dd-MM-yyyy HH:mm";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, id);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("273"));
                String time = simpleDateFormat.format(date);
                tanggal.setText("  " + time);
                tanggal.setForeground(Color.WHITE);
                tanggal.setFont(new Font("Serif", Font.PLAIN, 15));
                tanggal.setBounds(labelX, heightSpace * 5, 500, 70);
                cardPanel.add(tanggal);

                JLabel harga = new JLabel();
                BufferedImage rawPoster5 = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/dolar.png"));
                Image imghrg = rawPoster5.getScaledInstance(40, 30, Image.SCALE_SMOOTH);
                ImageIcon iconHrg = new ImageIcon(imghrg);
                String formatHarga = this.getIndoCurrency(rowData.getString("total"));
                harga.setIcon(iconHrg);
                harga.setText("  " + formatHarga);
                harga.setForeground(Color.WHITE);
                harga.setFont(new Font("Serif", Font.PLAIN, 15));
                harga.setBounds(labelX, heightSpace * 6, 500, 70);
                cardPanel.add(harga);

                if (rowData.getString("movie_status").equals("expired")) {
                    JLabel sudahTayang = new JLabel();
                    BufferedImage sudahTayangImg = ImageIO.read(getClass().getResource("/co/gararetech/cinemas/view/images/Sudah Tayang.png"));
                    Image imgSdhTyg = sudahTayangImg.getScaledInstance(275, 200, Image.SCALE_SMOOTH);
                    ImageIcon iconTyg = new ImageIcon(imgSdhTyg);
                    sudahTayang.setIcon(iconTyg);
                    sudahTayang.setBounds(labelXStudio - 28, heightSpace, 500, 250);
                    cardPanel.add(sudahTayang);

                    // delete button
                    JButton deleteButton = new JButton();
                    deleteButton.setForeground(Color.WHITE);
                    deleteButton.setBackground(Color.decode("#86290B"));
                    deleteButton.setText("Hapus");
                    deleteButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (JOptionPane.showConfirmDialog(view, "Apakah anda yakin ingin menghapus ?", "Cinemas",
                                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                new SwingWorker<Void, Void>() {
                                    @Override
                                    public Void doInBackground() throws ParseException {
                                        try {
                                            deleteOrderHistory(rowData.getString("order_id"));
                                            removeContent(view);
                                            setGrid(view);
                                            view.revalidate();
                                        } catch (IOException ex) {
                                            Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        return null;
                                    }
                                }.execute();
                            }
                        }
                    });
                    deleteButton.setFont(new Font("Serif", Font.PLAIN, 18));
                    deleteButton.setBounds(12, (heightSpace * 8), 1110, 30);
                    cardPanel.add(deleteButton);
                } else {
                    //update button
                    JButton updateButton = new JButton();
                    updateButton.setForeground(Color.WHITE);
                    updateButton.setBackground(Color.decode("#83860B"));
                    updateButton.setText("Check In Studio");
                    updateButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (JOptionPane.showConfirmDialog(view, "Pastikan anda sudah berada didalam studio, lanjutkan ?", "Cinemas",
                                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                new SwingWorker<Void, Void>() {
                                    @Override
                                    public Void doInBackground() throws ParseException {
                                        try {
                                            updateOrderHistory(rowData.getString("order_id"));
                                            JOptionPane.showMessageDialog(view, "Selamat menonton !");
                                            removeContent(view);
                                            setGrid(view);
                                            view.revalidate();
                                        } catch (IOException ex) {
                                            Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        return null;
                                    }
                                }.execute();
                            }
                        }
                    });
                    updateButton.setFont(new Font("Serif", Font.PLAIN, 18));
                    updateButton.setBounds(12, (heightSpace * 8), 1110, 30);
                    cardPanel.add(updateButton);
                }

                JLabel studio = new JLabel();
                studio.setText("AULA " + rowData.getString("studio_hall"));
                studio.setForeground(Color.WHITE);
                studio.setFont(new Font("Serif", Font.PLAIN, 50));
                studio.setBounds(labelXStudio, (heightSpace * 4) - 24, 580, 70);
                cardPanel.add(studio);

                contentPanel.add(cardPanel);
                gridPanel.add(contentPanel);

                gridPanel.add(Box.createVerticalBox());
            }
            
            if (model.getRequestID() == nextRID) {
                view.getContent().add(gridPanel);
                System.out.println("Success load Order History");
            } else {
                System.out.println("cancel load Order History");
            }
        }
    }

    public void removeContent(DashboardView view) {
        view.getContent().removeAll();
        view.getContent().revalidate();
    }
}
