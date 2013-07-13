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
    
    @Override
    boolean isInside(int x, int y)
    {
        System.out.println(this.x+" "+this.y+" "+x+" "+y+" "+radius+" "+Math.sqrt(Math.pow(this.y-y,2)+Math.pow(this.x-x,2)));
        return ( radius > Math.sqrt(Math.pow(this.y-y,2)+Math.pow(this.x-x,2)) );
    }
}
