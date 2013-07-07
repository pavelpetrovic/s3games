/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.gui;

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
    
    boolean isInside(int x, int y)
    {
        return ( radius < Math.sqrt(Math.pow(this.y-y,2)+Math.pow(this.x-x,2)) );
    }
}
