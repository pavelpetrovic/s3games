/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import s3games.gui.ImageWithHotSpot;

/**
 *
 * @author petrovic16
 */
public class ElementType
{
    public String name;
    public int numStates;
    public ImageWithHotSpot images[]; // 0..n-1
    public String realShapes[];

    public ElementType(String name)
    {
        this.name = name;
        numStates = 0;
    }
}
