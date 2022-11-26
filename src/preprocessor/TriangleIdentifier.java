package preprocessor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.Triangle;

public class TriangleIdentifier
{
	protected Set<Triangle>         _triangles;
	protected Map<Segment, Segment> _segments;

	public TriangleIdentifier(Map<Segment, Segment> segments)
	{
		_segments = segments;
	}

	/*
	 * Compute the figure triangles on the fly when requested;
	 * memoize results for subsequent calls.
	 */
	public Set<Triangle> getTriangles()
	{
		if (_triangles != null) return _triangles;

		_triangles = new HashSet<Triangle>();

		computeTriangles();

		return _triangles;
	}

	private void computeTriangles()
	{
		// TODO

		//begin with a segment from given segments
		for (int i = 0; i < _segments.size()-1; i++ ) {


			//for each segment AFTER that segment, compare
			for (int j = i+1; j < _segments.size(); j++) {

				//for the segment after the previous one
				for(int k = j+1; k < _segments.size(); k++) {

					//get the three segments in the map
					Segment seg1 = _segments.get(i);
					Segment seg2 = _segments.get(j);
					Segment seg3 = _segments.get(k);


					//if any of the endpoints of the segments lie on other segments
					//any of them intersect with any of the others
					if(!seg1.pointLiesBetweenEndpoints(seg2.getPoint1()) || !seg1.pointLiesBetweenEndpoints(seg2.getPoint2()) 
							|| !seg1.pointLiesBetweenEndpoints(seg3.getPoint1()) || !seg1.pointLiesBetweenEndpoints(seg3.getPoint2())
							|| !seg2.pointLiesBetweenEndpoints(seg3.getPoint1()) || !seg2.pointLiesBetweenEndpoints(seg3.getPoint2())) {
						
						
						//check that the segments are not on the same slope
						if(!seg1.coincideWithoutOverlap(seg2) || !seg1.coincideWithoutOverlap(seg3) || !seg2.coincideWithoutOverlap(seg3)) {
							
						}

					}






				}
			}

		}


		//get three segments
		//if two segments share a vertex and have an angle of greater than 0
		//and that works for all three segment verticies
		//create a new list of segments and create a new triangle and add to database

		//if the angle with any of the segments is 180 degrees then it is not a triangle

	}
}
