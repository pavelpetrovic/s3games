/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.gui;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author petrovic16
 */
public abstract class LocationShape
{
    abstract boolean isInside(int x, int y, Point center);
    abstract void paintShape(Graphics g, Point center);
     
}
