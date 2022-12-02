package preprocessor;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.angle.AngleEquivalenceClasses;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import input.components.FigureNode;
import input.InputFacade;

class AngleIdentifierTest
{
	protected PointDatabase _points;
	protected Preprocessor _pp;
	protected Map<Segment, Segment> _segments;
	
	protected void init(String filename)
	{
		FigureNode fig = InputFacade.extractFigure(filename);

		Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);

		_points = pair.getKey();

		_pp = new Preprocessor(_points, pair.getValue());

		_pp.analyze();

		_segments = _pp.getAllSegments();
	}
	
	//      A                                 
	//     / \                                
	//    B___C                               
	//   / \ / \                              
	//  /   X   \  X is not a specified point (it is implied) 
	// D_________E
	//
	// This figure contains 44 angles
	//
	
	@Test
	void test_crossing_symmetric_triangle()
	{
		init("jsonfiles/crossing_symmetric_triangle.json");

		AngleIdentifier angleIdentifier = new AngleIdentifier(_segments);

		AngleEquivalenceClasses computedAngles;
		try {
			computedAngles = angleIdentifier.getAngles();
		

		// The number of classes should equate to the number of 'minimal' angles
		assertEquals("Number of Angle Equivalence classes", 25, computedAngles.numClasses());
		
		//
		// ALL original segments: 8 in this figure.
		//
		Segment ab = new Segment(_points.getPoint("A"), _points.getPoint("B"));
		Segment ac = new Segment(_points.getPoint("A"), _points.getPoint("C"));
		Segment bc = new Segment(_points.getPoint("B"), _points.getPoint("C"));

		Segment bd = new Segment(_points.getPoint("B"), _points.getPoint("D"));
		Segment ce = new Segment(_points.getPoint("C"), _points.getPoint("E"));
		Segment de = new Segment(_points.getPoint("D"), _points.getPoint("E"));

		Segment be = new Segment(_points.getPoint("B"), _points.getPoint("E"));
		Segment cd = new Segment(_points.getPoint("C"), _points.getPoint("D"));

		//
		// Implied minimal segments: 4 in this figure.
		//
		Point a_star = _points.getPoint(3,3);

		Segment a_star_b = new Segment(a_star, _points.getPoint("B"));
		Segment a_star_c = new Segment(a_star, _points.getPoint("C"));
		Segment a_star_d = new Segment(a_star, _points.getPoint("D"));
		Segment a_star_e = new Segment(a_star, _points.getPoint("E"));

		//
		// Non-minimal, computed segments: 2 in this figure.
		//
		Segment ad = new Segment(_points.getPoint("A"), _points.getPoint("D"));
		Segment ae = new Segment(_points.getPoint("A"), _points.getPoint("E"));

		//
		// Angles we expect to find
		//
		List<Angle> expectedAngles = new ArrayList<Angle>();
		try {
			//
			//
			// Angles broken down by equivalence class
			//
			//

			// Straight angles
			//
			expectedAngles.add(new Angle(a_star_b, a_star_e));
			
			expectedAngles.add(new Angle(ac, ce));
			
			expectedAngles.add(new Angle(a_star_d, a_star_c));
			
			expectedAngles.add(new Angle(ab, bd));
			
			// right angles
			//
			expectedAngles.add(new Angle(a_star_b, a_star_d));
			
			expectedAngles.add(new Angle(a_star_b, a_star_c));
			
			expectedAngles.add(new Angle(a_star_d, a_star_e));

			expectedAngles.add(new Angle(a_star_c, a_star_e));
			
			// 
			//
			expectedAngles.add(new Angle(a_star_b, ab));
			expectedAngles.add(new Angle(be, ab));

			expectedAngles.add(new Angle(a_star_b, bc));
			expectedAngles.add(new Angle(be, bc));

			expectedAngles.add(new Angle(a_star_b, bd));
			expectedAngles.add(new Angle(be, bd));
			
			expectedAngles.add(new Angle(ac, a_star_c));
			expectedAngles.add(new Angle(ac, cd));

			expectedAngles.add(new Angle(bc, a_star_c));
			expectedAngles.add(new Angle(cd, bd));
			
			expectedAngles.add(new Angle(a_star_c, ce));
			expectedAngles.add(new Angle(cd, ce));
			
			expectedAngles.add(new Angle(a_star_d, de));
			expectedAngles.add(new Angle(cd, de));
			
			expectedAngles.add(new Angle(bd, de));
			expectedAngles.add(new Angle(ad, de));
			
			expectedAngles.add(new Angle(a_star_e, de));
			expectedAngles.add(new Angle(be, de));

			expectedAngles.add(new Angle(ce, de));
			expectedAngles.add(new Angle(ae, de));
			
			// Larger equivalence classes
			//
			expectedAngles.add(new Angle(ac, ab));
			expectedAngles.add(new Angle(ab, ae));
			expectedAngles.add(new Angle(ad, ae));
			expectedAngles.add(new Angle(ac, ad));

			expectedAngles.add(new Angle(a_star_d, bd));
			expectedAngles.add(new Angle(cd, bd));
			expectedAngles.add(new Angle(ad, a_star_d));
			expectedAngles.add(new Angle(cd, ad));

			expectedAngles.add(new Angle(a_star_e, ce));
			expectedAngles.add(new Angle(be, ae));
			expectedAngles.add(new Angle(be, ce));
			expectedAngles.add(new Angle(a_star_e, ce));

			
			// More singletons
			//
			expectedAngles.add(new Angle(ac, bc));

			expectedAngles.add(new Angle(ab, bc));
			
			expectedAngles.add(new Angle(bc, bd));

			expectedAngles.add(new Angle(bc, ce));			
		}
		catch (FactException te) { System.err.println("Invalid Angles in Angle test."); }

		assertEquals(expectedAngles.size(), computedAngles.size());
		
		//
		// Equality
		//
		for (Angle expected : expectedAngles)
		{
			assertTrue(computedAngles.contains(expected));
		}
		} catch (FactException e) {

		}
	}
	
	
	
	
	
	
	@Test
	void test_collinear_line_segments()
	{
		init("jsonfiles/collinear_line_segments.json");

		AngleIdentifier angleIdentifier = new AngleIdentifier(_segments);

		AngleEquivalenceClasses computedAngles;
		try {
			computedAngles = angleIdentifier.getAngles();
			
			

		// The number of classes should equate to the number of 'minimal' angles
		assertEquals("Number of Angle Equivalence classes", 4, computedAngles.numClasses());
		
		//
		// ALL original segments: 8 in this figure.
		//
		Segment ab = new Segment(_points.getPoint("A"), _points.getPoint("B"));
		Segment ac = new Segment(_points.getPoint("A"), _points.getPoint("C"));
		Segment ad = new Segment(_points.getPoint("A"), _points.getPoint("D"));
		Segment ae = new Segment(_points.getPoint("A"), _points.getPoint("E"));
		Segment af = new Segment(_points.getPoint("A"), _points.getPoint("F"));
		
		Segment bc = new Segment(_points.getPoint("B"), _points.getPoint("C"));
		Segment bd = new Segment(_points.getPoint("B"), _points.getPoint("D"));
		Segment be = new Segment(_points.getPoint("B"), _points.getPoint("E"));
		Segment bf = new Segment(_points.getPoint("B"), _points.getPoint("F"));
		
		Segment cd = new Segment(_points.getPoint("C"), _points.getPoint("D"));
		Segment ce = new Segment(_points.getPoint("C"), _points.getPoint("E"));
		Segment cf = new Segment(_points.getPoint("C"), _points.getPoint("F"));
		
		Segment de = new Segment(_points.getPoint("D"), _points.getPoint("E"));
		Segment df = new Segment(_points.getPoint("D"), _points.getPoint("F"));
		
		Segment ef = new Segment(_points.getPoint("E"), _points.getPoint("F"));

	

		//
		// Angles we expect to find
		//
		List<Angle> expectedAngles = new ArrayList<Angle>();
		try {
			//
			//
			// Angles broken down by equivalence class
			//
			//

			// Straight angles
			//
			expectedAngles.add(new Angle(ab , bc));
			expectedAngles.add(new Angle(ab , bd));
			expectedAngles.add(new Angle(ab , be));
			expectedAngles.add(new Angle(ab , bf));
			
			expectedAngles.add(new Angle(bc, cd));
			expectedAngles.add(new Angle(bc, ce));
			expectedAngles.add(new Angle(bc, cf));
			
			expectedAngles.add(new Angle(cd, de));
			expectedAngles.add(new Angle(cd, df));
			
			expectedAngles.add(new Angle(de, ef));
			expectedAngles.add(new Angle(de, bd));
			expectedAngles.add(new Angle(de, ad));
			
			expectedAngles.add(new Angle(ef, ce));
			expectedAngles.add(new Angle(ef, be));
			expectedAngles.add(new Angle(ef, ae));
			
			expectedAngles.add(new Angle(ac, ce));
			expectedAngles.add(new Angle(ac, cf));
			
			expectedAngles.add(new Angle(bd, df));
			
			
			
				
		}
		catch (FactException te) { System.err.println("Invalid Angles in Angle test."); }
		

		assertEquals(expectedAngles.size(), computedAngles.size());
		
		//
		// Equality
		//
		for (Angle expected : expectedAngles)
		{
			assertTrue(computedAngles.contains(expected));
		}
		} catch (FactException e) {

		}
	}
	
	
	@Test
	void test_crossed_lines()
	{
		init("jsonfiles/crossed_lines.json");

		AngleIdentifier angleIdentifier = new AngleIdentifier(_segments);

		AngleEquivalenceClasses computedAngles;
		try {
			computedAngles = angleIdentifier.getAngles();
			
		
		

		// The number of classes should equate to the number of 'minimal' angles
		assertEquals("Number of Angle Equivalence classes", 6, computedAngles.numClasses());
		
		
		//
		// Implied minimal segments: 4 in this figure.
		//
		Point a_star = _points.getPoint(3,4);

		Segment a_star_a = new Segment(a_star, _points.getPoint("A"));
		Segment a_star_b = new Segment(a_star, _points.getPoint("B"));
		Segment a_star_c = new Segment(a_star, _points.getPoint("C"));
		Segment a_star_d = new Segment(a_star, _points.getPoint("D"));

		
		//
		// Angles we expect to find
		//
		List<Angle> expectedAngles = new ArrayList<Angle>();
		try {
			//
			//
			// Angles broken down by equivalence class
			//
	
			expectedAngles.add(new Angle(a_star_a , a_star_b ));
			expectedAngles.add(new Angle(a_star_a , a_star_c));
			expectedAngles.add(new Angle(a_star_a , a_star_d));
			
			
			expectedAngles.add(new Angle(a_star_b, a_star_c));
			expectedAngles.add(new Angle(a_star_b, a_star_d));
			
			expectedAngles.add(new Angle(a_star_c, a_star_d));
	
				
		}
		catch (FactException te) { System.err.println("Invalid Angles in Angle test."); }
		

		assertEquals(expectedAngles.size(), computedAngles.size());
		
		//
		// Equality
		//
		for (Angle expected : expectedAngles)
		{
			assertTrue(computedAngles.contains(expected));
		}
		} catch (FactException e) {

		}
	}
	
	
	
	
	
	@Test
	void test_crossed_square()
	{
		init("jsonfiles/Crossed_Square.json");

		AngleIdentifier angleIdentifier = new AngleIdentifier(_segments);

		AngleEquivalenceClasses computedAngles;
		try {
			computedAngles = angleIdentifier.getAngles();
			
	
		

		// The number of classes should equate to the number of 'minimal' angles
		assertEquals("Number of Angle Equivalence classes", 25, computedAngles.numClasses());
		
		//
		// ALL original segments: 8 in this figure.
		//
		Segment ab = new Segment(_points.getPoint("A"), _points.getPoint("B"));
		Segment ac = new Segment(_points.getPoint("A"), _points.getPoint("C"));
		Segment ad = new Segment(_points.getPoint("A"), _points.getPoint("D"));
		Segment bc = new Segment(_points.getPoint("B"), _points.getPoint("C"));
		Segment bd = new Segment(_points.getPoint("B"), _points.getPoint("D"));
		Segment cd = new Segment(_points.getPoint("C"), _points.getPoint("D"));

	
		//
		// Implied minimal segments: 4 in this figure.
		//
		Point a_star = _points.getPoint(3,3);

		Segment a_star_a = new Segment(a_star, _points.getPoint("A"));
		Segment a_star_b = new Segment(a_star, _points.getPoint("B"));
		Segment a_star_c = new Segment(a_star, _points.getPoint("C"));
		Segment a_star_d = new Segment(a_star, _points.getPoint("D"));

		
		
		//
		// Angles we expect to find
		//
		List<Angle> expectedAngles = new ArrayList<Angle>();
		try {
			//
			//
			// Angles broken down by equivalence class
			//
			expectedAngles.add(new Angle(ab , ac));
			expectedAngles.add(new Angle(ab , bd));
			expectedAngles.add(new Angle(ab , a_star_a));
			expectedAngles.add(new Angle(ab , a_star_b));
			expectedAngles.add(new Angle(ab , ad));
			expectedAngles.add(new Angle(ab , bc));
			
			expectedAngles.add(new Angle(ac , cd));
			expectedAngles.add(new Angle(ac , a_star_a));
			expectedAngles.add(new Angle(ac , a_star_c));
			expectedAngles.add(new Angle(ac , ad));
			expectedAngles.add(new Angle(ac , bc));
			
			
			expectedAngles.add(new Angle(bd , cd));
			expectedAngles.add(new Angle(bd , a_star_b));
			expectedAngles.add(new Angle(bd , a_star_d));
			expectedAngles.add(new Angle(bd , ad));
			expectedAngles.add(new Angle(bd , bc));
			
			expectedAngles.add(new Angle(cd , a_star_c));
			expectedAngles.add(new Angle(cd , a_star_d));
			expectedAngles.add(new Angle(cd , bc));
			expectedAngles.add(new Angle(cd , ad));
			
	
			expectedAngles.add(new Angle(a_star_a , a_star_b ));
			expectedAngles.add(new Angle(a_star_a , a_star_c));
			expectedAngles.add(new Angle(a_star_a , a_star_d));
			
			
			expectedAngles.add(new Angle(a_star_b, a_star_c));
			expectedAngles.add(new Angle(a_star_b, a_star_d));
			
			expectedAngles.add(new Angle(a_star_c, a_star_d));
	
				
		}
		catch (FactException te) { System.err.println("Invalid Angles in Angle test."); }
		

		assertEquals(expectedAngles.size(), computedAngles.size());
		
		//
		// Equality
		//
		for (Angle expected : expectedAngles)
		{
			assertTrue(computedAngles.contains(expected));
		}
		} catch (FactException e) {

		}
	}

	
	
	@Test
	void test_snake()
	{
		init("jsonfiles/snake.json");

		AngleIdentifier angleIdentifier = new AngleIdentifier(_segments);

		AngleEquivalenceClasses computedAngles;
		try {
			computedAngles = angleIdentifier.getAngles();
		

		// The number of classes should equate to the number of 'minimal' angles
		assertEquals("Number of Angle Equivalence classes", 17, computedAngles.numClasses());
		
		//
		// ALL original segments: 8 in this figure.
		//
		
		Segment ab = new Segment(_points.getPoint("A"), _points.getPoint("B"));
		Segment ac = new Segment(_points.getPoint("A"), _points.getPoint("C"));
		Segment bc = new Segment(_points.getPoint("B"), _points.getPoint("C"));
		Segment cd = new Segment(_points.getPoint("C"), _points.getPoint("D"));
		Segment ce = new Segment(_points.getPoint("C"), _points.getPoint("E"));

		Segment de = new Segment(_points.getPoint("D"), _points.getPoint("E"));
		Segment eg = new Segment(_points.getPoint("E"), _points.getPoint("G"));
		Segment fg = new Segment(_points.getPoint("F"), _points.getPoint("G"));
		Segment ef = new Segment(_points.getPoint("E"), _points.getPoint("F"));
		Segment ad = new Segment(_points.getPoint("A"), _points.getPoint("D"));
		
		
		

		//
		// Angles we expect to find
		//
		List<Angle> expectedAngles = new ArrayList<Angle>();
		try {
			//
			//
			// Angles broken down by equivalence class
			//
			//

			// Straight angles
			//
			
			
			expectedAngles.add(new Angle(ab , bc));
			expectedAngles.add(new Angle(ab , ac));
			expectedAngles.add(new Angle(ab , ad));
			expectedAngles.add(new Angle(bc , ac));
			expectedAngles.add(new Angle(bc , cd));
			expectedAngles.add(new Angle(bc , ce));
			expectedAngles.add(new Angle(ac , cd));
			expectedAngles.add(new Angle(ac , ce));
			expectedAngles.add(new Angle(cd , ce));
			expectedAngles.add(new Angle(cd , de));
			expectedAngles.add(new Angle(ce , de));
			expectedAngles.add(new Angle(ce , ef));
			expectedAngles.add(new Angle(de, eg));
			expectedAngles.add(new Angle(de , ef));
			expectedAngles.add(new Angle(de , ad));
			expectedAngles.add(new Angle(eg , fg));
			expectedAngles.add(new Angle(eg , ef));
			expectedAngles.add(new Angle(fg , ef));

			
			
		}
		catch (FactException te) { System.err.println("Invalid Angles in Angle test."); }

		assertEquals(expectedAngles.size(), computedAngles.size());
		
		//
		// Equality
		//
		for (Angle expected : expectedAngles)
		{
			assertTrue(computedAngles.contains(expected));
		}
		} catch (FactException e) {

		}
	}
	
	
	
}
