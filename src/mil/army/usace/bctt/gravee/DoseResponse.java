package mil.army.usace.bctt.gravee;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

//This holds the dose-response data and the POD

public class DoseResponse {
	
	private TreeMap<Double, Double> tmDoseData;
	private String identifier;
	private ArrayList<Point> alPoints = new ArrayList<Point>();
	private double[] aDose;
	private double[] aResponse;
	
	public DoseResponse(String identifier, TreeMap<Double, Double> tmDoseData){
		this.identifier = identifier;
		this.tmDoseData = tmDoseData;
		transformPoints();
	}
	
	private void transformPoints(){
		int i = 0;
		aDose = new double[tmDoseData.size()];
		aResponse = new double[tmDoseData.size()];
		for(Map.Entry<Double, Double> entry : tmDoseData.entrySet()){
			double dose = entry.getKey().doubleValue();
			double response = entry.getValue().doubleValue();
			Point drPoint = new Point(dose, response);
			alPoints.add(drPoint);
			aDose[i] = dose;
			aResponse[i] = response;
			i++;
		}
		
		
	}
	
	public double[] getDoses(){
		return this.aDose;
	}
	
	public double[] getResponses(){
		return this.aResponse;
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
