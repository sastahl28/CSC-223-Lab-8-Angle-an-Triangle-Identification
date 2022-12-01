package preprocessor;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.lang.reflect.Array;
import java.util.ArrayList;

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

		//get a collection of the segments from the map of segments


		List<Segment> segmentList = new ArrayList<Segment>(_segments.keySet());


		//begin with a segment from given segments			
		for(int i =0; i< segmentList.size()-2; i++) {

			Segment seg1 = segmentList.get(i);

			for(int j = i+1; j <segmentList.size()-1; j++) {

				Segment seg2 = segmentList.get(j);

				for(int k = j+1; k < segmentList.size(); k++) {

					Segment seg3 = segmentList.get(k);

					//create a list of elements
					List<Segment> trigSegments = new ArrayList<Segment>();

					//add segments to the list
					trigSegments.add(seg1);
					trigSegments.add(seg2);
					trigSegments.add(seg3);

					//create the triangle
					try
					{Triangle triangle = new Triangle(trigSegments);

					//add the triangle to the 
					_triangles.add(triangle);
					}

					catch(FactException triangle) {

					}
				}
			}

		}

	}


}

