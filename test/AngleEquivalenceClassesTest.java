import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.angle.AngleEquivalenceClasses;
import geometry_objects.angle.AngleLinkedEquivalenceClass;
import geometry_objects.angle.comparators.AngleStructureComparator;
import geometry_objects.points.Point;

class AngleEquivalenceClassesTest {

public Angle angleBuilder(Point P1, Point P2, Point P3) throws FactException {
		
		Segment N1 = new Segment(P1, P2);
		Segment N2 = new Segment(P2, P3);
		Angle angle = new Angle(N1, N2);
		return angle;
		
	}
	
@Test
void AddWithNoCanonical() throws FactException {
	
	AngleEquivalenceClasses AEC = new AngleEquivalenceClasses();
	
	Point P1 = new Point(3,6);
	Point P2 = new Point(0,0);
	Point P3 = new Point(0,6);
	
	Angle angle1 = angleBuilder(P1,P2,P3);
	
	Point P4 = new Point(2,2);
	
	Angle angle2 = angleBuilder(P4,P1,P3);
	
	assertTrue(AEC.add(angle1));	
	assertFalse(AEC.add(angle1));	
	
	assertEquals(AEC.size(), 1);
	assertTrue(AEC.contains(angle1));
	
	assertFalse(AEC.contains(angle2));
	
}

//add element larger than the canonical
@Test
void AddWithCanonical() throws FactException {
	
	AngleEquivalenceClasses AEC = new AngleEquivalenceClasses();
	
	Point P1 = new Point(3,6);
	Point P2 = new Point(0,0);
	Point P3 = new Point(0,6);
	
	Angle angle1 = angleBuilder(P1,P2,P3);
	
	AEC.add(angle1);
	
	Point P4 = new Point(4,8);
	Point P5 = new Point(0,7);
	
	Angle angle2 = angleBuilder(P4, P2, P5);
	
	assertTrue(AEC.add(angle2));
	
	assertEquals(AEC.size(), 2);
	assertEquals(AEC.numClasses(), 1);
	assertTrue(AEC.contains(angle1));
	assertTrue(AEC.contains(angle2));
	
}

@Test
void TwoClasses() throws FactException {
	
	AngleEquivalenceClasses AEC = new AngleEquivalenceClasses();
	
	Point P1 = new Point(3,6);
	Point P2 = new Point(0,0);
	Point P3 = new Point(0,6);
	
	Angle angle1 = angleBuilder(P1,P2,P3);
	
	AEC.add(angle1);
	
	Point P4 = new Point(4,8);
	Point P5 = new Point(0,6);
	
	Angle angle2 = angleBuilder(P4, P1, P5);
	
	
	assertTrue(AEC.add(angle2));
	
	assertEquals(AEC.size(), 2);
	assertEquals(AEC.numClasses(), 2);
	assertTrue(AEC.contains(angle1));
	assertTrue(AEC.contains(angle2));
	
}

@Test
void SmallerAngle() throws FactException {
	
	AngleEquivalenceClasses AEC = new AngleEquivalenceClasses();
	
	Point P1 = new Point(3,6);
	Point P2 = new Point(0,0);
	Point P3 = new Point(0,6);
	
	Angle angle1 = angleBuilder(P1,P2,P3);
	
	
	Point P4 = new Point(4,8);
	Point P5 = new Point(0,7);
	
	Angle angle2 = angleBuilder(P4, P2, P5);
	
	AEC.add(angle2);
	
	assertTrue(AEC.add(angle1));
	assertEquals(AEC.size(), 2);
	assertEquals(AEC.numClasses(), 1);
	assertTrue(AEC.contains(angle1));
	assertTrue(AEC.contains(angle2));
	
}

//add element smaller than the canonical
	@Test
	void BiggerSmallerAngle() throws FactException {
			
		AngleEquivalenceClasses AEC = new AngleEquivalenceClasses();
			
		Point P1 = new Point(3,6);
		Point P2 = new Point(0,0);
		Point P3 = new Point(0,7);
			
		Angle angle1 = angleBuilder(P1,P2,P3);
			
			
		Point P4 = new Point(4,8);
		Point P5 = new Point(0,6);
		
		Angle angle2 = angleBuilder(P4, P2, P5);
			
		AEC.add(angle2);
			
		assertTrue(AEC.add(angle1));
		assertEquals(2, AEC.size());
		assertEquals(1, AEC.numClasses());
		assertTrue(AEC.contains(angle1));
		assertTrue(AEC.contains(angle2));
		}

}
