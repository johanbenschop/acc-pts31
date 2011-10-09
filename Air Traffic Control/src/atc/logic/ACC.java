package atc.logic;

import java.math.*;

public class ACC {

        /**************Datafields***********/
	private int ID;
        private CTA cta;
        
        /***************Constructor**********/
        /** 
         * An ACC is made with its own unique ID linked to a Control Area (CTA).
         * 
         * @Param ID: ID is the unique identification number for each Area Control Center(ACC).
         * 
         * @param CTA is the Control Area over wich this specific ACC has control.
         */
        public ACC(int ID, CTA CTA) {
            this.ID = ID;
            cta = CTA;
        }

        /**************Methods**************/
	public int GetID() {
		return ID;
	}
        
	public CTA GetCTA() {
		return cta;
	}

	/**
	 * Method to change the speed of the airplane. First its set to the Aimed
         * speed of the airplane so it can take its needed time to get to this speed
         * and doesnt suddenly change from 100 to 300 without taking time to do so.
         * 
         * @param speed is the desired new speed for the airplane.
         * 
         * @param a is the Airplane of wich you want to change the speed.
         * 
         * Question: Do we want to handle the not allowed assignements thru an exception
         * or do we want to return false as ive done now. If we do it thru an exception,
         * do we want to make one ourselves or is there an exception in java wich we
         * can use.
         * 
	 * @return true is returned when the speed has succesfully been change.
         * @return false is returned if the speed was above the planes maximum speed.
	 */
	public boolean ChangeSpeed(double speed, Airplane a) {
            if (speed < a.GetMaxSpeed) {
                a.SetAimedSpeed(speed);
                return true;
            } else {
                return false;
            }
	}

	/**
	 * Method to change the direction of the airplane. This is done thru setting
         * the aimed direction of the airplane so it has time to change the direction.
         * 
         * @param direction is the new aimed direction in degrees?
         * 
         * @param a is the airplane for wich the new direction is intended.
         * 
         * Question: Do we want to recalculate the direction in degrees to the direction
         * in radial or will we do that elsewhere as in before we invoke this method
         * or in the airplane class itself during the i guess fly method?
         * At the moment i have programmed it doing the calculation here. If we want
         * to move it the change calculation can be copied.
         * 
	 * @return true is returned if the new direction has succesfully been set.
         * @return false is when it was not possible to set the new direction.
	 */
	public boolean ChangeDirection(int direction, Airplane a) {
            if (direction < 360) {
                double r;
                r = direction / 180 * Math.PI;
		a.SetAimedDirection(r);
                return true;
            } else {
                return false;
            }
	}
        
        /**
         * 
         * @return
         */
        public boolean ChangeHeight(double height, Airplane a) {
                a.SetAimedHeight(height);
                return true;
        }

	/**
	 * 
	 * @return 
	 */
	public void GiveRunway(int direction, Runway r, Airplane a) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @return 
	 */
	public boolean GetAvailability(Runway r) {
		throw new UnsupportedOperationException();
	}
        
        /**
         * 
         * @return
         */
        public boolean CheckActivity() {
                throw new UnsupportedOperationException();
        } 

}