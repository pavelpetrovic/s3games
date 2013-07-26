/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import java.awt.Point;
import s3games.gui.LocationShape;
import s3games.robot.RobotLocation;
import s3games.util.IndexedName;

/**
 *
 * @author petrovic16
 */
public class Location
{
    // static specification
    public IndexedName name;
    public String type;
    public Point point;
    public Point camera;
    public RobotLocation robot;
    public boolean relevant;

    public Location(String name)
    {
        this.name = new IndexedName(name);
    }
}
