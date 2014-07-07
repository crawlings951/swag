package swag;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TestClient extends JFrame {
	
	JPanel mainPanel = new JPanel();
	JPanel refreshPanel = new JPanel();
	
	JFrame frame = new JFrame("Virus Simulation Tracker");
	JLabel messageLabel = new JLabel("Simulation will begin shortly");
	JButton button = new JButton("Refresh");
	
	static int PORT = 8901;
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	
	public TestClient(String serverAddress) throws Exception {
		super("Virus Simulation Tracker");
		
		
		socket = new Socket(serverAddress, PORT);
		in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		
		//GUI Specifications
		//mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				System.out.println("Button pressed");
				out.println("pressed");
				
			}
		});
		
		mainPanel.add(messageLabel);
		mainPanel.setPreferredSize(new Dimension(1200, 600));
		
		refreshPanel.add(button);
		refreshPanel.setPreferredSize(new Dimension(1200, 100));
		refreshPanel.setBorder(BorderFactory.createLineBorder(Color.black));
//		frame.setSize(1200, 700);
//		frame.setLocation(100, 0);
//		frame.setResizable(false);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(mainPanel, "Center");
		frame.getContentPane().add(refreshPanel, "South");
		//frame.getContentPane().add(messageLabel, "Center");
		//frame.getContentPane().add(button, "South");
		
//		setSize(1200, 700);
//		setLocation(100, 0);
//		setResizable(false);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		add(messageLabel, "Center");
//		add(button, "South");
	}
	
	public void play() throws Exception {
		String response;
		try {
			while(true){
				response = in.readLine();
				if(response.equals("cool")){
					System.out.println("It worked");
					messageLabel.setText("It worked");
				}
			}
			
			
		}
		finally{
			socket.close();
		}
		
		
	}
	
	public static void main(String[] args) throws Exception {
		while(true){
			String serverAddress = (args.length == 0) ? "localhost" : args[1];
			TestClient client = new TestClient(serverAddress);
			client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			client.frame.setSize(1200, 700);
            client.frame.setVisible(true);
            client.frame.setResizable(false);
            client.play();
		}
		
	}
	

}
