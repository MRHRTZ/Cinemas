/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package co.gararetech.cinemas.view;

import co.gararetech.cinemas.controller.CheckoutTicketController;
import co.gararetech.cinemas.model.CheckoutTicketModel;
import co.gararetech.cinemas.view.elements.RoundJTextField;
import co.gararetech.cinemas.view.elements.RoundedPanel;
import co.gararetech.cinemas.view.elements.RoundJButton;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author outlo
 */
public class CheckoutTicketView extends javax.swing.JFrame {

    /**
     * Creates new form CheckoutTIcketView
     */
    private CheckoutTicketController checkoutTicketController;
    private CheckoutTicketModel checkoutTicketModel;
    private ImageIcon appIcon;
    private BookingTicketView bookingticketview;
    private int mouseX;
    private int mouseY;

    public CheckoutTicketView() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        Properties p = new Properties();
        p.put("windowTitleFont", "Ebrima PLAIN 15");
        p.put("logoString", "");
        p.put("windowDecoration", "off");
        AluminiumLookAndFeel.setCurrentTheme(p);
        UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");

        checkoutTicketController = new CheckoutTicketController();
        bookingticketview = new BookingTicketView();
        initComponents();

        appIcon = new ImageIcon(getClass().getResource("images/chair.png"));
        this.setIconImage(appIcon.getImage());
    }

    public JTextField getFilterTanggal() {
        return filterTanggal;
    }

    public CheckoutTicketController getCheckoutTicketController() {
        return checkoutTicketController;
    }

    public void setCheckoutTicketController(CheckoutTicketController checkoutTicketController) {
        this.checkoutTicketController = checkoutTicketController;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public JPanel getContentPanelCheckout() {
        return contentPanelCheckout;
    }

    public JTextField getFilterBioskop() {
        return filterBioskop;
    }

    public JLabel getPosterLabel() {
        return posterLabel;
    }

    public JScrollPane getScrollContent() {
        return scrollContent;
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public JTextField getDurasiField() {
        return durasiField;
    }

    public JTextField getGenreField() {
        return genreField;
    }

    public JTextField getRatingUsiaField() {
        return ratingUsiaField;
    }

    public JTextField getSutradaraField() {
        return sutradaraField;
    }

    public JPanel getContainerScroller() {
        return containerScroller;
    }

    public JButton getBtnPilih() {
        return BtnPilih;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Divider = new javax.swing.JPanel();
        contentContainer = new javax.swing.JPanel();
        filterPanel = new RoundedPanel();
        filterPanel.setVisible(true);
        filterBioskop = new RoundJTextField(50);
        ;
        checkoutTicketController.setfilterBioskopPlaceholder(this.getFilterBioskop());
        jLabel11 = new javax.swing.JLabel();
        filterTanggal = new RoundJTextField(50);
        ;
        showDate = new com.github.lgooddatepicker.components.DatePicker();
        showDate.setDateToToday();
        showDate.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dce) {
                checkoutTicketController.onDateChange(CheckoutTicketView.this, dce);
            }
        });
        filterTanggal.setText(showDate.getDate().toString());
        datePick = new javax.swing.JLabel();
        containerScroller = new RoundedPanel();
        scrollContent = new javax.swing.JScrollPane();
        scrollContent.setOpaque(false);
        scrollContent.getViewport().setOpaque(false);
        scrollContent.getVerticalScrollBar().setUnitIncrement(25);
        scrollContent.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollContent.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        contentPanelCheckout = new javax.swing.JPanel();
        infoPanel = new RoundedPanel();
        titleLabel = new javax.swing.JLabel();
        BtnBatal = new javax.swing.JButton();
        BtnPilih = new javax.swing.JButton();
        separator1 = new RoundedPanel();
        separator2 = new RoundedPanel();
        separator3 = new RoundedPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        posterLabel = new javax.swing.JLabel();
        genreField = new javax.swing.JTextField();
        durasiField = new javax.swing.JTextField();
        sutradaraField = new javax.swing.JTextField();
        ratingUsiaField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        headerPanel.setBackground(Color.decode("#1D1C1C"));
        headerPanel.setPreferredSize(new java.awt.Dimension(1050, 85));
        headerPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                headerPanelMouseDragged(evt);
            }
        });
        headerPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                headerPanelMousePressed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/logo-159.png"))); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(jLabel1)
                .addContainerGap(689, Short.MAX_VALUE))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 90));

        Divider.setBackground(Color.decode("#42382F"));

        javax.swing.GroupLayout DividerLayout = new javax.swing.GroupLayout(Divider);
        Divider.setLayout(DividerLayout);
        DividerLayout.setHorizontalGroup(
            DividerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        DividerLayout.setVerticalGroup(
            DividerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        getContentPane().add(Divider, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 960, 60));

        contentContainer.setBackground(Color.decode("#42382F"));
        contentContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        filterPanel.setBackground(new java.awt.Color(221, 221, 221));
        filterPanel.setPreferredSize(new java.awt.Dimension(549, 35));
        filterPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        filterBioskop.setBackground(Color.decode("#454444"));
        filterBioskop.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        filterBioskop.setForeground(Color.decode("#757575"));
        filterBioskop.setBorder(BorderFactory.createCompoundBorder(
            filterBioskop.getBorder(), 
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    filterBioskop.setCaretColor(new java.awt.Color(255, 255, 255));
    filterBioskop.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            filterBioskopFocusGained(evt);
        }
        public void focusLost(java.awt.event.FocusEvent evt) {
            filterBioskopFocusLost(evt);
        }
    });
    filterBioskop.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            filterBioskopKeyReleased(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            filterBioskopKeyTyped(evt);
        }
    });
    filterPanel.add(filterBioskop, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 290, 40));

    jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/search-icon.png"))); // NOI18N
    jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jLabel11MouseClicked(evt);
        }
    });
    filterPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, 60));

    filterTanggal.setEditable(false);
    filterTanggal.setBackground(Color.decode("#454444"));
    filterTanggal.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
    filterTanggal.setForeground(new java.awt.Color(203, 203, 203));
    filterTanggal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    filterTanggal.setBorder(BorderFactory.createCompoundBorder(
        filterBioskop.getBorder(), 
        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
filterTanggal.setCaretColor(new java.awt.Color(255, 255, 255));
filterTanggal.addFocusListener(new java.awt.event.FocusAdapter() {
    public void focusGained(java.awt.event.FocusEvent evt) {
        filterTanggalFocusGained(evt);
    }
    public void focusLost(java.awt.event.FocusEvent evt) {
        filterTanggalFocusLost(evt);
    }
    });
    filterTanggal.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            filterTanggalMouseClicked(evt);
        }
    });
    filterTanggal.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            filterTanggalActionPerformed(evt);
        }
    });
    filterTanggal.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            filterTanggalKeyReleased(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            filterTanggalKeyTyped(evt);
        }
    });
    filterPanel.add(filterTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 110, 40));

    showDate.setOpaque(false);
    filterPanel.add(showDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 0, 40));

    datePick.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/calendar-39.png"))); // NOI18N
    datePick.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            datePickMouseClicked(evt);
        }
    });
    filterPanel.add(datePick, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, -1, 40));

    contentContainer.add(filterPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 40, 610, 60));

    containerScroller.setBackground(Color.decode("#19181C"));
    containerScroller.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    scrollContent.setBorder(null);
    scrollContent.setForeground(new java.awt.Color(102, 102, 102));
    scrollContent.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    contentPanelCheckout.setBackground(Color.decode("#19181C"));
    contentPanelCheckout.setLayout(new javax.swing.BoxLayout(contentPanelCheckout, javax.swing.BoxLayout.PAGE_AXIS));
    scrollContent.setViewportView(contentPanelCheckout);

    containerScroller.add(scrollContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 620, 390));

    contentContainer.add(containerScroller, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 680, 510));

    infoPanel.setBackground(Color.decode("#222222"));

    titleLabel.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
    titleLabel.setForeground(new java.awt.Color(255, 255, 255));
    titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    titleLabel.setText("MOVIE TITLE");

    BtnBatal.setBackground(Color.decode("#A27B5C"));
    BtnBatal.setForeground(new java.awt.Color(255, 255, 255));
    BtnBatal.setText("BATAL");
    BtnBatal.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            BtnBatalActionPerformed(evt);
        }
    });

    BtnPilih.setText("PILIH KURSI");
    BtnPilih.setBackground(Color.decode("#2E5B0B"));
    BtnPilih.setForeground(new java.awt.Color(255, 255, 255));
    BtnPilih.setEnabled(false);
    BtnPilih.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            BtnPilihActionPerformed(evt);
        }
    });

    separator1.setPreferredSize(new java.awt.Dimension(203, 4));

    javax.swing.GroupLayout separator1Layout = new javax.swing.GroupLayout(separator1);
    separator1.setLayout(separator1Layout);
    separator1Layout.setHorizontalGroup(
        separator1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 184, Short.MAX_VALUE)
    );
    separator1Layout.setVerticalGroup(
        separator1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 9, Short.MAX_VALUE)
    );

    separator2.setPreferredSize(new java.awt.Dimension(203, 4));

    javax.swing.GroupLayout separator2Layout = new javax.swing.GroupLayout(separator2);
    separator2.setLayout(separator2Layout);
    separator2Layout.setHorizontalGroup(
        separator2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 0, Short.MAX_VALUE)
    );
    separator2Layout.setVerticalGroup(
        separator2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 9, Short.MAX_VALUE)
    );

    separator3.setPreferredSize(new java.awt.Dimension(203, 4));

    javax.swing.GroupLayout separator3Layout = new javax.swing.GroupLayout(separator3);
    separator3.setLayout(separator3Layout);
    separator3Layout.setHorizontalGroup(
        separator3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 0, Short.MAX_VALUE)
    );
    separator3Layout.setVerticalGroup(
        separator3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 8, Short.MAX_VALUE)
    );

    jLabel2.setBackground(new java.awt.Color(102, 102, 102));
    jLabel2.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
    jLabel2.setForeground(new java.awt.Color(204, 204, 204));
    jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel2.setText("Genre :");

    jLabel3.setBackground(new java.awt.Color(102, 102, 102));
    jLabel3.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
    jLabel3.setForeground(new java.awt.Color(204, 204, 204));
    jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel3.setText("Sutradara :");

    jLabel4.setBackground(new java.awt.Color(102, 102, 102));
    jLabel4.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
    jLabel4.setForeground(new java.awt.Color(204, 204, 204));
    jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel4.setText("Durasi :");

    jLabel5.setBackground(new java.awt.Color(102, 102, 102));
    jLabel5.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
    jLabel5.setForeground(new java.awt.Color(204, 204, 204));
    jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel5.setText("Rating Usia :");

    posterLabel.setForeground(new java.awt.Color(255, 255, 255));
    posterLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

    genreField.setEditable(false);
    genreField.setBackground(Color.decode("#222222"));
    genreField.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
    genreField.setForeground(new java.awt.Color(255, 255, 255));
    genreField.setBorder(null);

    durasiField.setEditable(false);
    durasiField.setBackground(Color.decode("#222222"));
    durasiField.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
    durasiField.setForeground(new java.awt.Color(255, 255, 255));
    durasiField.setBorder(null);

    sutradaraField.setEditable(false);
    sutradaraField.setBackground(Color.decode("#222222"));
    sutradaraField.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
    sutradaraField.setForeground(new java.awt.Color(255, 255, 255));
    sutradaraField.setBorder(null);

    ratingUsiaField.setEditable(false);
    ratingUsiaField.setBackground(Color.decode("#222222"));
    ratingUsiaField.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
    ratingUsiaField.setForeground(new java.awt.Color(255, 255, 255));
    ratingUsiaField.setBorder(null);

    javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
    infoPanel.setLayout(infoPanelLayout);
    infoPanelLayout.setHorizontalGroup(
        infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(infoPanelLayout.createSequentialGroup()
            .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoPanelLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(separator1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoPanelLayout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(separator3, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                        .addComponent(BtnPilih, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtnBatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(infoPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(infoPanelLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(infoPanelLayout.createSequentialGroup()
                                    .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(infoPanelLayout.createSequentialGroup()
                                            .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                                            .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(durasiField, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(genreField, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(sutradaraField, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(ratingUsiaField, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(separator2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
                                    .addGap(3, 3, 3))
                                .addComponent(posterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(infoPanelLayout.createSequentialGroup()
                            .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 1, Short.MAX_VALUE)))))
            .addContainerGap())
    );
    infoPanelLayout.setVerticalGroup(
        infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(infoPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(separator1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(posterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(separator2, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(2, 2, 2)
            .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(genreField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel4)
                .addComponent(durasiField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3)
                .addComponent(sutradaraField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel5)
                .addComponent(ratingUsiaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addComponent(separator3, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(BtnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(BtnPilih, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(15, 15, 15))
    );

    contentContainer.add(infoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(728, 27, -1, -1));

    getContentPane().add(contentContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 93, 960, 560));

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void headerPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerPanelMouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - this.getMouseX(), y - this.getMouseY());
    }//GEN-LAST:event_headerPanelMouseDragged

    private void headerPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerPanelMousePressed
        // TODO add your handling code here:
        this.mouseX = evt.getX();
        this.mouseY = evt.getY();
    }//GEN-LAST:event_headerPanelMousePressed

    private void filterBioskopFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_filterBioskopFocusGained
        // TODO add your handling code here:
        checkoutTicketController.resetFilterBioskopPlaceholder(this.getFilterBioskop());
    }//GEN-LAST:event_filterBioskopFocusGained

    private void filterBioskopFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_filterBioskopFocusLost
        // TODO add your handling code here:
        checkoutTicketController.setfilterBioskopPlaceholder(this.getFilterBioskop());
    }//GEN-LAST:event_filterBioskopFocusLost

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        // TODO add your handling code here:
        checkoutTicketController.back(this);
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnPilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPilihActionPerformed
        try {
            // TODO add your handling code here:

//        JPanel contentPanel = new JPanel();
//        contentPanel.setLayout(new CardLayout(50, 30));
////        contentPanel.setPreferredSize(new Dimension(this.scrollContent.getWidth(), 450));
////        contentPanel.setMaximumSize(new Dimension(this.scrollContent.getWidth(), 450));
//        contentPanel.setPreferredSize(new Dimension(this.scrollContent.getWidth(), 85));
//        contentPanel.setMaximumSize(new Dimension(this.scrollContent.getWidth(), 85));
//        contentPanel.setBackground(Color.RED);
//        this.getContentPanelCheckout().add(contentPanel);
//        this.getContentPanelCheckout().revalidate();
//        this.dispose();
//        bookingticketview.setVisible(true);
//
//        this.getScrollContent().getVerticalScrollBar().setValue(0);
            checkoutTicketController.bookingTicket(this, new BookingTicketView());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CheckoutTicketView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(CheckoutTicketView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CheckoutTicketView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(CheckoutTicketView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_BtnPilihActionPerformed

    private void filterBioskopKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterBioskopKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_filterBioskopKeyTyped

    private void filterBioskopKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterBioskopKeyReleased
        // TODO add your handling code here:
        checkoutTicketController.onFilterTyped(this, evt);
    }//GEN-LAST:event_filterBioskopKeyReleased

    private void filterTanggalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_filterTanggalFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_filterTanggalFocusGained

    private void filterTanggalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_filterTanggalFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_filterTanggalFocusLost

    private void filterTanggalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterTanggalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_filterTanggalKeyReleased

    private void filterTanggalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterTanggalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_filterTanggalKeyTyped

    private void filterTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterTanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_filterTanggalActionPerformed

    private void datePickMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datePickMouseClicked
        // TODO add your handling code here:
        showDate.openPopup();
    }//GEN-LAST:event_datePickMouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
        filterBioskop.requestFocus();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void filterTanggalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filterTanggalMouseClicked
        // TODO add your handling code here:
        showDate.openPopup();
    }//GEN-LAST:event_filterTanggalMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CheckoutTicketView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CheckoutTicketView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CheckoutTicketView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CheckoutTicketView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new CheckoutTicketView().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CheckoutTicketView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(CheckoutTicketView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(CheckoutTicketView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(CheckoutTicketView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBatal;
    private javax.swing.JButton BtnPilih;
    private javax.swing.JPanel Divider;
    private javax.swing.JPanel containerScroller;
    private javax.swing.JPanel contentContainer;
    private javax.swing.JPanel contentPanelCheckout;
    private javax.swing.JLabel datePick;
    private javax.swing.JTextField durasiField;
    private javax.swing.JTextField filterBioskop;
    private javax.swing.JPanel filterPanel;
    private javax.swing.JTextField filterTanggal;
    private javax.swing.JTextField genreField;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel posterLabel;
    private javax.swing.JTextField ratingUsiaField;
    private javax.swing.JScrollPane scrollContent;
    private javax.swing.JPanel separator1;
    private javax.swing.JPanel separator2;
    private javax.swing.JPanel separator3;
    private com.github.lgooddatepicker.components.DatePicker showDate;
    private javax.swing.JTextField sutradaraField;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
