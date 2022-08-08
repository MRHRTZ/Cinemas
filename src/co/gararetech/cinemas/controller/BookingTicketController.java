package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.BookingTicketModel;
import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.view.BookingTicketView;
import co.gararetech.cinemas.view.elements.RoundJLabel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import org.json.JSONArray;
import org.json.JSONObject;
import org.netbeans.lib.awtextra.AbsoluteConstraints;

public class BookingTicketController {

    private BookingTicketModel model;
    private DashboardModel dashboardModel;

    public BookingTicketController() {

    }

    public BookingTicketModel getModel() {
        return model;
    }

    public void setModel(BookingTicketModel model) {
        this.model = model;
    }

    public DashboardModel getDashboardModel() {
        return dashboardModel;
    }

    public void setDashboardModel(DashboardModel dashboardModel) {
        this.dashboardModel = dashboardModel;
    }

    public void createOrder(String orderId, String studioId, String userId, String ticketCount, String seat, String tax, String total, String showTime, String movieId) throws MalformedURLException, IOException {
        URL usersUrl = dashboardModel.getCreateOrderUrl();
        String urlParameters = "order_id=" + orderId
                + "&studio_id=" + studioId
                + "&user_id=" + userId
                + "&total_ticket=" + ticketCount
                + "&seat=" + seat
                + "&tax=" + tax
                + "&total=" + total
                + "&show_time=" + showTime
                + "&movie_id=" + movieId;

        System.out.println("Parameter : " + urlParameters);

        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;

        dashboardModel.setConnection((HttpURLConnection) usersUrl.openConnection());
        dashboardModel.getConnection().setRequestMethod("POST");
        dashboardModel.getConnection().setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        dashboardModel.getConnection().setRequestProperty("charset", "utf-8");
        dashboardModel.getConnection().setRequestProperty("Content-Length", Integer.toString(postDataLength));
        dashboardModel.getConnection().setConnectTimeout(5000);
        dashboardModel.getConnection().setReadTimeout(5000);
        dashboardModel.getConnection().setDoOutput(true);
        dashboardModel.getConnection().setUseCaches(false);
        try ( DataOutputStream wr = new DataOutputStream(dashboardModel.getConnection().getOutputStream())) {
            wr.write(postData);
        }

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        int status = dashboardModel.getConnection().getResponseCode();

        if (status > 299) {
            reader = new BufferedReader(new InputStreamReader(dashboardModel.getConnection().getErrorStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        } else {
            reader = new BufferedReader(new InputStreamReader(dashboardModel.getConnection().getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        }
        System.out.println("create studio result : " + responseContent.toString());
    }

    public void createStudio(String studioId, String studioHall, String theaterName, String studioSeat) throws MalformedURLException, IOException {
        URL usersUrl = dashboardModel.getCreateStudioUrl();
        String urlParameters = "studio_id=" + studioId + "&studio_hall=" + studioHall + "&theater_name=" + theaterName + "&studio_seat=" + studioSeat;
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;

        dashboardModel.setConnection((HttpURLConnection) usersUrl.openConnection());
        dashboardModel.getConnection().setRequestMethod("POST");
        dashboardModel.getConnection().setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        dashboardModel.getConnection().setRequestProperty("charset", "utf-8");
        dashboardModel.getConnection().setRequestProperty("Content-Length", Integer.toString(postDataLength));
        dashboardModel.getConnection().setConnectTimeout(5000);
        dashboardModel.getConnection().setReadTimeout(5000);
        dashboardModel.getConnection().setDoOutput(true);
        dashboardModel.getConnection().setUseCaches(false);
        try ( DataOutputStream wr = new DataOutputStream(dashboardModel.getConnection().getOutputStream())) {
            wr.write(postData);
        }

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        int status = dashboardModel.getConnection().getResponseCode();

        if (status > 299) {
            reader = new BufferedReader(new InputStreamReader(dashboardModel.getConnection().getErrorStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        } else {
            reader = new BufferedReader(new InputStreamReader(dashboardModel.getConnection().getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        }
        System.out.println("create studio result : " + responseContent.toString());
    }

    public void updateStudio(String studioId, String studioSeat) throws MalformedURLException, IOException {
        URL usersUrl = dashboardModel.getUpdateStudioUrl();
        String urlParameters = "studio_id=" + studioId + "&studio_seat=" + studioSeat;
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;

        dashboardModel.setConnection((HttpURLConnection) usersUrl.openConnection());
        dashboardModel.getConnection().setRequestMethod("POST");
        dashboardModel.getConnection().setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        dashboardModel.getConnection().setRequestProperty("charset", "utf-8");
        dashboardModel.getConnection().setRequestProperty("Content-Length", Integer.toString(postDataLength));
        dashboardModel.getConnection().setConnectTimeout(5000);
        dashboardModel.getConnection().setReadTimeout(5000);
        dashboardModel.getConnection().setDoOutput(true);
        dashboardModel.getConnection().setUseCaches(false);
        try ( DataOutputStream wr = new DataOutputStream(dashboardModel.getConnection().getOutputStream())) {
            wr.write(postData);
        }

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        int status = dashboardModel.getConnection().getResponseCode();

        if (status > 299) {
            reader = new BufferedReader(new InputStreamReader(dashboardModel.getConnection().getErrorStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        } else {
            reader = new BufferedReader(new InputStreamReader(dashboardModel.getConnection().getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        }
        System.out.println("update studio result : " + responseContent.toString());
    }

    public JSONArray getStudioById(String id) throws MalformedURLException, IOException {
        URL usersUrl = new URL(dashboardModel.getShowStudioUrl().toString() + "?studio_id=" + id);

        dashboardModel.setConnection((HttpURLConnection) usersUrl.openConnection());
        dashboardModel.getConnection().setRequestMethod("GET");
        dashboardModel.getConnection().setConnectTimeout(5000);
        dashboardModel.getConnection().setReadTimeout(5000);

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        int status = dashboardModel.getConnection().getResponseCode();

        if (status > 299) {
            reader = new BufferedReader(new InputStreamReader(dashboardModel.getConnection().getErrorStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        } else {
            reader = new BufferedReader(new InputStreamReader(dashboardModel.getConnection().getInputStream()));
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

    public void initSeat(BookingTicketView view, JSONObject seatObject) throws IOException {
        model.setHarga(0);
        model.setHargaTiket(seatObject.getInt("price"));
        String studioId = seatObject.getString("movie_id") + seatObject.getString("theater_id") + seatObject.getString("studio_id");
        JSONArray studioData = getStudioById(studioId);
        JSONArray seatOldArr = new JSONArray();
        if (studioData.length() > 0) {
            String seatOld = "[" + studioData.getJSONObject(0).getString("studio_seat") + "]";
            seatOldArr = new JSONArray(seatOld);
        }
        String formatHargaTiket = this.getIndoCurrency(String.valueOf(model.getHargaTiket()));
        view.getTampilHargaTiket().setText(formatHargaTiket);
        view.getTampilHarga().setText("0");
        view.getTampilTax().setText(String.valueOf((int) (model.getPajak() * 100)) + " %");
        int h = 20;
        int w = 80;
        int maxX = 12;
        int maxY = 7;
        int nomorKursi = 3;
        String namaBaris = "";
        for (int ix = 1; ix < maxX; ix++) {
            if (ix != 3 && ix != 9) {
                for (int iy = 0; iy < maxY; iy++) {
                    if (iy == 0) {
                        namaBaris = "G";
                    } else if (iy == 1) {
                        namaBaris = "F";
                    } else if (iy == 2) {
                        namaBaris = "E";
                    } else if (iy == 3) {
                        namaBaris = "D";
                    } else if (iy == 4) {
                        namaBaris = "C";
                    } else if (iy == 5) {
                        namaBaris = "B";
                    } else if (iy == 6) {
                        namaBaris = "A";
                    }
                    //System.out.println(namaBaris + " " + iy + " : " + h + "x" + w);
                    JToggleButton btnKursi = new JToggleButton();
                    if (ix <= 3) {
                        btnKursi.setText(namaBaris + ix);
                        btnKursi.setForeground(Color.WHITE);
                        btnKursi.setFont(new Font("Segue UI", Font.PLAIN, 12));
                        btnKursi.setBackground(Color.RED);
                        btnKursi.addItemListener(new ItemListener() {
                            @Override
                            public void itemStateChanged(ItemEvent e) {
                                onSeatSelected(e, view);
                            }
                        });
                    } else if (ix > 3 && ix != 9) {
                        btnKursi.setText(namaBaris + nomorKursi);
                        btnKursi.setForeground(Color.WHITE);
                        btnKursi.setFont(new Font("Segue UI", Font.PLAIN, 12));
                        btnKursi.setBackground(Color.RED);
                        btnKursi.addItemListener(new ItemListener() {
                            @Override
                            public void itemStateChanged(ItemEvent e) {
                                onSeatSelected(e, view);
                            }
                        });
                    }
                    Boolean isTaken = false;
                    for (int i = 0; i < seatOldArr.length(); i++) {
                        if (seatOldArr.getString(i).equals(namaBaris + ix) && (ix <= 3)) {
                            isTaken = true;
                        } else if (seatOldArr.getString(i).equals(namaBaris + nomorKursi) && (ix > 3) && (ix != 9)) {
                            isTaken = true;
                        }
                    }
                    if (isTaken) {
                        btnKursi.setEnabled(false);
                        btnKursi.setBackground(Color.decode("#999999"));
                        btnKursi.setForeground(Color.BLACK);
                    }
                    view.getPanelKursi().add(btnKursi, new AbsoluteConstraints(h, w, 55, 30));
                    w += 50;
                    view.revalidate();
                }
            }
            h += 60;
            w = 80;
            if (ix > 3 && ix != 9) {
                nomorKursi += 1;
            }
        }
    }

    public void onSeatSelected(ItemEvent e, BookingTicketView view) {
        JToggleButton thisToggleBtn = (JToggleButton) e.getSource();
        String nama = thisToggleBtn.getText();
        System.out.println("Kursi " + nama + " Dipilih");
        int hargaSkrng = model.getHarga();
        if (e.getStateChange() == ItemEvent.SELECTED) {
            thisToggleBtn.setBackground(Color.GREEN);
            thisToggleBtn.setForeground(Color.BLACK);
            model.setHarga(hargaSkrng + model.getHargaTiket());
            model.getKursi().put(nama);
            model.getKursiAnda().put(nama);
        } else if (e.getStateChange() == ItemEvent.DESELECTED) {
            thisToggleBtn.setBackground(Color.RED);
            thisToggleBtn.setForeground(Color.WHITE);
            model.setHarga(hargaSkrng - model.getHargaTiket());
            JSONArray oldSeat = model.getKursi();
            JSONArray removeSeat = removeArrayElement(oldSeat, nama);
            model.setKursi(removeSeat);
            JSONArray oldSeatYou = model.getKursiAnda();
            JSONArray removeSeatYou = removeArrayElement(oldSeatYou, nama);
            model.setKursiAnda(removeSeatYou);
        }
        String formatHarga = this.getIndoCurrency(String.valueOf(model.getHarga()));
        view.getTampilHarga().setText(formatHarga);
        double hargaSetelahPPN = Double.valueOf(model.getHarga()) * model.getPajak();
        double hargaAkhir = Double.valueOf(model.getHarga()) + hargaSetelahPPN;
        model.setTotalHarga((int) hargaAkhir);
        String formatHargaAkhir = this.getIndoCurrency(String.valueOf(hargaAkhir));
        System.out.println("Harga : " + model.getHarga() + " - Harga Tiket : " + model.getHargaTiket() + " - Pajak : " + model.getPajak());
        view.getTampilTotalHarga().setText(formatHargaAkhir);
        String seatSelected = model.getKursi().toString();
        System.out.println("Seat selected : " + seatSelected);
        model.getCheckoutTicketObj().put("seat", model.getKursi());
        System.out.println(model.getCheckoutTicketObj());
        if (model.getKursi().length() == 0) {
            view.getBtnBayar().setEnabled(false);
        } else {
            view.getBtnBayar().setEnabled(true);
        }
        setGridTampilKursi(view, model.getKursi());
    }

    public void setGridTampilKursi(BookingTicketView view, JSONArray seat) {
        JPanel tampilKursiPanel = new JPanel();
        tampilKursiPanel.setLayout(new GridLayout(0, 5));
        tampilKursiPanel.setBackground(Color.decode("#222222"));

        int heightSeat = 0;
        for (int i = 0; i < seat.length(); i++) {
            String seatName = seat.getString(i);

            JPanel seatPanel = new JPanel();
            seatPanel.setBackground(Color.decode("#222222"));

            Dimension seatSize = new Dimension(30, 30);
            JLabel seatLabel = new RoundJLabel();
            seatLabel.setText(seatName);
            seatLabel.setForeground(Color.decode("#3F3030"));
            seatLabel.setBackground(Color.decode("#A27B5C"));
            seatLabel.setPreferredSize(seatSize);
            seatLabel.setHorizontalAlignment(JLabel.CENTER);
            seatPanel.add(seatLabel);

            tampilKursiPanel.add(seatPanel);
        }
        heightSeat += tampilKursiPanel.getPreferredSize().getHeight();

        System.out.println(heightSeat);

        view.getTampilKursi().removeAll();
        view.getTampilKursi().repaint();
        view.getTampilKursi().add(tampilKursiPanel);
        view.getTampilKursi().setPreferredSize(new Dimension(view.getScrollTampilKursi().getWidth() - 10, heightSeat));
        view.getTampilKursi().setMaximumSize(new Dimension(view.getScrollTampilKursi().getWidth() - 10, heightSeat));
        view.revalidate();
    }

    public JSONArray removeArrayElement(JSONArray element, String value) {
        for (int i = 0; i < element.length(); i++) {
            if (element.getString(i).equals(value)) {
                element.remove(i);
            }
        }

        return element;
    }

    public void makeOrder(String studioId) throws IOException {
        String orderId = generateID(2) + "-" + generateID(10);
        createOrder(orderId,
                studioId,
                dashboardModel.getUserData().getString("user_id"),
                String.valueOf(model.getKursiAnda().length()),
                model.getKursiAnda().join(",").replaceAll("\"", ""),
                String.valueOf((int) (model.getPajak() * 100)),
                String.valueOf(model.getTotalHarga()),
                Integer.toString(model.getCheckoutTicketObj().getInt("show_time")),
                model.getCheckoutTicketObj().getString("movie_id")
        );
    }

    public void bookNow(BookingTicketView view) {
        view.getBtnBayar().setEnabled(false);
        view.getBtnBayar().setIcon(new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/loading-25.gif")));

        new Thread() {
            public void run() {
                try {
                    JSONObject checkoutObj = model.getCheckoutTicketObj();
                    String studioHall = generateStudioHall();
                    String studioId = checkoutObj.getString("movie_id") + checkoutObj.getString("theater_id") + checkoutObj.getString("studio_id");
                    String theaterName = checkoutObj.getString("theater_name");
                    String studioSeat = checkoutObj.getJSONArray("seat").join(",");
                    JSONArray studioData = getStudioById(studioId);
                    System.out.println("Studio data : " + studioData);
                    if (studioData.length() > 0) {
                        System.out.println("Update");
                        String seatOld = studioData.getJSONObject(0).getString("studio_seat").replaceAll("\"", "");
                        String[] seatOldArr = seatOld.split(",");
                        JSONArray seatNew = model.getCheckoutTicketObj().getJSONArray("seat");
                        Boolean isDuplicateSeat = false;
                        for (String seatOldIter : seatOldArr) {
                            for (int i = 0; i < seatNew.length(); i++) {
                                if (seatNew.getString(i).equals(seatOldIter)) {
                                    isDuplicateSeat = true;
                                }
                            }
                        }
                        if (isDuplicateSeat) {
                            JOptionPane.showMessageDialog(view, "Booking gagal!\nKursi tersebut telah di ambil, silahkan pilih kursi lain.");
                            view.getBtnBayar().setEnabled(true);
                            view.getBtnBayar().setIcon(null);
                        } else {
                            for (String seatOldIter : seatOldArr) {
                                seatNew.put(seatOldIter);
                            }
                            updateStudio(studioId, seatNew.join(","));
                            makeOrder(studioId);
                            view.getBtnBayar().setIcon(null);
                            showDialogPayment(view);
                        }
                    } else {
                        System.out.println("Create");
                        createStudio(studioId, studioHall, theaterName, studioSeat);
                        makeOrder(studioId);
                        view.getBtnBayar().setIcon(null);
                        showDialogPayment(view);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(view, "Kesalahan sistem " + ex.getMessage());
                    System.exit(0);
                }
            }
        }.start();

    }

    public void showDialogPayment(BookingTicketView view) {
        JDialog frame = new JDialog(view);
        frame.setPreferredSize(new Dimension(600, 400));
        frame.setUndecorated(true);

        JPanel panelOutter = new JPanel(new CardLayout(30, 20));
        panelOutter.setBackground(Color.decode("#16264F"));

        JPanel panelInner = new JPanel();
        panelInner.setLayout(new BorderLayout(50, 10));
        panelInner.setBackground(Color.decode("#16264F"));

        JLabel cinemasLogo = new JLabel();
        cinemasLogo.setIcon(new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/logo-159.png")));
        cinemasLogo.setHorizontalAlignment(JLabel.CENTER);
        panelInner.add(cinemasLogo, BorderLayout.NORTH);

        JLabel successLogo = new JLabel();
        successLogo.setIcon(new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/paymentSuccess.gif")));
        successLogo.setHorizontalAlignment(JLabel.CENTER);
        panelInner.add(successLogo, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.decode("#16264F"));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        JLabel textSuccess = new JLabel("Yeay, Pembayaran Telah Berhasil. Selamat Menonton!");
        textSuccess.setFont(new Font("Serif", Font.PLAIN, 18));
        textSuccess.setForeground(Color.WHITE);
        textSuccess.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bottomPanel.add(textSuccess);

        JLabel divider = new JLabel("|");
        divider.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        divider.setForeground(Color.decode("#16264F"));
        bottomPanel.add(divider);

        JButton backButton = new JButton("Oke");
        backButton.setPreferredSize(new Dimension(80, 40));
        backButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        backButton.setBackground(Color.white);
        backButton.setMaximumSize(new Dimension(80, 40));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getCheckoutView().getCheckoutTicketController().getDashboardView().setVisible(true);
                model.getCheckoutView().dispose();
                view.dispose();
                frame.dispose();
            }
        });
        bottomPanel.add(backButton);

        panelInner.add(bottomPanel, BorderLayout.SOUTH);

        panelOutter.add(panelInner);
        frame.pack();

        // Center dialog
        Point parentPosition = view.getLocation();
        Dimension parentSize = view.getSize();
        Dimension size = frame.getSize();
        Point position = new Point(parentPosition.x + (parentSize.width / 2 - size.width / 2), parentPosition.y + (parentSize.height / 2 - size.height / 2));
        frame.setLocation(position);

        frame.add(panelOutter);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public String generateStudioHall() {
        JSONArray hallList = new JSONArray();
        hallList.put("A");
        hallList.put("B");
        hallList.put("C");
        hallList.put("D");
        hallList.put("E");
        hallList.put("F");
        String subtime = String.valueOf(model.getCheckoutTicketObj().getInt("show_time")).substring(0, 7);
        int indexHall = Integer.valueOf(subtime) % 5;
        String hall = hallList.getString(indexHall);
        String hallNo = subtime.substring(subtime.length() - 1);
        String studioHall = hall + hallNo;
        return studioHall;
    }

    public String generateID(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString.toUpperCase();
    }

    public void back(BookingTicketView bookingView) {
        model.getCheckoutView().setVisible(true);
        bookingView.dispose();
    }

    public void handleMouseDragged(MouseEvent evt, JFrame view) {
        int kordinatX = evt.getXOnScreen();
        int kordinatY = evt.getYOnScreen();

        view.setLocation(kordinatX - dashboardModel.getMousepX(), kordinatY - dashboardModel.getMousepY());
    }

    public void handleMousePressed(MouseEvent evt, JFrame view) {
        dashboardModel.setMousepX(evt.getX());
        dashboardModel.setMousepY(evt.getY());
    }

}
