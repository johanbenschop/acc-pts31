/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.logic;

import atc.interfaces.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import javax.naming.*;

/**
 *
 * @author Henk
 */
public class ATCServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NamingException {
        try {
            LocateRegistry.createRegistry(1099);
            IAirspace airspace = new Airspace();
            Context namingContext = new InitialContext();
            namingContext.bind("rmi:ATCServer", airspace);

        } catch (RemoteException re) {
            System.out.println("Problem with instantiation of server " + re.toString());
        }
    }
}
