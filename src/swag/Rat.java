package swag;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Rat extends LivingBeing implements Runnable{
	
	Random random = new Random();
	public Color color;
	public int RADIUS = 3;
	
	
	Rat(){
		super();
		color = Color.cyan;
	}


	public int calculateNextMove() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void draw(Graphics g){
		g.setColor(color);
		g.fillRect(this.getCurrentX(), this.getCurrentY(), RADIUS, RADIUS);
	}
	
	public void move(List<Rat> allRats, Vector<Pixel> globalPixels){
		
		int ratInd = this.index;
		int pnSize = globalPixels.get(ratInd).pixelNeighbors.size();
		int randValue = (int)Math.floor(Math.random() * pnSize);
		
		if(globalPixels.get(ratInd).pixelNeighbors.size() > 0){
			this.setCurrentX(globalPixels.get(ratInd).pixelNeighbors.get(randValue).xLoc);
			this.setCurrentY(globalPixels.get(ratInd).pixelNeighbors.get(randValue).yLoc);
			this.index = globalPixels.get(ratInd).pixelNeighbors.get(randValue).index;
		}
		

		 for(int i=0; i< allRats.size(); i++){
	        	if(allRats.get(i) != this){
	            	if(allRats.get(i).getCurrentX() == this.getCurrentX() && allRats.get(i).getCurrentY() == this.getCurrentY()){
	            		if(allRats.get(i).getInfected()){
	            		//System.out.println("COLLISION");
	            		color = color.red;
	            		this.setInfected(true);
	            		VirusSimulation.numInfectedRats++;
	            		

	            		}
	            	}
	        	}
	        }
		
		
	}
	
	public void run(){
		synchronized (this) {}
//		for(int i=1; i<= 5000; i++){
//			move(VirusSimulation.allRats, VirusSimulation.globalPixels);
//			try{
//				Thread.sleep(20);
//			}catch(InterruptedException e){}
//			synchronized (this) {}
//		}
		
		while(true){
			move(VirusSimulation.allRats, VirusSimulation.globalPixels);
			try{
				Thread.sleep(20);
			}catch(InterruptedException e){}
			
		}
//		System.out.println("Num infected rats: " + VirusSimulation.numInfectedRats);
//		try{
//			Thread.sleep(1000000000);
//		}catch(InterruptedException e){}
//		
//		if(this.getInfected()){
//			for(int i=0; i< 200; i++){
//				move(VirusSimulation.allRats, VirusSimulation.globalPixels);
//				try{
//					Thread.sleep(20);
//				}catch(InterruptedException e){}
//				
//			}
//			VirusSimulation.allRats.remove(this);
//		}
		
		
		//Add collision code here
		
	}
	
	
	
	
}