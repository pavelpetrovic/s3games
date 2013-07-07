/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.robot;

/**
 *
 * @author petrovic16
 */
public class RobotLocation
{
    public static final int NUMBER_OF_ROBOT_ANGLES = 5;

    int angles[];

    public RobotLocation(String locations) throws Exception
    {
        angles = new int[NUMBER_OF_ROBOT_ANGLES];
        String[] angs = locations.split(",");
        if (angs.length != angles.length)
            throw new Exception("incorrect specification of robot angles: '" + locations + "'");
        for (int i = 0; i < angles.length; i++)
            angles[i] = Integer.parseInt(angs[i].trim());
    }
    //etc
}
