/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3games.engine;

import s3games.gui.ImageWithHotSpot;
import s3games.gui.LocationShape;

/**
 *
 * @author Boris
 */
public class LocationType 
{
    public String name;
    public ImageWithHotSpot image;
    public LocationShape shape;
    
    public LocationType(String name)
    {
        this.name = name;
    }
}
