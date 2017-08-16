package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import mil.army.usace.bctt.gravee.DoseResponse;
import mil.army.usace.bctt.gravee.MengerCurvature;
import mil.army.usace.bctt.gravee.Parser;
import mil.army.usace.bctt.gravee.Point;

public class TestDoseResponsePoints {
	
	@Test
	public void test() {
		Parser parser = new Parser(new File("src/test_data.txt"));
		ArrayList<DoseResponse> alDoseResponseData = parser.getDoseResponseData();
		DoseResponse doseResponse = alDoseResponseData.get(0);
		ArrayList<Point> alPoints = doseResponse.getDRPoints();
		assertEquals(0.1, alPoints.get(0).getX(), 0.001);
		assertEquals(0.001, alPoints.get(0).getY(), 0.001);
		assertEquals(500, alPoints.get(7).getX(), 0.001);
		assertEquals(100, alPoints.get(7).getY(), 0.001);
	}

}
