/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import s3games.util.IndexedName;

/**
 *
 * @author petrovic16
 */
public class Element
{
    // static - specification
    public IndexedName name;

    public String type;
    public String initialOwner;
    public String initialLocation;
    public int initialState;
    public int initialZindex;

    // dynamic - during the game play
    public String owner;
    public Location location;

    public Element(String name)
    {

    }
}