package mil.army.usace.bctt.gravee;

import java.util.ArrayList;
import java.util.Random;

//Spline-based meta-regression

public class Spmr {
	
	private ArrayList<DoseResponse> alDoseResponses;
	private Random random = new Random();
	private int iterations = 100;
	double[] pods;
	
	public Spmr(ArrayList<DoseResponse> alDoseResponses, int iterations){
		this.alDoseResponses = alDoseResponses;
		this.iterations = iterations;
		this.pods = new double[iterations];
		podCalculation();
	}
	
	private void podCalculation(){
		for(int i = 0; i < pods.length; i++){
			RandomDoseResponse randomDoseResponse = buildRandomDRModel();
			Interpolator interpolator = new Interpolator(randomDoseResponse.getDoses(), randomDoseResponse.getResponses(), 50);
//			Interpolator interpolator = new Interpolator(randomDoseResponse.getDoses(), randomDoseResponse.getResponses(), 3000);
			double[] responses = interpolator.getInterpolatedResponses();
			double[] doses = interpolator.getInterpolatedDoses();
			MengerCurvature mc = new MengerCurvature(transformToPoints(doses, responses));
			//MengerCurvature mc = new MengerCurvature(transformToPoints(randomDoseResponse.getDoses(), randomDoseResponse.getResponses()));
			pods[i] = mc.getPOD();
		}
		
	}
	
	private ArrayList<Point> transformToPoints(double[] doses, double[] responses){
		ArrayList<Point> alPoints = new ArrayList<Point>(doses.length);
		for(int i = 0; i < doses.length; i++){
			Point point = new Point(doses[i], responses[i]);
			alPoints.add(point);
		}
		return alPoints;
	}
	
	private RandomDoseResponse buildRandomDRModel(){
		DoseResponse firstDR = alDoseResponses.get(0);
		int drLength = firstDR.getDoses().length;
		RandomDoseResponse randomDoseResponse = new RandomDoseResponse(drLength);
		for(int i = 0; i < drLength; i++){
			int randomIndex = random.nextInt(alDoseResponses.size());
			double dose = alDoseResponses.get(randomIndex).getDoses()[i];
			double response = alDoseResponses.get(randomIndex).getResponses()[i];
			randomDoseResponse.addDose(dose);
			randomDoseResponse.addResponse(response);
			
		}
		return randomDoseResponse;
		
	}
	
	public double[] getPods(){
		return pods;
	}

}
