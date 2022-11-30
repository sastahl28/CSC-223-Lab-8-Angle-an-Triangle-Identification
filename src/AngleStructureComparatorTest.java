import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.angle.comparators.AngleStructureComparator;
import geometry_objects.points.Point;

class AngleStructureComparatorTest {

	public Angle angleBuilder(Point P1, Point P2, Point P3) throws FactException {
		
		Segment N1 = new Segment(P1, P2);
		Segment N2 = new Segment(P2, P3);
		Angle angle = new Angle(N1, N2);
		return angle;
		
	}
	
	@Test
	void LeftLongerBothSides() throws FactException {
		Point P1 = new Point(3,5);
		Point P2 = new Point(0,2);
		Point P3 = new Point(0,6);
		
		Angle angle1 = angleBuilder(P1, P2, P3);
		
		Point P4 = new Point(5,7);
		Point P5 = new Point(0,8);
		
		Angle angle2 = angleBuilder(P4, P2, P5);
		
		AngleStructureComparator ASC = new AngleStructureComparator();
		
		assertEquals(-1, ASC.compare(angle1, angle2));

	}
	
	@Test
	void RightLongerBothSides() throws FactException {
		Point P1 = new Point(3,5);
		Point P2 = new Point(0,2);
		Point P3 = new Point(0,6);
		
		Angle angle1 = angleBuilder(P1, P2, P3);
		
		Point P4 = new Point(5,7);
		Point P5 = new Point(0,8);
		
		Angle angle2 = angleBuilder(P4, P2, P5);
		
		AngleStructureComparator ASC = new AngleStructureComparator();
		
		assertEquals(1, ASC.compare(angle2, angle1));

	}
	
	//Equal
	@Test
	void Equals() throws FactException {
		Point P1 = new Point(3,5);
		Point P2 = new Point(0,2);
		Point P3 = new Point(0,6);
		
		Angle angle1 = angleBuilder(P1, P2, P3);
		
		Angle angle2 = angleBuilder(P3, P2, P1);
		
		AngleStructureComparator ASC = new AngleStructureComparator();
		
		assertEquals(1, ASC.compare(angle2, angle1));

	}
	
	//Left Bigger on one side, same on other
	@Test
	void LeftLongerOneSide() throws FactException {
		Point P1 = new Point(3,5);
		Point P2 = new Point(0,2);
		Point P3 = new Point(0,6);
		
		Angle angle1 = angleBuilder(P1, P2, P3);
		
		Point P4 = new Point(5,7);
		
		Angle angle2 = angleBuilder(P4, P2, P3);
		
		AngleStructureComparator ASC = new AngleStructureComparator();
		
		assertEquals(-1, ASC.compare(angle1, angle2));

	}
	
	//Right bigger on one side, equal on other
	@Test
	void RightLongerOneSide() throws FactException {
		Point P1 = new Point(3,5);
		Point P2 = new Point(0,2);
		Point P3 = new Point(0,6);
		
		Angle angle1 = angleBuilder(P1, P2, P3);
		
		Point P4 = new Point(5,7);
		
		Angle angle2 = angleBuilder(P4, P2, P3);
		
		AngleStructureComparator ASC = new AngleStructureComparator();
		
		assertEquals(1, ASC.compare(angle2, angle1));

	}
	
	//Left bigger, right bigger
	@Test
	void LeftBiggerRightBigger() throws FactException {
		Point P1 = new Point(3,5);
		Point P2 = new Point(0,2);
		Point P3 = new Point(0,6);
		
		Angle angle1 = angleBuilder(P1, P2, P3);
		
		Point P4 = new Point(5,7);
		Point P5 = new Point(0,4);
		
		Angle angle2 = angleBuilder(P4, P2, P5);
		
		AngleStructureComparator ASC = new AngleStructureComparator();
		
		assertEquals(0, ASC.compare(angle1, angle2));

	}

	
	//share end point not vertex
	@Test
	void SharePointNotVertex() throws FactException {
		Point P1 = new Point(3,5);
		Point P2 = new Point(0,2);
		Point P3 = new Point(0,6);
		
		Angle angle1 = angleBuilder(P1, P2, P3);
		
		Point P4 = new Point(5,7);
		Point P5 = new Point(0,4);
		
		Angle angle2 = angleBuilder(P1, P4, P5);
		
		AngleStructureComparator ASC = new AngleStructureComparator();
		
		assertEquals(Integer.MAX_VALUE, ASC.compare(angle1, angle2));

	}
	
	//Don't share points
	@Test
	void ShareNoPoints() throws FactException {
		Point P1 = new Point(3,5);
		Point P2 = new Point(0,2);
		Point P3 = new Point(0,6);
		
		Angle angle1 = angleBuilder(P1, P2, P3);
		
		Point P4 = new Point(5,7);
		Point P5 = new Point(0,4);
		Point P6 = new Point(1,2);
		
		Angle angle2 = angleBuilder(P6, P4, P5);
		
		AngleStructureComparator ASC = new AngleStructureComparator();
		
		assertEquals(Integer.MAX_VALUE, ASC.compare(angle1, angle2));

	}
	
	//Share vertex, not colinear
	@Test
	void NotColinear() throws FactException {
		Point P1 = new Point(3,5);
		Point P2 = new Point(0,2);
		Point P3 = new Point(0,6);
		
		Angle angle1 = angleBuilder(P1, P2, P3);
		
		Point P4 = new Point(3,7);
		Point P5 = new Point(4,8);
		
		Angle angle2 = angleBuilder(P4, P2, P5);
		
		AngleStructureComparator ASC = new AngleStructureComparator();
		
		assertEquals(Integer.MAX_VALUE, ASC.compare(angle2, angle1));

	}


}
