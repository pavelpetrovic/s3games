/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import s3games.gui.LocationShape;
import s3games.robot.RobotLocation;

/**
 *
 * @author petrovic16
 */
public class Location
{
    // static specification
    String fullName;
    String baseName;
    Integer index[];
    String type;
    LocationShape shape;
    RobotLocation robot;

    // dynamic during the game
    Element content;
}
