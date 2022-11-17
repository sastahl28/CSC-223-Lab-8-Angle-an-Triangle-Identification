/**
 * Test commit
 */

package geometry_objects.points;

//import input.components.point.PointNode;
import utilities.math.MathUtilities;

/**
 * A 2D Point (x, y) only.
 * 
 * Points are ordered lexicographically (thus implementing the Comparable interface)
 * 
 * @author Sally Stahl
 * @date October 26, 2022
 * 
 */




public class Point implements Comparable<Point>
{
	public static final String ANONYMOUS = "__UNNAMED";

	public static final Point ORIGIN;
	
	static
	{
		ORIGIN = new Point("origin", 0, 0);
	}

	protected double _x;
	public double getX() { return this._x; }

	protected double _y; 
	public double getY() { return this._y; }

	protected String _name; 
	public String getName() { return _name; }

	// BasicPoint objects are named points (from input)
	// ImpliedPoint objects are unnamed points (from input)
	public boolean isGenerated() { return false; }

	/**
	 * Create a new Point with the specified coordinates.
	 * @param x The X coordinate
	 * @param y The Y coordinate
	 */
	public Point(double x, double y)
	{
		this(ANONYMOUS, x, y);
	}

	/**
	 * Create a new Point with the specified coordinates.
	 * @param name -- The name of the point. (Assigned by the UI)
	 * @param x -- The X coordinate
	 * @param y -- The Y coordinate
	 */
	public Point(String name, double x, double y)
	{
		_name = (name == null || name == "") ? ANONYMOUS : name;
		this._x = x;
		this._y = y;
	}

	/**
	 * @return if this point has not user-defined name associated with it
	 */
	public boolean isUnnamed()
	{
		return _name == ANONYMOUS;
	}

	@Override
	public int hashCode()
	{
		return Double.valueOf(MathUtilities.removeLessEpsilon(_x)).hashCode() +
			   Double.valueOf(MathUtilities.removeLessEpsilon(_y)).hashCode();
	}

	
	/**
	 * 
	 * @param p1 Point 1
	 * @param p2 Point 2
	 * @return Lexicographically: p1 < p2 return -1 : p1 == p2 return 0 : p1 > p2 return 1
	 *         Order of X-coordinates first; order of Y-coordinates second
	 */
	public static int LexicographicOrdering(Point p1, Point p2)
	{
		//either of the nodes are null
		//then return the correct value where the other node is larger
		if(p1 == null){return  -1;}
		if(p2 == null){return  1;}
		
		//if the x value of p1 is greater than p2, return -1
		if(p1._x < p2._x) {return -1;}
		if(p1._x > p2._x) {return 1;}
		
		//if the y value of p1 is greater than p2 then return -1
		if(p1._y < p2._y) {return -1;}
		if(p1._y > p2._y) {return 1;}
		
		return 0;
		
		
	}

	@Override
	public int compareTo(Point that)
	{
		if (that == null) return 1;

		return Point.LexicographicOrdering(this, that);
	}
	
	
	
	
	/**
	 * Compare this with a given object for equality; returns true
	 * if they are equal, false otherwise.
	 * @param obj
	 */
	@Override
	public boolean equals(Object obj) {
		//check if obj is null or of different class
		if (obj == null) return false;
		if (obj.getClass() != this.getClass()) return false;
		
		Point objAsPoint = (Point) obj;
		//check obj address and coordinates
		if (this == obj) return true; 
		if (MathUtilities.doubleEquals(this.getX(),objAsPoint.getX()) && 
			MathUtilities.doubleEquals(this.getY(),objAsPoint.getY())) return true;
		return false;
	}
}
	
