import static org.junit.jupiter.api.Assertions.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.Triangle;
import geometry_objects.angle.Angle;
import geometry_objects.angle.AngleEquivalenceClasses;
import geometry_objects.points.Point;
import preprocessor.AngleIdentifier;

class AngleIdentifierTest {

	@Test
	void singleTriangle() throws FactException {
		Point P1 = new Point(0,0);
		Point P2 = new Point(1,1);
		Point P3 = new Point(0,1);
		
		Segment seg1 = new Segment(P1,P2);
		Segment seg2 = new Segment(P2,P3);
		Segment seg3 = new Segment(P1,P3);
		
		List<Segment> tri = new ArrayList<Segment>();
		
		tri.add(seg3);
		tri.add(seg2);
		tri.add(seg1);
		
		Map <Segment, Segment> map = new HashMap<Segment, Segment>();
		map.put(seg1, seg1);
		map.put(seg2, seg2);
		map.put(seg3, seg3);
		
		AngleIdentifier AI = new AngleIdentifier(map);
		
		AngleEquivalenceClasses AEC = new AngleEquivalenceClasses();
		
		AEC = AI.getAngles();
		
		assertEquals(3, AEC.size());
	}
	
	@Test
	void singleTriangleWithMultipleSegments() throws FactException {
		Point P1 = new Point(0,0);
		Point P2 = new Point(1,1);
		Point P3 = new Point(0,1);
		Point P4 = new Point(.5,0);
		
		Segment seg1 = new Segment(P1,P4);
		Segment seg2 = new Segment(P4,P3);
		Segment seg3 = new Segment(P3,P2);
		Segment seg5 = new Segment(P1,P2);
		
		List<Segment> tri = new ArrayList<Segment>();
		
		tri.add(seg3);
		tri.add(seg2);
		tri.add(seg1);
		tri.add(seg5);
		
		Map <Segment, Segment> map = new HashMap<Segment, Segment>();
		map.put(seg1, seg1);
		map.put(seg2, seg2);
		map.put(seg3, seg3);
		map.put(seg5, seg5);
		
		AngleIdentifier AI = new AngleIdentifier(map);
		
		AngleEquivalenceClasses AEC = new AngleEquivalenceClasses();
		
		AEC = AI.getAngles();
		
		assertEquals(4, AEC.numClasses());
		assertEquals(6, AEC.size());
	}

}
