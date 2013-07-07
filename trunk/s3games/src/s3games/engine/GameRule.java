/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import java.util.ArrayList;

/**
 *
 * @author petrovic16
 */
public class GameRule
{
    public String name;
    public Expression element;
    public Expression from;
    public Expression to;
    public Expression condition;
    public Expression scores;
    public ArrayList<String> actions;

    public GameRule(String name)
    {
        this.name = name;
    }

}
