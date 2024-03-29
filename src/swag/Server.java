package swag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//import swag.Test.Viewer;

public class Server {
	
	Viewer currentViewer;
	
	
	class Viewer extends Thread {
		
		Socket socket;
		BufferedReader input;
		PrintWriter output;
		
		public Viewer(Socket socket){
			this.socket = socket;
			try{
				input = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				output = new PrintWriter(socket.getOutputStream(), true);
				System.out.println("Where the message to wait to connect to player would be");
			} catch (IOException e){
				System.out.println("Exception in the viewer constructor");
			}
		}
		
		public void run(){
			
			
				System.out.println("All players connected");
				
				try{
				while(true){
					String command = input.readLine();
					if(command.equals("pressed") && VirusSimulation.validData){
						System.out.println("Button was pressed according to server");
						output.println("available");	
					}
				}
				} catch (IOException ie){
					ie.printStackTrace();
				} finally {
					try {socket.close();} catch (IOException e){}
				}
		}
	}
}