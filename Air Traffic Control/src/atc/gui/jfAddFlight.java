/*
 * jfAddFlight.java
 *
 * Created on 6-okt-2011, 12:39:16
 */
package atc.gui;

import atc.logic.*;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Mateusz
 */
public class jfAddFlight extends javax.swing.JDialog {

    private AirplaneFactory airplane;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private GregorianCalendar arrivalDate;
    private GregorianCalendar departureDate;

    /** Creates new form jfAddFlight */
    public jfAddFlight(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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
        lblDepartureTime = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        tfAirplane = new javax.swing.JTextField();
        btnSelectAirplane = new javax.swing.JButton();
        tfDepartureAirport = new javax.swing.JTextField();
        tfArrivalAirport = new javax.swing.JTextField();
        btnSelectAirportDep = new javax.swing.JButton();
        btnSelectAirportArr = new javax.swing.JButton();
        dpDateDep = new org.jdesktop.swingx.JXDatePicker();
        spHoursDep = new javax.swing.JSpinner(new SpinnerNumberModel(00, 00, 23, 1));
        spMinutesDep = new javax.swing.JSpinner(new SpinnerNumberModel(01, 01, 60, 1));
        spSecondsDep = new javax.swing.JSpinner(new SpinnerNumberModel(01, 01, 60, 1));
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dpDateArr = new org.jdesktop.swingx.JXDatePicker();
        lblDepartureTime1 = new javax.swing.JLabel();
        spHoursArr = new javax.swing.JSpinner(new SpinnerNumberModel(00, 00, 23, 1));
        jLabel4 = new javax.swing.JLabel();
        spMinutesArr = new javax.swing.JSpinner(new SpinnerNumberModel(01, 01, 60, 1));
        jLabel5 = new javax.swing.JLabel();
        spSecondsArr = new javax.swing.JSpinner(new SpinnerNumberModel(01, 01, 60, 1));
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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

        tfDepartureAirport.setEditable(false);

        tfArrivalAirport.setEditable(false);

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

        spHoursDep.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setText("Hours");

        jLabel2.setText("Minutes");

        jLabel3.setText("Seconds");

        lblDepartureTime1.setText("Arrival time & date");

        jLabel4.setText("Hours");

        jLabel5.setText("Minutes");

        jLabel6.setText("Seconds");

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
                                        .addComponent(tfAirplane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                                        .addComponent(tfDepartureAirport, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                                        .addComponent(tfArrivalAirport, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(dpDateDep, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(spHoursDep, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel1))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel2)
                                                .addComponent(spMinutesDep, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel3)
                                                .addComponent(spSecondsDep, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                            .addContainerGap(483, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(dpDateArr, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblDepartureTime1)
                                    .addGap(73, 73, 73)))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(spHoursArr, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(spMinutesArr, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(spSecondsArr, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(159, 159, 159)))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDepartureTime)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dpDateDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spHoursDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spMinutesDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spSecondsDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDepartureTime1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dpDateArr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spSecondsArr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spMinutesArr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spHoursArr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddFlight)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectAirplaneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectAirplaneActionPerformed
        // TODO add your handling code here:
        this.airplane = new jfSelectAirplane(null, true).getValue();
        if (airplane != null) {
            tfAirplane.setText(airplane.ToString());
        } else {
            tfAirplane.setText("No airplane selected!");
        }
    }//GEN-LAST:event_btnSelectAirplaneActionPerformed

    private void btnSelectAirportDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectAirportDepActionPerformed
        // TODO add your handling code here:
        this.departureAirport = new jfSelectAirport(null, true).getValue();
        if (departureAirport != null) {
            tfDepartureAirport.setText(departureAirport.ToString());
        } else {
            tfDepartureAirport.setText("No airport selected!");
        }
    }//GEN-LAST:event_btnSelectAirportDepActionPerformed

    private void btnSelectAirportArrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectAirportArrActionPerformed
        // TODO add your handling code here:
        this.arrivalAirport = new jfSelectAirport(null, true).getValue();
        if (arrivalAirport != null) {
            tfArrivalAirport.setText(arrivalAirport.ToString());
        } else {
            tfArrivalAirport.setText("No airport selected!");
        }
    }//GEN-LAST:event_btnSelectAirportArrActionPerformed

    private void btnAddFlightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFlightActionPerformed
        // TODO add your handling code here:
        try {
            this.arrivalDate = new GregorianCalendar(dpDateArr.getDate().getYear(),
                    dpDateArr.getDate().getMonth(),
                    dpDateArr.getDate().getDay(),
                    (int) spHoursArr.getValue(),
                    (int) spMinutesArr.getValue(),
                    (int) spSecondsArr.getValue());

            this.departureDate = new GregorianCalendar(dpDateDep.getDate().getYear(),
                    dpDateDep.getDate().getMonth(),
                    dpDateDep.getDate().getDay(),
                    (int) spHoursDep.getValue(),
                    (int) spMinutesDep.getValue(),
                    (int) spSecondsDep.getValue());

            atc2.airspace.getACC(0).CreateFlight(airplane, departureAirport, arrivalAirport, arrivalDate, departureDate);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Not all fields have been satisfied.");
        }
        
        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
    }//GEN-LAST:event_btnAddFlightActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
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
                jfAddFlight dialog = new jfAddFlight(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddFlight;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSelectAirplane;
    private javax.swing.JButton btnSelectAirportArr;
    private javax.swing.JButton btnSelectAirportDep;
    private org.jdesktop.swingx.JXDatePicker dpDateArr;
    private org.jdesktop.swingx.JXDatePicker dpDateDep;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblAirplane;
    private javax.swing.JLabel lblArrivalAirport;
    private javax.swing.JLabel lblDepartureAirport;
    private javax.swing.JLabel lblDepartureTime;
    private javax.swing.JLabel lblDepartureTime1;
    private javax.swing.JSpinner spHoursArr;
    private javax.swing.JSpinner spHoursDep;
    private javax.swing.JSpinner spMinutesArr;
    private javax.swing.JSpinner spMinutesDep;
    private javax.swing.JSpinner spSecondsArr;
    private javax.swing.JSpinner spSecondsDep;
    private javax.swing.JTextField tfAirplane;
    private javax.swing.JTextField tfArrivalAirport;
    private javax.swing.JTextField tfDepartureAirport;
    // End of variables declaration//GEN-END:variables
}
