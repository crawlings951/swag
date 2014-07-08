package swag;

public abstract class LivingBeing {
	
	private int homeX; 
	private int homeY;
	private int currentX;
	private int currentY;
	private boolean isInfected;
	private int nextX;
	private int nextY;
	private int score;
	public int index;
	
	LivingBeing(){
		
		this.isInfected = false;
		
	}
	
	//Get Methods
	public int getIndex(){
		return this.index;
	}

	public int getScore(){
		return this.score;
	}
	
	public int getHomeX(){
		return this.homeX;
	}
	
	public int getHomeY(){
		return this.homeY;
	}
	
	public int getCurrentX(){
		return this.currentX;
	}
	
	public int getCurrentY(){
		return this.currentY;
	}
	
	public boolean getInfected(){
		return this.isInfected;
	}

	public int getNextX(){
		return this.nextX;
	}
	
	public int getNextY(){
		return this.nextY;
	}
	
	//Set Methods
	public void setIndex(int index){
		this.index = index;
	}
	public void setScore(int score)
	{
		this.score = score;
	}
	public void setCurrentX(int x){
		this.currentX = x;
	}
	
	public void setCurrentY(int y){
		this.currentY = y;
	}
	
	public void setInfected(boolean b){
		this.isInfected = b;
	}
	
	//Other Methods
	public abstract int calculateNextMove();
	

	public void diseaseTransmitted(LivingBeing lb){
		if(!this.getInfected() && lb.getInfected()){
			//The "lb.score" part is up for interpretation/can be changed
			if(this.score < lb.score){
				this.isInfected = true;
			}
			//If this.score < virus strength (package var) then you get the virus
			//If this.score > virus strength, then another loop where you calc a random
			//number between 0 and 1 and if it's less than 0.2 (less than the package contageon value)
			//then they get it 
			
		}
	}
	
	public void calculateScore(){
		
	}

}
