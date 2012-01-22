/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import atc.interfaces.*;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import javax.naming.*;
import javax.swing.JFrame;

/**
 *
 * @author Henk
 */
public class ATCServer extends JFrame {

    public ATCServer() {
        try {
            LocateRegistry.createRegistry(1099);
            IAirspace airspace = new Airspace();
            Context namingContext = new InitialContext();
            namingContext.bind("rmi:ATCServer", airspace);
            init();
        } catch (RemoteException re) {
            System.out.println("Problem with instantiation of server " + re.toString());
        } catch (NamingException ne) {
            System.out.println("Problem with the naming of server " + ne.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ATCServer();
    }
    
    private void init() throws Exception {
		setSize(250, 0);
		setTitle("ATC Server");
		setVisible(true);
                addWindowListener(new java.awt.event.WindowAdapter() {
                
                public void windowClosing(WindowEvent winEvt) {
                System.exit(0);
            }
        });
    }
}
