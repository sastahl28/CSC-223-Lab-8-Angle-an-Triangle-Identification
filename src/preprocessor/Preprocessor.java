package preprocessor;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;

import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import preprocessor.delegates.ImplicitPointPreprocessor;
import geometry_objects.Segment;

public class Preprocessor
{
	// The explicit points provided to us by the user.
	// This database will also be modified to include the implicit
	// points (i.e., all points in the figure).
	protected PointDatabase _pointDatabase;

	// Minimal ('Base') segments provided by the user
	protected Set<Segment> _givenSegments;

	// The set of implicitly defined points caused by segments
	// at implicit points.
	protected Set<Point> _implicitPoints;

	// The set of implicitly defined segments resulting from implicit points.
	protected Set<Segment> _implicitSegments;

	// Given all explicit and implicit points, we have a set of
	// segments that contain no other subsegments; these are minimal ('base') segments
	// That is, minimal segments uniquely define the figure.
	protected Set<Segment> _allMinimalSegments;

	// A collection of non-basic segments
	protected Set<Segment> _nonMinimalSegments;

	// A collection of all possible segments: maximal, minimal, and everything in between
	// For lookup capability, we use a map; each <key, value> has the same segment object
	// That is, key == value. 
	protected Map<Segment, Segment> _segmentDatabase;
	public Map<Segment, Segment> getAllSegments() { return _segmentDatabase; }

	public Preprocessor(PointDatabase points, Set<Segment> segments)
	{
		_pointDatabase  = points;
		_givenSegments = segments;

		_segmentDatabase = new HashMap<Segment, Segment>();

		analyze();
	}

	/**
	 * Invoke the precomputation procedure.
	 */
	public void analyze()
	{
		//
		// Implicit Points
		//
		_implicitPoints = ImplicitPointPreprocessor.compute(_pointDatabase, _givenSegments.stream().toList());

		//
		// Implicit Segments attributed to implicit points
		//
		_implicitSegments = computeImplicitBaseSegments(_implicitPoints);

		//
		// Combine the given minimal segments and implicit segments into a true set of minimal segments
		//     *givenSegments may not be minimal
		//     * implicitSegmen
		//
		_allMinimalSegments = identifyAllMinimalSegments(_implicitPoints, _givenSegments, _implicitSegments);

		//
		// Construct all segments inductively from the base segments
		//
		_nonMinimalSegments = constructAllNonMinimalSegments(_allMinimalSegments);

		//
		// Combine minimal and non-minimal into one package: our database
		//
		_allMinimalSegments.forEach((segment) -> _segmentDatabase.put(segment, segment));
		_nonMinimalSegments.forEach((segment) -> _segmentDatabase.put(segment, segment));
	}

	/**
	 * Create a set of segments out of a sorted list of points
	 * 
	 * Ex: The list {(0,0), (1,0), (2,0)} would become the set
	 * { Segment((0,0),(1,0)), Segment((1,0),(2,0)) }
	 * 
	 * @param point
	 * @return
	 */
	protected Set<Segment> getSegments(SortedSet<Point> point){
		
		
		Set<Segment> SegSet = new LinkedHashSet<Segment>(); 
		
		
		List<Point> sortedList = point.stream().toList();
		
		
		//for each point in the sorted list, create a segment with the next point to the right
		for (int p=0; p < sortedList.size() -1; p++) {
			
			
			Point Left = sortedList.get(p);
			Point Right = sortedList.get(p+1);
			
			
			Segment seg = new Segment(Left, Right);
			
			
			SegSet.add(seg);
			
			
			Left = Right;
			
		}
		
		return SegSet;
	}

	/**
	 * Find all the segments created from the implicit points
	 * 
	 * Ex: Segment A-------X-------B where X is implict returns segments (A,X) and (X,B)
	 * 
	 * 
	 * @param implicitPoints
	 * @return
	 */
	protected Set<Segment> computeImplicitBaseSegments(Set<Point> implicitPoints){
		
		Set<Segment> segSet = new LinkedHashSet<Segment>();
		
		//For each given segment, if an implicit point is contained on the segment, chop the segment up at the implicit point
		for (Segment s: _givenSegments) {
			
			
			//gets all implicit points on the segment
			SortedSet<Point> pointSet = s.collectOrderedPointsOnSegment(implicitPoints);
			
			
			//if there are implicit points, chop the original segment
			if (!(pointSet.isEmpty())) {
				
				
				pointSet.add(s.getPoint1());
				pointSet.add(s.getPoint2());
				
				
				Set<Segment> orderedSet = this.getSegments(pointSet);
				
				
				segSet.addAll(orderedSet);
				
			}
			
		}
		
		return segSet;
	}
	
	/**
	 * Identify all minimal segments
	 * 
	 * Ex:
	 * A------X------B
	 * Minimal segments: (A,X), (X,B)
	 * 
	 * @param implicitPoints
	 * @param givenSegments
	 * @param implicitSegments
	 * @return
	 */
	protected Set<Segment> identifyAllMinimalSegments(Set<Point> implicitPoints , Set<Segment> givenSegments , Set<Segment> implicitSegments){
		
		Set<Segment> segSet = new LinkedHashSet<Segment>();
 		segSet.addAll(implicitSegments);
		
		//For each segment, if it does NOT contain an implicit point, add it to set
		for (Segment seg: givenSegments) {
			
			
			boolean isMinimal = true;
			
			//if the segment contains an implicit point then it is not minimal
			for (Point imp: implicitPoints) {
				
				if (seg.pointLiesBetweenEndpoints(imp)){
					
					isMinimal=false;
				}	
			}
			
			
			if (isMinimal) {
				
				segSet.add(seg);
				
			}
			
		}
				  
		  return segSet;
		 
	}


	/**
	 * Given all our minimal segments, create a set of all NON minimal segments
	 * 
	 * Ex: A-------X-------B-------C
	 * Non minimal: (A,B), (A,C), (X,C)
	 * 
	 * @param allMinimalSegments
	 * @return
	 */
	protected Set<Segment> constructAllNonMinimalSegments(Set<Segment> allMinimalSegments){
		
		Set<Segment> allSegs = new LinkedHashSet<Segment>();
		
		Queue<Segment> workList = new LinkedList<Segment>();
		
		
		//Create a list of all minimal segments
		workList.addAll(allMinimalSegments);
		
		//while the list is not empty, remove the top segment and compare it to all other minimal segments
		//If the two segments are colinear and share a vertex, create a new segment out of them 
		//add the big segment to the work list
		
		while (!(workList.isEmpty())) {
			
			
			Segment seg1 = workList.remove();
			
			
			for (Segment seg2 : allMinimalSegments) {
				
				
				//check they are not the same segment
				if (!(seg1.equals(seg2))) {
					
					
					if (seg1.coincideWithoutOverlap(seg2)) {
						
						
						
						Point p = seg1.sharedVertex(seg2);
						
						
						//if the two segments share a vertex create a segment out of the non-shared points
						if(p != null) {
							
							Segment newSeg = new Segment(seg1.other(p), seg2.other(p));
							allSegs.add(newSeg);
							workList.add(newSeg);
							
						}
					}
				}
			}
		}			
			return allSegs;
	}
	



}

