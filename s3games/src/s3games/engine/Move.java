/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

/**
 *
 * @author petrovic16
 */
public class Move
{
    public String from;
    public String to;
    public String element;
    
    public Move(String from, String to, String element)
    {
        this.from = from;
        this.to = to;
        this.element = element;
    }
    
    @Override
    public String toString()
    {
        return from + "->" + to + ":" + element;
    }
}
