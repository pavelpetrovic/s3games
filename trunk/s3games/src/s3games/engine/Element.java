package s3games.engine;

import s3games.util.IndexedName;

/** Element is something that can be moved from a location to another location */
public class Element
{
    /** name of the element - we use IndexedName class since it can have an index, such as el(2) */
    public IndexedName name;

    /** element type */
    public String type;
    
    /** the player number who owns this element when the game starts, or 0, if we do not care */
    public int initialOwner;
    
    /** the name of the location the element is placed when the game starts, elements must be located somewhere at any moment */    
    public String initialLocation;
    
    /** the state of the element (a number 1,2, etc.) when the game starts */
    public int initialState;
    
    /** this is used just for graphical purposes - elements can have z-index which determines how their images overlap on the board */
    public int initialZindex;

    /** construct an element with the specified name - fill the structure items manually then */
    public Element(String name)
    {
        this.name = new IndexedName(name);
        initialState = 1;
    }
}