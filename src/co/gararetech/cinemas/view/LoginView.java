/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package co.gararetech.cinemas.view;

import co.gararetech.cinemas.controller.LoginController;
import co.gararetech.cinemas.view.elements.RoundJTextField;
import co.gararetech.cinemas.model.LoginModel;
import co.gararetech.cinemas.view.elements.RoundJPasswordField;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import java.awt.Color;
import java.awt.Cursor;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author user
 */
public class LoginView extends javax.swing.JFrame {

    private LoginController loginController;
    private LoginModel loginModel;
    private ImageIcon appIcon;
    private int mouseX;
    private int mouseY;

    public LoginView() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        Properties p = new Properties();
        p.put("windowTitleFont", "Ebrima PLAIN 15");
        p.put("logoString", "");
        p.put("windowDecoration", "off");
        AluminiumLookAndFeel.setCurrentTheme(p);
        UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        loginController = new LoginController();
        loginModel = new LoginModel();
        initComponents();
        appIcon = new ImageIcon(getClass().getResource("images/chair.png"));
        this.setIconImage(appIcon.getImage());
        setResizable(false);
        loginController.setModel(loginModel);
    }

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public JButton getBtnLoginSave() {
        return btnLoginSave;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        minimize = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtEmail = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnLoginSave = new javax.swing.JButton();
        btnRegister = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cinemas Booking App");
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(849, 584));
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
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(731, 502));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("MASUK");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 120, 507, 60));

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/minimizeButton.png"))); // NOI18N
        minimize.setToolTipText("Minimize");
        minimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        minimize.setIconTextGap(0);
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });
        jPanel2.add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, -1, -1));

        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/exitButton.png"))); // NOI18N
        exit.setToolTipText("Close");
        exit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exit.setIconTextGap(0);
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseClicked(evt);
            }
        });
        jPanel2.add(exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, -1, -1));

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(54, 12, 12)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Futura Bk BT", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("EMAIL");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 38, 57, -1));

        txtPassword.setBackground(new java.awt.Color(0, 0, 0));
        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(255, 255, 255));
        txtPassword.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        txtPassword.setCaretColor(new java.awt.Color(54, 12, 12));
        txtPassword.setSelectedTextColor(new java.awt.Color(54, 12, 12));
        txtPassword.setSelectionColor(new java.awt.Color(187, 187, 187));
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        jPanel3.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 250, 40));

        txtEmail.setBackground(new java.awt.Color(0, 0, 0));
        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(255, 255, 255));
        txtEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        txtEmail.setCaretColor(new java.awt.Color(54, 12, 12));
        txtEmail.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtEmail.setSelectedTextColor(new java.awt.Color(54, 12, 12));
        txtEmail.setSelectionColor(new java.awt.Color(187, 187, 187));
        txtEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtEmailMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtEmailMouseExited(evt);
            }
        });
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        jPanel3.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 250, 38));

        jLabel3.setFont(new java.awt.Font("Futura Bk BT", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("PASSWORD");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 128, -1, -1));

        btnLoginSave.setBackground(new java.awt.Color(217, 217, 217));
        btnLoginSave.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        btnLoginSave.setForeground(new java.awt.Color(0, 0, 0));
        btnLoginSave.setText("MASUK");
        btnLoginSave.setBorder(null);
        btnLoginSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnLoginSaveMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnLoginSaveMouseReleased(evt);
            }
        });
        btnLoginSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginSaveActionPerformed(evt);
            }
        });
        jPanel3.add(btnLoginSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 290, 30));

        btnRegister.setBackground(new java.awt.Color(217, 217, 217));
        btnRegister.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        btnRegister.setForeground(new java.awt.Color(0, 0, 0));
        btnRegister.setText("DAFTAR");
        btnRegister.setBorder(null);
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });
        jPanel3.add(btnRegister, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 290, 30));

        jLabel4.setFont(new java.awt.Font("Franklin Gothic Book", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("atau");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 287, -1, -1));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Gararetech Co.");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 358, -1, -1));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("2022");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 378, -1, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 330, 420));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/logoGararetechLogin.png"))); // NOI18N
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 40, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 0, 470, 650));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/loginImagesSide.jpg"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 650));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
        this.getTxtEmail().requestFocus();
    }//GEN-LAST:event_txtEmailActionPerformed

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        try {
            // TODO add your handling code here:
            loginController.viewRegister(this, new RegisterView());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Kesalahan system " + ex.getMessage());
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void btnLoginSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginSaveActionPerformed
        // TODO add your handling code here:
        this.getBtnLoginSave().setEnabled(false);
        new Thread() {
            public void run() {
                try {
                    loginController.submit(LoginView.this, new DashboardView());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }//GEN-LAST:event_btnLoginSaveActionPerformed

    private void btnLoginSaveMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginSaveMouseReleased

    }//GEN-LAST:event_btnLoginSaveMouseReleased

    private void btnLoginSaveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginSaveMousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnLoginSaveMousePressed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - this.getMouseX(), y - this.getMouseY());
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        // TODO add your handling code here:
        this.setMouseX(evt.getX());
        this.setMouseY(evt.getY());
    }//GEN-LAST:event_jPanel1MousePressed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
        this.getBtnLoginSave().setEnabled(false);
        new Thread() {
            public void run() {
                try {
                    loginController.submit(LoginView.this, new DashboardView());
                } catch (InstantiationException ex) {
                    Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }.start();
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        // TODO add your handling code here:
        loginController.exitButton(this);
    }//GEN-LAST:event_exitMouseClicked

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
        // TODO add your handling code here:
        loginController.minimizeButton(this);
    }//GEN-LAST:event_minimizeMouseClicked

    private void txtEmailMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEmailMouseEntered
        Cursor cursor = new Cursor(Cursor.TEXT_CURSOR);
        txtEmail.setCursor(cursor);
    }//GEN-LAST:event_txtEmailMouseEntered

    private void txtEmailMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEmailMouseExited
        Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
        txtEmail.setCursor(cursor);
    }//GEN-LAST:event_txtEmailMouseExited

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
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new LoginView().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoginSave;
    private javax.swing.JButton btnRegister;
    private javax.swing.JLabel exit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel minimize;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
