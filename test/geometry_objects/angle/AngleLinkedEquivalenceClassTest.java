package geometry_objects.angle;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.angle.AngleEquivalenceClasses;
import geometry_objects.angle.AngleLinkedEquivalenceClass;
import geometry_objects.angle.comparators.AngleStructureComparator;
import geometry_objects.points.Point;

class AngleLinkedEquivalenceClassTest {

public Angle angleBuilder(Point P1, Point P2, Point P3) throws FactException {
		
		Segment N1 = new Segment(P1, P2);
		Segment N2 = new Segment(P2, P3);
		Angle angle = new Angle(N1, N2);
		return angle;
		
	}
	
	@Test
	void AddWithNoCanonical() throws FactException {
		AngleStructureComparator ASC = new AngleStructureComparator();
		
		AngleLinkedEquivalenceClass AEC = new AngleLinkedEquivalenceClass(ASC);
		
		Point P1 = new Point(3,6);
		Point P2 = new Point(0,0);
		Point P3 = new Point(0,6);
		
		Angle angle1 = angleBuilder(P1,P2,P3);
		
		assertTrue(AEC.add(angle1));
		
	}
	
	@Test
	void AddWithCanonical() throws FactException {
		AngleStructureComparator ASC = new AngleStructureComparator();
		
		AngleLinkedEquivalenceClass AEC = new AngleLinkedEquivalenceClass(ASC);
		
		Point P1 = new Point(3,6);
		Point P2 = new Point(0,0);
		Point P3 = new Point(0,6);
		
		Angle angle1 = angleBuilder(P1,P2,P3);
		
		AEC.add(angle1);
		
		Point P4 = new Point(4,8);
		Point P5 = new Point(0,7);
		
		Angle angle2 = angleBuilder(P4, P2, P5);
		
		assertTrue(AEC.add(angle2));
		
	}
	
	

}
