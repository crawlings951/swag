package swag;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Human extends LivingBeing implements Runnable {
	
	Random random = new Random();
	public Color color = Color.YELLOW;
		
//        public int x = random.nextInt(VirusSimulation.upperPanel.getWidth());
//        public int y = random.nextInt(VirusSimulation.upperPanel.getWidth());
//        public int dx = 1 + random.nextInt(5);
//        public int dy = 1 + random.nextInt(3);
        public int RADIUS = 3;

	Human(){
		super();
	}
	
	@Override
	public int calculateNextMove() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void draw(Graphics g){
		g.setColor(color);
		g.fillRect(this.getCurrentX(), this.getCurrentY(), RADIUS, RADIUS);
	}
	
	public void move(List<Human> allHumans, Vector<Pixel> globalPixels){
		
//		for(int i = 0; i < allHumans.size(); i++)
//		{
			int humInd = this.index;
			int pnSize = globalPixels.get(humInd).pixelNeighbors.size();
			int randValue = (int)Math.floor(Math.random() * pnSize);
			
			//System.out.println("Nieghbors size: " + pnSize);
			
			if(globalPixels.get(humInd).pixelNeighbors.size() > 0){
//				allHumans.get(i).setCurrentX(globalPixels.get(humInd).pixelNeighbors.get(randValue).xLoc);
//				allHumans.get(i).setCurrentY(globalPixels.get(humInd).pixelNeighbors.get(randValue).yLoc);
//				allHumans.get(i).index = globalPixels.get(humInd).pixelNeighbors.get(randValue).index;
				//System.out.println("Current x: " + this.getCurrentX());
				
				this.setCurrentX(globalPixels.get(humInd).pixelNeighbors.get(randValue).xLoc);
				this.setCurrentY(globalPixels.get(humInd).pixelNeighbors.get(randValue).yLoc);
				
				//System.out.println("Current x after update: " + this.getCurrentX());
				this.index = globalPixels.get(humInd).pixelNeighbors.get(randValue).index;
			}
//			
//			//TODO can add the bit about exploding
//		//}
		
//		System.out.println("upperPanel width: " + VirusSimulation.upperPanel.getWidth());
//		x += dx;
//        y += dy;
//        if (x < 0) {
//            // Bounce off left wall.
//            x = 0; dx = -dx;
//        }
//        if (x + RADIUS >= VirusSimulation.upperPanel.getWidth()) {
//            // Bounce off right wall.
//            x = VirusSimulation.upperPanel.getWidth() - RADIUS; dx = -dx;
//        }
//        if (y < 0) {
//            // Bounce off top wall.
//            y = 0; dy = -dy;
//        }
//        if (y + RADIUS >= VirusSimulation.upperPanel.getHeight()) {
//            // Bounce off bottom wall.
//            y = VirusSimulation.upperPanel.getHeight() - RADIUS; dy = -dy;
//        }
        
        for(int i=0; i< allHumans.size(); i++){
        	if(allHumans.get(i) != this){
            	if(allHumans.get(i).getCurrentX() == this.getCurrentX() && allHumans.get(i).getCurrentY() == this.getCurrentY()){
            		//System.out.println("COLLISION");
            		RADIUS = 20;
            		allHumans.get(i).RADIUS = 20;
            	}
        	}
        }
		
	}
	
	public void run(){
		synchronized (this) { }
		for(int i=1; i<= 5000; i++){
			//System.out.println("Inside run for human");
			move(VirusSimulation.allHumans, VirusSimulation.globalPixels);
			try{
				Thread.sleep(20);
			}catch(InterruptedException e) {}
			//VirusSimulation.allHumans.remove(this);
			synchronized (this) { }
		}
		
	}

}
