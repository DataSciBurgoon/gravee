package mil.army.usace.bctt.gravee;

import org.apache.commons.math3.analysis.interpolation.AkimaSplineInterpolator;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Set;

import org.apache.commons.math3.analysis.interpolation.LoessInterpolator;
//import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.OutOfRangeException;


public class Interpolator {
	
	private int interpolationSize = 0;
	private double[] doses;
	private double[] logDoses;
	private double[] responses;
	private double[] modeledDoses = new double[interpolationSize];
	private double[] modeledResponses = new double[interpolationSize];
	private boolean flag = false;
	

	public Interpolator(double[] doses, double[] responses, int interpolationSize){
		this.doses = doses;
		this.responses = responses;
		this.interpolationSize = interpolationSize;
		this.logDoses = new double[doses.length];
		//takeLogDoses();
		calcLoess();
	}
	
	private void takeLogDoses(){
		for(int i = 0; i < doses.length; i++){
			logDoses[i] = Math.log10(doses[i]);
		}
	}
	
	private double getMax(double[] inputArray){ 
	    double maxValue = inputArray[0]; 
	    for(int i=1;i < inputArray.length;i++){ 
	      if(inputArray[i] > maxValue){ 
	         maxValue = inputArray[i]; 
	      } 
	    } 
	    return maxValue; 
	  }
	 
	  // Method for getting the minimum value
	  private double getMin(double[] inputArray){ 
	    double minValue = inputArray[0]; 
	    for(int i=1;i<inputArray.length;i++){ 
	      if(inputArray[i] < minValue){ 
	        minValue = inputArray[i]; 
	      } 
	    } 
	    return minValue; 
	  } 
	
	private void calcLoess(){
		//AkimaSplineInterpolator asi = new AkimaSplineInterpolator();
		LoessInterpolator loess = new LoessInterpolator(0.75, 4);
		try{
			//PolynomialSplineFunction psf = asi.interpolate(doses, responses);
			PolynomialSplineFunction psf = loess.interpolate(doses, responses);
			modeledDoses = interpolate(getMin(doses), getMax(doses), interpolationSize);
			/*PolynomialSplineFunction psf = asi.interpolate(logDoses, responses);
			modeledDoses = interpolate(getMin(logDoses), getMax(logDoses), interpolationSize);*/
			double maxResponse = maxResponse();
			modeledResponses = predictVals(psf, modeledDoses, maxResponse);
		}
		catch(NonMonotonicSequenceException e){
			e.printStackTrace();
		}
		catch(NumberIsTooSmallException e){
			System.out.println("There was a problem -- too few datapoints for a chemical-assay combination");
			flag = true;
		}
		catch(NoDataException e){
			System.out.println("There was a problem -- a case of no data for a chemical-assay combination");
			flag = true;
		}
	}
	
	public boolean getFlag(){
		return this.flag;
	}
	
	private double maxResponse(){
		double maxResponse = 0.0;
		for(double response : responses){
			if(maxResponse < response){
				maxResponse = response;
			}
		}
		return maxResponse;
	}
	
	private double[] predictVals(PolynomialSplineFunction psf, double[] newX, double maxResponse){
		double[] newVals = new double[newX.length];
		int i = 0;
		for(double x : newX){
			try{
				if(psf.value(x) > maxResponse){
					newVals[i] = maxResponse;
				}
				else{
					newVals[i] = psf.value(x);
				}
				
			}
			catch(OutOfRangeException e){
				newVals[i] = psf.getKnots()[psf.getKnots().length - 1];
			}
			i++;
		}
		return newVals;
	}
	
	private double[] interpolate(double start, double end, int count) {
	    if (count < 2) {
	        throw new IllegalArgumentException("interpolate: illegal count!");
	    }
	    double[] array = new double[count + 1];
	    for (int i = 0; i <= count; ++ i) {
	        array[i] = start + i * (end - start) / count;
	    }
	    return array;
	}
	
	public double[] getInterpolatedDoses(){
		return this.modeledDoses;
	}
	
	public double[] getInterpolatedResponses(){
		return this.modeledResponses;
	}

}
