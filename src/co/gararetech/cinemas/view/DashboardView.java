package co.gararetech.cinemas.view;

import co.gararetech.cinemas.controller.CinemaListController;
import co.gararetech.cinemas.controller.DashboardController;
import co.gararetech.cinemas.controller.NowPlayingController;
import co.gararetech.cinemas.controller.OrderHistoryContoller;
import co.gararetech.cinemas.controller.UpcomingController;
import co.gararetech.cinemas.model.DashboardModel;
import co.gararetech.cinemas.utils.GoogleCloudStorage;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class DashboardView extends javax.swing.JFrame {

    private NowPlayingController nowPlayingController;
    private UpcomingController upcomingController;
    private OrderHistoryContoller orderHistoryController;
    private CinemaListController cinemaListController;
    private DashboardController dashboardController;
    private GoogleCloudStorage googleCloudStorage;
    private DashboardModel dashboardModel;
    private ImageIcon appIcon;
    private JPanel loadingPanel;
    private JDialog loadingUser;
    private int mousepX;
    private int mousepY;

    public DashboardView() throws ClassNotFoundException, InstantiationException, UnsupportedLookAndFeelException, IllegalAccessException, URISyntaxException, IOException {
        Properties p = new Properties();
        p.put("windowTitleFont", "Ebrima PLAIN 15");
        p.put("logoString", "");
        p.put("windowDecoration", "off");
        AluminiumLookAndFeel.setCurrentTheme(p);
        UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");

        dashboardController = new DashboardController();
        dashboardModel = new DashboardModel();
        nowPlayingController = new NowPlayingController();
        upcomingController = new UpcomingController();
        cinemaListController = new CinemaListController();
        orderHistoryController = new OrderHistoryContoller();
        googleCloudStorage = new GoogleCloudStorage();

        initComponents();

        dashboardController.setModel(dashboardModel);
        nowPlayingController.setModel(dashboardModel);
        upcomingController.setModel(dashboardModel);
        cinemaListController.setModel(dashboardModel);
        orderHistoryController.setModel(dashboardModel);

        appIcon = new ImageIcon(getClass().getResource("images/chair.png"));
        this.setIconImage(appIcon.getImage());

        dashboardController.setActiveButton(this, "nowplaying");
        this.loadingPanel = dashboardController.addLoadingContent(this.getContent(), "");
        new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                try {
                    dashboardController.initPage(DashboardView.this);
                    dashboardController.getCities();
                    nowPlayingController.setNewGrid(DashboardView.this);
                    dashboardController.removeLoadingContent(DashboardView.this.getContent(), DashboardView.this.loadingPanel);
                } catch (IOException ex) {
                    Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        }.execute();
    }

    public JPanel getLoadingPanel() {
        return loadingPanel;
    }

    public void setLoadingPanel(JPanel loadingPanel) {
        this.loadingPanel = loadingPanel;
    }

    public JButton getBtnCinema() {
        return btnCinema;
    }

    public JButton getBtnNowPlaying() {
        return btnNowPlaying;
    }

    public JButton getBtnOrderHistory() {
        return btnOrderHistory;
    }

    public JButton getBtnUpcoming() {
        return btnUpcoming;
    }

    public int getMousepX() {
        return mousepX;
    }

    public void setMousepX(int mousepX) {
        this.mousepX = mousepX;
    }

    public int getMousepY() {
        return mousepY;
    }

    public void setMousepY(int mousepY) {
        this.mousepY = mousepY;
    }

    public JPanel getContent() {
        return content;
    }

    public DashboardModel getModel() {
        return dashboardModel;
    }

    public DashboardController getDashboardController() {
        return dashboardController;
    }

    public DashboardModel getDashboardModel() {
        return dashboardModel;
    }

    public JDialog getLoadingUser() {
        return loadingUser;
    }

    public void setLoadingUser(JDialog loadingUser) {
        this.loadingUser = loadingUser;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupProfile = new javax.swing.JPopupMenu();
        editProfile = new javax.swing.JMenuItem();
        bugReport = new javax.swing.JMenuItem();
        aboutUs = new javax.swing.JMenuItem();
        line = new javax.swing.JPopupMenu.Separator();
        Logout = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnNowPlaying = new javax.swing.JButton();
        btnUpcoming = new javax.swing.JButton();
        btnCinema = new javax.swing.JButton();
        btnOrderHistory = new javax.swing.JButton();
        minimize = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        btnCinema1 = new javax.swing.JButton();
        contentPane = new javax.swing.JScrollPane();
        contentPane.getVerticalScrollBar().setUnitIncrement(25);
        content = new javax.swing.JPanel();

        editProfile.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        editProfile.setText("Edit Profile");
        editProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProfileActionPerformed(evt);
            }
        });
        popupProfile.add(editProfile);

        bugReport.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        bugReport.setText("Bug Report");
        popupProfile.add(bugReport);

        aboutUs.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        aboutUs.setText("About Us");
        popupProfile.add(aboutUs);
        popupProfile.add(line);

        Logout.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        Logout.setText("Logout");
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });
        popupProfile.add(Logout);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cinemas Booking App");
        setMaximumSize(new java.awt.Dimension(1212, 640));
        setMinimumSize(new java.awt.Dimension(1212, 640));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1212, 640));
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(Color.decode("#1D1C1C"));
        jPanel1.setPreferredSize(new java.awt.Dimension(1050, 85));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/logo-159.png"))); // NOI18N

        btnNowPlaying.setBackground(Color.decode("#3D3C3A"));
        btnNowPlaying.setForeground(new java.awt.Color(255, 255, 255));
        btnNowPlaying.setText("Sedang Tayang");
        btnNowPlaying.setToolTipText("");
        btnNowPlaying.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNowPlayingActionPerformed(evt);
            }
        });

        btnUpcoming.setBackground(Color.decode("#D9D9D9"));
        btnUpcoming.setForeground(new java.awt.Color(204, 204, 204));
        btnUpcoming.setText("Segera");
        btnUpcoming.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpcomingActionPerformed(evt);
            }
        });

        btnCinema.setBackground(Color.decode("#D9D9D9"));
        btnCinema.setText("Bioskop");
        btnCinema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCinemaActionPerformed(evt);
            }
        });

        btnOrderHistory.setBackground(Color.decode("#D9D9D9"));
        btnOrderHistory.setText("Riwayat Pesanan");
        btnOrderHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderHistoryActionPerformed(evt);
            }
        });

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/minimizeButton.png"))); // NOI18N
        minimize.setToolTipText("MINIMIZE");
        minimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        minimize.setIconTextGap(0);
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });

        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/exitButton.png"))); // NOI18N
        exit.setToolTipText("EXIT");
        exit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exit.setIconTextGap(0);
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseClicked(evt);
            }
        });

        btnCinema1.setBackground(Color.decode("#D9D9D9"));
        btnCinema1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/ProfileIconBlack.png"))); // NOI18N
        btnCinema1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCinema1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                .addComponent(btnNowPlaying, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpcoming, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnOrderHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCinema, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btnCinema1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addComponent(minimize)
                .addGap(18, 18, 18)
                .addComponent(exit)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(minimize)
                    .addComponent(exit))
                .addGap(66, 66, 66))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(btnCinema1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnUpcoming, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCinema, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnOrderHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNowPlaying, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1212, 90));

        contentPane.setBackground(new java.awt.Color(102, 102, 102));
        contentPane.setBorder(null);
        contentPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        contentPane.setPreferredSize(new java.awt.Dimension(1050, 600));

        content.setBackground(Color.decode("#42382F"));
        content.setLayout(new java.awt.GridLayout(1, 0));
        contentPane.setViewportView(content);

        getContentPane().add(contentPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1230, 550));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void showPopupProfile() {
        this.popupProfile.show(this, 970, 65);
    }

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        // TODO add your handling code here:

        int kordinatX = evt.getXOnScreen();
        int kordinatY = evt.getYOnScreen();

        this.setLocation(kordinatX - this.getMousepX(), kordinatY - this.getMousepY());
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        // TODO add your handling code here:
        this.setMousepX(evt.getX());
        this.setMousepY(evt.getY());
    }//GEN-LAST:event_jPanel1MousePressed

    private void btnUpcomingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpcomingActionPerformed
        // TODO add your handling code here:
        dashboardController.setActiveButton(this, "upcoming");
        dashboardController.removeContent(this);
        this.loadingPanel = dashboardController.addLoadingContent(this.getContent(), "");
        new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                try {
                    upcomingController.setNewGrid(DashboardView.this);
                    dashboardController.removeLoadingContent(DashboardView.this.getContent(), DashboardView.this.loadingPanel);
                    DashboardView.this.revalidate();
                } catch (IOException ex) {
                    Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        }.execute();
    }//GEN-LAST:event_btnUpcomingActionPerformed

    private void btnNowPlayingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNowPlayingActionPerformed
        // TODO add your handling code here:
        dashboardController.setActiveButton(this, "nowplaying");
        dashboardController.removeContent(this);
        this.loadingPanel = dashboardController.addLoadingContent(this.getContent(), "");
        new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                try {
                    nowPlayingController.setNewGrid(DashboardView.this);
                    dashboardController.removeLoadingContent(DashboardView.this.getContent(), DashboardView.this.loadingPanel);
                    DashboardView.this.revalidate();
                } catch (IOException ex) {
                    Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        }.execute();
    }//GEN-LAST:event_btnNowPlayingActionPerformed

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        // TODO add your handling code here:
        dashboardController.exitButton(this);
    }//GEN-LAST:event_exitMouseClicked

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
        // TODO add your handling code here:
        dashboardController.minimizeButton(this);
    }//GEN-LAST:event_minimizeMouseClicked

    private void btnCinemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCinemaActionPerformed
        // TODO add your handling code here:
        dashboardController.setActiveButton(this, "cinemas");
        dashboardController.removeContent(this);
        this.loadingPanel = dashboardController.addLoadingContent(this.getContent(), "");
        new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                try {
                    cinemaListController.setNewGrid(DashboardView.this);
                    dashboardController.removeLoadingContent(DashboardView.this.getContent(), DashboardView.this.loadingPanel);
                    DashboardView.this.revalidate();
                } catch (IOException ex) {
                    Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        }.execute();
    }//GEN-LAST:event_btnCinemaActionPerformed

    private void btnCinema1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCinema1ActionPerformed
        this.showPopupProfile();
    }//GEN-LAST:event_btnCinema1ActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown

    private void btnOrderHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderHistoryActionPerformed
        // TODO add your handling code here:
        dashboardController.setActiveButton(this, "orderhistory");
        dashboardController.removeContent(this);
        this.loadingPanel = dashboardController.addLoadingContent(this.getContent(), "");
        new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() throws ParseException {
                try {
                    orderHistoryController.setGrid(DashboardView.this);
                    dashboardController.removeLoadingContent(DashboardView.this.getContent(), DashboardView.this.loadingPanel);
                    DashboardView.this.revalidate();
                } catch (IOException ex) {
                    Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        }.execute();
    }//GEN-LAST:event_btnOrderHistoryActionPerformed

    private void editProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProfileActionPerformed
        // TODO add your handling code here:
        new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                try {
                    dashboardController.viewProfile(DashboardView.this, new ProfileView());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        }.execute();
    }//GEN-LAST:event_editProfileActionPerformed

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        try {
            // TODO add your handling code here:
            dashboardController.logout(this, new LoginView());

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashboardView.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            Logger.getLogger(DashboardView.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            Logger.getLogger(DashboardView.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(DashboardView.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_LogoutActionPerformed

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
            java.util.logging.Logger.getLogger(DashboardView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashboardView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashboardView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new DashboardView().setVisible(false);

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DashboardView.class
                            .getName()).log(Level.SEVERE, null, ex);

                } catch (InstantiationException ex) {
                    Logger.getLogger(DashboardView.class
                            .getName()).log(Level.SEVERE, null, ex);

                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(DashboardView.class
                            .getName()).log(Level.SEVERE, null, ex);

                } catch (IllegalAccessException ex) {
                    Logger.getLogger(DashboardView.class
                            .getName()).log(Level.SEVERE, null, ex);

                } catch (URISyntaxException ex) {
                    Logger.getLogger(DashboardView.class
                            .getName()).log(Level.SEVERE, null, ex);

                } catch (IOException ex) {
                    Logger.getLogger(DashboardView.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Logout;
    private javax.swing.JMenuItem aboutUs;
    private javax.swing.JButton btnCinema;
    private javax.swing.JButton btnCinema1;
    private javax.swing.JButton btnNowPlaying;
    private javax.swing.JButton btnOrderHistory;
    private javax.swing.JButton btnUpcoming;
    private javax.swing.JMenuItem bugReport;
    private javax.swing.JPanel content;
    private javax.swing.JScrollPane contentPane;
    private javax.swing.JMenuItem editProfile;
    private javax.swing.JLabel exit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator line;
    private javax.swing.JLabel minimize;
    private javax.swing.JPopupMenu popupProfile;
    // End of variables declaration//GEN-END:variables
}
