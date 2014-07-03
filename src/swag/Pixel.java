package swag;

import java.util.Vector;

public class Pixel {
	
	
	private int xLoc;
	private int yLoc;	
	Vector<LivingBeing> livingBeingVector;
	Vector<Pixel> pixelNeighborsVector;
	private String type;
	
	Pixel(){
		
	}
	
	public void setXLoc(int x){
		this.xLoc = x;
	}
	
	public void setYLoc(int y){
		this.yLoc = y;
	}
	
	public String getType(){
		return this.type;
	}
	
}
