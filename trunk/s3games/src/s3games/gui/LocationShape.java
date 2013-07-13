/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.gui;

/**
 *
 * @author petrovic16
 */
public abstract class LocationShape
{
    int x;
    int y;

    abstract boolean isInside(int x, int y);
    
    public void setCenterPoint(int x, int y) {
       this.x =x;
       this.y =y;
    }
}
