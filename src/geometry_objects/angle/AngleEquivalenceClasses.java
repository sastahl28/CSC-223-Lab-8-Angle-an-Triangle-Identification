package geometry_objects.angle;

import geometry_objects.angle.comparators.AngleStructureComparator;
import utilities.eq_classes.EquivalenceClasses;

/**
 * Given the figure below:
 * 
 *    A-------B----C-----------D
 *     \
 *      \
 *       \
 *        E
 *         \
 *          \
 *           F
 * 
 * Equivalence classes structure we want:
 * 
 *   canonical = BAE
 *   rest = BAF, CAE, DAE, CAF, DAF
 */
public class AngleEquivalenceClasses extends EquivalenceClasses<Angle>
{
	// TODO
	//i think we just need to call every method from the equivalence class
	//but with the methods from other class
	 public AngleEquivalenceClasses() { 
	 }
		
	 @Override
	 public boolean add(Angle element) {
			//check if element null
			if (element == null) return false;
			//find index element belongs to and add to that class
			int index = indexOfClass(element);
			if (!(index == -1)) {
				_classes.get(index).add(element);
				return true;
			}
			
			//otherwise create a new equivalence class and set element as that classes canonical
			AngleLinkedEquivalenceClass c = new AngleLinkedEquivalenceClass(_comparator);
			c.demoteAndSetCanonical(element);
			_classes.add(c);
			return true;
		}

		/**
		 * Checks if the arrayList contains the input target
		 * @param target
		 * @return True if containment
		 */
	 	@Override
		public boolean contains(Angle target) {
			//check if target is null
			if (target == null) return false;
			//check each class in classes and see if contains target
			for (AngleLinkedEquivalenceClass c:_classes) {
				if (c.contains(target)) return true;
			}
			//item not contained
			return false;
		}
		
		/**
		 * Returns the number of equivalence classes contained in the list
		 * @return and integer 
		 */
		@Override
		public int size() {
			//calculate # of items in all classes
			int size = 0;
			for (AngleLinkedEquivalenceClass c:_classes) {
				size += c.size();
			}
			return size;
		}

		/**
		 * Returns the number of classes contained in the list
		 * @return
		 */
		@Override
		public int numClasses() {
			//return size of list, list should not have duplicates
			return _classes.size();
		}

		/**
		 * Returns the index a particular class is located at; Returns -1 if
		 * the input is null or the class is not contained in the list
		 * @param element
		 * @return index of particular class
		 */
		@Override
		protected int indexOfClass(Angle element) {
			//check if element is null
			if (element == null) return -1;
			//index location element belongs to
			for (int i = 0; i < _classes.size(); i++) {
				//check if target is equal to current item
				if (_classes.get(i).belongs(element)) return i;
			}
			//not contained
			return -1;
		}

		/**
		 * Returns a String representation of the contents of
		 * each Linked Equivalence Class the arrayList
		 * @return String 
		 */
		@Override
		public String toString() {
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < _classes.size(); i++) {
				//add each item in arrayList to string on a new line
				s.append(_classes.get(i).toString() + "\n");
			}
			//String representation of all the classes in arrayList 
			return s.toString();
		}
		
		
	

	
}
