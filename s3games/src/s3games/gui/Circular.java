package s3games.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/** Represents a circular clicking area */
public class Circular extends LocationShape
{
    /** radius of the clicking circle */
    int radius;

    /** construct a circular clicking area with the specified radius */
    public Circular(int radius)
    {
        this.radius = radius;
    }
    
    /** determines whether the user clicked inside the circular area */
    @Override
    boolean isInside(int x, int y, Point center)
    {
        return ( radius > Math.sqrt(Math.pow(center.y-y,2)+Math.pow(center.x-x,2)) );
    }
    
    /** draw the clicking area to the canvas's graphics */
    @Override 
    void paintShape(Graphics g, Point center) {
        g.setColor(Color.red);
        g.drawOval(center.x-radius, center.y-radius, radius*2, radius*2);
        g.setColor(Color.YELLOW);
        g.drawOval(center.x-radius+2, center.y-radius+2, radius*2-4, radius*2-4);
    }
}
