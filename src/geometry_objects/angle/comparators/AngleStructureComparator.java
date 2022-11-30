/**
 * Write a succinct, meaningful description of the class here. You should avoid wordiness    
 * and redundancy. If necessary, additional paragraphs should be preceded by <p>,
 * the html tag for a new paragraph.
 *
 * <p>Bugs: (a list of bugs and / or other problems)
 *
 * @author <your name>
 * @date   <date of completion>
 */

package geometry_objects.angle.comparators;

import java.util.Comparator;

import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.points.Point;
import utilities.math.MathUtilities;
import utilities.math.analytic_geometry.GeometryUtilities;

public class AngleStructureComparator implements Comparator<Angle>
{
	public static final int STRUCTURALLY_INCOMPARABLE = Integer.MAX_VALUE;
	
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
	 * What we care about is the fact that angle BAE is the smallest angle (structurally)
	 * and DAF is the largest angle (structurally). 
	 * 
	 * If one angle X has both rays (segments) that are subsegments of an angle Y, then X < Y.
	 * 
	 * If only one segment of an angle is a subsegment, then no conclusion can be made.
	 * 
	 * So:
	 *     BAE < CAE
   	 *     BAE < DAF
   	 *     CAF < DAF

   	 *     CAE inconclusive BAF
	 * 
	 * @param left -- an angle
	 * @param right -- an angle
	 * @return -- according to the algorithm above:
 	 *            Integer.MAX_VALUE will refer to our error result
 	 *            0 indicates an inconclusive result
	 */
	@Override
	public int compare(Angle left, Angle right)
	{
		//Check that the angles are structurally equivalent
        if (!(left.overlays(right))){
        	
        	return Integer.MAX_VALUE;
        	
        }
        
        //check that both left rays overlap both right rays
        Segment rayLeft1 = left.getRay1();
        Segment rayLeft2 = left.getRay2();
        
        if ((right.overlayingRay(rayLeft1) != null) && (right.overlayingRay(rayLeft2) != null)) {
        	
        	return 1;
        	
        }
        
        //check that both right rays overlap both left rays
        Segment rayRight1 = right.getRay1();
        Segment rayRight2 = right.getRay2();
        
        if ((left.overlayingRay(rayRight1) != null) && (left.overlayingRay(rayRight2) != null)) {
        	
        	return -1;
        	
        }
        
        //Check that one of the right rays overlaps a left ray 
        //and that one of the left rays overlaps a right ray
        if((left.overlayingRay(rayRight1) != null) && (right.overlayingRay(rayLeft1) != null)) {
        	return 0;
        }
        
        if((left.overlayingRay(rayRight2) != null) && (right.overlayingRay(rayLeft1) != null)) {
        	return 0;
        }
        
        if((left.overlayingRay(rayRight1) != null) && (right.overlayingRay(rayLeft2) != null)) {
        	return 0;
        }
        
        if((left.overlayingRay(rayRight2) != null) && (right.overlayingRay(rayLeft2) != null)) {
        	return 0;
        }
		
        return 12;        
	}
}
