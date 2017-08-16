package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

import org.junit.Test;

import mil.army.usace.bctt.gravee.DoseResponse;
import mil.army.usace.bctt.gravee.MengerCurvature;
import mil.army.usace.bctt.gravee.Parser;


public class TestMaxCurvatureSearch {
	
	@Test
	public void test() {
		Parser parser = new Parser(new File("src/test_data.txt"));
		ArrayList<DoseResponse> alDoseResponseData = parser.getDoseResponseData();
		DoseResponse doseResponse = alDoseResponseData.get(0);
		MengerCurvature mc = new MengerCurvature(doseResponse.getDRPoints());
		assertEquals(50.0, mc.getPOD(), 0.001);
	}

}
