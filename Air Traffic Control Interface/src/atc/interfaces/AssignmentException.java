/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atc.interfaces;

/**
 * Exception given if the ACC gives an assignment to the airplane that is not possible to fullfill.
 * @author Henk
 */
public class AssignmentException extends Exception {
    String WrongAssignment;
    
    public AssignmentException() {
        super();
        WrongAssignment = "unknown";
    }
    
    /**
     * Constructor for the exception when the ACC is giving a wrong assignment.
     * @param err is what the air traffic controller done wrong. 
     */
    public AssignmentException(String err) {
        super(err);
        WrongAssignment = err;
    }

    public String getError() {
        return WrongAssignment;
    }
}