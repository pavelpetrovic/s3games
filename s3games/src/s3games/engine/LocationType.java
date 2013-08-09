package s3games.engine;

import s3games.gui.ImageWithHotSpot;
import s3games.gui.LocationShape;

/** Represents a location type. */
public class LocationType 
{
    /** name of the location */
    public String name;
    /** image that depicts the location on the simulated game board (use empty image if it is not needed */
    public ImageWithHotSpot image;
    /** shape of the location where it is sensitive to mouse-clicking for the simulated-game human player */
    public LocationShape shape;
    /** determines if locations of this type should be taken into account when comparing state for equality */
    public boolean relevant;
    
    /** create a new location type with this name, fill other parameters manually */
    public LocationType(String name)
    {
        this.name = name;
        relevant = true;
    }
}
