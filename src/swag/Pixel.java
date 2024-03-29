package swag;

import java.util.Vector;

public class Pixel {
	
	
	int xLoc;
	int yLoc;	
	int index;
	Vector<LivingBeing> livingBeing = new Vector<LivingBeing>();
	Vector<Pixel> pixelNeighbors;
	String type;
	
	Pixel(){
		 pixelNeighbors = new Vector<Pixel>();
		 xLoc = 0;
		 yLoc = 0;
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
	public int getxLoc(){
		return this.xLoc;
	}
	public int getyLoc(){
		return this.yLoc;
	}
	public int getIndex(){
		return this.index;
	}
}
