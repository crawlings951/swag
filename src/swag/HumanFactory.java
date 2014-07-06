package swag;

import java.util.Vector;

public class HumanFactory extends Thread {
	
	private Vector<Human> humanThreadVector = new Vector<Human>();
	
	public HumanFactory(){
		this.start();
	}
	
	public void run(){
		//try{
			
			int humanNumber = 0;
			while( humanNumber < VirusSimulation.totalHumans  ){
				Human h = new Human();
			}
			
//		}catch (InterruptedException ie){
//			
//		}
	}

}


