package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.TreeMap;

import org.junit.Test;

import application.MengerCurvature;
import application.Parser;
import mil.army.usace.bctt.gravee.DoseResponse;


public class TestMaxCurvatureSearch {
	
	@Test
	public void test() {
		Parser parser = new Parser("test_data.txt");
		ArrayList<DoseResponse> alDoseResponseData = parser.getDoseResponseData();
		DoseResponse doseResponse = alDoseResponseData.get(0);
		MengerCurvature mc = new MengerCurvature(doseResponse.getDRPoints());
		assertEquals(50.0, mc.getPOD(), 0.001);
	}

}
