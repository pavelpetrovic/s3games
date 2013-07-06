/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

/**
 *
 * @author petrovic16
 */
public class Element
{
    // static - specification
    String fullName;
    String baseName;
    Integer index;

    String type;
    String initialOwner;
    String initialLocation;
    int initialState;
    int initialZindex;

    // dynamic - during the game play
    String owner;
    Location location;
}