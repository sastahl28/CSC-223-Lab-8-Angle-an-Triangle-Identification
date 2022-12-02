package preprocessor;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.Triangle;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import preprocessor.delegates.ImplicitPointPreprocessor;
import geometry_objects.angle.Angle;
import geometry_objects.angle.AngleEquivalenceClasses;

public class AngleIdentifier
{
	protected AngleEquivalenceClasses _angles;
	protected Map<Segment, Segment> _segments;

	public AngleIdentifier(Map<Segment, Segment> segments)
	{
		_segments = segments;
	}

	/*
	 * Compute the figure triangles on the fly when requested; memoize results for subsequent calls.
	 */
	public AngleEquivalenceClasses getAngles() throws FactException
	{
		if (_angles != null) return _angles;

		_angles = new AngleEquivalenceClasses();

		computeAngles();

		return _angles;
	}
	
	
	private void computeAngles() throws FactException
	{
		List<Segment> segmentList = new ArrayList<Segment>(_segments.keySet());
		
		for(int i =0; i< segmentList.size()-1; i++) {

			Segment seg1 = segmentList.get(i);

			for(int j = i+1; j <segmentList.size(); j++) {

				Segment seg2 = segmentList.get(j);
				
				try
				{Angle angle = new Angle(seg1, seg2);

				//add the triangle to the 
				_angles.add(angle);
				}

				catch(FactException triangle) {}
				
			}
		
		}
	}
		


		
		
		
		
		
}

