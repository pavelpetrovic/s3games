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
public class Rectangular extends LocationShape
{
    int a, b;

    public Rectangular(int a, int b)
    {
        this.a = a;
        this.b = b;
    }

    @Override
    boolean isInside(int x, int y, Point center)
    {
        if ( Math.abs(center.x-x)<a/2 && Math.abs(center.y-y)<b/2)  
          return true; 
        
        return false;
    }
    
    @Override 
    void paintShape(Graphics g, Point center) 
    {
        g.setColor(Color.red);
        g.drawRect(center.x-a/2, center.y-b/2, a, b);  //xy upper left corner - offset necessary
        g.setColor(Color.yellow);
        g.drawRect(center.x-a/2+2, center.y-b/2+2, a-4, b-4); 
    }
}
