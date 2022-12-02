package geometry_objects.angle;

import java.util.Comparator;

import geometry_objects.angle.comparators.AngleStructureComparator;
import utilities.eq_classes.LinkedEquivalenceClass;
import utilities.eq_classes.LinkedList;

/**
 * This implementation requires greater knowledge of the implementing Comparator.
 * 
 * According to our specifications for the AngleStructureComparator, we have
 * the following cases:
 *
 *    Consider Angles A and B
 *    * Integer.MAX_VALUE -- indicates that A and B are completely incomparable
                             STRUCTURALLY (have different measure, don't share sides, etc. )
 *    * 0 -- The result is indeterminate:
 *           A and B are structurally the same, but it is not clear one is structurally
 *           smaller (or larger) than another
 *    * 1 -- A > B structurally
 *    * -1 -- A < B structurally
 *    
 *    We want the 'smallest' angle structurally to be the canonical element of an
 *    equivalence class.
 * 
 * @author XXX
 */

//Comparator<Angle> comparator
//AngleStructureComparator comparator
public class AngleLinkedEquivalenceClass extends LinkedEquivalenceClass<Angle>
{

	
	public AngleLinkedEquivalenceClass() {
		
		
		
		super(new AngleStructureComparator());
	}
	
	@Override
	public boolean add(Angle element) {
		
		if (element == null) return false;
		
		if (this.contains(element)) return false;
		
		if (!(belongs(element)))return false;
		
		if (_canonical == null) {
			_canonical = element;
			return true;
		}
		
		if (_comparator.compare(element, _canonical) == 1 || _comparator.compare(element, _canonical) == 0) {
			_rest.addToBack(element);
		}
		
		if (_comparator.compare(element, _canonical) == -1) {
			this.demoteAndSetCanonical(element);
		}
		return true;
	
	}
	
	
	@Override
	public boolean belongs(Angle target) {
		if (_canonical == null) return true;
		
		if (_comparator.compare(_canonical, target) != Integer.MAX_VALUE) return true; 
		return false;
		
	}
	
	/**
	 * 
	 * 
	 * 
	 */
	

	
	
	/**
	 * Checks if the target is contained in the Linked Equivalence Class
	 * @param target
	 * @return True if contained
	 */
	@Override
	public boolean contains(Angle target) {
		if (target == null || _canonical == null) return false;
		//check if target is canonical or in linked list
		if (_canonical.equals(target) || _rest.contains(target)) return true;
		//not contained
		return false;
	}
	
	
	
	/**
	 * Adds previous Canonical to Linked Equivalence Class and sets input to new canonical
	 * @param element
	 * @return True if element is different than canonical
	 */
	@Override
	public boolean demoteAndSetCanonical(Angle element) {
		
		//if 1st time setting canonical//
		if (_canonical == null ) {
			_canonical = element;
			return true;
		}
		//resetting canonical//
		//check if canonical and element are identical or if element is null
		if (_canonical.equals(element) || element == null) return false;
		//check if element belongs in list
		if (!(belongs(element))) return false;
		//check if element is already contained , if so remove element
		if (contains(element)) _rest.remove(element);
		
		//add canonical to front of list
		_rest.addToFront(_canonical);
		//set input value to canonical
		_canonical = element;
		return true;
	}
	
	
	/**
	 * Returns a string representation of the Linked Equivalence Class
	 */
	@Override
	public String toString() {
		//Create a string that represents list/canonical EX: {2 | 4, 6, 8, 12}
		StringBuilder s = new StringBuilder();
		s.append("{" + _canonical + " | ");
		s.append(_rest.toString() + "}");
		return s.toString();
	}
	
	
}