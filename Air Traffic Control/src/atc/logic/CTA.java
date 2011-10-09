package atc.logic;

public class CTA {

	private GeoLocation location;
	private double width;
	private double length;
        
        public CTA(GeoLocation location, double width, double length) {
            this.location = location;
            this.width = width;
            this.length = length;            
        }

	/**
	 * 
	 * @return 
	 */
	public void GetActiveAirplane() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @return 
	 */
	public void DetectAirplane() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @return 
	 */
	public void Collision() {
		throw new UnsupportedOperationException();
	}

}