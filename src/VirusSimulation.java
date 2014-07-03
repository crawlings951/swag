import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.io.File;
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
	
	JTextField ratField;
	JTextField humansField;
	JTextField contagionField;
	JComboBox virusStrengthCombo;
	JPanel lowerPanel;
	JPanel mainPanel, upperPanel;
	JPanel topPanel, bottomPanel;
	JLabel ratLabel, humanLabel, strengthLabel, contagionLabel;

	VirusSimulation(){
		super("Virus Simulation");
		
		//Vector instantiations
		allStreets = new Vector<Street>();
		allSewers = new Vector<Sewer>();
		allHospitals = new Vector<Hospital>();
		
		//Parse the data
		parseXMLData("./test2.xml");
		
		//
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		// TODO fix the formatting and spacing of the lower panel
		upperPanel = new Panel();
		lowerPanel = newLowerPanel();
		upperPanel.setPreferredSize(new Dimension(1200, 605));
		lowerPanel.setPreferredSize(new Dimension(1200, 195));

		mainPanel.add(upperPanel);
		mainPanel.add(lowerPanel);
		add(mainPanel);
		
		
		setSize(1200, 800);
		setLocation(100, 0);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}
	
	public static void main(String args[]){
		
		VirusSimulation v = new VirusSimulation();
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
		contagionLabel = new JLabel("Contagion Level");
		
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
	
	public int getNumberofHumans(){
		int humans = Integer.parseInt(humansField.getText());
		return humans;
	}
	
	public int getNumberofRats(){
		int rats = Integer.parseInt(ratField.getText());
		return rats;
	}
	
	public int getVirusStrength(){
		int strength = virusStrengthCombo.getSelectedIndex() + 1;
		return strength;
	}
	
	/******** Draw Components Methods ********/
	public void drawStreets(Graphics g){
		
		//System.out.println("here");
		
		g.setColor(Color.blue);//Change color
		
		for(int i=0; i< allStreets.size(); i++){
			g.drawLine(allStreets.get(i).getStartXLocation(), allStreets.get(i).getStartYLocation(), 
					allStreets.get(i).getEndXLocation(), allStreets.get(i).getEndYLocation());
		}
		
		g.setColor(Color.BLACK); //reset color
		
	}

	public void drawSewers(Graphics g){

		
		g.setColor(Color.GREEN);//Change color
		
		for(int i=0; i< allSewers.size(); i++){
			g.drawLine(allSewers.get(i).getStartXLocation(), allSewers.get(i).getStartYLocation(),
					allSewers.get(i).getEndXLocation(), allSewers.get(i).getEndYLocation());
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
}

