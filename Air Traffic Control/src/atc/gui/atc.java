/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.gui;

import SysBar.*;
import gov.nasa.worldwind.*;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Path;
import gov.nasa.worldwind.render.ShapeAttributes;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author johan
 */
public class atc {

    private static final String AppName = "Airtraffic Control Centre";

    private static class AppFrame extends javax.swing.JFrame implements ActionListener {

        private static MenuBar menuBar;

        public AppFrame() {
            WorldWindowGLCanvas wwd = new WorldWindowGLCanvas();
            wwd.setPreferredSize(new java.awt.Dimension(1280, 720));
            this.getContentPane().add(wwd, java.awt.BorderLayout.CENTER);
            this.pack();
            this.setSize(1280, 720);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setTitle(AppName);
            BasicModel bModel = new BasicModel();
            wwd.setModel(bModel);

            menuBar = new MenuBar();
            this.getContentPane().add(menuBar, java.awt.BorderLayout.WEST);

            // Testing items, to be removed!
            menuBar.addItem(new Button("Start", Color.BLUE, 0, "F", MenuBar.Type.NORMAL)).addActionListener(this);
            menuBar.addItem(new Button("Add Flight", Color.GREEN, 0, "", MenuBar.Type.NORMAL)).addActionListener(this);
            menuBar.addItem(new Button("Crash Flight", Color.RED, 0, "", MenuBar.Type.NORMAL)).addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Button menuItem = (Button) e.getSource();
            switch (menuItem.getTitle()) {
                case "Start":
                    System.out.println("Start...");
                    break;
                case "Add Flight":
                    break;
                case "Crash Flight":
                    break;
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Object[] options = {"Override", "Cancel"};

        if (Configuration.isMacOS()) {
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", AppName);
        }

        // Because the jogl.jar libary does not (yet) play nice to 64 bit systems
        // we need to check whether the system uses 32 bit JVM.
        switch (System.getProperty("sun.arch.data.model").toString()) {
            case "32":
                System.out.println("Detecting a 32 bit Java virtual machine, all fine.");
                break;
            case "64":
                System.out.println("Detecting a 64 bit Java virtual machine, oh oh!");
                System.out.println("Please use a 32 bit JVM.");
                int n1 = JOptionPane.showOptionDialog(null,
                        "You seem to be running a 64 bit version of the Java virtual machine."
                        + "\nThe included JOGL libary doesn't work with 64 bit JVMs."
                        + "\nWould you like to ingore this warning?",
                        "Wrong architecture!",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, // This is for custom icon
                        options, // The titles of buttons
                        options[0]); // Default button title
                if (n1 == 1) {
                    return;
                } else {
                    System.out.println("Overridden. Starting on 64 bit JVM. Stay safe.");
                }
                break;
            default:
                System.out.println("Unkown architecture detected: " + System.getProperty("sun.arch.data.model").toString());
                int n2 = JOptionPane.showOptionDialog(null,
                        "You seem to be running an unknown architecture. (" + System.getProperty("sun.arch.data.model").toString() + ")"
                        + "\nWe cannot guarantee the safety of the universe when the application starts, so please contact Support."
                        + "\nWould you like to ingore this warning?",
                        "Wrong architecture!",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null, // This is for custom icon
                        options, // The titles of buttons
                        options[0]); // Default button title
                if (n2 == 1) {
                    return;
                } else {
                    System.out.println("Overridden. Stay safe.");
                }
                break;
        }

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                // Create an AppFrame and immediately make it visible. As per Swing convention, this
                // is done within an invokeLater call so that it executes on an AWT thread.
                new AppFrame().setVisible(true);
            }
        });

    }
}
