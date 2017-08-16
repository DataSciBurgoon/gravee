package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.junit.Test;

import application.Parser;
import mil.army.usace.bctt.gravee.DoseResponse;

public class TestParser {

	@Test
	public void test() throws IOException {
		Parser parser = new Parser(new File("src/test_data.txt"));
		ArrayList<DoseResponse> alDoseResponseData = parser.getDoseResponseData();
		DoseResponse doseResponse = alDoseResponseData.get(0);
		TreeMap<Double, Double> tmDoseResponse = doseResponse.getDoseData();
		assertEquals(0.001, tmDoseResponse.get(new Double(0.10)).doubleValue(), 0.001);
		assertEquals(0.002, tmDoseResponse.get(new Double(0.50)).doubleValue(), 0.001);
		assertEquals(100.0, tmDoseResponse.get(new Double(500.0)).doubleValue(), 0.001);
		assertEquals("blah x", doseResponse.getIdentifier());
	}

}
