/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.gui;

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

    boolean isInside(int x, int y)
    {
        if ( Math.abs(this.x-x)<a && Math.abs(this.y-y)<b)  {
          return true; 
        }
        return false;
    }
}
