package preprocessor;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import exceptions.FactException;
import geometry_objects.Segment;
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

	
	/*
	 * private Set<Segment> getAllSegments() { Set<Segment> segmentKeys =
	 * _segments.keySet(); List<Segment> segmentList = new ArrayList<Segment>();
	 * 
	 * for(Segment s: segmentKeys) { segmentList.add(s); }
	 * 
	 * PointDatabase PDB = new PointDatabase();
	 * 
	 * for(Segment s: segmentKeys) { PDB.put(s.getPoint1()); PDB.put(s.getPoint2());
	 * }
	 * 
	 * Set<Point> setPoint = ImplicitPointPreprocessor.compute(PDB, segmentList);
	 * 
	 * Preprocessor prepro = new Preprocessor(PDB, segmentKeys);
	 * 
	 * Set<Segment> baseSegs = prepro.computeImplicitBaseSegments(setPoint);
	 * 
	 * Set<Segment> minimalSegs = prepro.identifyAllMinimalSegments(setPoint,
	 * segmentKeys, baseSegs);
	 * 
	 * Set<Segment> NonMinimalSegs =
	 * prepro.constructAllNonMinimalSegments(minimalSegs);
	 * 
	 * Set<Segment> AllSegs = minimalSegs;
	 * 
	 * AllSegs.addAll(NonMinimalSegs);
	 * 
	 * return AllSegs;
	 * 
	 * }
	 */
	
	
	private void computeAngles() throws FactException
	{
		List<Segment> segmentList = new ArrayList<Segment>(_segments.keySet());
		
		for(int i =0; i< segmentList.size()-1; i++) {

			Segment seg1 = segmentList.get(i);

			for(int j = i+1; j <segmentList.size(); j++) {

				Segment seg2 = segmentList.get(j);
				
				Point vertex = seg1.sharedVertex(seg2);
				
				if (vertex != null) {
					
					if (!(seg1.HasSubSegment(seg2))){
						
						if (!(seg2.HasSubSegment(seg1))) {
						
						Angle angle = new Angle(seg1, seg2);
						
						_angles.add(angle);
					}
				}
			}
		
		}
	}
		

		/*
		 * for (int i = 0; i < _segments.size()-1; i++) { for (int j = i+1; j <
		 * _segments.size(); j++) {
		 * 
		 * Segment seg1 = _segments.get(i); Segment seg2 = _segments.get(j);
		 * 
		 * Point vertex = seg1.sharedVertex(seg2);
		 * 
		 * if (vertex != null) { if (!(seg1.HasSubSegment(seg2))){
		 * 
		 * Angle angle = new Angle(seg1, seg2); _angles.add(angle);
		 */
		
		//begin with a segment from given segments	
		
		/*
		 * for (int i = 0; i < allSegs.size()-1; i++) { for (int j = i+1; j <
		 * allSegs.size(); j++) {
		 * 
		 * Segment seg1 = segmentList.get(i); Segment seg2 = segmentList.get(j);
		 * 
		 * Point vertex = seg1.sharedVertex(seg2);
		 * 
		 * if (vertex != null) { if (!(seg1.HasSubSegment(seg2))){
		 * if(!(seg2.HasSubSegment(seg1))) {
		 * 
		 * Angle angle = new Angle(seg1, seg2); _angles.add(angle);
		 * 
		 * }
		 * 
		 * } }
		 * 
		 * } }
		 */
		
		
		
		
		
		
	}
}
