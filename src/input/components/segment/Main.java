package input.components.segment;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNodeDatabase;
import input.visitor.UnparseVisitor;
import input.components.FigureNode;

public class Main {

	public static void main(String[] args) {
		
		PointNode p1 = new PointNode("A", 1.0, 1.0);
		PointNode p2 = new PointNode("B", 2.0, 2.0);
		PointNode p3 = new PointNode("C", 3.0, 3.0);
		List<PointNode> list = new ArrayList<PointNode>(Arrays.asList(p1,p2,p3));
		
		Map<PointNode, Set<PointNode>> adjLists = new HashMap<PointNode, Set<PointNode>>();
		Set<PointNode> set1 = new HashSet<PointNode>();
		set1.add(p2);
		set1.add(p3);
		
		adjLists.put(p1, set1);
		
		Set<PointNode> set2 = new HashSet<PointNode>();
		set2.add(p1);
		set2.add(p3);
		
		adjLists.put(p2, set2);
		
		Set<PointNode> set3 = new HashSet<PointNode>();
		set3.add(p1);
		set3.add(p2);
		
		adjLists.put(p3, set3);
		
		
		PointNodeDatabase points = new PointNodeDatabase(list);
		SegmentNodeDatabase segs = new SegmentNodeDatabase(adjLists);
		FigureNode f = new FigureNode("Test", points, segs);
		
		StringBuilder sb = new StringBuilder();
		UnparseVisitor unparser = new UnparseVisitor();
		unparser.visitFigureNode((FigureNode)f,
		 new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0));
		System.out.println(sb.toString());
	}

}
