package atc.logic;

import java.util.ArrayList;

public class Airplane extends AirplaneFactory {

	private int Direction;
	private double Speed;
	private int MaxSpeed;
	private int MinSpeed;
	private int Weight;
	private String Type;
	private String Manufacturer;
	private int PlaneHeight;
	private int PlaneWidth;
	private int PlaneLength;
	private int MaxFuel;
	private int CurrentFuel;
	private int FuelUsage;
        private double Altitude;
        private ArrayList<Runway> Runways;

        
        public Airplane(int MaxSpeed, int MinSpeed, int Weight, String Type, String Manufacturer, 
                int PlaneHeight, int PlaneWidth, int PlaneLength, int MaxFuel, int FuelUsage, int Direction, double Speed, int CurrentFuel, double Altitude)
        {
            super(MaxSpeed, MinSpeed, Weight, Type, Manufacturer, PlaneHeight, PlaneWidth, PlaneLength, MaxFuel, FuelUsage);
            this.Direction = Direction;
            this.Speed = Speed;
            this.CurrentFuel = CurrentFuel;
            this.Altitude = Altitude;
        }
        
	/**
	 * 
	 * @return 
	 */
	public void Fly(int direction, int speed, double altitude) {
		this.Direction = direction;
                this.Speed = speed;
                this.Altitude = altitude;
	}
        
        
        public void SetAimedSpeed(double speed)
        {
            this.Speed = speed;
        }
        
        public void SetAimedDirection(int direction)
        {
            this.Direction = direction;
        }
        
        public void SetAimedAltitude(double altitude)
        {
            this.Altitude = altitude;
        }
        
        public void SetCurrentFuel()
        {
            this.CurrentFuel = (this.MaxFuel - this.FuelUsage);
        }
        

    public double getAltitude() {
        return Altitude;
    }

    public int getCurrentFuel() {
        return CurrentFuel;
    }

    public int getDirection() {
        return Direction;
    }

    public double getSpeed() {
        return Speed;
    }

       

    void Landing(Runway r, int direction) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void TakeOff(Runway r, int direction, double height, double speed) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}