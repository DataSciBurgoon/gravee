package mil.army.usace.bctt.gravee;

import java.util.ArrayList;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class ProbPod {
	
	private double[] pods;
	
	private double medianPOD = 0.0;
	private double upper95POD = 0.0;
	private double upper99POD = 0.0;
	private double lower95POD = 0.0;
	private double lower99POD = 0.0;
	
	
	public ProbPod(double[] pods){
		this.pods = pods;
		calcProbPods();
	}
	
	private void calcProbPods(){
		DescriptiveStatistics podStats = new DescriptiveStatistics(pods);
		medianPOD = podStats.getPercentile(50);
		upper95POD = podStats.getPercentile(95);
		upper99POD = podStats.getPercentile(99);
		lower95POD = podStats.getPercentile(5);
		lower99POD = podStats.getPercentile(1);
	}
	
	public double getMedianPOD(){
		return this.medianPOD;
	}
	
	public double getUpper95POD(){
		return this.upper95POD;
	}
	
	public double getUpper99POD(){
		return this.upper99POD;
	}
	
	public double getLower95POD(){
		return this.lower95POD;
	}
	
	public double getLower99POD(){
		return this.lower99POD;
	}
	
	

}
