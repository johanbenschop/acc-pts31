/*
 * jfSearchAirport.java
 *
 * Created on 6-okt-2011, 12:44:26
 */
package atc.gui;

import atc.interfaces.*;
import atc.logic.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.*;
import javax.swing.table.*;

/**
 *
 * @author Johan & Mateusz
 */
public class jfSelectAirport extends javax.swing.JDialog {

    private IAirport airport;
    private ListIterator<IAirport> airports;
    private ListIterator<IAirport> airports2;
    private Vector<String> columnNames = new Vector<>(); // Sigh to using an obsolite collection
    private Vector<Vector> data = new Vector<>();
    private boolean closed;

    /** Creates new form jfSearchAirport */
    public jfSelectAirport(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        airports = atc2.airspace.GetAirports();
        airports2 = atc2.airspace.GetAirports();
        createAirportList();

        columnNames.addElement("Airport ID");
        columnNames.addElement("Name");
        columnNames.addElement("City");
        columnNames.addElement("Country");
        columnNames.addElement("IATA/FAA");
        columnNames.addElement("ICAO");

        TableModel model = new DefaultTableModel(data, columnNames);
        jTable.setModel(model);
        //DefaultTableModel's setDataVector(Object[][] dataVector, Object[] columnIdentifiers) 

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelSearchFor = new javax.swing.JPanel();
        tfAirportName = new javax.swing.JTextField();
        tfCity = new javax.swing.JTextField();
        tfAirportID = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfCountry = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfIATA_FAA = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tfICAO = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        btnSelect = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Select Airport");

        jPanelSearchFor.setBorder(javax.swing.BorderFactory.createTitledBorder(" Search for "));
        jPanelSearchFor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanelSearchFor.setPreferredSize(new java.awt.Dimension(600, 150));

        tfAirportName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfSearchKeyTyped(evt);
            }
        });

        tfCity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfSearchKeyTyped(evt);
            }
        });

        tfAirportID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfSearchKeyTyped(evt);
            }
        });

        jLabel1.setText("Airport name");

        jLabel2.setText("Airport ID");

        jLabel3.setText("City");

        jLabel4.setText("Country");

        tfCountry.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfSearchKeyTyped(evt);
            }
        });

        jLabel5.setText("IATA/FAA");

        tfIATA_FAA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfSearchKeyTyped(evt);
            }
        });

        jLabel6.setText("ICAO");

        tfICAO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfSearchKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanelSearchForLayout = new javax.swing.GroupLayout(jPanelSearchFor);
        jPanelSearchFor.setLayout(jPanelSearchForLayout);
        jPanelSearchForLayout.setHorizontalGroup(
            jPanelSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSearchForLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSearchForLayout.createSequentialGroup()
                        .addComponent(tfAirportName, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanelSearchForLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(165, 165, 165))
                    .addGroup(jPanelSearchForLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(118, 118, 118))
                    .addGroup(jPanelSearchForLayout.createSequentialGroup()
                        .addComponent(tfCountry, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanelSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelSearchForLayout.createSequentialGroup()
                        .addGroup(jPanelSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfIATA_FAA, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfICAO, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSearchForLayout.createSequentialGroup()
                        .addGroup(jPanelSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfCity, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(tfAirportID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanelSearchForLayout.setVerticalGroup(
            jPanelSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSearchForLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanelSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfAirportName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfAirportID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelSearchForLayout.createSequentialGroup()
                        .addGroup(jPanelSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addGroup(jPanelSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelSearchForLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(tfCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelSearchForLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfIATA_FAA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(tfICAO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(" Search results (0 airports found) "));

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null}
            },
            new String [] {
                "null"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable.setColumnSelectionAllowed(true);
        jTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable);
        jTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSelect.setText("Select");
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelSearchFor, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSelect)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelSearchFor, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelect)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
    }//GEN-LAST:event_btnSelectActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        closed = true;
        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void tfSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchKeyTyped
        data.clear(); // Empty the data so we can get the limited results in.
        airports = atc2.airspace.GetAirports(); // we must get an new iterator, since the previus one is empty.
        airports2 = null;
        createAirportList();

        while (airports.hasNext()) {
            IAirport iter = airports.next();

            // Filter results bases on Airport ID
            try {
                if (evt.getComponent() == tfAirportID) {
                    if (evt.getKeyChar() == '\b') {
                        tfAirportID.setText(tfAirportID.getText().substring(0, tfAirportID.getText().length()));
                        if (iter.getAirportID() != Integer.parseInt(tfAirportID.getText())) {
                            continue;
                        }
                    } else {
                        if (iter.getAirportID() != Integer.parseInt(tfAirportID.getText() + evt.getKeyChar())) {
                            continue;
                        }
                    }
                }
            } catch (NumberFormatException e) {
                tfAirportID.setText("");
            }

            if (evt.getComponent() == tfAirportName) {
                if (evt.getKeyChar() == '\b') {
                    tfAirportName.setText(tfAirportName.getText().substring(0, tfAirportName.getText().length()));
                    if (!iter.getAirportName().contains(tfAirportName.getText())) {
                        continue;

                    }
                } else {
                    if (!iter.getAirportName().contains(tfAirportName.getText() + evt.getKeyChar())) {
                        continue;
                    }
                }
            }

            if (evt.getComponent() == tfCountry) {

                if (evt.getKeyChar() == '\b') {
                    tfCountry.setText(tfCountry.getText().substring(0, tfCountry.getText().length()));
                    if (!iter.getCountry().contains(tfCountry.getText())) {
                        continue;
                    }
                } else {
                    if (!iter.getCountry().contains(tfCountry.getText() + evt.getKeyChar())) {
                        continue;
                    }
                }
            }

            if (evt.getComponent() == tfCity) {
                if (evt.getKeyChar() == '\b') {
                    tfCity.setText(tfCity.getText().substring(0, tfCity.getText().length()));
                    if (!iter.getCity().contains(tfCity.getText())) {
                        continue;
                    }
                } else {
                    if (!iter.getCity().contains(tfCity.getText() + evt.getKeyChar())) {
                        continue;
                    }
                }
            }


            if (evt.getComponent() == tfIATA_FAA) {
                if (evt.getKeyChar() == '\b') {
                    tfIATA_FAA.setText(tfIATA_FAA.getText().substring(0, tfIATA_FAA.getText().length()));
                    if (!iter.getIATA_FAA().contains(tfIATA_FAA.getText())) {
                        continue;
                    }
                } else {
                    if (!iter.getIATA_FAA().contains(tfIATA_FAA.getText() + evt.getKeyChar())) {
                        continue;
                    }
                }
            }

            if (evt.getComponent() == tfICAO) {
                if (evt.getKeyChar() == '\b') {
                    tfICAO.setText(tfICAO.getText().substring(0, tfICAO.getText().length()));
                    if (!iter.getICAO().contains(tfICAO.getText())) {
                        continue;
                    }
                } else {
                    if (!iter.getICAO().contains(tfICAO.getText() + evt.getKeyChar())) {
                        continue;
                    }
                }
            }
            fillVector(iter);
        }
        fillTable();
    }//GEN-LAST:event_tfSearchKeyTyped

    public void fillVector(IAirport iter) {
        Vector<String> row = new Vector<>();
        row.addElement(String.valueOf(iter.getAirportID()));
        row.addElement(iter.getAirportName());
        row.addElement(iter.getCity());
        row.addElement(iter.getCountry());
        row.addElement(iter.getIATA_FAA());
        row.addElement(iter.getICAO());
        data.addElement(row);
    }

    public void fillTable() {
        TableModel model = new DefaultTableModel(data, columnNames);

        jTable.setModel(model);

        jTable.setColumnSelectionAllowed(
                false);
        jTable.setRowSelectionAllowed(
                true);
    }

    /**
     * Gets the value of this dialog.
     * @return Airport
     */
    IAirport getValue() {
        setVisible(true);
        if (!closed) {
            int id = Integer.parseInt((String) data.get(jTable.getSelectedRow()).get(0));
            airport = atc2.airspace.GetAirport(id);
            return airport;
        }
        return null;
    }

    private ListIterator<IAirport> createAirportList() {
        if (atc2.airspace.getOnlyOneACC() == true) {
            airports2 = atc2.airspace.getAirportCTA(atc2.airspace.getCurrentACC().GetCTA().getSector()).listIterator();
            airports = airports2;
        } else if (atc2.airspace.getOnlyOneACC() == false) {
            return airports;
        }
        return airports;
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
            java.util.logging.Logger.getLogger(jfSelectAirport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfSelectAirport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfSelectAirport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfSelectAirport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                jfSelectAirport dialog = new jfSelectAirport(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSelect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelSearchFor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    private javax.swing.JTextField tfAirportID;
    private javax.swing.JTextField tfAirportName;
    private javax.swing.JTextField tfCity;
    private javax.swing.JTextField tfCountry;
    private javax.swing.JTextField tfIATA_FAA;
    private javax.swing.JTextField tfICAO;
    // End of variables declaration//GEN-END:variables
}