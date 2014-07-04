package swag;

import java.util.Vector;

public class Pixel {
	
	
	 int xLoc;
	int yLoc;	
	Vector<LivingBeing> livingBeing;
	Vector<Pixel> pixelNeighbors;
	String type;
	
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
	public void setType(String type){
		this.type = type;
	}
}
