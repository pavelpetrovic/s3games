package s3games.engine;

import s3games.gui.ImageWithHotSpot;

/** Every movable element must have a type. Element type specifies the number 
 * of states and how the element is visualized on the board in each state. */
public class ElementType
{
    /** name of the element type */
    public String name;
    /** the number of states this element can take */
    public int numStates;
    /** images for all states, indexed from 0..numStates-1 */
    public ImageWithHotSpot images[]; 

    /** construct a new element type, fill the images manually */
    public ElementType(String name)
    {
        this.name = name;
        numStates = 0;
    }
}
