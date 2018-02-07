package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.junit.Test;

import mil.army.usace.bctt.gravee.DoseResponse;
import mil.army.usace.bctt.gravee.Interpolator;
import mil.army.usace.bctt.gravee.Parser;
import mil.army.usace.bctt.gravee.Spmr;

public class TestSplineMetaRegression {

	@Test
	public void test() {
//		Parser parser = new Parser(new File("src/test_data2.txt"));
//		HashMap<String, ArrayList> hmDoseResponseData = parser.getDoseResponseData();
//		ArrayList<DoseResponse> alDoseResponseData = hmDoseResponseData.get("blah x");
//		DoseResponse doseResponse = alDoseResponseData.get(0);
//		Spmr sprm = new Spmr(alDoseResponseData, 10);
//		double[] pods = sprm.getPods();
//		assertEquals(50.0, pods[0], 0.1);
		
//		Parser parser = new Parser(new File("src/test_data3.txt"));
//		HashMap<String, ArrayList> hmDoseResponseData = parser.getDoseResponseData();
//		ArrayList<DoseResponse> alDoseResponseData = hmDoseResponseData.get("x");
//		DoseResponse doseResponse = alDoseResponseData.get(0);
//		Spmr sprm = new Spmr(alDoseResponseData, 1000);
//		double[] pods = sprm.getPods();
//		Percentile percentile = new Percentile();
//		percentile.withEstimationType(Percentile.EstimationType.R_7);
//		percentile.setData(pods);
//		
//		//System.out.println("POD5 = " + percentile.evaluate(5));
//		assertEquals(0.6169, percentile.evaluate(5), 0.001);
		
		Parser parser = new Parser(new File("src/tnt_dr_table.txt"));
		HashMap<String, ArrayList> hmDoseResponseData = parser.getDoseResponseData();
		ArrayList<DoseResponse> alDoseResponseData = hmDoseResponseData.get("ACSM2B");
		DoseResponse doseResponse = alDoseResponseData.get(0);
		Spmr sprm = new Spmr(alDoseResponseData, 1000);
		double[] pods = sprm.getPods();
		Percentile percentile = new Percentile();
		percentile.withEstimationType(Percentile.EstimationType.R_7);
		percentile.setData(pods);
		
		System.out.println("POD5 = " + percentile.evaluate(5));
		System.out.println("POD50 = " + percentile.evaluate(50));
		System.out.println("POD95 = " + percentile.evaluate(95));
		
		try{
			
			FileWriter fw = new FileWriter("src/tnt_dr_pod_predictions.txt");
			int j = 0;
			String output = "";
			for(double pod : pods){
				if(j < 999){
					 output = output + pod + "\t";
				}
				else{
					output = output + pod + "\n";
				}
				
			}
			fw.write(output);
			fw.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		assertEquals(0.60, percentile.evaluate(5), 0.001);
		
	}

}
