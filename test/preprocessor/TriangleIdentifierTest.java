package preprocessor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.Triangle;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import input.components.FigureNode;
import preprocessor.delegates.ImplicitPointPreprocessor;
import input.InputFacade;

class TriangleIdentifierTest
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
	//🫠 🫥
	//      A                                 
	//     / \                                
	//    B___C                               
	//   / \ / \                              
	//  /   X   \  X is not a specified point (it is implied) 
	// D_________E
	//
	// This figure contains 12 triangles
	//
	@Test
	void test_crossing_symmetric_triangle()
	{
		init("jsonfiles/crossing_symmetric_triangle.json");

		TriangleIdentifier triIdentifier = new TriangleIdentifier(_segments);

		Set<Triangle> computedTriangles = triIdentifier.getTriangles();

		System.out.println(computedTriangles);

		assertEquals(12, computedTriangles.size());

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
		// Triangles we expect to find
		//
		List<Triangle> expectedTriangles = new ArrayList<Triangle>();
		try {
			expectedTriangles.add(new Triangle(Arrays.asList(ab, bc, ac)));
			expectedTriangles.add(new Triangle(Arrays.asList(bd, a_star_d, a_star_b)));
			expectedTriangles.add(new Triangle(Arrays.asList(bc, a_star_b, a_star_c)));
			expectedTriangles.add(new Triangle(Arrays.asList(ce, a_star_c, a_star_e)));
			expectedTriangles.add(new Triangle(Arrays.asList(de, a_star_d, a_star_e)));

			expectedTriangles.add(new Triangle(Arrays.asList(bd, cd, bc)));
			expectedTriangles.add(new Triangle(Arrays.asList(ce, be, bc)));

			expectedTriangles.add(new Triangle(Arrays.asList(bd, be, de)));
			expectedTriangles.add(new Triangle(Arrays.asList(ce, cd, de)));

			expectedTriangles.add(new Triangle(Arrays.asList(ab, be, ae)));
			expectedTriangles.add(new Triangle(Arrays.asList(ac, cd, ad)));

			expectedTriangles.add(new Triangle(Arrays.asList(ad, de, ae)));
		}
		catch (FactException te) { System.err.println("Invalid triangles in triangle test."); }

		assertEquals(expectedTriangles.size(), computedTriangles.size());
		
		for (Triangle computedTriangle : computedTriangles)
		{
			assertTrue(expectedTriangles.contains(computedTriangle));
		}
	}
	
	
	
	
	
	
	
	
	@Test
	void test_collinear_line_segments()
	{
		init("jsonfiles/collinear_line_segments.json");

		TriangleIdentifier triIdentifier = new TriangleIdentifier(_segments);

		Set<Triangle> computedTriangles = triIdentifier.getTriangles();

		System.out.println(computedTriangles);

		assertEquals(0, computedTriangles.size());


		//
		// Triangles we expect to find
		//
		List<Triangle> expectedTriangles = new ArrayList<Triangle>();
		
		assertEquals(expectedTriangles.size(), computedTriangles.size());
		
		for (Triangle computedTriangle : computedTriangles)
		{
			assertTrue(expectedTriangles.contains(computedTriangle));
		}
	}
	
	
	
	

	@Test
	void test_crossed_lines()
	{
		init("jsonfiles/crossed_lines.json");

		TriangleIdentifier triIdentifier = new TriangleIdentifier(_segments);

		Set<Triangle> computedTriangles = triIdentifier.getTriangles();

		System.out.println(computedTriangles);

		assertEquals(0, computedTriangles.size());


		//
		// Triangles we expect to find
		//
		List<Triangle> expectedTriangles = new ArrayList<Triangle>();
		
		assertEquals(expectedTriangles.size(), computedTriangles.size());
		
		for (Triangle computedTriangle : computedTriangles)
		{
			assertTrue(expectedTriangles.contains(computedTriangle));
		}
	}
	
	
	
	
	
	@Test
	void test_crossed_square()
	{
		init("jsonfiles/Crossed_Square.json");

		TriangleIdentifier triIdentifier = new TriangleIdentifier(_segments);

		Set<Triangle> computedTriangles = triIdentifier.getTriangles();

		System.out.println(computedTriangles);

		assertEquals(8, computedTriangles.size());

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
		// Triangles we expect to find
		//
		List<Triangle> expectedTriangles = new ArrayList<Triangle>();
		try {
			expectedTriangles.add(new Triangle(Arrays.asList(ab, bc, ac)));
			
			expectedTriangles.add(new Triangle(Arrays.asList(ab, ad, bd)));
			
			expectedTriangles.add(new Triangle(Arrays.asList(ab, a_star_a, a_star_b)));
			
			expectedTriangles.add(new Triangle(Arrays.asList(ac, ad, cd)));
			
			expectedTriangles.add(new Triangle(Arrays.asList(ac, a_star_a, a_star_c)));
			
			expectedTriangles.add(new Triangle(Arrays.asList(bc, bd, cd)));
			
			expectedTriangles.add(new Triangle(Arrays.asList(bd, a_star_b, a_star_d)));
			
			expectedTriangles.add(new Triangle(Arrays.asList(cd, a_star_c, a_star_d)));
			
		
		}
		catch (FactException te) { System.err.println("Invalid triangles in triangle test."); }

		assertEquals(expectedTriangles.size(), computedTriangles.size());
		
		for (Triangle computedTriangle : computedTriangles)
		{
			assertTrue(expectedTriangles.contains(computedTriangle));
		}
	}
	
	
	
	
	@Test
	void test_fully_connected_irregular_polygon()
	{
		init("jsonfiles/fully_connected_irregular_polygon.json");

		TriangleIdentifier triIdentifier = new TriangleIdentifier(_segments);

		Set<Triangle> computedTriangles = triIdentifier.getTriangles();


		assertEquals(35, computedTriangles.size());

		//
		// ALL original segments: 8 in this figure.
		//
		Segment ab = new Segment(_points.getPoint("A"), _points.getPoint("B"));
		Segment ac = new Segment(_points.getPoint("A"), _points.getPoint("C"));
		Segment ad = new Segment(_points.getPoint("A"), _points.getPoint("D"));
		Segment ae = new Segment(_points.getPoint("A"), _points.getPoint("E"));
		
		Segment bc = new Segment(_points.getPoint("B"), _points.getPoint("C"));
		Segment bd = new Segment(_points.getPoint("B"), _points.getPoint("D"));
		Segment be = new Segment(_points.getPoint("B"), _points.getPoint("E"));
		
		Segment cd = new Segment(_points.getPoint("C"), _points.getPoint("D"));
		Segment ce = new Segment(_points.getPoint("C"), _points.getPoint("E"));
		
		Segment de = new Segment(_points.getPoint("D"), _points.getPoint("E"));
		

		//
		// Implied minimal segments: 4 in this figure.
		//
		Point f_star = _points.getPoint(2.49122807018, 3.43859649123);
		Point g_star = _points.getPoint(3.52727272727 ,3.30909090909);
		Point h_star = _points.getPoint(2.173913043, 1.217391304);
		Point i_star = _points.getPoint(2.941176471, 0.7058823529);
		Point j_star = _points.getPoint(3.806451613 , 1.3548387);

		
		
		Segment f_star_a = new Segment(f_star, _points.getPoint("A"));
		Segment f_star_c = new Segment(f_star, _points.getPoint("C"));
		Segment f_star_d = new Segment(f_star, _points.getPoint("D"));
		Segment f_star_e = new Segment(f_star, _points.getPoint("E"));
	
	
		Segment g_star_b = new Segment(g_star, _points.getPoint("B"));
		Segment g_star_c = new Segment(g_star, _points.getPoint("C"));
		Segment g_star_d = new Segment(g_star, _points.getPoint("D"));
		Segment g_star_e = new Segment(g_star, _points.getPoint("E"));
		
	
		Segment h_star_a = new Segment(h_star, _points.getPoint("A"));
		Segment h_star_b = new Segment(h_star, _points.getPoint("B"));
		Segment h_star_d = new Segment(h_star, _points.getPoint("D"));
		Segment h_star_e = new Segment(h_star, _points.getPoint("E"));
		
		
		Segment i_star_a = new Segment(i_star, _points.getPoint("A"));
		Segment i_star_b = new Segment(i_star, _points.getPoint("B"));
		Segment i_star_c = new Segment(i_star, _points.getPoint("C"));
		Segment i_star_e = new Segment(i_star, _points.getPoint("E"));
		
		Segment j_star_a = new Segment(j_star, _points.getPoint("A"));
		Segment j_star_b = new Segment(j_star, _points.getPoint("B"));
		Segment j_star_c = new Segment(j_star, _points.getPoint("C"));
		Segment j_star_d = new Segment(j_star, _points.getPoint("D"));
		
		Segment f_star_g_star = new Segment(f_star, g_star);
		Segment f_star_h_star = new Segment(f_star, h_star);
		Segment h_star_i_star = new Segment(h_star, i_star);
		Segment i_star_j_star = new Segment(i_star, j_star);
		Segment g_star_j_star = new Segment(g_star, j_star);


		//
		// Non-minimal, computed segments: 2 in this figure.
		//
		

		//
		// Triangles we expect to find
		//
		List<Triangle> expectedTriangles = new ArrayList<Triangle>();
		try {
			//
			// Triangles we expect to find from maximum segments
			//
			expectedTriangles.add(new Triangle(Arrays.asList(ab, bc, ac)));
			expectedTriangles.add(new Triangle(Arrays.asList(ab, bd, ad)));
			expectedTriangles.add(new Triangle(Arrays.asList(ab, be, ae)));
			
			expectedTriangles.add(new Triangle(Arrays.asList(ac, cd, ad)));
			expectedTriangles.add(new Triangle(Arrays.asList(ac, ce, ae)));
			
			expectedTriangles.add(new Triangle(Arrays.asList(ad, de, ae)));
			
			
			expectedTriangles.add(new Triangle(Arrays.asList(bc, cd, bd)));
			expectedTriangles.add(new Triangle(Arrays.asList(bc, ce, be)));
			
			expectedTriangles.add(new Triangle(Arrays.asList(bd, de, be)));
			
			expectedTriangles.add(new Triangle(Arrays.asList(ce, cd, de)));
			
			
			
			
			expectedTriangles.add(new Triangle(Arrays.asList(ab , h_star_b, h_star_a)));
			expectedTriangles.add(new Triangle(Arrays.asList(ab , i_star_b, i_star_a)));
			expectedTriangles.add(new Triangle(Arrays.asList(ab , j_star_b, j_star_a)));
			expectedTriangles.add(new Triangle(Arrays.asList(ac , f_star_c, f_star_a)));
			expectedTriangles.add(new Triangle(Arrays.asList(ad , j_star_d, j_star_a)));
			expectedTriangles.add(new Triangle(Arrays.asList(ae , f_star_e, f_star_a)));
			expectedTriangles.add(new Triangle(Arrays.asList(ae , i_star_e, i_star_a)));
			expectedTriangles.add(new Triangle(Arrays.asList(ae , h_star_e, h_star_a)));
			
			
			expectedTriangles.add(new Triangle(Arrays.asList(bc , g_star_c, g_star_b)));
			expectedTriangles.add(new Triangle(Arrays.asList(bc , j_star_c, j_star_b)));
			expectedTriangles.add(new Triangle(Arrays.asList(bc , i_star_c, i_star_b)));
			expectedTriangles.add(new Triangle(Arrays.asList(bd , h_star_d, h_star_b)));
			expectedTriangles.add(new Triangle(Arrays.asList(be , g_star_e, g_star_b)));
			
			
			
			expectedTriangles.add(new Triangle(Arrays.asList(cd , f_star_c, f_star_d)));
			expectedTriangles.add(new Triangle(Arrays.asList(cd , g_star_c, g_star_d)));
			expectedTriangles.add(new Triangle(Arrays.asList(cd , j_star_c, j_star_d)));
			expectedTriangles.add(new Triangle(Arrays.asList(ce , i_star_c, i_star_e)));
			
			
			expectedTriangles.add(new Triangle(Arrays.asList(de , f_star_d, f_star_e)));
			expectedTriangles.add(new Triangle(Arrays.asList(de , g_star_d, g_star_e)));
			expectedTriangles.add(new Triangle(Arrays.asList(de , h_star_d, h_star_e)));
			
			
			expectedTriangles.add(new Triangle(Arrays.asList(h_star_a , i_star_a, h_star_i_star)));
			expectedTriangles.add(new Triangle(Arrays.asList(i_star_b , j_star_b, i_star_j_star)));
			expectedTriangles.add(new Triangle(Arrays.asList(g_star_c , j_star_c, g_star_j_star)));
			expectedTriangles.add(new Triangle(Arrays.asList(f_star_d , g_star_d, f_star_g_star)));
			expectedTriangles.add(new Triangle(Arrays.asList(f_star_e , h_star_e, f_star_h_star)));
			

			
		}
		catch (FactException te) { System.err.println("Invalid triangles in triangle test."); }

		assertEquals(expectedTriangles.size(), computedTriangles.size());
		
		for (Triangle computedTriangle : computedTriangles)
		{
			assertTrue(expectedTriangles.contains(computedTriangle));
		}
	}
	
	
	
	@Test
	void test_lineseg()
	{
		init("jsonfiles/lineseg.json") ;

		TriangleIdentifier triIdentifier = new TriangleIdentifier(_segments);

		Set<Triangle> computedTriangles = triIdentifier.getTriangles();

		System.out.println(computedTriangles);

		assertEquals(0, computedTriangles.size());


		//
		// Triangles we expect to find
		//
		List<Triangle> expectedTriangles = new ArrayList<Triangle>();
		
		assertEquals(expectedTriangles.size(), computedTriangles.size());
		
		for (Triangle computedTriangle : computedTriangles)
		{
			assertTrue(expectedTriangles.contains(computedTriangle));
		}
	}
	
	
	
	
	@Test
	void test_overlapping_rectangles()
	{
		init("jsonfiles/overlapping_rectangles.json");

		TriangleIdentifier triIdentifier = new TriangleIdentifier(_segments);

		Set<Triangle> computedTriangles = triIdentifier.getTriangles();

		System.out.println(computedTriangles);

		assertEquals(0, computedTriangles.size());


		//
		// Triangles we expect to find
		//
		List<Triangle> expectedTriangles = new ArrayList<Triangle>();
		
		assertEquals(expectedTriangles.size(), computedTriangles.size());
		
		for (Triangle computedTriangle : computedTriangles)
		{
			assertTrue(expectedTriangles.contains(computedTriangle));
		}
	}
	
	
	
	
	@Test
	void test_single_triangle()
	{
		init("jsonfiles/single_triangle.json");

		TriangleIdentifier triIdentifier = new TriangleIdentifier(_segments);

		Set<Triangle> computedTriangles = triIdentifier.getTriangles();

		System.out.println(computedTriangles);

		assertEquals(1, computedTriangles.size());
		
		
		//
		// ALL original segments: 8 in this figure.
		//
		Segment ab = new Segment(_points.getPoint("A"), _points.getPoint("B"));
		Segment ac = new Segment(_points.getPoint("A"), _points.getPoint("C"));
		Segment bc = new Segment(_points.getPoint("B"), _points.getPoint("C"));

		//
		// Triangles we expect to find
		//
		List<Triangle> expectedTriangles = new ArrayList<Triangle>();
		try {
			expectedTriangles.add(new Triangle(Arrays.asList(ab, bc, ac)));

			
		}
		catch (FactException te) { System.err.println("Invalid triangles in triangle test."); }

		assertEquals(expectedTriangles.size(), computedTriangles.size());
		
		for (Triangle computedTriangle : computedTriangles)
		{
			assertTrue(expectedTriangles.contains(computedTriangle));
		}
	}
	
	
	
	
	
	@Test
	void test_snake()
	{
		init("jsonfiles/snake.json");

		TriangleIdentifier triIdentifier = new TriangleIdentifier(_segments);

		Set<Triangle> computedTriangles = triIdentifier.getTriangles();

		System.out.println(computedTriangles);

		assertEquals(3, computedTriangles.size());

		//
		// ALL original segments: 8 in this figure.
		//
		Segment ab = new Segment(_points.getPoint("A"), _points.getPoint("B"));
		Segment ac = new Segment(_points.getPoint("A"), _points.getPoint("C"));
		Segment bc = new Segment(_points.getPoint("B"), _points.getPoint("C"));

		Segment cd = new Segment(_points.getPoint("C"), _points.getPoint("D"));
		Segment ce = new Segment(_points.getPoint("C"), _points.getPoint("E"));
		Segment de = new Segment(_points.getPoint("D"), _points.getPoint("E"));

		Segment ef = new Segment(_points.getPoint("E"), _points.getPoint("F"));
		Segment eg = new Segment(_points.getPoint("E"), _points.getPoint("G"));
		Segment fg = new Segment(_points.getPoint("F"), _points.getPoint("G"));


		//
		// Triangles we expect to find
		//
		List<Triangle> expectedTriangles = new ArrayList<Triangle>();
		try {
			expectedTriangles.add(new Triangle(Arrays.asList(ab, bc, ac)));
			expectedTriangles.add(new Triangle(Arrays.asList(ce, cd, de)));
			expectedTriangles.add(new Triangle(Arrays.asList(eg, ef, fg)));
			
		}
		catch (FactException te) { System.err.println("Invalid triangles in triangle test."); }

		assertEquals(expectedTriangles.size(), computedTriangles.size());
		
		for (Triangle computedTriangle : computedTriangles)
		{
			assertTrue(expectedTriangles.contains(computedTriangle));
		}
	}
	
	
	
	
	@Test
	void test_Tri_quad_with_implied()
	{
		init("jsonfiles/tri_quad_with_implied.json");

		TriangleIdentifier triIdentifier = new TriangleIdentifier(_segments);

		Set<Triangle> computedTriangles = triIdentifier.getTriangles();

		System.out.println(computedTriangles);

		assertEquals(1, computedTriangles.size());

		//
		// ALL original segments: 8 in this figure.
		//

		Segment cd = new Segment(_points.getPoint("C"), _points.getPoint("D"));

		//
		// Implied minimal segments: 2 in this figure.
		//
		Point a_star = _points.getPoint(6, 8.5);

		Segment a_star_c = new Segment(a_star, _points.getPoint("C"));
		Segment a_star_d = new Segment(a_star, _points.getPoint("D"));

		//
		// Triangles we expect to find
		//
		List<Triangle> expectedTriangles = new ArrayList<Triangle>();
		try {
	
			expectedTriangles.add(new Triangle(Arrays.asList(cd, a_star_c, a_star_d)));

		}
		catch (FactException te) { System.err.println("Invalid triangles in triangle test."); }

		assertEquals(expectedTriangles.size(), computedTriangles.size());
		
		for (Triangle computedTriangle : computedTriangles)
		{
			assertTrue(expectedTriangles.contains(computedTriangle));
		}
	}
	
	
	
	
	
	@Test
	void test_Tri_quad()
	{
		init("jsonfiles/tri_quad.json");

		TriangleIdentifier triIdentifier = new TriangleIdentifier(_segments);

		Set<Triangle> computedTriangles = triIdentifier.getTriangles();

		System.out.println(computedTriangles);

		assertEquals(1, computedTriangles.size());

		//
		// ALL original segments: 8 in this figure.
		//
		Segment ic = new Segment(_points.getPoint("I"), _points.getPoint("C"));
		Segment id = new Segment(_points.getPoint("I"), _points.getPoint("D"));
		Segment cd = new Segment(_points.getPoint("C"), _points.getPoint("D"));

		//
		// Triangles we expect to find
		//
		List<Triangle> expectedTriangles = new ArrayList<Triangle>();
		try {
			expectedTriangles.add(new Triangle(Arrays.asList(ic, id, cd)));

		}
		catch (FactException te) { System.err.println("Invalid triangles in triangle test."); }

		assertEquals(expectedTriangles.size(), computedTriangles.size());
		
		for (Triangle computedTriangle : computedTriangles)
		{
			assertTrue(expectedTriangles.contains(computedTriangle));
		}
	}
	
	
	
	@Test
	void test_triangle_four_points()
	{
		init("jsonfiles/trianglefourpoints.json");

		TriangleIdentifier triIdentifier = new TriangleIdentifier(_segments);

		Set<Triangle> computedTriangles = triIdentifier.getTriangles();


		assertEquals(1, computedTriangles.size());
		
		//
		// ALL original segments: 8 in this figure.
		//
		
		Segment ac = new Segment(_points.getPoint("A"), _points.getPoint("C"));
		Segment bc = new Segment(_points.getPoint("B"), _points.getPoint("C"));

		//
		// Non-minimal, computed segments: 2 in this figure.
		//
		Segment ab = new Segment(_points.getPoint("A"), _points.getPoint("B"));

		//
		// Triangles we expect to find
		//
		List<Triangle> expectedTriangles = new ArrayList<Triangle>();
		try {
			expectedTriangles.add(new Triangle(Arrays.asList(ab, bc, ac)));
			
		}
		catch (FactException te) { System.err.println("Invalid triangles in triangle test."); }

		assertEquals(expectedTriangles.size(), computedTriangles.size());
		
		for (Triangle computedTriangle : computedTriangles)
		{
			assertTrue(expectedTriangles.contains(computedTriangle));
		}
	}

}
