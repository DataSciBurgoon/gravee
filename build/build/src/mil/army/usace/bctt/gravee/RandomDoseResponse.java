package mil.army.usace.bctt.gravee;

public class RandomDoseResponse {
	
	private double[] doses;
	private double[] responses;
	private int dosePosition = 0;
	private int responsePosition = 0;
	
	public RandomDoseResponse(int length){
		doses = new double[length];
		responses = new double[length];
	}
	
	public double[] getDoses(){
		return doses;
	}
	
	public double[] getResponses(){
		return responses;
	}
	
	public void addDose(double dose){
		doses[dosePosition] = dose;
		dosePosition++;
	}
	
	public void addResponse(double response){
		responses[responsePosition] = response;
		responsePosition++;
	}

}
