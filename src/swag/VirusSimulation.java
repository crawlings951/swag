package swag;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	JPanel lowerPanel;
	JPanel mainPanel, upperPanel;
	JPanel topPanel, bottomPanel;
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
		//TODO change this so we can add in a single file or multiple files depending 
		//on what the user wants to do
		//TODO don't hardcode in the file 
		parseXMLData("./unitTest.xml");
		
		/******** Panel Declarations ********/
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		// TODO fix the formatting and spacing of the lower panel
		System.out.println("YOOOOOO");
		upperPanel = new Panel();
		lowerPanel = newLowerPanel();
		upperPanel.setPreferredSize(new Dimension(1200, 605));
		lowerPanel.setPreferredSize(new Dimension(1200, 195));

		mainPanel.add(upperPanel);
		mainPanel.add(lowerPanel);
		add(mainPanel);
		
		genStreetPixels();
		genSewerPixels();
		
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
	public void parseXMLData(String path){
		try{
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			parseStreets(doc);
			parseSewers(doc);
			parseHospitals(doc);
			
			
		} catch (Exception e) {
	    	JOptionPane.showMessageDialog(VirusSimulation.this, 
	    			"Incorrect file format", 
	    			"Error", 
	    			JOptionPane.ERROR_MESSAGE);
	    }
		
		
		
	}

	public void parseStreets(Document doc){
		
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
	
	public void parseSewers(Document doc){
		
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

				System.out.println("Sewer");
				System.out.println("Start x: " + eS.getElementsByTagName("x").item(0).getTextContent());
				System.out.println("Start y: " + eS.getElementsByTagName("y").item(0).getTextContent());
				System.out.println("End x: " + eE.getElementsByTagName("x").item(0).getTextContent());
				System.out.println("End y: " + eE.getElementsByTagName("y").item(0).getTextContent());
				System.out.println("\n\n");
				
				Sewer s = new Sewer(sX, sY, eX, eY);
				allSewers.add(s);
				
			}
			
		}
		
	}
	
	public void parseHospitals(Document doc){
		
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
		topPanel = new JPanel();
		bottomPanel = new JPanel();
		ratField = new JTextField(10);
		humansField = new JTextField(10);
		contagionField = new JTextField(10);
		// TODO add the numbers to the combobox 1-10
		
		String options[] = new String[10];
		for(int j=0; j< 10; j++){
			options[j] = String.valueOf(j+1);
		}
		virusStrengthCombo = new JComboBox(options);
		
		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));		
		ratLabel =  new JLabel("Number of Rats ");
		humanLabel = new JLabel("Number of Humans ");
		strengthLabel = new JLabel("Strength of Virus");
		contagionLabel = new JLabel("Contagion Level (0-1)");
		
		//TODO insert a question mark button so people can see how many to enter etc
		
		topPanel.add(ratLabel);
		topPanel.add(ratField);
		topPanel.add(humanLabel);
		topPanel.add(humansField);
		bottomPanel.add(strengthLabel);
		bottomPanel.add(virusStrengthCombo);
		bottomPanel.add(contagionLabel);
		bottomPanel.add(contagionField);
		
		lowerPanel.add(topPanel);
		lowerPanel.add(bottomPanel);
		
		
		return lowerPanel;
	}
	 
	/******** Accessor Methods to User Input Data ********/
	public double getContagionLevel(){		
		double contagion = Double.parseDouble(contagionField.getText());	
		return contagion;
		
	}
	
	public void getNumberofHumans(){
		totalHumans = Integer.parseInt(humansField.getText());
		//return humans;
	}
	
	public void getNumberofRats(){
		totalRats = Integer.parseInt(ratField.getText());
		//return rats;
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
				System.out.println(p.xLoc + ", " + p.yLoc + ", " + p.type);
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
			System.out.println("YO");
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
				System.out.println("YO2");
				find_neighbors((int)x,(int)y,p);
				//g.fillRect((int)x, (int)y, 1, 1);
			}
		}
	}

	public void genStreetPixels(){
		for(int i=0; i< allStreets.size(); i++){
			if(allStreets.get(i).getStartXLocation() == allStreets.get(i).getEndXLocation()){
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
			System.out.println("Pixel " + globalPixels.get(k).xLoc + ", " + globalPixels.get(k).yLoc + ": ");
			for(int l = 0; l < globalPixels.get(k).pixelNeighbors.size(); l++)
			{
				System.out.println(" - " + globalPixels.get(k).pixelNeighbors.get(l).xLoc + ", " + globalPixels.get(k).pixelNeighbors.get(l).yLoc);
			}
		}
		
	}
}



