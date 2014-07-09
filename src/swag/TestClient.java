package swag;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import swag.VirusSimulation.Panel;

public class TestClient extends JFrame implements Runnable{
	
	//////////////////GUI VARS/////////////////////////
	JMenuBar menuBar;
	JMenu helpMenu, newSimulationMenu;
	
	JPanel mainPanel = new JPanel();
	JPanel refreshPanel = new JPanel();
	JFrame frame = new JFrame("Virus Simulation Tracker Remote Client");
	JLabel messageLabel = new JLabel("Please Click Connect to Retrieve Remote XML data");
	JButton button = new JButton("Connect");
	JTextField ratField;
	JTextField humansField;
	JTextField contagionField;
	JComboBox virusStrengthCombo;
	
	JPanel lowerPanel;
	public static JPanel upperPanel;
	JPanel leftPanel, rightPanel, middlePanel;
	JLabel ratLabel, humanLabel, strengthLabel, contagionLabel;
	JLabel fileDisplay;
	
	/////////////////EXECUTION VARS///////////////////////
	public static int totalHumans;
	public static int totalRats;
	public Boolean streetsDrawn;
	public Boolean sewersDrawn;
	public Boolean beenPressed;
	public boolean parsedStreets;
	public boolean parsedSewers;
	public boolean parsedHospitals;
	public boolean startButtonPressed;
	public static int numInfectedRats;
	public static int numInfectedHumans;
	
	public ExecutorService pool;
	Thread renderThread = new Thread(this);
	public Action launchHuman;
	
	public static TestClient client;
	
	///////////////SERVER VARS////////////////////
	static int PORT = 8901;
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	boolean initial = true;
	String serverAddress;
	
	///////////VIRUS SIMULATION Arrays and Vectors//////////
	Vector<Street> allStreets = new Vector<Street>();
	Vector<Sewer> allSewers = new Vector<Sewer>();
	Vector<Hospital> allHospitals = new Vector<Hospital>();

	public static List<Human> allHumans;
	public static List<Rat> allRats;
	public static Vector<Pixel> globalPixels;
	
	/////////////////LAN MONGO/////////////////////
	MongoClient mongoClient;
	DB db;
	Set<String> colls;
	DBCollection street_coll;
	DBCollection sewer_coll;
	DBCollection hospital_coll;
	
	/////////////////JSON PARSING/////////////////
	public JSONObject obj;
	public String combined_string;	
	
	TestClient() throws Exception {
		allHumans = Collections.synchronizedList(new Vector<Human>());
		allRats = Collections.synchronizedList(new Vector<Rat>());
		
		if(initial){
			JPanel IPPanel = new JPanel();
			JPanel centerPanel = new JPanel();
			JLabel IPLabel = new JLabel("IP Address: ");
			final JTextField IPField = new JTextField(20);
			JLabel PortLabel = new JLabel("PORT: ");
			JTextField PortField = new JTextField(20);
			PortField.setText("8901");

			centerPanel.add(IPLabel);
			centerPanel.add(IPField);
			centerPanel.add(PortLabel);
			centerPanel.add(PortField);
			
			IPPanel.add(centerPanel);
			
			int result = JOptionPane.showConfirmDialog(null, 
					IPPanel,
					"IP Address",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if(result == JOptionPane.YES_OPTION){
				serverAddress = IPField.getText();
				PORT = Integer.parseInt(PortField.getText());
				initial = false;
			}
			
			
		}
//		socket = new Socket(serverAddress, PORT);
//		in = new BufferedReader(new InputStreamReader(
//				socket.getInputStream()));
//		out = new PrintWriter(socket.getOutputStream(), true);
		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				System.out.println("Button pressed");
				//out.println("pressed");
				
			}
		});
		
		mainPanel.add(messageLabel);
		mainPanel.setPreferredSize(new Dimension(1200, 600));
		
		refreshPanel.add(button);
		refreshPanel.setPreferredSize(new Dimension(1200, 100));
		refreshPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		frame.getContentPane().add(mainPanel, "Center");
		frame.getContentPane().add(refreshPanel, "South");

	}
	
	public void play() throws Exception {
		String response;
		try {
			while(true){
				response = "available"; //in.readLine();
				if(response.equals("available")){
					System.out.println("LOADING XML");
					messageLabel.setText("XML DATA IS BEING LOADED");
					try {
						mongoClient = new MongoClient("10.120.32.77" , 27017 );
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					//create new database
					db = mongoClient.getDB( "mongo_practice" );
					//create collection
					street_coll= db.getCollection("streets");
					sewer_coll = db.getCollection("sewers");
					hospital_coll = db.getCollection("hospital");
					//print all items in a collection
					DBCursor cursor = street_coll.find();
					try {
					   while(cursor.hasNext()) {
					       try {
					    	 System.out.println("Street yo");
							json_parse(cursor.next().toString(), "street");
					       } catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					       }
					   }
					} finally {
					   cursor.close();
					}
					cursor = sewer_coll.find();
					try{
						while(cursor.hasNext()){
							try{
								System.out.println("Sewer yo");
								json_parse(cursor.next().toString(), "sewer");
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					} finally{
						cursor.close();
					}
					cursor = hospital_coll.find();
					try{
						while(cursor.hasNext()){
							try{
								json_parse(cursor.next().toString(), "hospital");
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					} finally{
						cursor.close();
					}
					setup();
				}
			}
		}
		finally{
			socket.close();
		}
	}
	private void setup(){
		/******** Vector Instantiations ********/
		globalPixels = new Vector<Pixel>();
		streetsDrawn = false;
		sewersDrawn = false;
		parsedStreets = false;
		parsedStreets = false; 
		parsedHospitals = false; 
		startButtonPressed = false;
		numInfectedRats = 0;
		numInfectedHumans = 0;
		
		//Generate all pixels	
		for(int i = 0; i < 800; i++){
			for(int j = 0; j < 1200; j++){
				Pixel p = new Pixel();
				p.xLoc = j;
				p.yLoc = i;
				p.index = i*1200 + j;
				p.type = "error";
				globalPixels.add(p);
			}
		}
		
		//totalHumans = 200;
		//TODO this might not work because total humans hasn't been initialized yet
		//pool = Executors.newFixedThreadPool(totalHumans);
		
		launchHuman = new AbstractAction("START"){
			public void actionPerformed(ActionEvent ae){
				
				
			//if(checkVariableEntries()){
				System.out.println("Good to go");
				
				
				pool = Executors.newFixedThreadPool(getNumberofHumans() + getNumberofRats());
				//TODO this is where all of the code needs to go where we hit submit
				genStreetPixels();
				genSewerPixels();
				find_neighbors();
				System.out.println("made it out of find_neighbors");
				System.out.println("numOfHumans = " + getNumberofHumans());
				for(int i=0; i< getNumberofHumans(); i++){
					
					Human h = new Human();
					System.out.println("made a new human");
					//Code from before
					boolean foundLocation = true;
					Random randomGenerator = new Random();	
					int randomX = (randomGenerator.nextInt(1200));
					int randomY = (randomGenerator.nextInt(600));
					int random = randomY*1200 + randomX;
					//int random = randomGenerator.nextInt(960000);
					//System.out.println("Random number: " + random);
					while(foundLocation){
						//System.out.println(globalPixels.size());
						if(random >= globalPixels.size()){
							System.out.println("line 327");
							random = 0;
						}
						if(globalPixels.get(random).type.equals("street")){
							h.setCurrentX(globalPixels.get(random).xLoc);
							h.setCurrentY(globalPixels.get(random).yLoc);
							h.index = random;
							globalPixels.get(random).livingBeing.add(h);
							//allHumans.add(h);
							foundLocation = false;
						}
						
						random+=1201;
					}
					
					allHumans.add(h);
					synchronized (this) {}
					pool.execute(h);
				}
				System.out.println("made all of the humans");
				System.out.println("numOfRats:  " + getNumberofRats());
				for(int i=0; i< getNumberofRats(); i++){
					
					Rat r = new Rat();
					if(i < 15){
						r.setInfected(true);
						r.color = Color.red;
						numInfectedRats++;
					}
					
					Random randomGenerator = new Random();	
					//int random = randomGenerator.nextInt(960000);
					int randomX = (randomGenerator.nextInt(1200));
					int randomY = (randomGenerator.nextInt(600));
					int random = randomY*1200 + randomX;
					boolean foundLocation = true;
						System.out.println("HI");
							while(foundLocation)
					
							{
								if(random >= globalPixels.size())
								{
									//System.out.println("line 370");
									random = 0;
								}
								if(globalPixels.get(random).type.equals("sewer") || globalPixels.get(random).type.equals("street_sewer"))
								{
									r.setCurrentX(globalPixels.get(random).xLoc);
									r.setCurrentY(globalPixels.get(random).yLoc);
									r.index = random;
									globalPixels.get(random).livingBeing.add(r);
									//allRats.add(r);
									foundLocation = false;
								}
							random+= 1201;
							}
							System.out.println("made rat");
						allRats.add(r);
						synchronized (this) {}
						pool.execute(r);
					
				}
				System.out.println("made all of the rats");
				client.renderThread.start();
				startButtonPressed = true;
			}
		};
		
		
		//System.out.println(globalPixels.size());
		
		//Creates a Vector holding each pixel in the frame
		
		/******** Parse Data ********/
		//This was done for testing purposes
		//parseFullXMLData("./test2.xml");
		
		/******** Panel Declarations ********/
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		menuBar = new JMenuBar();
		/*** Help Menu ***/
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		aboutItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				JOptionPane.showMessageDialog(TestClient.this, "Created by Connor Poole, Angela Hou, Christine Hennes,"
						+ " and Carter Rawlings", 
						"About Virus Simulation", 
						JOptionPane.INFORMATION_MESSAGE);
	
			}	
		});
		
		JMenuItem helpItem = new JMenuItem("Help");
		helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		helpItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				JDialog jd = new JDialog();
				jd.setResizable(false);
				jd.setTitle("Help");
				jd.setLocationRelativeTo(TestClient.this);
				jd.setSize(800,500);
				
				//TODO FIX ALL OF THE HELP STUFF
				JPanel helpPanel = new JPanel();
				//JScrollPane jsp = new JScrollPane();
				JTextArea aboutL = new JTextArea();
				JScrollPane jsp = new JScrollPane(aboutL);
				
				String path1 = "./xmlHelp.txt";
				Scanner scan = new Scanner(path1);
				try{
					FileReader f = new FileReader(path1);
					BufferedReader b = new BufferedReader(f);
					
					String line = b.readLine();
					while(line!= null){
						aboutL.append(line + "\n");
						line = b.readLine();
					}
					
				}catch(FileNotFoundException fnfe){
					System.out.println("Error: " + fnfe.getMessage());
				}
				catch(IOException ioe){
					System.out.println("Error: " + ioe.getMessage());
				}
				
				
				helpPanel.add(jsp);
				jd.add(helpPanel);
				jd.setVisible(true);
				
	
			}	
		});
		helpMenu.add(aboutItem);
		helpMenu.add(helpItem);
		
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);

		upperPanel = new Panel();
		lowerPanel = newLowerPanel();
		upperPanel.setPreferredSize(new Dimension(1200, 800));
		lowerPanel.setPreferredSize(new Dimension(1200, 180));
		lowerPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		mainPanel.add(upperPanel);
		mainPanel.add(lowerPanel);
		add(mainPanel);
		
		/******** Window Specifications ********/
		setSize(1200, 905);
		setLocation(100, 0);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/******* Run ********/
	public void run(){		
		Image buffer = createImage(upperPanel.getWidth(), upperPanel.getHeight());
		Graphics gh = buffer.getGraphics();
		Graphics gc = upperPanel.getGraphics();
		System.out.println("Above the while loop");
		while(true){
			
			gh.setColor(Color.white);
            gh.fillRect(0, 0, upperPanel.getWidth(), upperPanel.getHeight());
           
            //if(startButtonPressed){
            this.drawSewers(gh);
           // System.out.println("Getting drawn");
            this.drawStreets(gh);
            this.drawHospitals(gh);
            //}
            
            synchronized (allHumans){
            	for (Human h : allHumans){
            		h.draw(gh);
            	}
            }
            synchronized(allRats){
            	for (Rat r: allRats){
            		r.draw(gh);
            	}
            }
            if(parsedSewers && parsedStreets && parsedHospitals){
            gc.drawImage(buffer, 0, 65, upperPanel);
            try {Thread.sleep(5);} catch(InterruptedException e) {}
            }
		}
	}
	/******** Panel Methods ********/
	public class Panel extends JPanel{
	//public class Component extends JComponent{
		Panel(){}
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
		}
	}
	
	public JPanel newLowerPanel(){
		

		lowerPanel = new JPanel();
		JPanel bot =  new JPanel();
		bot.setLayout(new GridBagLayout());
		leftPanel = new JPanel();
		middlePanel = new JPanel();
		ratField = new JTextField(10);
		humansField = new JTextField(10);
		contagionField = new JTextField(10);
		JButton startButton = new JButton("Start Simulation");

		String options[] = new String[10];
		for(int j=0; j< 10; j++){
			options[j] = String.valueOf(j+1);
		}
		virusStrengthCombo = new JComboBox(options);
		
		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));	
		ratLabel =  new JLabel("Number of Rats       ");
		humanLabel = new JLabel("Number of Humans ");
		strengthLabel = new JLabel("Strength of Virus        ");
		contagionLabel = new JLabel("Contagion Level (0-1)");
		GridBagConstraints gbc = new GridBagConstraints();
		
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		JPanel ratPanel = new JPanel();
		JPanel humanPanel = new JPanel();
		
		//Rat Panel
		ratPanel.add(ratLabel);
		ratPanel.add(ratField);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		bot.add(ratPanel, gbc);

		//Human Panel
		humanPanel.add(humanLabel);
		humanPanel.add(humansField);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		bot.add(humanPanel, gbc);
		
		//Contagion Panel
		JPanel contagionPanel = new JPanel();
		contagionPanel.add(contagionLabel);
		contagionPanel.add(contagionField);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		bot.add(contagionPanel, gbc);

		//Virus Strength Panel
		JPanel virusStrengthPanel = new JPanel();
		virusStrengthPanel.add(strengthLabel);
		virusStrengthPanel.add(virusStrengthCombo);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		bot.add(virusStrengthPanel, gbc);

		//Start Button
		startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				
				if(checkVariableEntries()){
				
					//System.out.println("YO");
					repaint();
					//TODO call the below functions somewhere else
					//genStreetPixels();
					//genSewerPixels();
					beenPressed = true;
					//sewersDrawn = true;
					
					System.out.println("YO2");
				}
				else{
				
					JOptionPane.showMessageDialog(TestClient.this, "Incorrect entry for one or more field or error in loading file(s)", 
							"Error", 
							JOptionPane.ERROR_MESSAGE);
		
					
				}
				
			}
		});
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		bot.add(new JButton(launchHuman), gbc);
		//bot.add(startButton, gbc);

		//Question Button
		JButton questionButton = new JButton("Variable Help (?)");
		questionButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				
				JPanel questionPanel = new JPanel();
				questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
				JLabel ratHelpLabel = new JLabel("Rats: Enter number between 20 and 500");
				JLabel humanHelpLabel = new JLabel("Humans: Enter number between 100 and 3000");
				JLabel contagionHelpLabel = new JLabel("Contagion: Enter a number between 0 and 1");
				
				questionPanel.add(ratHelpLabel);
				questionPanel.add(humanHelpLabel);
				questionPanel.add(contagionHelpLabel);
				
				JOptionPane.showConfirmDialog(null,
	                    questionPanel,
	                    "Variable Help",
	                    JOptionPane.PLAIN_MESSAGE);

				
				
			}
		});
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		bot.add(questionButton, gbc);
		
		//Top Panel with Label
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		top.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel topLabel = new JLabel("Virus Simulation Variables");
		topLabel.setFont(new Font("Arial", Font.BOLD, 18));
		fileDisplay = new JLabel("File Selected: No File Currently Selected");
		top.add(topLabel);
//		top.add(Box.createVerticalGlue());
		top.add(fileDisplay);
		
		lowerPanel.add(top);
		lowerPanel.add(bot);
		
		return lowerPanel;
	}

	public boolean checkVariableEntries(){
		
		boolean b;
		
		if(getContagionLevel() >=0 && getContagionLevel() <= 1){
			if(getNumberofHumans() >= 100 && getNumberofHumans() <= 3000){
				if(getNumberofRats() >= 20 && getNumberofRats() <= 500){
					if(parsedSewers && parsedStreets && parsedHospitals){
						b = true;
					}
					else {
						b = false;
					}
				}
				else{
					b = false;
				}
			}
			else{
				b = false;
			}
		}
		else{
			b = false;
		}
		
		return b;
	
	}
	
	/******** Accessor Methods to User Input Data ********/
	public double getContagionLevel(){	
		double contagion = -1;
		
		if(!contagionField.getText().isEmpty()){
			try{
			contagion = Double.parseDouble(contagionField.getText());	
			}
			catch(Exception e){
				contagion = -1;
			}

		}
		
		return contagion;
		
	}
	
	public int getNumberofHumans(){
		
		totalHumans = -1;
		
		if(!humansField.getText().isEmpty()){
			try{
				totalHumans = Integer.parseInt(humansField.getText());
			}
			catch(Exception e){
				totalHumans = -1;
			}
		}
		return totalHumans;
	}
	
	public int getNumberofRats(){
		
		totalRats = -1;
		
		if(!ratField.getText().isEmpty()){
			try{
				totalRats = Integer.parseInt(ratField.getText());
			}
			catch(Exception e){
				totalRats = -1;
			}
		}
		return totalRats;
	}
	
	public int getVirusStrength(){
		int strength = virusStrengthCombo.getSelectedIndex() + 1;
		return strength;
	}
	
	/******** Draw Components Methods ********/
	public void drawRats(Graphics g)
	{
		for(int j = 0; j < allRats.size(); j++)
		{
			g.setColor(Color.CYAN);
			g.fillRect(allRats.get(j).getCurrentX(), allRats.get(j).getCurrentY(), 3,3);
		}
	}
	
	public void drawHumans(Graphics g)
	{
			for(int j = 0; j < allHumans.size(); j++)
			{
				//g.setColor(Color.orange);
				g.fillRect(allHumans.get(j).getCurrentX(), allHumans.get(j).getCurrentY(), 3, 3);
			}
		}

	public void drawStreets(Graphics g){
		
		//System.out.println("here");
		
		g.setColor(Color.LIGHT_GRAY);//Change color
		//System.out.println(allStreets.size());
		for(int i=0; i< allStreets.size(); i++){
			if(allStreets.get(i).getStartXLocation() == allStreets.get(i).getEndXLocation()){
				//draw vertical line
				g.fillRect(allStreets.get(i).getStartXLocation(),allStreets.get(i).getStartYLocation(),10,allStreets.get(i).getEndYLocation()-allStreets.get(i).getStartYLocation());
			}
			else if(allStreets.get(i).getStartYLocation() == allStreets.get(i).getEndYLocation()){
				//draw horizontal line
				g.fillRect(allStreets.get(i).getStartXLocation(),allStreets.get(i).getStartYLocation(),allStreets.get(i).getEndXLocation()-allStreets.get(i).getStartXLocation(),10);

			}
			else{
				//draw diagonal line
				for(int x=0; x<15; x++){
					g.drawLine(allStreets.get(i).getStartXLocation()+x, allStreets.get(i).getStartYLocation(), 
							allStreets.get(i).getEndXLocation()+x, allStreets.get(i).getEndYLocation());

				}
			}
			
		}
		this.parsedStreets = true;
		g.setColor(Color.BLACK); //reset color
		
	}

	public void drawSewers(Graphics g){

		g.setColor(Color.GRAY);//Change color
		
		for(int i=0; i< allSewers.size(); i++){
			if(allSewers.get(i).getStartXLocation() == allSewers.get(i).getEndXLocation()){
				//draw vertical line
				g.fillRect(allSewers.get(i).getStartXLocation(),allSewers.get(i).getStartYLocation(),10,allSewers.get(i).getEndYLocation()-allSewers.get(i).getStartYLocation());

			}
			else if(allSewers.get(i).getStartYLocation() == allSewers.get(i).getEndYLocation()){
				//draw horizontal line
				g.fillRect(allSewers.get(i).getStartXLocation(),allSewers.get(i).getStartYLocation(),allSewers.get(i).getEndXLocation()-allSewers.get(i).getStartXLocation(),10);

			}
			else{
				//draw diagonal line
				for(int x=0; x<20; x++){
					g.drawLine(allSewers.get(i).getStartXLocation()+x, allSewers.get(i).getStartYLocation(), 
							allSewers.get(i).getEndXLocation()+x, allSewers.get(i).getEndYLocation());

				}
			}
			
		}
		this.parsedSewers = true;
		g.setColor(Color.BLACK); //reset color
		
	}
	
	public void drawHospitals(Graphics g){
		
		g.setColor(Color.black);//Change color
			
		for(int i=0; i< allHospitals.size(); i++){
			g.fillRect(allHospitals.get(i).getXLocation(), allHospitals.get(i).getYLocation(), 
					allHospitals.get(i).getHospWidth(), allHospitals.get(i).getHospHeight());
		}
		this.parsedHospitals = true;
		g.setColor(Color.BLACK); //reset color
	}
	
	/*********** Pixel Generation Methods **********/
	public void generatePixelsRectangle(int startX, int startY, int width, int height, String type){
		for(int y= startY; y<(height + startY); y++){
			for(int x= startX; x<(width+startX); x++){				
				int index = 1200*y + x;
//				if(!(globalPixels.get(index).type.equals("error"))){
//					//System.out.println("Changing rectangle to streer_sewer");
//					globalPixels.get(index).type = "street_sewer";
//				}
				if(globalPixels.get(index).type.equals("street") && type.equals("sewer")){
					globalPixels.get(index).type = "street_sewer";
				}
				else if (globalPixels.get(index).type.equals("sewer") && type.equals("street")){
					globalPixels.get(index).type = "street_sewer";
				}
				else if (globalPixels.get(index).type.equals("street_sewer")){
					globalPixels.get(index).type = "street_sewer";
				}
				
				else{
					globalPixels.get(index).type = type;
					globalPixels.get(index).xLoc = x;
					globalPixels.get(index).yLoc = y;
				}
			}
		}
	}
	
	public void generatePixelsDiagonal(int startX, int startY, int endX, int endY, String type){
//		
//		System.out.println("starx: " + startX);
//		System.out.println("stary: " + startY);
//		System.out.println("endx: " + endX);
//		System.out.println("endy: " + endY);
//		System.out.println("In gen pix diag. type: " + type);
//		
		//g.setColor(Color.BLACK); 
		double slope;
		if(endX - startX > 0){
//			System.out.println("Calculating slope");
//			System.out.println("endY-startY: " +(endY-startY) );
//			System.out.println("endX-startX: " + (endX-startX));
			slope = -1.0*((1.0*(endY-startY))/(1.0*(endX-startX)));
//			System.out.println("Slope: " + slope);
		}
		else{
			slope = 0;
		}

		double b = startY - (slope*startX);
		double y = startY;
		int index = (int)(1200*y + startX);
		//System.out.println("Starting index: " + index);
		//System.out.println("Slope: " + slope);
		//System.out.println("Starting y: " + y);
		//if(startX < endX){
		if(startY < endY){
			for(double x = startX; x < endX; x++){
				//y = Math.floor(y + slope);
				
				//y = Math.floor((-1*slope)*x +b);
				//y = (-1*slope)*x +b;
//				int index = (int)(1200*y + x);
				
				//y = Math.floor(y - slope);
				y = y - slope;
				if ( y< 0){
					y = 0;
				}
				
				index = (int)(1200*y + x);
				if(globalPixels.get(index).type.equals("street") && type.equals("sewer")){
					globalPixels.get(index).type = "street_sewer";
				}
				else if (globalPixels.get(index).type.equals("sewer") && type.equals("street")){
					globalPixels.get(index).type = "street_sewer";
				}
				else if (globalPixels.get(index).type.equals("street_sewer")){
					globalPixels.get(index).type = "street_sewer";
				}
					//System.out.println("Index: " + index);
					//System.out.println("changing it to street_sewer");
					
				else{
					//System.out.println("Where type is still normal type: " + type);
					globalPixels.get(index).type = type;
					  globalPixels.get(index).xLoc = (int)x;
					    globalPixels.get(index).yLoc = (int)y;
				}
			    
			}
		}
		
		
		else{
			//for(double x=endX; x>startX; x--){
			for(double x=startX; x < endX; x++){
				//System.out.println("Current x: " + x);
				//System.out.println("Current y: " + y);
				
				//y = Math.floor(y - slope);
				y = y - slope;
				if ( y< 0){
					y = 0;
				}
				
				//System.out.println("New y: " + y);
				//y = Math.floor((-1*slope)*x +b);
//				int index = (int)(1200*y + x);
				index = (int)(1200*y + x);
				//index = (int)(index - (1200*y) + x);
				if(globalPixels.get(index).type.equals("street") && type.equals("sewer")){
					globalPixels.get(index).type = "street_sewer";
				}
				else if (globalPixels.get(index).type.equals("sewer") && type.equals("street")){
					globalPixels.get(index).type = "street_sewer";
				}
				else if (globalPixels.get(index).type.equals("street_sewer")){
					globalPixels.get(index).type = "street_sewer";
				}
				else{
					//System.out.println("Where type is still normal type: " + type);
					globalPixels.get(index).type = type;
					  globalPixels.get(index).xLoc = (int)x;
					    globalPixels.get(index).yLoc = (int)y;
				}
//				System.out.println("x: " + x);
//			     System.out.println("\n\n");
			}
			
		}
//		System.out.println("x: " + test);
//	     System.out.println("\n\n");
	}
	
	public void genStreetPixels(){
		
		for(int i=0; i< allStreets.size(); i++){
			if(allStreets.get(i).getStartXLocation() == allStreets.get(i).getEndXLocation()){
				generatePixelsRectangle(allStreets.get(i).getStartXLocation(),allStreets.get(i).getStartYLocation(),10,(allStreets.get(i).getEndYLocation()-allStreets.get(i).getStartYLocation()),"street");
			}
			else if(allStreets.get(i).getStartYLocation() == allStreets.get(i).getEndYLocation()){
				//draw horizontal line
				generatePixelsRectangle(allStreets.get(i).getStartXLocation(),allStreets.get(i).getStartYLocation(),(allStreets.get(i).getEndXLocation()-allStreets.get(i).getStartXLocation()),10,"street");
			}
			else{
				//draw diagonal line
				for(int x=0; x<15; x++){
					generatePixelsDiagonal(allStreets.get(i).getStartXLocation(),allStreets.get(i).getStartYLocation(),allStreets.get(i).getEndXLocation(),allStreets.get(i).getEndYLocation(),"street");
				}
			}
		}
		streetsDrawn = true;
	}

	public void genSewerPixels(){
		System.out.println("SIze of sewere pixels is: " + allSewers.size());
		for(int i=0; i< allSewers.size(); i++){
			if(allSewers.get(i).getStartXLocation() == allSewers.get(i).getEndXLocation()){
				generatePixelsRectangle(allSewers.get(i).getStartXLocation(),allSewers.get(i).getStartYLocation(),10,allSewers.get(i).getEndYLocation()-allSewers.get(i).getStartYLocation(),"sewer");
			}
			else if(allSewers.get(i).getStartYLocation() == allSewers.get(i).getEndYLocation()){
				//draw horizontal line
				generatePixelsRectangle(allSewers.get(i).getStartXLocation(),allSewers.get(i).getStartYLocation(),allSewers.get(i).getEndXLocation()-allSewers.get(i).getStartXLocation(),10,"sewer");
			}
			else{
				//System.out.println("about to call generate pixel diagonal");
				//draw diagonal line
				for(int x=0; x<20; x++){
					generatePixelsDiagonal(allSewers.get(i).getStartXLocation()+x,allSewers.get(i).getStartYLocation(),allSewers.get(i).getEndXLocation()+x,allSewers.get(i).getEndYLocation(),"sewer");
				}
			}
		}
		sewersDrawn = true;
	}
	
	public void find_neighbors(){
		System.out.println("YO line 1048");
		int xBasedOnIndex;
		int yBasedOnIndex;
		int getX;
		int getY;
		for(int i = 0; i < globalPixels.size(); i++)
		{
			
			if(!(globalPixels.get(i).type.equals("error")))
			{
				xBasedOnIndex = i % 1200;
				yBasedOnIndex = (int)Math.floor(i/1200);
				if(yBasedOnIndex > 0 && yBasedOnIndex < 799 && xBasedOnIndex > 0 && xBasedOnIndex < 1199) //not on the edges
				{
					
					if((globalPixels.get(i-1).type.equals(globalPixels.get(i).type) || globalPixels.get(i-1).type.equals("street_sewer")))//check left
					{
						
						if(globalPixels.get(i-1).pixelNeighbors.contains(globalPixels.get(i)))
						{
							
						}
						else
						{
							
							globalPixels.get(i-1).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1));
						}
					}
					if((globalPixels.get(i+1).type.equals(globalPixels.get(i).type)|| globalPixels.get(i+1).type.equals("street_sewer"))) //check right
					{
						if(globalPixels.get(i+1).pixelNeighbors.contains(globalPixels.get(i)))
						{}
						else
						{
							globalPixels.get(i+1).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1));
						}
					}
					if((globalPixels.get(i-1200).type.equals(globalPixels.get(i).type)|| globalPixels.get(i-1200).type.equals("street_sewer"))) //check up
					{
						if(globalPixels.get(i-1200).pixelNeighbors.contains(globalPixels.get(i)))
						{}
						else
						{
							globalPixels.get(i-1200).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1200));
						}
					}
					if((globalPixels.get(i + 1200).type.equals(globalPixels.get(i).type)|| globalPixels.get(i+1200).type.equals("street_sewer"))) //check down
					{
						if(globalPixels.get(i+1200).pixelNeighbors.contains(globalPixels.get(i)))
						{}
						else
						{
							globalPixels.get(i+1200).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1200));
						}
					}
					if((globalPixels.get(i-1201).type.equals(globalPixels.get(i).type)|| globalPixels.get(i-1201).type.equals("street_sewer"))) //check up left
					{
						if(globalPixels.get(i-1201).pixelNeighbors.contains(globalPixels.get(i)))
						{}
						else
						{
							globalPixels.get(i-1201).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1201));
						}
					}
					if((globalPixels.get(i-1200).type.equals(globalPixels.get(i).type)|| globalPixels.get(i-1200).type.equals("street_sewer"))) //check up
					{
						if(globalPixels.get(i-1200).pixelNeighbors.contains(globalPixels.get(i)))
						{}
						else
						{
							globalPixels.get(i-1200).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1200));
						}
					}
					if((globalPixels.get(i + 1199).type.equals(globalPixels.get(i).type)|| globalPixels.get(i+1199).type.equals("street_sewer"))) //check down left
					{
						if(globalPixels.get(i+1199).pixelNeighbors.contains(globalPixels.get(i)))
						{}
						else
						{
							globalPixels.get(i+1199).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1199));
						}
					}
					
					
					if((globalPixels.get(i+1201).type.equals(globalPixels.get(i).type)|| globalPixels.get(i+1201).type.equals("street_sewer"))) //checks down right
					{
						if(globalPixels.get(i+1201).pixelNeighbors.contains(globalPixels.get(i)))
						{}
						else
						{
							globalPixels.get(i+1201).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1201));
						}
					}
					
					if((globalPixels.get(i-1199).type.equals(globalPixels.get(i).type)|| globalPixels.get(i-1199).type.equals("street_sewer"))) //check up right
					{
						if(globalPixels.get(i-1199).pixelNeighbors.contains(globalPixels.get(i)))
						{}
						else
						{
							globalPixels.get(i-1199).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1199));
						}
					}
					
				}
				else if(yBasedOnIndex == 0) //top row
				{
					if(xBasedOnIndex == 0) //checks for 0,0 Top Left
					{
						if((globalPixels.get(i+1).type.equals(globalPixels.get(i).type)|| globalPixels.get(i+1).type.equals("street_sewer"))) //check right
						{
							if(globalPixels.get(i+1).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i+1).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1));
							}
						}
						if((globalPixels.get(i + 1200).type.equals(globalPixels.get(i).type)|| globalPixels.get(i+1200).type.equals("street_sewer"))) //check down
						{
							if(globalPixels.get(i+1200).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i+1200).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1200));
							}
						}
						if((globalPixels.get(i+1201).type.equals(globalPixels.get(i).type)|| globalPixels.get(i+1201).type.equals("street_sewer"))) //checks down right
						{
							if(globalPixels.get(i+1201).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i+1201).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1201));
							}
						}
					}
					else if(xBasedOnIndex == 1199) //checks for 0,1199 Top right
					{
						if((globalPixels.get(i + 1200).type.equals(globalPixels.get(i).type)|| globalPixels.get(i+1200).type.equals("street_sewer"))) //check down
						{
							if(globalPixels.get(i+1200).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i+1200).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1200));
							}
						}
						if((globalPixels.get(i-1).type.equals(globalPixels.get(i).type)|| globalPixels.get(i-1).type.equals("street_sewer")))//check left
						{
							if(globalPixels.get(i-1).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i-1).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1));
							}
						}
						if((globalPixels.get(i + 1199).type.equals(globalPixels.get(i).type)|| globalPixels.get(i+1199).type.equals("street_sewer"))) //check down left
						{
							if(globalPixels.get(i+1199).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i+1199).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1199));
							}
						}
					}
					else
					{
						if((globalPixels.get(i-1).type.equals(globalPixels.get(i).type)|| globalPixels.get(i-1).type.equals("street_sewer")))//check left
						{
							if(globalPixels.get(i-1).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i-1).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1));
							}
						}
						if((globalPixels.get(i+1).type.equals(globalPixels.get(i).type)|| globalPixels.get(i+1).type.equals("street_sewer"))) //check right
						{
							if(globalPixels.get(i+1).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i+1).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1));
							}
							
						}
						if((globalPixels.get(i + 1199).type.equals(globalPixels.get(i).type)|| globalPixels.get(i+1199).type.equals("street_sewer"))) //check down left
						{
							if(globalPixels.get(i+1199).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i+1199).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1199));
							}
						}
						if((globalPixels.get(i + 1200).type.equals(globalPixels.get(i).type)|| globalPixels.get(i+1200).type.equals("street_sewer"))) //check down
						{
							if(globalPixels.get(i+1200).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i+1200).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1200));
							}
						}
						if((globalPixels.get(i+1201).type.equals(globalPixels.get(i).type)|| globalPixels.get(i+1201).type.equals("street_sewer"))) //checks down right
						{
							if(globalPixels.get(i+1201).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i+1201).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1201));
							}
						}
					}
					
				}
				else if(yBasedOnIndex == 799) //bottom row
				{
					if(xBasedOnIndex == 0) //bottom left 0,799
					{
						if((globalPixels.get(i-1200).type.equals(globalPixels.get(i).type)|| globalPixels.get(i-1200).type.equals("street_sewer"))) //check up
						{
							if(globalPixels.get(i-1200).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i-1200).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1200));
							}
						}
						if((globalPixels.get(i-1199).type.equals(globalPixels.get(i).type)|| globalPixels.get(i-1199).type.equals("street_sewer"))) //check up right
						{
							if(globalPixels.get(i-1).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i-1199).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1199));
							}
							
						}
						if((globalPixels.get(i+1).type.equals(globalPixels.get(i).type)|| globalPixels.get(i+1).type.equals("street_sewer"))) //check right
						{
							if(globalPixels.get(i+1).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i+1).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1));
							}
						}
						
					}
					else if(xBasedOnIndex == 1199) //bottom right 1199,799
					{
						if((globalPixels.get(i-1201).type.equals(globalPixels.get(i).type))|| globalPixels.get(i-1201).type.equals("street_sewer")) //check up left
						{
							if(globalPixels.get(i-1201).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i-1201).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1201));
							}
						}
						if((globalPixels.get(i-1200).type.equals(globalPixels.get(i).type))|| globalPixels.get(i-1200).type.equals("street_sewer")) //check up
						{
							if(globalPixels.get(i-1200).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i-1200).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1200));
							}
						}
						if((globalPixels.get(i-1).type.equals(globalPixels.get(i).type))|| globalPixels.get(i-1).type.equals("street_sewer"))//check left
						{
							if(globalPixels.get(i-1).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i-1).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1));
							}
						}
						
					}
					else
					{
						if((globalPixels.get(i-1200).type.equals(globalPixels.get(i).type))|| globalPixels.get(i-1200).type.equals("street_sewer")) //check up
						{
							if(globalPixels.get(i-1200).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i-1200).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1200));
							}
						}
						if((globalPixels.get(i-1199).type.equals(globalPixels.get(i).type))|| globalPixels.get(i-1199).type.equals("street_sewer")) //check up right
						{
							if(globalPixels.get(i-1199).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i-1199).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1199));
							}
						}
						if((globalPixels.get(i+1).type.equals(globalPixels.get(i).type))|| globalPixels.get(i+1).type.equals("street_sewer")) //check right
						{
							if(globalPixels.get(i+1).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i+1).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1));
							}
						}
						if((globalPixels.get(i-1).type.equals(globalPixels.get(i).type))|| globalPixels.get(i-1).type.equals("street_sewer"))//check left
						{
							if(globalPixels.get(i-1).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i-1).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1));
							}
						}
						if((globalPixels.get(i-1201).type.equals(globalPixels.get(i).type))|| globalPixels.get(i-1201).type.equals("street_sewer")) //check up left
						{
							if(globalPixels.get(i-1201).pixelNeighbors.contains(globalPixels.get(i)))
							{}
							else
							{
								globalPixels.get(i-1201).pixelNeighbors.add(globalPixels.get(i));
								globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1201));
							}
						}
					}
				}
				else if(xBasedOnIndex == 0) //left column
				{
					if((globalPixels.get(i-1200).type.equals(globalPixels.get(i).type))|| globalPixels.get(i-1200).type.equals("street_sewer")) //check up
					{
						if(globalPixels.get(i-1200).pixelNeighbors.contains(globalPixels.get(i)))
						{}
						else
						{
							globalPixels.get(i-1200).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1200));
						}
					}
					if((globalPixels.get(i-1199).type.equals(globalPixels.get(i).type))|| globalPixels.get(i-1199).type.equals("street_sewer")) //check up right
					{
						if(globalPixels.get(i-1199).pixelNeighbors.contains(globalPixels.get(i)))
						{}
						else
						{
							globalPixels.get(i-1199).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1199));
						}
					}
					if((globalPixels.get(i+1).type.equals(globalPixels.get(i).type))|| globalPixels.get(i+1).type.equals("street_sewer")) //check right
					{
						if(globalPixels.get(i+1).pixelNeighbors.contains(globalPixels.get(i)))
						{}
						else
						{
							globalPixels.get(i+1).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1));
						}
					}
					if((globalPixels.get(i + 1200).type.equals(globalPixels.get(i).type))|| globalPixels.get(i+1200).type.equals("street_sewer")) //check down
					{
						if(globalPixels.get(i+1200).pixelNeighbors.contains(globalPixels.get(i)))
						{}
						else
						{
							globalPixels.get(i+1200).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1200));
						}
					}
					if((globalPixels.get(i+1201).type.equals(globalPixels.get(i).type))|| globalPixels.get(i+1201).type.equals("street_sewer")) //checks down right
					{
						if(globalPixels.get(i+1201).pixelNeighbors.contains(globalPixels.get(i)))
						{}
						else
						{
							globalPixels.get(i+1201).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1201));
						}
					}
				}
				else //right column
				{
					if((globalPixels.get(i-1).type.equals(globalPixels.get(i).type))|| globalPixels.get(i-1).type.equals("street_sewer"))//check left
					{
						if(globalPixels.get(i-1).pixelNeighbors.contains(globalPixels.get(i)))
						{}
						else
						{
							globalPixels.get(i-1).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1));
						}
					}
					if((globalPixels.get(i-1201).type.equals(globalPixels.get(i).type))|| globalPixels.get(i-1201).type.equals("street_sewer")) //check up left
					{
						if(globalPixels.get(i-1201).pixelNeighbors.contains(globalPixels.get(i)))
						{}
						else
						{
							globalPixels.get(i-1201).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1201));
						}
					}
					if((globalPixels.get(i-1200).type.equals(globalPixels.get(i).type))|| globalPixels.get(i-1200).type.equals("street_sewer")) //check up
					{
						if(globalPixels.get(i-1200).pixelNeighbors.contains(globalPixels.get(i)))
						{}
						else
						{
							globalPixels.get(i-1200).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i-1200));
						}
					}
					if((globalPixels.get(i + 1199).type.equals(globalPixels.get(i).type))|| globalPixels.get(i+1199).type.equals("street_sewer")) //check down left
					{
						if(globalPixels.get(i+1199).pixelNeighbors.contains(globalPixels.get(i)))
						{}
						else
						{
							globalPixels.get(i+1199).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1199));
						}
					}
					if((globalPixels.get(i + 1200).type.equals(globalPixels.get(i).type))|| globalPixels.get(i+1200).type.equals("street_sewer")) //check down
					{
						if(globalPixels.get(i+1200).pixelNeighbors.contains(globalPixels.get(i)))
						{}
						else
						{
							globalPixels.get(i+1200).pixelNeighbors.add(globalPixels.get(i));
							globalPixels.get(i).pixelNeighbors.add(globalPixels.get(i+1200));
						}
					}
				}
			}

			
		}
		
	
	}
	
	public void genHumans(){
		int numOfHumans = getNumberofHumans();
		for(int i = 0; i < numOfHumans; i++)
		{
			Random randomGenerator = new Random();
			
			int random = randomGenerator.nextInt(960000);
			Boolean whatever = true;
			Human h = new Human();
				while(whatever)
				{
					if(random >= globalPixels.size())
					{
						random = 0;
					}
					if(globalPixels.get(random).type.equals("street") || globalPixels.get(random).type.equals("street_sewer"))
					{
						h.setCurrentX(globalPixels.get(random).xLoc);
						h.setCurrentY(globalPixels.get(random).yLoc);
						h.index = random;
						globalPixels.get(random).livingBeing.add(h);
						allHumans.add(h);
						whatever = false;
					}
				random+=799;
				}
		}
	}
	
	public void genRats(){

		
		int numOfRats = getNumberofRats();
		for(int i = 0; i < numOfRats; i++)
		{
			Random randomGenerator = new Random();
			
			int random = randomGenerator.nextInt(960000);
			Boolean whatever = true;
			Rat r = new Rat();
				while(whatever)
		
				{
					if(random >= globalPixels.size())
					{
						random = 0;
					}
					if(globalPixels.get(random).type.equals("sewer") || globalPixels.get(random).type.equals("street_sewer"))
					{
						r.setCurrentX(globalPixels.get(random).xLoc);
						r.setCurrentY(globalPixels.get(random).yLoc);
						r.index = random;
						globalPixels.get(random).livingBeing.add(r);
						allRats.add(r);
						whatever = false;
					}
				random+= 799;
				}
		}
	}
	
	private void json_parse(String json_string, String type) throws JSONException{
		try{
			obj= new JSONObject(json_string);
		}catch(JSONException e){
			e.printStackTrace();
		}
		if(type == "street"){
			Street s = new Street(obj.getInt("startXLocation"),obj.getInt("startYLocation"),obj.getInt("endXLocation"),obj.getInt("endYLocation"));
			allStreets.add(s);
			s.printMe();
			System.out.println("adding streets");
		}
		else if(type == "sewer"){
			//Sewer(int startX, int startY, int endX, int endY){
			Sewer s = new Sewer(obj.getInt("startXLocation"),obj.getInt("startYLocation"),obj.getInt("endXLocation"),obj.getInt("endYLocation"));
			allSewers.add(s);
			System.out.println("Adding sewers");
		}
		else if(type == "hospital"){
			//Hospital(int x, int y, int w, int h,int capacity, int numOfOccupants){
			Hospital h = new Hospital(obj.getInt("hospitalXLocation"),obj.getInt("hospitalYLocation"),obj.getInt("hospitalWidth"),obj.getInt("hospitalHeight"),obj.getInt("capacity"),obj.getInt("numOfOccupants"));
			allHospitals.add(h);
		}		
	}
	public static void main(String[] args) throws Exception {
		client = new TestClient();
		client.renderThread.setPriority(Thread.MAX_PRIORITY);
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.frame.setSize(1200, 700);
        client.frame.setVisible(true);
        client.frame.setResizable(false);
        client.play();
		
	}
	

}
