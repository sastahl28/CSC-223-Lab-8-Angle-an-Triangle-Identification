package preprocessor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
	public Set<Triangle> getTriangles() throws FactException
	{
		if (_triangles != null) return _triangles;

		_triangles = new HashSet<Triangle>();

		computeTriangles();

		return _triangles;
	}

	private void computeTriangles() throws FactException
	{
		//TODO
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


					//create a list of the segments
					List<Segment> trigSegments = new ArrayList<Segment>();

					//add segments to the list
					trigSegments.add(seg1);
					trigSegments.add(seg2);
					trigSegments.add(seg3);


					try
					{Triangle trig = new Triangle(trigSegments);




					//check that no segments are colinear
					//if they are there would be a 180 angle and not  triangle
					if(!seg1.isCollinearWith(seg2) && !seg1.isCollinearWith(seg3) && !seg2.isCollinearWith(seg3)) {

						//check that there is a shared vertex
						if( !(seg1.sharedVertex(seg2) == null) && !(seg1.sharedVertex(seg3) == null) 
								&& !(seg2.sharedVertex(seg3)==null))
						{

							//check that these shared vertices are not the same 
							if(!seg1.sharedVertex(seg2).equals(seg1.sharedVertex(seg3)) && 
									!seg1.sharedVertex(seg2).equals(seg2.sharedVertex(seg3)) &&
									!seg1.sharedVertex(seg3).equals(seg2.sharedVertex(seg3))) 
							{
								_triangles.add(trig);
							}

						}
					}
					}


					catch(FactException trig) {

					}




				}

				/*

					//check that no segmets are collinear
					//if they are there would be a 180 angle and not  triangle
					if(!seg1.isCollinearWith(seg2) && !seg1.isCollinearWith(seg3) && !seg2.isCollinearWith(seg3)) {

						//check that the 
						if(seg1.getPoint1().equals(seg2.getPoint1())) {


							if(seg1.getPoint2().equals(seg3.getPoint1()) && seg2.getPoint2().equals(seg3.getPoint2())) {

								//add the triangle to the list
								_triangles.add(trig);
							}

							if(seg1.getPoint2().equals(seg3.getPoint2()) && seg2.getPoint2().equals(seg3.getPoint1())) {
								//add the triangle to the hash map
								_triangles.add(trig);
							}

						}

						if(seg1.getPoint1().equals(seg2.getPoint2())) {


							if(seg1.getPoint2().equals(seg3.getPoint1()) && seg2.getPoint1().equals(seg3.getPoint2())) {
								//add the triangle to the hasmap
								_triangles.add(trig);

							}

							if(seg1.getPoint2().equals(seg3.getPoint2()) && seg2.getPoint1().equals(seg3.getPoint1())) {
								//add the triangle to the hash map
								_triangles.add(trig);
							}


						}

						if(seg1.getPoint1().equals(seg3.getPoint1())) {


							if(seg1.getPoint2().equals(seg2.getPoint1()) && seg2.getPoint2().equals(seg3.getPoint2())) {
								//add the triangle to the hasmap
								_triangles.add(trig);

							}

							if(seg1.getPoint2().equals(seg2.getPoint2()) && seg2.getPoint1().equals(seg3.getPoint2())) {
								//add the triangle to the hash map
								_triangles.add(trig);
							}


						}

						if(seg1.getPoint1().equals(seg3.getPoint2())) {


							if(seg1.getPoint2().equals(seg2.getPoint1()) && seg2.getPoint2().equals(seg3.getPoint1())) {
								//add the triangle to the hasmap
								_triangles.add(trig);


							}

							if(seg1.getPoint2().equals(seg2.getPoint2()) && seg2.getPoint1().equals(seg3.getPoint1())) {
								//add the triangle to the hash map
								_triangles.add(trig);
							}


						}




					}


				 */


			}
		}

	}




}
}
