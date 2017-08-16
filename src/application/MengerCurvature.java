package application;

import java.util.ArrayList;

public class MengerCurvature {
	
	private ArrayList<Point> alResponseValues;
	private double pod = 0.0;
	
	public MengerCurvature(ArrayList<Point> alResponseValues){
		this.alResponseValues = alResponseValues;
		this.alResponseValues.trimToSize();
		calculate();
	}
	
	private void calculate(){
		double maxCurvature = 0.0;
		
		for(int i = 1; i < alResponseValues.size()-2; i++){
			double curvature = curvature(alResponseValues.get(i-1), alResponseValues.get(i), alResponseValues.get(i+1));
			if(curvature > maxCurvature){
				maxCurvature = curvature;
				pod = alResponseValues.get(i).getX();
			}
		}
	}
	
	private double curvature(Point a, Point b, Point c){
		double area = 0.0;
		double length_edge_ab = Math.sqrt(Math.pow((b.getX() - a.getX()),2) + Math.pow((b.getY() - a.getY()),2));
		double length_edge_bc = Math.sqrt(Math.pow((c.getX() - b.getX()),2) + Math.pow((c.getY() - b.getY()),2));
		double length_edge_ac = Math.sqrt(Math.pow((a.getX() - c.getX()),2) + Math.pow((a.getY() - c.getY()),2));
		double half_perimeter = (length_edge_ab + length_edge_bc + length_edge_ac)/2;
		area = Math.sqrt(half_perimeter * (half_perimeter - length_edge_ab) * (half_perimeter - length_edge_bc) * 
				(half_perimeter - length_edge_ac));
		
		double curvature = (4 * area)/(Math.abs(length_edge_ab - length_edge_bc) * 
				Math.abs(length_edge_bc - length_edge_ac) * Math.abs(length_edge_ac - length_edge_ab));
		
		return curvature;
	}
	
	public double getPOD(){
		return this.pod;
	}
	
	
}
