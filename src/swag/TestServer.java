package swag;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class TestServer {
	
	public static void main(String[] args) throws Exception {
		ServerSocket listener = new ServerSocket(8901);
		System.out.println("Server is running!!!");
		try{
			while(true){
				Test test = new Test();
				Test.Viewer viewer = test.new Viewer(listener.accept());
				test.currentViewer = viewer;
				viewer.start();
			}
	
		} finally {
			listener.close();
		}
		
	}

}

class Test {
	
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
					if(command.equals("pressed")){
						System.out.println("Button was pressed according to server");
						output.println("cool");
						
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
