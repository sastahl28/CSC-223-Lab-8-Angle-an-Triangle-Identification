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
		Set<Segment> segmentKeys = _segments.keySet();
		List<Segment> segmentList = new ArrayList<Segment>();
		
		for(Segment s: segmentKeys) {
			segmentList.add(s);
		}

		//begin with a segment from given segments			
		for(int i =0; i< segmentKeys.size()-1; i++) {

			Segment seg1 = segmentList.get(i);
					
			for(int j = i+1; j <segmentKeys.size()-2; j++) {
				
				Segment seg2 = segmentList.get(j);

				for(int k = j+1; j < segmentKeys.size()-3; k++) {
					
					Segment seg3 = segmentList.get(k);

					//create a list of elements
					List<Segment> trigSegments = new ArrayList<Segment>();
					
					//add segments to the list
					trigSegments.add(seg1);
					trigSegments.add(seg2);
					trigSegments.add(seg3);

/*
						//check that no segments are colinear
						if( !seg1.isCollinearWith(seg2) && !seg1.isCollinearWith(seg3) &&!seg2.isCollinearWith(seg3)) {

							//check that there is a shared vertex
							if( !(seg1.sharedVertex(seg2) == null) && !(seg1.sharedVertex(seg3) == null) && !(seg2.sharedVertex(seg3)==null))
							{
								//check that the verticies are unique
								if(!seg1.sharedVertex(seg2).equals(seg1.sharedVertex(seg3)) && 
										!seg1.sharedVertex(seg2).equals(seg2.sharedVertex(seg3)) &&
										!seg1.sharedVertex(seg3).equals(seg2.sharedVertex(seg3))) 
								{*/
									//create the triangle
									try
									{Triangle trig = new Triangle(trigSegments);

									//add the triangle to the 
									_triangles.add(trig);
									}

									catch(FactException trig) {
										break;
									}
								}
							}

					}

				}
				

			}

/*		}

	}





}*/
