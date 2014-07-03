package swag;

public class Sewer {
	
	private int startXLocation;
	private int startYLocation;
	private int endXLocation;
	private int endYLocation;
	private int sewerThickness;
	
	Sewer(int startX, int startY, int endX, int endY){
		this.startXLocation = startX;
		this.startYLocation = startY;
		this.endXLocation = endX;
		this.endYLocation = endY;
		
		sewerThickness = 5;
	}
	
	public int getStartXLocation(){
		return this.startXLocation;
	}
	
	public int getStartYLocation(){
		return this.startYLocation;
	}
	
	public int getEndXLocation(){
		return this.endXLocation;
	}
	
	public int getEndYLocation(){
		return this.endYLocation;
	}
	
	public int getSewerThickness(){
		return this.sewerThickness;
	}

}
