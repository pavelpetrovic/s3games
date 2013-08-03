/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author petrovic16
 */
public class Circular extends LocationShape
{
    int radius;

    public Circular(int radius)
    {
        this.radius = radius;
    }
    
    @Override
    boolean isInside(int x, int y, Point center)
    {
        return ( radius > Math.sqrt(Math.pow(center.y-y,2)+Math.pow(center.x-x,2)) );
    }
    
    @Override 
    void paintShape(Graphics g, Point center) {
        g.setColor(Color.red);
        g.drawOval(center.x-radius, center.y-radius, radius*2, radius*2);
        g.setColor(Color.YELLOW);
        g.drawOval(center.x-radius+2, center.y-radius+2, radius*2-4, radius*2-4);
    }
}
