/**
 * A database for storing points that also names the points if they have no name when added. 
 * It also checks for redundancy when adding points and will 
 * return an existing point if it is already in the database
 * 
 * @author Miles Dame
 * @date October 31, 2022
 * 
 */

package geometry_objects.points;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * Given a pair of coordinates; generate a unique name for it;
 * return that point object.
 *
 * Names go from A..Z..AA..ZZ..AAA...ZZZ
 */
public class PointNamingFactory
{
	private static final String _PREFIX = "*_"; // Distinguishes generated names

	private static final char START_LETTER = 'A';
	private static final char END_LETTER = 'Z';

	private String _currentName = "A";
	private int _numLetters = 1;

	//
	// A hashed container for the database of points;
	// This requires the Point class implement equals
	// based solely on the individual values and not a name
	// We need a get() method; HashSet doesn't offer one.
	// Each entry is a <Key, Value> pair where Key == Value
	//
	protected Map<Point, Point> _database;

	public PointNamingFactory()
	{

		_database = new LinkedHashMap<Point, Point>();
	}

	/**
	 * 
	 * @param points -- a list of points, named or not named
	 */
	public PointNamingFactory(List<Point> points)
	{
		_database = new LinkedHashMap<Point, Point>();

		// Loop through the list of points and add them to the _database
		for (Point p : points) {
			// If not named then give it a name and add it to the database
			if (p.isUnnamed() && p != null) {
				Point np = new Point(getCurrentName(), p.getX(), p.getY());
				this.put(np); 
			}
			// if named add to the database
			if (!p.isUnnamed() && p != null){ 
				this.put(p); 
			}
		}
	}
	
	/**
	 * 
	 * @return String - name of the point
	 */
	public String getCurrName() {
		return this.getCurrentName();
	}
	
	/**
	 * 
	 * @return int the number of letters in the current name
	 */
	public int getNumLetters() {
		return _numLetters;
	}

	/**
	 * @param pt -- (x, y) coordinate pair object
	 * @return a point (if it already exists) or a completely new point that
	 *         has been added to the database
	 */
	public Point put(Point pt)
	{
		Point point;
		// Check if the point already exists in the database
		if (!contains(pt)) {
			// Check if the point is named
			if (pt.isUnnamed()) {
				// If it isn't then look up existing and generate a name
				 point = lookupExisting(this.getCurrentName(), pt._x, pt.getY());
				 // Add to the database and return
				_database.put(point, point);
				return point;
			}
		// If the point is named then add it to the database 
		point = lookupExisting(pt.getName(), pt._x, pt.getY());
		_database.put(point, point);
		return point;
			
		}
		
		// If the point is in the database then get and return it.
		return _database.get(pt);
	}

	/**
	 * @param x -- single coordinate
	 * @param y -- single coordinate
	 * @return a point (if it already exists) or a completely new point that has been added to the database
	 */
	public Point put(double x, double y)
	{
		// see if the point exists 
		Point point = lookupExisting(getCurrentName(), x, y);
		_database.put(point, point);
		
		return point;
		
	}

	/**
	 * @param name -- the name of the point 
	 * @param x -- single coordinate
	 * @param y -- single coordinate
	 * @return a point (if it already exists) or a completely new point that
	 *         has been added to the database.
	 *         
	 *         If the point is in the database and the name differs from what
	 *         is given, nothing in the database will be changed; essentially
	 *         this means we use the first name given for a point.
	 *         
	 *         The exception is that a valid name can overwrite an unnamed point.
	 */
	public Point put(String name, double x, double y) {
		Point point;
		if (name == null) { 
			point = lookupExisting(getCurrentName(), x, y);
			return put(x, y);
		}
		point = lookupExisting(name, x, y);
		_database.put(point, point);
		return point;
	}    

	/**
	 * Strict access (read-only of the database)
	 * 
	 * @param x
	 * @param y
	 * @return stored database Object corresponding to (x, y) 
	 */
	public Point get(double x, double y)
	{

		return _database.get(new Point(x, y));
	}	
	public Point get(Point pt)
	{ 

		return _database.get(pt);
	}

	/**
	 * @param name -- the name of the point 
	 * @param x -- single coordinate
	 * @param y -- single coordinate
	 * @return a point (if it already exists) or a completely new point that
	 *         has been added to the database.
	 *         
	 *         If the point is in the database and the name differs from what
	 *         is given, nothing in the database will be changed; essentially
	 *         this means we use the first name given for a point.
	 *         
	 *         The exception is that a valid name can overwrite an unnamed point.
	 */
	private Point lookupExisting(String name, double x, double y)
	{
		// TODO
		if (!contains(x, y)) { return createNewPoint(name, x, y); }
		
		return get(x, y);
	}  

	/**
	 * @param name -- the name of the point 
	 * @param x -- single coordinate
	 * @param y -- single coordinate
	 * @return a point (if it already exists) or a completely new point that
	 *         has been added to the database.
	 *         
	 *         If the point is in the database and the name differs from what
	 *         is given, nothing in the database will be changed; essentially
	 *         this means we use the first name given for a point.
	 *         
	 *         The exception is that a valid name can overwrite an unnamed point.
	 */
	private Point createNewPoint(String name, double x, double y)
	{
		if (contains(x, y)) { return get(x, y); }
		
		Point point = new Point(name, x, y);
		
		return point;
		
	}

	/**
	 * @param x -- single coordinate
	 * @param y -- single coordinate
	 * @return simple containment; no updating
	 */
	public boolean contains(double x, double y) { return _database.containsKey(new Point(x,y)); }
	public boolean contains(Point p) { return _database.containsKey(p); }

	/**
	 * @return acquires and returns the next name in sequence; also
	 * generates the next name in a 'lazy list' manner 
	 */
	private String getCurrentName()
	{
		String name = _PREFIX + _currentName;
		updateName();
		return name;
	}

	/**
	 * Advances the current generated name to the next letter in the alphabet:
	 * 'A' -> 'B' -> 'C' -> 'Z' --> 'AA' -> 'BB'
	 */
	private void updateName() {
		char currChar = _currentName.charAt(0);
		StringBuilder sb = new StringBuilder();
		
	
		if (currChar == END_LETTER) {
			_numLetters++;
			for (int i = 0; i < _numLetters; i++) {
				sb.append(START_LETTER);
			}
		}
		
		else {
			currChar++;
			for (int i = 0; i < _numLetters; i++) {
				sb.append(currChar);
			}
		}
		_currentName = sb.toString();
	}


	/**
	 * @return The entire database of points.
	 */
	public  Set<Point> getAllPoints()
	{
        return _database.keySet();
	}

	public void clear() { _database.clear(); }
	public int size() { return _database.size(); }

	@Override
	public String toString()
	{
        Set<Point> points = getAllPoints();
        StringBuilder sb = new StringBuilder();
        
        for (Point p : points) {
        	String pointStr = "Name: " + p.getName() + ", X : " + p.getX() + ", Y : " + p.getY() + "\n";
        	sb.append(pointStr);
        	
        }
        
        return sb.toString();	
	}
}