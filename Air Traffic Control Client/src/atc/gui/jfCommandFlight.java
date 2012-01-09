/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * jfCommandFlight.java
 *
 * Created on 6-okt-2011, 12:45:05
 */
package atc.gui;

import atc.interfaces.IAirplane.Statusses;
import atc.interfaces.*;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.Timer;
import javax.swing.JOptionPane;

/**
 *
 * @author Johan & Mateusz
 */
public class jfCommandFlight extends javax.swing.JDialog {

    private IFlightplan flightplan;
    private final Timer timer;
    private final DecimalFormat DF;

    /** Creates new form jfCommandFlight */
    public jfCommandFlight(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        Locale l = new Locale("en_US"); 
        NumberFormat NF = NumberFormat.getNumberInstance(l);
        DF = (DecimalFormat)NF;
        DF.applyPattern("#.##");
        
        this.timer = new Timer(50, new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                if (flightplan.getAirplane().getStatus() == Statusses.LANDING) {
                    btnLandFlight.setEnabled(false);
                }
                
                if (flightplan.getAirplane().getStatus() == Statusses.TAKINGOFF) {
                    btnTakeOffFlight.setEnabled(false);
                }
                
                if (flightplan.getAirplane().getStatus() == Statusses.HASLANDED) {
                    btChangeParameter.setEnabled(false);
                }
                
                updateLabels();
            }
        });
        timer.start();
    }

    public void setFlightplan(IFlightplan flightplan) {
        this.flightplan = flightplan;
        
        // Static
        jLabelFlightNumber.setText("Flight " + flightplan.getFlightnumber());
        jLabelAirplaneModel.setText(flightplan.getAirplane().getManufacturer() + " " + flightplan.getAirplane().getType());
        jLabelAirplaneWeight.setText("Weight: " + flightplan.getAirplane().getWeight());
        jLabelFlightAirports.setText(flightplan.getTakeoffAirport().getAirportName() + " - " + flightplan.getDestinationAirport().getAirportName());
        jLabelFlightCities.setText(flightplan.getTakeoffAirport().getCity() + " - " + flightplan.getDestinationAirport().getCity());
        jLabelMaxFuel.setText("Maximum fuel: " + flightplan.getAirplane().getMaxFuel());
        jLabelMaxSpeed.setText("Maximum speed: " + flightplan.getAirplane().getMaxSpeed());
        jLabelMinSpeed.setText("Minimum speed: " + flightplan.getAirplane().getMinSpeed());
        
        updateLabels();
        
        setVisible(true);
    }
    
    private void updateLabels() {
        // Dynamic
        jLabelAirplaneStatus.setText(flightplan.getAirplane().getStatus().toString());
        jLabelAltitude.setText("Altitude: " + DF.format(flightplan.getAirplane().getAltitude()));
        jLabelDistanceTraveled.setText("Distance traveled: " + DF.format(flightplan.getAirplane().getDistanceTravelled()));
        jLabelFuelFlowRate.setText("Fuel flow rate: " + DF.format(flightplan.getAirplane().getFuelUsage()));
        jLabelHeading.setText("Heading: " + DF.format(flightplan.getAirplane().getDirection()));
        jLabelRemainingFuel.setText("Remaining fuel: " + DF.format(flightplan.getAirplane().getCurrentFuel()));
        jLabelSpeed.setText("Speed: " + DF.format(flightplan.getAirplane().getSpeed()));
        jLabelCoordinates.setText("Coordinates: " + DF.format(flightplan.getAirplane().getLocation().getLatitude()) + ", " + DF.format(flightplan.getAirplane().getLocation().getLongitude()));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelFlightNumber = new javax.swing.JLabel();
        jLabelAirplaneModel = new javax.swing.JLabel();
        jLabelFlightCities = new javax.swing.JLabel();
        jLabelAirplaneStatus = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabelAltitude = new javax.swing.JLabel();
        jLabelHeading = new javax.swing.JLabel();
        jLabelSpeed = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabelMaxSpeed = new javax.swing.JLabel();
        jLabelMinSpeed = new javax.swing.JLabel();
        jLabelMaxFuel = new javax.swing.JLabel();
        jLabelAirplaneWeight = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabelCoordinates = new javax.swing.JLabel();
        jLabelDistanceTraveled = new javax.swing.JLabel();
        jLabelRemainingFuel = new javax.swing.JLabel();
        jLabelFuelFlowRate = new javax.swing.JLabel();
        jLabelFlightAirports = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        lblChangeSpeedTo = new javax.swing.JLabel();
        txtChangeSpeedTo = new javax.swing.JTextField();
        lblChangeHeightTo = new javax.swing.JLabel();
        tfFlightlevel = new javax.swing.JComboBox();
        lblChangeDirectionTo = new javax.swing.JLabel();
        txtChangeDirectionTo = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jButtonSetHeadingDestination = new javax.swing.JButton();
        jButtonSetHeadingArrival = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        btnTakeOffFlight = new javax.swing.JButton();
        btnLandFlight = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        btChangeParameter = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Information"));

        jLabelFlightNumber.setFont(new java.awt.Font("Ubuntu", 1, 48)); // NOI18N
        jLabelFlightNumber.setText("Flight 034");

        jLabelAirplaneModel.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabelAirplaneModel.setText("Airbus 380");

        jLabelFlightCities.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabelFlightCities.setText("Amsterdam - New York");

        jLabelAirplaneStatus.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabelAirplaneStatus.setText("LANDING");

        jPanel2.setBorder(null);

        jLabelAltitude.setText("Altitude: 312 feet");

        jLabelHeading.setText("Heading: 142°");

        jLabelSpeed.setText("Speed: 34 km/h");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelAltitude)
                    .addComponent(jLabelHeading)
                    .addComponent(jLabelSpeed))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelAltitude)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelHeading)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelSpeed)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(null);

        jLabelMaxSpeed.setText("Maximum speed: 100 km/h");

        jLabelMinSpeed.setText("Minimum speed: 10 km/h");

        jLabelMaxFuel.setText("Maximum fuel: 70 gallons");

        jLabelAirplaneWeight.setText("Weight: 530 pounds");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelMaxSpeed)
                    .addComponent(jLabelMinSpeed)
                    .addComponent(jLabelMaxFuel)
                    .addComponent(jLabelAirplaneWeight))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelMaxSpeed)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelMinSpeed)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelMaxFuel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelAirplaneWeight)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(null);

        jLabelCoordinates.setText("Coordinates: 34.23, 23.32");

        jLabelDistanceTraveled.setText("Distance traveled: 503 km");

        jLabelRemainingFuel.setText("Remaining fuel: 50 gallons");

        jLabelFuelFlowRate.setText("Fuel flow rate: 0.5 GPM");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelCoordinates)
                    .addComponent(jLabelDistanceTraveled)
                    .addComponent(jLabelRemainingFuel)
                    .addComponent(jLabelFuelFlowRate))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelCoordinates)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelDistanceTraveled)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelRemainingFuel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelFuelFlowRate)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabelFlightAirports.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabelFlightAirports.setText("Schiphol - Heathrow");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelFlightNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelFlightCities, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelFlightAirports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelAirplaneModel)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                        .addComponent(jLabelAirplaneStatus)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabelFlightNumber)
                .addGap(14, 14, 14)
                .addComponent(jLabelFlightCities)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelFlightAirports))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAirplaneModel)
                    .addComponent(jLabelAirplaneStatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Command Flight"));

        jPanel6.setBorder(null);

        lblChangeSpeedTo.setText("Change speed to: ");

        lblChangeHeightTo.setText("Change flight Level to: ");

        tfFlightlevel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Flighlevel One", "Flightlevel Two", "Flightlevel Three" }));

        lblChangeDirectionTo.setText("Change heading to: ");

        jLabel17.setText("km/h");

        jLabel18.setText("degrees");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblChangeSpeedTo)
                    .addComponent(lblChangeDirectionTo)
                    .addComponent(txtChangeDirectionTo)
                    .addComponent(tfFlightlevel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblChangeHeightTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtChangeSpeedTo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblChangeSpeedTo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtChangeSpeedTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblChangeHeightTo)
                .addGap(8, 8, 8)
                .addComponent(tfFlightlevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(lblChangeDirectionTo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtChangeDirectionTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(null);

        jLabel19.setText("Set heading towards");

        jButtonSetHeadingDestination.setText("Destination Airport");
        jButtonSetHeadingDestination.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetHeadingDestinationActionPerformed(evt);
            }
        });

        jButtonSetHeadingArrival.setText("Arrival Airport");
        jButtonSetHeadingArrival.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetHeadingArrivalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonSetHeadingDestination, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(jLabel19)
                    .addComponent(jButtonSetHeadingArrival, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonSetHeadingDestination)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSetHeadingArrival)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(null);

        btnTakeOffFlight.setText("Takeoff Flight");
        btnTakeOffFlight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTakeOffFlightActionPerformed(evt);
            }
        });

        btnLandFlight.setText("Land flight");
        btnLandFlight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLandFlightActionPerformed(evt);
            }
        });

        jLabel20.setText("Basic commands");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel20)
                        .addComponent(btnLandFlight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnTakeOffFlight, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTakeOffFlight)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLandFlight)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        btChangeParameter.setText("Change parameters");
        btChangeParameter.setName(""); // NOI18N
        btChangeParameter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btChangeParameterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(230, 230, 230))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btChangeParameter, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(btChangeParameter)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btChangeParameterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btChangeParameterActionPerformed
        if (!"".equals(txtChangeSpeedTo.getText())) {
            try {
                atc2.airspace.getCurrentACC().ChangeSpeed(Double.parseDouble(txtChangeSpeedTo.getText()), flightplan.getAirplane());
            } catch (AssignmentException ex) {             
            JOptionPane.showMessageDialog(this, "The given speed is not in the range of this airplane.", "Error speed range", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (!"".equals(txtChangeDirectionTo.getText())) {
            try {
                atc2.airspace.getCurrentACC().ChangeDirection(Double.parseDouble(txtChangeDirectionTo.getText()), flightplan.getAirplane());
            } catch (AssignmentException ex) {
             JOptionPane.showMessageDialog(this, "The given direction is not in the range", "Error direction range", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (!"".equals(tfFlightlevel.getSelectedItem())) {
            try {
                atc2.airspace.getCurrentACC().ChangeHeight(tfFlightlevel.getSelectedIndex() + 1, flightplan.getAirplane());
            } catch (AssignmentException ex) {
                JOptionPane.showMessageDialog(this, "The flightlevel is not correct", "Error incorrect flightlevel", JOptionPane.ERROR_MESSAGE);
            }
        }

        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
    }//GEN-LAST:event_btChangeParameterActionPerformed

    private void btnLandFlightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLandFlightActionPerformed
        // TODO add your handling code here:

        try {
            atc2.airspace.getCurrentACC().LandFlight(flightplan);
        } catch (AssignmentException ex) {
            JOptionPane.showMessageDialog(this, "There are currently no runways available at destination airport.");
        }
    }//GEN-LAST:event_btnLandFlightActionPerformed

    private void btnTakeOffFlightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTakeOffFlightActionPerformed
        flightplan.getAirplane().setStatus(Statusses.TAKINGOFF);
    }//GEN-LAST:event_btnTakeOffFlightActionPerformed

    private void jButtonSetHeadingDestinationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetHeadingDestinationActionPerformed
        double newHeading = IGeoLoc.CalcDirection(flightplan.getAirplane().getLocation(), flightplan.getDestinationAirport().getLocation());
        txtChangeDirectionTo.setText(String.valueOf(newHeading));
    }//GEN-LAST:event_jButtonSetHeadingDestinationActionPerformed

    private void jButtonSetHeadingArrivalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetHeadingArrivalActionPerformed
        double newHeading = IGeoLoc.CalcDirection(flightplan.getAirplane().getLocation(), flightplan.getTakeoffAirport().getLocation());
        txtChangeDirectionTo.setText(String.valueOf(newHeading));
    }//GEN-LAST:event_jButtonSetHeadingArrivalActionPerformed

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
            java.util.logging.Logger.getLogger(jfCommandFlight.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfCommandFlight.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfCommandFlight.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfCommandFlight.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                jfCommandFlight dialog = new jfCommandFlight(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btChangeParameter;
    private javax.swing.JButton btnLandFlight;
    private javax.swing.JButton btnTakeOffFlight;
    private javax.swing.JButton jButtonSetHeadingArrival;
    private javax.swing.JButton jButtonSetHeadingDestination;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabelAirplaneModel;
    private javax.swing.JLabel jLabelAirplaneStatus;
    private javax.swing.JLabel jLabelAirplaneWeight;
    private javax.swing.JLabel jLabelAltitude;
    private javax.swing.JLabel jLabelCoordinates;
    private javax.swing.JLabel jLabelDistanceTraveled;
    private javax.swing.JLabel jLabelFlightAirports;
    private javax.swing.JLabel jLabelFlightCities;
    private javax.swing.JLabel jLabelFlightNumber;
    private javax.swing.JLabel jLabelFuelFlowRate;
    private javax.swing.JLabel jLabelHeading;
    private javax.swing.JLabel jLabelMaxFuel;
    private javax.swing.JLabel jLabelMaxSpeed;
    private javax.swing.JLabel jLabelMinSpeed;
    private javax.swing.JLabel jLabelRemainingFuel;
    private javax.swing.JLabel jLabelSpeed;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lblChangeDirectionTo;
    private javax.swing.JLabel lblChangeHeightTo;
    private javax.swing.JLabel lblChangeSpeedTo;
    private javax.swing.JComboBox tfFlightlevel;
    private javax.swing.JTextField txtChangeDirectionTo;
    private javax.swing.JTextField txtChangeSpeedTo;
    // End of variables declaration//GEN-END:variables
}
