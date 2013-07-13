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
public class Circular extends LocationShape
{
    int radius;

    public Circular(int radius)
    {
        this.radius = radius;
    }
    
    @Override
    boolean isInside(int x, int y)
    {
      //  System.out.println(this.x+" "+this.y+" "+x+" "+y+" "+radius+" "+Math.sqrt(Math.pow(this.y-y,2)+Math.pow(this.x-x,2)));
        return ( radius > Math.sqrt(Math.pow(this.y-y,2)+Math.pow(this.x-x,2)) );
    }
    
    @Override 
    void paintShape(Graphics g) {
        g.drawRect(1, 1, 20, 20);
        g.setColor(Color.red);
        g.drawOval(x-radius, y-radius, radius*2, radius*2);
        g.setColor(Color.YELLOW);
        g.drawOval(x-radius+2, y-radius+2, radius*2-4, radius*2-4);
    }
}
