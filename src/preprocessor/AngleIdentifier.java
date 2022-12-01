package preprocessor;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.points.Point;
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
		Set<Segment> segmentKeys = _segments.keySet();
		List<Segment> segmentList = new ArrayList<Segment>();
		
		for(Segment s: segmentKeys) {
			segmentList.add(s);
		}	
		
		for (int i = 0; i < segmentKeys.size()-1; i++) {
			for (int j = i+1; j < segmentKeys.size(); j++) {
				
				Segment seg1 = segmentList.get(i);
				Segment seg2 = segmentList.get(j);
				
				Point vertex = seg1.sharedVertex(seg2);
				
				if (vertex != null) {
					if (!(seg1.HasSubSegment(seg2))){
						
						Angle angle = new Angle(seg1, seg2);
						_angles.add(angle);
						
					}
				}
				
			}
		}
		/**
		 * ALG:
		 * Compute minimal segments
		 * Build angles out of minimal segments -> Should then be canonical
		 * Using minimal and nonminimal segments make all other angles
		 * 
		 */
	}
}
