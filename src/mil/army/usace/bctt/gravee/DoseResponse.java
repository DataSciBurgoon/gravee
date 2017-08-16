package mil.army.usace.bctt.gravee;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import application.Point;

//This holds the dose-response data and the POD

public class DoseResponse {
	
	private TreeMap<Double, Double> tmDoseData;
	private String identifier;
	private ArrayList<Point> alPoints = new ArrayList<Point>();
	
	public DoseResponse(String identifier, TreeMap<Double, Double> tmDoseData){
		this.identifier = identifier;
		this.tmDoseData = tmDoseData;
		transformPoints();
	}
	
	private void transformPoints(){
		for(Map.Entry<Double, Double> entry : tmDoseData.entrySet()){
			double dose = entry.getKey().doubleValue();
			double response = entry.getKey().doubleValue();
			Point drPoint = new Point(dose, response);
			alPoints.add(drPoint);
		}
		
	}
	
	public TreeMap<Double, Double> getDoseData(){
		return this.tmDoseData;
	}
	
	public String getIdentifier(){
		return this.identifier;
	}
	
	public ArrayList<Point> getDRPoints(){
		return this.alPoints;
	}

}
