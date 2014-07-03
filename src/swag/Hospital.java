package swag;

public class Hospital {
	
	private int hospitalXLocation;
	private int hospitalYLocation;
	private int hospitalWidth;
	private int hospitalHeight;
	private int capacity;
	private int numOfOccupants;
	
	Hospital(int x, int y, int w, int h){
		this.hospitalXLocation = x;
		this.hospitalYLocation = y;
		this.hospitalWidth = w;
		this.hospitalHeight = h;
	}
	
	//We technically don't need any set variables for x,y,w,h because of the consturctor
	
	//We need to write a method that will incrament the number of patients but I'm not sure how
	//we want to do this in practice so I haven't included it for now

	
	public int getXLocation(){
		return this.hospitalXLocation;
	}
	
	public int getYLocation(){
		return this.hospitalYLocation;
	}
	
	public int getHospWidth(){
		return this.hospitalWidth;
	}
	
	public int getHospHeight(){
		return this.hospitalHeight;
	}
	
	public boolean atCapacitance(){
		if(this.capacity == this.numOfOccupants){
			return true;
		}
		else{
			return false;
		}
	}

}
