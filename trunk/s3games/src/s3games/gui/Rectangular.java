/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.gui;

import java.awt.Color;
import java.awt.Graphics;

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
    boolean isInside(int x, int y)
    {
        if ( Math.abs(this.x-x)<a/2 && Math.abs(this.y-y)<b/2)  {
          return true; 
        }
        return false;
    }
    
    @Override 
    void paintShape(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(x-a/2, y-b/2, a, b);  //xy upper left corner - offset necessary
        g.setColor(Color.yellow);
        g.drawRect(x-a/2+2, y-b/2+2, a-4, b-4); 
    }
}
