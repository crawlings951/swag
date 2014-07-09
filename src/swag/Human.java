package swag;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Human extends LivingBeing implements Runnable {
	
	Random random = new Random();
	public Color color;
		
//        public int x = random.nextInt(VirusSimulation.upperPanel.getWidth());
//        public int y = random.nextInt(VirusSimulation.upperPanel.getWidth());
//        public int dx = 1 + random.nextInt(5);
//        public int dy = 1 + random.nextInt(3);
        public int RADIUS = 3;

	Human(){
		super();
		 color = Color.yellow;
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
	
	public void move(List<Human> allHumans, List<Rat> allRats, Vector<Pixel> globalPixels){
		
//		for(int i = 0; i < allHumans.size(); i++)
//		{
			int humInd = this.index;
			//int pnSize = globalPixels.get(humInd).pixelNeighbors.size();
			//int randValue = random.nextInt(10) * 100;
			//int randValue = (int)Math.floor(Math.random() * pnSize);
			
			//System.out.println("Nieghbors size: " + pnSize);z
			//System.out.println(humInd + " = humInd");
			if(globalPixels.get(humInd).pixelNeighbors.size() > 0){
//				allHumans.get(i).setCurrentX(globalPixels.get(humInd).pixelNeighbors.get(randValue).xLoc);
//				allHumans.get(i).setCurrentY(globalPixels.get(humInd).pixelNeighbors.get(randValue).yLoc);
//				allHumans.get(i).index = globalPixels.get(humInd).pixelNeighbors.get(randValue).index;
				//System.out.println("Current x: " + this.getCurrentX());

//				if(globalPixels.get(humInd).pixelNeighbors.get(0).xLoc  != getCurrentX()-1) //if you cant go left anymore
//				{
//					if(globalPixels.get(humInd).pixelNeighbors.size() > 2){
//					if(!(this.wentUp) && !(this.wentDown)) //if we havent gone up or down yet
//					{
//						int rando = (int)Math.floor(Math.random() *2);
//						if(rando % 2 == 0){
//							this.setCurrentX(globalPixels.get(humInd).pixelNeighbors.get(1).xLoc);
//							this.setCurrentY(globalPixels.get(humInd).pixelNeighbors.get(1).yLoc);
//							this.index = globalPixels.get(humInd).pixelNeighbors.get(1).index;
//							wentUp = true;
//						}
//						else{
//
//							this.setCurrentX(globalPixels.get(humInd).pixelNeighbors.get(2).xLoc);
//							this.setCurrentY(globalPixels.get(humInd).pixelNeighbors.get(2).yLoc);
//							this.index = globalPixels.get(humInd).pixelNeighbors.get(2).index;
//							wentDown = true;
//						}
//					}
//					}
//					else if (globalPixels.get(humInd).pixelNeighbors.size() > 1){
//						this.setCurrentX(globalPixels.get(humInd).pixelNeighbors.get(1).xLoc);
//						this.setCurrentY(globalPixels.get(humInd).pixelNeighbors.get(1).yLoc);
//						this.index = globalPixels.get(humInd).pixelNeighbors.get(1).index;
//					}
//					
//				}
//				if(wentDown)
//				{
//					//if we are at the very bottom right
//					if (globalPixels.get(humInd).pixelNeighbors.size() > 1){
//					if(globalPixels.get(humInd).pixelNeighbors.get(1).xLoc != getCurrentX() + 1 && globalPixels.get(humInd).pixelNeighbors.size() < 3)
//					{
//						this.setCurrentX(globalPixels.get(humInd).pixelNeighbors.get(0).xLoc);
//						this.setCurrentY(globalPixels.get(humInd).pixelNeighbors.get(0).yLoc);
//						this.index = globalPixels.get(humInd).pixelNeighbors.get(0).index;
//						wentDown = false;
//					}
//					else if(globalPixels.get(humInd).pixelNeighbors.get(1).xLoc != getCurrentX() + 1) //if we can't go right anymore go down til we can go right
//					{
//						this.setCurrentX(globalPixels.get(humInd).pixelNeighbors.get(2).xLoc);
//						this.setCurrentY(globalPixels.get(humInd).pixelNeighbors.get(2).yLoc);
//						this.index = globalPixels.get(humInd).pixelNeighbors.get(2).index;
//					}
//					else //go right
//					{
//						this.setCurrentX(globalPixels.get(humInd).pixelNeighbors.get(1).xLoc);
//						this.setCurrentY(globalPixels.get(humInd).pixelNeighbors.get(1).yLoc);
//						this.index = globalPixels.get(humInd).pixelNeighbors.get(1).index;
//					}
//					}
//					else
//					{
//						this.setCurrentX(globalPixels.get(humInd).pixelNeighbors.get(0).xLoc);
//						this.setCurrentY(globalPixels.get(humInd).pixelNeighbors.get(0).yLoc);
//						this.index = globalPixels.get(humInd).pixelNeighbors.get(0).index;
//						wentDown = false;
//					}
//				}
//				if(wentUp)
//				{
//					//if we are at the very top right
//					if (globalPixels.get(humInd).pixelNeighbors.size() > 1)
//					{
//					if(globalPixels.get(humInd).pixelNeighbors.get(1).xLoc != getCurrentX() + 1 && globalPixels.get(humInd).pixelNeighbors.size() <3 )
//					{
//						this.setCurrentX(globalPixels.get(humInd).pixelNeighbors.get(0).xLoc);
//						this.setCurrentY(globalPixels.get(humInd).pixelNeighbors.get(0).yLoc);
//						this.index = globalPixels.get(humInd).pixelNeighbors.get(0).index;
//						wentUp = false;
//					}
//					else if(globalPixels.get(humInd).pixelNeighbors.get(1).xLoc != getCurrentX() + 1) //if we can't go right anymore go up
//					{
//						this.setCurrentX(globalPixels.get(humInd).pixelNeighbors.get(2).xLoc);
//						this.setCurrentY(globalPixels.get(humInd).pixelNeighbors.get(2).yLoc);
//						this.index = globalPixels.get(humInd).pixelNeighbors.get(2).index;
//					}
//					else //go right
//					{
//						this.setCurrentX(globalPixels.get(humInd).pixelNeighbors.get(1).xLoc);
//						this.setCurrentY(globalPixels.get(humInd).pixelNeighbors.get(1).yLoc);
//						this.index = globalPixels.get(humInd).pixelNeighbors.get(1).index;
//					}
//					}
//					else
//					{
//						this.setCurrentX(globalPixels.get(humInd).pixelNeighbors.get(0).xLoc);
//						this.setCurrentY(globalPixels.get(humInd).pixelNeighbors.get(0).yLoc);
//						this.index = globalPixels.get(humInd).pixelNeighbors.get(0).index;
//						wentUp = false;
//					}
//				}
//				else{
//					if(globalPixels.get(humInd).pixelNeighbors.get(0).xLoc != getCurrentX()-1)
//					{
//						if(globalPixels.get(humInd).pixelNeighbors.size() > 1)
//						{
//							this.setCurrentX(globalPixels.get(humInd).pixelNeighbors.get(1).xLoc); //go left
//							this.setCurrentY(globalPixels.get(humInd).pixelNeighbors.get(1).yLoc);
//							this.index = globalPixels.get(humInd).pixelNeighbors.get(1).index;
//						}
//					}
//					this.setCurrentX(globalPixels.get(humInd).pixelNeighbors.get(0).xLoc); //go left
//					this.setCurrentY(globalPixels.get(humInd).pixelNeighbors.get(0).yLoc);
//					this.index = globalPixels.get(humInd).pixelNeighbors.get(0).index;
//					wentUp = false;
//					wentDown = false;
//					//atBottom = false;
//					//atTop = false;
//				}
//				
//			//System.out.println("Current x after update: " + this.getCurrentX());
//				//this.index = globalPixels.get(humInd).pixelNeighbors.get(0).index;
////				if(getCurrentY() == 0)//top row
////				{
////					if(getCurrentX() < 1099) //not at very right
////					{setCurrentX(getCurrentX()+1);} //move right
////					else{
////						setCurrentY(getCurrentY()+1); //at very right move down
////					}
////				}
////				else if(getCurrentY() == 799)//very bottom
////				{
////					if(getCurrentX() < 1099) //not at very right
////					{setCurrentX(getCurrentX()+1);} //move right
////					else{
////						setCurrentY(getCurrentY()-1); //at very right move up
////					}
////				}
////			
////				else if(getCurrentX() == 0) // very left
////				{
////					if(getCurrentY() < 799)
////					{setCurrentY(getCurrentY()+1);} //move down til at bottom
////					else
////					{
////						setCurrentX(getCurrentX()+1); //move right
////					}
////				}
////				else if(getCurrentX() == 1099) //very right 
////				{
////					if(getCurrentY() < 799) //not at bottom
////					{setCurrentY(getCurrentY()+1);} //go down
////					else
////					{
////						setCurrentX(getCurrentX()-1); //go left
////					} 
////				}
////				else
////				{
////					if(globalPixels.get(humInd).type == globalPixels.get(humInd+1).type)
////					{
////						setCurrentX(getCurrentX()+1);
////					}
////					else if(globalPixels.get(humInd).type == globalPixels.get(humInd+1200).type)
////					{
////						setCurrentY(getCurrentY() + 1);
////					}else if(globalPixels.get(humInd).type == globalPixels.get(humInd-1).type)
////					{
////						setCurrentX(getCurrentX() + 1);
////					}
////					if(getCurrentY() < 200)
////					{
////						setCurrentX(getCurrentX()+1);
////					}
////					else if(getCurrentY() < 400)
////					{
////						setCurrentX(getCurrentX()+1);
////					}
////					else if(getCurrentY() < 600)
////					{
////						setCurrentX(getCurrentX()+1);
////					}
////					else{
////						setCurrentX(getCurrentX()+1);
////					}
//				//}
				if(movingLeft)
				{
					if(globalPixels.get(humInd-15).type.equals("street") || globalPixels.get(humInd-15).type.equals("street_sewer"))
					{
						this.setCurrentX(globalPixels.get(humInd-15).xLoc);
						this.setCurrentY(globalPixels.get(humInd-15).yLoc);
						this.index = humInd -15;
					}
					else{
						movingLeft = false;
						int rando = (int)Math.floor(Math.random() * 2);
						if(rando % 2 == 0)
						{
							if(globalPixels.get(humInd-18000).type.equals("street") || globalPixels.get(humInd-18000).type.equals("street_sewer"))
							{
								this.setCurrentX(globalPixels.get(humInd-18000).xLoc);
								this.setCurrentY(globalPixels.get(humInd-18000).yLoc);
								this.index = humInd -18000;
								movingUp = true;
							}
							else if(globalPixels.get(humInd+18000).type.equals("street") || globalPixels.get(humInd+18000).type.equals("street_sewer"))
							{
								this.setCurrentX(globalPixels.get(humInd+18000).xLoc);
								this.setCurrentY(globalPixels.get(humInd+18000).yLoc);
								this.index = humInd + 18000;
								movingDown = true;
							}
							else
							{
								this.setCurrentX(globalPixels.get(humInd+15).xLoc);
								this.setCurrentY(globalPixels.get(humInd+15).yLoc);
								this.index = humInd +15;
								movingRight = true;
								
							}
						}
						else
						{
							if(globalPixels.get(humInd+18000).type.equals("street") || globalPixels.get(humInd+18000).type.equals("street_sewer"))
							{
								this.setCurrentX(globalPixels.get(humInd+18000).xLoc);
								this.setCurrentY(globalPixels.get(humInd+18000).yLoc);
								this.index = humInd + 18000;
								movingDown = true;
							}
							else if(globalPixels.get(humInd-18000).type.equals("street") || globalPixels.get(humInd-18000).type.equals("street_sewer"))
							{
								this.setCurrentX(globalPixels.get(humInd-18000).xLoc);
								this.setCurrentY(globalPixels.get(humInd-18000).yLoc);
								this.index = humInd -18000;
								movingUp = true;
							}
							else
							{
								this.setCurrentX(globalPixels.get(humInd+15).xLoc);
								this.setCurrentY(globalPixels.get(humInd+15).yLoc);
								this.index = humInd +15;
								movingRight = true;
							}
						}
					}
				}
				if(movingUp){
					if(!wentUp)
					{
						if(globalPixels.get(humInd+15).type.equals("street") || globalPixels.get(humInd+15).type.equals("street_sewer"))
						{
							this.setCurrentX(globalPixels.get(humInd+15).xLoc);
							this.setCurrentY(globalPixels.get(humInd+15).yLoc);
							this.index = humInd +15;
							movingRight = true;
							movingUp = false;
							wentUp = true;
						}
						else if(globalPixels.get(humInd-15).type.equals("street") || globalPixels.get(humInd-15).type.equals("street_sewer"))
						{
							this.setCurrentX(globalPixels.get(humInd-15).xLoc);
							this.setCurrentY(globalPixels.get(humInd-15).yLoc);
							this.index = humInd -15;
							movingLeft = true;
							movingUp = false;
							wentUp = true;
						}
						else if(globalPixels.get(humInd-18000).type.equals("street") || globalPixels.get(humInd-18000).type.equals("street_sewer"))
						{
							this.setCurrentX(globalPixels.get(humInd-18000).xLoc);
							this.setCurrentY(globalPixels.get(humInd-18000).yLoc);
							this.index = humInd -18000;
						}
						else
						{
							movingUp = false;
							int rando = (int)Math.floor(Math.random() * 2);
							if(rando % 2 == 0)
							{
								movingRight = true;
							}
							else{
								movingLeft = true;
							}
						}
					}
					else
					{
						if(globalPixels.get(humInd-18000).type.equals("street") || globalPixels.get(humInd-18000).type.equals("street_sewer"))
						{
							this.setCurrentX(globalPixels.get(humInd-18000).xLoc);
							this.setCurrentY(globalPixels.get(humInd-18000).yLoc);
							this.index = humInd -18000;
							wentUp = false;
						}
					}
				}
				if(movingRight){
					if(globalPixels.get(humInd+15).type.equals("street") || globalPixels.get(humInd+15).type.equals("street_sewer"))
						{
							this.setCurrentX(globalPixels.get(humInd+15).xLoc);
							this.setCurrentY(globalPixels.get(humInd+15).yLoc);
							this.index = humInd +15;
						}
					else{
						movingRight = false;
						int rando = (int)Math.floor(Math.random() * 2);
						if(rando % 2 == 0)
						{
							if(globalPixels.get(humInd-18000).type.equals("street") || globalPixels.get(humInd-18000).type.equals("street_sewer"))
							{
								this.setCurrentX(globalPixels.get(humInd-18000).xLoc);
								this.setCurrentY(globalPixels.get(humInd-18000).yLoc);
								this.index = humInd -18000;
								movingUp = true;
							}
							else if(globalPixels.get(humInd+18000).type.equals("street") || globalPixels.get(humInd+18000).type.equals("street_sewer"))
							{
								this.setCurrentX(globalPixels.get(humInd+18000).xLoc);
								this.setCurrentY(globalPixels.get(humInd+18000).yLoc);
								this.index = humInd + 18000;
								movingDown = true;
							}
							else
							{
								this.setCurrentX(globalPixels.get(humInd-15).xLoc);
								this.setCurrentY(globalPixels.get(humInd-15).yLoc);
								this.index = humInd -15;
								movingLeft = true;
							}
							
						}
						else{
							
							if(globalPixels.get(humInd+18000).type.equals("street") || globalPixels.get(humInd+18000).type.equals("street_sewer"))
							{
								this.setCurrentX(globalPixels.get(humInd+18000).xLoc);
								this.setCurrentY(globalPixels.get(humInd+18000).yLoc);
								this.index = humInd + 18000;
								movingDown = true;
							}
							else if(globalPixels.get(humInd-18000).type.equals("street") || globalPixels.get(humInd-18000).type.equals("street_sewer"))
							{
								this.setCurrentX(globalPixels.get(humInd-18000).xLoc);
								this.setCurrentY(globalPixels.get(humInd-18000).yLoc);
								this.index = humInd -18000;
								movingUp = true;
							}
							else
							{
								this.setCurrentX(globalPixels.get(humInd-15).xLoc);
								this.setCurrentY(globalPixels.get(humInd-15).yLoc);
								this.index = humInd -15;
								movingLeft = true;
							}
						}
					}
				}
				if(movingDown)
				{
					if(!wentDown)
					{
						if(globalPixels.get(humInd+15).type.equals("street") || globalPixels.get(humInd+15).type.equals("street_sewer"))
						{
							this.setCurrentX(globalPixels.get(humInd+15).xLoc);
							this.setCurrentY(globalPixels.get(humInd+15).yLoc);
							this.index = humInd +15;
							movingRight = true;
							movingDown = false;
							wentDown = true;
						}
						else if(globalPixels.get(humInd-15).type.equals("street") || globalPixels.get(humInd-15).type.equals("street_sewer"))
						{
							this.setCurrentX(globalPixels.get(humInd-15).xLoc);
							this.setCurrentY(globalPixels.get(humInd-15).yLoc);
							this.index = humInd -15;
							movingLeft = true;
							movingDown = false;
							wentDown = true;
						}
						else if(globalPixels.get(humInd+18000).type.equals("street") || globalPixels.get(humInd+18000).type.equals("street_sewer")){
						this.setCurrentX(globalPixels.get(humInd+18000).xLoc);
						this.setCurrentY(globalPixels.get(humInd+18000).yLoc);
						this.index = humInd + 18000;
						}
						else{
							movingDown = false;	
							int rando = (int)Math.floor(Math.random() * 2);
							if(rando % 2 == 0)
							{
								movingLeft = true;
							}
							else{
								movingRight = true;
							}
						}
					}
					else
					{
						if(globalPixels.get(humInd+18000).type.equals("street") || globalPixels.get(humInd+18000).type.equals("street_sewer")){
							this.setCurrentX(globalPixels.get(humInd+18000).xLoc);
							this.setCurrentY(globalPixels.get(humInd+18000).yLoc);
							this.index = humInd + 18000;
							wentDown = false;
						}
					}
				}
				
			}

 
			for(int i=0; i< allHumans.size(); i++){
	        	if(allHumans.get(i) != this){
	            	if((allHumans.get(i).getCurrentX() == this.getCurrentX() && allHumans.get(i).getCurrentY() == this.getCurrentY()) 
	            			|| (allHumans.get(i).getCurrentX()-1 == this.getCurrentX() && allHumans.get(i).getCurrentY()-1 == this.getCurrentY())
	            			|| (allHumans.get(i).getCurrentX()-2 == this.getCurrentX() && allHumans.get(i).getCurrentY()-2 == this.getCurrentY())
	            			|| (allHumans.get(i).getCurrentX() == this.getCurrentX() && allHumans.get(i).getCurrentY()-2 == this.getCurrentY())
	            			|| (allHumans.get(i).getCurrentX() == this.getCurrentX() && allHumans.get(i).getCurrentY()-1 == this.getCurrentY())
	            			|| (allHumans.get(i).getCurrentX()-1 == this.getCurrentX() && allHumans.get(i).getCurrentY() == this.getCurrentY())
	            			|| (allHumans.get(i).getCurrentX()-2 == this.getCurrentX() && allHumans.get(i).getCurrentY() == this.getCurrentY()) ){
	            		if(allHumans.get(i).getInfected()){
	            		//System.out.println("COLLISION");
	            		color = color.red;
	            		this.setInfected(true);
	            		
	            		//TODO need to change the color of the other person??
	            		//allHumans.get(i).RADIUS = 20;
	            		}
	            	}
	        	}
	        }
			for(int i=0; i< allRats.size(); i++){
	        	
	        		if((allRats.get(i).getCurrentX() == this.getCurrentX() && allRats.get(i).getCurrentY() == this.getCurrentY()) 
	            			|| (allRats.get(i).getCurrentX()-1 == this.getCurrentX() && allRats.get(i).getCurrentY()-1 == this.getCurrentY())
	            			|| (allRats.get(i).getCurrentX()-2 == this.getCurrentX() && allRats.get(i).getCurrentY()-2 == this.getCurrentY())
	            			|| (allRats.get(i).getCurrentX() == this.getCurrentX() && allRats.get(i).getCurrentY()-2 == this.getCurrentY())
	            			|| (allRats.get(i).getCurrentX() == this.getCurrentX() && allRats.get(i).getCurrentY()-1 == this.getCurrentY())
	            			|| (allRats.get(i).getCurrentX()-1 == this.getCurrentX() && allRats.get(i).getCurrentY() == this.getCurrentY())
	            			|| (allRats.get(i).getCurrentX()-2 == this.getCurrentX() && allRats.get(i).getCurrentY() == this.getCurrentY()) ){
	            		if(allRats.get(i).getInfected()){
	            		//System.out.println("COLLISION");
	            		color = color.red;
	            		this.setInfected(true);
	            		VirusSimulation.numInfectedRats++;
	            		

	            		}
	            	}
	        	
	        }
		
	}
	
	public void run(){
		synchronized (this) { }
		for(int i=1; i<= 5000; i++){
			//System.out.println("Inside run for human");
			move(TestClient.allHumans, TestClient.allRats, TestClient.globalPixels);
			try{
				Thread.sleep(20);
			}catch(InterruptedException e) {}
			//VirusSimulation.allHumans.remove(this);
			synchronized (this) { }
		}
		
	}

}
