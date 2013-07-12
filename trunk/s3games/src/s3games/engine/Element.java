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
    public int initialOwner;
    public String initialLocation;
    public int initialState;
    public int initialZindex;

    public Element(String name)
    {
        this.name = new IndexedName(name);
    }
}