package co.gararetech.cinemas.controller;

import co.gararetech.cinemas.model.CheckoutTicketModel;
import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.utils.ScaleImage;
import co.gararetech.cinemas.view.CheckoutTicketView;
import co.gararetech.cinemas.view.DashboardView;
import co.gararetech.cinemas.view.elements.RoundedPanel;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingWorker;
import javax.swing.UnsupportedLookAndFeelException;
import org.json.JSONArray;
import org.json.JSONObject;

public class CheckoutTicketController {

    private DashboardView dashboardView;

    private DashboardModel dashboardModel;

    private CheckoutTicketModel model;

    private JToggleButton selectedTime;

    public DashboardView getDashboardView() {
        return dashboardView;
    }

    public void setDashboardView(DashboardView dashboardView) {
        this.dashboardView = dashboardView;
    }

    public DashboardModel getDashboardModel() {
        return dashboardModel;
    }

    public void setDashboardModel(DashboardModel dashboardModel) {
        this.dashboardModel = dashboardModel;
    }

    public CheckoutTicketModel getModel() {
        return model;
    }

    public void setModel(CheckoutTicketModel model) {
        this.model = model;
    }

    public JSONObject getMovieSchedule(String movieId, String page, String query, String date) throws MalformedURLException, IOException {
        System.out.println("Get API Movie Schedule page " + page + " ..");
        String cityId = "";
        String cityName = dashboardModel.getUserData().getString("city_id");
        JSONArray cityList = dashboardModel.getCityList();
        for (int i = 0; i < cityList.length(); i++) {
            JSONObject rowData = cityList.getJSONObject(i);
            if (rowData.getString("name").equals(cityName)) {
                cityId = rowData.getString("id");
            }
        }
        URL url = new URL((dashboardModel.getMovieScheduleUrl().toString() + "?city=" + cityId + "&date=" + date + "&lat=&lon=&merchant=&movie=" + movieId + "&page=" + page + "&q=" + query + "&sort=alfabetical&studio_type=").replaceAll(" ", "%20"));

        dashboardModel.setConnection((HttpURLConnection) url.openConnection());
        dashboardModel.getConnection().setRequestMethod("GET");
        dashboardModel.getConnection().setRequestProperty("Authorization", "Bearer " + dashboardModel.getToken());
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
        JSONObject result = new JSONObject(responseContent.toString());

        if (result.getBoolean("success")) {
            System.out.println("success get API Movie Schedule");
        } else {
            System.out.println("failed get API Movie Schedule");
        }

        return result;
    }

    public void setfilterBioskopPlaceholder(JTextField filterBioskop) {
        if (filterBioskop.getText().isEmpty()) {
            String placeholder = "Cari Bioskop";
            filterBioskop.setText(placeholder);
            filterBioskop.setForeground(Color.decode("#757575"));
        }
    }

    public void resetFilterBioskopPlaceholder(JTextField filterBioskop) {
        String placeholder = "Cari Bioskop";
        if (filterBioskop.getText().equals(placeholder)) {
            filterBioskop.setText("");
            filterBioskop.setForeground(Color.WHITE);
        }
    }

    public void showFrame(JSONObject movieObj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, MalformedURLException, IOException {
        CheckoutTicketView checkoutTicketView = new CheckoutTicketView();
        CheckoutTicketModel checkoutTicketModel = new CheckoutTicketModel();
        this.setModel(checkoutTicketModel);

        checkoutTicketView.setCheckoutTicketController(this);

        // Movie Title
        checkoutTicketView.getTitleLabel().setText(movieObj.getString("title"));

        // Poster Image
        URL posterUrl = new URL(movieObj.getString("poster_path"));
        Image icon = ImageIO.read(posterUrl);
        ImageIcon posterIcon;
        if (icon == null) {
            posterIcon = new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/blankposter.png"));
        } else {
            posterIcon = new ImageIcon(icon);
        }
        ScaleImage scaleImg = new ScaleImage(posterIcon, 184, 212);
        ImageIcon resizePoster = scaleImg.scaleImage();
        checkoutTicketView.getPosterLabel().setIcon(resizePoster);

        // Genre
        List<String> genreValueList = new ArrayList<String>();
        JSONArray genreObj = movieObj.getJSONArray("genre_ids");
        for (int i = 0; i < genreObj.length(); i++) {
            genreValueList.add(genreObj.getJSONObject(i).getString("name"));
        }
        String genreText = String.join(", ", genreValueList);
        checkoutTicketView.getGenreField().setText(genreText);

        // Duration
        int totalMinutes = movieObj.getInt("duration");
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        String durationText = String.format("%d jam %02d menit", hours, minutes);
        checkoutTicketView.getDurasiField().setText(durationText);

        // Director
        String directorText = movieObj.getString("director");
        checkoutTicketView.getSutradaraField().setText(directorText);

        // Age rating
        String ageCategory = movieObj.getString("age_category");
        JTextField ageScore = checkoutTicketView.getRatingUsiaField();
        if (ageCategory.equals("R")) {
            ageScore.setForeground(Color.GREEN);
            ageCategory = "R 13+";
        } else if (ageCategory.equals("D")) {
            ageScore.setForeground(Color.RED);
            ageCategory = "D 17+";
        } else {
            ageScore.setForeground(Color.WHITE);
        }
        ageScore.setText(ageCategory);
        model.setMovieId(movieObj.getString("id"));
        setListMovieSchedule(checkoutTicketView, "1", "", "");
        checkoutTicketView.getContentPanelCheckout().revalidate();
        checkoutTicketView.setVisible(true);
    }

    public void setListMovieSchedule(CheckoutTicketView checkoutTicketView, String page, String query, String date) throws IOException {
        // Set List Movie Schedule
        JSONObject scheduleMovie = getMovieSchedule(model.getMovieId(), page, query, date);

        if (!scheduleMovie.getBoolean("success")) {
            System.out.println("Bioskop tidak tersedia");
            JLabel iconNotFound = new JLabel();
            ImageIcon imageNF = new ScaleImage(new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/gadabioskopCheckout.png")), 600, 500).scaleImage();
            iconNotFound.setIcon(imageNF);
            iconNotFound.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            iconNotFound.setPreferredSize(new Dimension(checkoutTicketView.getScrollContent().getWidth(), 550));
            checkoutTicketView.getContentPanelCheckout().add(iconNotFound);
            checkoutTicketView.getScrollContent().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        } else {
            JSONArray scheduleListTemp = scheduleMovie.getJSONObject("results").getJSONArray("schedules").getJSONObject(0).getJSONArray("schedules");
            Boolean hasNext = scheduleMovie.getJSONObject("results").getBoolean("has_next");
            int pageInt = Integer.valueOf(page);
            int nextPage = pageInt + 1;
            if (pageInt > 1) {
                JSONArray oldList = dashboardModel.getMovieScheduleList();
                JSONArray newList = scheduleListTemp;

                for (int i = 0; i < oldList.length(); i++) {
                    newList.put(oldList.getJSONObject(i));
                }
                System.out.println("Length page " + pageInt + " : " + newList.length());
                dashboardModel.setMovieScheduleList(newList);
            } else {
                dashboardModel.setMovieScheduleList(scheduleListTemp);
                System.out.println("Length page 1 : " + scheduleListTemp.length());
            }
            if (hasNext) {
                System.out.println("Get next page " + nextPage);
                setListMovieSchedule(checkoutTicketView, String.valueOf(pageInt + 1), query, date);
            } else {
                JSONArray scheduleList = dashboardModel.getMovieScheduleList();
                System.out.println("Rendering " + scheduleList.length() + " Theater");
                for (int i = 0; i < scheduleList.length(); i++) {
                    JSONObject rowData = scheduleList.getJSONObject(i);

                    JPanel contentPanel = new JPanel();
                    contentPanel.setLayout(new CardLayout(20, 20));
//                contentPanel.setMinimumSize(new Dimension(checkoutTicketView.getScrollContent().getWidth() - 50, 450));
                    contentPanel.setPreferredSize(new Dimension(checkoutTicketView.getScrollContent().getWidth(), 450));
//                contentPanel.setMaximumSize(new Dimension(checkoutTicketView.getScrollContent().getWidth() - 50, 2450));
                    contentPanel.setBackground(Color.decode("#19181C"));
//                contentPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));

                    // Theater Panel
                    JPanel theaterPanel = new JPanel();
                    theaterPanel.setLayout(new BoxLayout(theaterPanel, BoxLayout.Y_AXIS));
                    theaterPanel.setBackground(Color.decode("#42382F"));

                    // Card Panel for margin
                    JPanel cardPanel = new RoundedPanel();
                    cardPanel.setLayout(new CardLayout(15, 15));
                    cardPanel.setBackground(Color.decode("#42382F"));

                    // Spacing
                    JLabel space_1 = new JLabel("----");
                    space_1.setForeground(Color.decode("#42382F"));
                    space_1.setAlignmentX(Component.LEFT_ALIGNMENT);
                    theaterPanel.add(space_1);

                    // Theater title Panel
                    JPanel theaterTitlePanel = new JPanel();
                    theaterTitlePanel.setPreferredSize(new Dimension(checkoutTicketView.getScrollContent().getWidth() - 20, 40));
                    theaterTitlePanel.setLayout(new BoxLayout(theaterTitlePanel, BoxLayout.X_AXIS));
                    theaterTitlePanel.setBackground(Color.decode("#42382F"));
//                theaterTitlePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));

                    // Location Icon
                    JLabel markerIcon = new JLabel();
                    URL markerIconPath = getClass().getResource("/co/gararetech/cinemas/view/images/marker-25.png");
                    ImageIcon starImage = new ImageIcon(markerIconPath);
                    markerIcon.setIcon(starImage);
                    markerIcon.setAlignmentX(Component.LEFT_ALIGNMENT);
                    theaterTitlePanel.add(markerIcon);

                    // Theater Title
                    JLabel theaterTitle = new JLabel();
                    theaterTitle.setForeground(Color.WHITE);
                    theaterTitle.setText("  " + String.valueOf(rowData.getString("name")));
                    theaterTitle.setFont(new Font("Serif", Font.BOLD, 18));
                    theaterTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
//                theaterTitle.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                    theaterTitlePanel.add(theaterTitle);
                    theaterTitlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    theaterPanel.add(theaterTitlePanel);

                    // Theater Address
                    JTextArea theaterAddress = new JTextArea();
                    theaterAddress.setForeground(Color.WHITE);
                    theaterAddress.setBackground(Color.decode("#42382F"));
                    theaterAddress.setWrapStyleWord(true);
                    theaterAddress.setLineWrap(true);
                    theaterAddress.setEditable(false);
                    theaterAddress.setText(String.valueOf(rowData.getString("address").replace("\r\n", ", ")));
                    theaterAddress.setFont(new Font("Serif", Font.PLAIN, 14));
                    theaterAddress.setPreferredSize(new Dimension(checkoutTicketView.getScrollContent().getWidth() - 30, 50));
                    theaterAddress.setMaximumSize(new Dimension(checkoutTicketView.getScrollContent().getWidth() - 30, 50));
//                theaterAddress.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                    theaterAddress.setAlignmentX(Component.LEFT_ALIGNMENT);
                    theaterPanel.add(space_1);
                    theaterPanel.add(theaterAddress);
                    theaterPanel.add(space_1);

                    // Stadium list in theater
                    double stadiumContentHeight = 0.0;
                    JSONArray stadiumList = rowData.getJSONArray("show_time");
                    for (int j = 0; j < stadiumList.length(); j++) {
                        JSONObject stadiumObj = stadiumList.getJSONObject(j);

                        // Header Stadium Panel
                        JPanel headerStadiumPanel = new JPanel();
                        headerStadiumPanel.setPreferredSize(new Dimension(checkoutTicketView.getScrollContent().getWidth() - 30, 20));
                        headerStadiumPanel.setMaximumSize(new Dimension(checkoutTicketView.getScrollContent().getWidth() - 30, 20));
                        headerStadiumPanel.setLayout(new BorderLayout());
                        headerStadiumPanel.setBackground(Color.decode("#42382F"));
//                    headerStadiumPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                        headerStadiumPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                        // Studio type
                        JLabel studioType = new JLabel(stadiumObj.getString("category"));
                        studioType.setFont(new Font("Serif", Font.BOLD, 15));
                        studioType.setForeground(Color.decode("#99c49e"));
                        headerStadiumPanel.add(studioType, BorderLayout.WEST);

                        // Studio Price
                        JLabel studioPrice = new JLabel(stadiumObj.getString("price_string"));
                        studioPrice.setFont(new Font("Serif", Font.PLAIN, 15));
                        studioPrice.setForeground(Color.decode("#a8a8a8"));
                        headerStadiumPanel.add(studioPrice, BorderLayout.EAST);

                        // Show Time Grid Panel
                        JPanel showTimePanel = new JPanel();
                        showTimePanel.setLayout(new GridLayout(0, 5));
                        showTimePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                        showTimePanel.setBackground(Color.decode("#42382F"));
//                    showTimePanel.setPreferredSize(new Dimension(checkoutTicketView.getScrollContent().getWidth() - 30, 500));
//                    showTimePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));

                        JSONArray showTimeList = stadiumObj.getJSONArray("show_time");
                        for (int k = 0; k < showTimeList.length(); k++) {
                            JSONObject showTimeObj = showTimeList.getJSONObject(k);

                            // Button Time Panel
                            JPanel timeButtonPanel = new JPanel();
                            timeButtonPanel.setLayout(new CardLayout(3, 3));
                            timeButtonPanel.setPreferredSize(new Dimension(20, 30));
                            timeButtonPanel.setBackground(Color.decode("#42382F"));
//                        timeButtonPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));

                            // Toggle Button
                            JToggleButton timeButton = new JToggleButton();
                            int timestamp = showTimeObj.getInt("time");
                            Date dateButton = new Date(timestamp * 1000L);
                            String pattern = "HH:mm";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("273"));
                            String time = simpleDateFormat.format(dateButton);
                            int timeStatus = showTimeObj.getInt("status");
                            if (timeStatus == 0) {
                                timeButton.setEnabled(false);
                            }
                            String buttonStudioType = showTimeObj.getString("studio_type");
                            if (buttonStudioType.equals("dolby")) {
                                timeButton.setText("<html><center>" + time + " (<b>DA</b>)</html>");
                            } else {
                                timeButton.setText(time);
                            }
                            timeButton.setName(rowData.getString("id") + "|" + stadiumObj.getString("category") + "|" + time);
                            timeButton.setFont(new Font("Serif", Font.PLAIN, 14));
                            timeButton.setBackground(Color.decode("#D9D9D9"));
                            timeButton.setForeground(Color.BLACK);
                            timeButton.addItemListener(new ItemListener() {
                                @Override
                                public void itemStateChanged(ItemEvent e) {
                                    JToggleButton thisToggleBtn = (JToggleButton) e.getSource();
                                    String nama = thisToggleBtn.getName();
                                    System.out.println("Selected : " + nama);
                                    if (e.getStateChange() == ItemEvent.SELECTED) {
                                        if (model.getSelectedTime() == null) {
                                            thisToggleBtn.setBackground(Color.decode("#2E5B0B"));
                                            thisToggleBtn.setForeground(Color.WHITE);
                                            model.setSelectedTime(thisToggleBtn);
                                            checkoutTicketView.getBtnPilih().setEnabled(true);
                                        } else {
                                            model.getSelectedTime().setSelected(false);
                                            model.getSelectedTime().setBackground(Color.decode("#D9D9D9"));
                                            model.getSelectedTime().setForeground(Color.BLACK);
                                            thisToggleBtn.setBackground(Color.decode("#2E5B0B"));
                                            thisToggleBtn.setForeground(Color.WHITE);
                                            model.setSelectedTime(thisToggleBtn);
                                            checkoutTicketView.getBtnPilih().setEnabled(true);
                                        }
                                    } else {
                                        model.getSelectedTime().setSelected(false);
                                        model.getSelectedTime().setBackground(Color.decode("#D9D9D9"));
                                        model.getSelectedTime().setForeground(Color.BLACK);
                                        checkoutTicketView.getBtnPilih().setEnabled(false);
                                    }
                                }
                            });
                            timeButtonPanel.add(timeButton);

                            showTimePanel.add(timeButtonPanel);
                        }

                        // Divider Studio Type
                        JPanel divider = new JPanel();
                        divider.setPreferredSize(new Dimension(50, 30));
                        divider.setBackground(Color.decode("#42382F"));
//                    divider.setBorder(BorderFactory.createLineBorder(Color.yellow));

                        stadiumContentHeight += headerStadiumPanel.getPreferredSize().getHeight() + showTimePanel.getPreferredSize().getHeight() + divider.getPreferredSize().getHeight();
                        theaterPanel.add(headerStadiumPanel);
                        theaterPanel.add(showTimePanel);
                        theaterPanel.add(divider);
                    }

                    double contentHeight = 100 + theaterTitlePanel.getPreferredSize().getHeight() + theaterAddress.getPreferredSize().getHeight() + stadiumContentHeight;
//                    contentPanel.setPreferredSize(new Dimension(checkoutTicketView.getScrollContent().getWidth(), (int) contentHeight));
                    contentPanel.setMaximumSize(new Dimension(checkoutTicketView.getScrollContent().getWidth(), (int) contentHeight));
//                    if (scheduleList.length() == 1) {
//                    }
                    cardPanel.add(theaterPanel);
                    contentPanel.add(cardPanel);
                    contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    checkoutTicketView.getContentPanelCheckout().add(contentPanel);
                    checkoutTicketView.getScrollContent().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

                }
            }

        }
    }

    public void onFilterTyped(CheckoutTicketView view, KeyEvent e) {
        JTextField filter = (JTextField) e.getSource();
        String filterQuery = filter.getText();
        System.out.println("Filter bioskop : " + filterQuery);
        new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                try {
                    view.getContentPanelCheckout().removeAll();
                    view.getContentPanelCheckout().repaint();
                    setListMovieSchedule(view, "1", filterQuery, view.getFilterTanggal().getText());
                    view.getContentPanelCheckout().revalidate();
                    view.getScrollContent().getVerticalScrollBar().setValue(0);
                } catch (IOException ex) {
                    System.out.println("Replace image on error.");
                    JLabel iconNotFound = new JLabel();
                    ImageIcon imageNF = new ScaleImage(new ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/gadabioskopCheckout.png")), 600, 500).scaleImage();
                    iconNotFound.setIcon(imageNF);
                    iconNotFound.setAlignmentX(JLabel.CENTER_ALIGNMENT);
                    iconNotFound.setPreferredSize(new Dimension(view.getScrollContent().getWidth(), 550));
                    view.getContentPanelCheckout().add(iconNotFound);
                    view.getScrollContent().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                    Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        }.execute();

    }

    public void onDateChange(CheckoutTicketView view, DateChangeEvent e) {
        String tanggal = String.valueOf(e.getNewDate());
        System.out.println("Filter tanggal : " + tanggal);
        new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                try {
                    String q = "";
                    if (!view.getFilterBioskop().getText().equals("Pilih bioskop kesayangan kamu ..")) {
                        q = view.getFilterBioskop().getText();
                    }
                    view.getFilterTanggal().setText(tanggal);
                    view.getContentPanelCheckout().removeAll();
                    view.getContentPanelCheckout().repaint();
                    setListMovieSchedule(view, "1", q, tanggal);
                    view.getContentPanelCheckout().revalidate();
                    view.getScrollContent().getVerticalScrollBar().setValue(0);
                } catch (IOException ex) {
                    Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        }.execute();
    }

    public void back(CheckoutTicketView checkoutTicketView) {
        checkoutTicketView.dispose();
        this.getDashboardView().setVisible(true);
    }
}
