package input;



import java.util.AbstractMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import geometry_objects.Segment;
import input.components.ComponentNode;
import input.components.FigureNode;
import input.components.point.PointNode;
import input.components.segment.SegmentNode;
import input.parser.JSONParser;

public class InputFacade
{
	/**
	 * Acquire a figure from the given JSON file.
     *
	 * @param filename -- the name of a file
	 * @return a FigureNode object corresponding to the input file.
	 */
	public static FigureNode extractFigure(String filename)
	{
		JSONParser Parse = new JSONParser();
		String figureStr = utilities.io.FileUtilities.readFileFilterComments(filename);
		ComponentNode node = Parse.parse(figureStr);
		
		return (FigureNode)node;
		
	}
	
	/**
	 * 1) Read in a figure from a JSON file.
	 * 2) Convert the PointNode and SegmentNode objects to a Point and Segment objects 
	 *    (those classes have more meaningful, geometric functionality).
     *
	 * @param filename
	 * @return a pair <set of points as a database, set of segments>
	 */
	public static Map.Entry<PointDatabase, Set<Segment>> toGeometryRepresentation(FigureNode figNode)
	{

		PointDatabase pointData = new PointDatabase();
		LinkedHashSet<Segment> segData = new LinkedHashSet<Segment>();
		
		
		//Get PointNode set
		Set<PointNode> PND = figNode.getPointsDatabase().getPoints();
		
		//for each pointNode in set, convert to point and add to pointDatabase
		for (PointNode pointNode : PND) {
			
			pointData.put(pointNode.getName(),pointNode.getX(),pointNode.getY());
		}
		
		//Create segment list
		List<SegmentNode> SNG = figNode.getSegments().asSegmentList();
		
		//for segments in SegmentNode, convert PointNodes to Points, then SegmentNode to Segment
		for (SegmentNode SegNode: SNG) {
			
			Point point1 = new Point(SegNode.getPoint1().getX(), SegNode.getPoint1().getY());
			Point point2 = new Point(SegNode.getPoint2().getX(), SegNode.getPoint2().getY());
			
			Segment Seg = new Segment(point1,point2);
			segData.add(Seg);
		}
		
		
		return new AbstractMap.SimpleEntry<>(pointData, segData);
	}
}
