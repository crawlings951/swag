import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
	
	VirusSimulation(){
		super("Virus Simulation");
		
		//Vector instantiations
		allStreets = new Vector<Street>();
		allSewers = new Vector<Sewer>();
		allHospitals = new Vector<Hospital>();
		
		add(new Panel());
		
		parseXMLData("./test1.xml");
		
		
		setSize(1200, 800);
		setLocation(100, 0);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String args[]){
		
		VirusSimulation v = new VirusSimulation();
	}
	
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
	
	public class Panel extends JPanel{
		
		Panel(){
			
		}
		
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			
			drawStreets(g);
			drawSewers(g);
			drawHospitals(g);
			//g.fillRect(100, 100, 5, 5);
			
		}
		
	}
	
	public void drawStreets(Graphics g){
		
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

