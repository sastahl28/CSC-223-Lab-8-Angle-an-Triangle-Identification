package geometry_objects.angle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.points.Point;

class AngleTest {

	//create points
	Point a = new Point(1,1);
	Point b = new Point(1,3);
	Point c = new Point(2,1);

	Point g = new Point(1 , 6);

	Point d = new Point(4 ,4);
	Point e = new Point(4 , 7);
	Point f = new Point(5 , 4);

	//create segments
	Segment ab = new Segment(a , b);
	Segment ac = new Segment( a, c);
	Segment bg = new Segment(b ,g);

	Segment de = new Segment(d ,e);
	Segment df = new Segment( d ,f);

	//create angles
	Angle _abc;
	Angle _def;
	Angle _abg;


	AngleTest() throws FactException {

		//Initialize angles 
		_abc = new Angle(ab , ac);
		_def = new Angle(de , df);
		_abg = new Angle(ab , bg);

	}


	@Test
	void testGetMeasure() {

		//find the measurement of the angles
		assertEquals(90.0 , _abc.getMeasure());
		assertEquals(90.0 , _def.getMeasure());
		assertEquals(180.0 , _abg.getMeasure());

	}

	@Test 
	void testGetRay1() {
		//find the measurement of the angles
		assertEquals(ab , _abc.getRay1());
		assertEquals(de , _def.getRay1());
		assertEquals(ab , _abg.getRay1());

	}
	
	
	@Test 
	void testGetRay2() {
		//find the measurement of the angles
		assertEquals(ac , _abc.getRay2());
		assertEquals(df , _def.getRay2());
		assertEquals(bg , _abg.getRay2());

	}


	@Test
	void findAngleTest() {

		//test various measures of angles
		assertEquals(1.5707963267948966 , _abc.findAngle(b, a, c));
		assertEquals(1.5707963267948966 , _def.findAngle(e, d, f));

		//check that the measurements of angles are the same
		assertEquals(_abc.findAngle(b, a, c) , _def.findAngle(e, d, f));

		assertEquals(3.141592653589793 , _abg.findAngle(a, b, g));
	}



	@Test
	void compareToTest() {

		//check with angles that have the same measurement
		assertEquals(0 , _abc.compareTo(_def));

		//check with angles with different measurement
		assertEquals(-90 , _abc.compareTo(_abg));
		assertEquals(-90 , _def.compareTo(_abg));
	}


	@Test
	void overlaysTest() {

		//examine relationships wiht angles
		//test with angles that overlay
		assertTrue(_abc.overlays(_abc));
		assertTrue(_abg.overlays(_abg));
		assertTrue(_def.overlays(_def));

		//test with angles that do not overlay
		assertFalse(_abc.overlays(_def));
		assertFalse(_abc.overlays(_abg));
		assertFalse(_def.overlays(_abg));
	}

	@Test
	void overlaysAsRayTest() {

		//examine relationships wiht angles
		//test with angles that overlay
		assertEquals( ab , _abc.overlayingRay(ab));
		assertEquals( bg , _abg.overlayingRay(bg));
		assertEquals( de , _def.overlayingRay(de));

		//test with angles that do not overlay
		assertEquals( null , _abc.overlayingRay(bg));
		assertEquals( null , _abg.overlayingRay(de));
		assertEquals( null , _def.overlayingRay(ab));	

	}


	@Test
	void testToString() {
		//test the toString method
		assertEquals("Angle( m__UNNAMED__UNNAMED__UNNAMED = 90.000)" , _abc.toString());
		assertEquals("Angle( m__UNNAMED__UNNAMED__UNNAMED = 90.000)" , _def.toString());
		assertEquals("Angle( m__UNNAMED__UNNAMED__UNNAMED = 180.000)" , _abg.toString());
	
	
	}

	
	@Test
	void equalsTest() {
		
		assertFalse(_abc.equals(_def));
	}
	 
}