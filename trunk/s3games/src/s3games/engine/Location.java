package s3games.engine;

import java.awt.Point;
import s3games.robot.RobotLocation;
import s3games.util.IndexedName;

/** Representation of a single location on the game board */
public class Location
{
    /** name of the location with one or two optional indexes */
    public IndexedName name;
    /** name of type of the location */
    public String type;
    /** point in the simulated game board image where this location has its center */
    public Point point;
    /** point in the real-world game board image obtained from camera where this location has its center */
    public Point camera;
    /** a configuration of robot arm to reach the point above and at the location */
    public RobotLocation robot;
    /** a flag of location relevancy for the game state as copied from the location type */
    public boolean relevant;

    /** construct a location with this name, setup other parameters manually */
    public Location(String name)
    {
        this.name = new IndexedName(name);
    }
}
