package geometry_objects.angle;

import geometry_objects.angle.comparators.AngleStructureComparator;
import utilities.eq_classes.EquivalenceClasses;
import utilities.eq_classes.LinkedEquivalenceClass;

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
		 super(new AngleStructureComparator());
	 }
		
	 @Override
	 public boolean add(Angle element) {
			
		if (element == null) return false; 
		 
		int index = indexOfClass(element);
			
		//If the the location of the class does not exist, create a new LinkedEquivalenceClass to add the element to
		if (index == -1) {
				
			AngleLinkedEquivalenceClass tempClass = new AngleLinkedEquivalenceClass();
				
			tempClass.add(element);
				
			_classes.add(tempClass);
				
			return true;
		}
			
		//if the class does exist, add the element to it
		return _classes.get(index).add(element);	
			
			
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
			for (LinkedEquivalenceClass<Angle> c:_classes) {
				if (c.contains(target)) return true;
			}
			
			//item not contained
			return false;
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


		
		
	

	
}
