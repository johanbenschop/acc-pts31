/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * jfAddFlight.java
 *
 * Created on 6-okt-2011, 12:39:16
 */
package atc.gui;

/**
 *
 * @author Mateusz
 */
public class jfAddFlight extends javax.swing.JFrame {

    /** Creates new form jfAddFlight */
    public jfAddFlight() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAddFlight = new javax.swing.JButton();
        lblAirplane = new javax.swing.JLabel();
        lblDepartureAirport = new javax.swing.JLabel();
        lblArrivalAirport = new javax.swing.JLabel();
        lblArrivalTime = new javax.swing.JLabel();
        lblDepartureTime = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        tfAirplane = new javax.swing.JTextField();
        btnSelectAirplane = new javax.swing.JButton();
        tfDepartureAirport = new javax.swing.JTextField();
        tfArrivalAirport = new javax.swing.JTextField();
        btnSelectAirportDep = new javax.swing.JButton();
        btnSelectAirportArr = new javax.swing.JButton();
        tfDepartureDate = new javax.swing.JTextField();
        tfArrivalDate = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Add Flight");

        btnAddFlight.setText("Add Flight");
        btnAddFlight.setName(""); // NOI18N
        btnAddFlight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFlightActionPerformed(evt);
            }
        });

        lblAirplane.setText("Airplane");

        lblDepartureAirport.setText("Departure airport");

        lblArrivalAirport.setText("Arrival airport");

        lblArrivalTime.setText("Arrival time & date");

        lblDepartureTime.setText("Departure time & date");

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        tfAirplane.setEditable(false);

        btnSelectAirplane.setText("Select Airplane");
        btnSelectAirplane.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectAirplaneActionPerformed(evt);
            }
        });

        btnSelectAirportDep.setText("Select Airport");
        btnSelectAirportDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectAirportDepActionPerformed(evt);
            }
        });

        btnSelectAirportArr.setText("Select Airport");
        btnSelectAirportArr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectAirportArrActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblAirplane)
                                .addComponent(lblDepartureAirport)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(tfAirplane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                                        .addComponent(tfDepartureAirport, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                                        .addComponent(tfArrivalAirport, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                                        .addComponent(tfDepartureDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                                        .addComponent(tfArrivalDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnSelectAirportDep)
                                        .addComponent(btnSelectAirplane)
                                        .addComponent(btnSelectAirportArr))))
                            .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblArrivalAirport)
                            .addGap(292, 292, 292))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblDepartureTime)
                            .addContainerGap(314, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblArrivalTime)
                            .addContainerGap(344, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddFlight)
                        .addContainerGap())))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnSelectAirplane, btnSelectAirportArr, btnSelectAirportDep});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAirplane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfAirplane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectAirplane))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDepartureAirport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfDepartureAirport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectAirportDep))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblArrivalAirport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfArrivalAirport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectAirportArr))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDepartureTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfDepartureDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblArrivalTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfArrivalDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddFlight)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectAirplaneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectAirplaneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSelectAirplaneActionPerformed

    private void btnSelectAirportDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectAirportDepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSelectAirportDepActionPerformed

    private void btnSelectAirportArrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectAirportArrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSelectAirportArrActionPerformed

    private void btnAddFlightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFlightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddFlightActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelActionPerformed

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
            java.util.logging.Logger.getLogger(jfAddFlight.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfAddFlight.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfAddFlight.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfAddFlight.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new jfAddFlight().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddFlight;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSelectAirplane;
    private javax.swing.JButton btnSelectAirportArr;
    private javax.swing.JButton btnSelectAirportDep;
    private javax.swing.JLabel lblAirplane;
    private javax.swing.JLabel lblArrivalAirport;
    private javax.swing.JLabel lblArrivalTime;
    private javax.swing.JLabel lblDepartureAirport;
    private javax.swing.JLabel lblDepartureTime;
    private javax.swing.JTextField tfAirplane;
    private javax.swing.JTextField tfArrivalAirport;
    private javax.swing.JTextField tfArrivalDate;
    private javax.swing.JTextField tfDepartureAirport;
    private javax.swing.JTextField tfDepartureDate;
    // End of variables declaration//GEN-END:variables
}
