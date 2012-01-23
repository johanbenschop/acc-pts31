/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.interfaces;

import java.rmi.RemoteException;

/**
 *
 * @author Henk
 */
public interface ICollision {
    
    public void colldetect() throws RemoteException;

    IAirplane getCrashobject() throws RemoteException;

    IAirplane getTarget() throws RemoteException;
    
}
