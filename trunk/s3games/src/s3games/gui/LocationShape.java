package s3games.gui;

import java.awt.Graphics;
import java.awt.Point;

/** A general class for representing a place where location is sensitive 
 * to user mouse clicking. Derived classes represent the specific shapes. */
public abstract class LocationShape
{
    /** determines if the specified pixel is inside of the area
     * @param x x-coordinate of the clicked pixel
     * @param y y-coordinate of the clicked pixel
     * @param center the center of the area to be tested */
    abstract boolean isInside(int x, int y, Point center);
    
    /** visualizes the clicking area in the canvas's graphics 
     * @param g - graphics to draw the shape into
     * @param center - center point of the area to be drawn */
    abstract void paintShape(Graphics g, Point center);
}
