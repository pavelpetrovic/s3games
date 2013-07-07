/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package s3games.engine;

import java.util.Map;

/**
 *
 * @author petrovic16
 */
public class Variables
{
    Map<String, Expr> vars;

    public void set(String var, Expr val)
    {
        vars.put(var, val);
    }

    public Expr get(String var)
    {
        return vars.get(var);
    }
}
