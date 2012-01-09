package atc.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.ListIterator;

/**
 *
 * @author Henk
 */
public interface IACC {

    /**
     * Method to change the direction of the airplane. This is done through setting
     * the aimed direction of the airplane so it has time to change the direction.
     *
     * @param direction is the new aimed direction in degrees
     *
     * @param a is the airplane for which the new direction is intended.
     *
     * Question: Do we want to recalculate the direction in degrees to the direction
     * in radial or will we do that elsewhere as in before we invoke this method
     * or in the airplane class itself during the I guess fly method?
     * At the moment I have programmed it doing the calculation here. If we want
     * to move it the change calculation can be copied.
     *
     * @return true is returned if the new direction has succesfully been set.
     *
     * @return false is when it was not possible to set the new direction.
     */
    void ChangeDirection(double direction, IAirplane a) throws AssignmentException;

    /**
     * Method to change the height of an airplane. This is done by setting the
     * aimed height and have the plane climb to this altitude in its own speed.
     *
     * @param height is the new altitude to which the airplane will climb.
     *
     * @param a is the airplane for whom this change is intended.
     *
     * Question: I seem to recall that the height is being set by flightlevels (1,2 or 3)
     * does height represent the new flightlevel to which the plane will climb or does it
     * actually represent an amount in feet? Height will be given in flightlevel then
     * recalculated to feet for the actual airplane.
     *
     * @return true if the change in altitude has succesfully been done.
     *
     * @return false if the change was incorrect and could not be made.
     */
    void ChangeHeight(int flightlevel, IAirplane a) throws AssignmentException;

    /**
     * Method to change the speed of the airplane. First its set to the Aimed
     * speed of the airplane so it can take its needed time to get to this speed
     * and does not suddenly change from 100 to 300 without taking time to do so.
     *
     * @param speed is the desired new speed for the airplane.
     *
     * @param a is the Airplane of which you want to change the speed.
     *
     * @return true is returned when the speed has succesfully been changed.
     *
     * @return false is returned if the speed was above/below the planes maximum/minimum speed.
     */
    void ChangeSpeed(double speed, IAirplane a) throws AssignmentException;

    /**
     * Method has to be called when assignmentexception is given on the
     * GiveLandingRunway method incase the runway is not available.
     *
     * @param a is the airplane that has to start circling the airport.
     */
    void CircleAirplane(IAirplane a);

    Boolean ContainsFlightplan(IFlightplan flightplan);

    /**
     * Method to create a flightplan
     *
     * @param a is an AirplaneFactory which contains the airplane
     *
     * @param start is an airport where the airplane takes off
     *
     * @param end is an airport where the airplane lands
     *
     * @param arrival is the date and time when the airplane arrives
     *
     * @param departure is the date and time when the airplane departures
     *
     * @param flightnumber is the flightnumber of the airplane
     */
    void CreateFlight(IAirplaneFactory a, IAirport start, IAirport end, GregorianCalendar arrival, GregorianCalendar departure);

    /**
     * Method to get the Airplane Factory
     *
     * @return airplane factory
     */
    IAirplaneFactory GetAirplaneFactory(int AirplaneFactoryID);

    /**
     * Method to get a CTA
     *
     * @return CTA
     */
    ICTA GetCTA();

    /**
     * Method to get the FlightController
     *
     * @return FlightController
     */
    IFC GetFlightController(int FlightControllerID);

    /**
     * Method to get ID of this ACC
     *
     * @return ID
     */
    int GetID();

    /**
     * Method to give the airplane the direction in which it has to approach
     * a runway.
     *
     * @param r is the runway on which the airplane has to land.
     *
     * @param a is the airplane to which this assignement is given.
     *
     * @param direction this is the direction in which the airplane will use for its final approach to the runway.
     *
     * @return true is given when the assignment has been succesfully transferred to the airplane.
     *
     * @return false is given when the assignement has not been succesfully transferred to the airplane.
     */
    void LandFlight(IFlightplan fp) throws AssignmentException;

    /**
     * Creates and adds a new FlightController and add it to the list of controller as well as return it.
     * @return FlightController
     * @deprecated
     */
    IFC addFlightController();

    /**
     * Creates and adds a new FlightController and add it to the list of controller.
     * @return FlightController
     */
    void addFlightController(IFC flightController);

    void addFlightPlan(IFlightplan flightplan);

    /**
     * Assign a controller to a flightplan based on the contollers busyness.
     * @param flightplan
     */
    void assignFlightToController(IFlightplan flightplan);

    ArrayList<IACC> getAdjacentACCList();

    /**
     * Method to get all Available Airplanes
     *
     * @return list of available airplanes
     */
    ListIterator<IAirplaneFactory> getAvailableAirplanes();

    ListIterator<IFC> getFlightControllers();

    /**
     * Method to get all Flightplans
     *
     * @return a list with all Flightplans
     */
    ListIterator<IFlightplan> getFlightplans();

    /**
     * Method to get list of flight controllers
     *
     * @return list of flight controllers
     */
    ArrayList<IFC> getfc();

    /**
     * Method to get list of flightplans
     *
     * @return list of flightplans
     */
    ArrayList<IFlightplan> getfp();

    /**
     * All airplanes in the AvailableAirplanes.dat list will be read into a list.
     */
    void loadAirplaneFactoryList() throws FileNotFoundException, IOException;

    void removeFlightController(IFC flightController);

    void removeFlightPlan(IFlightplan flightplan);

    void setAdjacentACCList(ArrayList<IACC> adjacentACCList);

    /**
     * Unassign the flight from the controller.
     * @param flightplan
     */
    void unassignFlightFromController(IFlightplan flightplan);
}