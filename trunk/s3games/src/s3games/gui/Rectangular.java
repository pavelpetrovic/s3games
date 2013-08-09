package s3games.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/** Represents a rectangular clicking area */
public class Rectangular extends LocationShape
{
    /** dimensions of the clicking rectangle */
    int a, b;

    /** construct a rectangular clicking area with the specified dimensions */
    public Rectangular(int a, int b)
    {
        this.a = a;
        this.b = b;
    }

    /** determines whether the user clicked inside the rectangular area */    
    @Override
    boolean isInside(int x, int y, Point center)
    {
        if ( Math.abs(center.x-x)<a/2 && Math.abs(center.y-y)<b/2)  
          return true; 
        
        return false;
    }
    
    /** draw the clicking area to the canvas's graphics */    
    @Override 
    void paintShape(Graphics g, Point center) 
    {
        g.setColor(Color.red);
        g.drawRect(center.x-a/2, center.y-b/2, a, b);  //xy upper left corner - offset necessary
        g.setColor(Color.yellow);
        g.drawRect(center.x-a/2+2, center.y-b/2+2, a-4, b-4); 
    }
}
