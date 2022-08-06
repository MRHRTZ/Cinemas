package co.gararetech.cinemas.view;

import co.gararetech.cinemas.view.elements.RoundedPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import org.netbeans.lib.awtextra.AbsoluteConstraints;

public class BookingTicketView extends javax.swing.JFrame {

    private List<String> kursi;

    public BookingTicketView() {
        kursi = new ArrayList();
        initComponents();

    }

    public JPanel getPanelKursi() {
        return panelKursi;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelUtama = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelKursi = new RoundedPanel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new RoundedPanel();
        panelHarga = new javax.swing.JPanel();
        panelTampilKursi = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tampilKursi = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1024, 640));
        setMinimumSize(new java.awt.Dimension(1024, 640));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1024, 640));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelUtama.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelHeader.setBackground(Color.decode("#1D1C1C"));
        panelHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/gararetech/cinemas/view/images/logo-159.png"))); // NOI18N
        panelHeader.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 18, -1, -1));

        panelUtama.add(panelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1024, 90));

        panelKursi.setBackground(new java.awt.Color(0, 0, 0));
        panelKursi.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("Create");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panelKursi.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        panelUtama.add(panelKursi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 690, 510));

        jPanel2.setBackground(new java.awt.Color(153, 153, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout panelHargaLayout = new javax.swing.GroupLayout(panelHarga);
        panelHarga.setLayout(panelHargaLayout);
        panelHargaLayout.setHorizontalGroup(
            panelHargaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );
        panelHargaLayout.setVerticalGroup(
            panelHargaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        jPanel2.add(panelHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 230, 250));

        tampilKursi.setColumns(20);
        tampilKursi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tampilKursi.setLineWrap(true);
        tampilKursi.setRows(5);
        jScrollPane1.setViewportView(tampilKursi);

        javax.swing.GroupLayout panelTampilKursiLayout = new javax.swing.GroupLayout(panelTampilKursi);
        panelTampilKursi.setLayout(panelTampilKursiLayout);
        panelTampilKursiLayout.setHorizontalGroup(
            panelTampilKursiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTampilKursiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTampilKursiLayout.setVerticalGroup(
            panelTampilKursiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTampilKursiLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel2.add(panelTampilKursi, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 230, 220));

        panelUtama.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 110, 250, 510));

        getContentPane().add(panelUtama, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 640));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int h = 20;
        int w = 80;

        int maxX = 11;
        int maxY = 7;

        for (int ix = 0; ix < maxX; ix++) {
            for (int iy = 0; iy < maxY; iy++) {
                System.out.println(ix + "." + iy + " : " + h + "x" + w);
                JToggleButton btnkursi = new JToggleButton();
                btnkursi.setText(ix + "." + iy);
                btnkursi.setForeground(Color.WHITE);
                btnkursi.setFont(new Font("Serial", Font.PLAIN, 13));
                btnkursi.setBackground(Color.RED);
                btnkursi.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        JToggleButton thisToggleBtn = (JToggleButton) e.getSource();
                        String nama = thisToggleBtn.getText();
                        System.out.println("Kursi " + nama + " Dipilih");
                        if (e.getStateChange() == ItemEvent.SELECTED) {
                            BookingTicketView.this.kursi.add(nama);
                            thisToggleBtn.setBackground(Color.GREEN);
                            thisToggleBtn.setForeground(Color.BLACK);
                        } else {
                            BookingTicketView.this.kursi.remove(String.valueOf(nama));
                            thisToggleBtn.setBackground(Color.RED);
                            thisToggleBtn.setForeground(Color.WHITE);
                        }
                        String delim = "-";
                        String res = String.join(delim, BookingTicketView.this.kursi);
                        BookingTicketView.this.tampilKursi.setText(res);
                    }
                });
                this.getPanelKursi().add(btnkursi, new AbsoluteConstraints(h, w, 50, 30));
                w += 50;
                this.revalidate();
            }
            h += 60;
            w = 80;
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public JTextArea getTampilKursi() {
        return tampilKursi;
    }

    public void setTampilKursi(JTextArea tampilKursi) {
        this.tampilKursi = tampilKursi;
    }

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
            java.util.logging.Logger.getLogger(BookingTicketView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookingTicketView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookingTicketView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookingTicketView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookingTicketView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelHarga;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelKursi;
    private javax.swing.JPanel panelTampilKursi;
    private javax.swing.JPanel panelUtama;
    private javax.swing.JTextArea tampilKursi;
    // End of variables declaration//GEN-END:variables
}
