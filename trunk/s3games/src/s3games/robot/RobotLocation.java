package s3games.robot;

/** Represents a particular robot location - i.e. two sets of
 * all servo angles. First set for the "above the location" position [0-4],
 * second set for the "at the location" position [5-9]. */
public class RobotLocation
{
    /** the arm has 5 degrees of freedom + the gripper */
    public static final int NUMBER_OF_ROBOT_ANGLES = 10;

    /** 2x5 angles */
    public double angles[];

    /** construct an empty location */
    public RobotLocation()
    {
        angles = new double[NUMBER_OF_ROBOT_ANGLES];
    }
    
    /** parse the location specification string as loaded from the game specification file */
    public RobotLocation(String locations) throws Exception
    {
        angles = new double[NUMBER_OF_ROBOT_ANGLES];
        String[] angs = locations.split(",");
        if (angs.length != angles.length)
            throw new Exception("incorrect specification of robot angles: '" + locations + "'");
        for (int i = 0; i < angles.length; i++)
            angles[i] = Double.parseDouble(angs[i].trim());
    }

    /** make a copy of this location */
    public RobotLocation getCopy()
    {
        RobotLocation copied = new RobotLocation();
        for (int i = 0; i < angles.length; i++)
            copied.angles[i] = angles[i];
        return copied;
    }
    
    /** convert the first part of the location to string for visualization in robot controll window */
    @Override
    public String toString()
    {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < NUMBER_OF_ROBOT_ANGLES / 2 ; i++)
        {
            b.append(angles[i]);
            b.append(" ");
        }
        return b.toString();
    }
}
