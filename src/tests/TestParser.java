package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.junit.Test;

import mil.army.usace.bctt.gravee.DoseResponse;
import mil.army.usace.bctt.gravee.Parser;
import mil.army.usace.bctt.gravee.Point;

public class TestParser {

	@Test
	public void test() throws IOException {
		Parser parser = new Parser(new File("src/test_data.txt"));
		HashMap<String, ArrayList> hmDoseResponseData = parser.getDoseResponseData();
		ArrayList<DoseResponse> alDoseResponseData = hmDoseResponseData.get("blah x");
		DoseResponse doseResponse = alDoseResponseData.get(0);
		TreeMap<Double, Double> tmDoseResponse = doseResponse.getDoseData();
		double[] doses = doseResponse.getDoses();
		double[] responses = doseResponse.getResponses();
		assertEquals(0.001, tmDoseResponse.get(new Double(0.10)).doubleValue(), 0.001);
		assertEquals(0.002, tmDoseResponse.get(new Double(0.50)).doubleValue(), 0.001);
		assertEquals(100.0, tmDoseResponse.get(new Double(500.0)).doubleValue(), 0.001);
		assertEquals("blah x", doseResponse.getIdentifier());
		assertEquals(8, doses.length);
		assertEquals(8, responses.length);
		assertEquals(0.10, doses[0], 0.001);
		assertEquals(100.0, doses[6], 0.001);
		assertEquals(0.001, responses[0], 0.001);
		assertEquals(10.0, responses[6], 0.001);
	}

}
