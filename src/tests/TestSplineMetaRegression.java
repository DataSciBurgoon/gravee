package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import mil.army.usace.bctt.gravee.DoseResponse;
import mil.army.usace.bctt.gravee.Interpolator;
import mil.army.usace.bctt.gravee.Parser;

public class TestSplineMetaRegression {

	@Test
	public void test() {
		Parser parser = new Parser(new File("src/test_data.txt"));
		HashMap<String, ArrayList> hmDoseResponseData = parser.getDoseResponseData();
		ArrayList<DoseResponse> alDoseResponseData = hmDoseResponseData.get("blah x");
		DoseResponse doseResponse = alDoseResponseData.get(0);
		Interpolator loess = new Interpolator(doseResponse.getDoses(), doseResponse.getResponses(), 5000);
		double[] responses = loess.getLoessResponses();
		double[] doses = loess.getLoessDoses();
		for(int i = 0; i < responses.length; i++){
			if(i % 5 == 0){
				System.out.println("dose: " + doses[i] + "\tresponse: " + responses[i]);
				//System.out.println("dose: " + doseResponse.getDoses()[i] + "\tresponse: " + responses[i]);
			}
		}
		assertEquals(0.001, responses[0], 0.001);
		assertEquals(0.002, responses[5], 0.001);
		assertEquals(100.0, responses[responses.length-1], 0.001);
		
		//TEST THE POD ESTIMATION HERE
	}

}
