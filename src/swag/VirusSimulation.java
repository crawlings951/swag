package swag;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

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
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class VirusSimulation extends JFrame {
	
	Vector<Street> allStreets;
	Vector<Sewer> allSewers;
	Vector<Hospital> allHospitals;
	Vector<Pixel> globalPixels;
	
	JTextField ratField;
	JTextField humansField;
	JTextField contagionField;
	JComboBox virusStrengthCombo;
	JMenuBar menuBar;
	JMenu fileMenu, helpMenu, newSimulationMenu;
	JPanel lowerPanel;
	JPanel mainPanel, upperPanel;
	JPanel leftPanel, rightPanel, middlePanel;
	JLabel ratLabel, humanLabel, strengthLabel, contagionLabel;
	public static int totalHumans;
	public static int totalRats;
	

	/******** Constructor ********/
	VirusSimulation(){
		super("Virus Simulation");
		
		/******** Vector Instantiations ********/
		allStreets = new Vector<Street>();
		allSewers = new Vector<Sewer>();
		allHospitals = new Vector<Hospital>();
		globalPixels = new Vector<Pixel>();
		
		/******** Parse Data ********/
		//This was done for testing purposes
		//parseFullXMLData("./unitTest.xml");
		
		/******** Panel Declarations ********/
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		menuBar = new JMenuBar();
		
		/*** File Menu ***/
		fileMenu = new JMenu("File");
		JMenuItem exisitingSimulationItem = new JMenuItem("Exisiting Simulation");
		
		exisitingSimulationItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				System.out.println("Inside of exisiting simluation");
				//TODO finish this by pulling out the data from the database
			}
		});
		
		//New simulation menu
		newSimulationMenu = new JMenu("New Simulation");
		JMenuItem singleFile = new JMenuItem("Single File");
		singleFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				
				JPanel singlePanel = new JPanel();
				JPanel centerPanel = new JPanel();
				JLabel fileLabel = new JLabel("File: ");
				final JTextField fileT = new JTextField(20);
				JButton selectT = new JButton("Select");
				selectT.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ae){
						
						//TODO open a jfilechooser and then add the path to the jtextfield
						JFileChooser chooser = new JFileChooser();
				        chooser.setMultiSelectionEnabled(false);
				        File sf = null;
				        
				        int option = chooser.showOpenDialog(VirusSimulation.this);
				        if (option == JFileChooser.APPROVE_OPTION) {
				          sf = chooser.getSelectedFile();
				        }
				        String path = sf.getName();
				        fileT.setText(path);
				        
				        //TODO GET THE PATH USING sf.getPath();
				        
				        parseFullXMLData(sf.getPath());
				        
						
					}
				});
				
				centerPanel.add(fileLabel);
				centerPanel.add(fileT);
				centerPanel.add(selectT);
				
				singlePanel.add(centerPanel);
				
				JOptionPane.showConfirmDialog(null,
	                    singlePanel,
	                    "New Simulation",
	                    JOptionPane.OK_CANCEL_OPTION,
	                    JOptionPane.PLAIN_MESSAGE);
				
			}
			
		});
		
		//TODO add action listener
		JMenuItem multipleFiles = new JMenuItem("Multiple Files");
		multipleFiles.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				
				JPanel topP = new JPanel();
				JPanel midP = new JPanel();
				JPanel botP = new JPanel();
				JPanel wholePanel = new JPanel();
				wholePanel.setLayout(new BoxLayout(wholePanel, BoxLayout.Y_AXIS));
				
				JLabel sewerL = new JLabel("Sewer File:     ");
				final JTextField sewerT = new JTextField(20);
				JButton sewerB = new JButton("Select");
				sewerB.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ae){

						JFileChooser chooser = new JFileChooser();
				        chooser.setMultiSelectionEnabled(false);
				        File sf = null;
				        
				        int option = chooser.showOpenDialog(VirusSimulation.this);
				        if (option == JFileChooser.APPROVE_OPTION) {
				          sf = chooser.getSelectedFile();
				        }
				        String path = sf.getName();
				        sewerT.setText(path);
						parseSewers(sf);

					}
				});
				
				JLabel roadL = new JLabel("Road File:      ");
				final JTextField roadT = new JTextField(20);
				JButton roadB = new JButton("Select");
				roadB.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ae){
						
						JFileChooser chooser = new JFileChooser();
				        chooser.setMultiSelectionEnabled(false);
				        File sf = null;
				        
				        int option = chooser.showOpenDialog(VirusSimulation.this);
				        if (option == JFileChooser.APPROVE_OPTION) {
				          sf = chooser.getSelectedFile();
				        }
				        String path = sf.getName();
				        roadT.setText(path);
				        parseStreets(sf);

					}
				});
				
				JLabel hospL = new JLabel("Hospital File: ");
				final JTextField hospT = new JTextField(20);
				JButton hospB = new JButton("Select");
				hospB.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ae){

						JFileChooser chooser = new JFileChooser();
				        chooser.setMultiSelectionEnabled(false);
				        File sf = null;
				        
				        int option = chooser.showOpenDialog(VirusSimulation.this);
				        if (option == JFileChooser.APPROVE_OPTION) {
				          sf = chooser.getSelectedFile();
				        }
				        String path = sf.getName();
				        hospT.setText(path);
				        //System.out.println("path: " + path);

						parseHospitals(sf);

						
					}
				});
				
				topP.add(sewerL);
				topP.add(sewerT);
				topP.add(sewerB);
				
				midP.add(roadL);
				midP.add(roadT);
				midP.add(roadB);
				
				botP.add(hospL);
				botP.add(hospT);
				botP.add(hospB);
				
				wholePanel.add(topP);
				wholePanel.add(midP);
				wholePanel.add(botP);
				
				JOptionPane.showConfirmDialog(null,
	                    wholePanel,
	                    "New Simulation",
	                    JOptionPane.OK_CANCEL_OPTION,
	                    JOptionPane.PLAIN_MESSAGE);
				
				//repaint();
				
			}
		});
		
		newSimulationMenu.add(singleFile);
		newSimulationMenu.add(multipleFiles);
		
		fileMenu.add(exisitingSimulationItem);
		fileMenu.add(newSimulationMenu);
		
		/*** Help Menu ***/
		helpMenu = new JMenu("Help");
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				JOptionPane.showMessageDialog(VirusSimulation.this, "Created by Connor Poole, Christine Hennes,"
						+ " and Carter Rawlings", 
						"About Virus Simulation", 
						JOptionPane.INFORMATION_MESSAGE);
	
			}	
		});
		
		JMenuItem helpItem = new JMenuItem("Help");
		helpItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				JDialog jd = new JDialog();
				jd.setResizable(false);
				jd.setTitle("Help");
				jd.setLocationRelativeTo(VirusSimulation.this);
				jd.setSize(330,250);
				
				//TODO FIX ALL OF THE HELP STUFF
				JPanel helpPanel = new JPanel();
				JLabel aboutL = new JLabel("This is where all the help stuff is going to go");
				helpPanel.add(aboutL);
				jd.add(helpPanel);
				jd.setVisible(true);
				
	
			}	
		});
		helpMenu.add(aboutItem);
		helpMenu.add(helpItem);
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);

		
		upperPanel = new Panel();
		lowerPanel = newLowerPanel();
		upperPanel.setPreferredSize(new Dimension(1200, 605));
		lowerPanel.setPreferredSize(new Dimension(1200, 195));
		lowerPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		mainPanel.add(upperPanel);
		mainPanel.add(lowerPanel);
		add(mainPanel);
		
		//TODO add these to happen on the press of a start button
//		genStreetPixels();
//		genSewerPixels();
		
		/******** Window Specifications ********/
		setSize(1200, 800);
		setLocation(100, 0);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}
	
	/******** Main Method ********/
	public static void main(String args[]){	
		new VirusSimulation();
	}
	
	/******** Parsing Methods ********/
	public void parseFullXMLData(String path){
		try{
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			parseStreets(fXmlFile);
			parseSewers(fXmlFile);
			parseHospitals(fXmlFile);
			
			
		} catch (Exception e) {
	    	JOptionPane.showMessageDialog(VirusSimulation.this, 
	    			"Incorrect file format", 
	    			"Error", 
	    			JOptionPane.ERROR_MESSAGE);
	    }
		
		
		
	}

	public void parseStreets(File file){
		Document doc = null;
		try{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();
		}
		catch (Exception e) {
	    	JOptionPane.showMessageDialog(VirusSimulation.this, 
	    			"Incorrect file format", 
	    			"Error", 
	    			JOptionPane.ERROR_MESSAGE);
	    }
		
		NodeList streetList = doc.getElementsByTagName("street");
		
		for(int i = 0; i < streetList.getLength(); i++){
			Node streetNode = streetList.item(i);
			
			if(streetNode.getNodeType() == Node.ELEMENT_NODE){
				Element e = (Element) streetNode;
				
				Element eS = (Element) (Node) e.getElementsByTagName("startlocation").item(0);
				int sX = Integer.parseInt(eS.getElementsByTagName("x").item(0).getTextContent());
				int sY = Integer.parseInt(eS.getElementsByTagName("y").item(0).getTextContent());
				
				Element eE = (Element) (Node) e.getElementsByTagName("endlocation").item(0);
				int eX = Integer.parseInt(eE.getElementsByTagName("x").item(0).getTextContent());
				int eY = Integer.parseInt(eE.getElementsByTagName("y").item(0).getTextContent());
				
				System.out.println("Street");
				System.out.println("Start x: " + eS.getElementsByTagName("x").item(0).getTextContent());
				System.out.println("Start y: " + eS.getElementsByTagName("y").item(0).getTextContent());
				System.out.println("End x: " + eE.getElementsByTagName("x").item(0).getTextContent());
				System.out.println("End y: " + eE.getElementsByTagName("y").item(0).getTextContent());
				System.out.println("\n\n");
				
				//SOS Need to create an object 
				Street s = new Street(sX, sY, eX, eY);
				allStreets.add(s);
			}
			
		}
		
		
		
	}
	
	public void parseSewers(File file){
		
		Document doc = null;
		try{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();
		}
		catch (Exception e) {
	    	JOptionPane.showMessageDialog(VirusSimulation.this, 
	    			"Incorrect file format", 
	    			"Error", 
	    			JOptionPane.ERROR_MESSAGE);
	    }
		
		NodeList sewerList = doc.getElementsByTagName("sewer");
		
		for(int i = 0; i < sewerList.getLength(); i++){
			Node streetNode = sewerList.item(i);
			
			if(streetNode.getNodeType() == Node.ELEMENT_NODE){
				Element e = (Element) streetNode;
				
				Element eS = (Element) (Node) e.getElementsByTagName("startlocation").item(0);
				int sX = Integer.parseInt(eS.getElementsByTagName("x").item(0).getTextContent());
				int sY = Integer.parseInt(eS.getElementsByTagName("y").item(0).getTextContent());
				
				Element eE = (Element) (Node) e.getElementsByTagName("endlocation").item(0);
				int eX = Integer.parseInt(eE.getElementsByTagName("x").item(0).getTextContent());
				int eY = Integer.parseInt(eE.getElementsByTagName("y").item(0).getTextContent());

//				System.out.println("Sewer");
//				System.out.println("Start x: " + eS.getElementsByTagName("x").item(0).getTextContent());
//				System.out.println("Start y: " + eS.getElementsByTagName("y").item(0).getTextContent());
//				System.out.println("End x: " + eE.getElementsByTagName("x").item(0).getTextContent());
//				System.out.println("End y: " + eE.getElementsByTagName("y").item(0).getTextContent());
//				System.out.println("\n\n");
				
				Sewer s = new Sewer(sX, sY, eX, eY);
				allSewers.add(s);
				
			}
			
		}
		
	}
	
	public void parseHospitals(File file){
		
		Document doc = null;
		try{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();
		}
		catch (Exception e) {
	    	JOptionPane.showMessageDialog(VirusSimulation.this, 
	    			"Incorrect file format", 
	    			"Error", 
	    			JOptionPane.ERROR_MESSAGE);
	    }
		
		NodeList hospitalList = doc.getElementsByTagName("hospital");
		
		for(int i = 0; i < hospitalList.getLength(); i++){
			Node streetNode = hospitalList.item(i);
			
			if(streetNode.getNodeType() == Node.ELEMENT_NODE){
				
				Element e = (Element) streetNode;
				Element eS = (Element) (Node) e.getElementsByTagName("location").item(0);
				int sX = Integer.parseInt(eS.getElementsByTagName("x").item(0).getTextContent());
				int sY = Integer.parseInt(eS.getElementsByTagName("y").item(0).getTextContent());
				
				Element eE = (Element) (Node) e.getElementsByTagName("size").item(0);
				int w = Integer.parseInt(eE.getElementsByTagName("width").item(0).getTextContent());
				int h = Integer.parseInt(eE.getElementsByTagName("height").item(0).getTextContent());

				System.out.println("Hospital");
				System.out.println("Start x: " + eS.getElementsByTagName("x").item(0).getTextContent());
				System.out.println("Start y: " + eS.getElementsByTagName("y").item(0).getTextContent());
				System.out.println("Width: " + eE.getElementsByTagName("width").item(0).getTextContent());
				System.out.println("Height: " + eE.getElementsByTagName("height").item(0).getTextContent());
				System.out.println("\n\n");
				
				Hospital hosp = new Hospital(sX, sY, w, h);
				allHospitals.add(hosp);
		
			}
		}
	}
	
	
	/******** Panel Methods ********/
	public class Panel extends JPanel{
	//public class Component extends JComponent{
		
		Panel(){
			
		}
		
//		Component(){
//			
//		}
		
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			drawStreets(g);
			drawSewers(g);
			drawHospitals(g);
			//g.fillRect(100, 100, 5, 5);
			
			//add(newLowerPanel());
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
				
					repaint();
					genStreetPixels();
					genSewerPixels();
				}
				else{
				
					JOptionPane.showMessageDialog(VirusSimulation.this, "Incorrect entry for one of more field", 
							"Error", 
							JOptionPane.ERROR_MESSAGE);
		
					
				}
				
			}
		});
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		bot.add(startButton, gbc);

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
		JLabel topLabel = new JLabel("Virus Simulation Variables");
		topLabel.setFont(new Font("Arial", Font.BOLD, 18));
		top.add(topLabel);
		
		lowerPanel.add(top);
		lowerPanel.add(bot);
		
		return lowerPanel;
	}
	
	public boolean checkVariableEntries(){
		
		boolean b;
		
		if(getContagionLevel() >=0 && getContagionLevel() <= 1){
			if(getNumberofHumans() >= 100 && getNumberofHumans() <= 3000){
				if(getNumberofRats() >= 20 && getNumberofRats() <= 500){
					b = true;
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
	public void drawStreets(Graphics g){
		
		//System.out.println("here");
		
		g.setColor(Color.blue);//Change color
		//System.out.println(allStreets.size());
		for(int i=0; i< allStreets.size(); i++){
			if(allStreets.get(i).getStartXLocation() == allStreets.get(i).getEndXLocation()){
				//draw vertical line
				g.fillRect(allStreets.get(i).getStartXLocation(),allStreets.get(i).getStartYLocation(),1,allStreets.get(i).getEndYLocation()-allStreets.get(i).getStartYLocation());
			}
			else if(allStreets.get(i).getStartYLocation() == allStreets.get(i).getEndYLocation()){
				//draw horizontal line
				g.fillRect(allStreets.get(i).getStartXLocation(),allStreets.get(i).getStartYLocation(),allStreets.get(i).getEndXLocation()-allStreets.get(i).getStartXLocation(),1);

			}
			else{
				//draw diagonal line
				for(int x=0; x<15; x++){
					g.drawLine(allStreets.get(i).getStartXLocation()+x, allStreets.get(i).getStartYLocation(), 
							allStreets.get(i).getEndXLocation()+x, allStreets.get(i).getEndYLocation());

				}
			}
			
		}
		
		g.setColor(Color.BLACK); //reset color
		
	}

	public void drawSewers(Graphics g){

		g.setColor(Color.green);//Change color
		
		for(int i=0; i< allSewers.size(); i++){
			if(allSewers.get(i).getStartXLocation() == allSewers.get(i).getEndXLocation()){
				//draw vertical line
				g.fillRect(allSewers.get(i).getStartXLocation(),allSewers.get(i).getStartYLocation(),1,allSewers.get(i).getEndYLocation()-allSewers.get(i).getStartYLocation());

			}
			else if(allSewers.get(i).getStartYLocation() == allSewers.get(i).getEndYLocation()){
				//draw horizontal line
				g.fillRect(allSewers.get(i).getStartXLocation(),allSewers.get(i).getStartYLocation(),allSewers.get(i).getEndXLocation()-allSewers.get(i).getStartXLocation(),1);

			}
			else{
				//draw diagonal line
				for(int x=0; x<20; x++){
					g.drawLine(allSewers.get(i).getStartXLocation()+x, allSewers.get(i).getStartYLocation(), 
							allSewers.get(i).getEndXLocation()+x, allSewers.get(i).getEndYLocation());

				}
			}
			
		}
		
		g.setColor(Color.BLACK); //reset color
		
	}
	
	public void drawHospitals(Graphics g){
		
		g.setColor(Color.RED);//Change color
			
		for(int i=0; i< allHospitals.size(); i++){
			g.fillRect(allHospitals.get(i).getXLocation(), allHospitals.get(i).getYLocation(), 
					allHospitals.get(i).getHospWidth(), allHospitals.get(i).getHospHeight());
		}
		g.setColor(Color.BLACK); //reset color
	}
	
	/*********** Pixel Generation Methods **********/
	public void generatePixelsRectangle(int startX, int startY, int width, int height, String type){
		//g.setColor(Color.BLACK); 
		//System.out.println("Y: " + startY + ",  Start y + height: " + (startY+height));
		//System.out.println("X: " + startX + ",  Start X + width: " + (startX+width));
		for(int y= startY; y<(height + startY); y++){
			for(int x= startX; x<(width+startX); x++){				
				Pixel p = new Pixel();
				if(p.type != type){
					p.type = "street_sewer";
				}
				p.type = type; 
				p.xLoc= x;
				p.yLoc= y;
				globalPixels.add(p);
				//System.out.println(p.xLoc + ", " + p.yLoc + ", " + p.type);
				find_neighbors((int)x,(int)y,p);
				//g.fillRect(x, y, 1, 1);
				//System.out.println("x"+x);
				//System.out.println(y);
			}
		}
	}
	
	public void generatePixelsDiagonal(int startX, int startY, int endX, int endY, String type){
		//g.setColor(Color.BLACK); 
		double slope = (endY-startY)/(endX-startX);
		double b = startY - slope*startX;
		double y = startY;
		if(startX < endX){
		for(double x=startX; x<endX; x++){
			y = Math.floor(slope*x +b);
			Pixel p = new Pixel();
			if(p.type != type){
				p.type = "street_sewer";
			}
			p.type = type; 
			p.xLoc= (int)x;
			p.yLoc= (int)y;
			globalPixels.add(p);
			//System.out.println("YO");
			find_neighbors((int)x,(int)y,p);
			//g.fillRect((int)x, (int)y, 1, 1);
		}
		}
		else{
			for(double x=endX; x>startX; x--){
				y = Math.floor(slope*x +b);
				Pixel p = new Pixel();
				if(p.type != type){	
					p.type = "street_sewer";
				}
				p.type = type; 
				p.xLoc= (int)x;
				p.yLoc= (int)y;
				globalPixels.add(p);
				//System.out.println("YO2");
				find_neighbors((int)x,(int)y,p);
				//g.fillRect((int)x, (int)y, 1, 1);
			}
		}
	}

	public void genStreetPixels(){
		
		for(int i=0; i< allStreets.size(); i++){
			if(allStreets.get(i).getStartXLocation() == allStreets.get(i).getEndXLocation()){
				System.out.println("here");
				//draw vertical line
				generatePixelsRectangle(allStreets.get(i).getStartXLocation(),allStreets.get(i).getStartYLocation(),1,allStreets.get(i).getEndYLocation()-allStreets.get(i).getStartYLocation(),"street");
			}
			else if(allStreets.get(i).getStartYLocation() == allStreets.get(i).getEndYLocation()){
				//draw horizontal line
				generatePixelsRectangle(allStreets.get(i).getStartXLocation(),allStreets.get(i).getStartYLocation(),allStreets.get(i).getEndXLocation()-allStreets.get(i).getStartXLocation(),1,"street");
			}
			else{
				//draw diagonal line
				for(int x=0; x<15; x++){
					generatePixelsDiagonal(allStreets.get(i).getStartXLocation(),allStreets.get(i).getStartYLocation(),allStreets.get(i).getEndXLocation(),allStreets.get(i).getEndYLocation(),"street");
				}
			}
		}
	}

	public void genSewerPixels(){
		for(int i=0; i< allSewers.size(); i++){
			if(allSewers.get(i).getStartXLocation() == allSewers.get(i).getEndXLocation()){
				//draw vertical line
				generatePixelsRectangle(allStreets.get(i).getStartXLocation(),allStreets.get(i).getStartYLocation(),1,allStreets.get(i).getEndYLocation()-allStreets.get(i).getStartYLocation(),"sewer");
			}
			else if(allSewers.get(i).getStartYLocation() == allSewers.get(i).getEndYLocation()){
				//draw horizontal line
				generatePixelsRectangle(allStreets.get(i).getStartXLocation(),allStreets.get(i).getStartYLocation(),allStreets.get(i).getEndXLocation()-allStreets.get(i).getStartXLocation(),1,"sewer");
			}
			else{
				//draw diagonal line
				for(int x=0; x<20; x++){
					generatePixelsDiagonal(allStreets.get(i).getStartXLocation(),allStreets.get(i).getStartYLocation(),allStreets.get(i).getEndXLocation(),allStreets.get(i).getEndYLocation(),"sewer");
				}
			}
		}
	}

	public void find_neighbors(int xLoc, int yLoc, Pixel p){
		//System.out.println(globalPixels.size());
		find_pixel(xLoc + 1, yLoc, p);
		find_pixel(xLoc - 1, yLoc, p);
		find_pixel(xLoc + 1, yLoc+1, p);
		find_pixel(xLoc, yLoc+1, p);
		find_pixel(xLoc-1, yLoc+1, p);
		find_pixel(xLoc-1, yLoc-1, p);
		find_pixel(xLoc+1, yLoc-1, p);
		find_pixel(xLoc, yLoc-1, p);
	}
	
	public void find_pixel(int xLoc, int yLoc, Pixel p){
		Boolean neighborExists = false;
		for(int i = 0; i < globalPixels.size(); i++)
		{
			if(globalPixels.get(i).getxLoc() == xLoc && globalPixels.get(i).getyLoc() == yLoc){
				if(p.pixelNeighbors.contains(globalPixels.get(i))){
					neighborExists = true;
					
					
				}
				if(!neighborExists){
					p.pixelNeighbors.add(globalPixels.get(i));
					globalPixels.get(i).pixelNeighbors.add(p);
					
				}
			}
		}
		
		for(int k = 0; k < globalPixels.size(); k++)
		{
			//System.out.println("Pixel " + globalPixels.get(k).xLoc + ", " + globalPixels.get(k).yLoc + ": ");
			for(int l = 0; l < globalPixels.get(k).pixelNeighbors.size(); l++)
			{
				//System.out.println(" - " + globalPixels.get(k).pixelNeighbors.get(l).xLoc + ", " + globalPixels.get(k).pixelNeighbors.get(l).yLoc);
			}
		}
		
	}



}



