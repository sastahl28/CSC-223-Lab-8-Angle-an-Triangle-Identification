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

	
	public AngleLinkedEquivalenceClass(AngleStructureComparator comparator) {
		
		super(comparator);
		_comparator = comparator;
		_canonical = null;
		_rest = new LinkedList<Angle>();
		
	}
	
	@Override
	public boolean add(Angle element) {
		
		if (element == null) return false;
		if (this.contains(element)) return false;
		if (!(belongs(element)))return false;
		
		if (_canonical == null) {
			_canonical = element;
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
	
	@Override
	public Angle canonical() {
		return _canonical;
	}
	
	/**
	 * Checks if the Linked Equivalence Class is empty
	 * UNCHANGED
	 * @return True if empty
	 */
	@Override
	public boolean isEmpty() {
		//check that list is empty AND that canonical is null;
		if (_canonical == null && _rest.isEmpty()) return true;
		//if not empty
		return false;
	}
	
	/**
	 * Clears the entire Linked Equivalence Class, including the canonical
	 */
	@Override
	public void clear() {
		//clear list AND clear canonical;
		_canonical = null;
		_rest.clear();
	}
	
	/**
	 * Clears the Linked Equivalence Class but the canonical does not change
	 * UNCHANGED
	 */
	@Override
	public void clearNonCanonical() {
		//clear list but NOT canonical
		_rest.clear();
	}
	
	/**
	 * Returns the size of the Linked Equivalence Class, including the canonical
	 * @return
	 * UNCHANGED
	 */
	@Override
	public int size() {
		//if canonical is not null size = linked list size + 1
		if (_canonical != null) return _rest._size + 1;
		//return the size of the rest of the linked list 
		return _rest.size();
	}
	
	
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
	 * Removes the input target from the Linked Equivalence Class
	 * @param target
	 * @return True if successfully removed
	 */
	@Override
	public boolean remove(Angle target) {
		//if target item is canonical
		if (target.equals(_canonical)) return removeCanonical();
		
		//check if in list
		if(_rest.contains(target)) {
			_rest.remove(target);
			return true;
		}
		
		//if cannot remove/not in list
		return false;
	}
	
	/**
	 * Removes the current canonical and replaces with 
	 * the first item in the rest of the list
	 * @return True if successful 
	 */
	@Override
	public boolean removeCanonical() {
		//if canonical is null or list is empty
		if ((_canonical == null) || (_rest.isEmpty())) return false;
		
		//get first item in rest
		Angle firstItem = _rest.getIndex(0);
		//remove first item from rest
		_rest.remove(firstItem);
		//set as new canonical
		_canonical = firstItem;
		//successfully removed
		return true;
		
		
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